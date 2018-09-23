package shared.Requests;

/**
 * Created by Jonathan on 2/12/18.
 */
//Done with code
/**
 * Handles register requests from the server
 */

public class RegisterRequest {
    private String userName = null;
    private String password = null;
    private String email = null;
    private String firstName = null;
    private String lastName = null;
    private String gender = null;

    /**
     * Constructs a new register request from the information the server received
     * @param userName user's name
     * @param password user's password
     * @param email user's email
     * @param firstName firstName of user
     * @param lastName lastName of user
     * @param gender m for male f for female
     */

    public RegisterRequest(String userName, String password, String email, String firstName, String lastName, String gender) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
