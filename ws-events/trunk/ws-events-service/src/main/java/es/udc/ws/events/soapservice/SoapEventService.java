package es.udc.ws.events.soapservice;

import java.util.Calendar;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import es.udc.ws.events.dto.EventDto;
import es.udc.ws.events.exceptions.EventRegisterUsersError;
import es.udc.ws.events.exceptions.OverCapacityError;
import es.udc.ws.events.model.event.Event;
import es.udc.ws.events.model.eventservice.EventServiceFactory;
import es.udc.ws.events.model.response.Response;
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
    public EventDto findEvent(@WebParam(name="eventId") Long eventId) throws SoapInstanceNotFoundException{
        try{
        	Event event = EventServiceFactory.getService().findEvent(eventId);
        	return EventToEventDtoConversor.toEventDto(event);
        }catch(InstanceNotFoundException e){
        	throw new SoapInstanceNotFoundException(new SoapInstanceNotFoundExceptionInfo(e.getInstanceId(), e.getInstanceType()));
        }
    }
    
    @WebMethod(
            operationName="findEventByKeyword"
        )
    public List<EventDto> findEventByKeyword(@WebParam(name="clave")String clave,@WebParam(name="dateSt") Calendar dateSt,@WebParam(name="duracion") Integer duration)throws SoapInstanceNotFoundException{
    	try{
            Long dateEndMilis = dateSt.getTimeInMillis() + (duration*60000);
            Calendar dateEnd = Calendar.getInstance();
            dateEnd.setTimeInMillis(dateEndMilis);
            List<Event> eventList= EventServiceFactory.getService().findEventByKeyword(clave, dateSt, dateEnd);
    		return EventToEventDtoConversor.toEventDtos(eventList);
    	}catch(InstanceNotFoundException e){
    		throw new SoapInstanceNotFoundException(new SoapInstanceNotFoundExceptionInfo(e.getInstanceId(), e.getInstanceType()));
    	}
    }
    
    @WebMethod(
            operationName="responseToEvent"
        )
    public Long responseToEvent(@WebParam(name="username")String username,@WebParam(name="eventId") Long eventId,@WebParam(name="code") Boolean code) throws SoapInstanceNotFoundException, SoapEventRegisterUsersError, SoapOverCapacityError{
    	try{
    		return EventServiceFactory.getService().responseToEvent(username, eventId, code);
    	}catch(InstanceNotFoundException e){
    		throw new SoapInstanceNotFoundException(new SoapInstanceNotFoundExceptionInfo(e.getInstanceId(), e.getInstanceType()));
    	} catch (OverCapacityError e) {
			throw new SoapOverCapacityError(e.getMessage());
		} catch (EventRegisterUsersError e) {
			throw new SoapEventRegisterUsersError(e.getMessage());
		}
    }
    
    @WebMethod(
            operationName="getResponses"
        )
    public List<Response> getResponses(Long eventId, Boolean code){
    	return null;
    }
    
    @WebMethod(
            operationName="getResponsesByID"
        )
    public Response getResponsesByID(Long responseId){
    	return null;
    }

}
