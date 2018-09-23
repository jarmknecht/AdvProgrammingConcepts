package server.Service;

import java.sql.*;

import server.DataAccessObjs.Transaction;
import server.DataAccessObjs.FillDao;
import shared.Model.Person;
import shared.Model.AuthToken;
import shared.Model.User;
//Done with code
/**
 * Created by Jonathan on 2/12/18.
 */

/**
 * Service that is in charge of helping a user register
 */

public class RegisterService {
    private Transaction transaction;
    private Connection conn;

    /**
     * Constructor for class
     * opens a new transaction and connection
     */

    public RegisterService() {
        transaction = new Transaction();
        transaction.openConnection();
        conn = transaction.getConn();
    }

    /**
     * Take a user's information and registers them into the user DB
     * @param user user's info to register
     */

    public void registerUser(User user) {
        boolean success = false;

        try {
            Person person = new Person(user.getFirstName(), user.getLastName(), user.getGender());
            user.setPersonID(person.getPersonID());

            transaction.userTableAccess();
            transaction.tokenTableAccess();

            success = transaction.userAccess.insertUser(user);

            AuthToken token = new AuthToken();
            success = transaction.tokenAccess.addToken(token, user.getUserName(), user.getPersonID());

            transaction.closeConnection(success);

            FillDao filler = new FillDao();
            filler.importData(user.getUserName(), 4);
        }
        catch (SQLException e) {
            transaction.closeConnection(false);
            e.printStackTrace();
        }
    }

    /**
     * Get the token of specified user
     * @param username user of whose authToken to get
     * @return authToken
     */

    public AuthToken getTokenOfUser(String username) {
        AuthToken token = null;
        transaction.openConnection();
        transaction.tokenTableAccess();
        token = transaction.tokenAccess.getTokenByUserName(username);
        transaction.closeConnection(true);
        return token;
    }

    /**
     * Checks to see if the user trying to register is already in the DB
     * and if so doesn't add it
     * @param username name to look for
     * @return false if name not in DB true if it is
     */

    public boolean checkForUser(String username) {
        boolean found = false;
        User user = null;

        transaction.userTableAccess();
        user = transaction.userAccess.getAccountInfoByUsername(username);

        if (user != null) {
            found = true;
            transaction.closeConnection(true);
        }

        return found;
    }
}
