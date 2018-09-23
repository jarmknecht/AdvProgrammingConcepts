package server.Service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import server.DataAccessObjs.FillDao;
import server.DataAccessObjs.Transaction;
import shared.Model.AuthToken;
import shared.Model.Person;
import shared.Model.User;

import static org.junit.Assert.*;

/**
 * Created by Jonathan on 3/1/18.
 */
public class RegisterServiceTest {
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

        trans = new Transaction();
        trans.openConnection();
        conn = trans.getConn();

        trans.userTableAccess();
        trans.personTableAccess();
        trans.tokenTableAccess();

        trans.userAccess.deleteAll();
        trans.personAccess.deleteAll();
        trans.tokenAccess.deleteAll();

        user = new User("jarm", "password", "email@email.com",
                "jonathan", "armknecht", "m");
        person = new Person(user.getFirstName(), user.getLastName(), user.getGender());
        user.setPersonID(person.getPersonID());
        trans.userAccess.insertUser(user);
    }

    @After
    public void tearDown() throws Exception {
        trans.closeConnection(false);
    }

    @Test
    public void registerUser() throws Exception {
        boolean success = false;

        AuthToken token = new AuthToken();
        success = trans.tokenAccess.addToken(token, user.getUserName(), user.getPersonID());

        assertTrue(success);
    }

    @Test
    public void getTokenOfUser() throws Exception {
        AuthToken token = null;
        token = trans.tokenAccess.getTokenByUserName(user.getUserName());
        assertNotNull(token);
    }

    @Test
    public void checkForUser() throws Exception {
        User u = null;
        u = trans.userAccess.getAccountInfoByUsername(user.getUserName());
        assertNotNull(u);
    }

}