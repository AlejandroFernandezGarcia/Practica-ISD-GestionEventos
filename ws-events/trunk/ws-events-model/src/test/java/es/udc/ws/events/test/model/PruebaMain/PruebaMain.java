package es.udc.ws.events.test.model.PruebaMain;

import java.util.Calendar;
import java.util.List;

import es.udc.ws.events.exceptions.EventRegisterUsersError;
import es.udc.ws.events.exceptions.InputDateError;
import es.udc.ws.events.exceptions.OverCapacityError;
import es.udc.ws.events.model.event.Event;
import es.udc.ws.events.model.eventservice.EventServiceImpl;
import es.udc.ws.events.model.response.Response;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class PruebaMain {

	/**
	 * @param args
	 * @throws InputDateError 
	 * @throws InputValidationException 
	 * @throws EventRegisterUsersError 
	 * @throws InstanceNotFoundException 
	 * @throws OverCapacityError 
	 */
	public static void main(String[] args) throws InputValidationException, InputDateError, InstanceNotFoundException, EventRegisterUsersError, OverCapacityError {
		EventServiceImpl serv = new EventServiceImpl();
		Calendar fechaIni1 = Calendar.getInstance();
		fechaIni1.set(2013, 1, 1);
		Calendar fechaFin1 = Calendar.getInstance();
		fechaFin1.set(2013, 1, 3);
		Event event = new Event("Evento1","Evento1 descripcion",fechaIni1,fechaFin1,true,"Calle 1",(short) 20);
		serv.addEvent(event);
		
		Calendar fechaIni2 = Calendar.getInstance();
		fechaIni2.set(2013, 1, 2);
		Calendar fechaFin2 = Calendar.getInstance();
		fechaFin2.set(2013, 1, 4);
		Event event2 = new Event("Evento2","Evento2 descripcion",fechaIni2,fechaFin2,true,"Calle 2",(short) 25);
		serv.addEvent(event2);
		
		Calendar fechaIni3 = Calendar.getInstance();
		fechaIni3.set(2013, 1, 3);
		Calendar fechaFin3 = Calendar.getInstance();
		fechaFin3.set(2013, 1, 5);
		Event event3 = new Event("Evento3","Evento3 descripcion",fechaIni3,fechaFin3,true,"Calle 3",(short) 10);
		serv.addEvent(event3);
		
		Calendar fechaIni4 = Calendar.getInstance();
		fechaIni4.set(2013, 1, 4);
		Calendar fechaFin4 = Calendar.getInstance();
		fechaFin4.set(2013, 1, 5);
		Event event4 = new Event("Evento4","Evento4 descripcion",fechaIni4,fechaFin4,true,"Calle 4",(short) 5);
		serv.addEvent(event4);
		
		event4.setCapacity((short) 2);
		serv.updateEvent(event4);
		
		serv.deleteEvent(event2.getEventId());
		
		serv.findEvent(event.getEventId()).toString();
		
		List evEncontrados1 = serv.findEventByKeyword("calle", null, null);
		List evEnontrados2 = serv.findEventByKeyword(null, fechaFin1, fechaIni4);
		
		Long responseId1 = serv.responseToEvent("alejandro", event4.getEventId(), true);
		serv.responseToEvent("alejandro2", event.getEventId(), true);
		serv.responseToEvent("alejandro3", event.getEventId(), true);
		serv.responseToEvent("alejandro4", event.getEventId(), true);
		
		List responses = serv.getResponses(event.getEventId(), true);
		
		Response resp = serv.getResponsesByID(responseId1);
		
	}

}
