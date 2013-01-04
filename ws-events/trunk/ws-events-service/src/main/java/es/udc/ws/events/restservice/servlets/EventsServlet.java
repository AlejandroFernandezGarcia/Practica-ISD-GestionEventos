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

import es.udc.ws.events.dto.EventDto;
import es.udc.ws.events.exceptions.EventRegisteredUsersException;
import es.udc.ws.events.model.event.Event;
import es.udc.ws.events.model.eventservice.EventServiceFactory;
import es.udc.ws.events.util.EventToEventDtoConversor;
import es.udc.ws.events.xml.ParsingException;
import es.udc.ws.events.xml.XmlEventDtoConversor;
import es.udc.ws.events.xml.XmlExceptionConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.ServletUtils;

@SuppressWarnings("serial")
public class EventsServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("DoPost de Event");
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
		System.out.println("Actualizar");
		String requestURI = req.getRequestURI();
		int index = requestURI.lastIndexOf('/');
		if (index <= 0) {
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
		String eventIdAsString = requestURI.substring(index + 1);
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

		EventDto eventDto;
		try {
			eventDto = XmlEventDtoConversor.toEvent(req.getInputStream());
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
		Event event = EventToEventDtoConversor.toEvent(eventDto);
		event.setEventId(eventId);
		try {
			EventServiceFactory.getService().updateEvent(event);
		} catch (InputValidationException ex) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpStatus.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											ex.getMessage())), null);
			return;
		} catch (InstanceNotFoundException ex) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpStatus.SC_NOT_FOUND,
							XmlExceptionConversor
									.toInstanceNotFoundExceptionXml(new InstanceNotFoundException(
											ex.getInstanceId().toString(), ex
													.getInstanceType())), null);
			return;
		} catch (EventRegisteredUsersException e) {
			System.out.println("Actualizar excepcion");
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpStatus.SC_CONFLICT,
							XmlExceptionConversor
									.toEventRegisteredUsersExceptionXml(new EventRegisteredUsersException(
											e.getMessage())),
							null);
			return;
		}
		ServletUtils.writeServiceResponse(resp, HttpStatus.SC_NO_CONTENT, null,
				null);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String path = req.getPathInfo();
		System.out.println("Path: " + path);
		if (path == null || path.length() == 0 || "/".equals(path)) {
			String clave = req.getParameter("clave");
			String strDateSt = req.getParameter("inicio");
			String strDateEnd = req.getParameter("fin");
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_hh:mm:ss");
			Calendar inicio = Calendar.getInstance();
			Calendar fin = Calendar.getInstance();
			if ((!clave.equals("null")) && (!strDateSt.equals("null"))
					&& (!strDateEnd.equals("null"))) {
				Date date;
				try {
					date = sdf.parse(strDateSt);
					inicio.setTime(date);
					date = sdf.parse(strDateEnd);
					fin.setTime(date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else if((!clave.equals("null")) && (strDateSt.equals("null"))
					&& (strDateEnd.equals("null"))){
				inicio = null;
				fin = null;
			}else if((clave.equals("null")) && (!strDateSt.equals("null"))
					&& (!strDateEnd.equals("null"))){
				Date date;
				try {
					date = sdf.parse(strDateSt);
					inicio.setTime(date);
					date = sdf.parse(strDateEnd);
					fin.setTime(date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				clave = null;
			}else{
				clave = null;
				inicio = null;
				fin = null;
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
										.toInstanceNotFoundExceptionXml(new InstanceNotFoundException(
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
									.toInstanceNotFoundExceptionXml(new InstanceNotFoundException(
											ex.getInstanceId().toString(), ex
													.getInstanceType())), null);
			return;
		} catch (EventRegisteredUsersException e) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpStatus.SC_CONFLICT,
							XmlExceptionConversor
									.toEventRegisteredUsersExceptionXml(new EventRegisteredUsersException(e.getMessage())),
							null);
			return;
		}
		ServletUtils.writeServiceResponse(resp, HttpStatus.SC_NO_CONTENT, null,
				null);
	}

}
