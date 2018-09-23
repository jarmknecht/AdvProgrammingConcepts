package model;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.*;
import shared.Model.Person;
import shared.Model.Event;
import recyclerview.EventChildListItem;
import recyclerview.PersonChildListItem;


/**
 * Created by Jonathan on 3/22/18.
 */

public class FamilyTree {
    private Person currentPerson;
    private List<Object> family;
    private List<Object> events;
    private PolylineOptions familyTreeLine;
    private PolylineOptions eventLine;
    private PolylineOptions spouseLine;
    private Set <String> children;

    public FamilyTree() {
        this.currentPerson = Model.getCurrentPerson();
        this.family = new ArrayList<>();
        this.events = new ArrayList<>();
        this.children = new HashSet<>();
    }

    public FamilyTree(Person person) {
        this.currentPerson = person;
        this.family = new ArrayList<>();
        this.events = new ArrayList<>();
        this.children = new HashSet<>();
        generateFamily();
        generateEvents();
    }

    public FamilyTree(Person person, String testCases) {
        this.currentPerson = person;
        this.family = new ArrayList<>();
        this.events = new ArrayList<>();
        this.children = new HashSet<>();

        currentPerson.setDescendant("Jonathan");
        currentPerson.setFather("John");
        currentPerson.setMother("Jill");
        currentPerson.setSpouse("Melissa");

        family.add(currentPerson.getFather());
        family.add(currentPerson.getMother());
        family.add(currentPerson.getSpouse());

        Event birth = new Event("Jonathan", "Jonathan", "1", "1",
                "USA", "NSL", "birth", 1990);
        Event marriage = new Event("Jonathan", "Jonathan", "1", "1",
                "USA", "NSL", "marriage", 2018);
        Event death = new Event("Jonathan", "Jonathan", "1", "1",
                "USA", "NSL", "death", 2090);
        Event baptism = new Event("Jonathan", "Jonathan", "1", "1",
                "USA", "NSL", "baptism", 1998);
        events.add(birth);
        events.add(marriage);
        events.add(baptism);
        events.add(death);


        children.add("Joe");
        children.add("Fred");
        children.add("Bill");
    }

    public void generateEvents() {
        if(events.size() == 0) {
            for (Map.Entry<String, Event> entry : Model.getCurrentEventMap().entrySet()) {
                if (entry.getValue().getPersonID().equals(currentPerson.getPersonID())) {
                    EventChildListItem event = new EventChildListItem(
                            entry.getValue().getEventType(),
                            entry.getValue().getCity() +
                                    ", " +
                                    entry.getValue().getCountry(),
                            Integer.toString(entry.getValue().getYear()),
                            currentPerson.getFullName(),
                            entry.getKey()
                    );
                    events.add(event);
                }
            }
            Collections.sort(events, new Comparator<Object>() {
                @Override
                public int compare(Object event1, Object event2) {
                    EventChildListItem eventChildListItem1 = (EventChildListItem) event1;
                    EventChildListItem eventChildListItem2 = (EventChildListItem) event2;
                    return eventChildListItem1.compareTo(eventChildListItem2);

                }
            });
        }
    }

    public void generateFamily() {
        if (family.size() == 0) {
            if (currentPerson.getFather() != null) {
                PersonChildListItem father = new PersonChildListItem(
                        Model.getCurrentPeopleMap().get(currentPerson.getFather()).getFullName(),
                        "Father",
                        true,
                        currentPerson.getFather()
                );
                family.add(father);
            }
            if (currentPerson.getMother() != null) {
                PersonChildListItem mother = new PersonChildListItem(
                        Model.getCurrentPeopleMap().get(currentPerson.getMother()).getFullName(),
                        "Mother",
                        false,
                        currentPerson.getMother()
                );
                family.add(mother);
            }
            if(currentPerson.getSpouse() != null) {
                PersonChildListItem spouse = new PersonChildListItem(
                        Model.getCurrentPeopleMap().get(currentPerson.getSpouse()).getFullName(),
                        "Spouse",
                        Model.getCurrentPeopleMap().get(currentPerson.getSpouse()).isMale(),
                        currentPerson.getSpouse()
                );
                family.add(spouse);
            }
            for (String childId : children) {
                PersonChildListItem child = new PersonChildListItem(
                        Model.getCurrentPeopleMap().get(childId).getFullName(),
                        "Child",
                        Model.getCurrentPeopleMap().get(childId).isMale(),
                        childId
                );
                family.add(child);
            }
        }
    }

    public void generateLines(String eventId) {
        generateLifeStoryLine();
        generateFamilyTreeLine(eventId);
        generateSpouseLine(eventId);
    }

    private void generateLifeStoryLine() {
        PolylineOptions lifeStoryLine = new PolylineOptions();
        for(Object object : events) {
            EventChildListItem eventChildListItem = (EventChildListItem) object;
            Event event = Model.getCurrentEventMap().get(eventChildListItem.getId());
            lifeStoryLine.add(new LatLng(Double.parseDouble(event.getLatitude()),Double.parseDouble(event.getLongitude())))
                    .width(10f)
                    .color(Model.getSettings().getCurrentEventsColor());
        }
        this.eventLine = lifeStoryLine;
    }

