package model;

import com.google.android.gms.maps.model.Marker;

import java.io.*;
import java.util.*;
import shared.Model.Event;

/**
 * Created by Jonathan on 3/29/18.
 */

public class Filter implements Serializable {
    private String eventType;
    private String filterTitle;
    private String filterSubTitle;
    private boolean filterOn;
    private ArrayList<String> eventsOnOrOff;

    public Filter(String eventType, String filterTitle, String filterSubTitle) {
        this.eventType = eventType;
        this.filterTitle = filterTitle;
        this.filterSubTitle = filterSubTitle;
        filterOn = true;
    }

    public Filter(String eventType) {
        //All events on at the beginning for tests
        eventsOnOrOff = new ArrayList<>();
        eventsOnOrOff.add("Male Events");
        eventsOnOrOff.add("Female Events");
        eventsOnOrOff.add("Father's Side");
        eventsOnOrOff.add("Mother's Side");
        eventsOnOrOff.add("Birth");
        eventsOnOrOff.add("Death");
        eventsOnOrOff.add("Marriage");
        eventsOnOrOff.add("Baptism");

        this.eventType = eventType;
        filterOn = true;
    }

    public ArrayList<String> getEventsOnOrOff() {
        return eventsOnOrOff;
    }

    public void setEventsOnOrOff(ArrayList<String> eventsOnOrOff) {
        this.eventsOnOrOff = eventsOnOrOff;
    }

    public static void applyFilters() {
        for (Map.Entry<Marker, String> markerEntry : Model.getMarkersToEvents().entrySet()) {
            for (Filter filter : Model.getFilters()) {
                Event event = Model.getCurrentEventMap().get(markerEntry.getValue());
                Marker marker = markerEntry.getKey();
                if (filter.getEventType() == null) {
                    checkStandardFilters(event, filter, marker);
                    if (!filter.isFilterOn()) {
                        break;
                    }
                }
                else if (isEventFilterApplicable(event,filter)) {
                    marker.setVisible(filter.isFilterOn());
                    if (!filter.isFilterOn()) {
                        break;
                    }
                }
            }
        }
    }

    public static void checkStandardFilters(Event event, Filter filter, Marker marker) {
        if (filter.getFilterTitle().equals("Male Events")) {
            if (isMaleEventApplicable(event)) {
                marker.setVisible(filter.isFilterOn());
            }
        }
        if (filter.getFilterTitle().equals("Female Events")) {
            if (isFemaleEventApplicable(event)) {
                marker.setVisible(filter.isFilterOn());
            }
        }
        if (filter.getFilterTitle().equals("Father's Side")) {
            if (isFathersSideApplicable(event)) {
                marker.setVisible(filter.isFilterOn());
            }
        }
        if (filter.getFilterTitle().equals("Mother's Side")) {
            if (isMothersSideApplicable(event)) {
                marker.setVisible(filter.isFilterOn());
            }
        }
    }

    public static boolean isEventFilterApplicable(Event event, Filter filter) {
        if (event.getEventType().toLowerCase().equals(filter.getEventType())) {
            return true;
        }
        else {
            return false;
        }
    }

    private static boolean isMaleEventApplicable(Event event) {
        if (Model.getCurrentPeopleMap().get(event.getPersonID()) != null) {
            return Model.getCurrentPeopleMap().get(event.getPersonID()).isMale();
        }
        else {
            return false;
        }
    }

    private static boolean isFemaleEventApplicable(Event event) {
        if (Model.getCurrentPeopleMap().get(event.getPersonID()) != null) {
            return !Model.getCurrentPeopleMap().get(event.getPersonID()).isMale();
        }
        else {
            return false;
        }
    }

    private static boolean isFathersSideApplicable(Event event) {
        for (String ancestor : Model.getPaternalAncestors()) {
            if (ancestor.equals(event.getPersonID())) {
                return true;
            }
        }
        return false;
    }

    private static boolean isMothersSideApplicable(Event event) {
        for (String ancestor : Model.getMaternalAncestors()) {
            if (ancestor.equals(event.getPersonID())) {
                return true;
            }
        }
        return false;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getFilterTitle() {
        return filterTitle;
    }

    public void setFilterTitle(String filterTitle) {
        this.filterTitle = filterTitle;
    }

    public String getFilterSubTitle() {
        return filterSubTitle;
    }

    public void setFilterSubTitle(String filterSubTitle) {
        this.filterSubTitle = filterSubTitle;
    }

    public boolean isFilterOn() {
        return filterOn;
    }

    public void setFilterOn(boolean filterOn) {
        this.filterOn = filterOn;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "eventType='" + eventType + '\'' +
                ", filterTitle='" + filterTitle + '\'' +
                ", filterSubTitle='" + filterSubTitle + '\'' +
                ", on=" + filterOn +
                '}';
    }
}
