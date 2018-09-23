package activities;

import com.example.jonathan.familymapserver.R;
import model.Model;
import fragments.SettingsFragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by Jonathan on 3/29/18.
 */

public class SettingsActivity extends AppCompatActivity {
    private SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fm = this.getSupportFragmentManager();
        settingsFragment = (SettingsFragment) fm.findFragmentById(R.id.settingsFragmentHolder);
        if (settingsFragment == null) {
            settingsFragment = SettingsFragment.newInstance();
            Model.getSettings().setSettingsFragment(settingsFragment);
            fm.beginTransaction()
                    .add(R.id.settingsFragmentHolder, settingsFragment)
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
