package shared.Requests;
//Done with code
/**
 * Created by Jonathan on 2/12/18.
 */

/**
 * Gets requests from the server to do things with the EventDao
 */

public class EventRequest {
    private String eventId = null;

    public String getEventId() {
        return this.eventId;
    }

    /**
     * Constructor with parameters that sets the request from the server
     * to the eventId
     * @param eventId eventId that is the request
     */

    public EventRequest(String eventId) {
        this.eventId = eventId;
    }
}
