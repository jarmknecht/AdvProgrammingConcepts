package server.DataAccessObjs;

import org.junit.*;

import java.sql.SQLException;

import shared.Model.*;

import static org.junit.Assert.*;
//tests work!!
/**
 * Created by Jonathan on 2/26/18.
 */
public class AuthTokenDaoTest {

    private Transaction trans;
    private AuthToken token;

    @Before
    public void setUp() {
        trans = new Transaction();
        token = new AuthToken();

    }

    @After
    public void tearDown() {
        trans.closeConnection(true);
    }


    @Test
    public void addToken() throws Exception {
        boolean success = false;
        trans.openConnection();
        trans.tokenTableAccess();
        try {
            success = trans.tokenAccess.addToken(token, "username", "123");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(success);
    }

    @Test
    public void getTokenByUserName() throws Exception {
        boolean success = false;
        AuthToken testToken;
        trans.openConnection();
        trans.tokenTableAccess();
        try {
            success = trans.tokenAccess.addToken(token, "username", "123");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        testToken = trans.tokenAccess.getTokenByUserName("username");
        assertNotNull(testToken);
    }

    @Test
    public void getUserNameByToken() throws Exception {
        boolean success = false;
        String username;
        trans.openConnection();
        trans.tokenTableAccess();
        try {
            success = trans.tokenAccess.addToken(token, "username", "123");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        username = trans.tokenAccess.getUserNameByToken(token.getToken());
        assertEquals("username", username);
    }

    @Test
    public void autheticationToken() throws Exception {
        boolean success = false;
        trans.openConnection();
        trans.tokenTableAccess();
        try {
            success = trans.tokenAccess.addToken(token, "username", "123");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        success = trans.tokenAccess.autheticationToken(token.getToken());
        assertTrue(success);
        success = trans.tokenAccess.autheticationToken("NOT A TOKEN");
        assertFalse(success);
    }

    @Test
    public void updateAuthToken() throws Exception {
        boolean success = false;
        String oldToken = null;
        AuthToken newToken;
        trans.openConnection();
        trans.tokenTableAccess();
        try {
            success = trans.tokenAccess.addToken(token, "username", "123");
            oldToken = token.getToken();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        token.generateToken();
        success = trans.tokenAccess.updateAuthToken("username", token.getToken());
        newToken = trans.tokenAccess.getTokenByUserName("username");
        assertNotEquals(newToken.getToken(), oldToken);
    }
}