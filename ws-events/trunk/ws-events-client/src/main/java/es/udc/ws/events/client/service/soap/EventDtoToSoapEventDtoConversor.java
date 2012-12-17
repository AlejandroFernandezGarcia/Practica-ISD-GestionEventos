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
		try{
			DatatypeFactory dtf = DatatypeFactory.newInstance();
			XMLGregorianCalendar xgc = dtf.newXMLGregorianCalendar();
			xgc.setYear(event.getDateSt().get(Calendar.YEAR));
			xgc.setDay(event.getDateSt().get(Calendar.DAY_OF_MONTH));
			xgc.setMonth(event.getDateSt().get(Calendar.MONTH)+ 1);
			xgc.setHour(event.getDateSt().get(Calendar.HOUR_OF_DAY));
			xgc.setMinute(event.getDateSt().get(Calendar.MINUTE));
			xgc.setSecond(event.getDateSt().get(Calendar.SECOND));
			xgc.setMillisecond(event.getDateSt().get(Calendar.MILLISECOND));
			
			int offsetInMinutes = (event.getDateSt().get(Calendar.ZONE_OFFSET) + event.getDateSt().get(Calendar.DST_OFFSET)) / (60 * 1000);
			xgc.setTimezone(offsetInMinutes);
			
			soapEventDto.setDateSt(xgc);
		}catch(DatatypeConfigurationException e){
			return null;
		}
		
		soapEventDto.setDuration(event.getDuration());
		soapEventDto.setIntern(event.isIntern());
		soapEventDto.setAddress(event.getAddress());
		soapEventDto.setCapacity(event.getCapacity());
		return soapEventDto;
	}
	public static EventDto toEventDto(
            es.udc.ws.events.client.service.soap.wsdl.EventDto event) {
			XMLGregorianCalendar xmlCalendar = event.getDateSt();
			TimeZone timeZone = xmlCalendar.getTimeZone(xmlCalendar.getTimezone());          
		    Calendar calendar = Calendar.getInstance(timeZone);  
		    calendar.set(Calendar.YEAR,xmlCalendar.getYear());  
		    calendar.set(Calendar.MONTH,xmlCalendar.getMonth()-1);  
		    calendar.set(Calendar.DATE,xmlCalendar.getDay());  
		    calendar.set(Calendar.HOUR_OF_DAY,xmlCalendar.getHour());  
		    calendar.set(Calendar.MINUTE,xmlCalendar.getMinute());  
		    calendar.set(Calendar.SECOND,xmlCalendar.getSecond());  
        return new EventDto(event.getEventId(),event.getName(),event.getDescription(),
        		calendar ,event.getDuration(),event.isIntern() ,event.getAddress(),
        		event.getCapacity());
    }     
    
    public static List<EventDto> toEventDtos(
            List<es.udc.ws.events.client.service.soap.wsdl.EventDto> events) {
        List<EventDto> eventsDtos = new ArrayList<>(events.size());
        for (int i = 0; i < events.size(); i++) {
            es.udc.ws.events.client.service.soap.wsdl.EventDto movie = 
                    events.get(i);
            eventsDtos.add(toEventDto(movie));
            
        }
        return eventsDtos;
    }
}
