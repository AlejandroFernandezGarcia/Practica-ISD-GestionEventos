package es.udc.ws.events.client.ui;

import es.udc.ws.events.client.service.ClientEventService;
import es.udc.ws.events.client.service.MockClientEventService;
import es.udc.ws.events.dto.EventDto;
import es.udc.ws.events.exceptions.InputValidationException;

public class EventServiceClient {

    public static void main(String[] args) {

        if (args.length == 0) {
            printUsageAndExit();
        }
        ClientEventService clientEventService = new MockClientEventService();
        if ("-a".equalsIgnoreCase(args[0])) {
            validateArgs(args, 2, new int[] {});

            // [add] EventServiceClient -a <name>

            try {
                Long eventId = clientEventService.addEvent(new EventDto(null,
                        args[1]));

                System.out.println("Event " + eventId + " "
                        + "created sucessfully");

            } catch (NumberFormatException | InputValidationException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        } else if ("-f".equalsIgnoreCase(args[0])) {
            validateArgs(args, 2, new int[] { 1 });

            // [find] EventServiceClient -f <eventId>

            try {
                EventDto eventDto = clientEventService.findEvent(Long
                        .parseLong(args[1]));
                System.out.println("Id: " + eventDto.getEventId() + " Name: "
                        + eventDto.getName());
            } catch (NumberFormatException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
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
