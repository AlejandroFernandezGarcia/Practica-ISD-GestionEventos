
package es.udc.ws.events.client.service.soap.wsdl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the es.udc.ws.events.client.service.soap.wsdl package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _FindEvent_QNAME = new QName("http://soap.ws.udc.es/", "findEvent");
    private final static QName _AddEvent_QNAME = new QName("http://soap.ws.udc.es/", "addEvent");
    private final static QName _AddEventResponse_QNAME = new QName("http://soap.ws.udc.es/", "addEventResponse");
    private final static QName _FindEventResponse_QNAME = new QName("http://soap.ws.udc.es/", "findEventResponse");
    private final static QName _SoapInstanceNotFoundException_QNAME = new QName("http://soap.ws.udc.es/", "SoapInstanceNotFoundException");
    private final static QName _SoapInputValidationException_QNAME = new QName("http://soap.ws.udc.es/", "SoapInputValidationException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.udc.ws.events.client.service.soap.wsdl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddEvent }
     * 
     */
    public AddEvent createAddEvent() {
        return new AddEvent();
    }

    /**
     * Create an instance of {@link AddEventResponse }
     * 
     */
    public AddEventResponse createAddEventResponse() {
        return new AddEventResponse();
    }

    /**
     * Create an instance of {@link SoapInstanceNotFoundExceptionInfo }
     * 
     */
    public SoapInstanceNotFoundExceptionInfo createSoapInstanceNotFoundExceptionInfo() {
        return new SoapInstanceNotFoundExceptionInfo();
    }

    /**
     * Create an instance of {@link FindEvent }
     * 
     */
    public FindEvent createFindEvent() {
        return new FindEvent();
    }

    /**
     * Create an instance of {@link EventDto }
     * 
     */
    public EventDto createEventDto() {
        return new EventDto();
    }

    /**
     * Create an instance of {@link FindEventResponse }
     * 
     */
    public FindEventResponse createFindEventResponse() {
        return new FindEventResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindEvent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "findEvent")
    public JAXBElement<FindEvent> createFindEvent(FindEvent value) {
        return new JAXBElement<FindEvent>(_FindEvent_QNAME, FindEvent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddEvent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "addEvent")
    public JAXBElement<AddEvent> createAddEvent(AddEvent value) {
        return new JAXBElement<AddEvent>(_AddEvent_QNAME, AddEvent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddEventResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "addEventResponse")
    public JAXBElement<AddEventResponse> createAddEventResponse(AddEventResponse value) {
        return new JAXBElement<AddEventResponse>(_AddEventResponse_QNAME, AddEventResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindEventResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "findEventResponse")
    public JAXBElement<FindEventResponse> createFindEventResponse(FindEventResponse value) {
        return new JAXBElement<FindEventResponse>(_FindEventResponse_QNAME, FindEventResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoapInstanceNotFoundExceptionInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapInstanceNotFoundException")
    public JAXBElement<SoapInstanceNotFoundExceptionInfo> createSoapInstanceNotFoundException(SoapInstanceNotFoundExceptionInfo value) {
        return new JAXBElement<SoapInstanceNotFoundExceptionInfo>(_SoapInstanceNotFoundException_QNAME, SoapInstanceNotFoundExceptionInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapInputValidationException")
    public JAXBElement<String> createSoapInputValidationException(String value) {
        return new JAXBElement<String>(_SoapInputValidationException_QNAME, String.class, null, value);
    }

}
