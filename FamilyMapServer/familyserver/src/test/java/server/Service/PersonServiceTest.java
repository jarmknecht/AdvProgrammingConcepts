package server.Service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

import server.DataAccessObjs.Transaction;
import shared.Model.AuthToken;
import shared.Model.Person;
import shared.Model.User;

import static org.junit.Assert.*;

/**
 * Created by Jonathan on 3/1/18.
 */
public class PersonServiceTest {
    private Transaction trans;
    private Connection conn;
    private User user = null;
    private Person person = null;
    private AuthToken token = null;

    @Before
    public void setUp() throws Exception {
        trans = new Transaction();
        trans.openConnection();
        conn = trans.getConn();

        trans.userTableAccess();
        trans.personTableAccess();
        trans.tokenTableAccess();

        trans.userAccess.deleteAll();
        trans.personAccess.deleteAll();
        trans.tokenAccess.deleteAll();

        token = new AuthToken();

        user = new User("jarm", "password", "email@email.com",
                "jonathan", "armknecht", "m");

        person = new Person("jonathan","armknecht","m");

        person.setDescendant(user.getUserName());
        trans.personAccess.createPerson(person);
        user.setPersonID(person.getPersonID());
        trans.userAccess.insertUser(user);
        token.setUserName(user.getUserName());
        token.setPersonID(user.getPersonID());
        trans.tokenAccess.addToken(token, token.getUserName(), token.getPersonID());
    }

    @After
    public void tearDown() throws Exception {
        trans.closeConnection(false);
    }

    @Test
    public void authenUser() throws Exception {
        boolean success = false;
        success = trans.tokenAccess.autheticationToken(token.getToken());
        assertTrue(success);
    }

    @Test
    public void getPersonById() throws Exception {
        Person p = null;
        p = trans.personAccess.getPersonByPersonId(person.getPersonID());
        p.setEvents(null);
        assertNotNull(p);
    }

    @Test
    public void getAllPeopleByUser() throws Exception {
        List<Person> people = null;
        String username = trans.tokenAccess.getUserNameByToken(token.getUserName());
        people = trans.personAccess.getAllPersonsByUser(username);
        assertNotNull(people);
    }

    @Test
    public void getUserByToken() throws Exception {
        String username = null;
        username = trans.tokenAccess.getUserNameByToken(token.getToken());
        assertNotNull(username);
    }

}