package es.udc.ws.events.test.model.eventservice;

import static es.udc.ws.events.model.util.ModelConstants.TEMPLATE_DATA_SOURCE;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;

import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.Test;

import es.udc.ws.events.exceptions.EventRegisterUsersError;
import es.udc.ws.events.exceptions.OverCapacityError;
import es.udc.ws.events.model.event.Event;
import es.udc.ws.events.model.eventservice.EventService;
import es.udc.ws.events.model.eventservice.EventServiceFactory;
import es.udc.ws.events.model.response.Response;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.sql.SimpleDataSource;

public class EventServiceTest {
	EventService serv =  EventServiceFactory.getService();
    @BeforeClass
    public static void init() {

        /*
         * Create a simple data source and add it to "DataSourceLocator" (this
         * is needed to test
         * "es.udc.ws.events.model.eventservice.EventService"
         */
        DataSource dataSource = new SimpleDataSource();

        /*
         * Add "dataSource" to "DataSourceLocator".
         */
        DataSourceLocator.addDataSource(TEMPLATE_DATA_SOURCE, dataSource);
       
    }
    
	@Test
    public void testAddEventAndFindEvent1() throws InputValidationException,
            InstanceNotFoundException {
    	EventService serv =  EventServiceFactory.getService();
		Calendar fechaIni1 = Calendar.getInstance();
		fechaIni1.set(2013, 1, 1,0,0,0);
		
		Calendar fechaFin1 = Calendar.getInstance();
		fechaFin1.set(2013, 1, 3,0,0,0);
		Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false,"Calle 1",(short) 20);
		event1 = serv.addEvent(event1);
    	
