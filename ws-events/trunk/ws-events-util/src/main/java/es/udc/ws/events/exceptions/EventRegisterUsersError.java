package es.udc.ws.events.exceptions;

@SuppressWarnings("serial")
public class EventRegisterUsersError extends Exception {

    public EventRegisterUsersError(String message) {
        super(message);
    }
}