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
import es.udc.ws.events.model.response.SqlResponseDao;
import es.udc.ws.events.model.response.SqlResponseDaoFactory;
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
	//Prueba el validateEvent y el anadir un evento correctamente
	@Test
    public void testAddEventAndFindEvent(){
    	EventService serv =  EventServiceFactory.getService();
		Calendar fechaIni1 = Calendar.getInstance();
		fechaIni1.set(2013, 1, 1,0,0,0);
		
		Calendar fechaFin1 = Calendar.getInstance();
		fechaFin1.set(2013, 1, 3,0,0,0);
		Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false,"Calle 1",(short) 20);
		try{
			event1 = serv.addEvent(event1);
			Event event1b = serv.findEvent((long) 1);
			assertEquals("---->testAddEventAndFindEvent error<----",event1,event1b);
		}catch(InstanceNotFoundException | InputValidationException e){
			assertTrue(false);
		}
    	
    }
	//Prueba dentro del validateEvent el validateMandatoryString
	@Test
    public void testValidateEvent(){
    	EventService serv =  EventServiceFactory.getService();
		Calendar fechaIni1 = Calendar.getInstance();
		fechaIni1.set(2013, 1, 1,0,0,0);
		
		Calendar fechaFin1 = Calendar.getInstance();
		fechaFin1.set(2013, 1, 3,0,0,0);
		Event event1 = new Event("","Evento1 descripcion",fechaIni1,fechaFin1,false,"Calle 1",(short) 20);
		try{
			event1 = serv.addEvent(event1);
			assertTrue(false);
		}catch(InputValidationException e){
			assertTrue(true);
		}
    	
    }
	//valida que las fechas de un evento sean correctas
	@Test
    public void testValidateEvent2(){
    	EventService serv =  EventServiceFactory.getService();
		Calendar fechaIni1 = Calendar.getInstance();
		fechaIni1.set(2013, 1, 1,0,0,0);
		
		Calendar fechaFin1 = Calendar.getInstance();
		fechaFin1.set(2012, 1, 3,0,0,0);
		Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false,"Calle 1",(short) 20);
		try{
			event1 = serv.addEvent(event1);
			assertTrue(false);
		}catch(InputValidationException e){
			assertTrue(true);
		}
    	
    }
	//valida el campo addres
	@Test
	public void testValidateEvent3(){
	   	EventService serv =  EventServiceFactory.getService();
		Calendar fechaIni1 = Calendar.getInstance();
		fechaIni1.set(2013, 1, 1,0,0,0);
		
		Calendar fechaFin1 = Calendar.getInstance();
		fechaFin1.set(2012, 1, 3,0,0,0);
		Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false,"",(short) 20);
		try{
			event1 = serv.addEvent(event1);
			assertTrue(false);
		}catch(InputValidationException e){
			assertTrue(true);
		}   	
	}
	//valida el campo capacity
	@Test
	public void testValidateEvent4(){
	   	EventService serv =  EventServiceFactory.getService();
		Calendar fechaIni1 = Calendar.getInstance();
		fechaIni1.set(2013, 1, 1,0,0,0);
		
		Calendar fechaFin1 = Calendar.getInstance();
		fechaFin1.set(2012, 1, 3,0,0,0);
		Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false ,"Calle 1",(short) 0);
		try{
			event1 = serv.addEvent(event1);
			assertTrue(false);
		}catch(InputValidationException e){
			assertTrue(true);
		}    	
	}
	//valida que la fecha de inicio sea posterior a la actual
	@Test
	public void testValidateEvent5(){
	   	EventService serv =  EventServiceFactory.getService();
		Calendar fechaIni1 = Calendar.getInstance();
		fechaIni1.set(2011, 1, 1,0,0,0);
		
		Calendar fechaFin1 = Calendar.getInstance();
		fechaFin1.set(2012, 1, 3,0,0,0);
		Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false,"Calle 1",(short) 20);
		try{
			event1 = serv.addEvent(event1);
		assertTrue(false);
		}catch(InputValidationException e){
			assertTrue(true);
		}    	
	}
	
	//valida que se actualicen correctamente los eventos
	@Test
	public void testUpdateEvent(){
	    try{
			Event event = serv.findEvent((long) 1);
			event.setCapacity((short) 2);
			serv.updateEvent(event);
			event = serv.findEvent((long) 1);
			assertEquals("---->testUpdateEvent error<----",event.getCapacity(),(short)2);
	    }catch(InstanceNotFoundException e){
	    	System.out.println("1");
	    	assertTrue(false);
	    }catch(InputValidationException e){
	    	System.out.println("2");
	    	assertTrue(false);
	    }catch(EventRegisterUsersError e){
	    	System.out.println("3");
	    	assertTrue(false);
	    }
	}
	//comprueba el caso de no encontrar el event a actualizar
	@Test
	public void testUpdateEvent2(){
	    try{
			Event event = serv.findEvent((long) 1);
			event.setEventId((long) 5);
			serv.updateEvent(event);
			assertTrue(false);
		}catch(InstanceNotFoundException e){
	    	assertTrue(true);
	    } catch (InputValidationException e) {
	    	assertTrue(false);
		} catch (EventRegisterUsersError e) {
			assertTrue(false);
		}
	}
	//comprueba que no se pueda actualizar un evento con usuarios registrados
	@Test
	public void testUpdateEvent3(){
	    try{
			Event event = serv.findEvent((long) 1);
			serv.responseToEvent("Pepe", event.getEventId(), true);
			serv.updateEvent(event);
			assertTrue(false);
	    }catch(InstanceNotFoundException e){
	    	System.out.println("1");
	    	assertTrue(false);
	    } catch (InputValidationException e) {
	    	System.out.println("2");
	    	assertTrue(false);
		} catch (EventRegisterUsersError e) {
			assertTrue(true);
		} catch (OverCapacityError e) {
			System.out.println("4");
			assertTrue(false);
		}
	}
	//elimina un evento de forma correcta
	@Test
	public void testdeleteEvent(){
		try{
			EventService serv =  EventServiceFactory.getService();
			Calendar fechaIni1 = Calendar.getInstance();
			fechaIni1.set(2013, 1, 1,0,0,0);
			
			Calendar fechaFin1 = Calendar.getInstance();
			fechaFin1.set(2013, 1, 3,0,0,0);
			Event event1 = new Event("Prueba para borrar","b",fechaIni1,fechaFin1,false,"Calle 1",(short) 20);
			serv.addEvent(event1);
			serv.deleteEvent((long)2);
		}catch(InstanceNotFoundException | EventRegisterUsersError | InputValidationException e){
			assertTrue(false);
		}
		try{
			serv.findEvent((long) 2);
			assertTrue(false);
		}catch(InstanceNotFoundException e){
			assertTrue(true);
		}
	}
	//intenta eliminar un evento que no existe
	@Test
	public void testdeleteEvent2(){
		try{
			serv.deleteEvent((long) 19);
			assertTrue(false);
		}catch(InstanceNotFoundException e){
			assertTrue(true);
		} catch (EventRegisterUsersError e) {
			assertTrue(false);
		}
	}
	//intenta eliminar un evento con usuarios registrados
	@Test
	public void testdeleteEvent3(){
		try{
			serv.deleteEvent((long) 1);
			assertTrue(false);
		}catch(EventRegisterUsersError e){
			assertTrue(true);
		} catch (InstanceNotFoundException e) {
			assertTrue(false);
		}
	}
	//Comprueba que devuelve en caso de no encontrar el evento que buscaba
	@Test
	public void testFindEvent(){
		try{
			serv.findEvent((long) 20);
			assertTrue(false);
		}catch(InstanceNotFoundException e){
			assertTrue(true);
		}
	}
	//Comprueba que devuelve una busqueda correcta buscando por solo la clave
	@Test
	public void testFindEventByKeywords(){
		try{
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
			assertEquals("---->testFindEventByKeywords error<----",eventsFound.size(),2);
		}catch(InputValidationException e) {
			assertTrue(false);
		}
	}
	//Comprueba el caso de busqueda de eventos por fecha
	@Test
	public void testFindEventByKeywords2(){
		Calendar dateTest1 = Calendar.getInstance();
		dateTest1.set(2013, 1, 2);
		Calendar dateTest2 = Calendar.getInstance();
		dateTest2.set(2013, 1, 4);
		ArrayList<Event> eventsFound = (ArrayList<Event>) serv.findEventByKeyword(null, dateTest1, dateTest2);
		assertEquals("---->testFindEventByKeywords2 error<----",eventsFound.size(),3);
	}
	//Comprueba el caso de busqueda de eventos por fecha y clave
	@Test
	public void testFinEventBykeywords3(){
		Calendar dateTest3 = Calendar.getInstance();
		dateTest3.set(2013, 1, 2);
		Calendar dateTest4 = Calendar.getInstance();
		dateTest4.set(2013, 1, 5);
		ArrayList<Event>eventsFound = (ArrayList<Event>) serv.findEventByKeyword("caramelo",dateTest3,dateTest4);
		assertEquals("---->testFindEventByKeywords3 error<----",eventsFound.size(),2);
	}
	//Comprueba el caso de busqueda de eventos y no encuentra ninguno
	@Test
	public void testFinEventBykeywords4(){
		Calendar dateTest3 = Calendar.getInstance();
		dateTest3.set(2016, 1, 2);
		Calendar dateTest4 = Calendar.getInstance();
		dateTest4.set(2016, 1, 5);
		ArrayList<Event>eventsFound = (ArrayList<Event>) serv.findEventByKeyword("caramelo",dateTest3,dateTest4);
		assertEquals("---->testFindEventByKeywords4 error<----",eventsFound.size(),0);
	}
	//Comprueba el caso de responder a un evento correctamente.
	@Test
	public void testResponseToEvent1(){
		try{
			Event event = serv.findEvent((long) 1);
			serv.responseToEvent("Alejandro", event.getEventId(), true);
			serv.responseToEvent("Francisco", event.getEventId(), true);
			serv.responseToEvent("Pepe", event.getEventId(), false);
			assertTrue(true);
		}catch(InstanceNotFoundException | EventRegisterUsersError | OverCapacityError e){
			assertTrue(false);
		}
	}
	//Obtener las respuestas positivas a un evento.
	@Test
	public void testGetResponses1(){
		try {
			Event event = serv.findEvent((long) 1);
			ArrayList <Response> listResp = (ArrayList<Response>) serv.getResponses(event.getEventId(), true);
			assertEquals("---->testResponesToEvent2 error<----",listResp.size(), 2);
		} catch (InstanceNotFoundException e){
			assertTrue(false);
		}
	}
	//Obtener las respuestas negativas a un evento.
	@Test
	public void testGetResponses2(){
		try {
			Event event = serv.findEvent((long) 1);
			ArrayList <Response> listResp = (ArrayList<Response>) serv.getResponses(event.getEventId(), false);
			assertEquals("---->testResponesToEvent3 error<----",listResp.size(), 1);
		} catch (InstanceNotFoundException e){
			assertTrue(false);
		}
	}	
	//Obtener las respuestas positivas-negativas a un evento.
	@Test
	public void testGetResponses3(){
		try {
			Event event = serv.findEvent((long) 1);
			ArrayList <Response> listResp = (ArrayList<Response>) serv.getResponses(event.getEventId(), null);
			assertEquals("---->testResponesToEvent4 error<----",listResp.size(), 3);
		} catch (InstanceNotFoundException e){
			assertTrue(false);
		}
	}
	//Responder a un evento que ya ha excedido la capacidad 
	@Test
	public void testResponseToEvent2(){
		EventService serv =  EventServiceFactory.getService();
		Calendar fechaIni6 = Calendar.getInstance();
		fechaIni6.set(2013, 1, 1);
		Calendar fechaFin6 = Calendar.getInstance();
		fechaFin6.set(2013, 1, 3);
		Event event6 = new Event("exposicion de Picasso","Evento9 descripcion",fechaIni6,fechaFin6,false,"Calle 6",(short) 2);
		try{
			event6 = serv.addEvent(event6);
			serv.responseToEvent("Alejandro", event6.getEventId(), true);
			serv.responseToEvent("Francisco", event6.getEventId(), true);
			serv.responseToEvent("Manolo", event6.getEventId(), true);
		} catch (InstanceNotFoundException | EventRegisterUsersError | OverCapacityError | InputValidationException e){
			assertTrue(true);
		}
	}
	
	//comprueba el caso de responder a un evento inexistente.
	@Test
	public void testResponseToEvent3(){
	    try{
			Event event = serv.findEvent((long) 7);
			serv.responseToEvent("Alejandro", event.getEventId(), true);
			assertTrue(false);
		}catch(InstanceNotFoundException e){
	    	assertTrue(true);
	    }catch (EventRegisterUsersError e) {
			assertTrue(false);
		}catch (OverCapacityError e){
			assertTrue(false);
		}
	}
	
	// Obtener respuestas por ID de evento
	@Test
	public void testGetResponsesByEventID1(){
		try {
			Response resp1 = serv.getResponsesByID((long) 1);
			assertEquals(resp1.getUsername(),"Pepe");
		} catch (InstanceNotFoundException e){
			assertTrue(false);
		}
	}
	
	// Obtener respuestas por ID de un evento inexsistente
	@Test
	public void testGetResponsesByEventID2(){
		try {
			Response resp1 = serv.getResponsesByID((long) 7);
			assertTrue("---->testGetResponsesByEventID2 error<----",false);	
		} catch (InstanceNotFoundException e){
			assertTrue(true);
		}
	}
	
