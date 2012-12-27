package es.udc.ws.events.test.model.eventservice;

import static es.udc.ws.events.model.util.ModelConstants.TEMPLATE_DATA_SOURCE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;

import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.Test;

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
	    }catch(EventRegisteredUsersException e){
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
		} catch (EventRegisteredUsersException e) {
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
		} catch (EventRegisteredUsersException e) {
			assertTrue(true);
		} catch (OverCapacityException e) {
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
		}catch(InstanceNotFoundException | EventRegisteredUsersException | InputValidationException e){
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
		} catch (EventRegisteredUsersException e) {
			assertTrue(false);
		}
	}
	//intenta eliminar un evento con usuarios registrados
	@Test
	public void testdeleteEvent3(){
		try{
			serv.deleteEvent((long) 1);
			assertTrue(false);
		}catch(EventRegisteredUsersException e){
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
			serv.responseToEvent("Pepe2", event.getEventId(), false);
			serv.responseToEvent("Alejandro", event.getEventId(), false);
			assertTrue(true);
		}catch(InstanceNotFoundException | EventRegisteredUsersException | OverCapacityException e){
			assertTrue(false);
		}
	}
	//Obtener las respuestas positivas a un evento.
	@Test
	public void testGetResponses1(){
		try {
			Event event = serv.findEvent((long) 1);
			ArrayList <Response> listResp = (ArrayList<Response>) serv.getResponses(event.getEventId(), true);
			assertEquals("---->testResponesToEvent2 error<----",listResp.size(), 1);
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
			assertEquals("---->testResponesToEvent3 error<----",listResp.size(), 2);
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
		
		try{
			Event event = serv.findEvent((long) 1);
			serv.responseToEvent("Alejandro", event.getEventId(), true);
			serv.responseToEvent("Francisco", event.getEventId(), true);
			assertTrue(false);
		} catch (InstanceNotFoundException e){
			assertTrue(false);
		} catch (OverCapacityException e) {
			assertTrue(true);
		} catch (EventRegisteredUsersException e) {
			assertTrue(false);
		}
	}
	
	//comprueba el caso de responder a un evento inexistente.
	@Test
	public void testResponseToEvent3(){
	    try{
			serv.responseToEvent("Alejandro", (long) 7, true);
			assertTrue(false);
		}catch(InstanceNotFoundException e){
	    	assertTrue(true);
	    }catch (EventRegisteredUsersException e) {
			assertTrue(false);
		}catch (OverCapacityException e){
			assertTrue(false);
		}
	}
	
	// Obtener respuestas por ID de evento
	@Test
	public void testGetResponsesByEventID1(){
		try {
			Response resp1 = serv.getResponsesByID((long) 1);
			assertEquals("---->testGetResponsesByEventID1 error<----",resp1.getUsername(),"Pepe");
		} catch (InstanceNotFoundException e){
			assertTrue(false);
		}
	}
	
	// Obtener respuestas por ID de un evento inexsistente
	@Test
	public void testGetResponsesByEventID2(){
		try {
			serv.getResponsesByID((long) 7);
			assertTrue(false);	
		} catch (InstanceNotFoundException e){
			assertTrue(true);
		}
	}
}
