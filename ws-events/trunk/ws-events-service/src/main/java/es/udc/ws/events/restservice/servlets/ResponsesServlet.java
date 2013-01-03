package es.udc.ws.events.restservice.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;

import es.udc.ws.events.dto.ResponseDto;
import es.udc.ws.events.exceptions.EventRegisteredUsersException;
import es.udc.ws.events.exceptions.OverCapacityException;
import es.udc.ws.events.model.eventservice.EventServiceFactory;
import es.udc.ws.events.model.response.Response;
import es.udc.ws.events.util.ResponseToResponseDtoConversor;
import es.udc.ws.events.xml.XmlExceptionConversor;
import es.udc.ws.events.xml.XmlResponseDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.ServletUtils;

@SuppressWarnings("serial")
public class ResponsesServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("DoPost de response");
		String eventIdParameter = req.getParameter("eventId");
		System.out.println("Pasa al servlet");
		if (eventIdParameter == null) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpStatus.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "parameter 'eventId' is mandatory")),
							null);
			return;
		}
		Long eventId;
		try {
			eventId = Long.valueOf(eventIdParameter);
		} catch (NumberFormatException ex) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpStatus.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "parameter 'eventId' is invalid '"
													+ eventIdParameter + "'")),
							null);

			return;
		}
		String userName = req.getParameter("userName");
		if (userName == null) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpStatus.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "parameter 'userName' is mandatory")),
							null);
			return;
		}
		String strResponse = req.getParameter("respuesta");
		if (strResponse == null) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpStatus.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "parameter 'respuesta' is mandatory")),
							null);

			return;
		}
		Boolean valueResponse = Boolean.getBoolean(strResponse);
		Long responseId;
		Response response;
		System.out.println(userName + eventId + valueResponse);
		try {
			responseId = EventServiceFactory.getService().responseToEvent(
					userName, eventId, valueResponse);
			System.out.println(responseId);
			response = EventServiceFactory.getService().getResponsesByID(
					responseId);
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
		} catch (OverCapacityException ex) {
			ServletUtils.writeServiceResponse(resp, HttpStatus.SC_GONE,
					XmlExceptionConversor
							.toOverCapacityError(new OverCapacityException(
									"Error: Event full")), null);
			return;
		} catch (EventRegisteredUsersException x) {
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
		ResponseDto responseDto = ResponseToResponseDtoConversor
				.toResponseDto(response);

		String responseURL = req.getRequestURL().append("/")
				.append(response.getId()).toString();

		Map<String, String> headers = new HashMap<>(1);
		headers.put("Location", responseURL);

		ServletUtils.writeServiceResponse(resp, HttpStatus.SC_CREATED,
				XmlResponseDtoConversor.toXml(responseDto), headers);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("DoGet de response");
		String path = req.getPathInfo();
		if (path == null || path.length() == 0 || "/".equals(path)) {
			Long eventId = Long.getLong(req.getParameter("eventId"));
			Boolean valueResponse = Boolean.getBoolean(req
					.getParameter("response"));
			Long responseId = Long.getLong(req.getParameter("responseId"));

			if (valueResponse == null) {
				Response response;
				try {
					response = EventServiceFactory.getService()
							.getResponsesByID(responseId);
				} catch (InstanceNotFoundException e) {
					ServletUtils
							.writeServiceResponse(
									resp,
									HttpStatus.SC_NOT_FOUND,
									XmlExceptionConversor
											.toInstanceNotFoundException(new InstanceNotFoundException(
													e.getInstanceId()
															.toString(), e
															.getInstanceType())),
									null);
					return;
				}
				ResponseDto respDto = ResponseToResponseDtoConversor
						.toResponseDto(response);
				ServletUtils.writeServiceResponse(resp, HttpStatus.SC_OK,
						XmlResponseDtoConversor.toXml(respDto), null);
			} else {
				List<Response> listResponses;
				try {
					listResponses = EventServiceFactory.getService()
							.getResponses(eventId, valueResponse);
				} catch (InstanceNotFoundException e) {
					ServletUtils
							.writeServiceResponse(
									resp,
									HttpStatus.SC_NOT_FOUND,
									XmlExceptionConversor
											.toInstanceNotFoundException(new InstanceNotFoundException(
													e.getInstanceId()
															.toString(), e
															.getInstanceType())),
									null);
					return;
				}
				List<ResponseDto> listResponsesDtos = ResponseToResponseDtoConversor
						.toResponseDtos(listResponses);
				ServletUtils.writeServiceResponse(resp, HttpStatus.SC_OK,
						XmlResponseDtoConversor.toXml(listResponsesDtos), null);//???
			}
		} else {
			String responseIdAsString = path.endsWith("/") && path.length() > 2 ? path
					.substring(1, path.length() - 1) : path.substring(1);
			Long responseId;
			try {
				responseId = Long.valueOf(responseIdAsString);
			} catch (NumberFormatException ex) {
				ServletUtils
						.writeServiceResponse(
								resp,
								HttpStatus.SC_BAD_REQUEST,
								XmlExceptionConversor
										.toInputValidationExceptionXml(new InputValidationException(
												"Invalid Request: "
														+ "parameter 'responseId' is invalid '"
														+ responseIdAsString
														+ "'")), null);

				return;
			}
			Response response;
			try {
				response = EventServiceFactory.getService().getResponsesByID(
						responseId);
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
			ResponseDto responseDto = ResponseToResponseDtoConversor
					.toResponseDto(response);
			ServletUtils.writeServiceResponse(resp, HttpStatus.SC_OK,
					XmlResponseDtoConversor.toXml(responseDto), null);
		}
	}
}
