package clientserver;

import android.content.Context;
import android.util.Log;

import java.io.*;
import java.net.*;
import java.util.*;

import shared.Requests.LoginRequest;
import shared.Requests.RegisterRequest;
import shared.Requests.PersonRequest;
import shared.Results.LoginResult;
import shared.Results.RegisterResult;
import shared.Results.PersonResult;
import shared.Results.EventResult;
import shared.Model.Person;
import shared.Model.Event;

import model.Model;
/**
 * Created by Jonathan on 3/22/18.
 */

public class ServerProxy {
    private String token = null;
    private String serverHost = null;
    private String serverPort = null;
    private Context context;
    private String firstName = null;
    private String lastName = null;

    public ServerProxy() {
        this.context = Model.getMainActivity();
    }

    public String getToken() {
        return token;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getServerHost() { return this.serverHost; }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerPort() { return this.serverPort;}

    public RegisterResult registerUser(RegisterRequest request) {
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort
                            + "/user/register");

            RegisterTask task = new RegisterTask(new RegisterTask.TaskListener() {
                @Override
                public boolean onFinished(RegisterResult results) {
                    if (results.getAuthToken() != null) {
                        Model.getServer().setToken(results.getAuthToken());
                        Model.getCurrentPerson().setPersonID(results.getPersonId());
                        Model.getMainActivity().getLoginFragment().onLogin(true);
                        return true;
                    }
                    else {
                        Log.e("Login Task Finished", results.toString());
                        Model.getMainActivity().getLoginFragment().onLogin(false);
                        return false;
                    }
                }
            },
                    "POST", request
            );
            task.execute(url);
        }
        catch (Exception e) {
            Log.e("Server URL Error", e.getMessage(), e);
            return null;
        }
        return null;
    }

    public LoginResult loginUser(LoginRequest request) {
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort +
                              "/user/login");
            LoginTask task = new LoginTask(new LoginTask.TaskListener() {
                @Override
                public boolean onFinished(LoginResult results) {
                    if (results.getAuthToken() != null) {
                        Model.getServer().setToken(results.getAuthToken());
                        Model.getCurrentPerson().setPersonID(results.getPersonID());
                        Model.getMainActivity().getLoginFragment().onLogin(true);
                        return true;
                    }
                    else {
                        Log.e("Login Task Finished", results.toString());
                        Model.getMainActivity().getLoginFragment().onLogin(false);
                        return false;
                    }
                }
            },
                    "POST", request
            );
            task.execute(url);
        }
        catch (Exception e) {
            Log.e("Server URL Error", e.getMessage(), e);
            return null;
        }
        return null;
    }

    public PersonResult getPerson(PersonRequest request) {
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort
            + "/person/" + request.getPersonId());

            PersonTask task = new PersonTask(new PersonTask.TaskListener() {
                @Override
                public boolean onFinished(PersonResult results) {
                    if (results.getPersonID() != null) {
                        Person person = new Person(results.getFirstName(),
                                results.getLastName(), results.getGender());
                        person.setDescendant(results.getDescendant());
                        person.setPersonID(results.getPersonID());
                        if (results.getFather() != null) {
                            person.setFather(results.getFather());
                        }
                        if (results.getMother() != null) {
                            person.setMother(results.getMother());
                        }
                        if (results.getSpouse() != null) {
                            person.setSpouse(results.getSpouse());
                        }
                        Model.setCurrentPerson(person);
                        return true;
                    }
                    else {
                        Log.e("Person Finished", results.toString());
                        return false;
                    }
                }
            },
                    "GET", request
            );
            task.execute(url);
        }
        catch (Exception e) {
            Log.e("Server URL Error", e.getMessage(), e);
            return null;
        }
        return null;
    }

    public List<PersonResult> getAllPeople(final boolean resync) {
        try {
            URL url = new URL("http://" + serverHost + ":" +
                        serverPort + "/person/");
            PeopleTask task = new PeopleTask(new PeopleTask.TaskListener() {
                @Override
                public boolean onFinished(List<PersonResult> results) {
                    if (results != null) {
                        List<Person> people = new ArrayList<>();
                        for (int i = 0; i < results.size(); i++) {
                            PersonResult rs = results.get(i);
                            Person person = new Person(rs.getFirstName(), rs.getLastName(), rs.getGender());
                            person.setDescendant(rs.getDescendant());
                            person.setPersonID(rs.getPersonID());
                            if (rs.getFather() != null) {
                                person.setFather(rs.getFather());
                            }
                            if (rs.getMother() != null) {
                                person.setMother(rs.getMother());
                            }
                            if (rs.getSpouse() != null) {
                                person.setSpouse(rs.getSpouse());
                            }
                            people.add(person);
                        }
                        if (resync) {
                            Model.setCurrentPeople(people);
                            Model.peopleListToMap();
                            Model.getSettings().resyncSucceeded();
                        }
                        else {
                            Model.setCurrentPeople(people);
                            Model.peopleListToMap();
                            Model.getMainActivity().onPeopleLoaded();
                        }
                        return true;
                    }
                    else {
                        Log.e("All people finished", results.toString());
                        return false;
                    }
                }
            },
                    "GET"
            );
            task.execute(url);
        }
        catch (Exception e) {
            Log.e("Server URL Error", e.getMessage(), e);
            return null;
        }
        return null;
    }

    public List<EventResult> getAllEvents(final boolean resync) {
        try {
            URL url = new URL("http://" + serverHost + ":" +
                            serverPort + "/event/");
            EventsTask task = new EventsTask(new EventsTask.TaskListener() {
                @Override
                public boolean onFinished(List<EventResult> results) {
                    if (results != null) {
                        List<Event> events = new ArrayList<>();
                        for (int i = 0; i < results.size(); i++) {
                            EventResult rs = results.get(i);
                            Event event = new Event(rs.getDescendant(),
                                    rs.getPersonId(), rs.getLatitude(), rs.getLongitude(),
                                    rs.getCountry(), rs.getCity(), rs.getEventType(), rs.getYear());
                            event.setEventID(rs.getEventId());
                            events.add(event);
                        }
                        if (resync) {
                            Model.setCurrentEvents(events);
                            Model.eventListToMap();
                            Model.getSettings().resyncSucceeded();
                        }
                        else {
                            Model.setCurrentEvents(events);
                            Model.eventListToMap();
                            Model.getMainActivity().onEventsLoaded();
                        }
                        return true;
                    }
                    else {
                        Log.e("All Events Finished", results.toString());
                        return false;
                    }
                }
            },
                    "GET"
            );
            task.execute(url);
        }
        catch (Exception e) {
            Log.e("Server URL Error", e.getMessage(), e);
            return null;
        }
        return null;
    }
}
