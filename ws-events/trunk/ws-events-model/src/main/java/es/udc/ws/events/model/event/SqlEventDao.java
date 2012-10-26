package es.udc.ws.events.model.event;

import es.udc.ws.util.exceptions.InstanceNotFoundException;
import java.sql.Connection;

public interface SqlEventDao {

    public Event create(Connection connection, Event event);

    public Event find(Connection connection, Long eventId)
            throws InstanceNotFoundException;

}
