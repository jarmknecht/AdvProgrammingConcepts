package activities;


import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.os.Bundle;


import com.example.jonathan.familymapserver.R;
import fragments.PersonFragment;
import fragments.PersonRecyclerFragment;

/**
 * Created by Jonathan on 3/29/18.
 */

public class PersonActivity extends AppCompatActivity {
    private PersonFragment personFragment;
    private PersonRecyclerFragment personRecyclerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fm = this.getSupportFragmentManager();
        personFragment = (PersonFragment) fm.findFragmentById(R.id.personActivity);
        if (personFragment == null) {
            personFragment = PersonFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.personActivity, personFragment)
                    .commit();
        }

        personRecyclerFragment = (PersonRecyclerFragment)
                fm.findFragmentById(R.id.personRecycler);
        if (personRecyclerFragment == null) {
            personRecyclerFragment = PersonRecyclerFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.personRecycler, personRecyclerFragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
