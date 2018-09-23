package server.DataAccessObjs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

//test works, other methods in transaction are tested in other DAO tests

/**
 * Created by Jonathan on 2/26/18.
 */
public class TransactionTest {
    private Transaction trans;

    @Before
    public void setUp() throws Exception {
        trans = new Transaction();
        trans.openConnection();
    }

    @After
    public void tearDown() throws Exception {
        trans.closeConnection(false);
    }

    @Test
    public void getConn() throws Exception {
        assertNotNull(trans.getConn());
    }

}