//	// Get answers by event ID
//	@Test
//	public void testResponseToEvent6() throws InputValidationException,
//    InstanceNotFoundException, OverCapacityError, EventRegisterUsersError {
//		EventService serv =  EventServiceFactory.getService();
//		Calendar fechaIni11 = Calendar.getInstance();
//		fechaIni11.set(2013, 1, 1);
//		Calendar fechaFin11 = Calendar.getInstance();
//		fechaFin11.set(2013, 1, 3);
//		Event event11 = new Event("ver la television","Evento8 descripcion",fechaIni11,fechaFin11,false,"Calle 11",(short) 20);
//		event11 = serv.addEvent(event11);
//
//		serv.responseToEvent("Alejandro", event11.getEventId(), true);
//		serv.responseToEvent("Francisco", event11.getEventId(), true);
//		serv.responseToEvent("Pepe", event11.getEventId(), false);
//
//		Response resp1 = serv.getResponsesByID((long) 1);
//
//		assertEquals(resp1.getUsername(),"Alejandro");
//	}
	
//	/*
//	// Find a a nonexistent event
//	@Test(expected = InstanceNotFoundException.class)
//	public void testFindNonExistentEvent() throws InstanceNotFoundException {
//    	EventService serv =  EventServiceFactory.getService();
//		Calendar fechaIni1 = Calendar.getInstance();
//		fechaIni1.set(2013, 1, 1,0,0,0);
//		
//		Calendar fechaFin1 = Calendar.getInstance();
//		fechaFin1.set(2013, 1, 3,0,0,0);
//		Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false,"Calle 1",(short) 20);
//
//		serv.findEvent(event1.getEventId());;
//
//	}
//	
//	// Add an invalid event
//	@Test
//	public void testAddInvalidEvent() throws EventRegisterUsersError {
//		boolean exceptionCatched = false;
//		Calendar fechaIni1 = Calendar.getInstance();
//		fechaIni1.set(2013, 1, 1,0,0,0);
//		
//		Calendar fechaFin1 = Calendar.getInstance();
//		fechaFin1.set(2013, 1, 3,0,0,0);
//		Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false,"Calle 1",(short) 20);
//		try {
//			
//			// Check event name not null
//			exceptionCatched = false;
//			event1.setName(null);
//			try {
//				event1 = serv.addEvent(event1);
//			} catch (InputValidationException e) {
//				exceptionCatched = true;
//			}
//			assertTrue(exceptionCatched);
//
//			// Check event name not empty
//			exceptionCatched = false;
//			event1.setName("");
//			try {
//				event1 = serv.addEvent(event1);
//			} catch (InputValidationException e) {
//				exceptionCatched = true;
//			}
//			assertTrue(exceptionCatched);
//			
//			// Check event description not null
//			exceptionCatched = false;
//			event1.setDescription(null);
//			try {
//				event1 = serv.addEvent(event1);
//			} catch (InputValidationException e) {
//				exceptionCatched = true;
//			}
//			assertTrue(exceptionCatched);
//
//			// Check event description not empty
//			exceptionCatched = false;
//			event1.setDescription("");
//			try {
//				event1 = serv.addEvent(event1);
//			} catch (InputValidationException e) {
//				exceptionCatched = true;
//			}
//			assertTrue(exceptionCatched);
//			
//			// Check event dateSt >= dateEnd
//			exceptionCatched = false;
//			Calendar fechaIni = Calendar.getInstance();
//			fechaIni1.set(2013, 3, 3,0,0,0);
//			Calendar fechaFin = Calendar.getInstance();
//			fechaFin1.set(2013, 1, 1,0,0,0);
//			event1.setDateSt(fechaIni);
//			event1.setDateEnd(fechaFin);
//			try {
//				event1 = serv.addEvent(event1);
//			} catch (InputValidationException e) {
//				exceptionCatched = true;
//			}
//			assertTrue(exceptionCatched);
//			
//			// Check event dateSt not null
//			exceptionCatched = false;
//			event1.setDateSt(null);
//			try {
//				event1 = serv.addEvent(event1);
//			} catch (InputValidationException e) {
//				exceptionCatched = true;
//			}
//			assertTrue(exceptionCatched);
//			
//			// Check event dateEnd not null
//			exceptionCatched = false;
//			event1.setDateEnd(null);
//			try {
//				event1 = serv.addEvent(event1);
//			} catch (InputValidationException e) {
//				exceptionCatched = true;
//			}
//			assertTrue(exceptionCatched);
//			
//			// Check event address not null
//			exceptionCatched = false;
//			event1.setAddress(null);
//			try {
//				event1 = serv.addEvent(event1);
//			} catch (InputValidationException e) {
//				exceptionCatched = true;
//			}
//			assertTrue(exceptionCatched);
//
//			// Check event address not empty
//			exceptionCatched = false;
//			event1.setAddress("");
//			try {
//				event1 = serv.addEvent(event1);
//			} catch (InputValidationException e) {
//				exceptionCatched = true;
//			}
//			assertTrue(exceptionCatched);
//			
//			// Check event capacity >= 0
//			exceptionCatched = false;
//			event1.setCapacity((short) -1);
//			try {
//				event1 = serv.addEvent(event1);
//			} catch (InputValidationException e) {
//				exceptionCatched = true;
//			}
//			assertTrue(exceptionCatched);		
//		} finally {
//			if (!exceptionCatched) {
//				// Clear Database
//				removeEvent(event1.getEventId());
//			}
//		}
//		
//	}
//	
//	/*	
//	@Test
//	public void eventTest() throws InputValidationException, InstanceNotFoundException{
//    	EventService serv =  EventServiceFactory.getService();
//		Calendar fechaIni1 = Calendar.getInstance();
//		fechaIni1.set(2013, 1, 1,0,0,0);
//		
//		Calendar fechaFin1 = Calendar.getInstance();
//		fechaFin1.set(2013, 1, 3,0,0,0);
//		Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false,"Calle 1",(short) 20);
//		event1 = serv.addEvent(event1);			
//		
//		String name = event1.getName();
//		assertEquals("---->eventTest error<----",event1.getName(),name);
//		
//		String namenew = "tarea comer pera";
//		event1.setName(namenew);
//		assertEquals("---->eventTest error<----",event1.getName(),namenew);
//		
//		String desc = event1.getDescription();
//		assertEquals("---->eventTest error<----",event1.getDescription(),desc);
//		
//		String newdesc = "Evento1 nueva descripcion";
//		event1.setDescription(newdesc);
//		assertEquals("---->eventTest error<----",event1.getDescription(),newdesc);
//		
//		Calendar ini = event1.getDateSt();
//		assertEquals("---->eventTest error<----",event1.getDateSt(),ini);
//		
//		Calendar newini = Calendar.getInstance();
//		newini.set(2013,2,2,0,0,0);
//		event1.setDateSt(newini);			
//		assertEquals("---->eventTest error<----",event1.getDateSt(),newini);
//		
//		Calendar fin = event1.getDateEnd();
//		assertEquals("---->eventTest error<----",event1.getDateEnd(),fin);
//		
//		Calendar newfin = Calendar.getInstance();
//		newfin.set(2013,4,4,0,0,0);
//		event1.setDateSt(newfin);			
//		assertEquals("---->eventTest error<----",event1.getDateSt(),newfin);			
//		
//		boolean itsintern = event1.isIntern();
//		assertEquals("---->eventTest error<----",event1.isIntern(),itsintern);			
//		
//		boolean newitsintern = true;
//		event1.setIntern(newitsintern);
//		assertEquals("---->eventTest error<----",event1.isIntern(),newitsintern);			
//
//		String dir = event1.getAddress();
//		assertEquals("---->eventTest error<----",event1.getAddress(),dir);
//		
//		String newdir = "Calle falsa 123";
//		event1.setAddress(newdir);
//		assertEquals("---->eventTest error<----",event1.getAddress(),newdir);
//		
//		short cap = event1.getCapacity();
//		assertEquals("---->eventTest error<----",event1.getCapacity(),cap);
//		
//		short newcap = 30;
//		event1.setCapacity(newcap);
//		assertEquals("---->eventTest error<----",event1.getCapacity(),newcap);
//		
//		String eventString = "Event [eventId=" + event1.getEventId() + ", name=" + event1.getName()
//				+ ", description=" + event1.getDescription() + ", dateSt=" + event1.getDateSt()
//				+ ", dateEnd=" + event1.getDateEnd() + ", intern=" + event1.isIntern() + ", address="
//				+ event1.getAddress() + ", capacity=" + event1.getAddress() + "]";
//		assertEquals("---->eventTest error<----",event1.toString(),eventString);			
//	}
//*/
//	// Update an event
//	@Test
//	public void testUpdateEvent() throws InputValidationException,
//			InstanceNotFoundException, EventRegisterUsersError {
//
//    	EventService serv =  EventServiceFactory.getService();
//		Calendar fechaIni1 = Calendar.getInstance();
//		fechaIni1.set(2013, 1, 1,0,0,0);
//		
//		Calendar fechaFin1 = Calendar.getInstance();
//		fechaFin1.set(2013, 1, 3,0,0,0);
//		Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false,"Calle 1",(short) 20);
//		event1 = serv.addEvent(event1);
//		try {
//			event1.setName("New name");
//			event1.setDescription("New description");
//			Calendar nuevaFechaIni = Calendar.getInstance();
//			fechaIni1.set(2013, 1, 2,0,0,0);
//			Calendar nuevaFechaFin = Calendar.getInstance();
//			fechaFin1.set(2013, 3, 4,0,0,0);			
//			event1.setDateSt(nuevaFechaIni);
//			event1.setDateSt(nuevaFechaFin);
//			event1.setIntern(true);
//			event1.setAddress("New address");
//			event1.setCapacity((short) 80);
//			
//			Event updatedEvent = serv.findEvent(event1.getEventId());
//			assertEquals(event1, updatedEvent);
//
//		} finally {
//			// Clear Database
//			removeEvent(event1.getEventId());
//		}
//	}		
//
//		// Update event with registered users
//		@Test(expected = EventRegisterUsersError.class)
//	    public void testUpdateEvent2() throws InputValidationException,
//	            InstanceNotFoundException, EventRegisterUsersError, OverCapacityError {	
//	    	EventService serv =  EventServiceFactory.getService();
//			Calendar fechaIni1 = Calendar.getInstance();
//			fechaIni1.set(2013, 1, 1,0,0,0);
//			
//			Calendar fechaFin1 = Calendar.getInstance();
//			fechaFin1.set(2013, 1, 3,0,0,0);
//			Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false,"Calle 1",(short) 20);
//			event1 = serv.addEvent(event1);
//			serv.responseToEvent("Alejandro", event1.getEventId(), true);
//			serv.responseToEvent("Francisco", event1.getEventId(), true);	
//			try {
//				event1.setCapacity((short) 2);
//				serv.updateEvent(event1);
//			} finally {
//				// Clear Database
//				removeEvent(event1.getEventId());
//			}
//		}			
//
//		// Update event with a start date after the end date
//		@Test(expected = InputValidationException.class)
//	    public void testUpdateEvent3() throws InputValidationException,
//	            InstanceNotFoundException, EventRegisterUsersError {	
//	    	EventService serv =  EventServiceFactory.getService();
//			Calendar fechaIni1 = Calendar.getInstance();
//			fechaIni1.set(2013, 1, 1,0,0,0);
//			
//			Calendar fechaFin1 = Calendar.getInstance();
//			fechaFin1.set(2013, 1, 3,0,0,0);
//			Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false,"Calle 1",(short) 20);
//			event1 = serv.addEvent(event1);
//			try {
//				Calendar newDate = Calendar.getInstance();
//				newDate.set(2013,1, 4,0,0,0);
//				event1.setDateSt(newDate);
//				serv.updateEvent(event1);
//			} finally {
//				// Clear Database
//				removeEvent(event1.getEventId());
//			}
//		}			
//		
//		// Update to an invalid event
//		@Test(expected = InputValidationException.class)
//		public void testUpdateInvalidMovie() throws InputValidationException,
//				InstanceNotFoundException, EventRegisterUsersError {
//
//	    	EventService serv =  EventServiceFactory.getService();
//			Calendar fechaIni1 = Calendar.getInstance();
//			fechaIni1.set(2013, 1, 1,0,0,0);
//			
//			Calendar fechaFin1 = Calendar.getInstance();
//			fechaFin1.set(2013, 1, 3,0,0,0);
//			Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false,"Calle 1",(short) 20);
//			event1 = serv.addEvent(event1);
//			try {
//				// Check movie title not null
//				event1 = serv.findEvent(event1.getEventId());
//				event1.setName(null);
//				serv.updateEvent(event1);
//			} finally {
//				// Clear Database
//				removeEvent(event1.getEventId());
//			}
//
//		}
//		
//		// Update a nonexistent event
//		@Test(expected = InstanceNotFoundException.class)
//		public void testUpdateNonExistentEvent() throws InputValidationException,
//				InstanceNotFoundException, EventRegisterUsersError {
//
//	    	EventService serv =  EventServiceFactory.getService();
//			Calendar fechaIni1 = Calendar.getInstance();
//			fechaIni1.set(2013, 1, 1,0,0,0);
//			
//			Calendar fechaFin1 = Calendar.getInstance();
//			fechaFin1.set(2013, 1, 3,0,0,0);
//			Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false,"Calle 1",(short) 20);
//
//			event1.setEventId((long) 1);
//			serv.updateEvent(event1);
//		}
//		
//		// Delete an event
//		@Test(expected = InstanceNotFoundException.class)
//	    public void testDeleteEvent1() throws InputValidationException,
//	            InstanceNotFoundException, EventRegisterUsersError {	
//	    	EventService serv =  EventServiceFactory.getService();
//			Calendar fechaIni1 = Calendar.getInstance();
//			fechaIni1.set(2013, 1, 1,0,0,0);
//			
//			Calendar fechaFin1 = Calendar.getInstance();
//			fechaFin1.set(2013, 1, 3,0,0,0);
//			Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false,"Calle 1",(short) 20);
//			event1 = serv.addEvent(event1);
//
//			boolean exceptionCatched = false;
//			try {
//				serv.deleteEvent(event1.getEventId());
//			} catch (InstanceNotFoundException e) {
//				exceptionCatched = true;
//			}
//			assertTrue(!exceptionCatched);
//
//			serv.findEvent(event1.getEventId());
//		}
//		
//		
//		// Delete event with registered users
//		@Test(expected = EventRegisterUsersError.class)
//	    public void testDeleteEvent2() throws InputValidationException,
//	            InstanceNotFoundException, EventRegisterUsersError, OverCapacityError {	
//	    	EventService serv =  EventServiceFactory.getService();
//			Calendar fechaIni5 = Calendar.getInstance();
//			fechaIni5.set(2013, 1, 1,0,0,0);
//			Calendar fechaFin5 = Calendar.getInstance();
//			fechaFin5.set(2013, 1, 3,0,0,0);
//			Event event5 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni5,fechaFin5,false,"Calle 5",(short) 20);
//			event5 = serv.addEvent(event5);
//			
//			serv.responseToEvent("Alejandro", event5.getEventId(), true);
//			serv.responseToEvent("Francisco", event5.getEventId(), true);
//			
//			boolean exceptionCatched = false;
//			try {
//				serv.deleteEvent(event5.getEventId());
//			} catch(EventRegisterUsersError e) {
//				exceptionCatched = true;
//			}
//			assertTrue(!exceptionCatched);
//		}		
//		
//		// Delete an event and try to find it
//		@Test(expected = InstanceNotFoundException.class)
//		public void testDeleteEvent3() throws InstanceNotFoundException, InputValidationException, EventRegisterUsersError {
//
//	    	EventService serv =  EventServiceFactory.getService();
//			Calendar fechaIni1 = Calendar.getInstance();
//			fechaIni1.set(2013, 1, 1,0,0,0);
//			Calendar fechaFin1 = Calendar.getInstance();
//			fechaFin1.set(2013, 1, 3,0,0,0);
//			Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false,"Calle 1",(short) 20);
//			event1 = serv.addEvent(event1);
//			
//			boolean exceptionCatched = false;
//			try {
//				serv.deleteEvent(event1.getEventId());
//			} catch (InstanceNotFoundException e) {
//				exceptionCatched = true;
//			}
//			assertTrue(!exceptionCatched);
//
//			serv.findEvent(event1.getEventId());
//
//		}
//		
//		// Delete nonexistent event
//		@Test(expected = InstanceNotFoundException.class)
//		public void testDeleteEvent4() throws InstanceNotFoundException, EventRegisterUsersError {
//
//	    	EventService serv =  EventServiceFactory.getService();
//			Calendar fechaIni1 = Calendar.getInstance();
//			fechaIni1.set(2013, 1, 1,0,0,0);
//			Calendar fechaFin1 = Calendar.getInstance();
//			fechaFin1.set(2013, 1, 3,0,0,0);
//			Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false,"Calle 1",(short) 20);
//			
//			serv.deleteEvent(event1.getEventId());
//		}
//		
//		// Find event by keyword
//		@Test
//	    public void testFindEventByKeyword1() throws InputValidationException,
//	            InstanceNotFoundException, EventRegisterUsersError {
//			EventService serv =  EventServiceFactory.getService();
//			Calendar fechaIni1 = Calendar.getInstance();
//			fechaIni1.set(2013, 1, 1);
//			Calendar fechaFin1 = Calendar.getInstance();
//			fechaFin1.set(2013, 1, 3);
//			Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false,"Calle 1",(short) 20);
//			event1 = serv.addEvent(event1);
//			
//			Calendar fechaIni2 = Calendar.getInstance();
//			fechaIni2.set(2013, 1, 2);
//			Calendar fechaFin2 = Calendar.getInstance();
//			fechaFin2.set(2013, 1, 4);
//			Event event2 = new Event("manzana de caramelo","Evento2 descripcion",fechaIni2,fechaFin2,true,"Calle 2",(short) 25);
//			event2 = serv.addEvent(event2);
//			
//			Calendar fechaIni3 = Calendar.getInstance();
//			fechaIni3.set(2013, 1, 3);
//			Calendar fechaFin3 = Calendar.getInstance();
//			fechaFin3.set(2013, 1, 5);
//			Event event3 = new Event("caramelo","Evento3 descripcion",fechaIni3,fechaFin3,true,"Calle 3",(short) 10);
//			event3 = serv.addEvent(event3);
//			
//			Calendar fechaIni4 = Calendar.getInstance();
//			fechaIni4.set(2013, 1, 4);
//			Calendar fechaFin4 = Calendar.getInstance();
//			fechaFin4.set(2013, 1, 5);
//			Event event4 = new Event("evento4","Evento4 descripcion",fechaIni4,fechaFin4,true,"Calle 4",(short) 5);
//			event4 = serv.addEvent(event4);
//			
//			ArrayList<Event> eventsFound = (ArrayList<Event>) serv.findEventByKeyword("caramelo", null, null);
//			int i = 0;
//			while(i < eventsFound.size()){
//				i++;
//			}			
//	    	assertEquals(eventsFound.size(),(short) 2);
//	    	
//			// Clear Database
//			removeEvent(event1.getEventId());
//			removeEvent(event2.getEventId());
//			removeEvent(event3.getEventId());
//			removeEvent(event4.getEventId());
//		}		
//		
//		// Find event between dates
//		@Test
//		public void testFindEventByKeyword2() throws InputValidationException,
//        InstanceNotFoundException, EventRegisterUsersError {
//			EventService serv =  EventServiceFactory.getService();
//			Calendar fechaIni1 = Calendar.getInstance();
//			fechaIni1.set(2013, 1, 1);
//			Calendar fechaFin1 = Calendar.getInstance();
//			fechaFin1.set(2013, 1, 3);
//			Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false,"Calle 1",(short) 20);
//			event1 = serv.addEvent(event1);
//			
//			Calendar fechaIni2 = Calendar.getInstance();
//			fechaIni2.set(2013, 1, 2);
//			Calendar fechaFin2 = Calendar.getInstance();
//			fechaFin2.set(2013, 1, 4);
//			Event event2 = new Event("manzana de caramelo","Evento2 descripcion",fechaIni2,fechaFin2,true,"Calle 2",(short) 25);
//			event2 = serv.addEvent(event2);
//			
//			Calendar fechaIni3 = Calendar.getInstance();
//			fechaIni3.set(2013, 1, 3);
//			Calendar fechaFin3 = Calendar.getInstance();
//			fechaFin3.set(2013, 1, 5);
//			Event event3 = new Event("caramelo","Evento3 descripcion",fechaIni3,fechaFin3,true,"Calle 3",(short) 10);
//			event3 = serv.addEvent(event3);
//			
//			Calendar fechaIni4 = Calendar.getInstance();
//			fechaIni4.set(2013, 7, 7);
//			Calendar fechaFin4 = Calendar.getInstance();
//			fechaFin4.set(2013, 8, 8);
//			Event event4 = new Event("evento4","Evento4 descripcion",fechaIni4,fechaFin4,true,"Calle 4",(short) 5);
//			event4 = serv.addEvent(event4);		
//			
//			Calendar dateTest1 = Calendar.getInstance();
//			dateTest1.set(2013, 1, 1);
//			Calendar dateTest2 = Calendar.getInstance();
//			dateTest2.set(2013, 6, 6);
//			ArrayList<Event> eventsFound = (ArrayList<Event>) serv.findEventByKeyword(null,dateTest1,dateTest2);
//			int i = 0;
//			while(i < eventsFound.size()){
//				i++;
//			}
//			assertEquals(eventsFound.size(),(short) 3);
//			
//			// Clear Database
//			removeEvent(event1.getEventId());
//			removeEvent(event2.getEventId());
//			removeEvent(event3.getEventId());
//			removeEvent(event4.getEventId());
//		}
//
//		// Find event by keyword and dates	
//		@Test
//		public void testFindEventByKeyword3() throws InputValidationException,
//        InstanceNotFoundException, EventRegisterUsersError {
//			EventService serv =  EventServiceFactory.getService();
//			Calendar fechaIni1 = Calendar.getInstance();
//			fechaIni1.set(2013, 1, 1);
//			Calendar fechaFin1 = Calendar.getInstance();
//			fechaFin1.set(2013, 1, 3);
//			Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false,"Calle 1",(short) 20);
//			event1 = serv.addEvent(event1);
//			
//			Calendar fechaIni2 = Calendar.getInstance();
//			fechaIni2.set(2013, 1, 2);
//			Calendar fechaFin2 = Calendar.getInstance();
//			fechaFin2.set(2013, 1, 4);
//			Event event2 = new Event("manzana de caramelo","Evento2 descripcion",fechaIni2,fechaFin2,true,"Calle 2",(short) 25);
//			event2 = serv.addEvent(event2);
//			
//			Calendar fechaIni3 = Calendar.getInstance();
//			fechaIni3.set(2013, 1, 3);
//			Calendar fechaFin3 = Calendar.getInstance();
//			fechaFin3.set(2013, 1, 5);
//			Event event3 = new Event("caramelo","Evento3 descripcion",fechaIni3,fechaFin3,true,"Calle 3",(short) 10);
//			event3 = serv.addEvent(event3);
//			
//			Calendar fechaIni4 = Calendar.getInstance();
//			fechaIni4.set(2013, 7, 7);
//			Calendar fechaFin4 = Calendar.getInstance();
//			fechaFin4.set(2013, 8, 8);
//			Event event4 = new Event("evento4","Evento4 descripcion",fechaIni4,fechaFin4,true,"Calle 4",(short) 5);
//			event4 = serv.addEvent(event4);		
//			
//			Calendar dateTest1 = Calendar.getInstance();
//			dateTest1.set(2013, 1, 1);
//			Calendar dateTest2 = Calendar.getInstance();
//			dateTest2.set(2013, 6, 6);
//			ArrayList<Event> eventsFound = (ArrayList<Event>) serv.findEventByKeyword("caramelo",dateTest1,dateTest2);
//			int i = 0;
//			while(i < eventsFound.size()){
//				i++;
//			}
//			assertEquals(eventsFound.size(),(short) 2);
//			
//			// Clear Database
//			removeEvent(event1.getEventId());
//			removeEvent(event2.getEventId());
//			removeEvent(event3.getEventId());
//			removeEvent(event4.getEventId());
//		}			
//		
//		// Positive responses to the event 6
//		@Test
//		public void testResponseToEvent1() throws InputValidationException,
//        InstanceNotFoundException, OverCapacityError, EventRegisterUsersError {
//			EventService serv =  EventServiceFactory.getService();
//			Calendar fechaIni6 = Calendar.getInstance();
//			fechaIni6.set(2013, 1, 1);
//			Calendar fechaFin6 = Calendar.getInstance();
//			fechaFin6.set(2013, 1, 3);
//			Event event6 = new Event("tarea comer mandarina","Evento6 descripcion",fechaIni6,fechaFin6,false,"Calle 6",(short) 2);
//			event6 = serv.addEvent(event6);
//
//			serv.responseToEvent("Alejandro", event6.getEventId(), true);
//			serv.responseToEvent("Francisco", event6.getEventId(), true);
//			serv.responseToEvent("Pepe", event6.getEventId(), false);
//
//			ArrayList <Response> listResp = (ArrayList<Response>) serv.getResponses(event6.getEventId(), true);
//			int i = 0;
//			while(i < listResp.size()){
//				i++;
//			}
//			assertEquals(listResp.size(),(short) 2);
//
//		}
//		
//		// Negative responses to the event 7
//		@Test
//		public void testResponseToEvent2() throws InputValidationException,
//        InstanceNotFoundException, OverCapacityError, EventRegisterUsersError {
//			EventService serv =  EventServiceFactory.getService();
//			Calendar fechaIni7 = Calendar.getInstance();
//			fechaIni7.set(2013, 1, 1);
//			Calendar fechaFin7 = Calendar.getInstance();
//			fechaFin7.set(2013, 1, 3);
//			Event event7 = new Event("comer kit-kat","Evento7 descripcion",fechaIni7,fechaFin7,false,"Calle 1",(short) 20);
//			event7 = serv.addEvent(event7);
//
//			serv.responseToEvent("Alejandro", event7.getEventId(), true);
//			serv.responseToEvent("Francisco", event7.getEventId(), true);
//			serv.responseToEvent("Pepe", event7.getEventId(), false);
//			
//			ArrayList <Response> listResp = (ArrayList<Response>) serv.getResponses(event7.getEventId(), false);
//			int i = 0;
//			while(i < listResp.size()){
//				i++;
//			}
//			assertEquals(listResp.size(),(short) 1);
//
//
//		}		
//		
//		// Positive-negative responses to the event 8
//		@Test
//		public void testResponseToEvent3() throws InputValidationException,
//        InstanceNotFoundException, OverCapacityError, EventRegisterUsersError {
//			EventService serv =  EventServiceFactory.getService();
//			Calendar fechaIni8 = Calendar.getInstance();
//			fechaIni8.set(2013, 1, 1);
//			Calendar fechaFin8 = Calendar.getInstance();
//			fechaFin8.set(2013, 1, 3);
//			Event event8 = new Event("ver la television","Evento8 descripcion",fechaIni8,fechaFin8,false,"Calle 8",(short) 20);
//			event8 = serv.addEvent(event8);
//
//			serv.responseToEvent("Alejandro", event8.getEventId(), true);
//			serv.responseToEvent("Francisco", event8.getEventId(), true);
//			serv.responseToEvent("Pepe", event8.getEventId(), false);
//
//			ArrayList <Response> listResp = (ArrayList<Response>) serv.getResponses(event8.getEventId(), null);
//			int i = 0;
//			while(i < listResp.size()){
//				i++;
//			}
//			assertEquals(listResp.size(),(short) 3);
//		}
//		
//		// Respond to an event that has already exceeded the capacity
//		@Test(expected = OverCapacityError.class)
//		public void testResponseToEvent4() throws InputValidationException,
//        InstanceNotFoundException, OverCapacityError, EventRegisterUsersError {
//			EventService serv =  EventServiceFactory.getService();
//			Calendar fechaIni9 = Calendar.getInstance();
//			fechaIni9.set(2013, 1, 1);
//			Calendar fechaFin9 = Calendar.getInstance();
//			fechaFin9.set(2013, 1, 3);
//			Event event9 = new Event("exposicion de Picasso","Evento9 descripcion",fechaIni9,fechaFin9,false,"Calle 9",(short) 2);
//			event9 = serv.addEvent(event9);
//			
//			serv.responseToEvent("Alejandro", event9.getEventId(), true);
//			serv.responseToEvent("Francisco", event9.getEventId(), true);
//			
//			boolean exceptionCatched = false;
//			try {
//				serv.responseToEvent("Manolo", event9.getEventId(), true);
//			} catch (OverCapacityError e) {
//				exceptionCatched = true;
//			}
//			assertTrue(exceptionCatched);
//		}
//		
//		// Response a nonexistent event
//		@Test(expected = InstanceNotFoundException.class)
//		public void testResponseToEvent5() throws InstanceNotFoundException, EventRegisterUsersError, OverCapacityError {
//
//	    	EventService serv =  EventServiceFactory.getService();
//			Calendar fechaIni1 = Calendar.getInstance();
//			fechaIni1.set(2013, 1, 1,0,0,0);
//			Calendar fechaFin1 = Calendar.getInstance();
//			fechaFin1.set(2013, 1, 3,0,0,0);
//			Event event1 = new Event("tarea comer manzana","Evento1 descripcion",fechaIni1,fechaFin1,false,"Calle 1",(short) 20);
//			
//			serv.responseToEvent("Manolo", event1.getEventId(), true);
//		}
//		
//		// Get answers by event ID
//		@Test
//		public void testResponseToEvent6() throws InputValidationException,
//        InstanceNotFoundException, OverCapacityError, EventRegisterUsersError {
//			EventService serv =  EventServiceFactory.getService();
//			Calendar fechaIni11 = Calendar.getInstance();
//			fechaIni11.set(2013, 1, 1);
//			Calendar fechaFin11 = Calendar.getInstance();
//			fechaFin11.set(2013, 1, 3);
//			Event event11 = new Event("ver la television","Evento8 descripcion",fechaIni11,fechaFin11,false,"Calle 11",(short) 20);
//			event11 = serv.addEvent(event11);
//
//			serv.responseToEvent("Alejandro", event11.getEventId(), true);
//			serv.responseToEvent("Francisco", event11.getEventId(), true);
//			serv.responseToEvent("Pepe", event11.getEventId(), false);
//
//			Response resp1 = serv.getResponsesByID((long) 1);
//
//			assertEquals(resp1.getUsername(),"Alejandro");
//		}
//		
//		// Get answers by event ID in a nonexistent event
//		@Test(expected = InstanceNotFoundException.class)
//		public void testResponseToEvent7() throws InputValidationException,
//        InstanceNotFoundException, OverCapacityError, EventRegisterUsersError {
//			EventService serv =  EventServiceFactory.getService();
//			Calendar fechaIni11 = Calendar.getInstance();
//			fechaIni11.set(2013, 1, 1);
//			Calendar fechaFin11 = Calendar.getInstance();
//			fechaFin11.set(2013, 1, 3);
//			Event event11 = new Event("ver la television","Evento8 descripcion",fechaIni11,fechaFin11,false,"Calle 11",(short) 20);
//
//			serv.responseToEvent("Alejandro", event11.getEventId(), true);
//			serv.responseToEvent("Francisco", event11.getEventId(), true);
//			serv.responseToEvent("Pepe", event11.getEventId(), false);
//
//			Response resp1 = serv.getResponsesByID((long) 1);
//
//			assertEquals(resp1.getUsername(),"Alejandro");
//		}
}
