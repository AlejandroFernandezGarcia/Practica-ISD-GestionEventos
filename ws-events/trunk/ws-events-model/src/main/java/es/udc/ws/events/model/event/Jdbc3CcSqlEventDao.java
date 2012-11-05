package es.udc.ws.events.model.event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class Jdbc3CcSqlEventDao extends AbstractSqlEventDao {

	@Override
	public Event create(Connection connection, Event event) {
		/* Create "queryString". */
        String queryString = "INSERT INTO Event"
                + " (name, description, dateSt, dateEnd, intern, adress, capacity)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                        queryString, Statement.RETURN_GENERATED_KEYS)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setString(i++, event.getName());
            preparedStatement.setString(i++, event.getDescription());
            Timestamp dateSt = event.getDateSt() != null ? new Timestamp(
                    event.getDateEnd().getTime().getTime()) : null;
            preparedStatement.setTimestamp(i++, dateSt);
            Timestamp dateEnd = event.getDateEnd() != null ? new Timestamp(
                    event.getDateEnd().getTime().getTime()) : null;
            preparedStatement.setTimestamp(i++, dateEnd);
            preparedStatement.setBoolean(i++, event.isIntern());
            preparedStatement.setString(i++, event.getAdress());
            preparedStatement.setShort(i++, event.getCapacity());

            /* Execute query. */
            int insertedRows = preparedStatement.executeUpdate();

            if (insertedRows == 0) {
                throw new SQLException("Can not add row to table 'Event'");
            }

            if (insertedRows > 1) {
                throw new SQLException("Duplicate row in table 'Event'");
            }

            /* Get generated identifier. */
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (!resultSet.next()) {
                throw new SQLException(
                        "JDBC driver did not return generated key.");
            }
            Long eventId = resultSet.getLong(1);

            /* Return movie. */
            return new Event(eventId,event.getName(),event.getDescription(),event.getDateSt(),event.getDateEnd(),event.isIntern(), event.getAdress(), event.getCapacity());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
	

}
