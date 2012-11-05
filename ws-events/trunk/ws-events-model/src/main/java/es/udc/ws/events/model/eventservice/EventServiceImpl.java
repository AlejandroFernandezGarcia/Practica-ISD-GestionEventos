package es.udc.ws.events.model.eventservice;

import static es.udc.ws.events.model.util.ModelConstants.TEMPLATE_DATA_SOURCE;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import es.udc.ws.events.model.event.Event;
import es.udc.ws.events.model.event.SqlEventDao;
import es.udc.ws.events.model.event.SqlEventDaoFactory;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;


public class EventServiceImpl implements EventService {

	private DataSource dataSource;
	private SqlEventDao eventDao = null;
    //private SqlResponseDao saleDao = null;
    
	public EventServiceImpl() {
		dataSource = DataSourceLocator.getDataSource(TEMPLATE_DATA_SOURCE);
        eventDao = SqlEventDaoFactory.getDao();
        //responseDao = SqlResponseDaoFactory.getDao();
	}
	
	private void validateEvent(Event event){
		//como esta hecha la comprobacion anterior?
		// no puedo importar clase de ws-events-util
		
	}
	
	@Override /*devolver event??*/
	public Event addEvent(Event event) throws InputValidationException {
		
		validateEvent(event);
		if((event.getName()!=null)&&(event.getDateSt()!=null)&&(event.getDateEnd()!=null)&&(event.getAdress()!=null)&&(event.getCapacity()>0)){
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
	public void updateEvent(Event event) throws InputValidationException,InstanceNotFoundException {
		//falta comprobacion: eventos en los que no haya nadie registrado
		validateEvent(event);
		if((event.getName()!=null)&&(event.getDateSt()!=null)&&(event.getDateEnd()!=null)&&(event.getAdress()!=null)&&(event.getCapacity()>0)){
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
	public void deleteEvent(Long eventId) throws InstanceNotFoundException {
		//falta condicion:borrar evento que no tiene nadie registrado
		
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

	

}
