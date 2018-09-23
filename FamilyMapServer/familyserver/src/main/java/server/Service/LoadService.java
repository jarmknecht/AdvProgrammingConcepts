package server.Service;
import java.sql.*;
import server.DataAccessObjs.Transaction;
import shared.Model.User;
import shared.Model.Person;
import shared.Model.Event;

//DONE with code
/**
 * Created by Jonathan on 2/12/18.
 */

/**
 * This service is in charge of loading
 * an array of users, people and events that TAs will load
 * into program
 */

public class LoadService {
    private Transaction transaction;
    private Connection conn;
    private int numOfUsers;
    private int numOfPeople;
    private int numOfEvents;

    /**
     * Constructor of the class
     * Opens a new connection and starts transactions on all DB tables
     */

    public LoadService() {
        transaction = new Transaction();
        transaction.openConnection();
        conn = transaction.getConn();

        transaction.userTableAccess();
        transaction.personTableAccess();
        transaction.eventTableAccess();
        transaction.tokenTableAccess();
        numOfEvents = 0;
        numOfPeople = 0;
        numOfUsers = 0;
    }

    /**
     * First deletes everything in DB then inserts the parameter arrays
     * into the DB and keeps track of how many were added
     * @param users array of users to add to user DB table
     * @param people array of people to add to person DB table
     * @param events array of events to add to event DB table
     */

    public void loadData(User[] users, Person[] people, Event[] events) {
        transaction.userAccess.deleteAll();
        transaction.personAccess.deleteAll();
        transaction.eventAccess.deleteAll();
        transaction.tokenAccess.deleteAll();

        for (User user : users) {
            try {
                transaction.userAccess.insertUser(user);
                numOfUsers++;
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        for (Person person : people) {
            person.setEvents(null);
            try {
                transaction.personAccess.createPerson(person);
                numOfPeople++;
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for (Event event : events) {
            try {
                transaction.eventAccess.createEvent(event);
                numOfEvents++;
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        transaction.closeConnection(true);
    }

    public int getNumOfUsers() {
        return numOfUsers;
    }

    public int getNumOfPeople() {
        return numOfPeople;
    }

    public int getNumOfEvents() {
        return numOfEvents;
    }
}
