package server.DataAccessObjs;

import java.sql.*;

import shared.Model.AuthToken;

//DONE code

/**
 * Created by Jonathan on 2/12/18.
 */


/**
 * Class is used to talk to the Database through SQL and find
 * a specific person using the auth token
 */
public class AuthTokenDao {
    private Connection conn;

    /**
     * Construct a new DAO based off of a connection received
     * @param conn Connection specific to the token
     */
    public AuthTokenDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts the token, username and personId into authentication table
     * @param token the unique auth token user received
     * @param username name of user
     * @param personId id of user
     * @return Returns true or false if the token was added or not
     * @throws SQLException
     */

    public boolean addToken(AuthToken token, String username, String personId) throws SQLException {
        PreparedStatement statement = null;
        boolean success = false;

        try {
            String mySQL = "Insert into authentication (authToken, username, personID) values (?, ?, ?)";
            statement = conn.prepareStatement(mySQL);
            statement.setString(1, token.getToken());
            statement.setString(2, username);
            statement.setString(3, personId);

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
     * Selects from the authentication table the user with the username
     * @param username name of user
     * @return Authtoken that belongs to user
     */

    public AuthToken getTokenByUserName(String username) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        AuthToken token = new AuthToken();

        try {
            String mySQL = "Select * from authentication where authentication.username = ?";
            statement = conn.prepareStatement(mySQL);
            statement.setString(1, username);
            rs = statement.executeQuery();

            while (rs.next()) {
                token.setToken(rs.getString(1));
                token.setUserName(rs.getString(2));
                token.setPersonID(rs.getString(3));
            }
        }
        catch (Exception e) {
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

        return token;
    }

    /**
     * Selects from the authentication table the user with authtoken
     * @param token authtoken belonging to user
     * @return username
     */

    public String getUserNameByToken(String token) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        String name = null;

        try {
            String mySQL = "Select username from authentication where authentication.authToken = ?";
            statement = conn.prepareStatement(mySQL);
            statement.setString(1, token);
            rs = statement.executeQuery();

            while (rs.next()) {
                name = rs.getString(1);
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

        return name;
    }

    /**
     * Checks to see if everything can be selected from the auth table based on authtoken
     * So should return the username and password associated with the authtoken
     * @param token unique token to look for
     * @return True if ResultSet is a whole row False if not
     */
    public boolean autheticationToken(String token) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        boolean success = false;

        try {
            String mySQL = "Select * from authentication where authentication.authToken = ?";
            statement = conn.prepareStatement(mySQL);
            statement.setString(1, token);
            rs = statement.executeQuery();

            if (!rs.next()) {
                success = false;
            }
            else {
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
            if (rs != null) {
                try {
                    rs.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return success;
    }

    /**
     * Updates the row where username and token passed to it are the same
     * @param username user name to update
     * @param token token to update
     * @return True if Update worked False if otherwise
     */

    public boolean updateAuthToken(String username, String token) {
        PreparedStatement statement = null;
        boolean success = false;

        try {
            String mySQL = "Update authentication set authToken = ? where username = ?";
            statement = conn.prepareStatement(mySQL);
            statement.setString(1, token);
            statement.setString(2, username);

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
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return success;
    }

    /**
     * Selects the username password and authtoken if the username exists in DB
     * @param username name to look for
     * @return True if the user exists false if they aren't in DB
     */

    public boolean checkForUser(String username) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        boolean success = false;

        try {
            String mySQL = "Select * from authentication where authentication.username = ?";
            statement = conn.prepareStatement(mySQL);
            statement.setString(1, username);
            rs = statement.executeQuery();

            if (!rs.next()) {
                success = false;
            }
            else {
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
            if (rs != null) {
                try {
                    rs.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return success;
    }

    /**
     * Deletes all info from the AuthTable DB
     */

    public void deleteAll() {
        PreparedStatement statement = null;

        try {
            String mySQL = "Delete from authentication";
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
}
