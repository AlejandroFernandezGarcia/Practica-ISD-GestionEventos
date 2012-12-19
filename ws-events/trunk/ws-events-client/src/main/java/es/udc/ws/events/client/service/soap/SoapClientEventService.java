package es.udc.ws.events.client.service.soap;

import java.util.Calendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;

import es.udc.ws.events.client.service.ClientEventService;
import es.udc.ws.events.client.service.soap.wsdl.EventsProvider;
import es.udc.ws.events.client.service.soap.wsdl.EventsProviderService;
import es.udc.ws.events.client.service.soap.wsdl.SoapEventRegisterUsersError;
import es.udc.ws.events.client.service.soap.wsdl.SoapInputValidationException;
import es.udc.ws.events.client.service.soap.wsdl.SoapInstanceNotFoundException;
import es.udc.ws.events.client.service.soap.wsdl.SoapOverCapacityError;
import es.udc.ws.events.dto.EventDto;
import es.udc.ws.events.dto.ResponseDto;
import es.udc.ws.events.exceptions.EventRegisterUsersException;
import es.udc.ws.events.exceptions.OverCapacityException;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class SoapClientEventService implements ClientEventService{
	
	private final static String ENDPOINT_ADDRESS_PARAMETER =
			"SoapClientEventService.endpointAddress";
	
	private String endpointAddress;
	
	private EventsProvider eventsProvider;
	
	public SoapClientEventService(){
		init(getEndpointAddress());
	}
	
	private void init(String eventsProviderURL){
		EventsProviderService stockQuoteProviderService = new EventsProviderService();
		eventsProvider = stockQuoteProviderService.getEventsProviderPort();
		((BindingProvider) eventsProvider).getRequestContext().put(
				BindingProvider.ENDPOINT_ADDRESS_PROPERTY,eventsProviderURL);
	}
	
	private String getEndpointAddress(){
		if(endpointAddress == null){
			endpointAddress = ConfigurationParametersManager.getParameter(ENDPOINT_ADDRESS_PARAMETER);
		}
		return endpointAddress;
	}
	
	@Override
	public Long addEvent(EventDto event) throws InputValidationException {
		try{
			return eventsProvider.addEvent(EventDtoToSoapEventDtoConversor.toSoapEventDto(event));
		}catch(SoapInputValidationException e){
			throw new InputValidationException(e.getMessage());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updateEvent(EventDto event) throws InputValidationException,
			InstanceNotFoundException, EventRegisterUsersException {
		try {
			eventsProvider.updateEvent(EventDtoToSoapEventDtoConversor.toSoapEventDto(
					event));
		}catch (SoapEventRegisterUsersError e) {
			throw new EventRegisterUsersException(e.getMessage());
		}catch (SoapInputValidationException e) {
			throw new InputValidationException(e.getMessage());
		}catch (SoapInstanceNotFoundException e){
			throw new InstanceNotFoundException(e.getFaultInfo().getInstanceId(),
												e.getFaultInfo().getInstanceType());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
			
	}

	@Override
	public void deleteEvent(Long eventId) throws InstanceNotFoundException,
			EventRegisterUsersException {
		try {
			eventsProvider.removeEvent(eventId);
		} catch (SoapEventRegisterUsersError e) {
			throw new EventRegisterUsersException(e.getMessage());
		} catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(e.getFaultInfo().getInstanceId(),
												 e.getFaultInfo().getInstanceType());
		}
		
	}

	@Override
	public EventDto findEvent(Long eventId) throws InstanceNotFoundException {
		try {
			return EventDtoToSoapEventDtoConversor.toEventDto(
							eventsProvider.findEvent(eventId));
		} catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(e.getFaultInfo().getInstanceId(),
					 							 e.getFaultInfo().getInstanceType());
		}
	}

	@Override
	public List<EventDto> findEventByKeyword(String clave, Calendar fechaIni,
			Calendar fechaFin) {
		if((fechaIni ==null) |(fechaFin== null)){
			XMLGregorianCalendar xgc = null;
			return EventDtoToSoapEventDtoConversor.toEventDtos(
					eventsProvider.findEventByKeyword(clave, xgc,(int) 0));
		}
		Long dist = fechaFin.getTimeInMillis() - fechaIni.getTimeInMillis();
        int duration =(int) (dist / 60000);
        try{
			DatatypeFactory dtf = DatatypeFactory.newInstance();
			XMLGregorianCalendar xgc = dtf.newXMLGregorianCalendar();
			xgc.setYear(fechaIni.get(Calendar.YEAR));
			xgc.setDay(fechaIni.get(Calendar.DAY_OF_MONTH));
			xgc.setMonth(fechaIni.get(Calendar.MONTH));
			xgc.setHour(fechaIni.get(Calendar.HOUR_OF_DAY));
			xgc.setMinute(fechaIni.get(Calendar.MINUTE));
			xgc.setSecond(fechaIni.get(Calendar.SECOND));
			xgc.setMillisecond(fechaIni.get(Calendar.MILLISECOND));
			
			int offsetInMinutes = (fechaIni.get(Calendar.ZONE_OFFSET) + fechaIni.get(Calendar.DST_OFFSET)) / (60 * 1000);
			xgc.setTimezone(offsetInMinutes);
			return EventDtoToSoapEventDtoConversor.toEventDtos(
					eventsProvider.findEventByKeyword(clave, xgc, duration));
		}catch(DatatypeConfigurationException e){
			return null;
		}
		
	}

	@Override
	public Long responseToEvent(String username, Long eventId, Boolean code)
			throws InstanceNotFoundException, OverCapacityException,
			EventRegisterUsersException {
		try {
			return eventsProvider.responseToEvent(username, eventId, code);
		} catch (SoapEventRegisterUsersError e) {
			throw new EventRegisterUsersException(e.getMessage());
		} catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(e.getFaultInfo().getInstanceId(),
					 							 e.getFaultInfo().getInstanceType());
		} catch (SoapOverCapacityError e) {
			throw new OverCapacityException(e.getMessage());
		}
	}

	@Override
	public List<ResponseDto> getResponses(Long eventId, Boolean code)
			throws InstanceNotFoundException {
		try {
			return ResponseDtoToSoapResponseDtoConversor.toResponseDtos(
					eventsProvider.getResponses(eventId, code));
		} catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(e.getFaultInfo().getInstanceId(),
					 							 e.getFaultInfo().getInstanceType());
		}
	}

	@Override
	public ResponseDto getResponsesByID(Long responseId) throws InstanceNotFoundException{
		try {
			return ResponseDtoToSoapResponseDtoConversor.toResponseDto(
							 eventsProvider.getResponsesByID(responseId));
		} catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(e.getFaultInfo().getInstanceId(),
					 							e.getFaultInfo().getInstanceType());
		}
	}

}
