package server.Handlers;

import java.io.*;
import java.util.*;
import java.net.*;

import com.google.gson.*;
import com.sun.net.httpserver.*;

import shared.Results.EventResult;
import shared.Model.Event;
import server.Service.EventService;

//done with code
/**
 * Created by Jonathan on 2/24/18.
 */

public class EventHandler implements HttpHandler {
    private EncoderDecoder encoderDecoder = new EncoderDecoder();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String eventId;
        String user;
        URI url = exchange.getRequestURI();
        String siteUrl = url.toString();
        Gson gson = new Gson();

        String token = exchange.getRequestHeaders().getFirst("Authorization");

        System.out.println("    URL received: " + siteUrl); //need??
        String[] params = siteUrl.split("/");

        if (exchange.getRequestMethod().toLowerCase().equals("get")) {
            EventService service = new EventService();
            if (!service.authenticateUser(token)) {
                service.errorClosingConnection();
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                encoderDecoder.sendData(encoderDecoder.makeMessage("Authorization token not found"), exchange);
                return;
            }
            else {
                user = service.getUserByToken(token);
                if (params.length == 3) {
                    Event event;
                    eventId = params[2];
                    event = service.getEventById(eventId);

                    if (event.getDescendant().equals(user)) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        encoderDecoder.sendData(event, exchange);
                    }
                    else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        encoderDecoder.sendData(encoderDecoder.makeMessage("That information is owned by other user"), exchange);
                        return;
                    }
                }
                else {
                    List<EventResult> events = new ArrayList<>();
                    List<Event> eventList;
                    eventList = service.getAllEventsByUser(token);
                    for (Event e : eventList) {
                        EventResult eventResult = new EventResult(e.getDescendant(),
                                e.getEventID(),e.getPersonID(),e.getLatitude(),
                                e.getLongitude(),e.getCountry(),e.getCity(),e.getEventType(),e.getYear());
                        events.add(eventResult);
                    }
                    if (eventList == null) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        encoderDecoder.sendData(encoderDecoder.makeMessage("No events exist for current user"), exchange);
                    }
                    else if (eventList.get(0).getDescendant().equals(user)) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        EventResult[] eventresults = events.toArray(new EventResult[events.size()]);
                        JsonObject jsonData = new JsonObject();
                        jsonData.add("data", gson.toJsonTree(eventresults));
                        encoderDecoder.sendData(jsonData, exchange);
                    }
                    else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        encoderDecoder.sendData(encoderDecoder.makeMessage("That information is owned by other user"), exchange);
                    }
                }

            }
        }
    }

}
