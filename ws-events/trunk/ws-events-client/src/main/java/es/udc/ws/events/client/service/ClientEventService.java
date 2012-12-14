package es.udc.ws.events.client.service;

import es.udc.ws.events.dto.EventDto;
import es.udc.ws.util.exceptions.InputValidationException;

public interface ClientEventService {

    public Long addEvent(EventDto event) throws InputValidationException;

    //public EventDto findEvent(Long eventId) throws InstanceNotFoundException;

}
