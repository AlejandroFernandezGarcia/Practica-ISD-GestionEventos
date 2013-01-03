package es.udc.ws.events.xml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

import es.udc.ws.events.dto.ResponseDto;
import es.udc.ws.events.xml.XmlEntityResponseWriter;

public class XmlResponseDtoConversor {
	public final static Namespace XML_NS = Namespace
			.getNamespace("http://ws.udc.es/responses/xml");

	public static XmlEntityResponseWriter toXml(ResponseDto responseDto) {
		Element responseElement = toJDOMElement(responseDto);
		Document document = new Document(responseElement);
		return new XmlEntityResponseWriter(document);
	}

	public static XmlEntityResponseWriter toXml(
			List<ResponseDto> listResponsesDtos) {
		Element responsesElement = new Element("responses", XML_NS);
		for (int i = 0; i < listResponsesDtos.size(); i++) {
			ResponseDto xmlResponseDto = listResponsesDtos.get(i);
			Element responseElement = toJDOMElement(xmlResponseDto);
			responsesElement.addContent(responseElement);
		}
		Document document = new Document(responsesElement);
		return new XmlEntityResponseWriter(document);
	}

	private static Element toJDOMElement(ResponseDto responseDto) {
		Element responseElement = new Element("response", XML_NS);

		if (responseDto.getResponseId() != null) {
			Element responseIdElement = new Element("responseId", XML_NS);
			responseIdElement.setText(responseDto.getResponseId().toString());
			responseElement.addContent(responseIdElement);
		}

		Element eventIdElement = new Element("eventId", XML_NS);
		eventIdElement.setText(responseDto.getEventId().toString());
		responseElement.addContent(eventIdElement);

		Element userNameElement = new Element("userName", XML_NS);
		userNameElement.setText(responseDto.getUsername());
		responseElement.addContent(userNameElement);

		Element assistsElement = new Element("assists", XML_NS);
		assistsElement.setText(Boolean.toString(responseDto.isAssists()));
		responseElement.addContent(assistsElement);
		
		return responseElement;
	}

	public static List<ResponseDto> toResponses(InputStream responseXml) {
		try {

			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(responseXml);
			Element rootElement = document.getRootElement();

			if (!"responses".equalsIgnoreCase(rootElement.getName())) {
				throw new ParsingException("Unrecognized element '"
						+ rootElement.getName() + "' ('responses' expected)");
			}
			@SuppressWarnings("unchecked")
			List<Element> children = rootElement.getChildren();
			List<ResponseDto> responseDtos = new ArrayList<>(children.size());
			for (int i = 0; i < children.size(); i++) {
				Element element = children.get(i);
				responseDtos.add(toResponse(element));
			}

			return responseDtos;
		} catch (ParsingException ex) {
			throw ex;
		} catch (Exception e) {
			throw new ParsingException(e);
		}
	}

	private static ResponseDto toResponse(Element responseElement) {
		if (!"response".equals(responseElement.getName())) {
			throw new ParsingException("Unrecognized element '"
					+ responseElement.getName() + "' ('response' expected)");
		}
		Element identifierElement = responseElement.getChild("responseId",
				XML_NS);
		Long responseId = null;

		if (identifierElement != null) {
			responseId = Long.valueOf(identifierElement.getTextTrim());
		}
		Long eventId = Long.parseLong(responseElement.getChildTextNormalize(
				"eventId", XML_NS));

		String userName = responseElement.getChildTextNormalize("userName",
				XML_NS);

		Boolean assists = Boolean.getBoolean(responseElement
				.getChildTextNormalize("assists", XML_NS));
		return new ResponseDto(responseId, eventId, userName, assists);
	}

	public static ResponseDto toResponse(InputStream responseXml) {
		try {

			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(responseXml);
			Element rootElement = document.getRootElement();

			return toResponse(rootElement);
		} catch (ParsingException ex) {
			throw ex;
		} catch (Exception e) {
			throw new ParsingException(e);
		}
	}

}