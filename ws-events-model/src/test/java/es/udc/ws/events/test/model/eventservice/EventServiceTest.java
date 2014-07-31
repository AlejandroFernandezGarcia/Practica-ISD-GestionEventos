package es.udc.ws.events.test.model.eventservice;

import static es.udc.ws.events.model.util.ModelConstants.TEMPLATE_DATA_SOURCE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.Test;

import es.udc.ws.events.exceptions.EventRegisteredUsersException;
import es.udc.ws.events.exceptions.OverCapacityException;
import es.udc.ws.events.model.event.Event;
import es.udc.ws.events.model.event.SqlEventDao;
import es.udc.ws.events.model.event.SqlEventDaoFactory;
import es.udc.ws.events.model.eventservice.EventService;
import es.udc.ws.events.model.eventservice.EventServiceFactory;
import es.udc.ws.events.model.response.Response;
import es.udc.ws.events.model.response.SqlResponseDao;
import es.udc.ws.events.model.response.SqlResponseDaoFactory;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.sql.SimpleDataSource;

public class EventServiceTest {
	EventService serv = EventServiceFactory.getService();

	private static SqlEventDao eventDao = null;
	private static SqlResponseDao responseDao = null;

	@BeforeClass
	public static void init() {

		/*
		 * Create a simple data source and add it to "DataSourceLocator" (this
		 * is needed to test "es.udc.ws.events.model.eventservice.EventService"
		 */
		DataSource dataSource = new SimpleDataSource();

		/*
		 * Add "dataSource" to "DataSourceLocator".
		 */
		DataSourceLocator.addDataSource(TEMPLATE_DATA_SOURCE, dataSource);
		eventDao = SqlEventDaoFactory.getDao();
		responseDao = SqlResponseDaoFactory.getDao();
		cleanDB();

	}
	private static void cleanDB(){
		deleteEvents();
		deleteResponses();
	}
	
	private static void deleteEvents() {
		long i = 0;
		DataSource dataSource = DataSourceLocator
				.getDataSource(TEMPLATE_DATA_SOURCE);
		while (i < 50) {
			try (Connection connection = dataSource.getConnection()) {
				eventDao.delete(connection, i);
			} catch (SQLException | InstanceNotFoundException e) {

			}
			i++;
		}
	}

	private static void deleteResponses() {
		long i = 0;
		DataSource dataSource = DataSourceLocator
				.getDataSource(TEMPLATE_DATA_SOURCE);
		while (i < 50) {
			try (Connection connection = dataSource.getConnection()) {
				responseDao.delete(connection, i);
			} catch (SQLException | InstanceNotFoundException e) {

			}
			i++;
		}
	}

	// Prueba el validateEvent y el anadir un evento correctamente
	@Test
	public void testAddEventAndFindEvent() {
		cleanDB();
		Calendar fechaIni1 = Calendar.getInstance();
		fechaIni1.set(2013, 1, 1, 0, 0, 0);
		Calendar fechaFin1 = Calendar.getInstance();
		fechaFin1.set(2013, 1, 3, 0, 0, 0);
		Event event1 = new Event("tarea comer manzana", "Evento1 descripcion",
				fechaIni1, fechaFin1, false, "Calle 1", (short) 20);
		try {
			event1 = serv.addEvent(event1);
			Event event1b = serv.findEvent(event1.getEventId());
			assertEquals("---->testAddEventAndFindEvent error<----", event1,
					event1b);
		} catch (InstanceNotFoundException | InputValidationException e) {
			cleanDB();
			assertTrue(false);
		}
		cleanDB();
	}

	// Prueba dentro del validateEvent el validateMandatoryString
	@Test
	public void testValidateEvent() {
		cleanDB();
		Calendar fechaIni1 = Calendar.getInstance();
		fechaIni1.set(2013, 1, 1, 0, 0, 0);
		Calendar fechaFin1 = Calendar.getInstance();
		fechaFin1.set(2013, 1, 3, 0, 0, 0);
		Event event1 = new Event("", "Evento1 descripcion", fechaIni1,
				fechaFin1, false, "Calle 1", (short) 20);
		try {
			event1 = serv.addEvent(event1);
			cleanDB();
			assertTrue(false);
		} catch (InputValidationException e) {
			assertTrue(true);
		}
	}

