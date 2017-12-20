package image.processing.mycode.exceptions;

import javax.xml.bind.ValidationException;

/**
 * User: aviy
 * Date: 6/21/2016
 * Time: 14:51:00
 */
public class ChangeLogValidationException extends ValidationException {
    public ChangeLogValidationException(String message) {
        super(message);
    }
}
