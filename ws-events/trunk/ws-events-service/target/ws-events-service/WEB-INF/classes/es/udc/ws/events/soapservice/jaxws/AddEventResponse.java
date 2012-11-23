
package es.udc.ws.events.soapservice.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "addEventResponse", namespace = "http://soap.ws.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addEventResponse", namespace = "http://soap.ws.udc.es/")
public class AddEventResponse {

    @XmlElement(name = "return", namespace = "")
    private Long _return;

    /**
     * 
     * @return
     *     returns Long
     */
    public Long getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(Long _return) {
        this._return = _return;
    }

}
