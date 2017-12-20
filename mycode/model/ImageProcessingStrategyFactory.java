package image.processing.mycode.model;


import image.processing.mycode.exceptions.StrategyValidationException;

import java.util.Optional;

/**
 * User: aviy
 * Date: 1/28/2016
 * Time: 12:50:22
 */

//Get image processing strategy that contains all processing implementation
public interface ImageProcessingStrategyFactory {
    ImageProcessingStrategy getStrategy(Optional<String> width, Optional<String> height, Optional<String> isExact) throws StrategyValidationException;
}
