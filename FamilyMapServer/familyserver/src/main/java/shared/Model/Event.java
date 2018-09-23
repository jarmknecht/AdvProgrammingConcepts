package shared.Model;
//DONE with code

/**
 * Created by Jonathan on 2/12/18.
 */

import java.util.*;

/**
 * Class makes event objects that happen to a personID
 * These events include birth, death, marriage, baptism etc.
 */

public class Event {

    private String eventID;
    private String descendant;
    private String personID;
    private String latitude;
    private String longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;

    /**
     * Constructor for an Event object calls private function that creates a unique ID for event
     * @param descendant name of the user who the personID belongs to
     * @param personID ID of the personID event will go to
     * @param latitude latitude on map where event occurred
     * @param longitude longitude on map where event occurred
     * @param country country where event occurred
     * @param city city where event occurred
     * @param eventType birth, death, marriage etc
     * @param year year event occurred
     */

    public Event(String descendant, String personID, String latitude, String longitude, String country,
                 String city, String eventType, int year) {
        generateEventId();
        setDescendant(descendant);
        setPersonID(personID);
        setLatitude(latitude);
        setLongitude(longitude);
        setCountry(country);
        setCity(city);
        setEventType(eventType);
        setYear(year);
    }

    /**
     * Helper function that uses UUID to generate a custom ID for event
     */

    public void generateEventId() {
        this.eventID = UUID.randomUUID().toString();
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Generates a string with Event data members as string
     * @return Returns event data members as one string
     */

    @Override
    public String toString() {
        return "Event{" +
                "descendant = '" + descendant + '\'' +
                ", eventID = '" + eventID + '\'' +
                " , personID = '" + personID + '\'' +
                ", latitude = '" + latitude + '\'' +
                ", longitude = '" + longitude + '\'' +
                ", country = '" + country + '\'' +
                ", city = '" + city + '\'' +
                ", eventType = '" + eventType + '\'' +
                ", year = " + year +
                '}';
    }
}
