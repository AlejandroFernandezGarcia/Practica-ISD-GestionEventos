package es.udc.ws.events.model.eventservice;

import static es.udc.ws.events.model.util.ModelConstants.TEMPLATE_DATA_SOURCE;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import es.udc.ws.events.exceptions.EventRegisterUsersError;
import es.udc.ws.events.exceptions.InputDateError;
import es.udc.ws.events.exceptions.OverCapacityError;
import es.udc.ws.events.model.event.Event;
import es.udc.ws.events.model.event.SqlEventDao;
import es.udc.ws.events.model.event.SqlEventDaoFactory;
import es.udc.ws.events.model.response.Response;
import es.udc.ws.events.model.response.SqlResponseDao;
import es.udc.ws.events.model.response.SqlResponseDaoFactory;
import es.udc.ws.events.validator.PropertyValidatorEvent;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;


public class EventServiceImpl implements EventService {

	private DataSource dataSource;
	private SqlEventDao eventDao = null;
    private SqlResponseDao responseDao = null;
    
	public EventServiceImpl() {
		dataSource = DataSourceLocator.getDataSource(TEMPLATE_DATA_SOURCE);
        eventDao = SqlEventDaoFactory.getDao();
        responseDao = SqlResponseDaoFactory.getDao();
	}
	
	private void validateEvent(Event event) throws InputValidationException, InputDateError{
		PropertyValidatorEvent.validateMandatoryString("name",event.getName()) ;
		PropertyValidatorEvent.validateDate("dates", event.getDateSt(),event.getDateEnd());
		PropertyValidatorEvent.validateMandatoryString("address", event.getAddress());
		PropertyValidatorEvent.validateIntern("intern", event.isIntern());
		PropertyValidatorEvent.validateShort("capacity", event.getCapacity());
	}
	
	@Override
	public Event addEvent(Event event) throws InputValidationException,InputDateError {
		
		validateEvent(event);
		try (Connection connection = dataSource.getConnection()) {

            try {

                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
                Event createdEvent = eventDao.create(connection, event);

                /* Commit. */
                connection.commit();

                return createdEvent;

            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}

	@Override
	public void updateEvent(Event event) throws InputValidationException,InstanceNotFoundException, EventRegisterUsersError, InputDateError {
		try (Connection connection = dataSource.getConnection()) {
			
            try {
            	connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
    			connection.setAutoCommit(false);
    			ArrayList<Response> listaRespuestas = responseDao.find(connection, event.getEventId(), null);
    			throw new EventRegisterUsersError("Error: Cannot be update, registered users");
            } catch (InstanceNotFoundException e) {
            	validateEvent(event);
                try{
            		eventDao.update(connection, event);
                } catch(InstanceNotFoundException err){
                	throw new InstanceNotFoundException(event.getEventId(),Event.class.getName());
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        
        }

		
	}

	@Override
	public void deleteEvent(Long eventId) throws InstanceNotFoundException,EventRegisterUsersError {
		try (Connection connection = dataSource.getConnection()) {
            try {
            	connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            	connection.setAutoCommit(false);
            	Event event = eventDao.find(connection, eventId);
            	ArrayList<Response> listaRespuestas = responseDao.find(connection, event.getEventId(), null);
            	throw new EventRegisterUsersError("Error: Cannot be deleted, registered users");
            } catch (InstanceNotFoundException e) {
            	try{
            		eventDao.delete(connection, eventId);
            	} catch (InstanceNotFoundException err){
            		throw new InstanceNotFoundException(eventId,Event.class.getName());
            	}
                connection.commit();
                //throw e;
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}

	@Override
	public Event findEvent(Long eventId) throws InstanceNotFoundException {
		try (Connection connection = dataSource.getConnection()) {
            return eventDao.find(connection, eventId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}

	@Override
	public List<Event> findEventByKeyword(String keywords, Calendar dateSt,
			Calendar dateEnd) throws InstanceNotFoundException {
		try (Connection connection = dataSource.getConnection()) {
            return eventDao.findByKeyword(connection, keywords, dateSt, dateEnd);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}

	@Override
	public Long responseToEvent(String username, Long eventId, Boolean code)
			throws InstanceNotFoundException, OverCapacityError, InputDateError, EventRegisterUsersError {
		List<Response> lista;
		//cambiar todo
		Response response = null;
		try (Connection connection = dataSource.getConnection()) {
            try {
            	connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            	connection.setAutoCommit(false);
            	Event event;
            	try{
            		event = eventDao.find(connection, eventId);
            	}catch (InstanceNotFoundException er){
                	throw new InstanceNotFoundException(eventId,Event.class.getName());
            	}
            	//getDateSt o getDateEnd?
            	if (event.getDateSt().before(Calendar.getInstance())) throw new InputDateError("The event was expired");
            	if (code){
            		lista = responseDao.find(connection, event.getEventId(), true);
            		if ((lista.size()+1) > event.getCapacity()) throw new OverCapacityError("Full capacity");
            	}
                Calendar respDate = Calendar.getInstance();
        		response = new Response(username,eventId,code,respDate);
                lista = getResponses(event.getEventId(), null);
                int i = 0;
                while(i<lista.size()){
                	if(lista.get(i).getUsername().compareTo(username)==0){
                		return responseDao.update(connection, response);
                	}
                	i++;
                }
            	response = responseDao.create(connection, response);
                connection.commit();
                return response.getId();
        		
            } catch (InstanceNotFoundException err){
            	//connection.commit();
            	Calendar respDate = Calendar.getInstance();
            	response = new Response(username,eventId,code,respDate);
            	response = responseDao.create(connection, response);
                connection.commit();
                return response.getId();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}

	@Override
	public List<Response> getResponses(Long eventId, Boolean code)
			throws InstanceNotFoundException {
		try (Connection connection = dataSource.getConnection()) {
			eventDao.find(connection, eventId);
			return responseDao.find(connection, eventId, code);
		} catch (InstanceNotFoundException er){
        	throw new InstanceNotFoundException(eventId,Response.class.getName());
		} catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}

	@Override
	public Response getResponsesByID(Long responseId)
			throws InstanceNotFoundException {
		try (Connection connection = dataSource.getConnection()) {
			return responseDao.findById(connection, responseId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}

	

}
