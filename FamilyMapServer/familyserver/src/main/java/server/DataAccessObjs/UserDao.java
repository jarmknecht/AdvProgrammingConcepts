package server.DataAccessObjs;
import shared.Model.User;
import java.sql.*;
//DONE code
/**
 * Created by Jonathan on 2/12/18.
 */

/**
 * Object that interacts with the User DB Table
 */

public class UserDao {
    private Connection conn;

    /**
     * Construct a new DAO based off of a connection received
     * @param conn Connection specific to the user
     */

    public UserDao(Connection conn) {
        this.conn = conn;
    }

    /**
     *Inserts the user along with a user object's data members into the user table
     * @param user user object to add
     * @return True if it was added to the DB false otherwise
     * @throws SQLException
     */

    public boolean insertUser(User user) throws SQLException{
        PreparedStatement statement = null;
        boolean success = false;

        try {
            String mySQL = "Insert into user (username, password, email, first_name, last_name, " +
                            "gender, person_id) values (?, ?, ?, ?, ?, ?, ?)";
            statement = conn.prepareStatement(mySQL);

            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getFirstName());
            statement.setString(5, user.getLastName());
            statement.setString(6, user.getGender());
            statement.setString(7, user.getPersonID());

            if (statement.executeUpdate() == 1) {
                success = true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (statement != null) {
                statement.close();
            }
        }

        return success;
    }

    /**
     * Deletes the user specified from the DB table
     * @param user user to delete
     * @return True if executeUpdate worked false otherwise
     */

    public boolean deleteUser(User user) {
        PreparedStatement statement = null;
        boolean success = false;

        try {
            String mySQL = "Delete from user where username = ?";
            statement = conn.prepareStatement(mySQL);
            statement.setString(1, user.getUserName());

            if (statement.executeUpdate() == 1) {
                success = true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (statement != null) {
                try {
                    statement.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return success;
    }

    /**
     * Deletes all info from the UserTable DB
     */

    public void deleteAll() {
        PreparedStatement statement = null;

        try {
            String mySQL = "Delete from user";
            statement = conn.prepareStatement(mySQL);

            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (statement != null) {
                try {
                    statement.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * Select the row in the DB that corresponds with the username so it
     * will return a result set with all of the user's info
     * @param username username to search for
     * @return user object
     */

    public User getAccountInfoByUsername(String username) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        User user = null;

        try {
            String mySQL = "Select * from user where username = ?";
            statement = conn.prepareStatement(mySQL);
            statement.setString(1, username);
            rs = statement.executeQuery();

            while (rs.next()) {
                user = new User(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5),rs.getString(6));
                user.setPersonID(rs.getString(7));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (statement != null) {
                try {
                    statement.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return user;
    }

}
