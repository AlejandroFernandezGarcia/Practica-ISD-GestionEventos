package es.udc.ws.events.service.client.rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;

import es.udc.ws.events.client.service.ClientEventService;
import es.udc.ws.events.dto.EventDto;
import es.udc.ws.events.dto.ResponseDto;
import es.udc.ws.events.exceptions.EventRegisteredUsersException;
import es.udc.ws.events.exceptions.OverCapacityException;
import es.udc.ws.events.xml.ParsingException;
import es.udc.ws.events.xml.XmlEntityResponseWriter;
import es.udc.ws.events.xml.XmlEventDtoConversor;
import es.udc.ws.events.xml.XmlExceptionConversor;
import es.udc.ws.events.xml.XmlResponseDtoConversor;

import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class RestClientEventService implements ClientEventService {
	private final static String ENDPOINT_ADDRESS_PARAMETER = "RestClientEventService.endpointAddress";
	private String endpointAddress;

	@Override
	public Long addEvent(EventDto event) throws InputValidationException {
		PostMethod method = new PostMethod(getEndpointAddress() + "events");
		try {

			ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();
			XmlEntityResponseWriter response;
			try {
				response = XmlEventDtoConversor.toXml(event);
				response.write(xmlOutputStream);
			} catch (IOException ex) {
				throw new InputValidationException(ex.getMessage());
			}
			ByteArrayInputStream xmlInputStream = new ByteArrayInputStream(
					xmlOutputStream.toByteArray());
			InputStreamRequestEntity requestEntity = new InputStreamRequestEntity(
					xmlInputStream, response.getContentType());
			HttpClient client = new HttpClient();
			method.setRequestEntity(requestEntity);

			int statusCode;
			try {
				statusCode = client.executeMethod(method);
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
			try {
				validateResponse(statusCode, HttpStatus.SC_CREATED, method);
			} catch (InputValidationException ex) {
				throw ex;
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
			return getIdFromHeaders(method);

		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
	}

	@Override
	public void updateEvent(EventDto event) throws InputValidationException,
			InstanceNotFoundException, EventRegisteredUsersException {
		PutMethod method =
                new PutMethod(getEndpointAddress() + "events/"
                + event.getEventId());
        try {

            ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();
            XmlEntityResponseWriter response;
            try {
                response = XmlEventDtoConversor.toXml(event);
                response.write(xmlOutputStream);
            } catch (IOException ex) {
                throw new InputValidationException(ex.getMessage());
            }
            ByteArrayInputStream xmlInputStream =
                    new ByteArrayInputStream(xmlOutputStream.toByteArray());
            InputStreamRequestEntity requestEntity =
                    new InputStreamRequestEntity(xmlInputStream,
                    response.getContentType());
            HttpClient client = new HttpClient();
            method.setRequestEntity(requestEntity);

            int statusCode;
            try {
                statusCode = client.executeMethod(method);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                validateResponse(statusCode, HttpStatus.SC_NO_CONTENT, method);
            } catch (InputValidationException | InstanceNotFoundException | EventRegisteredUsersException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }
    }

	@Override
	public void deleteEvent(Long eventId) throws InstanceNotFoundException,
			EventRegisteredUsersException {
		DeleteMethod method =
                new DeleteMethod(getEndpointAddress() + "events/" + eventId);
        try {
            HttpClient client = new HttpClient();
            int statusCode = client.executeMethod(method);
            validateResponse(statusCode, HttpStatus.SC_NO_CONTENT, method);
        } catch (InstanceNotFoundException | EventRegisteredUsersException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }
	}

	@Override
	public EventDto findEvent(Long eventId) throws InstanceNotFoundException {
		GetMethod method = null;
        try {
            method = new GetMethod(getEndpointAddress() + "events/?eventId="
                    + URLEncoder.encode(eventId.toString(), "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
        try {
            HttpClient client = new HttpClient();
            int statusCode;
            try {
                statusCode = client.executeMethod(method);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            try {
                validateResponse(statusCode, HttpStatus.SC_OK, method);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            try {
                return XmlEventDtoConversor.toEvent(
                        method.getResponseBodyAsStream());
            } catch (ParsingException | IOException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }
	}

	@Override
	public List<EventDto> findEventByKeyword(String clave, Calendar fechaIni,
			Calendar fechaFin) {
		GetMethod method = null;
        try {
        	String inicio=fechaIni.get(Calendar.DATE)+"-"+fechaIni.get(Calendar.MONTH)+"-"+fechaIni.get(Calendar.YEAR);
        	String fin=fechaFin.get(Calendar.DATE)+"-"+fechaFin.get(Calendar.MONTH)+"-"+fechaFin.get(Calendar.YEAR);
        	method = new GetMethod(getEndpointAddress() + "events/?clave="
                    + URLEncoder.encode(clave, "UTF-8")+"&inicio="+URLEncoder.encode(inicio, "UTF-8")+"&fin="+URLEncoder.encode(fin, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
        try {
            HttpClient client = new HttpClient();
            int statusCode;
            try {
                statusCode = client.executeMethod(method);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            try {
                validateResponse(statusCode, HttpStatus.SC_OK, method);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            try {
                return XmlEventDtoConversor.toEvents(
                        method.getResponseBodyAsStream());
            } catch (ParsingException | IOException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }
	}

	@Override
	public Long responseToEvent(String username, Long eventId, Boolean code){
		PostMethod method = new PostMethod(getEndpointAddress() + "responses");
        try {
            method.addParameter("eventId", Long.toString(eventId));
            method.addParameter("userName", username);
            method.addParameter("respuesta", Boolean.toString(code));
            HttpClient client = new HttpClient();

            int statusCode;
            try {
                statusCode = client.executeMethod(method);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                validateResponse(statusCode, HttpStatus.SC_CREATED, method);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            return getIdFromHeaders(method);
		} finally {
            if (method != null) {
                method.releaseConnection();
            }
        }
	}

	@Override
	public List<ResponseDto> getResponses(Long eventId, Boolean code)
			throws InstanceNotFoundException {
		GetMethod method = null;
        try {
            method = new GetMethod(getEndpointAddress() + "responses/?eventId="
                    + URLEncoder.encode(eventId.toString(), "UTF-8")+"&response="+URLEncoder.encode(Boolean.toString(code), "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
        try {
            HttpClient client = new HttpClient();
            int statusCode;
            try {
                statusCode = client.executeMethod(method);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            try {
                validateResponse(statusCode, HttpStatus.SC_OK, method);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            try {
                return XmlResponseDtoConversor.toResponses(
                        method.getResponseBodyAsStream());
            } catch (ParsingException | IOException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }
	}

	@Override
	public ResponseDto getResponsesByID(Long responseId)
			throws InstanceNotFoundException {
		GetMethod method = null;
        try {
            method = new GetMethod(getEndpointAddress() + "responses/?responseId="
                    + URLEncoder.encode(responseId.toString(), "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
        try {
            HttpClient client = new HttpClient();
            int statusCode;
            try {
                statusCode = client.executeMethod(method);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            try {
                validateResponse(statusCode, HttpStatus.SC_OK, method);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            try {
                return XmlResponseDtoConversor.toResponse(
                        method.getResponseBodyAsStream());
            } catch (ParsingException | IOException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }
	}

	private synchronized String getEndpointAddress() {

		if (endpointAddress == null) {
			endpointAddress = ConfigurationParametersManager
					.getParameter(ENDPOINT_ADDRESS_PARAMETER);
		}

		return endpointAddress;
	}

	private void validateResponse(int statusCode, int expectedStatusCode,
			HttpMethod method) throws InstanceNotFoundException,
			OverCapacityException, InputValidationException, EventRegisteredUsersException {

		InputStream in;
		try {
			in = method.getResponseBodyAsStream();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		String contentType = getResponseHeader(method, "Content-Type");
		boolean isXmlResponse = "application/xml".equalsIgnoreCase(contentType);
		if (!isXmlResponse && statusCode >= 400) {
			throw new RuntimeException("HTTP error; status code = "
					+ statusCode);
		}
		switch (statusCode) {
		case HttpStatus.SC_NOT_FOUND:
			try {
				throw XmlExceptionConversor
						.fromInstanceNotFoundExceptionXml(in);
			} catch (ParsingException e) {
				throw new RuntimeException(e);
			}
		case HttpStatus.SC_BAD_REQUEST:
			try {
				throw XmlExceptionConversor.fromInputValidationExceptionXml(in);
			} catch (ParsingException e) {
				throw new RuntimeException(e);
			}
		case HttpStatus.SC_INSUFFICIENT_SPACE_ON_RESOURCE:
			try {
				throw XmlExceptionConversor.fromOverCapacityException(in);
			} catch (ParsingException e) {
				throw new RuntimeException(e);
			}
		case HttpStatus.SC_INTERNAL_SERVER_ERROR:
			try {
				throw XmlExceptionConversor.fromEventRegisterUsersException(in);
			} catch (ParsingException e) {
				throw new RuntimeException(e);
			}
		default:
			if (statusCode != expectedStatusCode) {
				throw new RuntimeException("HTTP error; status code = "
						+ statusCode);
			}
			break;
		}
	}
	
	private static Long getIdFromHeaders(HttpMethod method) {
        String location = getResponseHeader(method, "Location");
        if (location != null) {
            int idx = location.lastIndexOf('/');
            return Long.valueOf(location.substring(idx + 1));
        }
        return null;
    }

    private static String getResponseHeader(HttpMethod method,
            String headerName) {
        Header[] headers = method.getResponseHeaders();
        for (int i = 0; i < headers.length; i++) {
            Header header = headers[i];
            if (headerName.equalsIgnoreCase(header.getName())) {
                return header.getValue();
            }
        }
        return null;
    }
}
