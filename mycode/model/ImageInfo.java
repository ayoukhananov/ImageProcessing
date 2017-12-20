package image.processing.mycode.model;


import java.awt.image.BufferedImage;


//Information about the buffered image
public interface ImageInfo {

    BufferedImage getBufferedImage();

    String getContentType();

}
