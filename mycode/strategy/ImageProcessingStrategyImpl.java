package image.processing.mycode.strategy;


import image.processing.mycode.api.PathInfo;
import image.processing.mycode.exceptions.StrategyValidationException;
import image.processing.mycode.exceptions.VisitorValidationException;
import image.processing.mycode.model.ImageInfo;
import image.processing.mycode.model.ImageProcessingStrategy;
import image.processing.mycode.model.ImageVisitorFactory;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ImageProcessingStrategyImpl implements ImageProcessingStrategy {

    private final int width;
    private final int height;
    private final boolean isExact;
    private final ImageVisitorFactory imageVisitorFactory;

    public ImageProcessingStrategyImpl(int width, int height, boolean isExact, ImageVisitorFactory imageVisitorFactory) {
        //log.info("ImageProcessingStrategyImpl constructor Width = {} , Height= {} ,isExact = {}", width, height, isExact);
        this.width = width;
        this.height = height;
        this.isExact = isExact;
        this.imageVisitorFactory = imageVisitorFactory;
    }

    @Override
    public List<ImageInfo> doOperation(ImageInfo imageInfo, List<ImageSize> imageSizeList, PathInfo imagePathInfo) throws Exception {

        final Optional<ImageSize> imageSize = imageSizeList.stream().filter(x -> x.getWidth() == width && x.getHeight() == height && x.isExactSize() == isExact).findFirst();

        validateImageSize(imageSize, imagePathInfo);

        //log.info("{}", imageSize.get().toString());

        final List<ImageAction> actions = imageSize.get().getSortedImageActions();

        validateActions(actions);

        final ImageInfo image = imageInfo.withImageSize(imageSize.get());

        final List<ImageVisitor> imageVisitors = createVisitorsByActions(actions);

        //Actions are sorted so visitors also
        final ImageInfo processed = executeOrSkipVisitors(image, imageVisitors);

        return Collections.singletonList(processed);
    }

    private static void validateActions(List<ImageAction> actions) throws StrategyValidationException {

        if (actions.isEmpty()) {
            throw new StrategyValidationException("Actions are empty");
        }
    }

    private void validateImageSize(Optional<ImageSize> imageSize, PathInfo imagePathInfo) throws StrategyValidationException {
        if (!imageSize.isPresent()) {
            final String errorMsg = String.format("imageSize not found, ImageSize width = %s, height = %s, isExact = %s, atok = %s, url = %s .Not found.", this.width, this.height, this.isExact, imagePathInfo.getAtok(), imagePathInfo.getUrl());
            throw new StrategyValidationException(errorMsg);
        }
    }

    private List<ImageVisitor> createVisitorsByActions(List<ImageAction> actions) throws Exception {

        final List<ImageVisitor> imageVisitors = new ArrayList<>();
        //Actions are sorted so visitors also
        for (final ImageAction action : actions) {
            imageVisitors.add(imageVisitorFactory.getImageVisitor(action));
        }
        return imageVisitors;
    }

    private  ImageInfo executeOrSkipVisitors(ImageInfo imageInfo, List<ImageVisitor> imageVisitors) {

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
