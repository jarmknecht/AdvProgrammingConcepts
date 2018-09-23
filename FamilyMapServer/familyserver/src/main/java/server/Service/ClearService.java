package server.Service;

import java.sql.*;
import server.DataAccessObjs.Transaction;
//DONE code
/**
 * Created by Jonathan on 2/12/18.
 */

/**
 * Uses transactions to ensure that all data from all DB
 * is cleared
 */

public class ClearService {
    private Transaction transaction;
    private Connection conn;

    /**
     * Constructor
     * Makes a new transaction, opens a connection and will
     * make new transactions for all tables
     */

    public ClearService() {
        transaction = new Transaction();
        transaction.openConnection();
        conn = transaction.getConn();

        transaction.userTableAccess();
        transaction.personTableAccess();
        transaction.eventTableAccess();
        transaction.tokenTableAccess();
    }

    /**
     * Deletes all things in every table and does so with the transactions
     * made from the constructor will
     * @return true if transactions go through
     */

    public boolean clearAll() {
        transaction.userAccess.deleteAll();
        transaction.personAccess.deleteAll();
        transaction.eventAccess.deleteAll();
        transaction.tokenAccess.deleteAll();

        transaction.closeConnection(true);

        return true;
    }
}
