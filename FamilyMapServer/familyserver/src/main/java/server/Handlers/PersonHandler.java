package server.Handlers;

import java.io.*;
import java.util.*;
import java.net.*;

import com.google.gson.*;
import com.sun.net.httpserver.*;

import shared.Results.PersonResult;
import server.Service.PersonService;
import shared.Model.Person;

//done with code
/**
 * Created by Jonathan on 2/24/18.
 */

public class PersonHandler implements HttpHandler {
    private EncoderDecoder encoderDecoder = new EncoderDecoder();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String personId;
        String currentUser;
        PersonService personService;
        URI url = exchange.getRequestURI();
        String URL = url.toString();

        String token = exchange.getRequestHeaders().getFirst("Authorization");
        System.out.println("    URL received: " + URL); //need??
        String[] params = URL.split("/");

        if (exchange.getRequestMethod().toLowerCase().equals("get")) {
            personService = new PersonService();

            if (!personService.authenUser(token)) {
                personService.errorClosConnection();
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                encoderDecoder.sendData(encoderDecoder.makeMessage("Authorization token not found."), exchange);
                return;
            }
            else {
                currentUser = personService.getUserByToken(token);
                if (params.length == 3) {
                    Person person;
                    personId = params[2];
                    person = personService.getPersonById(personId);

                    if (person.getDescendant().equals(currentUser)) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        encoderDecoder.sendData(person, exchange);
                    }
                    else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                        encoderDecoder.sendData(encoderDecoder.makeMessage("That information is owned by other user.") , exchange);
                        return;
                    }
                }
                else {
                    List<PersonResult> people = new ArrayList<>();
                    List<Person> personList;

                    personList = personService.getAllPeopleByUser(token);

                    for (Person p : personList) {
                        PersonResult personResult = new PersonResult(p.getDescendant(),
                                                    p.getPersonID(), p.getFirstName(), p.getLastName(),
                                                    p.getGender(), p.getMother(), p.getFather(), p.getSpouse());
                        people.add(personResult);
                    }

                    if (people == null) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        encoderDecoder.sendData(encoderDecoder.makeMessage("No people exist for user"), exchange);
                    }
                    else if (personList.get(0).getDescendant().equals(currentUser)) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        Gson gson = new Gson();
                        PersonResult[] personResults = people.toArray(new PersonResult[people.size()]);
                        JsonObject jsonData = new JsonObject();
                        jsonData.add("data", gson.toJsonTree(personResults));
                        encoderDecoder.sendData(jsonData, exchange);
                    }
                    else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        encoderDecoder.sendData(encoderDecoder.makeMessage("That information is owned by another user"),
                                exchange);
                    }
                }
            }
        }
    }
}
