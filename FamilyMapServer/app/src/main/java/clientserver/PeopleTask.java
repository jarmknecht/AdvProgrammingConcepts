package clientserver;


import android.os.AsyncTask;

import com.google.gson.*;
import java.io.*;
import java.net.*;
import java.util.*;

import shared.Results.PersonResult;
import model.Model;


/**
 * Created by Jonathan on 3/25/18.
 */

public class PeopleTask extends AsyncTask<URL, Void, List<PersonResult>> {
    private final PeopleTask.TaskListener listener;
    private String requestMethod;

    public interface TaskListener {
        boolean onFinished(List<PersonResult> results);
    }

    public PeopleTask(PeopleTask.TaskListener listener, String requestMethod) {
        this.listener = listener;
        this.requestMethod = requestMethod;
    }

    @Override
    public List<PersonResult> doInBackground(URL... urls) {
        for (URL url : urls) {
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();


                connection.setRequestMethod("GET");
                connection.setDoOutput(false);

                connection.addRequestProperty("Authorization", Model.getServer().getToken());
                connection.addRequestProperty("Accept", "/person/");

                connection.connect();
                Gson gson = new Gson();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream respBody = connection.getInputStream();
                    String respData = readString(respBody);
                    JsonObject result = gson.fromJson(respData, JsonElement.class).getAsJsonObject();

                    ArrayList<PersonResult> results = new ArrayList<>();
                    JsonArray jArray = (JsonArray) result.get("data");
                    if (jArray != null) {
                        for (int i = 0; i < jArray.size(); i++) {
                            PersonResult er = makePerson(jArray.get(i).getAsJsonObject());
                            results.add(er);
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
    protected void onPostExecute(List<PersonResult> results) {
        super.onPostExecute(results);
        if (this.listener != null) {
            this.listener.onFinished(results);
        }
    }

    public String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }


    public PersonResult makePerson(JsonObject obj) {
        String descendant = null;
        String personID = null;
        String firstName = null;
        String lastName = null;
        String gender = null;
        String mother = null;
        String father = null;
        String spouse = null;

        descendant = obj.get("descendant").getAsString();
        personID = obj.get("personID").getAsString();
        firstName = obj.get("firstName").getAsString();
        lastName = obj.get("lastName").getAsString();
        gender = obj.get("gender").getAsString();
        if (obj.get("mother") == null) {
            mother = null;
        }
        else {
            mother = obj.get("mother").getAsString();
        }
        if (obj.get("father") == null) {
            father = null;
        }
        else {
            father = obj.get("father").getAsString();
        }
        if (obj.get("spouse") == null) {
            spouse = null;
        }
        else {
            spouse = obj.get("spouse").getAsString();
        }

        PersonResult result = new PersonResult(descendant, personID, firstName, lastName, gender, mother, father, spouse);

        return result;
    }

}