	// valida que las fechas de un evento sean correctas
	@Test
	public void testValidateEvent2() {
		cleanDB();
		Calendar fechaIni1 = Calendar.getInstance();
		fechaIni1.set(2013, 1, 1, 0, 0, 0);
		Calendar fechaFin1 = Calendar.getInstance();
		fechaFin1.set(2012, 1, 3, 0, 0, 0);
		Event event1 = new Event("tarea comer manzana", "Evento1 descripcion",
				fechaIni1, fechaFin1, false, "Calle 1", (short) 20);
		try {
			event1 = serv.addEvent(event1);
			cleanDB();
			assertTrue(false);
		} catch (InputValidationException e) {
			assertTrue(true);
		}
	}

	// valida el campo addres
	@Test
	public void testValidateEvent3() {
		cleanDB();
		Calendar fechaIni1 = Calendar.getInstance();
		fechaIni1.set(2013, 1, 1, 0, 0, 0);
		Calendar fechaFin1 = Calendar.getInstance();
		fechaFin1.set(2012, 1, 3, 0, 0, 0);
		Event event1 = new Event("tarea comer manzana", "Evento1 descripcion",
				fechaIni1, fechaFin1, false, "", (short) 20);
		try {
			event1 = serv.addEvent(event1);
			cleanDB();
			assertTrue(false);
		} catch (InputValidationException e) {
			assertTrue(true);
		}
	}

	// valida el campo capacity
	@Test
	public void testValidateEvent4() {
		cleanDB();
		Calendar fechaIni1 = Calendar.getInstance();
		fechaIni1.set(2013, 1, 1, 0, 0, 0);
		Calendar fechaFin1 = Calendar.getInstance();
		fechaFin1.set(2012, 1, 3, 0, 0, 0);
		Event event1 = new Event("tarea comer manzana", "Evento1 descripcion",
				fechaIni1, fechaFin1, false, "Calle 1", (short) 0);
		try {
			event1 = serv.addEvent(event1);
			cleanDB();
			assertTrue(false);
		} catch (InputValidationException e) {
			assertTrue(true);
		}
	}

	// valida que la fecha de inicio sea posterior a la actual
	@Test
	public void testValidateEvent5() {
		cleanDB();
		Calendar fechaIni1 = Calendar.getInstance();
		fechaIni1.set(2011, 1, 1, 0, 0, 0);
		Calendar fechaFin1 = Calendar.getInstance();
		fechaFin1.set(2012, 1, 3, 0, 0, 0);
		Event event1 = new Event("tarea comer manzana", "Evento1 descripcion",
				fechaIni1, fechaFin1, false, "Calle 1", (short) 20);
		try {
			event1 = serv.addEvent(event1);
			cleanDB();
			assertTrue(false);
		} catch (InputValidationException e) {
			assertTrue(true);
		}
	}

	// valida que se actualicen correctamente los eventos
	@Test
	public void testUpdateEvent() {
		cleanDB();
		Calendar fechaIni1 = Calendar.getInstance();
		fechaIni1.set(2013, 1, 1, 0, 0, 0);
		Calendar fechaFin1 = Calendar.getInstance();
		fechaFin1.set(2013, 1, 3, 0, 0, 0);
		Event event1 = new Event("tarea comer manzana", "Evento1 descripcion",
				fechaIni1, fechaFin1, false, "Calle 1", (short) 20);
		try {
			event1 = serv.addEvent(event1);
		} catch (InputValidationException e) {
			cleanDB();
			assertTrue(false);
		}
		try {
			Event event = serv.findEvent(event1.getEventId());
			event.setCapacity((short) 2);
			serv.updateEvent(event);
			event = serv.findEvent(event.getEventId());
			assertEquals("---->testUpdateEvent error<----",
					event.getCapacity(), (short) 2);
		} catch (InstanceNotFoundException | InputValidationException
				| EventRegisteredUsersException e) {
			cleanDB();
			assertTrue(false);
		}
		cleanDB();
	}

