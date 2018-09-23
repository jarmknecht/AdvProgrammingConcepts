package server.DataAccessObjs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import shared.Model.User;
import shared.Model.Person;

//all tests pass!
/**
 * Created by Jonathan on 2/26/18.
 */
public class UserDaoTest {
    private Transaction trans;
    private User user;

    @Before
    public void setUp() throws Exception {
        trans = new Transaction();
        user = new User("username", "password", "email@email.com", "first", "last", "gender");
        Person person = new Person("first","last","m");
        person.generatePersonId();
        user.setPersonID(person.getPersonID());
    }

    @After
    public void tearDown() throws Exception {
        trans.closeConnection(true);
        trans.openConnection();
        trans.userTableAccess();
        trans.userAccess.deleteAll();
        trans.closeConnection(true);
    }

    @Test
    public void insertUser() throws Exception {
        boolean success = false;
        trans.openConnection();
        trans.userTableAccess();
        success = trans.userAccess.insertUser(user);
        assertTrue(success);
    }

    @Test
    public void deleteUser() throws Exception {
        boolean success = false;
        trans.openConnection();
        trans.userTableAccess();
        trans.userAccess.insertUser(user);
        trans.closeConnection(true);
        trans.openConnection();
        trans.userTableAccess();
        success = trans.userAccess.deleteUser(user);
        assertTrue(success);
    }

    @Test
    public void deleteAll() throws Exception {
        User testUser;
        trans.openConnection();
        trans.userTableAccess();
        trans.userAccess.insertUser(user);
        user.setUserName("a");
        trans.userAccess.insertUser(user);
        user.setUserName("b");
        trans.userAccess.insertUser(user);
        user.setUserName("c");
        trans.userAccess.insertUser(user);
        trans.closeConnection(true);
        trans.openConnection();
        trans.userTableAccess();
        testUser = trans.userAccess.getAccountInfoByUsername("a");
        assertNotNull(testUser);
        testUser = trans.userAccess.getAccountInfoByUsername("b");
        assertNotNull(testUser);
        testUser = trans.userAccess.getAccountInfoByUsername("c");
        assertNotNull(testUser);
        trans.userAccess.deleteAll();
        trans.closeConnection(true);
        trans.openConnection();
        trans.userTableAccess();
        testUser = trans.userAccess.getAccountInfoByUsername("a");
        assertNull(testUser);
        testUser = trans.userAccess.getAccountInfoByUsername("b");
        assertNull(testUser);
        testUser = trans.userAccess.getAccountInfoByUsername("c");
        assertNull(testUser);
    }

    @Test
    public void getAccountInfoByUsername() throws Exception {
        User testUser;
        trans.openConnection();
        trans.userTableAccess();
        trans.userAccess.insertUser(user);
        trans.closeConnection(true);
        trans.openConnection();
        trans.userTableAccess();
        testUser = trans.userAccess.getAccountInfoByUsername(user.getUserName());
        assertNotNull(testUser);
    }

}