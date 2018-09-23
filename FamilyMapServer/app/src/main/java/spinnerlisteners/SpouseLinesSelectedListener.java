package spinnerlisteners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import model.Model;

public class SpouseLinesSelectedListener implements AdapterView.OnItemSelectedListener {
    private Spinner spouseLinesSpinner;

    public SpouseLinesSelectedListener(Spinner spouseLinesSpinner) {
        this.spouseLinesSpinner = spouseLinesSpinner;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Model.getSettings().setCurrentSpouseLineColor(
                Model.getSettings().getLineColorIntValues().get(
                        position
                )
        );
        spouseLinesSpinner.setSelection(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }
}