    private void generateFamilyTreeLine(String eventId) {
        double lat = Double.parseDouble(Model.getCurrentEventMap().get(eventId).getLatitude());
        double lon = Double.parseDouble(Model.getCurrentEventMap().get(eventId).getLongitude());;
        LatLng position = new LatLng(lat, lon);
        PolylineOptions familyTreeLine = new PolylineOptions();
        familyTreeLine.add(position)
                .color(Model.getSettings().getCurrentFamilyTreeLineColor());
        familyTreeHelper(currentPerson, familyTreeLine, 20);
        this.familyTreeLine = familyTreeLine;
    }

    private void familyTreeHelper(Person currentPerson, PolylineOptions familyTreeLine, float width) {
        if(currentPerson.getFather() != null) {
            Person father = Model.getCurrentPeopleMap().get(currentPerson.getFather());
            FamilyTree fatherTree = new FamilyTree(father);
            fatherTree.generateEvents();
            if(fatherTree.getEvents().size() > 0) {
                EventChildListItem eventChildListItem = (EventChildListItem) fatherTree.getEvents().get(0);
                Event event = Model.getCurrentEventMap().get(eventChildListItem.getId());
                familyTreeLine.add(new LatLng(Double.parseDouble(event.getLatitude()),Double.parseDouble(event.getLongitude())))
                        .width(width);
            }
        }
        if(currentPerson.getMother() != null) {
            Person mother = Model.getCurrentPeopleMap().get(currentPerson.getMother());
            FamilyTree motherTree = new FamilyTree(mother);
            motherTree.generateEvents();
            if(motherTree.getEvents().size() > 0) {
                EventChildListItem eventChildListItem = (EventChildListItem) motherTree.getEvents().get(0);
                Event event = Model.getCurrentEventMap().get(eventChildListItem.getId());
                familyTreeLine.add(new LatLng(Double.parseDouble(event.getLatitude()),Double.parseDouble(event.getLongitude())))
                        .width(width);
            }
        }
        if(currentPerson.getFather() != null) {
            if((width - 2) > 0) {
                width -= 2;
            }
            familyTreeHelper(
                    Model.getCurrentPeopleMap().get(currentPerson.getFather()),
                    familyTreeLine,
                    width
            );
        }
        if(currentPerson.getMother() != null) {
            if((width - 2) > 0) {
                width -= 2;
            }
            familyTreeHelper(
                    Model.getCurrentPeopleMap().get(currentPerson.getMother()),
                    familyTreeLine,
                    width
            );
        }
    }

    private void generateSpouseLine(String eventId) {
        if(currentPerson.getSpouse() != null) {
            Person spouse = Model.getCurrentPeopleMap().get(currentPerson.getSpouse());
            FamilyTree spouseTree = new FamilyTree(spouse);
            spouseTree.generateEvents();
            if(spouseTree.getEvents().size() > 0) {
                PolylineOptions spouseLine = new PolylineOptions();
                EventChildListItem eventChildListItem = (EventChildListItem) spouseTree.getEvents().get(0);
                Event event = Model.getCurrentEventMap().get(eventChildListItem.getId());
                spouseLine.add(new LatLng(Double.parseDouble(event.getLatitude()),Double.parseDouble(event.getLongitude())))
                        .width(10f)
                        .color(Model.getSettings().getCurrentSpouseLineColor());
                spouseLine.add(
                        new LatLng(Double.parseDouble(Model.getCurrentEventMap().get(eventId).getLatitude()),
                                Double.parseDouble(Model.getCurrentEventMap().get(eventId).getLongitude()))
                );
                this.spouseLine = spouseLine;
            }
        }
    }

    public Person getCurrentPerson() {
        return currentPerson;
    }

    public void setCurrentPerson(Person currentPerson) {
        this.currentPerson = currentPerson;
    }

    public List<Object> getFamily() {
        return family;
    }

    public void setFamily(List<Object> family) {
        this.family = family;
    }

    public List<Object> getEvents() {
        return events;
    }

    public void setEvents(List<Object> events) {
        this.events = events;
    }

    public PolylineOptions getSpouseLine() {
        return spouseLine;
    }

    public void setSpouseLine(PolylineOptions spouseLine) {
        this.spouseLine = spouseLine;
    }

    public PolylineOptions getFamilyTreeLine() {
        return familyTreeLine;
    }

    public void setFamilyTreeLine(PolylineOptions familyTreeLine) {
        this.familyTreeLine = familyTreeLine;
    }

    public PolylineOptions getLifeStoryLine() {
        return eventLine;
    }

    public void setLifeStoryLine(PolylineOptions lifeStoryLine) {
        this.eventLine = lifeStoryLine;
    }

    public Set<String> getChildren() {
        return children;
    }

    public void setChildren(Set<String> children) {
        this.children = children;
    }

}
