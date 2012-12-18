package es.udc.ws.events.client.ui;

import java.util.Calendar;
import java.util.StringTokenizer;

import es.udc.ws.events.client.service.ClientEventService;
import es.udc.ws.events.client.service.ClientEventServiceFactory;
import es.udc.ws.events.dto.EventDto;
import es.udc.ws.util.exceptions.InputValidationException;

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
        		Calendar fecha = Calendar.getInstance();
        		StringTokenizer tokens = new StringTokenizer(args[3]," ");
        		int i=0;
        		String vector[]={""};
        		while(tokens.hasMoreTokens()){
        			vector[i] = tokens.nextToken();
        			i++;
        		}
        		StringTokenizer tokens1 = new StringTokenizer(vector[0],"/");
        		i=0;
        		int vectorI[]={0};
        		while(tokens1.hasMoreTokens()){
        			vectorI[i] = Integer.valueOf(tokens1.nextToken());
        			i++;
        		}
        		StringTokenizer tokens2 = new StringTokenizer(vector[1],":");
        		while(tokens2.hasMoreTokens()){
        			vectorI[i] = Integer.valueOf(tokens2.nextToken());
        			i++;
        		}
        		fecha.set(vectorI[2],vectorI[1],vectorI[0],vectorI[3],vectorI[4],vectorI[5]);
        		int duration = Integer.valueOf(args[4]);
        		boolean intern = Boolean.valueOf(args[5]);
        		short capacity = Short.valueOf(args[7]);
        		EventDto eventDto = new EventDto(null,args[1],args[2],fecha,duration,
        										  intern,args[6],capacity);
        		Long eventId = clientEventService.addEvent(eventDto);
        		System.out.println("Event "+ eventId+ " created succesfully");
        	}catch(InputValidationException e){
        		e.printStackTrace(System.err);
        	}
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

    public static void printUsage() {
        System.err.println("Usage:\n"
                + "    [add]    EventServiceClient -a <name>\n"
                + "    [find]   EventServiceClient -f <eventId>\n");
    }

}
