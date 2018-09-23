package shared.Results;

/**
 * Created by Jonathan on 2/12/18.
 */
//Done with code
/**
 * Makes the message that results from a load request
 */

public class LoadResult {
    String message = null;

    /**
     * Create message based on the result
     * @param message result message
     */

    public LoadResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
