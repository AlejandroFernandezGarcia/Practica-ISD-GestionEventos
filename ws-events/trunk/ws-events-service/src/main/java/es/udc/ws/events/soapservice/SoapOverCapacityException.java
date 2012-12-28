package es.udc.ws.events.soapservice;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(name = "SoapOverCapacityError", targetNamespace = "http://soap.ws.udc.es/")
public class SoapOverCapacityException extends Exception {

	public SoapOverCapacityException(String message) {
		super(message);
	}

	public String getFaultInfo() {
		return getMessage();
	}
}