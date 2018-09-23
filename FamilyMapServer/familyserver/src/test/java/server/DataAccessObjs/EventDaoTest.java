package server.DataAccessObjs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import shared.Model.*;

import static org.junit.Assert.*;
//tests all work
/**
 * Created by Jonathan on 2/26/18.
 */
public class EventDaoTest {
    private Transaction trans;
    Event event;

    @Before
    public void setUp() throws Exception {
        trans = new Transaction();
        event = new Event("descendant","123","123","123","country","city","type",2000);
        event.generateEventId();
    }

    @After
    public void tearDown() throws Exception {
        trans.closeConnection(true);
    }

    @Test
    public void createEvent() throws Exception {
        boolean success = false;
        trans.openConnection();
        trans.eventTableAccess();
        try {
            success = trans.eventAccess.createEvent(event);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(success);
    }

    @Test
    public void getEventByEventId() throws Exception {
        Event testEvent;
        boolean success = false;
        trans.openConnection();
        trans.eventTableAccess();
        try {
            success = trans.eventAccess.createEvent(event);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        trans.closeConnection(true);
        trans.openConnection();
        trans.eventTableAccess();
        testEvent = trans.eventAccess.getEventByEventId(event.getEventID());
        assertNotNull(testEvent);
    }

    @Test
    public void getAllEventsByDescendant() throws Exception {
        List<Event> testEvents;
        trans.openConnection();
        trans.eventTableAccess();
        try {
            trans.eventAccess.createEvent(event);
            event.generateEventId();
            trans.eventAccess.createEvent(event);
            event.generateEventId();
            trans.eventAccess.createEvent(event);
            event.generateEventId();
            trans.eventAccess.createEvent(event);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        trans.closeConnection(true);
        trans.openConnection();
        trans.eventTableAccess();
        testEvents = trans.eventAccess.getAllEventsByDescendant(event.getDescendant());
        assertNotNull(testEvents);
    }

}