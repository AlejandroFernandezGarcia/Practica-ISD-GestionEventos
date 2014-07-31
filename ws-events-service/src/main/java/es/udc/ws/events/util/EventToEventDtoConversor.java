package es.udc.ws.events.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.udc.ws.events.dto.EventDto;
import es.udc.ws.events.model.event.Event;

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
		int duration = getDurationFromCalendar(event.getDateSt(),
				event.getDateEnd());
		event.getDateSt().add(Calendar.MONTH, 1);
		Calendar dateSt = event.getDateSt();
		boolean intern = event.isIntern();
		String address = event.getAddress();
		short capacity = event.getCapacity();
		return new EventDto(eventId, name, description, dateSt, duration,
				intern, address, capacity);
	}

	public static Event toEvent(EventDto eventDto) {
		Long eventId = eventDto.getEventId();
		String name = eventDto.getName();
		String description = eventDto.getDescription();
		Calendar dateSt = eventDto.getDateSt();
		int duration = eventDto.getDuration();
		Calendar dateEnd = getCalendarFromDuration(duration, dateSt);
		boolean intern = eventDto.isIntern();
		String address = eventDto.getAddress();
		short capacity = eventDto.getCapacity();
		return new Event(eventId, name, description, dateSt, dateEnd, intern,
				address, capacity);
	}

	public static int getDurationFromCalendar(Calendar date1, Calendar date2) {
		Long dist = (date2.getTime().getTime()) - (date1.getTime().getTime());
		int duration = (int) (dist / 60000);
		return duration;
	}

	public static Calendar getCalendarFromDuration(int duration, Calendar date) {
		Long dateEndMilis = date.getTime().getTime()
				+ ((long) duration * (long) 60000);
		Calendar dateEnd = Calendar.getInstance();
		dateEnd.setTimeInMillis(dateEndMilis);
		// Calendar prueba1 = Calendar.getInstance();
		// String l = "2764800000";
		// prueba1.setTimeInMillis(Long.valueOf(l));
		// System.out.println("Este"+prueba1.toString());

		return dateEnd;
	}
}
