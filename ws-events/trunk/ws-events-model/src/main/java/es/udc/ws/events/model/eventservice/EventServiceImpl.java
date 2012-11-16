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
                connection
                        .setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
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
		ArrayList<Response> listaRespuestas;
		try (Connection connection = dataSource.getConnection()) {
			connection
            .setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
    connection.setAutoCommit(false);
			listaRespuestas = responseDao.find(connection, event.getEventId(), null);
			if (listaRespuestas.size()!=0) {throw new EventRegisterUsersError("Cannot update an event that have register users");}
			validateEvent(event);

            try {

                /* Prepare connection. */
                connection
                        .setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
                eventDao.update(connection, event);

                /* Commit. */
                connection.commit();

            } catch (InstanceNotFoundException e) {
                connection.commit();
                throw e;
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
		ArrayList<Response> listaRespuestas;
		
		try (Connection connection = dataSource.getConnection()) {

            try {
            	connection
                .setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            	connection.setAutoCommit(false);
            	Event event = eventDao.find(connection, eventId);
    			listaRespuestas = responseDao.find(connection, event.getEventId(), null);
    			if (listaRespuestas.size()!=0) {throw new EventRegisterUsersError("Cannot delete an event that have register users");}
                /* Prepare connection. */
                

                /* Do work. */
                eventDao.delete(connection, eventId);

                /* Commit. */
                connection.commit();

            } catch (InstanceNotFoundException e) {
                connection.commit();
                throw e;
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
			throws InstanceNotFoundException, OverCapacityError, InputDateError {
		List<Response> lista;
		try (Connection connection = dataSource.getConnection()) {

            try {
            	connection
                .setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            	connection.setAutoCommit(false);
            	Event event = eventDao.find(connection, eventId);
            	if (event.getDateEnd().before(Calendar.getInstance())) throw new InputDateError("The event expired");
            	if (code){
            		lista = responseDao.find(connection, event.getEventId(), true);
            		if ((lista.size()+1) >= event.getCapacity()) throw new OverCapacityError("Full capacity");
            	}
            	/* Prepare connection. */
                

                /* Do work. */
                Calendar respCal = Calendar.getInstance();
        		Response response = new Response(username,eventId,code,respCal);
                lista = getResponses(event.getEventId(), null);
                int i = 0;
                while(i<lista.size()){
                	if(response.getUsername().compareTo(username)==0){
                		return responseDao.update(connection, response);
                	}
                	i++;
                }
        		response = responseDao.create(connection, response);

                /* Commit. */
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
			Event event = eventDao.find(connection, eventId);
			return responseDao.find(connection, event.getEventId(), code);
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
