package server.Handlers;

import java.io.*;
import java.net.*;

import com.google.gson.*;

import com.sun.net.httpserver.*;

import shared.Requests.RegisterRequest;

import server.Service.RegisterService;
import shared.Model.AuthToken;
import shared.Model.User;

//done with code
/**
 * Created by Jonathan on 2/24/18.
 */

public class RegisterHandler implements HttpHandler {
    private EncoderDecoder encoderDecoder = new EncoderDecoder();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String userName;
        String password;
        String email;
        String firstName;
        String lastName;
        String gender;
        boolean success = false;
        URI url = exchange.getRequestURI();
        String URL = url.toString();
        Gson gson = new Gson();

        System.out.println("    URL received: " + URL); //need??
        if (exchange.getRequestMethod().toLowerCase().equals("post")) {
            InputStream body = exchange.getRequestBody();
            String partsOfBody = encoderDecoder.readIncomingData(body);

            RegisterRequest registerRequest = gson.fromJson(partsOfBody, RegisterRequest.class);
            RegisterService registerService = new RegisterService();
            userName = registerRequest.getUsername();
            password = registerRequest.getPassword();
            email = registerRequest.getEmail();
            firstName = registerRequest.getFirstName();
            lastName = registerRequest.getLastName();
            gender = registerRequest.getGender();

            User user = new User(userName, password, email, firstName, lastName, gender);

            if (!registerService.checkForUser(userName)) {
                registerService.registerUser(user);
                AuthToken token = new AuthToken();
                token = registerService.getTokenOfUser(user.getUserName());
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                encoderDecoder.sendData(token, exchange);
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                encoderDecoder.sendData(encoderDecoder.makeMessage("Username already exists"), exchange);
                return;
            }
        }
    }
}
