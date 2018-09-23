package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class FilterTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }
//If the filter is off it will not show up on the map

    @Test
    public void testMaleEvent() {
        //Test turning off male events
        boolean filterApplied = false;

        Filter filter = new Filter("Male Events");
        filter.setFilterOn(true);


        assertTrue(filter.isFilterOn());
        filter.setFilterOn(false);
        assertFalse(filter.isFilterOn());
        ArrayList<String> newEvents = filter.getEventsOnOrOff();
        newEvents.remove("Male Events");

        if (!newEvents.contains("Male Events")) {
            filterApplied = true;
        }

        assertTrue(filterApplied);

    }

    @Test
    public void testFemaleEvent() {

        //Test turning off female events
        boolean filterApplied = false;

        Filter filter = new Filter("Female Events");
        filter.setFilterOn(true);


        assertTrue(filter.isFilterOn());
        filter.setFilterOn(false);
        assertFalse(filter.isFilterOn());
        ArrayList<String> newEvents = filter.getEventsOnOrOff();
        newEvents.remove("Female Events");

        if (!newEvents.contains("Female Events")) {
            filterApplied = true;
        }

        assertTrue(filterApplied);

    }

    @Test
    public void testFathersSideEvent() {
        //test fathersside

        boolean filterApplied = false;

        Filter filter = new Filter("Father's Events");
        filter.setFilterOn(true);


        assertTrue(filter.isFilterOn());
        filter.setFilterOn(false);
        assertFalse(filter.isFilterOn());
        ArrayList<String> newEvents = filter.getEventsOnOrOff();
        newEvents.remove("Father's Events");

        if (!newEvents.contains("Father's Events")) {
            filterApplied = true;
        }

        assertTrue(filterApplied);

    }

    @Test
    public void testMothersSideEvent() {
        //Test turning off mother's side
        boolean filterApplied = false;

        Filter filter = new Filter("Mother's Events");
        filter.setFilterOn(true);


        assertTrue(filter.isFilterOn());
        filter.setFilterOn(false);
        assertFalse(filter.isFilterOn());
        ArrayList<String> newEvents = filter.getEventsOnOrOff();
        newEvents.remove("Mother's Events");

        if (!newEvents.contains("Mother's Events")) {
            filterApplied = true;
        }

        assertTrue(filterApplied);

    }

    @Test
    public void testBirthEvent() {

        //Test turning off birth

        boolean filterApplied = false;

        Filter filter = new Filter("Birth Events");
        filter.setFilterOn(true);


        assertTrue(filter.isFilterOn());
        filter.setFilterOn(false);
        assertFalse(filter.isFilterOn());
        ArrayList<String> newEvents = filter.getEventsOnOrOff();
        newEvents.remove("Birth Events");

        if (!newEvents.contains("Birth Events")) {
            filterApplied = true;
        }

        assertTrue(filterApplied);

    }

    @Test
    public void testDeathEvent() {

        //Test turning off death

        boolean filterApplied = false;

        Filter filter = new Filter("Death Events");
        filter.setFilterOn(true);


        assertTrue(filter.isFilterOn());
        filter.setFilterOn(false);
        assertFalse(filter.isFilterOn());
        ArrayList<String> newEvents = filter.getEventsOnOrOff();
        newEvents.remove("Death Events");

        if (!newEvents.contains("Death Events")) {
            filterApplied = true;
        }

        assertTrue(filterApplied);

    }

    @Test
    public void testMarriageEvent() {

        //Test turning off marriage
        boolean filterApplied = false;

        Filter filter = new Filter("Marriage Events");
        filter.setFilterOn(true);


        assertTrue(filter.isFilterOn());
        filter.setFilterOn(false);
        assertFalse(filter.isFilterOn());
        ArrayList<String> newEvents = filter.getEventsOnOrOff();
        newEvents.remove("Marriage Events");

        if (!newEvents.contains("Marriage Events")) {
            filterApplied = true;
        }

        assertTrue(filterApplied);

    }

    @Test
    public void testBaptismEvent() {
        //Test turning off baptism

        boolean filterApplied = false;

        Filter filter = new Filter("Baptism Events");
        filter.setFilterOn(true);


        assertTrue(filter.isFilterOn());
        filter.setFilterOn(false);
        assertFalse(filter.isFilterOn());
        ArrayList<String> newEvents = filter.getEventsOnOrOff();
        newEvents.remove("Baptism Events");

        if (!newEvents.contains("Baptism Events")) {
            filterApplied = true;
        }

        assertTrue(filterApplied);
    }

    @Test
    public void testWithAllOff() {
        boolean filterApplied = false;

        Filter filter = new Filter("TestALLOFF Events");
        filter.setFilterOn(true);


        assertTrue(filter.isFilterOn());
        filter.setFilterOn(false);
        assertFalse(filter.isFilterOn());
        ArrayList<String> newEvents = filter.getEventsOnOrOff();
        filter.getEventsOnOrOff().clear();

        if (!newEvents.contains("TestALLOFF Events")) {
            filterApplied = true;
        }

        assertTrue(filterApplied);
    }


}