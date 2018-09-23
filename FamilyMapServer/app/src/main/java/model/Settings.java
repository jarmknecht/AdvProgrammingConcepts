package model;

import android.graphics.Color;
import com.google.android.gms.maps.GoogleMap;
import java.io.Serializable;
import java.util.*;

import fragments.SettingsFragment;

/**
 * Created by Jonathan on 3/29/18.
 */

public class Settings implements Serializable {
    private SettingsFragment settingsFragment;
    private int currentMapType;
    private List<String> mapStringValues;
    private List<Integer> mapIntegerValues;
    private int currentSpouseLineColor;
    private boolean spouseOn;
    private int currentFamilyTreeLineColor;
    private boolean familyTreeOn;
    private int currentEventsColor;
    private boolean currentEventsOn;
    private List<String> lineColorStringValues;
    private List<Integer> lineColorIntValues;

    public Settings() {
        currentMapType = GoogleMap.MAP_TYPE_NORMAL;
        mapStringValues = new ArrayList<>(4);
        mapIntegerValues = new ArrayList<>(4);
        lineColorIntValues = new ArrayList<>();
        lineColorStringValues = new ArrayList<>();
        loadMapTypes();
        loadLineColorTypes();
        currentSpouseLineColor = lineColorIntValues.get(0);
        spouseOn = true;
        currentFamilyTreeLineColor = lineColorIntValues.get(1);
        familyTreeOn = true;
        currentEventsColor = lineColorIntValues.get(2);
        currentEventsOn = true;
    }

    public SettingsFragment getSettingsFragment() {
        return settingsFragment;
    }

    public void setSettingsFragment(SettingsFragment settingsFragment) {
        this.settingsFragment = settingsFragment;
    }

    public int getCurrentMapType() {
        return currentMapType;
    }

    public void setCurrentMapType(int currentMapType) {
        this.currentMapType = currentMapType;
    }

    public List<String> getMapStringValues() {
        return mapStringValues;
    }

    public void setMapStringValues(List<String> mapStringValues) {
        this.mapStringValues = mapStringValues;
    }

    public List<Integer> getMapIntegerValues() {
        return mapIntegerValues;
    }

    public void setMapIntegerValues(List<Integer> mapIntegerValues) {
        this.mapIntegerValues = mapIntegerValues;
    }

    public int getCurrentSpouseLineColor() {
        return currentSpouseLineColor;
    }

    public void setCurrentSpouseLineColor(int currentSpouseLineColor) {
        this.currentSpouseLineColor = currentSpouseLineColor;
    }

    public boolean isSpouseOn() {
        return spouseOn;
    }

    public void setSpouseOn(boolean spouseOn) {
        this.spouseOn = spouseOn;
    }

    public int getCurrentFamilyTreeLineColor() {
        return currentFamilyTreeLineColor;
    }

    public void setCurrentFamilyTreeLineColor(int currentFamilyTreeLineColor) {
        this.currentFamilyTreeLineColor = currentFamilyTreeLineColor;
    }

    public boolean isFamilyTreeOn() {
        return familyTreeOn;
    }

    public void setFamilyTreeOn(boolean familyTreeOn) {
        this.familyTreeOn = familyTreeOn;
    }

    public int getCurrentEventsColor() {
        return currentEventsColor;
    }

    public void setCurrentEventsColor(int currentEventsColor) {
        this.currentEventsColor = currentEventsColor;
    }

    public boolean isCurrentEventsOn() {
        return currentEventsOn;
    }

    public void setCurrentEventsOn(boolean currentEventsOn) {
        this.currentEventsOn = currentEventsOn;
    }

    public List<String> getLineColorStringValues() {
        return lineColorStringValues;
    }

    public void setLineColorStringValues(List<String> lineColorStringValues) {
        this.lineColorStringValues = lineColorStringValues;
    }

    public List<Integer> getLineColorIntValues() {
        return lineColorIntValues;
    }

    public void setLineColorIntValues(List<Integer> lineColorIntValues) {
        this.lineColorIntValues = lineColorIntValues;
    }

    public void logout() {
        Model.setCurrentPerson(null);
    }

    public void resyncData() {
        Model.getServer().getAllEvents(true);
        Model.getServer().getAllPeople(true);
        Model.getImporter().generateData();
    }

    public void resyncFailed() {
        settingsFragment.onResyncFailed();
    }

    public void resyncSucceeded() {
        settingsFragment.onResyncSucceeded();
    }

    private void loadMapTypes() {
        mapStringValues.add(0, "Normal");
        mapIntegerValues.add(0, GoogleMap.MAP_TYPE_NORMAL);

        mapStringValues.add(1, "Hybrid");
        mapIntegerValues.add(1, GoogleMap.MAP_TYPE_HYBRID);

        mapStringValues.add(2, "Satellite");
        mapIntegerValues.add(2, GoogleMap.MAP_TYPE_SATELLITE);

        mapStringValues.add(3, "Terrain");
        mapIntegerValues.add(3, GoogleMap.MAP_TYPE_TERRAIN);
    }

    private void loadLineColorTypes() {
        lineColorStringValues.add(0, "Red");
        lineColorIntValues.add(0, Color.RED);

        lineColorStringValues.add(1, "Blue");
        lineColorIntValues.add(1, Color.BLUE);

        lineColorStringValues.add(2, "Green");
        lineColorIntValues.add(2, Color.GREEN);

        lineColorStringValues.add(3, "Black");
        lineColorIntValues.add(3, Color.BLACK);
    }

}
