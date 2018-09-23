package shared.Results;

/**
 * Created by Jonathan on 2/12/18.
 */
//Done with code
/**
 * Prints out a message if the DB was cleared correctly or not
 */

public class ClearResult {
    String message = null;

    /**
     * Create message based on result
     * @param message tells if clear worked or didn't work
     */

    public ClearResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    /**
     * Turns the message into a Json string with the info
     * @return message string in Json
     */

    @Override
    public String toString() {
        return "{" +
                "message = '" + message + '\'' +
                '}';
    }
}
