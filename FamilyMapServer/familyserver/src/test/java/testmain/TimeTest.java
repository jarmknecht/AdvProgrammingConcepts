package testmain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TimeTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void equals() {
        Object o = new ArrayList();
        Object other = null;
        Time time1 = new Time(12, 30);
        Time time2 = new Time(1, 30);
        Time time3 = new Time (12, 34);
        Time time4 = new Time (12, 30);

        assertFalse(time1.equals(o)); //different class
        assertFalse(time1.equals(other)); //object is null
        assertFalse(time1.equals(time2)); //hours differ
        assertFalse(time2.equals(time3)); //hours and mins differ
        assertFalse(time1.equals(time3)); //mins differ
        assertTrue(time1.equals(time4)); //same hours and mins

    }

    @Test
    public void compareTo() {
        Time time1 = new Time(12, 40);
        Time time2 = new Time(1, 30);
        Time time3 = new Time (2, 34);
        Time time4 = new Time (12, 30);
        Time time5 = new Time (12, 25);
        Time time6 = new Time (12, 30);

        assertEquals((int) -1, (int)time2.compareTo(time3)); //compare with this less by hour
        assertEquals((int) 1,(int)time1.compareTo(time2)); // compare with this greater by hour
        assertEquals((int) -1, (int) time5.compareTo(time4)); //compare with min less
        assertEquals((int) 1, (int) time6.compareTo(time5)); //compare with min more
        assertEquals((int) 0, (int) time4.compareTo(time6)); //equal

        assertEquals((int) 0, (int) time4.compareTo(time4)); //compare to itself


    }
}