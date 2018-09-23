package server.Service;
import java.sql.Connection;

import server.DataAccessObjs.Transaction;
import server.DataAccessObjs.FillDao;
import shared.Model.User;

//DONE with code

/**
 * Created by Jonathan on 2/12/18.
 */

/**
 * This class is in charge of filling up the DB of a user with
 * number of generations provided
 */

public class FillService {
    private Transaction transaction;
    private Connection conn;
    private int numOfPeople;
    private int numOfEvents;

    /**
     * Constructor for Fill service class
     * Starts a new transaction and opens a connection
     * transactions will have to deal with user, event and person DB tables
     */

    public FillService(){
        transaction = new Transaction();
        transaction.openConnection();
        conn = transaction.getConn();

        transaction.userTableAccess();
        transaction.personTableAccess();
        transaction.eventTableAccess();
        numOfEvents = 0;
        numOfPeople = 0;
    }

    /**
     * Fills a certain user's family tree with a certain number
     * of generations
     * @param username user's name to add generations to
     * @param generations number of generations to give to user
     * @return true if user exists and user was fill, false otherwise
     */

    public boolean fill(String username, int generations) {
        User user = transaction.userAccess.getAccountInfoByUsername(username);

        if (user == null) {
            return false;
        }
        else {
            transaction.personAccess.deleteByUser(user.getUserName());
            transaction.eventAccess.deleteByDescendant(user.getUserName());
            FillDao filler = new FillDao();
            transaction.closeConnection(true);
            filler.importData(user.getUserName(), generations);
            numOfPeople = filler.getNumOfPeople();
            numOfEvents = filler.getNumOfEvents();

            return true;
        }
    }

    /**
     * Rollsback with the transaction didn't work correctly
     */

    public void closeConnError() {
        transaction.closeConnection(false);
    }

    public int getNumOfPeople() {
        return numOfPeople;
    }

    public int getNumOfEvents() {
        return numOfEvents;
    }
}
