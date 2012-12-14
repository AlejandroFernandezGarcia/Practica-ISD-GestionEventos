package es.udc.ws.events.dto;


public class ResponseDto {
	private Long responseId;
	private Long eventId;
	private String username;
	private boolean assists;
	
	public ResponseDto() {
	}
	
	public ResponseDto(Long responseId, Long eventId, String username, Boolean assists){
		this.responseId = responseId;
		this.eventId = eventId;
		this.username = username;
		this.assists = assists;
	}

	public Long getResponseId() {
		return responseId;
	}

	public void setResponseId(Long responseId) {
		this.responseId = responseId;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
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

	public void setAssists(boolean assists) {
		this.assists = assists;
	}


	@Override
	public String toString() {
		return "ResponseDto [responseId=" + responseId + ", eventId=" + eventId
				+ ", username=" + username + ", assists=" + assists + "]";
	}
	
}
