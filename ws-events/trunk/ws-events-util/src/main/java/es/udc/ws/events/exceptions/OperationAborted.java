package es.udc.ws.events.exceptions;


@SuppressWarnings("serial")
public class OperationAborted extends Exception{
	public OperationAborted(String message) {
        super(message);
    }
}