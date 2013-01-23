package es.udc.ws.events.client.service.soap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import es.udc.ws.events.dto.EventDto;

public class EventDtoToSoapEventDtoConversor {
	public static es.udc.ws.events.client.service.soap.wsdl.EventDto toSoapEventDto(
			EventDto event) {
		es.udc.ws.events.client.service.soap.wsdl.EventDto soapEventDto = new es.udc.ws.events.client.service.soap.wsdl.EventDto();
		soapEventDto.setEventId(event.getEventId());
		soapEventDto.setName(event.getName());
		soapEventDto.setDescription(event.getDescription());
		soapEventDto.setDateSt(toXMLGregorianCalendarFromCalendar(event
				.getDateSt()));
		soapEventDto.setDuration(event.getDuration());
		soapEventDto.setIntern(event.isIntern());
		soapEventDto.setAddress(event.getAddress());
		soapEventDto.setCapacity(event.getCapacity());
		return soapEventDto;
	}

	public static EventDto toEventDto(
			es.udc.ws.events.client.service.soap.wsdl.EventDto event) {
		Calendar calendar = toCalendarFromXml(event.getDateSt());
		return new EventDto(event.getEventId(), event.getName(),
				event.getDescription(), calendar, event.getDuration(),
				event.isIntern(), event.getAddress(), event.getCapacity());
	}

	public static List<EventDto> toEventDtos(
			List<es.udc.ws.events.client.service.soap.wsdl.EventDto> events) {
		List<EventDto> eventsDtos = new ArrayList<>(events.size());
		for (int i = 0; i < events.size(); i++) {
			es.udc.ws.events.client.service.soap.wsdl.EventDto event = events
					.get(i);
			eventsDtos.add(toEventDto(event));

		}
		return eventsDtos;
	}

	public static Calendar toCalendarFromXml(XMLGregorianCalendar xmlCalendar) {
		return xmlCalendar.toGregorianCalendar();
	}

	public static XMLGregorianCalendar toXMLGregorianCalendarFromCalendar(
			Calendar cal) {
		try {
			DatatypeFactory dtf = DatatypeFactory.newInstance();
			XMLGregorianCalendar xgc = dtf.newXMLGregorianCalendar();
			xgc.setYear(cal.get(Calendar.YEAR));
			xgc.setDay(cal.get(Calendar.DAY_OF_MONTH));
			xgc.setMonth(cal.get(Calendar.MONTH)+1);
			xgc.setHour(cal.get(Calendar.HOUR_OF_DAY));
			xgc.setMinute(cal.get(Calendar.MINUTE));
			xgc.setSecond(cal.get(Calendar.SECOND));
			xgc.setMillisecond(cal.get(Calendar.MILLISECOND));

			int offsetInMinutes = (cal.get(Calendar.ZONE_OFFSET) + cal
					.get(Calendar.DST_OFFSET)) / (60 * 1000);
			xgc.setTimezone(offsetInMinutes);
			return xgc;

		} catch (DatatypeConfigurationException e) {
			e.printStackTrace(System.err);
			return null;
		}
	}

	public static int getDurationFromCalendar(Calendar date1, Calendar date2) {
		Long dist = (date2.getTime().getTime()) - (date1.getTime().getTime());
		int duration = (int) (dist / 60000);
		return duration;
	}
}
