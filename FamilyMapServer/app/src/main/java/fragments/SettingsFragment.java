package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.jonathan.familymapserver.R;
import model.Model;
import model.Settings;
import activities.MainActivity;
import spinnerlisteners.FamilyTreeSelectedListener;
import spinnerlisteners.LifeStoryLinesSelectedListener;
import spinnerlisteners.MapTypeSelectedListener;
import spinnerlisteners.SpouseLinesSelectedListener;


public class SettingsFragment extends Fragment {
    private Settings settings;

    public static SettingsFragment newInstance() {
        SettingsFragment settingsFragment = new SettingsFragment();
        settingsFragment.settings = Model.getSettings();
        return settingsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.settings_fragment, container, false);
        onCreateViewLogout(v);
        onCreateViewResyncData(v);
        onCreateViewMapType(v);
        onCreateViewSpouseLines(v);
        onCreateViewFamilyTreeLines(v);
        onCreateViewLifeStoryLines(v);
        return v;
    }

    private void onCreateViewLogout(View v) {
        RelativeLayout logout = (RelativeLayout) v.findViewById(R.id.logout);
        logout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        settings.logout();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
        );
    }

    private void onCreateViewResyncData(View v) {
        RelativeLayout resyncData = (RelativeLayout) v.findViewById(R.id.resyncData);
        resyncData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        settings.resyncData();
                    }
                }
        );
    }

    private void onCreateViewMapType(View v) {
        Spinner mapTypeSpinner = (Spinner) v.findViewById(R.id.mapTypeSpinner);
        mapTypeSpinner.setOnItemSelectedListener(new MapTypeSelectedListener(mapTypeSpinner));

        int selectionIndex = settings.getMapIntegerValues().indexOf(
                settings.getCurrentMapType()
        );

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                settings.getMapStringValues()
        );
        dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mapTypeSpinner.setAdapter(dataAdapter);
        mapTypeSpinner.setSelection(selectionIndex);
    }

    private void onCreateViewSpouseLines(View v) {
        Spinner spouseLinesSpinner = (Spinner) v.findViewById(R.id.spouseLinesSpinner);
        spouseLinesSpinner.setOnItemSelectedListener(new SpouseLinesSelectedListener(spouseLinesSpinner));

        int selectionIndex = settings.getLineColorIntValues().indexOf(
                settings.getCurrentSpouseLineColor()
        );

        ArrayAdapter<String> colorDataAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                settings.getLineColorStringValues()
        );
        colorDataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spouseLinesSpinner.setAdapter(colorDataAdapter);
        spouseLinesSpinner.setSelection(selectionIndex);

        Switch spouseLinesSwitch = (Switch) v.findViewById(R.id.spouseLinesSwitch);
        spouseLinesSwitch.setChecked(settings.isSpouseOn());
        spouseLinesSwitch.setShowText(true);

        spouseLinesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setSpouseOn(!settings.isSpouseOn());
            }
        });
    }

    private void onCreateViewFamilyTreeLines(View v) {
        Spinner familyTreeSpinner = (Spinner) v.findViewById(R.id.familyTreeLinesSpinner);
        familyTreeSpinner.setOnItemSelectedListener(new FamilyTreeSelectedListener(familyTreeSpinner));

        int selectionIndex = settings.getLineColorIntValues().indexOf(
                settings.getCurrentFamilyTreeLineColor()
        );

        ArrayAdapter<String> colorDataAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                settings.getLineColorStringValues()
        );
        colorDataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        familyTreeSpinner.setAdapter(colorDataAdapter);
        familyTreeSpinner.setSelection(selectionIndex);

        Switch familyTreeSwitch = (Switch) v.findViewById(R.id.familyTreeLinesSwitch);
        familyTreeSwitch.setChecked(settings.isFamilyTreeOn());
        familyTreeSwitch.setShowText(true);

        familyTreeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setFamilyTreeOn(!settings.isFamilyTreeOn());
            }
        });
    }

    private void onCreateViewLifeStoryLines(View v) {
        Spinner lifeStorySpinner = (Spinner) v.findViewById(R.id.lifeStoryLinesSpinner);
        lifeStorySpinner.setOnItemSelectedListener(new LifeStoryLinesSelectedListener(lifeStorySpinner));

        int selectionIndex = settings.getLineColorIntValues().indexOf(
                settings.getCurrentEventsColor()
        );

        ArrayAdapter<String> colorDataAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                settings.getLineColorStringValues()
        );
        colorDataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        lifeStorySpinner.setAdapter(colorDataAdapter);
        lifeStorySpinner.setSelection(selectionIndex);

        final Switch lifeStorySwitch = (Switch) v.findViewById(R.id.lifeStoryLinesSwitch);
        lifeStorySwitch.setChecked(settings.isCurrentEventsOn());
        lifeStorySwitch.setShowText(true);

        lifeStorySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setCurrentEventsOn(!settings.isCurrentEventsOn());
            }
        });
    }

    public void onResyncFailed() {
        Toast resyncFailed = Toast.makeText(
                getContext(),
                "Oops, the resync has failed. Please try again.",
                Toast.LENGTH_SHORT
        );
        resyncFailed.show();
    }

    public void onResyncSucceeded() {
        Toast loadingSuccess = Toast.makeText(
                getActivity(),
                "Successfully resynced with server.",
                Toast.LENGTH_SHORT
        );
        loadingSuccess.show();
        Model.getImporter().generateData();
        startActivity(new Intent(getActivity(), MainActivity.class));
    }

}
