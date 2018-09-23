package server.Handlers;

import java.io.*;
import java.net.*;

import com.google.gson.*;
import com.sun.net.httpserver.*;

import shared.Requests.LoginRequest;
import shared.Model.AuthToken;
import shared.Model.User;
import server.Service.LoginService;

//done with code
/**
 * Created by Jonathan on 2/24/18.
 */

public class LoginHandler implements HttpHandler{
    private EncoderDecoder encoderDecoder = new EncoderDecoder();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String userName;
        String password;
        URI url = exchange.getRequestURI();
        String URL = url.toString();
        Gson gson = new Gson();

        System.out.println("    URL received: " + URL); //need??
        if (exchange.getRequestMethod().toLowerCase().equals("post")) {
            InputStream body = exchange.getRequestBody();
            String partsOfBody = encoderDecoder.readIncomingData(body);

            LoginRequest loginRequest = gson.fromJson(partsOfBody, LoginRequest.class);
            LoginService loginService = new LoginService();
            userName = loginRequest.getUserName();
            password = loginRequest.getPassword();
            User user = loginService.checkIfUserExists(userName);

            if (user == null) {
                loginService.closeConnOnBadNameOrPassword();
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                encoderDecoder.sendData(encoderDecoder.makeMessage("Username does not exist."), exchange);
                return;
            }

            if (user.getPassword().equals(password)) {
                AuthToken token = new AuthToken();
                token.setUserName(userName);
                token.setPersonID(user.getPersonID());
                loginService.loginUser(user, token);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                encoderDecoder.sendData(token, exchange);
            }
            else {
                loginService.closeConnOnBadNameOrPassword();
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                encoderDecoder.sendData(encoderDecoder.makeMessage("Password incorrect."), exchange);
                return;
            }
        }
    }
}