	// comprueba el caso de no encontrar el event a actualizar
	@Test
	public void testUpdateEvent2() {
		cleanDB();
		try {
			Calendar fechaIni1 = Calendar.getInstance();
			fechaIni1.set(2013, 1, 1, 0, 0, 0);
			Calendar fechaFin1 = Calendar.getInstance();
			fechaFin1.set(2013, 1, 3, 0, 0, 0);
			Event event1 = new Event("tarea comer manzana", "Evento1 descripcion",
					fechaIni1, fechaFin1, false, "Calle 1", (short) 20);
			event1.setEventId((long) 50000);
			serv.updateEvent(event1);
			cleanDB();
			assertTrue(false);
		} catch (InstanceNotFoundException e) {
			cleanDB();
			assertTrue(true);
		} catch (InputValidationException | EventRegisteredUsersException e) {
			cleanDB();
			assertTrue(false);
		}
		cleanDB();
	}

	// comprueba que no se pueda actualizar un evento con usuarios registrados
	@Test
	public void testUpdateEvent3() {
		cleanDB();
		Calendar fechaIni1 = Calendar.getInstance();
		fechaIni1.set(2013, 1, 1, 0, 0, 0);
		Calendar fechaFin1 = Calendar.getInstance();
		fechaFin1.set(2013, 1, 3, 0, 0, 0);
		Event event1 = new Event("tarea comer manzana", "Evento1 descripcion",
				fechaIni1, fechaFin1, false, "Calle 1", (short) 20);
		try {
			event1 = serv.addEvent(event1);
		} catch (InputValidationException e) {
			assertTrue(false);
		}
		try {
			serv.responseToEvent("Pepe", event1.getEventId(), true);
			serv.updateEvent(event1);
			cleanDB();
			assertTrue(false);
		} catch (InstanceNotFoundException | InputValidationException | OverCapacityException e) {
			cleanDB();
			assertTrue(false);
		} catch (EventRegisteredUsersException e) {
			cleanDB();
			assertTrue(true);
		}
		cleanDB();
	}

	// elimina un evento de forma correcta
	@Test
	public void testdeleteEvent() {
		cleanDB();
		Event event1 = null;
		try {
			EventService serv = EventServiceFactory.getService();
			Calendar fechaIni1 = Calendar.getInstance();
			fechaIni1.set(2013, 1, 1, 0, 0, 0);
			Calendar fechaFin1 = Calendar.getInstance();
			fechaFin1.set(2013, 1, 3, 0, 0, 0);
			event1 = new Event("Prueba para borrar", "b", fechaIni1, fechaFin1,
					false, "Calle 1", (short) 20);
			event1 = serv.addEvent(event1);
			serv.deleteEvent(event1.getEventId());
		} catch (InstanceNotFoundException | EventRegisteredUsersException
				| InputValidationException e) {
			cleanDB();
			assertTrue(false);
		}
		try {
			serv.findEvent(event1.getEventId());
			cleanDB();
			assertTrue(false);
		} catch (InstanceNotFoundException e) {
			cleanDB();
			assertTrue(true);
		}
		cleanDB();
	}

	// intenta eliminar un evento que no existe
	@Test
	public void testdeleteEvent2() {
		cleanDB();
		try {
			serv.deleteEvent((long) 5);
			assertTrue(false);
		} catch (InstanceNotFoundException e) {
			assertTrue(true);
		} catch (EventRegisteredUsersException e) {
			assertTrue(false);
		}
	}

	// intenta eliminar un evento con usuarios registrados
	@Test
	public void testdeleteEvent3() {
		cleanDB();
		EventService serv = EventServiceFactory.getService();
		Calendar fechaIni1 = Calendar.getInstance();
		fechaIni1.set(2013, 1, 1, 0, 0, 0);
		Long eventId = null;
		Calendar fechaFin1 = Calendar.getInstance();
		fechaFin1.set(2013, 1, 3, 0, 0, 0);
		Event event1 = new Event("tarea comer manzana", "Evento1 descripcion",
				fechaIni1, fechaFin1, false, "Calle 1", (short) 20);
		try {
			event1 = serv.addEvent(event1);
			eventId = event1.getEventId();
			serv.responseToEvent("Milan", eventId, true);
		} catch (InputValidationException | InstanceNotFoundException
				| OverCapacityException | EventRegisteredUsersException e) {
			cleanDB();
			assertTrue(false);
		}
		try {
			serv.deleteEvent(eventId);
			cleanDB();
			assertTrue(false);
		} catch (EventRegisteredUsersException e) {
			cleanDB();
			assertTrue(true);
		} catch (InstanceNotFoundException e) {
			cleanDB();
			assertTrue(false);
		}
		cleanDB();
	}

