package es.udc.ws.events.model.eventservice;

import static es.udc.ws.events.model.util.ModelConstants.TEMPLATE_DATA_SOURCE;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import es.udc.ws.events.model.event.Event;
import es.udc.ws.events.model.event.SqlEventDao;
import es.udc.ws.events.model.event.SqlEventDaoFactory;
import es.udc.ws.events.model.response.Response;
import es.udc.ws.events.model.response.SqlResponseDao;
import es.udc.ws.events.model.response.SqlResponseDaoFactory;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.events.exceptions.InputValidationException;


public class EventServiceImpl implements EventService {

	private DataSource dataSource;
	private SqlEventDao eventDao = null;
    private SqlResponseDao responseDao = null;
    
	public EventServiceImpl() {
		dataSource = DataSourceLocator.getDataSource(TEMPLATE_DATA_SOURCE);
        eventDao = SqlEventDaoFactory.getDao();
        responseDao = SqlResponseDaoFactory.getDao();
	}
	
	private void validateEvent(Event event){
		//como esta hecha la comprobacion anterior?
		// no puedo importar clase de ws-events-util
		
	}
	
	@Override /*devolver event??*/
	public Event addEvent(Event event) throws InputValidationException {
		
		validateEvent(event);
		if((event.getName()!=null)&&(event.getDateSt()!=null)&&(event.getDateEnd()!=null)&&(event.getAddress()!=null)&&(event.getCapacity()>0)){
			if (event.getDateSt().before(Calendar.getInstance())){
				throw new InputValidationException("The event start date cannot be earlier than the current date");
			}else if (event.getDateEnd().before(Calendar.getInstance())){
				throw new InputValidationException("The event end date cannot be earlier than the current date");
			}else if (event.getDateSt().after(event.getDateEnd())){
				throw new InputValidationException("The event end date cannot be earlier than the start date of the event");
			}
		}
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
	public void updateEvent(Event event) throws InputValidationException,InstanceNotFoundException,OperationAborted {
		ArrayList<Response> listaRespuestas;
		try (Connection connection2 = dataSource.getConnection()) {
			listaRespuestas = responseDao.find(connection2, event, null);
		} catch (SQLException e) {
            throw new RuntimeException(e);
        }
		if (listaRespuestas.size()!=0) {throw new OperationAborted("Cannot update an event that have register users");}
		validateEvent(event);
		if((event.getName()!=null)&&(event.getDateSt()!=null)&&(event.getDateEnd()!=null)&&(event.getAddress()!=null)&&(event.getCapacity()>0)){
			if (event.getDateSt().before(Calendar.getInstance())){
				throw new InputValidationException("The event start date cannot be earlier than the current date");
			}else if (event.getDateEnd().before(Calendar.getInstance())){
				throw new InputValidationException("The event end date cannot be earlier than the current date");
			}else if (event.getDateSt().after(event.getDateEnd())){
				throw new InputValidationException("The event end date cannot be earlier than the start date of the event");
			}
		}
        try (Connection connection = dataSource.getConnection()) {

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
	public void deleteEvent(Long eventId) throws InstanceNotFoundException,OperationAborted {
		ArrayList<Response> listaRespuestas;
		try (Connection connection2 = dataSource.getConnection()) {
			Event event = eventDao.find(connection2, eventId);
			listaRespuestas = responseDao.find(connection2, event, null);
		} catch (SQLException e) {
            throw new RuntimeException(e);
        }
		if (listaRespuestas.size()!=0) {throw new OperationAborted("Cannot delete an event that have register users");}
		try (Connection connection = dataSource.getConnection()) {

            try {

                /* Prepare connection. */
                connection
                        .setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

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
			throws InstanceNotFoundException {
		List<Response> lista;
		try (Connection connection = dataSource.getConnection()) {

            try {
            	Event event = eventDao.find(connection, eventId);
            	if (event.getDateEnd().before(Calendar.getInstance())) throw new OperationAborted("The event expired");
            	if (code){
            		lista = responseDao.find(connection, event, true);
            		if ((lista.size()+1) >= event.getCapacity()) throw new OperationAborted("Full capacity");
            	}
            	/* Prepare connection. */
                connection
                        .setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

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
			return responseDao.find(connection, event, code);
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
