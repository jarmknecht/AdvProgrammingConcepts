package activities;

import com.example.jonathan.familymapserver.R;
import fragments.SearchFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by Jonathan on 3/29/18.
 */

public class SearchActivity extends AppCompatActivity {
    private SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FragmentManager fm = this.getSupportFragmentManager();
        searchFragment = (SearchFragment) fm.findFragmentById(R.id.searchRecyclerViewHolder);
        if (searchFragment == null) {
            searchFragment = SearchFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.searchRecyclerViewHolder, searchFragment)
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
