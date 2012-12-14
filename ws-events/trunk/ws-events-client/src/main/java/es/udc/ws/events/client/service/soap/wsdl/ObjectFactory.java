
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
    private final static QName _SoapOverCapacityError_QNAME = new QName("http://soap.ws.udc.es/", "SoapOverCapacityError");
    private final static QName _SoapEventRegisterUsersError_QNAME = new QName("http://soap.ws.udc.es/", "SoapEventRegisterUsersError");
    private final static QName _AddEventResponse_QNAME = new QName("http://soap.ws.udc.es/", "addEventResponse");
    private final static QName _FindEventByKeywordResponse_QNAME = new QName("http://soap.ws.udc.es/", "findEventByKeywordResponse");
    private final static QName _UpdateEventResponse_QNAME = new QName("http://soap.ws.udc.es/", "updateEventResponse");
    private final static QName _FindEventResponse_QNAME = new QName("http://soap.ws.udc.es/", "findEventResponse");
    private final static QName _ResponseToEvent_QNAME = new QName("http://soap.ws.udc.es/", "responseToEvent");
    private final static QName _RemoveEvent_QNAME = new QName("http://soap.ws.udc.es/", "removeEvent");
    private final static QName _SoapInstanceNotFoundException_QNAME = new QName("http://soap.ws.udc.es/", "SoapInstanceNotFoundException");
    private final static QName _GetResponsesByIDResponse_QNAME = new QName("http://soap.ws.udc.es/", "getResponsesByIDResponse");
    private final static QName _RemoveEventResponse_QNAME = new QName("http://soap.ws.udc.es/", "removeEventResponse");
    private final static QName _FindEventByKeyword_QNAME = new QName("http://soap.ws.udc.es/", "findEventByKeyword");
    private final static QName _GetResponsesByID_QNAME = new QName("http://soap.ws.udc.es/", "getResponsesByID");
    private final static QName _GetResponsesResponse_QNAME = new QName("http://soap.ws.udc.es/", "getResponsesResponse");
    private final static QName _AddEvent_QNAME = new QName("http://soap.ws.udc.es/", "addEvent");
    private final static QName _ResponseToEventResponse_QNAME = new QName("http://soap.ws.udc.es/", "responseToEventResponse");
    private final static QName _GetResponses_QNAME = new QName("http://soap.ws.udc.es/", "getResponses");
    private final static QName _UpdateEvent_QNAME = new QName("http://soap.ws.udc.es/", "updateEvent");
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
     * Create an instance of {@link SoapInstanceNotFoundExceptionInfo }
     * 
     */
    public SoapInstanceNotFoundExceptionInfo createSoapInstanceNotFoundExceptionInfo() {
        return new SoapInstanceNotFoundExceptionInfo();
    }

    /**
     * Create an instance of {@link GetResponsesByIDResponse }
     * 
     */
    public GetResponsesByIDResponse createGetResponsesByIDResponse() {
        return new GetResponsesByIDResponse();
    }

    /**
     * Create an instance of {@link AddEventResponse }
     * 
     */
    public AddEventResponse createAddEventResponse() {
        return new AddEventResponse();
    }

    /**
     * Create an instance of {@link ResponseToEvent }
     * 
     */
    public ResponseToEvent createResponseToEvent() {
        return new ResponseToEvent();
    }

    /**
     * Create an instance of {@link UpdateEventResponse }
     * 
     */
    public UpdateEventResponse createUpdateEventResponse() {
        return new UpdateEventResponse();
    }

    /**
     * Create an instance of {@link RemoveEventResponse }
     * 
     */
    public RemoveEventResponse createRemoveEventResponse() {
        return new RemoveEventResponse();
    }

    /**
     * Create an instance of {@link ResponseDto }
     * 
     */
    public ResponseDto createResponseDto() {
        return new ResponseDto();
    }

    /**
     * Create an instance of {@link FindEventByKeyword }
     * 
     */
    public FindEventByKeyword createFindEventByKeyword() {
        return new FindEventByKeyword();
    }

    /**
     * Create an instance of {@link ResponseToEventResponse }
     * 
     */
    public ResponseToEventResponse createResponseToEventResponse() {
        return new ResponseToEventResponse();
    }

    /**
     * Create an instance of {@link GetResponsesByID }
     * 
     */
    public GetResponsesByID createGetResponsesByID() {
        return new GetResponsesByID();
    }

    /**
     * Create an instance of {@link RemoveEvent }
     * 
     */
    public RemoveEvent createRemoveEvent() {
        return new RemoveEvent();
    }

    /**
     * Create an instance of {@link FindEvent }
     * 
     */
    public FindEvent createFindEvent() {
        return new FindEvent();
    }

    /**
     * Create an instance of {@link UpdateEvent }
     * 
     */
    public UpdateEvent createUpdateEvent() {
        return new UpdateEvent();
    }

    /**
     * Create an instance of {@link FindEventResponse }
     * 
     */
    public FindEventResponse createFindEventResponse() {
        return new FindEventResponse();
    }

    /**
     * Create an instance of {@link EventDto }
     * 
     */
    public EventDto createEventDto() {
        return new EventDto();
    }

    /**
     * Create an instance of {@link GetResponses }
     * 
     */
    public GetResponses createGetResponses() {
        return new GetResponses();
    }

    /**
     * Create an instance of {@link GetResponsesResponse }
     * 
     */
    public GetResponsesResponse createGetResponsesResponse() {
        return new GetResponsesResponse();
    }

    /**
     * Create an instance of {@link FindEventByKeywordResponse }
     * 
     */
    public FindEventByKeywordResponse createFindEventByKeywordResponse() {
        return new FindEventByKeywordResponse();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapOverCapacityError")
    public JAXBElement<String> createSoapOverCapacityError(String value) {
        return new JAXBElement<String>(_SoapOverCapacityError_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapEventRegisterUsersError")
    public JAXBElement<String> createSoapEventRegisterUsersError(String value) {
        return new JAXBElement<String>(_SoapEventRegisterUsersError_QNAME, String.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link FindEventByKeywordResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "findEventByKeywordResponse")
    public JAXBElement<FindEventByKeywordResponse> createFindEventByKeywordResponse(FindEventByKeywordResponse value) {
        return new JAXBElement<FindEventByKeywordResponse>(_FindEventByKeywordResponse_QNAME, FindEventByKeywordResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateEventResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "updateEventResponse")
    public JAXBElement<UpdateEventResponse> createUpdateEventResponse(UpdateEventResponse value) {
        return new JAXBElement<UpdateEventResponse>(_UpdateEventResponse_QNAME, UpdateEventResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseToEvent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "responseToEvent")
    public JAXBElement<ResponseToEvent> createResponseToEvent(ResponseToEvent value) {
        return new JAXBElement<ResponseToEvent>(_ResponseToEvent_QNAME, ResponseToEvent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveEvent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "removeEvent")
    public JAXBElement<RemoveEvent> createRemoveEvent(RemoveEvent value) {
        return new JAXBElement<RemoveEvent>(_RemoveEvent_QNAME, RemoveEvent.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResponsesByIDResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "getResponsesByIDResponse")
    public JAXBElement<GetResponsesByIDResponse> createGetResponsesByIDResponse(GetResponsesByIDResponse value) {
        return new JAXBElement<GetResponsesByIDResponse>(_GetResponsesByIDResponse_QNAME, GetResponsesByIDResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveEventResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "removeEventResponse")
    public JAXBElement<RemoveEventResponse> createRemoveEventResponse(RemoveEventResponse value) {
        return new JAXBElement<RemoveEventResponse>(_RemoveEventResponse_QNAME, RemoveEventResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindEventByKeyword }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "findEventByKeyword")
    public JAXBElement<FindEventByKeyword> createFindEventByKeyword(FindEventByKeyword value) {
        return new JAXBElement<FindEventByKeyword>(_FindEventByKeyword_QNAME, FindEventByKeyword.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResponsesByID }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "getResponsesByID")
    public JAXBElement<GetResponsesByID> createGetResponsesByID(GetResponsesByID value) {
        return new JAXBElement<GetResponsesByID>(_GetResponsesByID_QNAME, GetResponsesByID.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResponsesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "getResponsesResponse")
    public JAXBElement<GetResponsesResponse> createGetResponsesResponse(GetResponsesResponse value) {
        return new JAXBElement<GetResponsesResponse>(_GetResponsesResponse_QNAME, GetResponsesResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseToEventResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "responseToEventResponse")
    public JAXBElement<ResponseToEventResponse> createResponseToEventResponse(ResponseToEventResponse value) {
        return new JAXBElement<ResponseToEventResponse>(_ResponseToEventResponse_QNAME, ResponseToEventResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResponses }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "getResponses")
    public JAXBElement<GetResponses> createGetResponses(GetResponses value) {
        return new JAXBElement<GetResponses>(_GetResponses_QNAME, GetResponses.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateEvent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "updateEvent")
    public JAXBElement<UpdateEvent> createUpdateEvent(UpdateEvent value) {
        return new JAXBElement<UpdateEvent>(_UpdateEvent_QNAME, UpdateEvent.class, null, value);
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
