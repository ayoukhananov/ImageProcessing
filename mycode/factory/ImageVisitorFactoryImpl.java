package image.processing.mycode.factory;


import image.processing.mycode.model.ImageRepository;
import image.processing.mycode.model.ImageVisitorFactory;
import image.processing.mycode.visitors.AutoCropVisitor;
import image.processing.mycode.visitors.CropVisitor;
import image.processing.mycode.visitors.ImageOverlayVisitor;
import image.processing.mycode.visitors.ImageVisitor;
import image.processing.mycode.visitors.OutputCanvasVisitor;
import image.processing.mycode.visitors.ResizeVisitor;
import image.processing.mycode.visitors.TransparentBgVisitor;


public class ImageVisitorFactoryImpl implements ImageVisitorFactory {

  private final ImageRepository imageRepository;

  public ImageVisitorFactoryImpl(ImageRepository imageRepository) {
    this.imageRepository = imageRepository;
  }

  @Override
  public ImageVisitor getImageVisitor(String action) throws Exception {

    if (action instanceof AutoCropAction) {
      return new AutoCropVisitor((AutoCropAction) action, "AutoCropVisitor");
    } else if (action instanceof CropAction) {
      return new CropVisitor((CropAction) action, "CropVisitor");
    } else if (action instanceof ImageOverlayAction) {
      return new ImageOverlayVisitor((ImageOverlayAction) action, imageRepository, "ImageOverlayVisitor");
    } else if (action instanceof ResizeAction) {
      return new ResizeVisitor((ResizeAction) action, "ResizeVisitor");
    } else if (action instanceof OutputCanvasAction) {
      return new OutputCanvasVisitor((OutputCanvasAction) action, "OutputCanvasVisitor");
    } else if (action instanceof TransparentBgAction) {
      return new TransparentBgVisitor((TransparentBgAction) action, "TransparentBgVisitor");
    } else {
      throw new Exception("Action not found");
    }

  }
}
