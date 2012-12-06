package es.udc.ws.events.model.response;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

public abstract class AbstractSqlResponseDao implements SqlResponseDao {
	
	protected AbstractSqlResponseDao(){}
	@Override
	public ArrayList<Response> find(Connection connection,Long eventId, Boolean assists){
		ArrayList<Response> listResponses = new ArrayList<Response>();
		/* Create "queryString". */
        String queryString = "SELECT responseId, userId, eventId, date," +
        		" assists FROM Response WHERE eventId = ?";
        if(assists != null){
        	queryString = queryString + " AND assists = ?";
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, eventId);
            if(assists != null){
            	preparedStatement.setBoolean(i++, assists);
            }
            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
            	return listResponses;
            }
            i = 1;
    		Long responseId = resultSet.getLong(i++);
    		String userId = resultSet.getString(i++);
    		eventId = resultSet.getLong(i++);
    		Calendar respDate = Calendar.getInstance();
    		respDate.setTime(resultSet.getTimestamp(i++));
    		Boolean confirm = resultSet.getBoolean(i++);
    		listResponses.add(new Response(responseId, userId, eventId,respDate,confirm));
            	/* Get results. */
        	while (resultSet.next()) {
        		i = 1;
        		responseId = resultSet.getLong(i++);
        		userId = resultSet.getString(i++);
        		eventId = resultSet.getLong(i++);
        		respDate = Calendar.getInstance();
        		respDate.setTime(resultSet.getTimestamp(i++));
        		confirm = resultSet.getBoolean(i++);
        		listResponses.add(new Response(responseId, userId, eventId,respDate,confirm));
        	}
            
            /* Return Responses. */
            return listResponses;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

	}

	@Override
	public Response findById(Connection connection, Long responseId) throws InstanceNotFoundException {
		/* Create "queryString". */
        String queryString = "SELECT responseId, userId, eventId, date," +
        		" assists FROM Response WHERE responseId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, responseId.longValue());

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new InstanceNotFoundException(responseId,
                        Response.class.getName());
            }

            /* Get results. */
            i = 1;
            Long response = resultSet.getLong(i++);
            String userId = resultSet.getString(i++);
            Long eventId = resultSet.getLong(i++);
            Calendar respDate = Calendar.getInstance();
            respDate.setTime(resultSet.getTimestamp(i++));
            Boolean confirm = resultSet.getBoolean(i++);
            
            /* Return Response. */
            return new Response(response, userId, eventId, respDate,confirm);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

	}
	public Response findResponseByEventUser(Connection connection, String userName, Long eventId){
		//devuelve la respuesta de un usuario a un evento o null sino tiene
		/* Create "queryString". */
        String queryString = "SELECT responseId, userId, eventId, date," +
        		" assists FROM Response WHERE eventId = ?";
        if(userName != null){
        	queryString = queryString + " AND userId = ?";
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, eventId);
            if(userName != null){
            	preparedStatement.setString(i++, userName);
            }
            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
            	return null;
            }
            i = 1;
    		Long responseId = resultSet.getLong(i++);
    		String userId = resultSet.getString(i++);
    		eventId = resultSet.getLong(i++);
    		Calendar respDate = Calendar.getInstance();
    		respDate.setTime(resultSet.getTimestamp(i++));
    		Boolean confirm = resultSet.getBoolean(i++);
    		return new Response(responseId, userId, eventId,respDate,confirm);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
	public Long numResponsesToEvent(Connection connection, Long eventId, Boolean assists){
		/* Create "queryString". */
		//solo interesan los que han contestado que si
        String queryString = "SELECT COUNT(*) FROM Response WHERE eventId = ?";
        if (assists != null){
        	 queryString += " AND assists = ?";
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, eventId);
            if (assists != null){
            	preparedStatement.setBoolean(i++, assists);
            }
            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
            	return (long)0;
            }
            i = 1;
    		return resultSet.getLong(i++);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
	@Override
	public Long update(Connection connection, Response response) throws InstanceNotFoundException {
		/* Create "queryString". */
        String queryString = "UPDATE Response"
                + " SET userId = ?, eventId = ?, date = ?, assists = ? " +
                "WHERE responseId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setString(i++, response.getUsername());
            preparedStatement.setLong(i++, response.getEventId());
            Timestamp date = new Timestamp(response.getRespDate().getTime()
                    .getTime());
            preparedStatement.setTimestamp(i++, date);
            preparedStatement.setBoolean(i++, response.isAssists());
            preparedStatement.setLong(i++, response.getId());
            
            
            /* Execute query. */
            int updatedRows = preparedStatement.executeUpdate();

            if (updatedRows == 0) {
                throw new InstanceNotFoundException(response.getId(),
                        Response.class.getName());
            }

            if (updatedRows > 1) {
                throw new SQLException("Duplicate row for identifier = '"
                        + response.getId() + "' in table 'Response'");
            }
            return response.getId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
	@Override
	public void delete(Connection connection, Long responseId)
			throws InstanceNotFoundException{
		String queryString = "DELETE FROM Response WHERE responseId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, responseId);

            /* Execute query. */
            int removedRows = preparedStatement.executeUpdate();

            if (removedRows == 0) {
                throw new InstanceNotFoundException(responseId,
                        Response.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

	}
	
}
