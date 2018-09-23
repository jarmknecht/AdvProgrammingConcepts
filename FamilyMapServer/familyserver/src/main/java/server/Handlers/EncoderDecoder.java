package server.Handlers;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;

//done with code
/**
 * Created by Jonathan on 2/24/18.
 */

public class EncoderDecoder {
    public EncoderDecoder() {

    }

    public void sendData(Object object, HttpExchange exchange) {
        Gson gson = new Gson();

        try {
            if (object != null) {
                OutputStreamWriter data = new OutputStreamWriter(exchange.getResponseBody(),
                                                Charset.forName("UTF-8"));
                String json = gson.toJson(object);
                data.write(json);
                data.close();
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, -1);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("\nError sending out JSON data " + e.getMessage());
        }
    }

    public JsonObject makeMessage(String message) {
        JsonObject object = new JsonObject();
        object.addProperty("message", message);
        return object;
    }

    public String readIncomingData(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader reader = new InputStreamReader(inputStream);
        char[] buffer = new char[1024]; //how large inputstream can be
        int length;
        while ((length = reader.read(buffer)) > 0) {
            sb.append(buffer, 0, length);
        }

        return sb.toString();
    }
}
