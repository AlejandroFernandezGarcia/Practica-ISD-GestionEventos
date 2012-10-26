package es.udc.ws.events.model.eventservice;

import es.udc.ws.events.model.event.Event;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public interface EventService {

    public Event addEvent(Event event) throws InputValidationException;

    public Event findEvent(Long eventId) throws InstanceNotFoundException;
}
