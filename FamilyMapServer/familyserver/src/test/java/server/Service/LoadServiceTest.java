package server.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

import server.DataAccessObjs.Transaction;
import server.Handlers.EncoderDecoder;
import shared.Model.AuthToken;
import shared.Model.Event;
import shared.Model.Person;
import shared.Model.User;
import shared.Requests.LoadRequest;

import static org.junit.Assert.*;

/**
 * Created by Jonathan on 3/1/18.
 */
public class LoadServiceTest {
    private Transaction transaction;
    private Connection conn;
    private Person person;
    private User user;
    private Event event;
    private int numOfUsers;
    private int numOfPeople;
    private int numOfEvents;
    private User[] users = null;
    private Person[] persons = null;
    private Event[] events = null;
    private HttpExchange exchange;
    private EncoderDecoder encoderDecoder = new EncoderDecoder();

    @Before
    public void setUp() throws Exception {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();

        JsonObject jsonObject = (JsonObject) parser.parse(new FileReader
                        ("json" + File.separator + "example.json"));

        LoadRequest load = gson.fromJson(jsonObject, LoadRequest.class);
        users = load.getUsers();
        persons = load.getPersons();
        events = load.getEvents();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void loadData() throws Exception {
        assertNotNull(users);
        assertNotNull(persons);
        assertNotNull(events);

        transaction = new Transaction();
        transaction.openConnection();
        conn = transaction.getConn();
        transaction.userTableAccess();
        transaction.personTableAccess();
        transaction.eventTableAccess();
        transaction.tokenTableAccess();
        numOfEvents = 0;
        numOfPeople = 0;
        numOfUsers = 0;

        transaction.userAccess.deleteAll();
        transaction.personAccess.deleteAll();
        transaction.eventAccess.deleteAll();
        transaction.tokenAccess.deleteAll();

        for (User user : users) {
            try {
                transaction.userAccess.insertUser(user);
                numOfUsers++;
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        for (Person person : persons) {
            person.setEvents(null);
            try {
                transaction.personAccess.createPerson(person);
                numOfPeople++;
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for (Event event : events) {
            try {
                transaction.eventAccess.createEvent(event);
                numOfEvents++;
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        assertEquals(1, numOfUsers);
        assertEquals(3, numOfPeople);
        assertEquals(2, numOfEvents);

        transaction.closeConnection(false);
    }

}