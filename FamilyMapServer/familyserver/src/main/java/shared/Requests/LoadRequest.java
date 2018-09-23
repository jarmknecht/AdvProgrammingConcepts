package shared.Requests;
import shared.Model.Event;
import shared.Model.Person;
import shared.Model.User;
//DOne with code
/**
 * Created by Jonathan on 2/12/18.
 */

/**
 * Handles the load requests from the server
 */

public class LoadRequest {
    private User[] users = null;
    private Person[] persons = null;
    private Event[] events = null;

    /**
     * Creates a load request object with the data given to it
     * @param users array of users
     * @param persons array of persons/ancestors
     * @param events array of ancestors' events
     */

    public LoadRequest(User[] users, Person[] persons, Event[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public User[] getUsers() {
        return users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public Event[] getEvents() {
        return events;
    }
}
