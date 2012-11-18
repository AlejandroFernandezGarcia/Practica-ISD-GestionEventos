package es.udc.ws.events.model.event;

import java.util.Calendar;

public class Event {

    private Long eventId;
    private String name;
    private String description;
    private Calendar dateSt;
    private Calendar dateEnd;
    private boolean intern = true;
    private String address;
    private short capacity = 0;

    public Event(String name, String description,Calendar fechaIni,Calendar fechaFin,boolean interno, String direccion, short aforo) {
        this.name = name;
        this.description = description;
        this.dateSt = fechaIni;
        this.dateEnd = fechaFin;
        this.intern = interno;
        this.address = direccion;
        this.capacity = aforo;
    }

    public Event(Long eventId, String name, String description, Calendar fechaIni,Calendar fechaFin,boolean interno, String direccion, short aforo) {
        this(name, description, fechaIni, fechaFin, interno,  direccion, aforo);
        this.eventId = eventId;
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

	public Calendar getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Calendar dateEnd) {
		this.dateEnd = dateEnd;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + capacity;
		result = prime * result + ((dateEnd == null) ? 0 : dateEnd.hashCode());
		result = prime * result + ((dateSt == null) ? 0 : dateSt.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((eventId == null) ? 0 : eventId.hashCode());
		result = prime * result + (intern ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Event other = (Event) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (capacity != other.capacity)
			return false;
		if (dateEnd == null) {
			if (other.dateEnd != null)
				return false;
		} else if (!dateEnd.equals(other.dateEnd))
			return false;
		if (dateSt == null) {
			if (other.dateSt != null)
				return false;
		} else if (!dateSt.equals(other.dateSt))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (eventId == null) {
			if (other.eventId != null)
				return false;
		} else if (!eventId.equals(other.eventId))
			return false;
		if (intern != other.intern)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Event [eventId=" + eventId + ", name=" + name
				+ ", description=" + description + ", dateSt=" + dateSt.toString()
				+ ", dateEnd=" + dateEnd.toString() + ", intern=" + intern + ", address="
				+ address + ", capacity=" + capacity + "]";
	}
    
}