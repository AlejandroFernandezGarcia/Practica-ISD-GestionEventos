package es.udc.ws.events.util;

import es.udc.ws.events.dto.EventDto;
import es.udc.ws.events.model.event.Event;

import java.util.ArrayList;
import java.util.Calendar;
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
    	Long eventId = event.getEventId();
        String name = event.getName();
        String description = event.getDescription();
        Calendar dateSt = event.getDateSt();
        Long dist = event.getDateEnd().getTimeInMillis() - event.getDateSt().getTimeInMillis();
        int duration =(int) (dist / 60000);
        boolean intern = event.isIntern();
        String address = event.getAddress();
        short capacity = event.getCapacity();
        return new EventDto(eventId, name,description, dateSt, duration, intern, address, capacity);
    }

    public static Event toEvent(EventDto eventDto) {
    	Long eventId = eventDto.getEventId();
        String name = eventDto.getName();
        String description = eventDto.getDescription();
        Calendar dateSt = eventDto.getDateSt();
        int duration = eventDto.getDuration();
        Long dateEndMilis = dateSt.getTimeInMillis() + (duration*60000);
        Calendar dateEnd = Calendar.getInstance();
        dateEnd.setTimeInMillis(dateEndMilis);
        boolean intern = eventDto.isIntern();
        String address = eventDto.getAddress();
        short capacity = eventDto.getCapacity();
        return new Event(eventId, name, description, dateSt, dateEnd, intern, address, capacity);
    }

}
