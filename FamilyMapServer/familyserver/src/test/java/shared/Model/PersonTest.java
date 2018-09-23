package shared.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

//test works
/**
 * Created by Jonathan on 2/26/18.
 */
public class PersonTest {
    private Person person;
    private String firstName = "jonathan";
    private String lastName = "armknecht";
    private String gender = "m";

    @Before
    public void setUp() throws Exception {
        person = new Person(firstName, lastName, gender);
    }

    @After
    public void tearDown() throws Exception {
        person = null;
    }

    @Test
    public void testPersonMade() {
        assertEquals(firstName, person.getFirstName());
        assertEquals(lastName, person.getLastName());
        assertEquals(gender, person.getGender());
    }

}