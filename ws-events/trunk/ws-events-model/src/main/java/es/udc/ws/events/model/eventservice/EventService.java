package es.udc.ws.events.model.eventservice;

import java.util.Calendar;
import java.util.List;

import es.udc.ws.events.exceptions.EventRegisteredUsersException;
import es.udc.ws.events.exceptions.OverCapacityException;
import es.udc.ws.events.model.event.Event;
import es.udc.ws.events.model.response.Response;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public interface EventService {

	public Event addEvent(Event event) throws InputValidationException;

	public void updateEvent(Event event) throws InputValidationException,
			InstanceNotFoundException, EventRegisteredUsersException;

	public void deleteEvent(Long eventId) throws InstanceNotFoundException,
			EventRegisteredUsersException;

	public Event findEvent(Long eventId) throws InstanceNotFoundException;

	public List<Event> findEventByKeyword(String clave, Calendar fechaIni,
			Calendar fechaFin);

	public Long responseToEvent(String username, Long eventId, Boolean code)
			throws InstanceNotFoundException, OverCapacityException,
			EventRegisteredUsersException;

	public List<Response> getResponses(Long eventId, Boolean code)
			throws InstanceNotFoundException;

	public Response getResponsesByID(Long responseId)
			throws InstanceNotFoundException;
}
