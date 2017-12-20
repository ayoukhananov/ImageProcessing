package image.processing.repository;


import image.processing.api.ImageInfoImpl;
import image.processing.api.PathInfo;
import image.processing.model.FileSystemService;
import image.processing.model.ImageInfo;
import image.processing.model.ImageRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ImageRepositoryImpl implements ImageRepository {
    public static final String PNG_CONTENT_TYPE = "image/png";
    public static final String GIF_CONTENT_TYPE = "image/gif";
    public static final String JPEG_CONTENT_TYPE = "image/jpeg";

    public static final String PNG_TYPE = "png";
    public static final String JPEG_TYPE = "jpg";

    public static final String EXACT_SIZE = "_1";

    private final FileSystemService fileSystemService;



    public ImageRepositoryImpl(FileSystemService fileSystemService) {
        this.fileSystemService = fileSystemService;
    }

    @Override
    public ImageInfo loadImageByUrl(String imgUrl) throws Exception {
        HttpURLConnection huc = null;
        InputStream input = null;
        try {
            final URL url = new URL(imgUrl);
            //log.debug("loadImageByUrl() imgUrl = {}", imgUrl);
            huc = (HttpURLConnection) url.openConnection();
            huc.setInstanceFollowRedirects(true);
            huc.setConnectTimeout(1000);
            huc.setReadTimeout(30000);
            huc.setRequestMethod("GET");
            huc.connect();

            final String contentType = huc.getContentType();
            //log.debug("loadImageByUrl() Image contentType is: {}", contentType);
            if (contentType.isEmpty()) {
                final String msg = String.format("Image content type is missing for url = %s", url);
                throw new IllegalArgumentException(msg);
            }

            input = huc.getInputStream();

            final BufferedImage buffered = ImageIO.read(input);
            if(buffered == null){
                //log.info("BufferedImage is null, contentType: {}",contentType);
            }
            return new ImageInfoImpl(buffered, contentType);
        } finally {
            if (huc != null) {
                huc.disconnect();
            }
            if (input != null) {
                input.close();
            }
        }
    }

    @Override
    public void saveImage(ImageInfo imageInfo, PathInfo info) throws Exception {

        final byte[] buffer = getByteArray(imageInfo);

        final String imagePath = getImagePath(imageInfo, info);

        final String contentType = getContentType(imageInfo);

        fileSystemService.write(imagePath, buffer, contentType);
    }

    private String getImagePath(ImageInfo imageInfo, PathInfo info) throws Exception {
//        final ImageSize size = imageInfo.getImageSize();
//        final StringBuilder path = new StringBuilder();
//        path.append(imageProcessingProperties.getEnv().toLowerCase());
//        path.append('/');
//        path.append(imageProcessingProperties.getImagesFolder());
//        path.append('/');
//        path.append(info.getAtok().toLowerCase());
//        path.append('/');
//        path.append(size.getWidth());
//        path.append('X');
//        path.append(size.getHeight());
//        if (size.isExactSize()) {
//            path.append(EXACT_SIZE);
//        }
//
//        path.append('/');
//
//        path.append(info.getUrlMD5Hash());
//
//        log.debug("getImagePath() S3 image path : {}", path);
//        return path.toString();
        return "Path";

    }

    private static String getContentType(ImageInfo imageInfo) {

        if (isPng(imageInfo)) {
            return PNG_CONTENT_TYPE;
        }
        return JPEG_CONTENT_TYPE;
    }



    private static byte[] getByteArray(ImageInfo imageInfo) throws Exception {

        final ByteArrayOutputStream os = new ByteArrayOutputStream();

        final BufferedImage buffered = imageInfo.getBufferedImage();

        if (isPng(imageInfo, buffered)) {
            ImageIO.write(buffered, PNG_TYPE, os);
        } else if (imageInfo.getContentType().toLowerCase().equals(JPEG_CONTENT_TYPE.toLowerCase())) {
            ImageIO.write(buffered, JPEG_TYPE, os);
        }else{
            throw new Exception("Bad content type");
        }

        return os.toByteArray();
    }

    private static boolean isPng(ImageInfo imageInfo, BufferedImage buffered) {
        final String contentTypeLowerCase = imageInfo.getContentType().toLowerCase();
        return contentTypeLowerCase.equals(PNG_CONTENT_TYPE.toLowerCase()) || buffered.getTransparency() == Transparency.TRANSLUCENT || contentTypeLowerCase.equals(GIF_CONTENT_TYPE.toLowerCase());
    }

    private static boolean isPng(ImageInfo imageInfo) {
        final String contentTypeLowerCase = imageInfo.getContentType().toLowerCase();
        return contentTypeLowerCase.equals(PNG_CONTENT_TYPE.toLowerCase()) || imageInfo.getBufferedImage().getTransparency() == Transparency.TRANSLUCENT || contentTypeLowerCase.equals(GIF_CONTENT_TYPE.toLowerCase());
    }

}
