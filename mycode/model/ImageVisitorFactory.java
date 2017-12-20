package image.processing.mycode.model;


import image.processing.mycode.visitors.ImageVisitor;

//Get image visitors
public interface ImageVisitorFactory {

    ImageVisitor getImageVisitor(ImageAction action) throws Exception;
}
