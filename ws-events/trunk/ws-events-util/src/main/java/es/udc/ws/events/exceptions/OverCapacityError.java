package es.udc.ws.events.exceptions;

@SuppressWarnings("serial")
public class OverCapacityError extends Exception {

    public OverCapacityError(String message) {
        super(message);
    }
}
