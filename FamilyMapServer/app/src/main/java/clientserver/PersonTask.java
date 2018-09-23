package clientserver;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.*;
import java.net.*;
import model.Model;
import shared.Requests.PersonRequest;
import shared.Results.PersonResult;



/**
 * Created by Jonathan on 3/24/18.
 */

public class PersonTask extends AsyncTask<URL, Void, PersonResult> {
    private final PersonTask.TaskListener listener;
    private String requestMethod;
    private PersonRequest personRequest;

    public interface TaskListener {
        boolean onFinished(PersonResult personResult);
    }

    public PersonTask(PersonTask.TaskListener listener, String requestMethod, PersonRequest request) {
        this.listener = listener;
        this.requestMethod = requestMethod;
        this.personRequest = request;
    }

    @Override
    public PersonResult doInBackground(URL... urls) {
        PersonResult result;

        for (URL url : urls) {
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoOutput(false);

                connection.addRequestProperty("Authorization", Model.getServer().getToken());

                connection.connect();
                Gson gson = new Gson();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream respBody = connection.getInputStream();
                    String respData = readString(respBody);
                    result = gson.fromJson(respData, PersonResult.class);
                    return result;
                }
                else {
                    return new PersonResult(connection.getResponseMessage());
                }

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(PersonResult result) {
        super.onPostExecute(result);

        if (this.listener != null) {
            this.listener.onFinished(result);
        }
    }

    public String readString(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader reader = new InputStreamReader(inputStream);
        char[] buffer = new char[1024];
        int length;
        while ((length = reader.read(buffer)) > 0) {
            sb.append(buffer, 0, length);
        }
        return sb.toString();
    }
}
