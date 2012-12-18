package es.udc.ws.events.client.service.soap;

import java.util.ArrayList;
import java.util.List;

import es.udc.ws.events.dto.ResponseDto;

public class ResponseDtoToSoapResponseDtoConversor {
	public static es.udc.ws.events.client.service.soap.wsdl.ResponseDto 
										toSoapResponseDto(ResponseDto response){
		es.udc.ws.events.client.service.soap.wsdl.ResponseDto soapResponseDto = 
				new es.udc.ws.events.client.service.soap.wsdl.ResponseDto();
		
		soapResponseDto.setEventId(response.getEventId());
		soapResponseDto.setResponseId(response.getResponseId());
		soapResponseDto.setAssists(response.isAssists());
		soapResponseDto.setUsername(response.getUsername());
		
		return soapResponseDto;
	}
	public static ResponseDto toResponseDto(
            es.udc.ws.events.client.service.soap.wsdl.ResponseDto response) {
		
		return new ResponseDto(response.getResponseId(),response.getEventId(), 
								response.getUsername(), response.isAssists());
    }     
    
    public static List<ResponseDto> toResponseDtos(
            List<es.udc.ws.events.client.service.soap.wsdl.ResponseDto> responses) {
        List<ResponseDto> responsesDtos = new ArrayList<>(responses.size());
        for (int i = 0; i < responses.size(); i++) {
            es.udc.ws.events.client.service.soap.wsdl.ResponseDto response = 
                    responses.get(i);
            responsesDtos.add(toResponseDto(response));
            
        }
        return responsesDtos;
    }
}
