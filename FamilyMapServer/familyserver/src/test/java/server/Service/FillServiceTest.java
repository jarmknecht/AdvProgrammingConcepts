package server.Service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import server.DataAccessObjs.FillDao;
import server.DataAccessObjs.Transaction;
import shared.Model.AuthToken;
import shared.Model.Event;
import shared.Model.Person;
import shared.Model.User;

import static org.junit.Assert.*;

/**
 * Created by Jonathan on 3/1/18.
 */
public class FillServiceTest {
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

        trans.userAccess.deleteAll();
        trans.eventAccess.deleteAll();
        trans.personAccess.deleteAll();
        trans.tokenAccess.deleteAll();

        token = new AuthToken();
        trans.tokenAccess.addToken(token, "jarm", "123");

        user = new User("jarm", "password", "email@email.com",
                "jonathan", "armknecht", "m");

        person = new Person("jonathan","armknecht","m");
        person.setDescendant(user.getUserName());
        trans.personAccess.createPerson(person);
        user.setPersonID(person.getPersonID());
        trans.userAccess.insertUser(user);

        event = new Event("jarm", "123", "123", "123", "country", "city", "type", 2000);
        event.generateEventId();
        trans.eventAccess.createEvent(event);
        trans.closeConnection(true);
    }

    @After
    public void tearDown() throws Exception {
        trans.closeConnection(false);
    }

    @Test
    public void fill() throws Exception {
        trans.openConnection();
        conn = trans.getConn();

        trans.userTableAccess();
        trans.personTableAccess();
        trans.eventTableAccess();
        trans.tokenTableAccess();

        trans.personAccess.deleteByUser(user.getUserName());
        trans.eventAccess.deleteByDescendant(user.getUserName());
        FillDao filler = new FillDao();
        trans.closeConnection(true);
        filler.importData(user.getUserName(), 5);
        int numOfPeople = filler.getNumOfPeople();
        int numOfEvents = filler.getNumOfEvents();
        assertEquals(63, numOfPeople); //63
        assertEquals(248, numOfEvents); //248
    }
}