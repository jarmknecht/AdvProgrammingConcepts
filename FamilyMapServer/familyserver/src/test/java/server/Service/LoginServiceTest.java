package server.Service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import server.DataAccessObjs.Transaction;
import shared.Model.AuthToken;
import shared.Model.Person;
import shared.Model.User;

import static org.junit.Assert.*;

/**
 * Created by Jonathan on 3/1/18.
 */
public class LoginServiceTest {
    private Transaction trans;
    private Connection conn;
    private User user;
    private Person person;
    private AuthToken token;

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
        trans.tokenAccess.addToken(token, user.getUserName(), user.getPersonID());
    }

    @After
    public void tearDown() throws Exception {
        trans.closeConnection(false);
    }

    @Test
    public void checkIfUserExists() throws Exception {
        user = trans.userAccess.getAccountInfoByUsername(user.getUserName());
        assertNotNull(user);
    }

    @Test
    public void loginUser() throws Exception {
        boolean success = false;
        if (trans.tokenAccess.checkForUser(user.getUserName())) {
            success = trans.tokenAccess.updateAuthToken(user.getUserName(), token.getToken());
        }
        else {
            success = trans.tokenAccess.addToken(token, user.getUserName(), user.getPersonID());
        }
        assertTrue(success);
    }

}