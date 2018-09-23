package clientserver;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.*;
import java.net.*;

import shared.Requests.LoginRequest;
import shared.Results.LoginResult;

/**
 * Created by Jonathan on 3/24/18.
 */

public class LoginTask extends AsyncTask<URL, Void, LoginResult> {
    private final LoginTask.TaskListener listener;
    private String requestMethod;
    private LoginRequest loginRequest;


    public interface TaskListener {
        boolean onFinished(LoginResult loginResult);
    }

    public LoginTask(LoginTask.TaskListener listener, String requestMethod, LoginRequest loginRequest) {
        this.listener = listener;
        this.requestMethod = requestMethod;
        this.loginRequest = loginRequest;
    }

    @Override
    public LoginResult doInBackground(URL... urls) {
        LoginResult loginResult = null;

        for (URL url : urls) {
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.connect();

                Gson gson = new Gson();
                String jsonString = gson.toJson(loginRequest);
                OutputStream requestBody = connection.getOutputStream();
                writeString(jsonString, requestBody);
                requestBody.close();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream resultBody = connection.getInputStream();
                    String resultData = readString(resultBody);
                    loginResult = gson.fromJson(resultData, LoginResult.class);
                    return loginResult;
                }
                else {
                    return new LoginResult(connection.getResponseMessage());
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(LoginResult loginResult) {
        super.onPostExecute(loginResult);

        if (this.listener != null) {
            this.listener.onFinished(loginResult);
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

    public void writeString(String str, OutputStream outputStream) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        writer.write(str);
        writer.flush();
    }

}
