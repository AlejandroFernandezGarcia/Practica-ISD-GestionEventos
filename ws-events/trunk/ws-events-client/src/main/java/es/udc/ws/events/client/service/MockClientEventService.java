package es.udc.ws.events.client.service;

import es.udc.ws.events.dto.EventDto;

public class MockClientEventService implements ClientEventService {

    @Override
    public Long addEvent(EventDto event) {
        // TODO Auto-generated method stub
        return (long) 0;
    }

    @Override
    public EventDto findEvent(Long eventId) {
        return new EventDto(eventId, "Event name");
    }

}
