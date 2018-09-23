package activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.jonathan.familymapserver.R;
import fragments.FilterFragment;


/**
 * Created by Jonathan on 3/29/18.
 */

public class FilterActivity extends AppCompatActivity {
    private FilterFragment filterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fm = getSupportFragmentManager();
        filterFragment = (FilterFragment) fm.findFragmentById(R.id.filterActivity);

        if (filterFragment == null) {
            filterFragment = new FilterFragment();
            fm.beginTransaction()
                    .add(R.id.filterActivity, filterFragment)
                    .commit();
        }
    }

    @Override
    public void onPause() {
        FragmentTransaction ft = getSupportFragmentManager()
                .beginTransaction();
        ft.remove(filterFragment);
        ft.commit();
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
