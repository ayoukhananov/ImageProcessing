//package image.processing.mycode.visitors;
//
//import com.codahale.metrics.Timer;
//import com.codahale.metrics.Timer.Context;
//
//import org.imgscalr.Scalr;
//
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//
//
//public class CropVisitor extends ImageVisitor {
//
//    private final CropAction action;
//
//    public CropVisitor(CropAction action,String name) {
//        super(name);
//       // log.debug("CropVisitor C'tor, action x = {}, y = {},width = {},height = {}, order = {}", action.getX(), action.getY(), action.getWidth(), action.getHeight(), action.getOrder());
//        this.action = action;
//    }
//
//    @Override
//    public boolean validate(BufferedImage buffered) {
//        return true;
//    }
//
//    @Override
//    public BufferedImage visit(BufferedImage buffered) throws IOException {
//        log.debug("CropVisitor.Visit() start");
//        try (final Context ignored = imageProcessingTimer.time()) {
//            buffered = Scalr.crop(buffered, action.getX(), action.getY(), action.getWidth(), action.getHeight());
//            log.debug("CropVisitor.Visit() end");
//            return buffered;
//        }
//
//    }
//}
