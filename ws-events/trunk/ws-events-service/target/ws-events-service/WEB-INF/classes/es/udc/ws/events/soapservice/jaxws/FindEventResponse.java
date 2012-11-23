
package es.udc.ws.events.soapservice.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import es.udc.ws.events.dto.EventDto;

@XmlRootElement(name = "findEventResponse", namespace = "http://soap.ws.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findEventResponse", namespace = "http://soap.ws.udc.es/")
public class FindEventResponse {

    @XmlElement(name = "return", namespace = "")
    private EventDto _return;

    /**
     * 
     * @return
     *     returns EventDto
     */
    public EventDto getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(EventDto _return) {
        this._return = _return;
    }

}
