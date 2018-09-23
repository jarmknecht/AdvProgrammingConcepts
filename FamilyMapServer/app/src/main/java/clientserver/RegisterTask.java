package clientserver;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.*;
import java.net.*;


import shared.Requests.RegisterRequest;
import shared.Results.RegisterResult;


/**
 * Created by Jonathan on 3/24/18.
 */

public class RegisterTask extends AsyncTask<URL, Void, RegisterResult> {
    private final RegisterTask.TaskListener listener;
    private String requestMethod;
    private RegisterRequest registerRequest;

    public interface TaskListener {
        boolean onFinished(RegisterResult registerResult);
    }

    public RegisterTask(RegisterTask.TaskListener listener, String requestMethod, RegisterRequest request) {
        this.listener = listener;
        this.requestMethod = requestMethod;
        this.registerRequest = request;
    }

    @Override
    public RegisterResult doInBackground(URL... urls) {
        RegisterResult result = null;

        for (URL url : urls) {
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.connect();

                Gson gson = new Gson();
                String jsonString = gson.toJson(registerRequest);
                OutputStream requestBody = connection.getOutputStream();
                writeString(jsonString, requestBody);
                requestBody.close();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream resultBody = connection.getInputStream();
                    String resultData = readString(resultBody);
                    result = gson.fromJson(resultData, RegisterResult.class);
                    return result;
                }
                else {
                    return new RegisterResult(connection.getResponseMessage());
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(RegisterResult registerResult) {
        super.onPostExecute(registerResult);

        assert listener != null;

        if (listener != null) {
            listener.onFinished(registerResult);
        }
    }

    public String readString(InputStream input) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader reader = new InputStreamReader(input);
        char[] buffer = new char[1024];
        int length;
        while ((length = reader.read(buffer)) > 0) {
            sb.append(buffer, 0, length);
        }
        return sb.toString();
    }

    public void writeString(String str, OutputStream output) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter((output));
        writer.write(str);
        writer.flush();
    }
}
