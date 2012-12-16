package es.udc.ws.events.client.service.soap;

import java.util.Calendar;

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
}
