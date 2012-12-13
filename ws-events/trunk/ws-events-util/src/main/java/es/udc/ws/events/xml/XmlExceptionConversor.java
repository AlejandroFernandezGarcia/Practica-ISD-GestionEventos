package es.udc.ws.events.xml;

import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;

import es.udc.ws.events.exceptions.EventRegisterUsersError;
import es.udc.ws.events.exceptions.OverCapacityError;
import es.udc.ws.events.xml.XmlEntityResponseWriter;
import es.udc.ws.events.xml.XmlEventDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.ResponseEntityWriter;


public class XmlExceptionConversor {
	public final static String CONVERSION_PATTERN = "EEE, d MMM yyyy HH:mm:ss Z";

	public final static Namespace XML_NS = XmlEventDtoConversor.XML_NS;

	public static XmlEntityResponseWriter toInputValidationExceptionXml(
			InputValidationException ex) throws IOException {

		Element exceptionElement = new Element("InputValidationException",
				XML_NS);

		Element messageElement = new Element("message", XML_NS);
		messageElement.setText(ex.getMessage());
		exceptionElement.addContent(messageElement);

		Document document = new Document(exceptionElement);
		return new XmlEntityResponseWriter(document);
	}

	public static XmlEntityResponseWriter toInstanceNotFoundException(
			InstanceNotFoundException e) {
		Element exceptionElement = new Element("InstanceNotFoundException",
				XML_NS);

		Element messageElement = new Element("message", XML_NS);
		messageElement.setText(e.getMessage());
		exceptionElement.addContent(messageElement);
		Document document = new Document(exceptionElement);
		return new XmlEntityResponseWriter(document);
	}

	public static XmlEntityResponseWriter toEventRegisterUsersError(
			EventRegisterUsersError e) {
		Element exceptionElement = new Element("EventRegisterUsersError",
				XML_NS);

		Element messageElement = new Element("message", XML_NS);
		messageElement.setText(e.getMessage());
		exceptionElement.addContent(messageElement);
		Document document = new Document(exceptionElement);
		return new XmlEntityResponseWriter(document);
	}

	public static ResponseEntityWriter toOverCapacityError(
			OverCapacityError e) {
		Element exceptionElement = new Element("OverCapacityError",
				XML_NS);

		Element messageElement = new Element("message", XML_NS);
		messageElement.setText(e.getMessage());
		exceptionElement.addContent(messageElement);
		Document document = new Document(exceptionElement);
		return new XmlEntityResponseWriter(document);
	}
}
