package shared.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
//test passes
/**
 * Created by Jonathan on 2/26/18.
 */
public class UserTest {
    private User user;
    private Person person;
    private String username = "jarm";
    private String password = "password";
    private String email = "email@email.com";
    private String firstName = "jonathan";
    private String lastName = "armknecht";
    private String gender = "m";

    @Before
    public void setUp() throws Exception {
        user = new User(username, password, email, firstName, lastName, gender);
        person = new Person(firstName,lastName,gender);
        user.setPersonID(person.getPersonID());
    }

    @After
    public void tearDown() throws Exception {
        user = null;
    }

    @Test
    public void testUserMade() {
        assertEquals(username, user.getUserName());
        assertEquals(password, user.getPassword());
        assertEquals(email, user.getEmail());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(gender, user.getGender());
        assertEquals(person.getPersonID(), user.getPersonID());
        assertNotNull(user.getPersonID());
    }
}