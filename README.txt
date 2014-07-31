
Running the project example
-----------------------------

+ Running the events service with Maven/Jetty.

	cd ws-events/ws-events-service
	mvn jetty:run

+ Running the events client application

	Configure ws-events/ws-events-client/src/main/resources/ConfigurationParameters.properties
	for specifying the client project service implementation (Rest or Soap) and 
	the port number of the web server in the endpoint address (9090 for Jetty, 8080
	for Tomcat)

	* AddEvent
		mvn exec:java -Dexec.mainClass="es.udc.ws.events.client.ui.EventServiceClient" -Dexec.args="-a 'New Event'"
		
	* FindEnvent
		mvn exec:java -Dexec.mainClass="es.udc.ws.events.client.ui.EventServiceClient" -Dexec.args="-f 1"