    	Event event1b = serv.findEvent((long) 1);
    	System.out.println(event1.toString());
    	System.out.println(event1b.toString());
    	assertEquals("---->testAddEventAndFindEvent1 error<----",event1,event1b);
    }

		@Test
	    public void testAddEventAndFindEvent2() throws InputValidationException,
	            InstanceNotFoundException {
	    	EventService serv =  EventServiceFactory.getService();
			Calendar fechaIni2 = Calendar.getInstance();
			fechaIni2.set(2013, 1, 2,0,0,0);
			
			Calendar fechaFin2 = Calendar.getInstance();
			fechaFin2.set(2013, 1, 4,0,0,0);
			Event event2 = new Event("manzana de caramelo","Evento2 descripcion",fechaIni2,fechaFin2,false,"Calle 2",(short) 20);
			event2 = serv.addEvent(event2);
	    	
	    	Event event2b = serv.findEvent((long) 2);
	    	System.out.println(event2.toString());
	    	System.out.println(event2b.toString());
	    	assertEquals("---->testAddEventAndFindEvent2 error<----",event2,event2b);
	    }

		@Test
	    public void testAddEventAndFindEvent3() throws InputValidationException,
	            InstanceNotFoundException {
	    	EventService serv =  EventServiceFactory.getService();
			Calendar fechaIni3 = Calendar.getInstance();
			fechaIni3.set(2013, 1, 3,0,0,0);
			
			Calendar fechaFin3 = Calendar.getInstance();
			fechaFin3.set(2013, 1, 5,0,0,0);
			Event event3 = new Event("caramelo","Evento3 descripcion",fechaIni3,fechaFin3,false,"Calle 3",(short) 20);
			event3 = serv.addEvent(event3);
	    	
	    	Event event3b = serv.findEvent((long) 3);
	    	System.out.println(event3.toString());
	    	System.out.println(event3b.toString());
	    	assertEquals("---->testAddEventAndFindEvent3 error<----",event3,event3b);
	    }
	
		@Test
	    public void testAddEventAndFindEvent4() throws InputValidationException,
	            InstanceNotFoundException {
	    	EventService serv =  EventServiceFactory.getService();
			Calendar fechaIni4 = Calendar.getInstance();
			fechaIni4.set(2013, 1, 4,0,0,0);
			
			Calendar fechaFin4 = Calendar.getInstance();
			fechaFin4.set(2013, 1, 5,0,0,0);
			Event event4 = new Event("evento4","Evento4 descripcion",fechaIni4,fechaFin4,false,"Calle 4",(short) 20);
			event4 = serv.addEvent(event4);
	    	
	    	Event event4b = serv.findEvent((long) 4);
	    	System.out.println(event4.toString());
	    	System.out.println(event4b.toString());
	    	assertEquals("---->testAddEventAndFindEvent4 error<----",event4,event4b);
	    }	
		
		// Crear evento sin fehca de fin
		@Test
	    public void testAddEvent5() throws InputValidationException,
	            InstanceNotFoundException {
	    	EventService serv =  EventServiceFactory.getService();
			Calendar fechaIni5 = Calendar.getInstance();
			fechaIni5.set(2013, 2, 6,0,0,0);
			
			Calendar fechaFin5 = Calendar.getInstance();
			fechaFin5.set(2013, 3, 8,0,0,0);
			Event event5 = new Event("evento5","Evento5 descripcion",null,fechaFin5,true,"Calle 5",(short) 15);
			
			try{
				event5 = serv.addEvent(event5);
				fail("---->testAddEvent5 error<----");
			} catch (InputValidationException e) {
				throw new InputValidationException("test ok");
			}
		}

		@Test
	    public void testAddEvent6() throws InputValidationException,
	            InstanceNotFoundException {
	    	EventService serv =  EventServiceFactory.getService();
			Calendar fechaIni6 = Calendar.getInstance();
			fechaIni6.set(2013, 1, 6,0,0,0);
			
			Calendar fechaFin6 = Calendar.getInstance();
			fechaFin6.set(2013, 1, 3,0,0,0);
			Event event6 = new Event("evento6","Evento6 descripcion",fechaIni6,fechaFin6,true,"Calle 6",(short) 15);
			
			try{
				event6 = serv.addEvent(event6);
				fail("---->testAddEvent6 error<----");
			} catch (InputValidationException e) {
				throw new InputValidationException("test ok");
			}
		}		
		
		@Test
		public void eventTest() throws InputValidationException, InstanceNotFoundException{
	    	EventService serv =  EventServiceFactory.getService();
			Calendar fechaIni1 = Calendar.getInstance();
			fechaIni1.set(2013, 1, 1,0,0,0);
			
			Calendar fechaFin1 = Calendar.getInstance();
			fechaFin1.set(2013, 1, 3,0,0,0);
			Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false,"Calle 1",(short) 20);
			event1 = serv.addEvent(event1);			
			
			String name = event1.getName();
			assertEquals("---->eventTest error<----",event1.getName(),name);
			
			String namenew = "tarea comer pera";
			event1.setName(namenew);
			assertEquals("---->eventTest error<----",event1.getName(),namenew);
			
			String desc = event1.getDescription();
			assertEquals("---->eventTest error<----",event1.getDescription(),desc);
			
			String newdesc = "Evento1 nueva descripcion";
			event1.setDescription(newdesc);
			assertEquals("---->eventTest error<----",event1.getDescription(),newdesc);
			
			Calendar ini = event1.getDateSt();
			assertEquals("---->eventTest error<----",event1.getDateSt(),ini);
			
			Calendar newini = Calendar.getInstance();
			newini.set(2013,2,2,0,0,0);
			event1.setDateSt(newini);			
			assertEquals("---->eventTest error<----",event1.getDateSt(),newini);
			
			Calendar fin = event1.getDateEnd();
			assertEquals("---->eventTest error<----",event1.getDateEnd(),fin);
			
			Calendar newfin = Calendar.getInstance();
			newfin.set(2013,4,4,0,0,0);
			event1.setDateSt(newfin);			
			assertEquals("---->eventTest error<----",event1.getDateSt(),newfin);			
			
			boolean itsintern = event1.isIntern();
			assertEquals("---->eventTest error<----",event1.isIntern(),itsintern);			
			
			boolean newitsintern = true;
			event1.setIntern(newitsintern);
			assertEquals("---->eventTest error<----",event1.isIntern(),newitsintern);			

			String dir = event1.getAddress();
			assertEquals("---->eventTest error<----",event1.getAddress(),dir);
			
			String newdir = "Calle falsa 123";
			event1.setAddress(newdir);
			assertEquals("---->eventTest error<----",event1.getAddress(),newdir);
			
			short cap = event1.getCapacity();
			assertEquals("---->eventTest error<----",event1.getCapacity(),cap);
			
			short newcap = 30;
			event1.setCapacity(newcap);
			assertEquals("---->eventTest error<----",event1.getCapacity(),newcap);
			
			String eventString = "Event [eventId=" + event1.getEventId() + ", name=" + event1.getName()
					+ ", description=" + event1.getDescription() + ", dateSt=" + event1.getDateSt()
					+ ", dateEnd=" + event1.getDateEnd() + ", intern=" + event1.isIntern() + ", address="
					+ event1.getAddress() + ", capacity=" + event1.getAddress() + "]";
			assertEquals("---->eventTest error<----",event1.toString(),eventString);			
		}
			
		// Actualizar evento normalmente
		@Test
	    public void testUpdateEventAndFindEvent1() throws InputValidationException,
	            InstanceNotFoundException, EventRegisterUsersError {	
	    	EventService serv =  EventServiceFactory.getService();
			Calendar fechaIni1 = Calendar.getInstance();
			fechaIni1.set(2013, 1, 1,0,0,0);
			
			Calendar fechaFin1 = Calendar.getInstance();
			fechaFin1.set(2013, 1, 3,0,0,0);
			Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false,"Calle 1",(short) 20);
			event1 = serv.addEvent(event1);

			event1.setCapacity((short) 2);
			serv.updateEvent(event1);
			event1 = serv.findEvent((long) 1);
	    	assertEquals("---->testUpdateEventAndFindEvent1 error<----",event1.getCapacity(),(short) 2);	
		}		

		// Actualizar evento con usuarios registrados
		@Test
	    public void testUpdateEvent2() throws InputValidationException,
	            InstanceNotFoundException, EventRegisterUsersError, OverCapacityError {	
	    	EventService serv =  EventServiceFactory.getService();
			Calendar fechaIni1 = Calendar.getInstance();
			fechaIni1.set(2013, 1, 1,0,0,0);
			
			Calendar fechaFin1 = Calendar.getInstance();
			fechaFin1.set(2013, 1, 3,0,0,0);
			Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false,"Calle 1",(short) 20);
			event1 = serv.addEvent(event1);
			serv.responseToEvent("Alejandro", event1.getEventId(), true);
			serv.responseToEvent("Francisco", event1.getEventId(), true);	
			try {
				event1.setCapacity((short) 2);
				serv.updateEvent(event1);
				fail("---->testUpdateEvent2 error<----");
			} catch(InputValidationException e) {
				throw new InputValidationException("test ok");
			}
		}			

		// Actualizar evento con fecha de inicio posterior a la fecha de fin
		@Test
	    public void testUpdateEvent3() throws InputValidationException,
	            InstanceNotFoundException, EventRegisterUsersError {	
	    	EventService serv =  EventServiceFactory.getService();
			Calendar fechaIni1 = Calendar.getInstance();
			fechaIni1.set(2013, 1, 1,0,0,0);
			
			Calendar fechaFin1 = Calendar.getInstance();
			fechaFin1.set(2013, 1, 3,0,0,0);
			Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false,"Calle 1",(short) 20);
			event1 = serv.addEvent(event1);
			try {
				Calendar newDate = Calendar.getInstance();
				newDate.set(2013,1, 4,0,0,0);
				event1.setDateSt(newDate);
				serv.updateEvent(event1);
				fail("---->testUpdateEvent3 error<----");
			} catch(InputValidationException e) {
				throw new InputValidationException("test ok");
			}
		}			
		
		// Borrar evento
		@Test
	    public void testDeleteEvent1() throws InputValidationException,
	            InstanceNotFoundException, EventRegisterUsersError {	
	    	EventService serv =  EventServiceFactory.getService();
			Calendar fechaIni5 = Calendar.getInstance();
			fechaIni5.set(2013, 1, 1,0,0,0);
			
			Calendar fechaFin5 = Calendar.getInstance();
			fechaFin5.set(2013, 1, 3,0,0,0);
			Event event5 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni5,fechaFin5,false,"Calle 1",(short) 20);
			event5 = serv.addEvent(event5);

			serv.deleteEvent(event5.getEventId());
			event5 = serv.findEvent((long) 5);
	    	assertNull("---->testDeleteEvent1 error<----",event5);	
		}

		@Test
		// Borrar eventos con gente registrada
	    public void testDeleteEvent2() throws InputValidationException,
	            InstanceNotFoundException, EventRegisterUsersError, OverCapacityError {	
	    	EventService serv =  EventServiceFactory.getService();
			Calendar fechaIni5 = Calendar.getInstance();
			fechaIni5.set(2013, 1, 1,0,0,0);
			
			Calendar fechaFin5 = Calendar.getInstance();
			fechaFin5.set(2013, 1, 3,0,0,0);
			Event event5 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni5,fechaFin5,false,"Calle 1",(short) 20);
			event5 = serv.addEvent(event5);
			serv.responseToEvent("Alejandro", event5.getEventId(), true);
			serv.responseToEvent("Francisco", event5.getEventId(), true);
			try {
				serv.deleteEvent(event5.getEventId());
				fail("---->testDeleteEvent2 error<----");
			} catch(EventRegisterUsersError e) {
				throw new EventRegisterUsersError("test ok");
			}
		}		
		
		// Buscar evento por palabra clave
		@Test
	    public void testFindEventByKeyword1() throws InputValidationException,
	            InstanceNotFoundException {
			EventService serv =  EventServiceFactory.getService();
			Calendar fechaIni1 = Calendar.getInstance();
			fechaIni1.set(2013, 1, 1);
			Calendar fechaFin1 = Calendar.getInstance();
			fechaFin1.set(2013, 1, 3);
			Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false,"Calle 1",(short) 20);
			event1 = serv.addEvent(event1);
			
			Calendar fechaIni2 = Calendar.getInstance();
			fechaIni2.set(2013, 1, 2);
			Calendar fechaFin2 = Calendar.getInstance();
			fechaFin2.set(2013, 1, 4);
			Event event2 = new Event("manzana de caramelo","Evento2 descripcion",fechaIni2,fechaFin2,true,"Calle 2",(short) 25);
			event2 = serv.addEvent(event2);
			
			Calendar fechaIni3 = Calendar.getInstance();
			fechaIni3.set(2013, 1, 3);
			Calendar fechaFin3 = Calendar.getInstance();
			fechaFin3.set(2013, 1, 5);
			Event event3 = new Event("caramelo","Evento3 descripcion",fechaIni3,fechaFin3,true,"Calle 3",(short) 10);
			event3 = serv.addEvent(event3);
			
			Calendar fechaIni4 = Calendar.getInstance();
			fechaIni4.set(2013, 1, 4);
			Calendar fechaFin4 = Calendar.getInstance();
			fechaFin4.set(2013, 1, 5);
			Event event4 = new Event("evento4","Evento4 descripcion",fechaIni4,fechaFin4,true,"Calle 4",(short) 5);
			event4 = serv.addEvent(event4);
			
			ArrayList<Event> eventsFound = (ArrayList<Event>) serv.findEventByKeyword("caramelo", null, null);
			int i = 0;
			while(i < eventsFound.size()){
				System.out.println(eventsFound.get(i).toString());
				i++;
			}			
	    	assertEquals("---->testFindEventByKeyword1 error<----",eventsFound.size(),(short) 2);
		}		
		
		// Buscar evento entre fechas
		@Test
		public void testFindEventByKeyword2() throws InputValidationException,
        InstanceNotFoundException {
			EventService serv =  EventServiceFactory.getService();
			Calendar fechaIni1 = Calendar.getInstance();
			fechaIni1.set(2013, 1, 1);
			Calendar fechaFin1 = Calendar.getInstance();
			fechaFin1.set(2013, 1, 3);
			Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false,"Calle 1",(short) 20);
			event1 = serv.addEvent(event1);
			
			Calendar fechaIni2 = Calendar.getInstance();
			fechaIni2.set(2013, 1, 2);
			Calendar fechaFin2 = Calendar.getInstance();
			fechaFin2.set(2013, 1, 4);
			Event event2 = new Event("manzana de caramelo","Evento2 descripcion",fechaIni2,fechaFin2,true,"Calle 2",(short) 25);
			event2 = serv.addEvent(event2);
			
			Calendar fechaIni3 = Calendar.getInstance();
			fechaIni3.set(2013, 1, 3);
			Calendar fechaFin3 = Calendar.getInstance();
			fechaFin3.set(2013, 1, 5);
			Event event3 = new Event("caramelo","Evento3 descripcion",fechaIni3,fechaFin3,true,"Calle 3",(short) 10);
			event3 = serv.addEvent(event3);
			
			Calendar fechaIni4 = Calendar.getInstance();
			fechaIni4.set(2013, 1, 4);
			Calendar fechaFin4 = Calendar.getInstance();
			fechaFin4.set(2013, 1, 5);
			Event event4 = new Event("evento4","Evento4 descripcion",fechaIni4,fechaFin4,true,"Calle 4",(short) 5);
			event4 = serv.addEvent(event4);		
			
			Calendar dateTest1 = Calendar.getInstance();
			dateTest1.set(2013, 1, 3);
			Calendar dateTest2 = Calendar.getInstance();
			dateTest2.set(2013, 1, 5);
			ArrayList<Event> eventsFound = (ArrayList<Event>) serv.findEventByKeyword(null,dateTest1,dateTest2);
			int i = 0;
			while(i < eventsFound.size()){
				System.out.println(eventsFound.get(i).toString());
				i++;
			}
			assertEquals("---->testFindEventByKeyword2 error<----",eventsFound.size(),(short) 3);
		}

		// Buscar evento por palabra clave y fechas	
		@Test
		public void testFindEventByKeyword3() throws InputValidationException,
        InstanceNotFoundException {
			EventService serv =  EventServiceFactory.getService();
			Calendar fechaIni1 = Calendar.getInstance();
			fechaIni1.set(2013, 1, 1);
			Calendar fechaFin1 = Calendar.getInstance();
			fechaFin1.set(2013, 1, 3);
			Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false,"Calle 1",(short) 20);
			event1 = serv.addEvent(event1);
			
			Calendar fechaIni2 = Calendar.getInstance();
			fechaIni2.set(2013, 1, 2);
			Calendar fechaFin2 = Calendar.getInstance();
			fechaFin2.set(2013, 1, 4);
			Event event2 = new Event("manzana de caramelo","Evento2 descripcion",fechaIni2,fechaFin2,true,"Calle 2",(short) 25);
			event2 = serv.addEvent(event2);
			
			Calendar fechaIni3 = Calendar.getInstance();
			fechaIni3.set(2013, 1, 3);
			Calendar fechaFin3 = Calendar.getInstance();
			fechaFin3.set(2013, 1, 5);
			Event event3 = new Event("caramelo","Evento3 descripcion",fechaIni3,fechaFin3,true,"Calle 3",(short) 10);
			event3 = serv.addEvent(event3);
			
			Calendar fechaIni4 = Calendar.getInstance();
			fechaIni4.set(2013, 1, 4);
			Calendar fechaFin4 = Calendar.getInstance();
			fechaFin4.set(2013, 1, 5);
			Event event4 = new Event("evento4","Evento4 descripcion",fechaIni4,fechaFin4,true,"Calle 4",(short) 5);
			event4 = serv.addEvent(event4);		
			
			Calendar dateTest1 = Calendar.getInstance();
			dateTest1.set(2013, 1, 3);
			Calendar dateTest2 = Calendar.getInstance();
			dateTest2.set(2013, 1, 5);
			ArrayList<Event> eventsFound = (ArrayList<Event>) serv.findEventByKeyword("caramelo",dateTest1,dateTest2);
			int i = 0;
			while(i < eventsFound.size()){
				System.out.println(eventsFound.get(i).toString());
				i++;
			}
			assertEquals("---->testFindEventByKeyword3 error<----",eventsFound.size(),(short) 1);
		}		
		
		// Respuestas afirmativas al evento 3
		@Test
		public void testResponseToEvent1() throws InputValidationException,
        InstanceNotFoundException, OverCapacityError, EventRegisterUsersError {
			EventService serv =  EventServiceFactory.getService();
			Calendar fechaIni1 = Calendar.getInstance();
			fechaIni1.set(2013, 1, 1);
			Calendar fechaFin1 = Calendar.getInstance();
			fechaFin1.set(2013, 1, 3);
			Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false,"Calle 1",(short) 20);
			event1 = serv.addEvent(event1);
			
			Calendar fechaIni3 = Calendar.getInstance();
			fechaIni3.set(2013, 1, 3);
			Calendar fechaFin3 = Calendar.getInstance();
			fechaFin3.set(2013, 1, 5);
			Event event3 = new Event("caramelo","Evento3 descripcion",fechaIni3,fechaFin3,true,"Calle 3",(short) 10);
			event3 = serv.addEvent(event3);

			serv.responseToEvent("Alejandro", event1.getEventId(), true);
			serv.responseToEvent("Francisco", event1.getEventId(), true);
			serv.responseToEvent("Pepe", event3.getEventId(), true);
			serv.responseToEvent("Xacobe", event3.getEventId(), false);
			serv.responseToEvent("Daniel", event3.getEventId(), true);
			serv.responseToEvent("Maria", event3.getEventId(), false);
			serv.responseToEvent("Alex", event3.getEventId(), true);
			serv.responseToEvent("Juan Carlos", event3.getEventId(), true);
			
			System.out.println("Obtener respuestas para evento");
			System.out.println("Respuestas afirmativas al evento 3");

			ArrayList <Response> listResp = (ArrayList<Response>) serv.getResponses(event3.getEventId(), true);
			int i = 0;
			while(i < listResp.size()){
				System.out.println(listResp.get(i).toString());
				i++;
			}
			assertEquals("---->testResponseToEvent1 error<----",listResp.size(),(short) 4);


		}
		
		//Respuestas negativas al evento 3
		@Test
		public void testResponseToEvent2() throws InputValidationException,
        InstanceNotFoundException, OverCapacityError, EventRegisterUsersError {
			EventService serv =  EventServiceFactory.getService();
			Calendar fechaIni1 = Calendar.getInstance();
			fechaIni1.set(2013, 1, 1);
			Calendar fechaFin1 = Calendar.getInstance();
			fechaFin1.set(2013, 1, 3);
			Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false,"Calle 1",(short) 20);
			event1 = serv.addEvent(event1);
			
			Calendar fechaIni3 = Calendar.getInstance();
			fechaIni3.set(2013, 1, 3);
			Calendar fechaFin3 = Calendar.getInstance();
			fechaFin3.set(2013, 1, 5);
			Event event3 = new Event("caramelo","Evento3 descripcion",fechaIni3,fechaFin3,true,"Calle 3",(short) 10);
			event3 = serv.addEvent(event3);

			serv.responseToEvent("Alejandro", event1.getEventId(), true);
			serv.responseToEvent("Francisco", event1.getEventId(), true);
			serv.responseToEvent("Pepe", event3.getEventId(), true);
			serv.responseToEvent("Xacobe", event3.getEventId(), false);
			serv.responseToEvent("Daniel", event3.getEventId(), true);
			serv.responseToEvent("Maria", event3.getEventId(), false);
			serv.responseToEvent("Alex", event3.getEventId(), true);
			serv.responseToEvent("Juan Carlos", event3.getEventId(), true);
			
			System.out.println("Respuestas negativas al evento 3");

			ArrayList <Response> listResp = (ArrayList<Response>) serv.getResponses(event3.getEventId(), false);
			int i = 0;
			while(i < listResp.size()){
				System.out.println(listResp.get(i).toString());
				i++;
			}
			assertEquals("---->testResponseToEvent2 error<----",listResp.size(),(short) 2);


		}		
		
		// Respuestas afirmativas-negativas al evento 3
		@Test
		public void testResponseToEvent3() throws InputValidationException,
        InstanceNotFoundException, OverCapacityError, EventRegisterUsersError {
			EventService serv =  EventServiceFactory.getService();
			Calendar fechaIni1 = Calendar.getInstance();
			fechaIni1.set(2013, 1, 1);
			Calendar fechaFin1 = Calendar.getInstance();
			fechaFin1.set(2013, 1, 3);
			Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false,"Calle 1",(short) 20);
			event1 = serv.addEvent(event1);
			
			Calendar fechaIni3 = Calendar.getInstance();
			fechaIni3.set(2013, 1, 3);
			Calendar fechaFin3 = Calendar.getInstance();
			fechaFin3.set(2013, 1, 5);
			Event event3 = new Event("caramelo","Evento3 descripcion",fechaIni3,fechaFin3,true,"Calle 3",(short) 10);
			event3 = serv.addEvent(event3);

			serv.responseToEvent("Alejandro", event1.getEventId(), true);
			serv.responseToEvent("Francisco", event1.getEventId(), true);
			serv.responseToEvent("Pepe", event3.getEventId(), true);
			serv.responseToEvent("Xacobe", event3.getEventId(), false);
			serv.responseToEvent("Daniel", event3.getEventId(), true);
			serv.responseToEvent("Maria", event3.getEventId(), false);
			serv.responseToEvent("Alex", event3.getEventId(), true);
			serv.responseToEvent("Juan Carlos", event3.getEventId(), true);
			
			System.out.println("Respuestas afirmativas-negativas al evento 3");

			ArrayList <Response> listResp = (ArrayList<Response>) serv.getResponses(event3.getEventId(), null);
			int i = 0;
			while(i < listResp.size()){
				System.out.println(listResp.get(i).toString());
				i++;
			}
			assertEquals("---->testResponseToEvent3 error<----",listResp.size(),(short) 6);
		}
		
		// responder a un evento que ya se sobrepasado el aforo
		@Test
		public void testResponseToEvent4() throws InputValidationException,
        InstanceNotFoundException, OverCapacityError, EventRegisterUsersError {
			EventService serv =  EventServiceFactory.getService();
			Calendar fechaIni1 = Calendar.getInstance();
			fechaIni1.set(2013, 1, 1);
			Calendar fechaFin1 = Calendar.getInstance();
			fechaFin1.set(2013, 1, 3);
			Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false,"Calle 1",(short) 2);
			event1 = serv.addEvent(event1);
			
			try {
				serv.responseToEvent("Alejandro", event1.getEventId(), true);
				serv.responseToEvent("Francisco", event1.getEventId(), true);
				serv.responseToEvent("Manulo", event1.getEventId(), true);
				fail("---->testResponseToEvent4 error<----");
			} catch (OverCapacityError e) {
				throw new OverCapacityError("test ok");
			}
		}
		
}
