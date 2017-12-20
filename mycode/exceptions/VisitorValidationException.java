package image.processing.mycode.exceptions;

import javax.xml.bind.ValidationException;


public class VisitorValidationException extends ValidationException {
    public VisitorValidationException(String message) {
        super(message);
    }
}
