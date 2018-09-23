package shared.Results;

/**
 * Created by Jonathan on 2/12/18.
 */
//Done with code
/**
 * Makes a message that has the result if the fill worked or not
 */

public class FillResult {
    String message = null;

    /**
     * create message based on result
     * @param message the result message
     */

    public FillResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    /**
     * Makes the message in Json
     * @return Json string message
     */

    @Override
    public String toString() {
        return "{" +
                "message = '" + message + '\'' +
                '}';
    }
}
