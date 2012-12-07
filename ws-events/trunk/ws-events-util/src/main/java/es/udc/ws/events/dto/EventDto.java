package es.udc.ws.events.dto;

import java.util.Calendar;

public class EventDto {

    private Long eventId;
    private String name;
    private String description;
    private Calendar dateSt;
    private int duration;
    private boolean intern = true;
    private String address;
    private short capacity = 0;
    
    
    public EventDto() {
    }

    public EventDto(Long eventId, String name, String description, Calendar dateSt, Integer duration, Boolean intern, String address, Short capacity) {
        this.eventId = eventId;
        this.name = name;
        this.description = description;
        this.dateSt = dateSt;
        this.duration = duration;
        this.intern = intern;
        this.address = address;
        this.capacity = capacity;
        
    }

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Calendar getDateSt() {
		return dateSt;
	}

	public void setDateSt(Calendar dateSt) {
		this.dateSt = dateSt;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public boolean isIntern() {
		return intern;
	}

	public void setIntern(boolean intern) {
		this.intern = intern;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public short getCapacity() {
		return capacity;
	}

	public void setCapacity(short capacity) {
		this.capacity = capacity;
	}

	@Override
	public String toString() {
		return "EventDto [eventId=" + eventId + ", name=" + name
				+ ", description=" + description + ", dateSt=" + dateSt
				+ ", duration=" + duration + ", intern=" + intern
				+ ", address=" + address + ", capacity=" + capacity + "]";
	}
    
}
