package shared.Results;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jonathan on 2/12/18.
 */
//DOne with code
/**
 * Returns an eventresult object that is in JSON format
 */

public class EventResult {
    @SerializedName("descendant")
    private String descendant = null;
    @SerializedName("eventID")
    private String eventID = null;
    @SerializedName("personID")
    private String personID = null;
    @SerializedName("latitude")
    private String latitude = null;
    @SerializedName("longitude")
    private String longitude = null;
    @SerializedName("country")
    private String country = null;
    @SerializedName("city")
    private String city = null;
    @SerializedName("eventType")
    private String eventType = null;
    @SerializedName("year")
    private int year = 0;

    private String message = null;

    /**
     * Error message constructor
     * @param message error message
     */

    public EventResult(String message) {
        this.message = message;
    }

    /**
     * Success message constructor
     * @param descendant user
     * @param eventId id of event
     * @param personId id of ancestor
     * @param latitude where event occurred in latitude
     * @param longitude where event occurred in longitude
     * @param country country where event happened
     * @param city city where event happened
     * @param eventType what the event was
     * @param year year event happened
     */

    public EventResult(String descendant, String eventId, String personId, String latitude, String longitude, String country, String city,
                       String eventType, int year) {
        this.descendant = descendant;
        this.eventID = eventId;
        this.personID = personId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public String getDescendant() {
        return descendant;
    }

    public String getEventId() {
        return eventID;
    }

    public String getPersonId() {
        return personID;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getEventType() {
        return eventType;
    }

    public int getYear() {
        return year;
    }

    /**
     * Makes the JSON object to return
     * @return a Json string object
     */

    @Override
    public String toString() {
        if (eventID != null) {
            return "{" +
                    "descendant = '" + descendant + '\'' +
                    ", eventID = '" + eventID + '\'' +
                    ", personID = '" + personID + '\'' +
                    ", latitude = '" + latitude + '\'' +
                    ", longitude = '" + longitude + '\'' +
                    ", country = '" + country + '\'' +
                    ", city = '" + city + '\'' +
                    ", eventType = '" + eventType + '\'' +
                    ", year = " + year +
                    '}';
        }
        else {
            JsonObject object = new JsonObject();
            object.addProperty("message", message);
            return object.toString();
        }
    }
}
