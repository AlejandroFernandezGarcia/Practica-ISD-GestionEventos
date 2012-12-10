package es.udc.ws.events.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.udc.ws.events.dto.ResponseDto;
import es.udc.ws.events.model.eventservice.EventServiceFactory;
import es.udc.ws.events.model.response.Response;
import es.udc.ws.util.exceptions.InstanceNotFoundException;


public class ResponseToResponseDtoConversor {

	    public static List<ResponseDto> toResponseDtos(List<Response> responses) {
	        List<ResponseDto> responseDtos = new ArrayList<>(responses.size());
	        for (int i = 0; i < responses.size(); i++) {
	            Response response = responses.get(i);
	            responseDtos.add(toResponseDto(response));
	        }
	        return responseDtos;
	    }

	    public static ResponseDto toResponseDto(Response response) {
	        return new ResponseDto(response.getId(), response.getEventId(), response.getUsername(), response.isAssists());
	    }

	    public static Response toResponse(ResponseDto responseDto) throws InstanceNotFoundException {
	        return EventServiceFactory.getService().getResponsesByID(responseDto.getResponseId());
	    }
}
