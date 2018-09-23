package fragments;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.content.*;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.joanzapata.iconify.widget.IconTextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import shared.Model.Event;
import shared.Model.Person;
import com.example.jonathan.familymapserver.R;

import model.FamilyTree;
import model.Filter;
import model.Model;
import activities.MainActivity;
import activities.FilterActivity;
import activities.PersonActivity;
import activities.SearchActivity;
import activities.SettingsActivity;

import java.util.*;

/**
 * Created by Jonathan on 3/29/18.
 */

public class MapFragment extends Fragment {
    private LatLng currentLocation;
    private String selectedEventId;
    private GoogleMap map;
    private RelativeLayout personInfoLayout;
    private Polyline eventsLine;
    private Polyline spouseLine;
    private Polyline familyTreeLine;

    public static MapFragment newInstance() {
        MapFragment mapFragment = new MapFragment();
        mapFragment.currentLocation = null;

        return mapFragment;
    }

    public static MapFragment newInstance(Event event) {
        MapFragment mapFragment = new MapFragment();
        mapFragment.currentLocation = new LatLng(Double.parseDouble(event.getLatitude()),Double.parseDouble(event.getLongitude()));
        mapFragment.selectedEventId = event.getEventID();
        Model.getFamilyMembers().generateLines(event.getEventID());

        return mapFragment;
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setHasOptionsMenu(true);

        SupportMapFragment supportMapFragment = SupportMapFragment.newInstance();

        supportMapFragment.getMapAsync(
                new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap gMap) {
                        map = gMap;

                        generateMarkers();

                        if (currentLocation != null) {
                            Log.i("MapFragment", "Moving camera to selected event");
                            CameraUpdate center =
                                    CameraUpdateFactory.newLatLng(currentLocation);
                            CameraUpdate zoom =
                                    CameraUpdateFactory.zoomTo(5);
                            map.moveCamera(center);
                            map.animateCamera(zoom);
                            displaySelectedEvent(selectedEventId);
                        }

                        map.setMapType(Model.getSettings().getCurrentMapType());

                        map.setOnMarkerClickListener(
                                new GoogleMap.OnMarkerClickListener() {
                                    @Override
                                    public boolean onMarkerClick(Marker marker) {
                                        String selectedEventId = Model.getMarkersToEvents().get(marker);
                                        if (selectedEventId == null) {
                                            Log.d("MarkerClickListener", "selectedEventId is null");
                                        }
                                        else {
                                            Model.setCurrentAncestor(
                                                    Model.getCurrentPeopleMap().get(
                                                            Model.getCurrentEventMap().get(selectedEventId).getPersonID()
                                                    )
                                            );
                                            assert Model.getCurrentAncestor() != null;
                                            FamilyTree focusedPerson = new FamilyTree(Model.getCurrentAncestor());
                                            focusedPerson.generateLines(selectedEventId);
                                            displaySelectedEvent(selectedEventId);
                                        }
                                        return false;
                                    }
                                }
                        );
                    }
                }
        );

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.map, supportMapFragment).commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        Filter.applyFilters();
        clearPolyLines();
        if(map != null) {
            map.setMapType(Model.getSettings().getCurrentMapType());
        }
        if(eventsLine != null) {
            eventsLine.setVisible(Model.getSettings().isCurrentEventsOn());
            eventsLine.setColor(Model.getSettings().getCurrentEventsColor());
        }
        if(spouseLine != null) {
            spouseLine.setVisible(Model.getSettings().isSpouseOn());
            spouseLine.setColor(Model.getSettings().getCurrentSpouseLineColor());
        }
        if(familyTreeLine != null) {
            familyTreeLine.setVisible(Model.getSettings().isFamilyTreeOn());
            familyTreeLine.setColor(Model.getSettings().getCurrentFamilyTreeLineColor());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map_fragment, container, false);
        personInfoLayout = (RelativeLayout) v.findViewById(R.id.mapPersonInfo);
        personInfoLayout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), PersonActivity.class));
                    }
                }
        );
        return v;
    }

    private void generateMarkers() {

        for(Map.Entry<String, Event> entry : Model.getCurrentEventMap().entrySet()) {
            float color = getEventColor(entry.getValue());
            LatLng location = new LatLng((Double.parseDouble(entry.getValue().getLatitude())),
                    Double.parseDouble(entry.getValue().getLongitude()));
            Marker marker = map.addMarker(new MarkerOptions()
                            .position(location).icon(BitmapDescriptorFactory
                            .defaultMarker(color)));
            marker.setTag(entry.getValue());
            Model.getMarkersToEvents().put(marker, entry.getValue().getEventID());
            Model.getPeopleToMarkers().put(entry.getValue().getPersonID(), marker);
        }
        Filter.applyFilters();
    }

    private float getEventColor(Event event) {
        String eventType = event.getEventType().toLowerCase();
        float eventMarkerColor;

        switch (eventType) {
            case "baptism":
                eventMarkerColor = BitmapDescriptorFactory.HUE_BLUE;
                break;
            case "birth":
                eventMarkerColor = BitmapDescriptorFactory.HUE_RED;
                break;
            case "death":
                eventMarkerColor = BitmapDescriptorFactory.HUE_VIOLET;
                break;
            case "marriage":
                eventMarkerColor = BitmapDescriptorFactory.HUE_YELLOW;
                break;
            default:
                eventMarkerColor = BitmapDescriptorFactory.HUE_ORANGE;
                break;
        }

        return eventMarkerColor;
    }

    private void displaySelectedEvent(String eventId) {
        assert eventId.length() > 0;
        Event selectedEvent = Model.getCurrentEventMap().get(eventId);

        assert selectedEvent != null;

        Log.d("MapFragment", "selected event is: " + eventId);

        Person person = Model.getCurrentPeopleMap().get(selectedEvent.getPersonID());

        TextView name = (TextView) getActivity().findViewById(R.id.name);
        name.setText(person.getFullName());

        TextView eventInfo = (TextView) getActivity().findViewById(R.id.eventInfo);
        eventInfo.setText(selectedEvent.getEventType() + ": ");
        eventInfo.append(selectedEvent.getCity() + ", ");
        eventInfo.append(selectedEvent.getCountry() + " (");
        eventInfo.append(selectedEvent.getYear() + ")");

        IconTextView image = (IconTextView) getActivity().findViewById(R.id.image);
        if (person.getGender().equals("m")) {
            image.setText("{fa-male}");
            image.setTextColor(getResources().getColor(R.color.male, getActivity().getTheme()));
        }
        else {
            image.setText("{fa-female}");
            image.setTextColor(getResources().getColor(R.color.female, getActivity().getTheme()));
        }
        clearPolyLines();
        addPolyLines(person, selectedEvent);
    }

    private void clearPolyLines() {
        if (eventsLine != null) {
            eventsLine.remove();
        }
        if (familyTreeLine != null) {
            familyTreeLine.remove();
        }
        if (spouseLine != null) {
            spouseLine.remove();
        }
    }

    private void addPolyLines(Person person, Event event) {
        FamilyTree personTree = new FamilyTree(person);
        personTree.generateLines(event.getEventID());

        if(Model.getSettings().isCurrentEventsOn()) {
            eventsLine = map.addPolyline(personTree.getLifeStoryLine());
        }
        if(Model.getSettings().isFamilyTreeOn()) {
            familyTreeLine = map.addPolyline(personTree.getFamilyTreeLine());
        }
        if(Model.getSettings().isSpouseOn() && person.getSpouse() != null) {
            spouseLine = map.addPolyline(personTree.getSpouseLine());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if(currentLocation == null) {
            inflater.inflate(R.menu.fragment_map_menu, menu);

            MenuItem searchMenuItem = menu.findItem(R.id.search);
            searchMenuItem.setIcon(
                    new IconDrawable(getActivity(), FontAwesomeIcons.fa_search)
                            .actionBarSize()
                            .colorRes(R.color.menuItemColor)
            );

            MenuItem filterMenuItem = menu.findItem(R.id.filter);
            filterMenuItem.setIcon(
                    new IconDrawable(getActivity(), FontAwesomeIcons.fa_filter)
                            .actionBarSize()
                            .colorRes(R.color.menuItemColor)
            );

            MenuItem settingsMenuItem = menu.findItem(R.id.settings);
            settingsMenuItem.setIcon(
                    new IconDrawable(getActivity(), FontAwesomeIcons.fa_gear)
                            .actionBarSize()
                            .colorRes(R.color.menuItemColor)
            );
        }
        else {
            inflater.inflate(R.menu.fragment_go_to_top, menu);

            MenuItem goToTopMenuItem = menu.findItem(R.id.goToTopButton);
            goToTopMenuItem.setIcon(
                    new IconDrawable(getActivity(), FontAwesomeIcons.fa_angle_double_up)
                            .actionBarSize()
                            .colorRes(R.color.menuItemColor)
            );
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.filter:
                startActivity(new Intent(getActivity(), FilterActivity.class));
                break;
            case R.id.search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.settings:
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                break;
            case R.id.goToTopButton:
                startActivity(new Intent(getActivity(), MainActivity.class));
                break;
        }
        return true;
    }
}
