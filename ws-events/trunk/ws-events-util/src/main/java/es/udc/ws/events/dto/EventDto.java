package es.udc.ws.events.dto;

public class EventDto {

    private Long eventId;
    private String name;

    public EventDto() {
    }

    public EventDto(Long eventId, String name) {
        super();
        this.eventId = eventId;
        this.name = name;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "EventDto [eventId=" + eventId + ", name=" + name + "]";
    }
}
