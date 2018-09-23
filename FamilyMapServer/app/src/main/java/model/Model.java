package model;

import com.google.android.gms.maps.model.Marker;

import java.util.*;

import shared.Model.Event;
import shared.Model.Person;
import dataimporter.DataImporter;
import clientserver.ServerProxy;
import activities.MainActivity;


/**
 * Created by Jonathan on 3/22/18.
 */

public class Model {

    private static MainActivity mainActivity;
    private static ServerProxy serverProxy;
    private static Person currentPerson;
    private static DataImporter importer;
    private static List<Person> currentPeople;
    private static List<Event> currentEvents;
    private static Map<String, Person> currentPeopleMap;
    private static Map<String, Event> currentEventMap;
    private static Map<Marker, String> markersToEvents;
    private static Map<String, Marker> peopleToMarkers;
    private static Settings settings;
    private static FamilyTree familyMembers;
    private static List<Filter> filters;
    private static Set<String> paternalAncestors;
    private static Set<String> maternalAncestors;
    private static Set<String> eventTypes;
    private static Person currentAncestor;
    private static Map<String, Float> colorsToEvents;

    public static void init(MainActivity main) {
        mainActivity = main;
        serverProxy = new ServerProxy();
        familyMembers = new FamilyTree();
        currentPerson = new Person(null, null, null);
        currentPeople = new ArrayList<>();
        currentEvents = new ArrayList<>();
        currentPeopleMap = new HashMap<>();
        currentEventMap = new HashMap<>();
        markersToEvents = new HashMap<>();
        peopleToMarkers = new HashMap<>();
        eventTypes = new HashSet<>();
        settings = new Settings();
        filters = new ArrayList<>();
        importer = new DataImporter();
        maternalAncestors = new HashSet<>();
        paternalAncestors = new HashSet<>();
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public static ServerProxy getServer() {
        return serverProxy;
    }

    public static Person getCurrentPerson() {
        return currentPerson;
    }

    public static void setCurrentPerson(Person person) {
        Model.currentPerson = person;
    }

    public static DataImporter getImporter() {
        return importer;
    }

    public static List<Person> getCurrentPeople() {
        return currentPeople;
    }

    public static void setCurrentPeople(List<Person> people) {
        Model.currentPeople = people;
    }

    public static List<Event> getCurrentEvents() {
        return currentEvents;
    }

    public static void setCurrentEvents(List<Event> events) {
        Model.currentEvents = events;
    }

    public static void peopleListToMap() {
        for (int i = 0; i < currentPeople.size(); i++) {
            currentPeopleMap.put(currentPeople.get(i).getPersonID(), currentPeople.get(i));
        }
    }

    public static void eventListToMap() {
        for (int i = 0; i < currentEvents.size(); i++) {
            currentEventMap.put(currentEvents.get(i).getEventID(), currentEvents.get(i));
        }
    }

    public static Map<String,Person> getCurrentPeopleMap() {
        return currentPeopleMap;
    }

    public static Map<String, Event> getCurrentEventMap() {
        return currentEventMap;
    }

    public static Map<Marker, String> getMarkersToEvents() {
        return markersToEvents;
    }

    public static void setMarkersToEvents(Map<Marker, String> markers) {
        Model.markersToEvents = markers;
    }

    public static Map<String, Marker> getPeopleToMarkers() {
        return peopleToMarkers;
    }

    public static void setPeopleToMarkers(Map<String, Marker> peopleMarkers) {
        Model.peopleToMarkers = peopleMarkers;
    }

    public static Settings getSettings() {
        return settings;
    }

    public static void setSettings(Settings choices) {
         Model.settings = choices;
    }

    public static FamilyTree getFamilyMembers() {
        return familyMembers;
    }

    public static void setFamilyMembers(FamilyTree tree) {
        Model.familyMembers = tree;
    }

    public static List<Filter> getFilters() {
        return filters;
    }

    public static void setFilters(List<Filter> filters) {
        Model.filters = filters;
    }

    public static void setPaternalAncestors(Set<String> pAncestors) {
        Model.paternalAncestors = pAncestors;
    }

    public static Set<String> getPaternalAncestors() {
        return paternalAncestors;
    }

    public static void setMaternalAncestors(Set<String> mAncestors) {
        Model.maternalAncestors = mAncestors;
    }

    public static Set<String> getMaternalAncestors() {
        return maternalAncestors;
    }

    public static void setEventTypes(Set<String> types) {
        Model.eventTypes = types;
    }

    public static Set<String> getEventTypes() {
        return eventTypes;
    }

    public static void setCurrentAncestor(Person person) {
        Model.currentAncestor = person;
    }

    public static Person getCurrentAncestor() {
        return currentAncestor;
    }
}
