package server.Handlers;

import java.io.*;
import java.net.*;

import com.google.gson.*;
import com.sun.net.httpserver.*;

import server.Service.LoadService;
import shared.Requests.LoadRequest;
import shared.Model.Event;
import shared.Model.Person;
import shared.Model.User;

//done with code
/**
 * Created by Jonathan on 2/24/18.
 */

public class LoadHandler implements HttpHandler {
    private EncoderDecoder encoderDecoder = new EncoderDecoder();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        User[] users;
        Person[] persons;
        Event[] events;
        boolean success = false;

        URI url = exchange.getRequestURI();
        String URL = url.toString();
        Gson gson = new Gson();

        System.out.println("    URL received: " + URL); //need??

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream body = exchange.getRequestBody();
                String partsOfBody = encoderDecoder.readIncomingData(body);

                LoadRequest load = gson.fromJson(partsOfBody, LoadRequest.class);
                users = load.getUsers();
                persons = load.getPersons();
                events = load.getEvents();

                if (users == null || persons == null || events == null) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    encoderDecoder.sendData(encoderDecoder.makeMessage("Missing load data"), exchange);
                    return;
                }

                LoadService loadService = new LoadService();
                loadService.loadData(users, persons, events);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                encoderDecoder.sendData(encoderDecoder.makeMessage("Seccuessfully added " +
                                        loadService.getNumOfUsers() + " users, " + loadService.getNumOfPeople() +
                                        " persons, and " + loadService.getNumOfEvents() + " events to the database"), exchange);
                success = true;
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            encoderDecoder.sendData(encoderDecoder.makeMessage(e.toString()), exchange);
        }
    }
}
