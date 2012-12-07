package es.udc.ws.events.soapservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import es.udc.ws.events.dto.EventDto;
import es.udc.ws.events.exceptions.EventRegisterUsersError;
import es.udc.ws.events.model.event.Event;
import es.udc.ws.events.model.eventservice.EventServiceFactory;
import es.udc.ws.events.util.EventToEventDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

@WebService(
    name="EventsProvider",
    serviceName="EventsProviderService",
    targetNamespace="http://soap.ws.udc.es/"
)
public class SoapEventService {

	@WebMethod(
        operationName="addEvent"
    )
    public Long addEvent(@WebParam(name="eventDto") EventDto eventDto) throws SoapInputValidationException {
    	Event event = EventToEventDtoConversor.toEvent(eventDto);
    	try{
    		return EventServiceFactory.getService().addEvent(event).getEventId();
    	}catch(InputValidationException e){
    		throw new SoapInputValidationException(e.getMessage());
    	}
    }
    
    
	@WebMethod(
            operationName="updateEvent"
        )
    public void updateEvent(@WebParam(name="eventDto") EventDto eventDto) throws SoapInputValidationException, SoapInstanceNotFoundException, SoapEventRegisterUsersError {
    	Event event = EventToEventDtoConversor.toEvent(eventDto);
    	try{
    		EventServiceFactory.getService().updateEvent(event);
    	}catch(InputValidationException e){
    		throw new SoapInputValidationException(e.getMessage());
    	}catch(InstanceNotFoundException e){
			throw new SoapInstanceNotFoundException(new SoapInstanceNotFoundExceptionInfo(e.getInstanceId(), e.getInstanceType()));
		}catch(EventRegisterUsersError e){
			throw new SoapEventRegisterUsersError(e.getMessage());
		}
    }
    
    
	@WebMethod(
            operationName="removeEvent"
        )
    public void removeEvent(@WebParam(name="eventId") Long eventId) throws SoapInstanceNotFoundException, SoapEventRegisterUsersError{
    	try{
    		EventServiceFactory.getService().deleteEvent(eventId);
    	}catch(InstanceNotFoundException e){
			throw new SoapInstanceNotFoundException(new SoapInstanceNotFoundExceptionInfo(e.getInstanceId(), e.getInstanceType()));
		} catch (EventRegisterUsersError e) {
			throw new SoapEventRegisterUsersError(e.getMessage());
		}
    }
	
	
    
    @WebMethod(
        operationName="findEvent"
    )        
    public EventDto findEvent(@WebParam(name="eventId") Long eventId) 
            throws SoapInstanceNotFoundException{
        return null;
    }

}
