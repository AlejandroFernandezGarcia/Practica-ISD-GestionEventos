package es.udc.ws.events.client.service.soap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import es.udc.ws.events.dto.EventDto;

public class EventDtoToSoapEventDtoConversor {
	public static es.udc.ws.events.client.service.soap.wsdl.EventDto 
										toSoapEventDto(EventDto event){
		es.udc.ws.events.client.service.soap.wsdl.EventDto soapEventDto = 
				new es.udc.ws.events.client.service.soap.wsdl.EventDto();
		soapEventDto.setEventId(event.getEventId());
		soapEventDto.setName(event.getName());
		soapEventDto.setDescription(event.getDescription());
		soapEventDto.setDateSt(toXMLGregorianCalendarFromCalendar(event.getDateSt()));
		soapEventDto.setDuration(event.getDuration());
		soapEventDto.setIntern(event.isIntern());
		soapEventDto.setAddress(event.getAddress());
		soapEventDto.setCapacity(event.getCapacity());
		return soapEventDto;
	}
	public static EventDto toEventDto(
            es.udc.ws.events.client.service.soap.wsdl.EventDto event) {
		Calendar calendar = toCalendarFromXml(event.getDateSt());
        return new EventDto(event.getEventId(),event.getName(),event.getDescription(),
        		calendar ,event.getDuration(),event.isIntern() ,event.getAddress(),
        		event.getCapacity());
    }     
    
    public static List<EventDto> toEventDtos(
            List<es.udc.ws.events.client.service.soap.wsdl.EventDto> events) {
        List<EventDto> eventsDtos = new ArrayList<>(events.size());
        for (int i = 0; i < events.size(); i++) {
            es.udc.ws.events.client.service.soap.wsdl.EventDto event = 
                    events.get(i);
            eventsDtos.add(toEventDto(event));
            
        }
        return eventsDtos;
    }
    
    public static Calendar toCalendarFromXml(XMLGregorianCalendar xmlCalendar){
		TimeZone timeZone = xmlCalendar.getTimeZone(xmlCalendar.getTimezone());          
	    Calendar calendar = Calendar.getInstance(timeZone);  
	    calendar.set(Calendar.YEAR,xmlCalendar.getYear());  
	    calendar.set(Calendar.MONTH,xmlCalendar.getMonth());  
	    calendar.set(Calendar.DATE,xmlCalendar.getDay());  
	    calendar.set(Calendar.HOUR_OF_DAY,xmlCalendar.getHour());  
	    calendar.set(Calendar.MINUTE,xmlCalendar.getMinute());  
	    calendar.set(Calendar.SECOND,xmlCalendar.getSecond());
	    
	    return calendar;
    }
    
    public static XMLGregorianCalendar toXMLGregorianCalendarFromCalendar(Calendar cal){
    	try{
			DatatypeFactory dtf = DatatypeFactory.newInstance();
			XMLGregorianCalendar xgc = dtf.newXMLGregorianCalendar();
			xgc.setYear(cal.get(Calendar.YEAR));
			xgc.setDay(cal.get(Calendar.DAY_OF_MONTH));
			xgc.setMonth(cal.get(Calendar.MONTH));
			xgc.setHour(cal.get(Calendar.HOUR_OF_DAY));
			xgc.setMinute(cal.get(Calendar.MINUTE));
			xgc.setSecond(cal.get(Calendar.SECOND));
			xgc.setMillisecond(cal.get(Calendar.MILLISECOND));
			
			int offsetInMinutes = (cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET)) / (60 * 1000);
			xgc.setTimezone(offsetInMinutes);
			return xgc;
			
		}catch(DatatypeConfigurationException e){
			return null;
		}
    }
}
