package server.DataAccessObjs;
import java.sql.*;

//Done code
/**
 * Created by Jonathan on 2/13/18.
 */

/**
 * A class that makes all transactions so as to avoid duplicate code
 * when calling a transaction on any of the DB
 */

public class Transaction {

    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private Connection conn;
    public UserDao userAccess;
    public PersonDao personAccess;
    public EventDao eventAccess;
    public AuthTokenDao tokenAccess;

    /**
     * Opens a new connection with a certain url
     */

    public void openConnection() {
        //System.out.println("opened\n");
        try {
            final String CONNECTION_URL = "jdbc:sqlite:familymapserver.sqlite";
            conn = DriverManager.getConnection(CONNECTION_URL);
            conn.setAutoCommit(false);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the connection and commits or rolls back the transactions
     * @param commit if true will commit transactions if not will rollback
     */

    public void closeConnection(boolean commit) {
        //System.out.println("closed\n");
        try {
            if (conn != null) {
                if (commit) {
                    conn.commit();
                } else {
                    conn.rollback();
                }
                conn.close();
                conn = null;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    conn.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Returns connection
     * @return conn data member
     */

    public Connection getConn() {
        return conn;
    }

    /**
     * Makes new userTable access through a UserDao
     */

    public void userTableAccess() {
        this.userAccess = new UserDao(conn);
    }

    /**
     * Makes new personTable access through a PersonDao
     */

    public void personTableAccess() {
        this.personAccess = new PersonDao(conn);
    }

    /**
     * Makes new eventTable access through a EventDao
     */

    public void eventTableAccess() {
        this.eventAccess = new EventDao(conn);
    }

    /**
     * Makes new tokenTable access through an AuthTokenDao
     */

    public void tokenTableAccess() {
        this.tokenAccess = new AuthTokenDao(conn);
    }

}
