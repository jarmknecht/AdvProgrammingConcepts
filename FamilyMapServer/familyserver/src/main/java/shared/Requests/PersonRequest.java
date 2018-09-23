package shared.Requests;

/**
 * Created by Jonathan on 2/12/18.
 */
//Done with code
/**
 * Handles requests from the server on Person class
 */

public class PersonRequest {
    String personId = null;

    /**
     * Make a new request object with personId
     * @param personId id to make a request on
     */

    public PersonRequest(String personId) {
        this.personId = personId;
    }

    public String getPersonId() {
        return personId;
    }
}
