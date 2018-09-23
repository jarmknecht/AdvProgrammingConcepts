package shared.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

//test passed
/**
 * Created by Jonathan on 2/26/18.
 */
public class EventTest {
    private Event event;
    private String descendant = "Bill";
    private String person = "person";
    private String latitude = "77";
    private String longitude = "5";
    private String country = "USA";
    private String city = "Bountiful";
    private String eventType = "Birth";
    private int year = 2018;

    @Before
    public void setUp() throws Exception {
        event = new Event(descendant, person, latitude, longitude, country,
                 city, eventType,year);
    }

    @After
    public void tearDown() throws Exception {
        event = null;
    }

    @Test
    public void testMadeEvent() {
        assertEquals(descendant, event.getDescendant());
        assertEquals(person, event.getPersonID());
        assertEquals(latitude, event.getLatitude());
        assertEquals(longitude, event.getLongitude());
        assertEquals(country, event.getCountry());
        assertEquals(city, event.getCity());
        assertEquals(eventType, event.getEventType());
        assertEquals(year, event.getYear());
        assertNotNull(event.getEventID());
    }

}