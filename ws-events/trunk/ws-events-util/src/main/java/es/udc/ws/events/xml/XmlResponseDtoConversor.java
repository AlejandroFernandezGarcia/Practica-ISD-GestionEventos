package es.udc.ws.events.xml;

import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;

import es.udc.ws.events.dto.ResponseDto;
import es.udc.ws.events.xml.XmlEntityResponseWriter;

public class XmlResponseDtoConversor {
	public final static Namespace XML_NS =
            Namespace.getNamespace("http://ws.udc.es/responses/xml");
	public static XmlEntityResponseWriter toXml(ResponseDto responseDto) {
		Element responseElement = toJDOMElement(responseDto);
        Document document = new Document(responseElement);
        return new XmlEntityResponseWriter(document);
	}

	public static XmlEntityResponseWriter toXml(List<ResponseDto> listResponsesDtos) {
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

}
