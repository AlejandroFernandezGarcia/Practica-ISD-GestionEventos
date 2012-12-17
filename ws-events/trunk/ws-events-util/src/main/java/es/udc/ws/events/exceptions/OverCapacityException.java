package es.udc.ws.events.exceptions;

@SuppressWarnings("serial")
public class OverCapacityException extends Exception {

    public OverCapacityException(String message) {
        super(message);
    }
}
