package model;


import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import shared.Model.Person;
import shared.Model.Event;
import static org.junit.Assert.*;

public class FamilyTreeTest {
    private FamilyTree familyTree;
    private Map<String, Person> personMap;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        //don't want to tearDown cause each one will
        //build off the next one
    }

    @Test
    public void testFamilyTree() {

        Person testPerson = new Person("Jonathan", "Armknecht", "m");
        familyTree = new FamilyTree(testPerson, "THIS IS A TEST");
//Tests family relations between father, mother and spouse
        assertSame("John", familyTree.getFamily().get(0));
        assertSame("Jill", familyTree.getFamily().get(1));
        assertSame("Melissa", familyTree.getFamily().get(2));
//Tests the the events are sorted and collected right
        Event first = (Event) familyTree.getEvents().get(0);
        Event last = (Event) familyTree.getEvents().get(3);
        assertSame("birth", first.getEventType());
        assertSame("death", last.getEventType());

//Tests that the children are set correctly
        assertTrue(familyTree.getChildren().contains("Joe"));
        assertTrue(familyTree.getChildren().contains("Fred"));
        assertTrue(familyTree.getChildren().contains("Bill"));


//Tests for search for people and events
        List<Object> searchFamily = familyTree.getFamily();
        Set<String> searchChildren = familyTree.getChildren();
        List<Object> searchEvents = familyTree.getEvents();
        boolean found = false;

        for (int i = 0; i < searchFamily.size(); i++) {
            String name = searchFamily.get(i).toString().toLowerCase();
            for (int j = 0; j < name.length(); j++) {
                if (name.charAt(j) == 'j') {
                    found = true;
                }
            }
        }
        //Is J found in string of family members
        assertTrue(found);
        found = false;
        for (int i = 0; i < searchFamily.size(); i++) {
            String name = searchFamily.get(i).toString().toLowerCase();
            for (int j = 0; j < name.length(); j++) {
                if (name.charAt(j) == 'z') {
                    found = true;
                }
            }
        }
        //is z in family members
        assertFalse(found);

        //Is j in childeren?
        found = false;

        for (int i = 0; i < searchChildren.size(); i++) {
            Iterator iter = searchChildren.iterator();

            while (iter.hasNext()) {
                String name = iter.next().toString().toLowerCase();
                for (int j = 0; j < name.length(); j++) {
                    if (name.charAt(j) == 'j') {
                        found = true;
                    }
                }
            }
        }
        assertTrue(found);
        found = false;

        for (int i = 0; i < searchChildren.size(); i++) {
            Iterator iter = searchChildren.iterator();

            while (iter.hasNext()) {
                String name = iter.next().toString().toLowerCase();
                for (int j = 0; j < name.length(); j++) {
                    if (name.charAt(j) == 'z') {
                        found = true;
                    }
                }
            }
        }
        assertFalse(found);
        found = false;
        //Searching through events
        for (int i = 0; i < searchEvents.size(); i++) {
            String events = searchEvents.get(i).toString().toLowerCase();
            for (int j = 0; j < events.length(); j++) {
                if (events.charAt(j) == 'b') {
                    found = true;
                }
            }
        }
        //Is b found in eventTypes yes birth
        assertTrue(found);
        found = false;
        for (int i = 0; i < searchEvents.size(); i++) {
            String events = searchEvents.get(i).toString().toLowerCase();
            for (int j = 0; j < events.length(); j++) {
                if (events.charAt(j) == 'z') {
                    found = true;
                }
            }
        }
        //is z found in eventTypes no
        assertFalse(found);
    }

}