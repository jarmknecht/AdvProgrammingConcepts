package server.Handlers;

import java.io.*;
import java.nio.file.*;

import java.util.*;
import java.net.*;

import com.sun.net.httpserver.*;


//done with code
/**
 * Created by Jonathan on 2/24/18.
 */

public class DefaultHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String filePathStr;
        if (exchange.getRequestURI().getPath().equals("/")) {
            filePathStr = "web/index.html";
        }
        else {
           filePathStr = "web" + exchange.getRequestURI().getPath();
        }
        Path filePath = FileSystems.getDefault().getPath(filePathStr);


        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        Files.copy(filePath, exchange.getResponseBody());
        exchange.close();
    }
}
