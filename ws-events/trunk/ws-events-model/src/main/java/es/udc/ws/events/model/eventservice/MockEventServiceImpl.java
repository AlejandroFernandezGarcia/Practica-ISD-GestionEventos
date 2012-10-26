package es.udc.ws.events.model.eventservice;

import es.udc.ws.events.model.event.Event;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class MockEventServiceImpl implements EventService {

	public MockEventServiceImpl() {
	}

	@Override
	public Event addEvent(Event event) throws InputValidationException {
		return new Event(1L, event.getName(), event.getDescription());
	}

	@Override
	public Event findEvent(Long eventId) throws InstanceNotFoundException {
		return new Event(eventId, "Event name", "Event description");
	}

}
