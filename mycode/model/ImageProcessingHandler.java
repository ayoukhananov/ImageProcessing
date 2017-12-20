package image.processing.mycode.model;

import atlas.imageprocessing.api.ImageProcessingResponse;

import java.util.Optional;

/**
 * User: aviy
 * Date: 1/20/2016
 * Time: 17:01:22
 */

//Handle all requests
public interface ImageProcessingHandler {

    ImageProcessingResponse handle(Optional<String> advProjectId, Optional<String> url, Optional<String> width, Optional<String> height, Optional<String> isExact, String requestUri, Optional<String> changeLogTime);
}
