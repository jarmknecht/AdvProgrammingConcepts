package clientserver;


import android.os.AsyncTask;

import com.google.gson.*;
import java.io.*;
import java.net.*;
import java.util.*;

import shared.Results.EventResult;
import model.Model;

/**
 * Created by Jonathan on 3/25/18.
 */

public class EventsTask extends AsyncTask<URL, Void, List<EventResult>> {
    private final EventsTask.TaskListener listener;
    private String requestMethod;

    public interface TaskListener {
        boolean onFinished(List<EventResult> results);
    }

    public EventsTask(EventsTask.TaskListener listener, String requestMethod) {
        this.listener = listener;
        this.requestMethod = requestMethod;
    }

    @Override
    public List<EventResult> doInBackground(URL... urls) {
        for (URL url : urls) {
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();


                connection.setRequestMethod("GET");
                connection.setDoOutput(false);

                connection.addRequestProperty("Authorization", Model.getServer().getToken());
                connection.addRequestProperty("Accept", "/event/");

                connection.connect();
                Gson gson = new Gson();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream resultBody = connection.getInputStream();
                    String respData = readString(resultBody);
                    JsonObject result = gson.fromJson(respData, JsonElement.class).getAsJsonObject();

                    ArrayList<EventResult> results = new ArrayList<>();
                    JsonArray jArray = (JsonArray) result.get("data");
                    if (jArray != null) {
                        for (int i = 0; i < jArray.size(); i++) {
                            EventResult eventResult = makeEvent(jArray.get(i).getAsJsonObject());
                            results.add(eventResult);
                        }
                    }

                    return results;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<EventResult> results) {
        super.onPostExecute(results);

        if (this.listener != null) {
            this.listener.onFinished(results);
        }
    }

    public String readString(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader reader = new InputStreamReader(inputStream);
        char[] buf = new char[1024];
        int len;
        while ((len = reader.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }


    public EventResult makeEvent(JsonObject obj){
        String descendant, eventID, personID, latitude, longitude, country, city, eventType;
        int year;

        descendant = obj.get("descendant").getAsString();
        eventID = obj.get("eventID").getAsString();
        personID = obj.get("personID").getAsString();
        latitude = obj.get("latitude").getAsString();
        longitude = obj.get("longitude").getAsString();
        country = obj.get("country").getAsString();
        city = obj.get("city").getAsString();
        eventType = obj.get("eventType").getAsString();
        year = obj.get("year").getAsInt();

        EventResult result = new EventResult(descendant,eventID,personID,latitude,longitude,country,city,eventType,year);

        return result;
    }
}
