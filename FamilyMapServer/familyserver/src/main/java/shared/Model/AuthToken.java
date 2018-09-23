package shared.Model;

//DONE with code

/**
 * Created by Jonathan on 2/12/18.
 */

import java.util.*;

/**
 * Authentication Token class that is in
 * charge of creating tokens for users when they connect
 */

public class AuthToken {

    private String token;
    private String userName;
    private String personID;

    /**
     * Constructs a new authtoken by calling a private helper function
     */
    public AuthToken() {
        generateToken();
    }

    /**
     * Helper function that uses UUID to generate a custom ID token
     */

    public void generateToken() {
        this.token = UUID.randomUUID().toString();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    /**
     * Generates a string with authtoken data members as string
     * @return Returns authToken username and personId as a String
     */

    @Override
    public String toString() {
        return "AuthToken{" +
                "authToken = '" + token + '\'' +
                ", username = '" + userName + '\'' +
                ", personId = '" + personID + '\'' +
                '}';
    }

}
