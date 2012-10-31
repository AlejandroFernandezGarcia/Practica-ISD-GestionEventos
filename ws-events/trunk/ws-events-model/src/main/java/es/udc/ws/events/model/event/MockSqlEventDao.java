package es.udc.ws.events.model.event;

import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Connection;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class MockSqlEventDao implements SqlEventDao {
	private Calendar fecha1 = Calendar.getInstance();
	private Calendar fecha2 = Calendar.getInstance();
	
	
	@Override
	public Event create(Connection connection, Event event) {
		return new Event(1L, event.getName(), event.getDescription(),event.getFechaIni(), event.getFechaFin(), event.isInterno(),event.getDireccion(),event.getAforo());
	}

	@Override
	public void update(Connection connection, Long eventId)
			throws InstanceNotFoundException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void delete(Connection connection, Long eventId)
			throws InstanceNotFoundException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Event find_by_ID(Connection connection, Long eventId)
			throws InstanceNotFoundException {
		fecha1.set(2012,10,11);
		fecha2.set(2012,10,14);
		return new Event(eventId, "event name", "event description", fecha1,fecha2,true,"Calle Falsa 123",90);
	}

	

	

	@Override
	public Event find(Connection connection, String clave, Calendar fecha)
			throws InstanceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int response(Connection connection, String username, Long eventId,
			boolean asistencia) throws InstanceNotFoundException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList getResponses(Connection connection, Event evento,
			boolean respuesta) throws InstanceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList getResponses_by_ID(Connection connection, Long eventId)
			throws InstanceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