	// Comprueba que devuelve en caso de no encontrar el evento que buscaba
	@Test
	public void testFindEvent() {
		cleanDB();
		try {
			serv.findEvent((long) 20);
			assertTrue(false);
		} catch (InstanceNotFoundException e) {
			assertTrue(true);
		}
	}

	// Comprueba que devuelve una busqueda correcta buscando por solo la clave
	@Test
	public void testFindEventByKeywords() {
		cleanDB();
		try {
			Calendar fechaIni2 = Calendar.getInstance();
			fechaIni2.set(2013, 1, 2,15,15,15);
			Calendar fechaFin2 = Calendar.getInstance();
			fechaFin2.set(2013, 1, 4,15,15,15);
			Event event2 = new Event("manzana de caramelo",
					"Evento2 descripcion", fechaIni2, fechaFin2, true,
					"Calle 2", (short) 25);
			event2 = serv.addEvent(event2);

			Calendar fechaIni3 = Calendar.getInstance();
			fechaIni3.set(2013, 1, 3,15,15,15);
			Calendar fechaFin3 = Calendar.getInstance();
			fechaFin3.set(2013, 1, 5,15,15,15);
			Event event3 = new Event("caramelo", "Evento3 descripcion",
					fechaIni3, fechaFin3, true, "Calle 3", (short) 10);
			event3 = serv.addEvent(event3);

			Calendar fechaIni4 = Calendar.getInstance();
			fechaIni4.set(2013, 1, 4,15,15,15);
			Calendar fechaFin4 = Calendar.getInstance();
			fechaFin4.set(2013, 1, 5,15,15,15);
			Event event4 = new Event("evento4", "Evento4 descripcion",
					fechaIni4, fechaFin4, true, "Calle 4", (short) 5);
			event4 = serv.addEvent(event4);

			ArrayList<Event> eventsFound = (ArrayList<Event>) serv
					.findEventByKeyword("caramelo", null, null);
			assertEquals("---->testFindEventByKeywords error<----",
					eventsFound.size(), 2);
		} catch (InputValidationException e) {
			cleanDB();
			assertTrue(false);
		}
		cleanDB();
	}

	// Comprueba el caso de busqueda de eventos por fecha
	@Test
	public void testFindEventByKeywords2() {
		cleanDB();
		try {
			Calendar fechaIni2 = Calendar.getInstance();
			fechaIni2.set(2013, 1, 2,15,15,15);
			Calendar fechaFin2 = Calendar.getInstance();
			fechaFin2.set(2013, 1, 4,15,15,15);
			Event event2 = new Event("manzana de caramelo",
					"Evento2 descripcion", fechaIni2, fechaFin2, true,
					"Calle 2", (short) 25);
			event2 = serv.addEvent(event2);

			Calendar fechaIni3 = Calendar.getInstance();
			fechaIni3.set(2013, 1, 3,15,15,15);
			Calendar fechaFin3 = Calendar.getInstance();
			fechaFin3.set(2013, 1, 5,15,15,15);
			Event event3 = new Event("caramelo", "Evento3 descripcion",
					fechaIni3, fechaFin3, true, "Calle 3", (short) 10);
			event3 = serv.addEvent(event3);

			Calendar fechaIni4 = Calendar.getInstance();
			fechaIni4.set(2013, 1, 4,15,15,15);
			Calendar fechaFin4 = Calendar.getInstance();
			fechaFin4.set(2013, 1, 5,15,15,15);
			Event event4 = new Event("evento4", "Evento4 descripcion",
					fechaIni4, fechaFin4, true, "Calle 4", (short) 5);
			event4 = serv.addEvent(event4);
		} catch (InputValidationException e) {
			cleanDB();
		}
		Calendar dateTest1 = Calendar.getInstance();
		dateTest1.set(2013, 1, 2,14,14,14);
		Calendar dateTest2 = Calendar.getInstance();
		dateTest2.set(2013, 1, 4,16,16,16);
		ArrayList<Event> eventsFound = (ArrayList<Event>) serv
				.findEventByKeyword(null, dateTest1, dateTest2);
		assertEquals("---->testFindEventByKeywords1 error<----",
				eventsFound.size(), 3);
		cleanDB();
	}

