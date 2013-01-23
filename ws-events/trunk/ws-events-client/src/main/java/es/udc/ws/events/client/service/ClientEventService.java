package es.udc.ws.events.client.service;

import java.util.Calendar;
import java.util.List;

import es.udc.ws.events.dto.EventDto;
import es.udc.ws.events.dto.ResponseDto;
import es.udc.ws.events.exceptions.EventRegisteredUsersException;
import es.udc.ws.events.exceptions.OverCapacityException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public interface ClientEventService {

	public Long addEvent(EventDto event) throws InputValidationException;

	public void updateEvent(EventDto event) throws InputValidationException,
			InstanceNotFoundException, EventRegisteredUsersException;

	public void deleteEvent(Long eventId) throws InstanceNotFoundException,
			EventRegisteredUsersException;

	public EventDto findEvent(Long eventId) throws InstanceNotFoundException;

	public List<EventDto> findEventByKeyword(String clave, Calendar fechaIni,
			Calendar fechaFin);

	public Long responseToEvent(String username, Long eventId, Boolean code)
			throws InstanceNotFoundException, OverCapacityException,
			EventRegisteredUsersException, InputValidationException;

	public List<ResponseDto> getResponses(Long eventId, Boolean code)
			throws InstanceNotFoundException;

	public ResponseDto getResponsesByID(Long responseId)
			throws InstanceNotFoundException;
}
