package es.udc.ws.events.model.response;

import java.util.Calendar;

public class Response {
	private Long responseId;
	private Long eventId;
	private String username;
	private boolean assists;
	private Calendar respDate;
	
	
	public Response(String username,Long eventId, boolean response, Calendar respDate) {
		this.username = username;
		this.assists = response;
		this.respDate = respDate;
		this.setEventId(eventId);
	}
	
	
	public Response(Long responseId,String username, Long eventId,Calendar respDate, boolean response) {
		this(username,eventId,response,respDate);
		this.responseId = responseId;
	}


	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isAssists() {
		return assists;
	}
	public void setAssists(boolean response) {
		this.assists = response;
	}
	public Long getId() {
		return responseId;
	}
	public void setId(Long id) {
		this.responseId = id;
	}
	public Calendar getRespDate() {
		return respDate;
	}
	public void setRespDate(Calendar respDate) {
		this.respDate = respDate;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((respDate == null) ? 0 : respDate.hashCode());
		result = prime * result + (assists ? 1231 : 1237);
		result = prime * result
				+ ((responseId == null) ? 0 : responseId.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Response other = (Response) obj;
		if (respDate == null) {
			if (other.respDate != null)
				return false;
		} else if (!respDate.equals(other.respDate))
			return false;
		if (assists != other.assists)
			return false;
		if (responseId == null) {
			if (other.responseId != null)
				return false;
		} else if (!responseId.equals(other.responseId))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}


	public Long getEventId() {
		return eventId;
	}


	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}


	@Override
	public String toString() {
		return "Response [responseId=" + responseId + ", eventId=" + eventId
				+ ", username=" + username + ", assists=" + assists
				+ ", respDate=" + respDate + "]";
	}
	
}
