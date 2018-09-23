package shared.Results;
import com.google.gson.JsonObject;
/**
 * Created by Jonathan on 2/12/18.
 */
//DOne with code
/**
 * Creates the Json result string to give back to server
 */

public class RegisterResult {
    private String token = null;
    private String userName = null;
    private String personID = null;
    private String message = null;

    /**
     * Makes the error message if request fails
     * @param message error message
     */

    public RegisterResult(String message) {
        this.message = message;
    }

    public RegisterResult(String authToken, String userName, String personId) {
        this.token = authToken;
        this.userName = userName;
        this.personID = personId;
    }

    /**
     * Creates the Json message of result
     * @return Json string
     */

    @Override
    public String toString() {
        if (token != null) {
            return "{" +
                    "authToken = '" + token + '\'' +
                    ", userName = '" + userName + '\'' +
                    ", personID = '" + personID + '\'' +
                    '}';
        }
        else {
            JsonObject object = new JsonObject();
            object.addProperty("message", message);
            return object.toString();
        }
    }

    public String getAuthToken() {
        return token;
    }

    public void setAuthToken(String authToken) {
        this.token = authToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersonId() {
        return personID;
    }

    public void setPersonId(String personId) {
        this.personID = personId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
