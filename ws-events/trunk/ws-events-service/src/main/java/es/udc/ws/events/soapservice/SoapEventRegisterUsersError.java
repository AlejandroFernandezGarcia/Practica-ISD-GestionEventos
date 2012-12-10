package es.udc.ws.events.soapservice;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(name = "SoapEventRegisterUsersError", targetNamespace = "http://soap.ws.udc.es/")
public class SoapEventRegisterUsersError extends Exception {

    public SoapEventRegisterUsersError(String message) {
        super(message);
    }

    public String getFaultInfo() {
        return getMessage();
    }
}