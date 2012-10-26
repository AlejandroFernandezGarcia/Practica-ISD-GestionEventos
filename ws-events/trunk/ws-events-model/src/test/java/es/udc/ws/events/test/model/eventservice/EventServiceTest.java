package es.udc.ws.events.test.model.eventservice;

import static es.udc.ws.events.model.util.ModelConstants.TEMPLATE_DATA_SOURCE;

import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.Test;

import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.sql.SimpleDataSource;

public class EventServiceTest {

    @BeforeClass
    public static void init() {

        /*
         * Create a simple data source and add it to "DataSourceLocator" (this
         * is needed to test
         * "es.udc.ws.events.model.eventservice.EventService"
         */
        DataSource dataSource = new SimpleDataSource();

        /*
         * Add "dataSource" to "DataSourceLocator".
         */
        DataSourceLocator.addDataSource(TEMPLATE_DATA_SOURCE, dataSource);

    }


    @Test
    public void testAddEventAndFindEvent() throws InputValidationException,
            InstanceNotFoundException {

        // TODO
    }


}
