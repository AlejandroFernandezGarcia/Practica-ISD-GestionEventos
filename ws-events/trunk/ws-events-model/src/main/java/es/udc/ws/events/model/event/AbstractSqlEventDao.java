package es.udc.ws.events.model.event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;





import es.udc.ws.util.exceptions.InstanceNotFoundException;

public abstract class AbstractSqlEventDao implements SqlEventDao {

    protected AbstractSqlEventDao() {
    }

	@Override
	public void update(Connection connection, Event event)
			throws InstanceNotFoundException {
		/* Create "queryString". */
        String queryString = "UPDATE Event"
                + " SET name = ?, description = ?, dateSt = ?, "
                + "dateEnd = ?,intern = ?, address = ?, capacity = ? WHERE eventId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setString(i++, event.getName());
            preparedStatement.setString(i++, event.getDescription());
            Timestamp dateSt = new Timestamp(event.getDateSt().getTime()
                    .getTime());
            preparedStatement.setTimestamp(i++, dateSt);
            Timestamp dateEnd = new Timestamp(event.getDateEnd().getTime()
                    .getTime());
            preparedStatement.setTimestamp(i++, dateEnd);
            preparedStatement.setBoolean(i++, event.isIntern());
            preparedStatement.setString(i++, event.getAddress());
            preparedStatement.setShort(i++, event.getCapacity());
            
            /* Execute query. */
            int updatedRows = preparedStatement.executeUpdate();

            if (updatedRows == 0) {
                throw new InstanceNotFoundException(event.getEventId(),
                        Event.class.getName());
            }

            if (updatedRows > 1) {
                throw new SQLException("Duplicate row for identifier = '"
                        + event.getEventId() + "' in table 'Movie'");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

	}

	@Override
	public void delete(Connection connection, Long eventId)
			throws InstanceNotFoundException {
		String queryString = "DELETE FROM Event WHERE eventId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, eventId);

            /* Execute query. */
            int removedRows = preparedStatement.executeUpdate();

            if (removedRows == 0) {
                throw new InstanceNotFoundException(eventId,
                        Event.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

	}

	@Override
	public Event find(Connection connection, Long eventId)
			throws InstanceNotFoundException {
        /* Create "queryString". */
        String queryString = "SELECT name, description, "
                + " dateSt, dateEnd, intern, address, capacity FROM Event WHERE eventId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, eventId.longValue());

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new InstanceNotFoundException(eventId,
                        Event.class.getName());
            }

            /* Get results. */
            i = 1;
            String name = resultSet.getString(i++);
            String description = resultSet.getString(i++);
            Calendar dateSt = Calendar.getInstance();
            dateSt.setTime(resultSet.getTimestamp(i++));
            Calendar dateEnd = Calendar.getInstance();
            dateEnd.setTime(resultSet.getTimestamp(i++));
            Boolean intern = resultSet.getBoolean(i++);
            String address = resultSet.getString(i++);
            Short capacity = resultSet.getShort(i++);

            /* Return events. */
            return new Event(eventId, name, description, dateSt, dateEnd,
                    intern, address, capacity);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}

	@Override
	public List<Event> findByKeyword(Connection connection, String keywords,
			Calendar fechaIni, Calendar fechaFin)
			throws InstanceNotFoundException {
		/* Create "queryString". */
		//areglar consulta
        String[] words = keywords != null ? keywords.split(" ") : null;
        String queryString = "SELECT eventId, name, description,"
                + " dateSt, dateEnd, intern, address, capacity FROM Event";
        if (words != null && words.length > 0) {
            queryString += " WHERE";
            for (int i = 0; i < words.length; i++) {
            	//queryString = " "+words[i];
                if (i > 0) {
                    queryString += " AND";
                }
                queryString += " LOWER(name) LIKE LOWER(?)";
            }
        }
        queryString += " ORDER BY name";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            if (words != null) {
                /* Fill "preparedStatement". */
                for (int i = 0; i < words.length; i++) {
                    preparedStatement.setString(i + 1, "%" + words[i] + "%");
                }
            }

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            /* Read events. */
            List<Event> events = new ArrayList<Event>();

            while (resultSet.next()) {

                int i = 1;
                Long eventId = new Long(resultSet.getLong(i++));
                String name = resultSet.getString(i++);
                String description = resultSet.getString(i++);
                Calendar dateSt = Calendar.getInstance();
                dateSt.setTime(resultSet.getTimestamp(i++));
                Calendar dateEnd = Calendar.getInstance();
                dateEnd.setTime(resultSet.getTimestamp(i++));
                Boolean intern = resultSet.getBoolean(i++);
                String address = resultSet.getString(i++);
                Short capacity = resultSet.getShort(i++);
                
                

                events.add(new Event(eventId, name, description, dateSt, dateEnd, intern, address, capacity));

            }

            /* Return events. */
            return events;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}

}
