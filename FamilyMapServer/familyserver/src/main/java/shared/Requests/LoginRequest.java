package shared.Requests;

/**
 * Created by Jonathan on 2/12/18.
 */
//Done with code
/**
 * Handles login requests from the server
 */

public class LoginRequest {
    private String userName = null;
    private String password = null;

    /**
     * Login constructor that make a new login request object
     * @param userName userName to log in
     * @param password password of user logging in
     */

    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
