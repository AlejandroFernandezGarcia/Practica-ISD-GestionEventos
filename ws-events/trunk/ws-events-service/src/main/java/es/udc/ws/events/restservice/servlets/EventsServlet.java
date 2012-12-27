package es.udc.ws.events.restservice.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.jdom.Document;
import org.jdom.Element;

import es.udc.ws.events.dto.EventDto;
import es.udc.ws.events.exceptions.EventRegisteredUsersException;
import es.udc.ws.events.model.event.Event;
import es.udc.ws.events.model.eventservice.EventServiceFactory;
import es.udc.ws.events.util.EventToEventDtoConversor;
import es.udc.ws.events.xml.XmlExceptionConversor;
import es.udc.ws.events.xml.XmlEventDtoConversor;
import es.udc.ws.events.xml.ParsingException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.ServletUtils;

@SuppressWarnings("serial")
public class EventsServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * Long eventId = new Long(0L);
		 * 
		 * String eventURL = req.getRequestURL().append("/").append(eventId)
		 * .toString(); Map<String, String> headers = new HashMap<>(1);
		 * headers.put("Location", eventURL);
		 * 
		 * ServletUtils.writeServiceResponse(resp, HttpStatus.SC_CREATED, null,
		 * headers);
		 * 
		 * XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		 * outputter.output(getEventDocument(), resp.getOutputStream());
		 */
		EventDto xmlEvent;
		try {
			xmlEvent = XmlEventDtoConversor.toEvent(req.getInputStream());
		} catch (ParsingException ex) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpStatus.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											ex.getMessage())), null);

			return;

		}
		Event evento = EventToEventDtoConversor.toEvent(xmlEvent);
		try {
			evento = EventServiceFactory.getService().addEvent(evento);
		} catch (InputValidationException ex) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpStatus.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											ex.getMessage())), null);
			return;
		}
		EventDto eventDto = EventToEventDtoConversor.toEventDto(evento);

		String eventURL = req.getRequestURL().append("/")
				.append(evento.getEventId()).toString();
		Map<String, String> headers = new HashMap<>(1);
		headers.put("Location", eventURL);

		ServletUtils.writeServiceResponse(resp, HttpStatus.SC_CREATED,
				XmlEventDtoConversor.toXml(eventDto), headers);
	}

	protected void doPut(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String requestURI = req.getRequestURI();
        int index = requestURI.lastIndexOf('/');
        if (index <= 0) {
            ServletUtils.writeServiceResponse(resp, HttpStatus.SC_BAD_REQUEST,
                    XmlExceptionConversor.toInputValidationExceptionXml(
                    new InputValidationException("Invalid Request: " + 
                        "unable to find event id")), null);
            return;
        }
        Long eventId;
        String eventIdAsString = requestURI.substring(index + 1);
        try {
            eventId = Long.valueOf(eventIdAsString);
        } catch (NumberFormatException ex) {
            ServletUtils.writeServiceResponse(resp, HttpStatus.SC_BAD_REQUEST,
                    XmlExceptionConversor.toInputValidationExceptionXml(
                    new InputValidationException("Invalid Request: " + 
                        "unable to parse event id '" + 
                    eventIdAsString + "'")), null);
            return;
        }        
        
        EventDto eventDto;
        try {
            eventDto = XmlEventDtoConversor
                    .toEvent(req.getInputStream());
        } catch (ParsingException ex) {
            ServletUtils.writeServiceResponse(resp, HttpStatus.SC_BAD_REQUEST, 
                    XmlExceptionConversor.toInputValidationExceptionXml(
                    new InputValidationException(ex.getMessage())), 
                    null);
            return;
            
        }
        Event event = EventToEventDtoConversor.toEvent(eventDto);
        event.setEventId(eventId);
        try {
            EventServiceFactory.getService().updateEvent(event);
        } catch (InputValidationException ex) {
            ServletUtils.writeServiceResponse(resp, HttpStatus.SC_BAD_REQUEST, 
                    XmlExceptionConversor.toInputValidationExceptionXml(
                    new InputValidationException(ex.getMessage())), 
                    null);
            return;
        } catch (InstanceNotFoundException ex) {
            ServletUtils.writeServiceResponse(resp, HttpStatus.SC_NOT_FOUND, 
                    XmlExceptionConversor.toInstanceNotFoundException(
                new InstanceNotFoundException(
                    ex.getInstanceId().toString(), ex.getInstanceType())),
                    null);       
            return;
        } catch (EventRegisteredUsersException e) {
        	ServletUtils.writeServiceResponse(resp, HttpStatus.SC_NOT_FOUND, 
                    XmlExceptionConversor.toEventRegisterUsersError(
                    new EventRegisteredUsersException("Error: Register user not found")), 
                    null);
            return;
		}
        ServletUtils.writeServiceResponse(resp, HttpStatus.SC_NO_CONTENT, 
                null, null);        
    }
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * ServletUtils.writeServiceResponse(resp, HttpStatus.SC_OK,
		 * "application/xml", null, null); XMLOutputter outputter = new
		 * XMLOutputter(Format.getPrettyFormat());
		 * outputter.output(getEventDocument(), resp.getOutputStream());
		 */
		String path = req.getPathInfo();
		if (path == null || path.length() == 0 || "/".equals(path)) {
			String clave = req.getParameter("clave");
			String strDate = req.getParameter("inicio");
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date date;
			Calendar inicio = Calendar.getInstance();
			Calendar fin = Calendar.getInstance();
			try {
				date = sdf.parse(strDate);
				inicio.setTime(date);
				strDate = req.getParameter("fin");
				date = sdf.parse(strDate);
				fin.setTime(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			List<Event> listaEventos = EventServiceFactory.getService()
					.findEventByKeyword(clave, inicio, fin);
			List<EventDto> listaEventosDtos = EventToEventDtoConversor
					.toEventDtos(listaEventos);
			ServletUtils.writeServiceResponse(resp, HttpStatus.SC_OK,
					XmlEventDtoConversor.toXml(listaEventosDtos), null);
		} else {
			String eventIdAsString = path.endsWith("/") && path.length() > 2 ? path
					.substring(1, path.length() - 1) : path.substring(1);
			Long eventId;
			try {
				eventId = Long.valueOf(eventIdAsString);
			} catch (NumberFormatException ex) {
				ServletUtils
						.writeServiceResponse(
								resp,
								HttpStatus.SC_BAD_REQUEST,
								XmlExceptionConversor
										.toInputValidationExceptionXml(new InputValidationException(
												"Invalid Request: "
														+ "parameter 'eventId' is invalid '"
														+ eventIdAsString + "'")),
								null);

				return;
			}
			Event evento;
			try {
				evento = EventServiceFactory.getService().findEvent(eventId);
			} catch (InstanceNotFoundException ex) {
				ServletUtils
						.writeServiceResponse(
								resp,
								HttpStatus.SC_NOT_FOUND,
								XmlExceptionConversor
										.toInstanceNotFoundException(new InstanceNotFoundException(
												ex.getInstanceId().toString(),
												ex.getInstanceType())), null);
				return;
			}
			EventDto eventDto = EventToEventDtoConversor.toEventDto(evento);
			ServletUtils.writeServiceResponse(resp, HttpStatus.SC_OK,
					XmlEventDtoConversor.toXml(eventDto), null);
		}
	}

	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String requestURI = req.getRequestURI();
		int idx = requestURI.lastIndexOf('/');
		if (idx <= 0) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpStatus.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "unable to find event id")),
							null);
			return;
		}
		Long eventId;
		String eventIdAsString = requestURI.substring(idx + 1);
		try {
			eventId = Long.valueOf(eventIdAsString);
		} catch (NumberFormatException ex) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpStatus.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "unable to parse event id '"
													+ eventIdAsString + "'")),
							null);

			return;
		}
		try {
			EventServiceFactory.getService().deleteEvent(eventId);
		} catch (InstanceNotFoundException ex) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpStatus.SC_NOT_FOUND,
							XmlExceptionConversor
									.toInstanceNotFoundException(new InstanceNotFoundException(
											ex.getInstanceId().toString(), ex
													.getInstanceType())), null);
			return;
		} catch (EventRegisteredUsersException e) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpStatus.SC_NOT_FOUND,
							XmlExceptionConversor
									.toEventRegisterUsersError(new EventRegisteredUsersException(
											"Error: Register user not found")),
							null);
			return;
		}
		ServletUtils.writeServiceResponse(resp, HttpStatus.SC_NO_CONTENT, null,
				null);
	}
	
	private Document getEventDocument() {

		Element eventElement = new Element("event");

		Element identifierElement = new Element("eventId");
		identifierElement.setText(new Long(0L).toString());
		eventElement.addContent(identifierElement);

		Element nameElement = new Element("name");
		nameElement.setText("event name");
		eventElement.addContent(nameElement);

		Document document = new Document(eventElement);
		return document;

	}
}
