package server.DataAccessObjs;
import java.sql.*;
import shared.Model.Event;
import java.util.*;
//DONE with code
/**
 * Created by Jonathan on 2/12/18.
 */

/**
 * Object that interacts with the event DB table
 */

public class EventDao {
    private Connection conn;

    /**
     * Construct a new DAO based off of a connection received
     * @param conn Connection specific to the event
     */

    public EventDao(Connection conn) {
        this.conn = conn;
    }

    /**
     *Inserts the event along with an events data members into the event table
     * @param event event object to add
     * @return True if it was added to the DB false otherwise
     * @throws SQLException
     */

    public boolean createEvent(Event event) throws SQLException {
        PreparedStatement stmt = null;
        boolean close = false;

        try {
            String sql = "insert into event (event_id, descendant, person, latitude, longitude, country, city, event_type, year) values (?,?,?,?,?,?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getDescendant());
            stmt.setString(3, event.getPersonID());
            stmt.setString(4, event.getLatitude());
            stmt.setString(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            if (stmt.executeUpdate() == 1) {
                close = true;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            if (stmt != null){
                stmt.close();
            }
        }

        return close;
    }

    /**
     * Selects from the event table the row that belongs to the eventId
     * @param eventId Id of event looking for
     * @return event that belongs to the Id
     */

    public Event getEventByEventId(String eventId) {
        Event event = null;
        String descendant = null;
        String eventID = null;
        String personId = null;
        String latitude = null;
        String longitude = null;
        String country = null;
        String city = null;
        String eventType = null;
        int year = 0;

        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            String mySQL = "Select * from event where event.event_id = ?";
            statement = conn.prepareStatement(mySQL);
            statement.setString(1, eventId);
            rs = statement.executeQuery();

            while (rs.next()) {
               eventID = rs.getString(1);
               descendant = rs.getString(2);
               personId = rs.getString(3);
               latitude = rs.getString(4);
               longitude = rs.getString(5);
               country = rs.getString(6);
               city = rs.getString(7);
               eventType = rs.getString(8);
               year = rs.getInt(9);
            }
            event = new Event(descendant, personId, latitude, longitude, country, city, eventType, year);
            event.setEventID(eventID);
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

        return event;
    }

    /**
     * Select all of the events that belong to the user's ancestors
     * @param username user name to search for
     * @return a List of all events
     */

    public List<Event> getAllEventsByDescendant(String username) {
        PreparedStatement statement = null;
        ResultSet rs = null;

        Event event = null;
        String descendant = null;
        String eventID = null;
        String personId = null;
        String latitude = null;
        String longitude = null;
        String country = null;
        String city = null;
        String eventType = null;
        int year = 0;
        List<Event> events = new ArrayList<>();

        try {
            String mySQL = "Select * from event where event.descendant = ?";
            statement = conn.prepareStatement(mySQL);
            statement.setString(1, username);
            rs = statement.executeQuery();

            while (rs.next()) {
                eventID = rs.getString(1);
                descendant = rs.getString(2);
                personId = rs.getString(3);
                latitude = rs.getString(4);
                longitude = rs.getString(5);
                country = rs.getString(6);
                city = rs.getString(7);
                eventType = rs.getString(8);
                year = rs.getInt(9);
                event = new Event(descendant, personId, latitude, longitude, country, city, eventType, year);
                event.setEventID(eventID);

                events.add(event);
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

        return events;
    }

    /**
     * Delete from the event DB all entries that belong to the user's ancestors
     * @param username user name to search for
     * @return True if everything was deleted false otherwise
     */

    public boolean deleteByDescendant(String username) {
        PreparedStatement statement = null;
        boolean success = false;

        try {
            String mySQL = "Delete from event where event.descendant = ?";
            statement = conn.prepareStatement(mySQL);
            statement.setString(1, username);

            if (statement.executeUpdate() > 0) { //updated to > 0 from == 1
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
     * Deletes all info from the EventTable DB
     */

    public void deleteAll() {
        PreparedStatement statement = null;

        try {
            String mySQL = "Delete from event";
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
