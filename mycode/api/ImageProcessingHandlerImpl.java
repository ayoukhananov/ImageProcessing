//package image.processing.mycode.api;
//
//
//import image.processing.mycode.exceptions.ChangeLogValidationException;
//import image.processing.mycode.exceptions.ReasonCode;
//import image.processing.mycode.exceptions.StrategyValidationException;
//import image.processing.mycode.model.ImageInfo;
//import image.processing.mycode.model.ImageProcessingHandler;
//import image.processing.mycode.model.ImageProcessingStrategy;
//import image.processing.mycode.model.ImageProcessingStrategyFactory;
//import image.processing.mycode.model.ImageRepository;
//
//import javax.xml.bind.ValidationException;
//import java.io.FileNotFoundException;
//import java.net.SocketTimeoutException;
//import java.net.UnknownHostException;
//import java.util.List;
//import java.util.MissingFormatArgumentException;
//import java.util.Optional;
//
//
//public class ImageProcessingHandlerImpl implements ImageProcessingHandler {
//
//    private final ImageRepository imageRepository;
//    private final ImageProcessingStrategyFactory imageProcessingStrategyFactory;
//
//
//    public ImageProcessingHandlerImpl(ImageRepository imageRepository, ImageProcessingStrategyFactory imageProcessingStrategyFactory) {
//
//        this.imageRepository = imageRepository;
//        this.imageProcessingStrategyFactory = imageProcessingStrategyFactory;
//
//
//    }
//
//    @Override
//    public ImageProcessingResponse handle(Optional<String> advProjectId, Optional<String> url, Optional<String> width, Optional<String> height, Optional<String> isExact, String requestUri, Optional<String> changeLogTime) {
//        try {
//
//            final long start = System.currentTimeMillis();
//            //log.info("Start handle request = {}", requestUri);
//            final ImageProcessingResponse response = doHandle(advProjectId, url, width, height, isExact, changeLogTime);
//
//            final long totalTime = System.currentTimeMillis() - start;
//
//            //log.info("Successfully image processed overall {}", DurationUtils.toHumanReadable(totalTime));
//            return response;
//
//        } catch (final StrategyValidationException e1) {
//            final String msg = getErrorMessage(url, e1.getMessage());
//            return failureImageProcessingResponse(requestUri, ReasonCode.VALIDATION, e1, msg);
//        } catch (final ChangeLogValidationException e2) {
//            final String msg = getErrorMessage(url, e2.getMessage());
//            return failureImageProcessingResponse(requestUri, ReasonCode.CHANGELOG, e2, msg);
//        } catch (final ValidationException e3) {
//            return failureImageProcessingResponse(requestUri, ReasonCode.VALIDATION, e3);
//        } catch (final FileNotFoundException e4) {
//            final String msg = getErrorMessage(url, e4.getMessage());
//            return failureImageProcessingResponse(requestUri, ReasonCode.NOT_FOUND, e4, msg);
//        } catch (final UnknownHostException e5) {
//            final String msg = getErrorMessage(url, e5.getMessage());
//            return failureImageProcessingResponse(requestUri, ReasonCode.SITE_NOT_RESPONDING, e5, msg);
//        } catch (final SocketTimeoutException e6) {
//            return failureImageProcessingResponse(requestUri, ReasonCode.BAD_URL, e6);
//        } catch (NumberFormatException | UnsupportedOperationException e7) {
//            return unknownFailureImageProcessingResponse(requestUri, ReasonCode.UNKNOWN, e7);
//        } catch (final RuntimeException e8) {
//            return unknownFailureImageProcessingResponse(requestUri, ReasonCode.UNKNOWN, e8);
//        } catch (final Exception ex) {
//            return unknownFailureImageProcessingResponse(requestUri, ReasonCode.UNKNOWN, ex);
//        }
//    }
//
//    private static String getErrorMessage(Optional<String> url, String message) {
//        return String.format("exception message :%s , url:%s", message, url.get());
//    }
//
//    private ImageProcessingResponse doHandle(Optional<String> advProjectId, Optional<String> url, Optional<String> width, Optional<String> height, Optional<String> isExact, Optional<String> changeLogTime) throws Exception {
//
//        validateMandatoryParameters(advProjectId, url, changeLogTime);
//
//        validateChangeLogTimeStamp(changeLogTime.get());
//
//        final ImageInfo imageInfo = imageRepository.loadImageByUrl(url.get());
//
//        //final AdvProject advProject = imageRepository.getAdvProject(advProjectId.get());
//
//        //final String aTok = advProject.getATok();
//
//        //final List<ImageSize> imageSizes = advProject.getImageSizes();
//
//        final PathInfo imagePathInfo = new PathInfo(url.get(), imageInfo.getContentType());
//
//        final ImageProcessingStrategy strategy = imageProcessingStrategyFactory.getStrategy(width, height, isExact);
//
//        final List<ImageInfo> imagesToSave = strategy.doOperation(imageInfo, imageSizes, imagePathInfo);
//
//        final long start = System.currentTimeMillis();
//
//        saveImages(imagesToSave, imagePathInfo);
//
//        final long totalTime = System.currentTimeMillis() - start;
//
//        //log.info("Overall images saved in ={}, image url={}, mdfHashUrl={}", DurationUtils.toHumanReadable(totalTime), url.get(), imagePathInfo.getUrlMD5Hash());
//
//        return ImageProcessingResponse.successImageProcessingResponse(imagePathInfo.getUrlMD5Hash());
//    }
//
//    private void validateChangeLogTimeStamp(String changeLogTime) throws ChangeLogValidationException {
//        final long changeLogTimeStamp = Long.parseLong(changeLogTime);
//        final long metaDataChangeLog = odsService.getLastUpdateChangeLogTime().getMillis();
//        //log.debug("Legacy changeLog={}, Picasso changeLog={}", changeLogTimeStamp, metaDataChangeLog);
//
//        if (changeLogTimeStamp > metaDataChangeLog) {
//            throw new ChangeLogValidationException(String.format("Request parameter changeLog:%s is newer than Picasso changeLog:%s", changeLogTimeStamp, metaDataChangeLog));
//        }
//
//    }
//
//    private void saveImages(List<ImageInfo> imagesToSave, PathInfo imagePathInfo) throws Exception {
//        for (final ImageInfo img : imagesToSave) {
//            final long start = System.currentTimeMillis();
//            imageRepository.saveImage(img, imagePathInfo);
//            final long totalTime = System.currentTimeMillis() - start;
//            //log.debug("Image saved in {}", DurationUtils.toHumanReadable(totalTime));
//        }
//    }
//
//    private static void validateMandatoryParameters(Optional<String> advProjectId, Optional<String> url, Optional<String> changeLogTime) {
//        if (!url.isPresent()) {
//            throw new MissingFormatArgumentException("url parameter is missing");
//        }
//        if (!advProjectId.isPresent()) {
//            throw new MissingFormatArgumentException("advProjectId parameter is missing");
//        }
//        if (!changeLogTime.isPresent()) {
//            throw new MissingFormatArgumentException("changeLogTime parameter is missing");
//        }
//    }
//
//    private ImageProcessingResponse failureImageProcessingResponse(String requestUri, String reasonCode, Exception ex) {
//        //log.error("Processed uri {}, Error {}", requestUri, ex);
//        return ImageProcessingResponse.errorImageProcessingResponse(reasonCode, ex.getMessage());
//    }
//
//    private ImageProcessingResponse unknownFailureImageProcessingResponse(String requestUri, String reasonCode, Exception ex) {
//        return failureImageProcessingResponse(requestUri, reasonCode, ex);
//    }
//
//    private ImageProcessingResponse failureImageProcessingResponse(String requestUri, String reasonCode, Exception ex, String msg) {
//        //log.error("Processed uri {}, Error {}", requestUri, ex);
//        return ImageProcessingResponse.errorImageProcessingResponse(reasonCode, msg);
//    }
//
//}
