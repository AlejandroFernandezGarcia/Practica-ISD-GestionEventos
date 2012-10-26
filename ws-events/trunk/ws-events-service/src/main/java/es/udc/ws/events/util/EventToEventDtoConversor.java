package es.udc.ws.events.util;

import es.udc.ws.events.dto.EventDto;
import es.udc.ws.events.model.event.Event;

import java.util.ArrayList;
import java.util.List;

public class EventToEventDtoConversor {

    public static List<EventDto> toEventDtos(List<Event> events) {
        List<EventDto> eventDtos = new ArrayList<>(events.size());
        for (int i = 0; i < events.size(); i++) {
            Event event = events.get(i);
            eventDtos.add(toEventDto(event));
        }
        return eventDtos;
    }

    public static EventDto toEventDto(Event event) {
        return new EventDto(event.getEventId(), event.getName());
    }

    public static Event toEvent(EventDto eventDto) {
        return new Event(eventDto.getEventId(), eventDto.getName(), null);
    }

}
