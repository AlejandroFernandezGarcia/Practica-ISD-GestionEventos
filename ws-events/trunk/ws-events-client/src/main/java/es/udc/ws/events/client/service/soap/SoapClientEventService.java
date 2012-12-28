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
import es.udc.ws.events.client.service.soap.wsdl.SoapEventRegisteredUsersException;
import es.udc.ws.events.client.service.soap.wsdl.SoapInputValidationException;
import es.udc.ws.events.client.service.soap.wsdl.SoapInstanceNotFoundException;
import es.udc.ws.events.client.service.soap.wsdl.SoapOverCapacityException;
import es.udc.ws.events.dto.EventDto;
import es.udc.ws.events.dto.ResponseDto;
import es.udc.ws.events.exceptions.EventRegisteredUsersException;
import es.udc.ws.events.exceptions.OverCapacityException;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class SoapClientEventService implements ClientEventService {

	private final static String ENDPOINT_ADDRESS_PARAMETER = "SoapClientEventService.endpointAddress";

	private String endpointAddress;

	private EventsProvider eventsProvider;

	public SoapClientEventService() {
		init(getEndpointAddress());
	}

	private void init(String eventsProviderURL) {
		EventsProviderService stockQuoteProviderService = new EventsProviderService();
		eventsProvider = stockQuoteProviderService.getEventsProviderPort();
		((BindingProvider) eventsProvider).getRequestContext().put(
				BindingProvider.ENDPOINT_ADDRESS_PROPERTY, eventsProviderURL);
	}

	private String getEndpointAddress() {
		if (endpointAddress == null) {
			endpointAddress = ConfigurationParametersManager
					.getParameter(ENDPOINT_ADDRESS_PARAMETER);
		}
		return endpointAddress;
	}

	@Override
	public Long addEvent(EventDto event) throws InputValidationException {
		try {
			return eventsProvider.addEvent(EventDtoToSoapEventDtoConversor
					.toSoapEventDto(event));
		} catch (SoapInputValidationException e) {
			throw new InputValidationException(e.getMessage());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updateEvent(EventDto event) throws InputValidationException,
			InstanceNotFoundException, EventRegisteredUsersException {
		try {
			eventsProvider.updateEvent(EventDtoToSoapEventDtoConversor
					.toSoapEventDto(event));
		} catch (SoapEventRegisteredUsersException e) {
			throw new EventRegisteredUsersException(e.getMessage());
		} catch (SoapInputValidationException e) {
			throw new InputValidationException(e.getMessage());
		} catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(e.getFaultInfo()
					.getInstanceId(), e.getFaultInfo().getInstanceType());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void deleteEvent(Long eventId) throws InstanceNotFoundException,
			EventRegisteredUsersException {
		try {
			eventsProvider.removeEvent(eventId);
		} catch (SoapEventRegisteredUsersException e) {
			throw new EventRegisteredUsersException(e.getMessage());
		} catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(e.getFaultInfo()
					.getInstanceId(), e.getFaultInfo().getInstanceType());
		}

	}

	@Override
	public EventDto findEvent(Long eventId) throws InstanceNotFoundException {
		try {
			return EventDtoToSoapEventDtoConversor.toEventDto(eventsProvider
					.findEvent(eventId));
		} catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(e.getFaultInfo()
					.getInstanceId(), e.getFaultInfo().getInstanceType());
		}
	}

	@Override
	public List<EventDto> findEventByKeyword(String clave, Calendar fechaIni,
			Calendar fechaFin) {
		XMLGregorianCalendar xgc = null;
		XMLGregorianCalendar xgc1 = null;
		if ((fechaIni == null) | (fechaFin == null)) {
			return EventDtoToSoapEventDtoConversor.toEventDtos(eventsProvider
					.findEventByKeyword(clave, xgc, xgc1));
		}
		xgc = EventDtoToSoapEventDtoConversor
				.toXMLGregorianCalendarFromCalendar(fechaIni);
		xgc1 = EventDtoToSoapEventDtoConversor
				.toXMLGregorianCalendarFromCalendar(fechaFin);
		return EventDtoToSoapEventDtoConversor.toEventDtos(eventsProvider
				.findEventByKeyword(clave, xgc, xgc1));

	}

	@Override
	public Long responseToEvent(String username, Long eventId, Boolean code)
			throws InstanceNotFoundException, OverCapacityException,
			EventRegisteredUsersException {
		try {
			return eventsProvider.responseToEvent(username, eventId, code);
		} catch (SoapEventRegisteredUsersException e) {
			throw new EventRegisteredUsersException(e.getMessage());
		} catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(e.getFaultInfo()
					.getInstanceId(), e.getFaultInfo().getInstanceType());
		} catch (SoapOverCapacityException e) {
			throw new OverCapacityException(e.getMessage());
		}
	}

	@Override
	public List<ResponseDto> getResponses(Long eventId, Boolean code)
			throws InstanceNotFoundException {
		try {
			return ResponseDtoToSoapResponseDtoConversor
					.toResponseDtos(eventsProvider.getResponses(eventId, code));
		} catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(e.getFaultInfo()
					.getInstanceId(), e.getFaultInfo().getInstanceType());
		}
	}

	@Override
	public ResponseDto getResponsesByID(Long responseId)
			throws InstanceNotFoundException {
		try {
			return ResponseDtoToSoapResponseDtoConversor
					.toResponseDto(eventsProvider.getResponsesByID(responseId));
		} catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(e.getFaultInfo()
					.getInstanceId(), e.getFaultInfo().getInstanceType());
		}
	}

}
