//package image.processing.mycode.visitors;
//
//
//import com.codahale.metrics.Timer;
//import com.codahale.metrics.Timer.Context;
//import image.processing.mycode.api.ImageUtils;
//import image.processing.mycode.exceptions.VisitorValidationException;
//import org.imgscalr.Scalr;
//import org.imgscalr.Scalr.Method;
//
//import java.awt.*;
//import java.awt.image.BufferedImage;
//
//
//public class ResizeVisitor extends ImageVisitor {
//
//    private final ResizeAction action;
//    private final Timer imageProcessingTimer = timer("imageProcessingTimer");
//
//    public ResizeVisitor(ResizeAction action,String name) {
//        super(name);
//        log.debug("ResizeVisitor C'tor, action width = {},height = {}, order = {}", action.getWidth(), action.getHeight(), action.getOrder());
//        this.action = action;
//    }
//
//    @Override
//    public boolean validate(BufferedImage buffered) throws VisitorValidationException {
//        if (action.getWidth() > buffered.getWidth() && action.getHeight() > buffered.getHeight()) {
//            final String errorMsg = String.format("InLarge image size not supported. Original image size W %s X H %s. action image size W %s X H %s ", buffered.getWidth(), buffered.getHeight(), action.getWidth(), action.getHeight());
//            throw new VisitorValidationException(errorMsg);
//        }
//        return true;
//    }
//
//    @Override
//    public BufferedImage visit(BufferedImage buffered) {
//        log.debug("ResizeVisitor.Visit() start");
//        try (final Context ignored = imageProcessingTimer.time()) {
//            buffered = process(buffered, action);
//            log.debug("ResizeVisitor.Visit() end");
//            return buffered;
//        }
//    }
//
//    private BufferedImage process(BufferedImage buffered, ResizeAction action) {
//        final Dimension imgSize = new Dimension(buffered.getWidth(), buffered.getHeight());
//
//        final Dimension boundary = new Dimension(action.getWidth(), action.getHeight());
//        final Dimension newImgSize = ImageUtils.getScaledDimension(imgSize, boundary);
//
//        final BufferedImage resizedImage;
//        if (buffered.getType() == BufferedImage.TYPE_BYTE_INDEXED) //In case of gif
//        {
//            resizedImage = Scalr.resize(buffered, Method.AUTOMATIC, newImgSize.width, newImgSize.height);
//        } else {
//            resizedImage = Scalr.resize(buffered, Method.ULTRA_QUALITY, newImgSize.width, newImgSize.height); //Scalr.OP_ANTIALIAS
//        }
//
//        return resizedImage;
//    }
//}