	// Comprueba el caso de busqueda de eventos por fecha y clave
	@Test
	public void testFindEventBykeywords3() {
		cleanDB();
		try {
			Calendar fechaIni2 = Calendar.getInstance();
			fechaIni2.set(2013, 1, 3,15,15,15);
			Calendar fechaFin2 = Calendar.getInstance();
			fechaFin2.set(2013, 1, 4,15,15,15);
			Event event2 = new Event("manzana de caramelo",
					"Evento2 descripcion", fechaIni2, fechaFin2, true,
					"Calle 2", (short) 25);
			event2 = serv.addEvent(event2);

			Calendar fechaIni3 = Calendar.getInstance();
			fechaIni3.set(2013, 1, 3,15,15,15);
			Calendar fechaFin3 = Calendar.getInstance();
			fechaFin3.set(2013, 1, 5,15,15,15);
			Event event3 = new Event("caramelo", "Evento3 descripcion",
					fechaIni3, fechaFin3, true, "Calle 3", (short) 10);
			event3 = serv.addEvent(event3);

			Calendar fechaIni4 = Calendar.getInstance();
			fechaIni4.set(2013, 1, 4,15,15,15);
			Calendar fechaFin4 = Calendar.getInstance();
			fechaFin4.set(2013, 1, 5,15,15,15);
			Event event4 = new Event("evento4", "Evento4 descripcion",
					fechaIni4, fechaFin4, true, "Calle 4", (short) 5);
			event4 = serv.addEvent(event4);
		} catch (InputValidationException e) {
			cleanDB();
		}
		Calendar dateTest3 = Calendar.getInstance();
		dateTest3.set(2013, 1, 2,14,14,14);
		Calendar dateTest4 = Calendar.getInstance();
		dateTest4.set(2013, 1, 5,16,16,16);
		ArrayList<Event> eventsFound = (ArrayList<Event>) serv
				.findEventByKeyword("caramelo", dateTest3, dateTest4);
		assertEquals("---->testFindEventByKeywords3 error<----",
				eventsFound.size(), 2);
		cleanDB();
	}

	// Comprueba el caso de busqueda de eventos y no encuentra ninguno
	@Test
	public void testFindEventBykeywords4() {
		cleanDB();
		try{
			Calendar fechaIni2 = Calendar.getInstance();
			fechaIni2.set(2013, 1, 2,15,15,15);
			Calendar fechaFin2 = Calendar.getInstance();
			fechaFin2.set(2013, 1, 4,15,15,15);
			Event event2 = new Event("manzana de caramelo",
					"Evento2 descripcion", fechaIni2, fechaFin2, true,
					"Calle 2", (short) 25);
			event2 = serv.addEvent(event2);
	
			Calendar fechaIni3 = Calendar.getInstance();
			fechaIni3.set(2013, 1, 3,15,15,15);
			Calendar fechaFin3 = Calendar.getInstance();
			fechaFin3.set(2013, 1, 5,15,15,15);
			Event event3 = new Event("caramelo", "Evento3 descripcion",
					fechaIni3, fechaFin3, true, "Calle 3", (short) 10);
			event3 = serv.addEvent(event3);
		}catch(InputValidationException e){
			cleanDB();
		}
		Calendar dateTest3 = Calendar.getInstance();
		dateTest3.set(2016, 1, 2,14,14,14);
		Calendar dateTest4 = Calendar.getInstance();
		dateTest4.set(2016, 1, 5,16,16,16);
		ArrayList<Event> eventsFound = (ArrayList<Event>) serv
				.findEventByKeyword("caramelo", dateTest3, dateTest4);
		assertEquals("---->testFindEventByKeywords4 error<----",
				eventsFound.size(), 0);
		cleanDB();
	}

