package server.Service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import shared.Model.*;

import server.DataAccessObjs.Transaction;

import static org.junit.Assert.*;

//all tests passed!
/**
 * Created by Jonathan on 2/26/18.
 */
public class ClearServiceTest {
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

        for (int i = 0; i < 10; i++) { //make ten entries of the same info to see if can delete all
            token = new AuthToken();
            trans.tokenAccess.addToken(token, "jarm" + i, "123");

            person = new Person("jonathan", "armknecht", "m");
            trans.personAccess.createPerson(person);

            user = new User("jarm" + i, "password", "email@email.com",
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
    }

    @After
    public void tearDown() throws Exception {
        trans.closeConnection(false);
    }

    @Test
    public void clearAll() throws Exception {
        Person testPerson;
        User testUser;
        boolean isNull = false;
        Event testEvent;

        trans.tokenAccess.deleteAll();
        assertFalse(trans.tokenAccess.autheticationToken(token.getToken()));

        trans.userAccess.deleteAll();
        testUser = trans.userAccess.getAccountInfoByUsername(user.getUserName());
        if (testUser == null) {
            isNull = true;
        }
        assertTrue(isNull);

        trans.personAccess.deleteAll();
        testPerson = trans.personAccess.getPersonByPersonId(person.getPersonID());
        assertNull(testPerson.getPersonID());

        trans.eventAccess.deleteAll();
        testEvent = trans.eventAccess.getEventByEventId(event.getEventID());
        assertNull(testEvent.getEventID());
    }

}