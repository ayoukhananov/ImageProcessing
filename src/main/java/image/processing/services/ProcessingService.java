package image.processing.services;

import com.outbrain.ob1k.concurrent.ComposableFuture;
import com.sun.tools.internal.ws.wsdl.framework.ValidationException;
import image.processing.api.ImageInfoImpl;
import image.processing.api.PathInfo;
import image.processing.dto.CropAction;
import image.processing.model.ImageInfo;
import image.processing.model.ImageRepository;
import org.imgscalr.Scalr;

import java.awt.image.BufferedImage;

import static com.outbrain.ob1k.concurrent.ComposableFutures.fromValue;

public class ProcessingService implements IProcessingService {
  private final String defaultTitle;
  private ImageRepository imageRepository;

  public ProcessingService(String defaultTitle,
                           ImageRepository imageRepository) {
    this.defaultTitle = defaultTitle;
    this.imageRepository = imageRepository;
  }

  @Override
  public ComposableFuture<String> hello() {
    return fromValue(defaultTitle);
  }

  @Override
  public ComposableFuture<String> crop(CropAction cropAction) throws Exception {

    final ImageInfo originalImageInfo = imageRepository.loadImageByUrl(cropAction.getImageUrl());
    if(originalImageInfo.getBufferedImage() == null){
      throw new ValidationException("buffered image is null");
    }
    final BufferedImage bufferedOut = Scalr.crop(originalImageInfo.getBufferedImage(), cropAction.getxPoint(), cropAction.getyPoint(), cropAction.getWidth(), cropAction.getHeight());

    final ImageInfoImpl imageInfoOut = new ImageInfoImpl(bufferedOut, originalImageInfo.getContentType());

    final PathInfo imagePathInfo = new PathInfo("url", "content type");

    imageRepository.saveImage(imageInfoOut, imagePathInfo);
    return fromValue(imagePathInfo.getUrlMD5Hash());
  }

}
