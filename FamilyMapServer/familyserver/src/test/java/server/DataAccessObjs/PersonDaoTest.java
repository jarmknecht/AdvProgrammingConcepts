package server.DataAccessObjs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import shared.Model.Person;
import java.sql.SQLException;
import java.util.List;

//done tests all pass!!
/**
 * Created by Jonathan on 2/26/18.
 */
public class PersonDaoTest {
    private Transaction trans;
    private Person person;

    @Before
    public void setUp() throws Exception {
        trans = new Transaction();
        person = new Person("first","last","m");
        person.generatePersonId();
        person.setDescendant("Descendant");
        person.setFather("father");
        person.setMother("mother");
        person.setSpouse("spouse");
    }

    @After
    public void tearDown() throws Exception {
        trans.closeConnection(true);
    }

    @Test
    public void createPerson() throws Exception {
        boolean success = false;
        trans.openConnection();
        trans.personTableAccess();
        try {
            success = trans.personAccess.createPerson(person);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(success);
    }

    @Test
    public void getPersonByPersonId() throws Exception {
        Person testPerson;
        trans.openConnection();
        trans.personTableAccess();
        try {
            trans.personAccess.createPerson(person);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        trans.closeConnection(true);
        trans.openConnection();
        trans.personTableAccess();
        testPerson = trans.personAccess.getPersonByPersonId(person.getPersonID());
        assertNotNull(testPerson);
    }

    @Test
    public void getAllPersonsByUser() throws Exception {
        List<Person> testPersons;
        trans.openConnection();
        trans.personTableAccess();
        try {
            trans.personAccess.createPerson(person);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        trans.closeConnection(true);
        trans.openConnection();
        trans.personTableAccess();
        testPersons = trans.personAccess.getAllPersonsByUser(person.getDescendant());
        assertNotNull(testPersons);
    }

    @Test
    public void deleteByUser() throws Exception {
        Person testPerson;
        boolean success = false;
        trans.openConnection();
        trans.personTableAccess();
        try {
            trans.personAccess.createPerson(person);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        testPerson = trans.personAccess.getPersonByPersonId(person.getPersonID());
        assertNotNull(testPerson);
        success = trans.personAccess.deleteByUser(person.getDescendant());
        testPerson = trans.personAccess.getPersonByPersonId(person.getPersonID());
        assertNull(testPerson.getPersonID());
    }

    @Test
    public void deleteAll() throws Exception {
        Person testPerson;
        boolean success = false;
        trans.openConnection();
        trans.personTableAccess();
        try {
            trans.personAccess.createPerson(person);
            person.generatePersonId();
            trans.personAccess.createPerson(person);
            person.generatePersonId();
            trans.personAccess.createPerson(person);
            person.generatePersonId();
            trans.personAccess.createPerson(person);
            person.generatePersonId();
            trans.personAccess.createPerson(person);
            person.generatePersonId();
            trans.personAccess.createPerson(person);
            person.generatePersonId();
            trans.personAccess.createPerson(person);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        trans.closeConnection(true);
        trans.openConnection();
        trans.personTableAccess();
        trans.personAccess.deleteAll();
        testPerson = trans.personAccess.getPersonByPersonId(person.getPersonID());
        assertNull(testPerson.getPersonID());
    }

}