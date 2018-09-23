package server.Service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

import server.DataAccessObjs.Transaction;
import shared.Model.AuthToken;
import shared.Model.Event;
import shared.Model.Person;
import shared.Model.User;

import static org.junit.Assert.*;

/**
 * Created by Jonathan on 3/1/18.
 */
public class EventServiceTest {
    private Transaction trans;
    private Connection conn;
    private Person person;
    private User user;
    private AuthToken token;
    private Event event;

    @Before
    public void setUp() throws Exception {
        trans = new Transaction();
        trans.openConnection();
        conn = trans.getConn();

        trans.userTableAccess();
        trans.personTableAccess();
        trans.eventTableAccess();
        trans.tokenTableAccess();

        token = new AuthToken();
        trans.tokenAccess.addToken(token, "jarm", "123");

        user = new User("jarm", "password", "email@email.com",
                "jonathan", "armknecht", "m");

        person = new Person("jonathan","armknecht","m");

        person.setDescendant(user.getUserName());
        trans.personAccess.createPerson(person);
        user.setPersonID(person.getPersonID());
        trans.userAccess.insertUser(user);

        event = new Event("descendant", "123", "123", "123", "country", "city", "type", 2000);
        event.generateEventId();
        trans.eventAccess.createEvent(event);
    }

    @After
    public void tearDown() throws Exception {
        trans.closeConnection(false);
    }

    @Test
    public void authenticateUser() throws Exception {
        boolean success = false;
        success = trans.tokenAccess.autheticationToken(token.getToken());
        assertTrue(success);
    }

    @Test
    public void getUserByToken() throws Exception {
        String username = null;
        username = trans.tokenAccess.getUserNameByToken(token.getToken());
        assertEquals("jarm", username);
    }

    @Test
    public void getEventById() throws Exception {
        boolean success = false;
        event = trans.eventAccess.getEventByEventId(event.getEventID());
        assertNotNull(event);
    }

    @Test
    public void getAllEventsByUser() throws Exception {
        List<Event> events = null;
        String username = trans.tokenAccess.getUserNameByToken(token.getToken());
        events = trans.eventAccess.getAllEventsByDescendant(username);
        assertNotNull(events);
    }

}