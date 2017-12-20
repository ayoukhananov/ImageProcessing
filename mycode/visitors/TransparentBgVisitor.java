//package image.processing.mycode.visitors;
//
//
//import com.codahale.metrics.Timer;
//import com.codahale.metrics.Timer.Context;
//import image.processing.mycode.api.ImageUtils;
//import image.processing.mycode.exceptions.VisitorValidationException;
//
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//
//
//public class TransparentBgVisitor extends ImageVisitor {
//    private final TransparentBgAction action;
//    private static final int THRESHOLD_MAX = 255;
//    private final Timer imageProcessingTimer = timer("imageProcessingTimer");
//
//    public TransparentBgVisitor(TransparentBgAction action,String name) {
//        super(name);
//        //log.debug("TransparentBgVisitor C'tor, action colorToTransform = {},threshold = {}, order = {}", action.getColorToTransform(), action.getThreshold(), action.getOrder());
//        this.action = action;
//    }
//
//    @Override
//    public boolean validate(BufferedImage buffered) throws VisitorValidationException {
//        if (action.getThreshold() > THRESHOLD_MAX) {
//            final String errorMsg = String.format("Threshold max is %s, Actual threshold is %s", THRESHOLD_MAX, action.getThreshold());
//            throw new VisitorValidationException(errorMsg);
//        }
//        return true;
//    }
//
//    @Override
//    public BufferedImage visit(BufferedImage buffered) throws IOException {
//        log.debug("TransparentBgVisitor.Visit() start");
//        try (final Context ignored = imageProcessingTimer.time()) {
//            buffered = ImageUtils.modifytToransparentBg(buffered, action.getColorToTransform(), action.getThreshold());
//            log.debug("TransparentBgVisitor.Visit()");
//            return buffered;
//        }
//
//    }
//}
