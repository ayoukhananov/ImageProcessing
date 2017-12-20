//package image.processing.mycode.visitors;
//
//
//
//import image.processing.mycode.model.ImageInfo;
//import image.processing.mycode.model.ImageRepository;
//
//import java.awt.*;
//import java.awt.image.BufferedImage;
//
//
//public class ImageOverlayVisitor extends ImageVisitor {
//
//    private final ImageOverlayAction action;
//    private final ImageRepository imageRepository;
//    //private final Timer imageProcessingTimer = timer("imageProcessingTimer");
//
//    public ImageOverlayVisitor(ImageOverlayAction action, ImageRepository imageRepository,String name) {
//        super(name);
//        //log.debug("ImageOverlayVisitor C'tor, action url = {}, x = {}, y = {}, order = {}", action.getUrl(), action.getX(), action.getY(), action.getOrder());
//        this.action = action;
//        this.imageRepository = imageRepository;
//    }
//
//    @Override
//    public boolean validate(BufferedImage buffered) {
//        return true;
//    }
//
//    @Override
//    public BufferedImage visit(BufferedImage buffered) throws Exception {
//        //log.debug("ImageOverlayVisitor.Visit() start");
//
//            buffered = process(buffered, action);
//            //log.debug("ImageOverlayVisitor.Visit() end");
//            return buffered;
//
//    }
//
//    private BufferedImage process(BufferedImage buffered, ImageOverlayAction action) throws Exception {
//
//        final ImageInfo logoImageInfo = imageRepository.loadImageByUrl(action.getUrl());
//        final BufferedImage logo = logoImageInfo.getBufferedImage();
//        return addImage(buffered, logo, action.getX(), action.getY());
//
//    }
//
//    public static BufferedImage addImage(BufferedImage image, BufferedImage logo,
//                                         int x, int y) {
//        final BufferedImage clone = new BufferedImage(image.getWidth(),
//                image.getHeight(), image.getType());
//        final Graphics2D g2d = clone.createGraphics();
//        g2d.drawImage(image, 0, 0, null);
//        g2d.drawImage(logo, x, y, null);
//        g2d.dispose();
//
//        return clone;
//    }
//
//}
