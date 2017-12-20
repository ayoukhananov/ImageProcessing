//package image.processing.mycode.visitors;
//
//
//import com.codahale.metrics.Timer;
//import com.codahale.metrics.Timer.Context;
//
//
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//
//
//public class OutputCanvasVisitor extends ImageVisitor {
//
//    private final OutputCanvasAction action;
//    private static final Color DEFAULT_COLOR = Color.decode("#FFFFFF");
//    private final Timer imageProcessingTimer = timer("imageProcessingTimer");
//
//    public OutputCanvasVisitor(OutputCanvasAction action,String name) {
//        super(name);
//        log.debug("OutputCanvasVisitor C'tor, action width = {},height = {}, canvasColor = {},position = {}, order = {}", action.getWidth(), action.getHeight(), action.getColorHex(), action.getPosition(), action.getOrder());
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
//        //log.debug("OutputCanvasVisitor.Visit() start");
//        try (final Context ignored = imageProcessingTimer.time()) {
//            buffered = processImage(buffered, action);
//            //log.debug("OutputCanvasVisitor.Visit() end");
//            return buffered;
//        }
//    }
//
//    private BufferedImage processImage(BufferedImage buffered, OutputCanvasAction action) throws IOException {
//
//        //Boolean isTransparentBg = false;  //TODO in case transparent bg will be required
//
//        final Color color = getColorOrDefault(action.getColorHex());
//        final BufferedImage bImage;
//        if (buffered.getTransparency() == Transparency.TRANSLUCENT) {   //if png and transparent or add transparency
//            bImage = new BufferedImage(action.getWidth(), action.getHeight(), BufferedImage.TYPE_INT_ARGB);
//            //TODO in case transparent bg will be required
//            //isTransparentBg = true;
//            //color = DEFAULT_COLOR; //In case we want transparent bg
//        } else {
//            bImage = new BufferedImage(action.getWidth(), action.getHeight(), BufferedImage.TYPE_INT_RGB);
//        }
//        final Graphics2D bGr = bImage.createGraphics();
//        bGr.setPaint(color);
//        bGr.fillRect(0, 0, bImage.getWidth(), bImage.getHeight());
//        bGr.drawImage(buffered, (bImage.getWidth() - buffered.getWidth()) / 2, (bImage.getHeight() - buffered.getHeight()) / 2, null);
//        bGr.dispose();
//        //TODO in case transparent bg will be required
////        if (isTransparentBg) {  //In case to support the transparency should  bGr.setPaint(color);  color White
////            bImage = ImageUtils.modifytToransparentBg(bImage, "#FFFFFF");
////        }
//
//        return bImage;
//    }
//
//    private Color getColorOrDefault(String colorHex) {
//
//        if (colorHex == null || colorHex.isEmpty()) {
//            return DEFAULT_COLOR;
//        }
//        return Color.decode(colorHex);
//    }
//}
//
