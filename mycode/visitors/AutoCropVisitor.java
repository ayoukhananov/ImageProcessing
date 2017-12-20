//package image.processing.mycode.visitors;
//
//
//
//import com.codahale.metrics.Timer.Context;
//import image.processing.mycode.api.ImageUtils;
//import image.processing.mycode.exceptions.VisitorValidationException;
//
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//
//
//public class AutoCropVisitor extends ImageVisitor {
//
//    public static final double MAX_TOLERANCE = 1.0;
//    private final AutoCropAction action;
//
//
//    public AutoCropVisitor(AutoCropAction action,String name) {
//        super(name);
//        //log.debug("AutoCropVisitor C'tor, action tolerance = {}, order = {}", action.getTolerance(), action.getOrder());
//        this.action = action;
//    }
//
//    @Override
//    public boolean validate(BufferedImage buffered) throws VisitorValidationException {
//        if (action.getTolerance() > MAX_TOLERANCE) {
//            final String errorMsg = String.format("Valid tolerance is between 0.0 to 1.0, Original tolerance = %s ", action.getTolerance());
//            throw new VisitorValidationException(errorMsg);
//        }
//        return true;
//    }
//
//    @Override
//    public BufferedImage visit(BufferedImage buffered) throws IOException {
//        //log.debug("AutoCropVisitor.Visit() start");
//        try (final Context ignored = imageProcessingTimer.time()) {
//            buffered = ImageUtils.getCroppedImage(buffered, action.getTolerance());
//            //log.debug("AutoCropVisitor.Visit() end");
//            return buffered;
//        }
//
//    }
//}
