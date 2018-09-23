package shared.Model;

//DONE with code
/**
 * Created by Jonathan on 2/12/18.
 */

/**
 * Class contains the data for a user of the application
 * including username, password, email etc
 */

public class User {
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String personID;

    /**
     * Constructs a new user for use with the app
     * @param userName name user will be known by it is unique
     * @param password password of user
     * @param email user's email
     * @param firstName user's first name
     * @param lastName user's last name
     * @param gender user's gender m or f
     */

    public User(String userName, String password, String email, String firstName, String lastName, String gender) {

        setUserName(userName);
        setPassword(password);
        setEmail(email);
        setFirstName(firstName);
        setLastName(lastName);
        setGender(gender);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }


    /**
     * Creates a string with all info of the user
     * @return string with user's data
     */

    @Override
    public String toString() {
        return "User{" +
                "userName = '" + userName + '\'' +
                ", password = '" + password + '\'' +
                ", email = '" + email + '\'' +
                ", firstName = '" + firstName + '\'' +
                ", lastName = '" + lastName + '\'' +
                ", gender = '" + gender + '\'' +
                ", personID = '" + personID + '\'' +
                '}';
    }

}
