package image.processing.api;


import image.processing.model.ImageInfo;

import java.awt.image.BufferedImage;


public class ImageInfoImpl implements ImageInfo {

    private final BufferedImage bufferedImage;
    private final String contentType;

    public ImageInfoImpl(final BufferedImage bufferedImage, final String contentType) {
        this.bufferedImage = bufferedImage;
        this.contentType = contentType;
    }

    @Override
    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    @Override
    public String getContentType() {
        return contentType;
    }
}
