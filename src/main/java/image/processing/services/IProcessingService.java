package image.processing.services;

import com.outbrain.ob1k.Service;
import com.outbrain.ob1k.concurrent.ComposableFuture;
import image.processing.dto.CropAction;


public interface IProcessingService extends Service {
  ComposableFuture<String> hello();
  ComposableFuture<String> crop(final CropAction cropAction) throws Exception;
}
