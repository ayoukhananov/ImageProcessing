package image.processing.mycode.strategy;



import image.processing.mycode.api.PathInfo;
import image.processing.mycode.exceptions.StrategyValidationException;
import image.processing.mycode.exceptions.VisitorValidationException;
import image.processing.mycode.model.ImageInfo;
import image.processing.mycode.model.ImageProcessingStrategy;
import image.processing.mycode.model.ImageVisitorFactory;
import image.processing.mycode.visitors.ImageVisitor;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class NoImageSizeStrategyImpl implements ImageProcessingStrategy {

    public static final int VISITORS_INITIAL_CAPACITY = 2;
    private final ImageVisitorFactory imageVisitorFactory;

    public NoImageSizeStrategyImpl(ImageVisitorFactory imageVisitorFactory) {
        //log.info("NoImageSizeStrategyImpl constructor");
        this.imageVisitorFactory = imageVisitorFactory;
    }

    @Override
    public List<ImageInfo> doOperation(ImageInfo imageInfo, List<ImageSize> imageSizeList, PathInfo imagePathInfo) throws Exception {

        validateImageSizeList(imageSizeList, imagePathInfo);

        final List<ImageInfo> images = new ArrayList<>();

        for (final ImageSize imageSize : imageSizeList) {

            final List<ImageAction> actions = imageSize.getSortedImageActions();

            validateActions(imageSize, actions);

            log.info("{}", imageSize.toString());

            final List<ImageVisitor> imageVisitors = createVisitorsByActions(actions);

            final ImageInfo image = imageInfo.withImageSize(imageSize);

            //Actions are sorted so visitors also
            final ImageInfo processedImage = executeOrSkipVisitors(image, imageVisitors);

            images.add(processedImage);

            imageVisitors.clear();

        }
        return images;
    }

    private static void validateActions(ImageSize imageSize, List<ImageAction> actions) throws StrategyValidationException {
        if (actions.isEmpty()) {
            final String errorMsg = String.format("Actions not found, ImageSize width = %s, height = %s, isExact = %s, id = %s", imageSize.getWidth(), imageSize.getHeight(), imageSize.isExactSize(), imageSize.getId());
            throw new StrategyValidationException(errorMsg);
        }
    }

    private List<ImageVisitor> createVisitorsByActions(List<ImageAction> actions) throws Exception {
        final List<ImageVisitor> imageVisitors = new ArrayList<>(VISITORS_INITIAL_CAPACITY);
        for (final ImageAction action : actions) {
            final ImageVisitor visitor = imageVisitorFactory.getImageVisitor(action);
            imageVisitors.add(visitor);
        }
        return imageVisitors;
    }

    private static void validateImageSizeList(List<ImageSize> imageSizeList, PathInfo imagePathInfo) throws StrategyValidationException {
        if (imageSizeList.isEmpty()) {
            final String errorMsg = String.format("imageSize not found for atok: %s, url: %s", imagePathInfo.getAtok(), imagePathInfo.getUrl());
            throw new StrategyValidationException(errorMsg);
        }
    }

    private ImageInfo executeOrSkipVisitors(ImageInfo imageInfo, List<ImageVisitor> imageVisitors) {

        BufferedImage buffered = imageInfo.getBufferedImage();
        for (final ImageVisitor visitor : imageVisitors) {
            try {
                final boolean isValid = visitor.validate(buffered);
                if (isValid) {
                    buffered = visitor.visit(buffered);
                }
            } catch (final VisitorValidationException e) {
                skipAction.mark();
                log.warn("Skip action name:{} ,validation exception:{}", visitor.getName(), e.toString());
            } catch (final Exception ex) {
                skipAction.mark();
                log.warn("Skip action name:{},general exception:{}", visitor.getName(), ex.toString());
            }
        }
        return imageInfo.withBufferedImage(buffered);
    }
}
