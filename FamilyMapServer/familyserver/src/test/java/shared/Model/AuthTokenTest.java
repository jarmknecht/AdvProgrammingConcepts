package shared.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

//test works
/**
 * Created by Jonathan on 2/26/18.
 */
public class AuthTokenTest {
    private AuthToken token;

    @Before
    public void setUp() throws Exception {
        token = new AuthToken();
    }

    @After
    public void tearDown() throws Exception {
        token = null;
    }

    @Test
    public void getToken() throws Exception {
        assertNotNull(token.getToken());
    }

}