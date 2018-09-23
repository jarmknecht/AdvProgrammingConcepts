package activities;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import com.example.jonathan.familymapserver.R;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import model.Model;
import fragments.MapFragment;



public class MapsActivity extends AppCompatActivity {

    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String eventID = getIntent().getExtras().getString("SELECTED_EVENT");

        if (eventID != null) {
            Log.i("MapActivity", "EventId is: " + eventID);
        }

        FragmentManager fm = this.getSupportFragmentManager();
        mapFragment = (MapFragment) fm.findFragmentById(R.id.mapFragmentHolder);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance(Model.getCurrentEventMap().get(eventID));
            fm.beginTransaction()
                    .add(R.id.mapFragmentHolder, mapFragment)
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
