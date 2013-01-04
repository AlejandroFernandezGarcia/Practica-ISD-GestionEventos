package es.udc.ws.events.xml;

import java.io.IOException;
import java.io.InputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

import es.udc.ws.events.exceptions.EventRegisteredUsersException;
import es.udc.ws.events.exceptions.OverCapacityException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

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

	public static XmlEntityResponseWriter toInstanceNotFoundExceptionXml(
			InstanceNotFoundException e) {
		Element exceptionElement = new Element("InstanceNotFoundException",
				XML_NS);

		Element messageElement = new Element("message", XML_NS);
		messageElement.setText(e.getMessage());
		exceptionElement.addContent(messageElement);
		Document document = new Document(exceptionElement);
		return new XmlEntityResponseWriter(document);
	}

	public static XmlEntityResponseWriter toEventRegisteredUsersExceptionXml(
			EventRegisteredUsersException e) {
		Element exceptionElement = new Element("EventRegisteredUsersException",
				XML_NS);

		Element messageElement = new Element("message", XML_NS);
		messageElement.setText(e.getMessage());
		exceptionElement.addContent(messageElement);
		System.out.println("XML");
		
		Document document = new Document(exceptionElement);
		return new XmlEntityResponseWriter(document);
	}

	public static XmlEntityResponseWriter toOverCapacityExceptionXml(
			OverCapacityException e) {
		Element exceptionElement = new Element("OverCapacityException", XML_NS);

		Element messageElement = new Element("message", XML_NS);
		messageElement.setText(e.getMessage());
		exceptionElement.addContent(messageElement);
		Document document = new Document(exceptionElement);
		return new XmlEntityResponseWriter(document);
	}

	public static InputValidationException fromInputValidationExceptionXml(
			InputStream in) throws ParsingException {
		try {

			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(in);
			Element rootElement = document.getRootElement();

			Element message = rootElement.getChild("message", XML_NS);
			
			return new InputValidationException(message.getText());
		} catch (JDOMException | IOException e) {
			throw new ParsingException(e);
		} catch (Exception e) {
			throw new ParsingException(e);
		}
	}

	public static OverCapacityException fromOverCapacityExceptionXml(InputStream in)
			throws ParsingException {
		try {

			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(in);
			Element rootElement = document.getRootElement();

			Element message = rootElement.getChild("message", XML_NS);

			return new OverCapacityException(message.getText());
		} catch (JDOMException | IOException e) {
			throw new ParsingException(e);
		} catch (Exception e) {
			throw new ParsingException(e);
		}
	}

	public static EventRegisteredUsersException fromEventRegisterUsersExceptionXml(
			InputStream in) throws ParsingException {
		try {
			System.out.println("XML2");

			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(in);
			Element rootElement = document.getRootElement();
			Element message = rootElement.getChild("message", XML_NS);

			return new EventRegisteredUsersException(message.getText());
		} catch (JDOMException | IOException e) {
			System.out.println("XML2a");

			throw new ParsingException(e);
		} catch (Exception e) {
			System.out.println("XML2b");

			throw new ParsingException(e);
		}
	}

	public static InstanceNotFoundException fromInstanceNotFoundExceptionXml(
			InputStream in) throws ParsingException {
		try {

			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(in);
			Element rootElement = document.getRootElement();

			Element instanceId = rootElement.getChild("instanceId", XML_NS);
			Element instanceType = rootElement.getChild("instanceType", XML_NS);

			return new InstanceNotFoundException(instanceId.getText(),
					instanceType.getText());
		} catch (JDOMException | IOException e) {
			throw new ParsingException(e);
		} catch (Exception e) {
			throw new ParsingException(e);
		}
	}
}
