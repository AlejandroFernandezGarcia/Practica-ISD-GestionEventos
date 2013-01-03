package es.udc.ws.events.xml;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jdom.DataConversionException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

import es.udc.ws.events.dto.EventDto;
import es.udc.ws.events.xml.XmlEntityResponseWriter;

public class XmlEventDtoConversor {
	public final static Namespace XML_NS = Namespace
			.getNamespace("http://ws.udc.es/events/xml");

	public static XmlEntityResponseWriter toXml(List<EventDto> eventsDtos) {
		Element eventsElement = new Element("events", XML_NS);
		for (int i = 0; i < eventsDtos.size(); i++) {
			EventDto xmlEventDto = eventsDtos.get(i);
			Element eventElement = toJDOMElement(xmlEventDto);
			eventsElement.addContent(eventElement);
		}
		Document document = new Document(eventsElement);
		return new XmlEntityResponseWriter(document);
	}

	public static XmlEntityResponseWriter toXml(EventDto eventDto) {
		Element eventElement = toJDOMElement(eventDto);
		Document document = new Document(eventElement);
		return new XmlEntityResponseWriter(document);
	}

	private static Element toJDOMElement(EventDto eventDto) {
		Element eventElement = new Element("event", XML_NS);

		if (eventDto.getEventId() != null) {
			Element identifierElement = new Element("eventId", XML_NS);
			identifierElement.setText(eventDto.getEventId().toString());
			eventElement.addContent(identifierElement);
		}

		Element nameElement = new Element("name", XML_NS);
		nameElement.setText(eventDto.getName());
		eventElement.addContent(nameElement);

		Element descriptionElement = new Element("description", XML_NS);
		descriptionElement.setText(eventDto.getDescription());
		eventElement.addContent(descriptionElement);

		Element dateStElement = new Element("dateSt", XML_NS);
		//dateStElement.setText(eventDto.getDateSt().toString());
		int day = eventDto.getDateSt().get(Calendar.DAY_OF_MONTH);
        int month = eventDto.getDateSt().get(Calendar.MONTH) + 1;
        int year = eventDto.getDateSt().get(Calendar.YEAR);
        int hour = eventDto.getDateSt().get(Calendar.HOUR_OF_DAY);
        int minute = eventDto.getDateSt().get(Calendar.MINUTE);
        int second = eventDto.getDateSt().get(Calendar.SECOND);

        dateStElement.setAttribute("day", Integer.toString(day));
        dateStElement.setAttribute("month", Integer.toString(month));
        dateStElement.setAttribute("year", Integer.toString(year));
        dateStElement.setAttribute("hour", Integer.toString(hour));
        dateStElement.setAttribute("minute", Integer.toString(minute));
        dateStElement.setAttribute("second", Integer.toString(second));
        /*****/
		eventElement.addContent(dateStElement);

		Element durationElement = new Element("duration", XML_NS);
		durationElement.setText(Integer.toString(eventDto.getDuration()));
		eventElement.addContent(durationElement);

		Element internElement = new Element("intern", XML_NS);
		internElement.setText(Boolean.toString(eventDto.isIntern()));
		eventElement.addContent(internElement);

		Element addressElement = new Element("address", XML_NS);
		addressElement.setText(eventDto.getAddress());
		eventElement.addContent(addressElement);

		Element capacityElement = new Element("capacity", XML_NS);
		capacityElement.setText(Integer.toString(eventDto.getCapacity()));
		eventElement.addContent(capacityElement);

		return eventElement;
	}

	public static EventDto toEvent(InputStream entradaXml) {
		try {

			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(entradaXml);
			Element rootElement = document.getRootElement();

			return toEvent(rootElement);
		} catch (ParsingException ex) {
			throw ex;
		} catch (Exception e) {
			throw new ParsingException(e);
		}
	}

	private static EventDto toEvent(Element eventElement) throws ParseException {
		if (!"event".equals(eventElement.getName())) {
			throw new ParsingException("Unrecognized element '"
					+ eventElement.getName() + "' ('event' expected)");
		}
		Element identifierElement = eventElement.getChild("eventId", XML_NS);
		Long eventId = null;

		if (identifierElement != null) {
			eventId = Long.valueOf(identifierElement.getTextTrim());
		}

		String name = eventElement.getChildTextNormalize("name", XML_NS);

		String description = eventElement.getChildTextNormalize("description",
				XML_NS);

		Calendar dateSt = Calendar.getInstance();
		/*SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date date;
		try {
			date = sdf.parse(eventElement.getChildTextTrim("dateSt", XML_NS));
			dateSt.setTime(date);
		} catch (ParseException e) {
			System.out.println("Falla aqui!!!!");
			throw e;
		}*/
		Element fecha = eventElement.getChild("dateSt",XML_NS);
		int day = 0;
		int month = 0;
		int year = 0;
		int hour = 0;
		int minute = 0;
		int second = 0;
		try {
			day = fecha.getAttribute("day").getIntValue();
			//System.out.println(day);
			month = fecha.getAttribute("month").getIntValue();
			//System.out.println(month);
			year = fecha.getAttribute("year").getIntValue();
			//System.out.println(year);
			hour = fecha.getAttribute("hour").getIntValue();
			//System.out.println(hour);
			minute = fecha.getAttribute("minute").getIntValue();
			//System.out.println(minute);
			second = fecha.getAttribute("second").getIntValue();
			//System.out.println(second);
			
		} catch (DataConversionException e) {
			System.out.println("Falla aqui lo recien cambiado!!!!");
			
		}

        dateSt.set(Calendar.DAY_OF_MONTH, day);
        dateSt.set(Calendar.MONTH, month - 1);
        dateSt.set(Calendar.YEAR, year);
        dateSt.set(Calendar.HOUR_OF_DAY, hour);
        dateSt.set(Calendar.MINUTE, minute);
        dateSt.set(Calendar.SECOND, second);
        dateSt.set(Calendar.MILLISECOND, 0);
		//System.out.println(dateSt.toString());
		
		/**/
		Integer duration = Integer.parseInt(eventElement.getChildTextTrim(
				"duration", XML_NS));

		Boolean intern = Boolean.getBoolean(eventElement.getChildTextTrim(
				"intern", XML_NS));

		String address = eventElement.getChildTextNormalize("address", XML_NS);

		Short capacity = Short.parseShort(eventElement.getChildTextTrim(
				"capacity", XML_NS));

		return new EventDto(eventId, name, description, dateSt, duration,
				intern, address, capacity);
	}

	public static List<EventDto> toEvents(InputStream eventXml) {
		try {

			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(eventXml);
			Element rootElement = document.getRootElement();

			if (!"events".equalsIgnoreCase(rootElement.getName())) {
				throw new ParsingException("Unrecognized element '"
						+ rootElement.getName() + "' ('events' expected)");
			}
			@SuppressWarnings("unchecked")
			List<Element> children = rootElement.getChildren();
			List<EventDto> eventDtos = new ArrayList<>(children.size());
			for (int i = 0; i < children.size(); i++) {
				Element element = children.get(i);
				eventDtos.add(toEvent(element));
			}

			return eventDtos;
		} catch (ParsingException ex) {
			throw ex;
		} catch (Exception e) {
			throw new ParsingException(e);
		}
	}

}
