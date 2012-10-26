package es.udc.ws.events.model.event;

import java.sql.Connection;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class MockSqlEventDao implements SqlEventDao {

	@Override
	public Event create(Connection connection, Event event) {
		return new Event(1L, event.getName(), event.getDescription());
	}

	@Override
	public Event find(Connection connection, Long eventId)
			throws InstanceNotFoundException {
		return new Event(eventId, "event name", "event description");
	}

}
