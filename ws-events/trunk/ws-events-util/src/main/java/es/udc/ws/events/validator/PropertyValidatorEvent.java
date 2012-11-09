package es.udc.ws.events.validator;

import java.util.Calendar;

import es.udc.ws.events.exceptions.InputDateError;
import es.udc.ws.util.exceptions.InputValidationException;



public final class PropertyValidatorEvent{
	
	private PropertyValidatorEvent(){}

	public static void validateMandatoryString(String propertyName,
            String stringValue) throws InputValidationException {

        if ( (stringValue == null) || (stringValue.length() == 0) ) {
            throw new InputValidationException("Invalid " + propertyName +
                    " value (it cannot be null neither empty): " +
                    stringValue);
        }

    }
	public static void validateDate(String propertyName, Calendar dateSt, Calendar dateEnd) throws InputDateError{
			if (dateSt.before(Calendar.getInstance())){
				throw new InputDateError("The event start date cannot be earlier than the current date");
			}else if (dateEnd.before(Calendar.getInstance())){
				throw new InputDateError("The event end date cannot be earlier than the current date");
			}else if (dateSt.after(dateEnd)){
				throw new InputDateError("The event end date cannot be earlier than the start date of the event");
			}
		
	}
	public static void validateIntern(String propertyName, Boolean value) throws InputValidationException{
		if (value == null) {throw new InputValidationException("Invalid" + propertyName);}
	}
	public static void validateShort(String propertyName, Short value) throws InputValidationException{
		if (value <= 0) {throw new InputValidationException("Invalid "+ propertyName+ " capacity must be higher than 0");}
	}

}
