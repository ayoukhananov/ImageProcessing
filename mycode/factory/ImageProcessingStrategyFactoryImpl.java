//package image.processing.mycode.factory;

//import lombok.extern.slf4j.Slf4j;
//
//import java.util.Optional;
//

//public class ImageProcessingStrategyFactoryImpl implements ImageProcessingStrategyFactory {
//
//    private final ImageVisitorFactory imageVisitorFactory;
//
//    public ImageProcessingStrategyFactoryImpl(ImageVisitorFactory imageVisitorFactory) {
//        this.imageVisitorFactory = imageVisitorFactory;
//    }
//
//    @Override
//    public ImageProcessingStrategy getStrategy(Optional<String> width, Optional<String> height, Optional<String> isExact) throws StrategyValidationException {
//
//        if (width.isPresent() && height.isPresent() && isExact.isPresent()) {
//            final int parsedWidth = Integer.parseInt(width.get());
//            final int parsedHeight = Integer.parseInt(height.get());
//            final boolean parsedIsExact = convertToBoolean(isExact.get());
//
//            return new ImageProcessingStrategyImpl(parsedWidth, parsedHeight, parsedIsExact, this.imageVisitorFactory);
//        }
//        validateAllSizeParamsMissing(width, height, isExact);
//
//        return new NoImageSizeStrategyImpl(this.imageVisitorFactory);
//
//    }
//
//    private static void validateAllSizeParamsMissing(Optional<String> width, Optional<String> height, Optional<String> isExact) throws StrategyValidationException {
//        if (width.isPresent() || height.isPresent() || isExact.isPresent()) {
//            throw new StrategyValidationException("One of size parameters in url is missing");
//        }
//
//    }
//
//    private static boolean convertToBoolean(String value) {
//
//        if ("1".equalsIgnoreCase(value) || "true".equalsIgnoreCase(value)) {
//            return true;
//        }
//        if ("0".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
//            return false;
//        }
//        throw new UnsupportedOperationException("isExact parameter part of url contains not valid value  " + value);
//    }
//}
