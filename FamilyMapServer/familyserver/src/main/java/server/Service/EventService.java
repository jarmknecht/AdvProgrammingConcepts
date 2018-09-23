package server.Service;

import java.sql.Connection;
import java.util.*;
import server.DataAccessObjs.Transaction;
import shared.Model.Event;
//DONE code
/**
 * Created by Jonathan on 2/12/18.
 */

/**
 * This class performs the transactions on the Event DB
 */

public class EventService {
    private Transaction transaction;
    private Connection conn;

    /**
     * Constructor
     * Makes a new transaction, opens a connection and will
     * make new transactions for user event and token tables
     */

    public EventService() {
        transaction = new Transaction();
        transaction.openConnection();
        conn = transaction.getConn();

        transaction.userTableAccess();
        transaction.eventTableAccess();
        transaction.tokenTableAccess();
    }

    /**
     * Checks through a transaction if the user's token is in the DB
     * @param token authToken owned by the user
     * @return True if transaction worked false otherwise
     */

    public boolean authenticateUser(String token) {
        boolean success = false;
        success = transaction.tokenAccess.autheticationToken(token);
        return success;
    }

    /**
     * Uses a transaction to obtain the user's name from their token
     * @param token authTokenId
     * @return a string with the user's name
     */

    public String getUserByToken(String token) {
        String username = null;
        username = transaction.tokenAccess.getUserNameByToken(token);
        return username;
    }

    /**
     * Uses a transaction to obtain an event base on its Id
     * @param eventId the Id of the event
     * @return return the event
     */

    public Event getEventById(String eventId) {
        Event event = null;
        event = transaction.eventAccess.getEventByEventId(eventId);
        transaction.closeConnection(true);
        return event;
    }

    /**
     * Uses a transaction to obtain all the events connected to
     * a user's ancestors
     * @param token the authToken owned by user
     * @return List of all ancestors events
     */

    public List<Event> getAllEventsByUser(String token) {
        List<Event> events = null;
        String username = transaction.tokenAccess.getUserNameByToken(token);
        events = transaction.eventAccess.getAllEventsByDescendant(username);
        transaction.closeConnection(true);
        return events;
    }

    /**
     * Called to help close a connection if there was an error closing it before
     */

    public void errorClosingConnection() {
        transaction.closeConnection(true);
    }
}
