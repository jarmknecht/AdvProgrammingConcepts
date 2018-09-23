package server.Handlers;

import server.Service.ClearService;

import java.io.*;
import java.net.*;

import com.sun.net.httpserver.*;
import com.google.gson.*;

//done with code
/**
 * Created by Jonathan on 2/24/18.
 */

public class ClearHandler implements HttpHandler {
    private EncoderDecoder encoderDecoder = new EncoderDecoder();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        URI url = exchange.getRequestURI();
        String siteUrl = url.toString();
        Gson gson = new Gson();

        System.out.println("    URL received: " + siteUrl); //need??

        if (exchange.getRequestMethod().toLowerCase().equals("post")) {
            ClearService clear = new ClearService();

            if (clear.clearAll()) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                encoderDecoder.sendData(encoderDecoder.makeMessage("Clear succeeded"), exchange);
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
                encoderDecoder.sendData(encoderDecoder.makeMessage("Clear failed"), exchange);
            }
        }
    }
}
