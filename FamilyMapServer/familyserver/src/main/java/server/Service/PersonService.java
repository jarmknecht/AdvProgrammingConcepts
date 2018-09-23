package server.Service;

import java.sql.*;
import java.util.*;

import server.DataAccessObjs.Transaction;
import shared.Model.Person;
//Done with code
/**
 * Created by Jonathan on 2/12/18.
 */

/**
 * Class is in charge of performing methods that allow one to
 * receive the data members of the person class
 */

public class PersonService {
    private Transaction transaction;
    private Connection conn;

    /**
     * Constructor for person service
     * sets up new transactions on user, person and token tables
     */

    public PersonService() {
        transaction = new Transaction();
        transaction.openConnection();
        conn = transaction.getConn();

        transaction.userTableAccess();
        transaction.personTableAccess();
        transaction.tokenTableAccess();
    }

    /**
     * Checks to see in the user in authentic or not based on if their token
     * is part of the token DB
     * @param token authToken to look for
     * @return true if the user is authentic
     */

    public boolean authenUser(String token) {
        boolean success = false;
        success = transaction.tokenAccess.autheticationToken(token);
        return success;
    }

    /**
     * Looks for a person and their events by the ancestor's personId
     * @param personId Id of ancestor to look for in DB
     * @return Person found or null if not found
     */

    public Person getPersonById(String personId) {
        Person person = null;
        person = transaction.personAccess.getPersonByPersonId(personId);
        person.setEvents(null);
        transaction.closeConnection(true);
        return person;
    }

    /**
     * Looks in DB to see if the person exists good to call during last method
     * @param personId who to look for
     * @return true if found person false otherwise
     */

    /*public boolean checkForPersonbyId(String personId) {
        boolean success = false;
        success = transaction.personAccess.checkForPersonByPersonId(personId);
        return success;
    }*/

    /**
     * Finds all of the ancestor's related to the user
     * @param token user's token
     * @return List of people
     */

    public List<Person> getAllPeopleByUser(String token) {
        List<Person> people = null;
        String username = transaction.tokenAccess.getUserNameByToken(token);
        people = transaction.personAccess.getAllPersonsByUser(username);
        transaction.closeConnection(true);
        return people;
    }

    /**
     * Looks if a user exists with a unique token
     * @param token authToken to look for
     * @return username
     */

    public String getUserByToken(String token) {
        String username = null;
        username = transaction.tokenAccess.getUserNameByToken(token);
        return username;
    }

    /**
     * Rollback all of the transactions done
     */

    public void errorClosConnection(){
        transaction.closeConnection(true);
    }
}
