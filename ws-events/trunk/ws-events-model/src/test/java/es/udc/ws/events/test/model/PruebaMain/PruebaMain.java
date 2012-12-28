package es.udc.ws.events.test.model.PruebaMain;

import static es.udc.ws.events.model.util.ModelConstants.TEMPLATE_DATA_SOURCE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import javax.sql.DataSource;

import es.udc.ws.events.exceptions.EventRegisteredUsersException;
import es.udc.ws.events.exceptions.OverCapacityException;
import es.udc.ws.events.model.event.Event;
import es.udc.ws.events.model.eventservice.EventService;
import es.udc.ws.events.model.eventservice.EventServiceFactory;
import es.udc.ws.events.model.response.Response;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.sql.SimpleDataSource;

public class PruebaMain {

	/**
	 * @param args
	 * @throws InputDateError
	 * @throws InputValidationException
	 * @throws EventRegisteredUsersException
	 * @throws InstanceNotFoundException
	 * @throws OverCapacityException
	 * @throws IOException
	 */
	public static void main(String[] args) throws InputValidationException,
			InstanceNotFoundException, EventRegisteredUsersException,
			OverCapacityException, IOException {
		/*
		 * Create a simple data source and add it to "DataSourceLocator" (this
		 * is needed to test "es.udc.ws.events.model.eventservice.EventService"
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		DataSource dataSource = new SimpleDataSource();

		/*
		 * Add "dataSource" to "DataSourceLocator".
		 */
		DataSourceLocator.addDataSource(TEMPLATE_DATA_SOURCE, dataSource);

		EventService serv = EventServiceFactory.getService();
		Calendar fechaIni1 = Calendar.getInstance();
		fechaIni1.set(2013, 1, 1);
		Calendar fechaFin1 = Calendar.getInstance();
		fechaFin1.set(2013, 1, 3);
		Event event1 = new Event("tarea comer manzana", "Evento1 descripcion",
				fechaIni1, fechaFin1, false, "Calle 1", (short) 20);
		event1 = serv.addEvent(event1);

		Calendar fechaIni2 = Calendar.getInstance();
		fechaIni2.set(2013, 1, 2);
		Calendar fechaFin2 = Calendar.getInstance();
		fechaFin2.set(2013, 1, 4);
		Event event2 = new Event("manzana de caramelo", "Evento2 descripcion",
				fechaIni2, fechaFin2, true, "Calle 2", (short) 25);
		event2 = serv.addEvent(event2);

		Calendar fechaIni3 = Calendar.getInstance();
		fechaIni3.set(2013, 1, 3);
		Calendar fechaFin3 = Calendar.getInstance();
		fechaFin3.set(2013, 1, 5);
		Event event3 = new Event("caramelo", "Evento3 descripcion", fechaIni3,
				fechaFin3, true, "Calle 3", (short) 10);
		event3 = serv.addEvent(event3);

		Calendar fechaIni4 = Calendar.getInstance();
		fechaIni4.set(2013, 1, 4);
		Calendar fechaFin4 = Calendar.getInstance();
		fechaFin4.set(2013, 1, 5);
		Event event4 = new Event("evento4", "Evento4 descripcion", fechaIni4,
				fechaFin4, true, "Calle 4", (short) 5);
		event4 = serv.addEvent(event4);
		System.out.println(event4.getEventId() + " " + event4.getName());
		/*
		 * Event event1 = serv.findEvent((long) 1); Event event2 =
		 * serv.findEvent((long) 2); Event event3 = serv.findEvent((long) 3);
		 * Event event4 = serv.findEvent((long) 4);
		 */
		System.out.println(event1.toString());
		System.out.println(event2.toString());
		System.out.println(event3.toString());
		System.out.println(event4.toString());

		System.out.println("Prueba para actualizacion de eventos");
		event1.setCapacity((short) 2);
		serv.updateEvent(event1);
		event1 = serv.findEvent((long) 1);
		if ((event1.getCapacity()) == ((short) 2)) {
			System.out.println("Actualizado correctamente");
		} else {
			System.out.println("Error actualizado");
		}

		System.out.println("Prueba para borrado de eventos");

		Calendar fechaIni5 = Calendar.getInstance();
		fechaIni5.set(2013, 1, 1);
		Calendar fechaFin5 = Calendar.getInstance();
		fechaFin5.set(2013, 1, 3);
		Event event5 = new Event("prueba borrar", "borrarn", fechaIni5,
				fechaFin5, false, "Calle 1", (short) 20);
		event5 = serv.addEvent(event5);
		// reader.readLine();
		serv.deleteEvent(event5.getEventId());

		System.out.println("Busqueda de eventos by keywords (keywords)");

		ArrayList<Event> eventsFound = (ArrayList<Event>) serv
				.findEventByKeyword("caramelo", null, null);
		int i = 0;
		while (i < eventsFound.size()) {
			System.out.println(eventsFound.get(i).toString());
			i++;
		}
		System.out.println("Busqueda de eventos by keywords(date)");
		eventsFound.clear();
		Calendar dateTest1 = Calendar.getInstance();
		dateTest1.set(2013, 1, 2);
		Calendar dateTest2 = Calendar.getInstance();
		dateTest2.set(2013, 1, 4);
		eventsFound = (ArrayList<Event>) serv.findEventByKeyword(null,
				dateTest1, dateTest2);
		i = 0;
		while (i < eventsFound.size()) {
			System.out.println(eventsFound.get(i).toString());
			i++;
		}
		System.out.println("Busqueda de eventos by keywords (keywords & date)");
		eventsFound.clear();
		Calendar dateTest3 = Calendar.getInstance();
		dateTest3.set(2013, 1, 2);
		Calendar dateTest4 = Calendar.getInstance();
		dateTest4.set(2013, 1, 5);
		eventsFound = (ArrayList<Event>) serv.findEventByKeyword("caramelo",
				dateTest3, dateTest4);
		i = 0;
		while (i < eventsFound.size()) {
			System.out.println(eventsFound.get(i).toString());
			i++;
		}

		System.out.println("Creando respuestas");
		serv.responseToEvent("Alejandro", event1.getEventId(), true);
		serv.responseToEvent("Francisco", event1.getEventId(), true);
		serv.responseToEvent("Pepe", event3.getEventId(), true);
		serv.responseToEvent("Xacobe", event3.getEventId(), false);
		serv.responseToEvent("Daniel", event3.getEventId(), true);
		serv.responseToEvent("Maria", event3.getEventId(), false);
		serv.responseToEvent("Alex", event3.getEventId(), true);
		serv.responseToEvent("Juan Carlos", event3.getEventId(), true);
		serv.responseToEvent("Adrian", event4.getEventId(), true);

		System.out.println("Obtener respuestas pare evento");
		System.out.println("Respuestas afirmativas al evento 3");
		ArrayList<Response> listResp = (ArrayList<Response>) serv.getResponses(
				event3.getEventId(), true);
		i = 0;
		while (i < listResp.size()) {
			System.out.println(listResp.get(i).toString());
			i++;
		}
		System.out.println("Respuestas negativas al evento 3");
		listResp = (ArrayList<Response>) serv.getResponses(event3.getEventId(),
				false);
		i = 0;
		while (i < listResp.size()) {
			System.out.println(listResp.get(i).toString());
			i++;
		}
		System.out.println("Respuestas afirmativas-negativas al evento 3");
		listResp = (ArrayList<Response>) serv.getResponses(event3.getEventId(),
				null);
		i = 0;
		while (i < listResp.size()) {
			System.out.println(listResp.get(i).toString());
			i++;
		}

		System.out.println("Obtener respuestas pare evento por ID");
		Response resp1 = serv.getResponsesByID((long) 1);
		System.out.println(resp1.toString());
		Response resp2 = serv.getResponsesByID((long) 2);
		System.out.println(resp2.toString());
		Response resp3 = serv.getResponsesByID((long) 3);
		System.out.println(resp3.toString());
	}

}