	// Comprueba el caso de responder a un evento correctamente.
	@Test
	public void testResponseToEvent1() {
		cleanDB();
		EventService serv = EventServiceFactory.getService();
		Calendar fechaIni1 = Calendar.getInstance();
		fechaIni1.set(2013, 1, 1, 0, 0, 0);
		Calendar fechaFin1 = Calendar.getInstance();
		fechaFin1.set(2013, 1, 3, 0, 0, 0);
		Event event1 = new Event("tarea comer manzana", "Evento1 descripcion",
				fechaIni1, fechaFin1, false, "Calle 1", (short) 1);
		try {
			event1 = serv.addEvent(event1);
		} catch (InputValidationException e) {
			cleanDB();
			assertTrue(false);
		}
		try {
			Event event = serv.findEvent(event1.getEventId());
			serv.responseToEvent("Alejandro", event.getEventId(), true);
			serv.responseToEvent("Pepe2", event.getEventId(), false);
			serv.responseToEvent("Milan", event.getEventId(), false);
			cleanDB();
			assertTrue(true);
		} catch (InstanceNotFoundException | EventRegisteredUsersException
				| OverCapacityException | InputValidationException e) {
			cleanDB();
			assertTrue(false);
		}
		cleanDB();
	}

	// Obtener las respuestas positivas a un evento.
	@Test
	public void testGetResponses1() {
		cleanDB();
		EventService serv = EventServiceFactory.getService();
		Calendar fechaIni1 = Calendar.getInstance();
		fechaIni1.set(2013, 1, 1, 0, 0, 0);
		Calendar fechaFin1 = Calendar.getInstance();
		fechaFin1.set(2013, 1, 3, 0, 0, 0);
		Event event1 = new Event("tarea comer manzana", "Evento1 descripcion",
				fechaIni1, fechaFin1, false, "Calle 1", (short) 4);
		try {
			event1 = serv.addEvent(event1);
			serv.responseToEvent("Pepe", event1.getEventId(), true);
			serv.responseToEvent("Manolo", event1.getEventId(), true);			
		} catch (InputValidationException | InstanceNotFoundException
				| OverCapacityException | EventRegisteredUsersException e) {
			cleanDB();
			assertTrue(false);
		}

		try {
			ArrayList<Response> listResp = (ArrayList<Response>) serv
					.getResponses(event1.getEventId(), true);
			assertEquals("---->testResponesToEvent2 error<----",
					listResp.size(), 2);
		} catch (InstanceNotFoundException e) {
			cleanDB();
			assertTrue(false);
		}
		cleanDB();
	}

	// Obtener las respuestas negativas a un evento.
	@Test
	public void testGetResponses2() {
		cleanDB();
		EventService serv = EventServiceFactory.getService();
		Calendar fechaIni1 = Calendar.getInstance();
		fechaIni1.set(2013, 1, 1, 0, 0, 0);
		Calendar fechaFin1 = Calendar.getInstance();
		fechaFin1.set(2013, 1, 3, 0, 0, 0);
		Event event1 = new Event("tarea comer manzana", "Evento1 descripcion",
				fechaIni1, fechaFin1, false, "Calle 1", (short) 4);
		try {
			event1 = serv.addEvent(event1);
			serv.responseToEvent("Pepe", event1.getEventId(), false);
			serv.responseToEvent("Manolo", event1.getEventId(), true);			
		} catch (InputValidationException | InstanceNotFoundException
				| OverCapacityException | EventRegisteredUsersException e) {
			cleanDB();
			assertTrue(false);
		}
		try {
			ArrayList<Response> listResp = (ArrayList<Response>) serv
					.getResponses(event1.getEventId(), false);
			assertEquals("---->testResponesToEvent3 error<----",
					listResp.size(), 1);
		} catch (InstanceNotFoundException e) {
			cleanDB();
			assertTrue(false);
		}
		cleanDB();
	}

