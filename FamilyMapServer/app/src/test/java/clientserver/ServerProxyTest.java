package clientserver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import android.test.AndroidTestCase;
import android.util.Log;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.*;
import java.util.*;
import com.example.jonathan.familymapserver.R;
import clientserver.ServerProxy;
import dataimporter.DataImporter;
import model.Model;
import shared.Requests.RegisterRequest;
import shared.Results.EventResult;
import shared.Results.PersonResult;
import shared.Results.RegisterResult;
import shared.Requests.LoginRequest;
import shared.Results.LoginResult;

public class ServerProxyTest {
    private DataImporter dataImporter;
    private  JSONObject eventsRoot;
    private ServerProxy serverProxy;


    @Before
    public void setUp() throws Exception {
        serverProxy = new ServerProxy();
        dataImporter = new DataImporter();
        serverProxy.setServerHost("192.168.253.59");
        serverProxy.setServerPort("8080");
        assertEquals("192.168.253.59", serverProxy.getServerHost());
        assertEquals("8080", serverProxy.getServerPort());
    }

    @After
    public void tearDown() throws Exception {
        //don't want to tearDown cause each one will
        //build off the next one
    }

    @Test
    public void registerUser() {
        RegisterRequest registerRequest = new RegisterRequest("jarm", "jarm", "jarm@jarm.com",
                "Jonathan", "Armknecht", "m");
        RegisterResult registerResult = serverProxy.registerUser(registerRequest);
        assertEquals("jarm", registerResult.getUserName());

        //Try to register the same user. This will fail
        registerRequest = new RegisterRequest("jarm", "jarm", "jarm@jarm.com",
                "Jonathan", "Armknecht", "m");
        registerResult = serverProxy.registerUser(registerRequest);
        assertEquals(null, registerResult.getUserName());
    }

    @Test
    public void loginUser() {
        LoginRequest loginRequest = new LoginRequest("jarm", "jarm");
        LoginResult loginResult = serverProxy.loginUser(loginRequest);
        assertNotNull(serverProxy.getToken());

        //Try to login in with a user that isn't really in Database
        loginRequest = new LoginRequest("bob", "bob");
        loginResult = serverProxy.loginUser(loginRequest);
        assertNull(serverProxy.getToken());
    }

    @Test
    public void getAllPeople() {
        LoginRequest loginRequest = new LoginRequest("jarm", "jarm");
        LoginResult loginResult = serverProxy.loginUser(loginRequest);
        assertNotNull(serverProxy.getToken());
        List<PersonResult> list = serverProxy.getAllPeople(false);
        assertNotNull(list);

        loginRequest = new LoginRequest("bob", "bob");
        loginResult = serverProxy.loginUser(loginRequest);
        assertNull(serverProxy.getToken());
        list = serverProxy.getAllPeople(false);
        assertNull(list);

    }

    @Test
    public void getAllEvents() {
        LoginRequest loginRequest = new LoginRequest("jarm", "jarm");
        LoginResult loginResult = serverProxy.loginUser(loginRequest);
        assertNotNull(serverProxy.getToken());
        List<EventResult> list = serverProxy.getAllEvents(false);
        assertNotNull(list);

        loginRequest = new LoginRequest("bob", "bob");
        loginResult = serverProxy.loginUser(loginRequest);
        assertNull(serverProxy.getToken());
        list = serverProxy.getAllEvents(false);
        assertNull(list);
    }
}