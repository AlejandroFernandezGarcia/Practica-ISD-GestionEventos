package es.udc.ws.events.xml;

import java.util.List;

import org.jdom.Namespace;

import es.udc.ws.events.dto.EventDto;
import es.udc.ws.util.servlet.ResponseEntityWriter;

public class XmlEventDtoConversor {
	public final static Namespace XML_NS =
            Namespace.getNamespace("http://ws.udc.es/movies/xml");
	public static ResponseEntityWriter toXml(List<EventDto> listaEventosDtos) {
		// TODO Auto-generated method stub
		return null;
	}

	public static ResponseEntityWriter toXml(EventDto eventDto) {
		// TODO Auto-generated method stub
		return null;
	}

}
