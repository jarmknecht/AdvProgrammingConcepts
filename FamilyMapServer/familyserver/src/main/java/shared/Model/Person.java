package shared.Model;

import java.util.*;
//DONE with code

/**
 * Created by Jonathan on 2/12/18.
 */

/**
 * Class that creates a new person and holds the data
 * of a person object include a set of event objects that has happen
 * to this person
 */

public class Person {
    private String personID;
    private String descendant;
    private String firstName;
    private String lastName;
    private String gender;
    private String father;
    private String mother;
    private String spouse;
    private Set<Event> events;

    /**
     * Constructs a new person and creates a new HashSet of events
     * for the said person
     * Also calls a private helper function that makes unique id for each new person
     * @param firstName first name of new person
     * @param lastName last name of new person
     * @param gender m for male f for female
     */

    public Person(String firstName, String lastName, String gender) {
        setFirstName(firstName);
        setLastName(lastName);
        setGender(gender);
        this.events = new HashSet<>();
        generatePersonId();
    }

    /**
     * This adds a new event to the HashSet
     * @param event the event to add
     */

    public void addEvent(Event event) {
        this.events.add(event);
    }

    /**
     * Helper function that generates a unique id for a person object
     */

    public void generatePersonId() {
        this.personID = UUID.randomUUID().toString();
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    /**
     * Function that returns the full name of person
     * @return string that is the full name of person object
     */

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public boolean isMale() {
        if (getGender().equals("m")) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Creates a string with all of the data members in it
     * @return a string that contains all of the data members of a person object
     */

    @Override
    public String toString() {
        return "Person{" +
                "descendant = '" + descendant + '\'' +
                ", personID = '" + personID + '\'' +
                ", firstName = '" + firstName + '\'' +
                ", lastName = '" + lastName + '\'' +
                ", gender = '" + gender + '\'' +
                ", father = '" + father + '\'' +
                ", mother = '" + mother + '\'' +
                ", spouse = '" + spouse + '\'' +
                ", events = '" + events +
                '}';
    }

}