	// Obtener las respuestas positivas-negativas a un evento.
	@Test
	public void testGetResponses3() {
		cleanDB();
		EventService serv = EventServiceFactory.getService();
		Calendar fechaIni1 = Calendar.getInstance();
		fechaIni1.set(2013, 1, 1, 0, 0, 0);
		Calendar fechaFin1 = Calendar.getInstance();
		fechaFin1.set(2013, 1, 3, 0, 0, 0);
		Event event1 = new Event("tarea comer manzana", "Evento1 descripcion",
				fechaIni1, fechaFin1, false, "Calle 1", (short) 4);
		try {
			event1 = serv.addEvent(event1);
			serv.responseToEvent("Pepe", event1.getEventId(), false);
			serv.responseToEvent("Manolo", event1.getEventId(), true);			
		} catch (InputValidationException | InstanceNotFoundException
				| OverCapacityException | EventRegisteredUsersException e) {
			cleanDB();
			assertTrue(false);
		}
		try {
			ArrayList<Response> listResp = (ArrayList<Response>) serv
					.getResponses(event1.getEventId(), null);
			assertEquals("---->testResponesToEvent4 error<----",
					listResp.size(), 2);
		} catch (InstanceNotFoundException e) {
			cleanDB();
			assertTrue(false);
		}
		cleanDB();
	}

	// Responder a un evento que ya ha excedido la capacidad
	@Test
	public void testResponseToEvent2() {
		cleanDB();
		EventService serv = EventServiceFactory.getService();
		Calendar fechaIni1 = Calendar.getInstance();
		fechaIni1.set(2013, 1, 1, 0, 0, 0);
		Calendar fechaFin1 = Calendar.getInstance();
		fechaFin1.set(2013, 1, 3, 0, 0, 0);
		Event event1 = new Event("tarea comer manzana", "Evento1 descripcion",
				fechaIni1, fechaFin1, false, "Calle 1", (short) 1);
		try {
			event1 = serv.addEvent(event1);
		} catch (InputValidationException e) {
			cleanDB();
			assertTrue(false);
		}
		try {
			serv.responseToEvent("Alejandro", event1.getEventId(), true);
			serv.responseToEvent("Francisco", event1.getEventId(), true);
			cleanDB();
			assertTrue(false);
		} catch (InstanceNotFoundException | EventRegisteredUsersException | InputValidationException e) {
			cleanDB();
			assertTrue(false);
		} catch (OverCapacityException e) {
			cleanDB();
			assertTrue(true);
		}
		cleanDB();
	}

	// comprueba el caso de responder a un evento inexistente.
	@Test
	public void testResponseToEvent3() {
		cleanDB();
		try {
			serv.responseToEvent("Alejandro", (long) 7, true);
			cleanDB();
			assertTrue(false);
		} catch (InstanceNotFoundException e) {
			cleanDB();
			assertTrue(true);
		} catch (EventRegisteredUsersException | OverCapacityException | InputValidationException e) {
			cleanDB();
			assertTrue(false);
		}
	}

	// Obtener respuestas por ID de evento
	@Test
	public void testGetResponsesByEventID1() {
		cleanDB();
		EventService serv = EventServiceFactory.getService();
		Calendar fechaIni1 = Calendar.getInstance();
		fechaIni1.set(2013, 1, 1, 0, 0, 0);
		Calendar fechaFin1 = Calendar.getInstance();
		fechaFin1.set(2013, 1, 3, 0, 0, 0);
		Event event1 = new Event("tarea comer manzana", "Evento1 descripcion",
				fechaIni1, fechaFin1, false, "Calle 1", (short) 4);
		long responseId = 0;
		try {
			event1 = serv.addEvent(event1);
			responseId = serv.responseToEvent("Manolo", event1.getEventId(), true);			
		} catch (InputValidationException | InstanceNotFoundException
				| OverCapacityException | EventRegisteredUsersException e) {
			cleanDB();
			assertTrue(false);
		}
		try {
			Response resp1 = serv.getResponsesByID(responseId);
			assertEquals("---->testGetResponsesByEventID1 error<----",
					resp1.getUsername(), "Manolo");
		} catch (InstanceNotFoundException e) {
			cleanDB();
			assertTrue(false);
		}
		cleanDB();
	}

	// Obtener respuestas por ID de un evento inexsistente
	@Test
	public void testGetResponsesByEventID2() {
		cleanDB();
		try {
			serv.getResponsesByID((long) 7);
			assertTrue(false);
		} catch (InstanceNotFoundException e) {
			assertTrue(true);
		}
	}
}
