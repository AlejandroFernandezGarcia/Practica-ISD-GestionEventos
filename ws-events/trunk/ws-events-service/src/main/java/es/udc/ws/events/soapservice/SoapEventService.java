package es.udc.ws.events.soapservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import es.udc.ws.events.dto.EventDto;

@WebService(
    name="EventsProvider",
    serviceName="EventsProviderService",
    targetNamespace="http://soap.ws.udc.es/"
)
public class SoapEventService {

    @WebMethod(
        operationName="addEvent"
    )
    public Long addEvent(@WebParam(name="eventDto") EventDto eventDto) 
            throws SoapInputValidationException {
        return 0L;
    }

    @WebMethod(
        operationName="findEvent"
    )        
    public EventDto findEvent(@WebParam(name="eventId") Long eventId) 
            throws SoapInstanceNotFoundException{
        return new EventDto(eventId,"event name");
    }

}
