package es.udc.ws.events.xml;

@SuppressWarnings("serial")
public class ParsingException extends RuntimeException {

	public ParsingException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParsingException(Throwable cause) {
		super(cause);
	}

	public ParsingException(String message) {
		super(message);
	}

	public ParsingException() {
	}

}
