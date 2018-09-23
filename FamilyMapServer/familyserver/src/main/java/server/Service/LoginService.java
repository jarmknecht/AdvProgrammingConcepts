package server.Service;
import java.sql.*;
import server.DataAccessObjs.Transaction;
import shared.Model.AuthToken;
import shared.Model.User;


//DONE with stubs
/**
 * Created by Jonathan on 2/12/18.
 */

/**
 * Class is in charged of logging in a user
 * by checking if the user exists
 */

public class LoginService {
    private Transaction transaction;
    private Connection conn;

    /**
     * Makes a new Transaction on the user and token tables
     */

    public LoginService() {
        transaction = new Transaction();
        transaction.openConnection();
        conn = transaction.getConn();

        transaction.userTableAccess();
        transaction.tokenTableAccess();
    }

    /**
     * Checks to see if the user is in the DB if they are returns user
     * @param username user to look for
     * @return null or the user information
     */

    public User checkIfUserExists(String username) {
        User user = null;
        user = transaction.userAccess.getAccountInfoByUsername(username);
        return user;
    }

    /**
     * Closes the connection if the incorrect username or password
     * was given
     */

    public void closeConnOnBadNameOrPassword() {
        transaction.closeConnection(true);
    }

    /**
     * Logs in the user if user exists in DB
     * @param user name to look for to login
     * @param token authToken of user
     */

    public void loginUser(User user, AuthToken token) {
        if (transaction.tokenAccess.checkForUser(user.getUserName())) {
            transaction.tokenAccess.updateAuthToken(user.getUserName(), token.getToken());
        }
        else {
            try {
                transaction.tokenAccess.addToken(token, user.getUserName(), user.getPersonID());
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        transaction.closeConnection(true);
    }
}
