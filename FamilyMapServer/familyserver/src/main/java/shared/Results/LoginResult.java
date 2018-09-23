package shared.Results;

import com.google.gson.JsonObject;

/**
 * Created by Jonathan on 2/12/18.
 */
//Done with code
/**
 * Makes a Json message based on if the request worked or not
 */

public class LoginResult {
    private String token = null;
    private String userName = null;
    private String personID = null;
    private String message = null;

    /**
     * Makes a login error message
     * @param message what the error was
     */

    public LoginResult(String message) {
        this.message = message;
    }

    /**
     * Create a login result if no error
     * @param authToken authtoken
     * @param userName userName of user
     * @param personID Id of user
     */

    public LoginResult(String authToken, String userName, String personID) {
        this.token = authToken;
        this.userName = userName;
        this.personID = personID;
    }


    public String getAuthToken() {
        return token;
    }

    public String getPersonID() {
        return personID;
    }

    /**
     * Creates the Json string to give back to the server
     * @return Json string
     */

    @Override
    public String toString() {
        if (token != null) {
            return "{" +
                    "Authtoken = '" + token + '\'' +
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
}
