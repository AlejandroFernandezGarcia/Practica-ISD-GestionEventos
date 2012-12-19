package es.udc.ws.events.xml;

import java.io.IOException;
import java.io.InputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

import es.udc.ws.events.exceptions.EventRegisterUsersException;
import es.udc.ws.events.exceptions.OverCapacityException;
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
			EventRegisterUsersException e) {
		Element exceptionElement = new Element("EventRegisterUsersError",
				XML_NS);

		Element messageElement = new Element("message", XML_NS);
		messageElement.setText(e.getMessage());
		exceptionElement.addContent(messageElement);
		Document document = new Document(exceptionElement);
		return new XmlEntityResponseWriter(document);
	}

	public static ResponseEntityWriter toOverCapacityError(
			OverCapacityException e) {
		Element exceptionElement = new Element("OverCapacityError",
				XML_NS);

		Element messageElement = new Element("message", XML_NS);
		messageElement.setText(e.getMessage());
		exceptionElement.addContent(messageElement);
		Document document = new Document(exceptionElement);
		return new XmlEntityResponseWriter(document);
	}

	public static InputValidationException fromInputValidationExceptionXml(InputStream in)throws ParsingException{
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

	public static OverCapacityException fromOverCapacityException(InputStream in)throws ParsingException {
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

	public static EventRegisterUsersException fromEventRegisterUsersException(InputStream in)throws ParsingException {
		try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(in);
            Element rootElement = document.getRootElement();

            Element message = rootElement.getChild("message", XML_NS);

            return new EventRegisterUsersException(message.getText());
        } catch (JDOMException | IOException e) {
            throw new ParsingException(e);
        } catch (Exception e) {
            throw new ParsingException(e);
        }
	}

	public static InstanceNotFoundException fromInstanceNotFoundExceptionXml(InputStream in)throws ParsingException {
		try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(in);
            Element rootElement = document.getRootElement();

            Element instanceId = rootElement.getChild("instanceId", XML_NS);
            Element instanceType = 
                    rootElement.getChild("instanceType", XML_NS);

            return new InstanceNotFoundException(instanceId.getText(),
                    instanceType.getText());
        } catch (JDOMException | IOException e) {
            throw new ParsingException(e);
        } catch (Exception e) {
            throw new ParsingException(e);
        }
	}
}
