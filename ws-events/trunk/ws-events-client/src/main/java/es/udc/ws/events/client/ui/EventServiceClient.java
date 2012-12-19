package es.udc.ws.events.client.ui;

import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import es.udc.ws.events.client.service.ClientEventService;
import es.udc.ws.events.client.service.ClientEventServiceFactory;
import es.udc.ws.events.dto.EventDto;
import es.udc.ws.events.exceptions.EventRegisterUsersException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

/*
 * 
 * 
 */
public class EventServiceClient {

    public static void main(String[] args) {
        if (args.length == 0) {
            printUsageAndExit();
        }
        ClientEventService clientEventService = ClientEventServiceFactory.getService();
        if("-a".equalsIgnoreCase(args[0])){
        	//[add] EventServiceClient -a <name> <description> <dateSt> <duration> <intern>
        	//							  <address> <capacity>
        	validateArgs(args,8,new int []{4,7});
        	try{
        		
        		int duration = Integer.valueOf(args[4]);
        		boolean intern = Boolean.valueOf(args[5]);
        		short capacity = Short.valueOf(args[7]);
        		EventDto eventDto = new EventDto(null,args[1],args[2],parseCalendar(args[3]),
        											duration,intern,args[6],capacity);
        		Long eventId = clientEventService.addEvent(eventDto);
        		System.out.println("Event "+ eventId+ " created succesfully");
        	}catch(InputValidationException e){
        		e.printStackTrace(System.err);
        	}
        
        }else if ("-f".equalsIgnoreCase(args[0])){
        	//[find] eventServiceCliente -f <eventId>
        	validateArgs(args,2,new int[]{1});
        		try {
					System.out.println(clientEventService.findEvent(Long.valueOf(args[1])).toString());
				} catch (InstanceNotFoundException e) {
					e.printStackTrace(System.err);
				}
        	
	    }else if ("-u".equalsIgnoreCase(args[0])){
	    	//[update]	 EventServiceClient -u <eventId><name><description><dateSt>
	        //                                       <duration><intern><address><capacity>
	        validateArgs(args,9,new int []{1,5,8});
	    	int duration = Integer.valueOf(args[5]);
    		boolean intern = Boolean.valueOf(args[6]);
    		short capacity = Short.valueOf(args[8]);
    		long eventId = Long.valueOf(args[1]);
    		EventDto eventDto = new EventDto(eventId,args[2],args[3],parseCalendar(args[4]),
    											duration,intern,args[7],capacity);
    		try {
				clientEventService.updateEvent(eventDto);
			} catch (InputValidationException | InstanceNotFoundException
					| EventRegisterUsersException e) {
				e.printStackTrace(System.err);
			}
	    }else if ("-r".equalsIgnoreCase(args[0])){
	    	//[remove]	 EventServiceClient -r 		<eventId>
	    	validateArgs(args,2,new int []{1});
	    	try {
				clientEventService.deleteEvent(Long.valueOf(args[1]));
				System.out.println("Event "+args[1]+" deleted sucesfully");
			} catch (InstanceNotFoundException | EventRegisterUsersException e) {
				e.printStackTrace(System.err);
			}
	    	
	    }else if ("-fb".equalsIgnoreCase(args[0])){
	    	//[findEveBy]	 EventServiceClient -fb		<keyword><date1><date2>
	    	validateArgs(args,4,new int[]{});
	    	List <EventDto> listEvents = clientEventService.findEventByKeyword(args[1],parseCalendar(args[2]),
	    												  parseCalendar(args[3]));
	    	int i = 0;
	    	while(i<listEvents.size()){
	    		System.out.println(listEvents.get(i).toString());
	    		i++;
	    	}
	    }else if ("-res".equalsIgnoreCase(args[0])){
	    	System.out.println("hola");
	    }else if ("-fr".equalsIgnoreCase(args[0])){
	    	System.out.println("hola");
	    }else if ("-frb".equalsIgnoreCase(args[0])){
	    	System.out.println("hola");
	    }
    }

    public static void validateArgs(String[] args, int expectedArgs,
            int[] numericArguments) {
        if (expectedArgs != args.length) {
            printUsageAndExit();
        }
        for (int i = 0; i < numericArguments.length; i++) {
            int position = numericArguments[i];
            try {
                Double.parseDouble(args[position]);
            } catch (NumberFormatException n) {
                printUsageAndExit();
            }
        }
    }

    public static void printUsageAndExit() {
        printUsage();
        System.exit(-1);
    }
    
    private static Calendar parseCalendar(String dateString){
    	Calendar fecha = Calendar.getInstance();
		StringTokenizer tokens = new StringTokenizer(dateString," ");
		int i=0;
		String vector[]={"",""};
		while(tokens.hasMoreTokens()){
			String tok = tokens.nextToken();
			vector[i] = tok;
			i++;
		}
		StringTokenizer tokens1 = new StringTokenizer(vector[0],"/");
		i=0;
		int vectorI[]={0,1,3,4,5,6};
		while(tokens1.hasMoreTokens()){
			vectorI[i] = Integer.valueOf(tokens1.nextToken());
			i++;
		}
		StringTokenizer tokens2 = new StringTokenizer(vector[1],":");
		while(tokens2.hasMoreTokens()){
			vectorI[i] = Integer.valueOf(tokens2.nextToken());
			i++;
		}
		fecha.set(Calendar.YEAR,vectorI[2]);
		fecha.set(Calendar.MONTH,vectorI[1]);
		fecha.set(Calendar.DATE,vectorI[0]);
		fecha.set(Calendar.HOUR_OF_DAY,vectorI[3]);
		fecha.set(Calendar.MINUTE,vectorI[4]);
		fecha.set(Calendar.SECOND,vectorI[5]);
		fecha.set(Calendar.MILLISECOND,0);
		
		return fecha;
    }
    
    public static void printUsage() {
        System.err.println("Usage:\n"
                + "	[add]    	 EventServiceClient -a 		<name><description><dateSt*>"
                +                                       	"<duration><intern><address><capacity>\n"
                + "	[find]   	 EventServiceClient -f 		<eventId>\n"
                + "	[update]	 EventServiceClient -u 		<eventId><name><description><dateSt*>"
                +                                        	"<duration><intern><address><capacity>\n"
                + "	[remove]	 EventServiceClient -r 		<eventId>\n"
                + "	[findEveBy]	 EventServiceClient -fb		<keyword><date1*><date2*>\n"
                + "	[response]	 EventServiceClient -res	<username><eventId><code>\n"
                + "	[findResp]	 EventServiceClient -fr		<eventId><code>\n"
                + "	[findRespBy] EventServiceClient -frb	<responseId>\n\n"
                +"	*Format of the  dates		<dd/MM/YY hh:mm:ss>");
    }
}
