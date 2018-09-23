package model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;

/**
 * Created by Jonathan on 3/22/18.
 */

public class Login implements Serializable {
    private String userName;
    private String password;
    private String serverHost;
    private int serverPort;
    private String token;
    private String personId;

    public Login() {
        this.userName = "NEW FRAGMENT";
        this.password = "NEW FRAGMENT";
        this.serverHost = "NEW FRAGMENT";
        this.serverPort = 0;
        this.personId = "NEW FRAGMENT";
    }

    public Login(String userName, String password, String serverHost, int serverPort) {
        this.userName = userName;
        this.password = password;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public static void parseLoginInfo(Login login, JSONObject obj) throws JSONException {
        String authToken = obj.getString("Authorization");
        String personId = obj.getString("personId");

        login.setAuthToken(authToken);
        login.setPersonId(personId);
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

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getAuthToken() {
        return token;
    }

    public void setAuthToken(String token) {
        this.token = token;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String toJSON() {
        return "{ " + "username:\"" + userName + "\"," +
                "password:\"" + password + "\"" + "}";
    }

    @Override
    public String toString() {
        return String.format(
                "User name: %s \nPassword: %s \n" +
                        "Server Host: %s \nServer Post: %d",
                userName,
                password,
                serverHost,
                serverPort);
    }

}
