//package image.processing.mycode.visitors;
//
//
//import image.processing.mycode.exceptions.VisitorValidationException;
//
//import java.awt.image.BufferedImage;
//
//
//public abstract class ImageVisitor {
//
//    private String name;
//
//    protected ImageVisitor(String name) {
//        this.name = name;
//    }
//
//    public boolean validate(BufferedImage buffered) throws VisitorValidationException {
//        return true;
//    }
//
//    public BufferedImage visit(BufferedImage buffered) throws Exception {
//        return buffered;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//}
//
