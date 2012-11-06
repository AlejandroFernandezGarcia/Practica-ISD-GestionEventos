package es.udc.ws.events.model.response;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;

import es.udc.ws.events.model.event.Event;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public interface SqlResponseDao {
	public Response create(Connection connection, Response response)
			throws InstanceNotFoundException;
	public Long update(Connection connection, Response response)
			throws InstanceNotFoundException;
	public void delete(Connection connection, Long responseId)
			throws InstanceNotFoundException;
	public void deleteByUserId(Connection connection, String userId)
			throws InstanceNotFoundException;
	public void deleteByEventId(Connection connection, Long eventId)
			throws InstanceNotFoundException;
	public ArrayList<Response> find(Connection connection, Event event,
			Boolean response) throws InstanceNotFoundException;
	public Response findById(Connection connection, Long responseId)
			throws InstanceNotFoundException;
}
