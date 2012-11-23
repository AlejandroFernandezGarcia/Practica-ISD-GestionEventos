
package es.udc.ws.events.soapservice.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "findEvent", namespace = "http://soap.ws.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findEvent", namespace = "http://soap.ws.udc.es/")
public class FindEvent {

    @XmlElement(name = "eventId", namespace = "")
    private Long eventId;

    /**
     * 
     * @return
     *     returns Long
     */
    public Long getEventId() {
        return this.eventId;
    }

    /**
     * 
     * @param eventId
     *     the value for the eventId property
     */
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

}
