package server.Server;

import java.io.*;
import java.net.*;

import com.sun.net.httpserver.*;

import server.Handlers.ClearHandler;
import server.Handlers.DefaultHandler;
import server.Handlers.EventHandler;
import server.Handlers.FillHandler;
import server.Handlers.LoadHandler;
import server.Handlers.LoginHandler;
import server.Handlers.PersonHandler;
import server.Handlers.RegisterHandler;
//done with code
/**
 * Created by Jonathan on 2/24/18.
 */

public class Server {
    private static final int MAX_WAITING_CONNECTIONS = 12;

    private HttpServer server;

    private void run(String portNum) {
        System.out.println("Initializing HTTP Server");

        try {
            server = HttpServer.create(new InetSocketAddress
                                    (Integer.parseInt(portNum)), MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
        server.setExecutor(null);

        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/person", new PersonHandler());
        server.createContext("/event", new EventHandler());
        server.createContext("/", new DefaultHandler());

        System.out.println("Starting server");
        server.start();
        System.out.println("Server started");
    }

    public static void main(String[] args) {
        String portNum = args[0];
        //portNum = "8080";
        new Server().run(portNum);
    }
}
