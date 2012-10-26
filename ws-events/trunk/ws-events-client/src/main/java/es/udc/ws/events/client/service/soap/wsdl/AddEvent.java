
package es.udc.ws.events.client.service.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for addEvent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addEvent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="eventDto" type="{http://soap.ws.udc.es/}eventDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addEvent", propOrder = {
    "eventDto"
})
public class AddEvent {

    protected EventDto eventDto;

    /**
     * Gets the value of the eventDto property.
     * 
     * @return
     *     possible object is
     *     {@link EventDto }
     *     
     */
    public EventDto getEventDto() {
        return eventDto;
    }

    /**
     * Sets the value of the eventDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventDto }
     *     
     */
    public void setEventDto(EventDto value) {
        this.eventDto = value;
    }

}
