package es.udc.ws.events.model.response;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import es.udc.ws.events.model.event.Event;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class Jdbc3CcSqlResponseDAO extends AbstractSqlResponseDao {
	@Override
	public Response create(Connection connection, Response response)
			throws InstanceNotFoundException{
		/* Create "queryString". */
		String queryString = "INSERT INTO Response"
				+ " (userId, eventId, date, assists)"
				+ " VALUES (?, ?, ?, ?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				queryString, Statement.RETURN_GENERATED_KEYS)) {

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setString(i++, response.getUsername());
			preparedStatement.setLong(i++, response.getEventId());
			Timestamp date = 
					response.getRespDate() != null ?
					new Timestamp(response.getRespDate().getTime().getTime()): null;
			preparedStatement.setTimestamp(i++, date);
			preparedStatement.setBoolean(i++, response.isAssists());

			/* Execute query. */
			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'Response'");
			}

			if (insertedRows > 1) {
				throw new SQLException("Duplicate row in table 'Response'");
			}

			/* Get generated identifier. */
			ResultSet resultSet = preparedStatement.getGeneratedKeys();

			if (!resultSet.next()) {
				throw new SQLException(
						"JDBC driver did not return generated key.");
			}
			response.setId(resultSet.getLong(1));
			/* Return Response. */
			return response;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
}
