package activities;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import com.example.jonathan.familymapserver.R;
import shared.Requests.PersonRequest;
import model.Login;
import model.Model;
import fragments.LoginFragment;
import fragments.MapFragment;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

public class MainActivity extends AppCompatActivity {

    private LoginFragment loginFragment;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Iconify.with(new FontAwesomeModule());
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {

        }//need?

        FragmentManager fm = this.getSupportFragmentManager();
        if (Model.getCurrentPerson() == null) {
            Model.init(this);

            loginFragment = (LoginFragment) fm.findFragmentById(R.id.loginFragment);
            if (loginFragment == null) {
                loginFragment = LoginFragment.newInstance(new Login());
                fm.beginTransaction()
                        .add(R.id.loginFragment, loginFragment)
                        .commit();
            }
        }

       mapFragment = (MapFragment) fm.findFragmentById(R.id.mapFragment);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            if (Model.getServer().getToken() != null) {
                fm.beginTransaction()
                        .add(R.id.mapFragment, mapFragment)
                        .commit();
            }
        }
    }

    public void onLogin() {
        PersonRequest request = new PersonRequest(Model.getCurrentPerson().getPersonID());
        Model.getServer().getPerson(request);
        Model.getServer().getAllPeople(false);
        Model.getServer().getAllEvents(false);
    }

    public void onPeopleLoaded() {
        Toast welcome = Toast.makeText(
                this,
                "Welcome " + Model.getCurrentPerson().getFirstName() +
                        " " + Model.getCurrentPerson().getLastName() + "!",
                Toast.LENGTH_SHORT);
        welcome.show();
        Model.getFamilyMembers().setCurrentPerson(Model.getCurrentPerson());
    }

   public void onEventsLoaded() {
        Model.getImporter().generateData();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.loginFragment, mapFragment, "mapFragment");
        transaction.commit();
    }

    public LoginFragment getLoginFragment() {
        return loginFragment;
    }
}
