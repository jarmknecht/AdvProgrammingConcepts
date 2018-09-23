package recyclerview;

import shared.Model.Event;

public class EventChildListItem extends AbstractChildListItem implements Comparable<EventChildListItem> {
    public String description;
    public String location;
    public String year;
    public String name;
    public String eventId;

    public EventChildListItem(
            String description,
            String location,
            String year,
            String name,
            String eventId)
    {
        this.description = description;
        this.location = location;
        this.year = year;
        this.name = name;
        this.eventId = eventId;
    }

    @Override
    public int compareTo(EventChildListItem other) {
        return Integer.compare(
                Integer.valueOf(this.year),
                Integer.valueOf(other.year)
        );
    }

    @Override
    public String getTitle() {
        return description + ": " + location + " (" + year + ")";
    }

    @Override
    public String getSubTitle() {
        return name;
    }

    @Override
    public String getId() {
        return eventId;
    }

}
