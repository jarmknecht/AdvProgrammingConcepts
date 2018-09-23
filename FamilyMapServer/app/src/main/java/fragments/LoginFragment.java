package fragments;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.os.Bundle;
import com.example.jonathan.familymapserver.R;
import shared.Requests.LoginRequest;
import shared.Requests.RegisterRequest;
import model.Model;
import model.Login;
import model.Register;

/**
 * Created by Jonathan on 3/22/18.
 */

public class LoginFragment extends Fragment {
    private static String serverHost;
    private static int serverPort;
    private static String userName;
    private static String password;
    private static String firstName;
    private static String lastName;
    private static String email;

    private Login loginObject;
    private Register registerObject;

    private EditText serverHostTxt;
    private EditText serverPortTxt;
    private EditText userNameTxt;
    private EditText passwordTxt;
    private EditText firstNameTxt;
    private EditText lastNameTxt;
    private EditText emailTxt;
    private RadioGroup radioGender;
    private RadioButton radioButton;
    private Button loginButton;
    private Button registerButton;

    public LoginFragment() {
        //need to have empty constructor
    }

    public static LoginFragment newInstance(Login loginObject) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(userName, loginObject.getUserName());
        args.putString(password, loginObject.getPassword());
        args.putString(serverHost, loginObject.getServerHost());
        args.putInt(String.valueOf(serverPort), loginObject.getServerPort());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            loginObject = new Login();
            loginObject.setUserName(getArguments().getString(userName));
            loginObject.setPassword(getArguments().getString(password));
            loginObject.setServerHost(getArguments().getString(serverHost));
            loginObject.setServerPort(getArguments().getInt(String.valueOf(serverPort)));
        }
    }

    private void onLoginButtonClicked() {
        Log.i("Log in", "Log in button clicked");
        Login login = getLoginCred();
        if (login == null) {
            onLogin(false);
        }
        else {
            Model.getServer().setServerHost(login.getServerHost());
            Model.getServer().setServerPort(Integer.toString(login.getServerPort()));
            LoginRequest loginRequest = new LoginRequest(login.getUserName(), login.getPassword());
            Model.getServer().loginUser(loginRequest);
        }
    }

    private void onRegisterButtonClicked() {
        Log.i("Register", "Register button pressed");
        registerObject = new Register();
        Register register = getRegisterCred();
        if (register == null) {
            onLogin(false);
        }
        else {
            Model.getServer().setServerHost(register.getServerHost());
            Model.getServer().setServerPort(Integer.toString(register.getServerPort()));
            RegisterRequest registerRequest = new RegisterRequest(register.getUserName(), register.getPassword(),
                    register.getEmail(), register.getFirstName(), register.getLastName(), register.getGender());
            Model.getServer().registerUser(registerRequest);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstance) {
        View v = inflater.inflate(R.layout.login_fragment, group, false);

        loginButton = (Button) v.findViewById(R.id.logInButton);
        loginButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onLoginButtonClicked();
                    }
                }
        );

        registerButton = (Button) v.findViewById(R.id.registerButton);
        registerButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onRegisterButtonClicked();
                    }
                }
        );

        serverHostTxt = (EditText) v.findViewById(R.id.serverHost);
        serverPortTxt = (EditText) v.findViewById(R.id.serverPort);
        userNameTxt = (EditText) v.findViewById(R.id.userName);
        passwordTxt = (EditText) v.findViewById(R.id.password);
        firstNameTxt = (EditText) v.findViewById(R.id.firstName);
        lastNameTxt = (EditText) v.findViewById(R.id.lastName);
        emailTxt = (EditText) v.findViewById(R.id.email);
        radioGender = (RadioGroup) v.findViewById(R.id.radioGender);

        return v;
    }

    public Login getLoginCred() {
        if (serverHostTxt.length() == 0) {
            return null;
        }
        else if (serverPortTxt.length() == 0) {
            return null;
        }
        else if (userNameTxt.length() == 0) {
            return null;
        }
        else if (passwordTxt.length() == 0) {
            return null;
        }
        else {
            loginObject.setServerHost(serverHostTxt.getText().toString());
            loginObject.setServerPort(Integer.parseInt(serverPortTxt.getText().toString()));
            loginObject.setUserName(userNameTxt.getText().toString());
            loginObject.setPassword(passwordTxt.getText().toString());

            return loginObject;
        }
    }

    public Register getRegisterCred() {
        if (serverHostTxt.length() == 0) {
            return null;
        }
        else if (serverPortTxt.length() == 0) {
            return null;
        }
        else if (userNameTxt.length() == 0) {
            return null;
        }
        else if (passwordTxt.length() == 0) {
            return null;
        }
        else if (firstNameTxt.length() == 0) {
            return null;
        }
        else if (lastNameTxt.length() == 0) {
            return null;
        }
        else if (emailTxt.length() == 0) {
            return null;
        }
        else if (radioGender.getCheckedRadioButtonId() == -1) {
            return null;
        }
        else {
            registerObject.setServerHost(serverHostTxt.getText().toString());
            registerObject.setServerPort(Integer.parseInt(serverPortTxt.getText().toString()));
            registerObject.setUserName(userNameTxt.getText().toString());
            registerObject.setPassword(passwordTxt.getText().toString());
            registerObject.setFirstName(firstNameTxt.getText().toString());
            registerObject.setLastName(lastNameTxt.getText().toString());
            registerObject.setEmail(emailTxt.getText().toString());

            int selectedId = radioGender.getCheckedRadioButtonId();

            if (selectedId == 2131427482) {
                registerObject.setGender("m");
            }
            else {
                registerObject.setGender("f");
            }

            return registerObject;
        }
    }

    public void onLogin(boolean login) {
        Toast status;

        if (login) {
            status = Toast.makeText(
                    this.getContext(),
                    "Authorization succeeded",
                    Toast.LENGTH_SHORT);
            Model.getMainActivity().onLogin();
            loginWelcome();

        }

        else {
            status = Toast.makeText(
                    this.getContext(),
                    "Authorization failed",
                    Toast.LENGTH_SHORT);
        }
        status.show();
    }

    public void loginWelcome() {
        Toast user;
        user = Toast.makeText(this.getContext(),
                "Welcome " + Model.getCurrentPerson()
                        .getFirstName() + " " + Model.getCurrentPerson().getLastName() + "!",
                Toast.LENGTH_LONG);

        user.show();
    }


}
