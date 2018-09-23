package spinnerlisteners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import model.Model;

public class LifeStoryLinesSelectedListener implements AdapterView.OnItemSelectedListener {
    private Spinner lifeStoryLinesSpinner;

    public LifeStoryLinesSelectedListener(Spinner lifeStoryLinesSpinner) {
        this.lifeStoryLinesSpinner = lifeStoryLinesSpinner;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Model.getSettings().setCurrentEventsColor(
                Model.getSettings().getLineColorIntValues().get(
                        position
                )
        );
        lifeStoryLinesSpinner.setSelection(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }
}
