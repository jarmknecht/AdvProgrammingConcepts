package server.Handlers;

import java.io.*;
import java.net.*;

import com.google.gson.*;
import com.sun.net.httpserver.*;
import com.sun.net.httpserver.HttpHandler;

import server.Service.FillService;

//done with code
/**
 * Created by Jonathan on 2/24/18.
 */

public class FillHandler implements HttpHandler{
    private EncoderDecoder encoderDecoder = new EncoderDecoder();

    @Override
    public void handle(HttpExchange exchange) throws IOException{
        String username = null;
        int generations = 0;
        URI url = exchange.getRequestURI();
        String URL = url.toString();
        Gson gson = new Gson();

        System.out.println("    URL received: " + URL); //need??

        if (exchange.getRequestMethod().toLowerCase().equals("post")) {
            String [] params = URL.split("/");

            if (params.length < 3) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_ACCEPTABLE, 0);
                encoderDecoder.sendData(encoderDecoder.makeMessage("Invalid number of parameters"), exchange);
                return;
            }
            username = params[2];
            if (params.length == 4) {
                generations = Integer.parseInt(params[3]);
            }
            else {
                generations = 4;
            }
            FillService fillService = new FillService();

            if (fillService.fill(username, generations)) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                encoderDecoder.sendData(encoderDecoder.makeMessage("Successfully added " + fillService.getNumOfPeople()
                                        + " persons and " + fillService.getNumOfEvents() + " events to the database"), exchange);
            }
            else {
                fillService.closeConnError();
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                encoderDecoder.sendData(encoderDecoder.makeMessage("Username does not exist"), exchange);
                return;
            }
        }
    }
}
