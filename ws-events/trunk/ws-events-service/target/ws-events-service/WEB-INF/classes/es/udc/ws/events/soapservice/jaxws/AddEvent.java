
package es.udc.ws.events.soapservice.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import es.udc.ws.events.dto.EventDto;

@XmlRootElement(name = "addEvent", namespace = "http://soap.ws.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addEvent", namespace = "http://soap.ws.udc.es/")
public class AddEvent {

    @XmlElement(name = "eventDto", namespace = "")
    private EventDto eventDto;

    /**
     * 
     * @return
     *     returns EventDto
     */
    public EventDto getEventDto() {
        return this.eventDto;
    }

    /**
     * 
     * @param eventDto
     *     the value for the eventDto property
     */
    public void setEventDto(EventDto eventDto) {
        this.eventDto = eventDto;
    }

}
