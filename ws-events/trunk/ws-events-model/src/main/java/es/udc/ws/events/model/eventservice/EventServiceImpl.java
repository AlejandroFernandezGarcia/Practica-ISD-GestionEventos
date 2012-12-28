package es.udc.ws.events.model.eventservice;

import static es.udc.ws.events.model.util.ModelConstants.TEMPLATE_DATA_SOURCE;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import es.udc.ws.events.exceptions.EventRegisteredUsersException;
import es.udc.ws.events.exceptions.OverCapacityException;
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

	private void validateEvent(Event event) throws InputValidationException {
		PropertyValidatorEvent.validateMandatoryString("name", event.getName());
		PropertyValidatorEvent.validateDate("dates", event.getDateSt(),
				event.getDateEnd());
		PropertyValidatorEvent.validateMandatoryString("address",
				event.getAddress());
		PropertyValidatorEvent.validateIntern("intern", event.isIntern());
		PropertyValidatorEvent.validateShort("capacity", event.getCapacity());
		PropertyValidatorEvent.validateDate2("currentDate", event.getDateSt(),
				Calendar.getInstance());

	}

	@Override
	public Event addEvent(Event event) throws InputValidationException {

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
	public void updateEvent(Event event) throws InputValidationException,
			InstanceNotFoundException, EventRegisteredUsersException {
		try (Connection connection = dataSource.getConnection()) {

			try {
				connection
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				connection.setAutoCommit(false);
				Long numResponses = responseDao.numResponsesToEvent(connection,
						event.getEventId(), null);
				if (numResponses != 0) {
					throw new EventRegisteredUsersException(
							"Cannot update the event with registered users");
				}
				validateEvent(event);
				eventDao.update(connection, event);
				connection.commit();
			} catch (InstanceNotFoundException e) {
				throw new InstanceNotFoundException(event.getEventId(),
						Event.class.getName());
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
	public void deleteEvent(Long eventId) throws InstanceNotFoundException,
			EventRegisteredUsersException {
		try (Connection connection = dataSource.getConnection()) {
			connection
					.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			connection.setAutoCommit(false);
			Event event = eventDao.find(connection, eventId);
			Long numResponses = responseDao.numResponsesToEvent(connection,
					event.getEventId(), null);
			if (numResponses != 0) {
				throw new EventRegisteredUsersException(
						"Error: Cannot be deleted, registered users");
			}
			try {
				eventDao.delete(connection, eventId);
			} catch (InstanceNotFoundException err) {
				throw new InstanceNotFoundException(eventId,
						Event.class.getName());
			} catch (RuntimeException | Error e) {
				connection.rollback();
				throw e;
			}
			connection.commit();
			// throw e;

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
			Calendar dateEnd) {
		try (Connection connection = dataSource.getConnection()) {
			return eventDao
					.findByKeyword(connection, keywords, dateSt, dateEnd);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Long responseToEvent(String username, Long eventId, Boolean code)
			throws InstanceNotFoundException, OverCapacityException,
			EventRegisteredUsersException {
		try (Connection connection = dataSource.getConnection()) {
			try {
				connection
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				connection.setAutoCommit(false);
				Event event = eventDao.find(connection, eventId);
				if ((event.getDateSt()).before(Calendar.getInstance())) {
					throw new EventRegisteredUsersException("Event expired");
				}
				if (code) {
					Long num = responseDao.numResponsesToEvent(connection,
							eventId, true);
					if (num == event.getCapacity()) {
						throw new OverCapacityException("Full event");
					}
				}
				Response response = responseDao.findResponseByEventUser(
						connection, username, eventId);
				if (response != null) {
					responseDao.update(
							connection,
							new Response(response.getId(), response
									.getUsername(), response.getEventId(),
									response.getRespDate(), code));
				} else {
					response = new Response(username, eventId, code,
							Calendar.getInstance());
					response = responseDao.create(connection, response);
				}
				connection.commit();
				return response.getId();
			} catch (InstanceNotFoundException e) {
				throw new InstanceNotFoundException(eventId,
						Event.class.getName());
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
		} catch (InstanceNotFoundException er) {
			throw new InstanceNotFoundException(eventId,
					Response.class.getName());
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
