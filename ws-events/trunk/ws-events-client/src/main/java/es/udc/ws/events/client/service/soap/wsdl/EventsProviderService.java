
package es.udc.ws.events.client.service.soap.wsdl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.7-b01-
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "EventsProviderService", targetNamespace = "http://soap.ws.udc.es/", wsdlLocation = "file:/C:/FIC/Docencia/ISD/Practicas/ws-events-1.0.1/ws-events-service/target/jaxws/wsgen/wsdl/EventsProviderService.wsdl")
public class EventsProviderService
    extends Service
{

    private final static URL EVENTSPROVIDERSERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(es.udc.ws.events.client.service.soap.wsdl.EventsProviderService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = es.udc.ws.events.client.service.soap.wsdl.EventsProviderService.class.getResource(".");
            url = new URL(baseUrl, "file:/C:/FIC/Docencia/ISD/Practicas/ws-events-1.0.1/ws-events-service/target/jaxws/wsgen/wsdl/EventsProviderService.wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'file:/C:/FIC/Docencia/ISD/Practicas/ws-events-1.0.1/ws-events-service/target/jaxws/wsgen/wsdl/EventsProviderService.wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        EVENTSPROVIDERSERVICE_WSDL_LOCATION = url;
    }

    public EventsProviderService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public EventsProviderService() {
        super(EVENTSPROVIDERSERVICE_WSDL_LOCATION, new QName("http://soap.ws.udc.es/", "EventsProviderService"));
    }

    /**
     * 
     * @return
     *     returns EventsProvider
     */
    @WebEndpoint(name = "EventsProviderPort")
    public EventsProvider getEventsProviderPort() {
        return super.getPort(new QName("http://soap.ws.udc.es/", "EventsProviderPort"), EventsProvider.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns EventsProvider
     */
    @WebEndpoint(name = "EventsProviderPort")
    public EventsProvider getEventsProviderPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://soap.ws.udc.es/", "EventsProviderPort"), EventsProvider.class, features);
    }

}