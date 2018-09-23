package spinnerlisteners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import model.Model;

public class FamilyTreeSelectedListener implements AdapterView.OnItemSelectedListener {
    private Spinner familyTreeSpinner;

    public FamilyTreeSelectedListener(Spinner familyTreeSpinner) {
        this.familyTreeSpinner = familyTreeSpinner;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Model.getSettings().setCurrentFamilyTreeLineColor(
                Model.getSettings().getLineColorIntValues().get(
                        position
                )
        );
        familyTreeSpinner.setSelection(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }
}
