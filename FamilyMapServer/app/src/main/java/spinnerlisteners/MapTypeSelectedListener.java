package spinnerlisteners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import model.Model;

public class MapTypeSelectedListener implements AdapterView.OnItemSelectedListener {
    private Spinner mapTypeSpinner;

    public MapTypeSelectedListener(Spinner mapTypeSpinner) {
        this.mapTypeSpinner = mapTypeSpinner;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Model.getSettings().setCurrentMapType(
                Model.getSettings().getMapIntegerValues().get(
                        position
                )
        );
        mapTypeSpinner.setSelection(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }
}
