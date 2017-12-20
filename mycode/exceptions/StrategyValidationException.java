package image.processing.mycode.exceptions;

import javax.xml.bind.ValidationException;


public class StrategyValidationException extends ValidationException {
    public StrategyValidationException(String message) {
        super(message);
    }
}
