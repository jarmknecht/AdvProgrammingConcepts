package server.DataAccessObjs;

import java.util.*;
import java.sql.*;
import java.io.*;

import com.google.gson.*;

import shared.Model.User;
import shared.Model.Event;
import shared.Model.Person;
//done with code
/**
 * Created by Jonathan on 2/24/18.
 */

public class FillDao {
    private Transaction transaction;
    private Connection conn;
    private Gson gson = new Gson();
    private String username;
    private Random rand;
    private int numOfEvents;
    private int numOfPeople;
    private JsonArray femaleNames;
    private JsonArray maleNames;
    private JsonArray lastNames;
    private JsonArray locations;

    public FillDao() {
        rand = new Random();
        rand.setSeed((int) System.nanoTime());

        transaction = new Transaction();
        transaction.openConnection();
        conn = transaction.getConn();
        transaction.userTableAccess();
        transaction.personTableAccess();
        transaction.eventTableAccess();
        transaction.tokenTableAccess();

        numOfEvents = 0;
        numOfPeople = 0;
    }

    public void importData(String username, int generations) {
        this.username = username;
        String femalenames = "json" + File.separator + "fnames.json";
        String malenames = "json" + File.separator + "mnames.json";
        String lastnames = "json" + File.separator + "snames.json";
        String locations = "json" + File.separator + "locations.json";

        femaleNames = parseData(femalenames);
        maleNames = parseData(malenames);
        lastNames = parseData(lastnames);
        this.locations = parseData(locations);

        try {
            User user = transaction.userAccess.getAccountInfoByUsername(username);
            Person person = new Person(user.getFirstName(), user.getLastName(), user.getGender());
            person.setPersonID(user.getPersonID());
            person.setDescendant(user.getUserName());
            generateGenerations(person, generations);

            transaction.closeConnection(true);
        }
        catch (Exception e) {
            e.printStackTrace();
            transaction.closeConnection(false);
        }
    }

    private JsonArray parseData(String data) {
        try {
            InputStreamReader input = new InputStreamReader(new FileInputStream(data), "UTF8");
            BufferedReader reader = new BufferedReader(input);
            StringBuilder output = new StringBuilder();
            String lineOfData;

            while ((lineOfData = reader.readLine()) != null) {
                output.append(lineOfData);
            }

            reader.close();

            try {
                JsonObject dataJson = gson.fromJson(output.toString(), JsonObject.class);
                return dataJson.getAsJsonArray("data");
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void generateGenerations(Person person, int generations) {
        int personBirth = 0;

        if (generations <= 0) {
            try {
                transaction.personAccess.createPerson(person);
                numOfPeople++;
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }
        generations--;

        if (person.getEvents().size() == 0) {
            personBirth = rand.nextInt((2000 - 1900) + 1) + 1900; //makes random birth year
        }
        else {
            for (Event event : person.getEvents()) {
                if (event.getEventType().equals("Birth")) {
                    personBirth = event.getYear();
                }
            }
        }

        Person father = generatePerson("m", rand.nextInt(((personBirth-30) - (personBirth-75)) + 1) + (personBirth-75));
        Person mother = generatePerson("f", rand.nextInt(((personBirth-25) - (personBirth-75)) + 1) + (personBirth-75));

        marriage(father,mother);
        person.setFather(father.getPersonID());
        person.setMother(mother.getPersonID());
        generateGenerations(father, generations);
        generateGenerations(mother, generations);

        try {
            transaction.personAccess.createPerson(person);
            numOfPeople++;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Makes json objects of the people created
    private Person generatePerson(String gender, int birth) {
        JsonPrimitive firstNameObject;
        JsonPrimitive lastNameObject;

        if (gender == "m") {
            firstNameObject = (JsonPrimitive) maleNames.get(rand.nextInt(((maleNames.size() - 1) - 0) + 1));
        }
        else {
            firstNameObject = (JsonPrimitive) femaleNames.get(rand.nextInt(((femaleNames.size() - 1) - 0) + 1));
        }

        lastNameObject = (JsonPrimitive) lastNames.get(rand.nextInt(((lastNames.size() - 1) - 0) + 1));

        String firstName = firstNameObject.getAsString();
        String lastName = lastNameObject.getAsString();

        Person person = new Person(firstName, lastName, gender);
        person.generatePersonId();
        person.setDescendant(username);
        generateEvents(person, birth);

        return person;
    }

    private void marriage(Person man, Person woman) {
        Event marriage = null;
        int manDeathYear = 0;
        int manBirthYear = 0;
        int womanDeathYear = 0;
        int womanBirthYear = 0;
        int deathYear;
        int birthYear;
        int marryYear;

        for (Event event : man.getEvents()) {
            if (event.getEventType().equals("Death")) {
                manDeathYear = event.getYear();
            }
            if (event.getEventType().equals("Birth")) {
                manBirthYear = event.getYear();
            }
        }

        for (Event event : woman.getEvents()) {
            if (event.getEventType().equals("Death")) {
                womanDeathYear = event.getYear();
            }
            if (event.getEventType().equals("Birth")) {
                womanBirthYear = event.getYear();
            }
        }

        if (womanDeathYear < manDeathYear) {
            deathYear = womanDeathYear;
        }
        else {
            deathYear = manDeathYear;
        }

        marryYear =  rand.nextInt(((deathYear-1) - (deathYear-25) + 1)) + (deathYear-25);

        addEvent(man, marryYear, "Marriage");
        for (Event event : man.getEvents()) {
            if (event.getEventType().equals("Marriage")) {
                marriage = event;
            }
        }

        marriage.setPersonID(woman.getPersonID());
        marriage.generateEventId();
        try {
            transaction.eventAccess.createEvent(marriage);
            numOfEvents++;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        man.setSpouse(woman.getPersonID());
        woman.setSpouse(man.getPersonID());
    }

    private void addEvent(Person person, int year, String description) {
        boolean success = false;
        JsonObject location;
        location = (JsonObject) this.locations.get(rand.nextInt(((this.locations.size()-1) - 0) + 1));

        String latitude = location.get("latitude").getAsString();
        String longitude = location.get("longitude").getAsString();
        String city = location.get("city").getAsString();
        String country = location.get("country").getAsString();


        Event event = new Event(username, person.getPersonID(), latitude, longitude, country, city, description, year);
        person.addEvent(event);
        try {
            success = transaction.eventAccess.createEvent(event);
            numOfEvents++;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void generateEvents(Person person, int birthYear) {
        int birth = birthYear;

        int death = rand.nextInt(((birth+100) - (birth+30)) + 1) + (birth+30);
        if (death > 2017) {
            death = 2017;
        }
        int baptism = rand.nextInt((death - birth) + 1) + (birth);
        addEvent(person, birth, "Birth");
        addEvent(person, death, "Death");
        addEvent(person, baptism, "Baptism");
    }

    public int getNumOfEvents() {
        return numOfEvents;
    }

    public int getNumOfPeople() {
        return numOfPeople;
    }
}
