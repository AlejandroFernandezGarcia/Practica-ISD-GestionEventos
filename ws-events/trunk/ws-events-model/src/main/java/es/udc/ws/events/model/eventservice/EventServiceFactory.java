package es.udc.ws.events.model.eventservice;

import es.udc.ws.util.configuration.ConfigurationParametersManager;

public class EventServiceFactory {
	private final static String CLASS_NAME_PARAMETER = "EventServiceFactory.className";
    private static EventService evServ = null;

    private EventServiceFactory() {
    }

    @SuppressWarnings("rawtypes")
    private static EventService getInstance() {
        try {
            String serviceClassName = ConfigurationParametersManager
                    .getParameter(CLASS_NAME_PARAMETER);
            Class serviceClass = Class.forName(serviceClassName);
            return (EventService) serviceClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public synchronized static EventService getService() {

        if (evServ == null) {
        	evServ = getInstance();
        }
        return evServ;

    }
}

