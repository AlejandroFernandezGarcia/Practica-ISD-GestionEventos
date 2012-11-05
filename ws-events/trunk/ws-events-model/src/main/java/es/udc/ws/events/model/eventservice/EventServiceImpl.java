package es.udc.ws.events.model.eventservice;

import java.util.Calendar;
import java.util.List;

import es.udc.ws.events.model.event.Event;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class EventServiceImpl implements EventService {

	public EventServiceImpl() {
	}

	@Override
	public Event addEvent(Event event) throws InputValidationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateEvent(Event event) throws InputValidationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteEvent(Long eventId) throws InstanceNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Event findEvent(Long eventId) throws InstanceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> findEventByKeyword(String clave, Calendar fechaIni,
			Calendar fechaFin) throws InstanceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
