//package image.processing.mycode.api;
//
//
//public class ImageProcessingResponse {
//
//    public static final String OK_STATUS = "OK";
//    private final String status;
//
//    private final String imagePath;
//
//    private final String errorMessage;
//
//    public ImageProcessingResponse(String status, String imagePath, String errorMessage) {
//        this.status = status;
//
//        this.imagePath = imagePath;
//
//        this.errorMessage = errorMessage;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public String getImagePath() {
//        return imagePath;
//    }
//
//    public String getErrorMessage() {
//        return errorMessage;
//    }
//
//    public static ImageProcessingResponse errorImageProcessingResponse(String reasonCode, String message) {
//        return new ImageProcessingResponse(reasonCode, "", message);
//    }
//
//    public static ImageProcessingResponse successImageProcessingResponse(String imagePath) {
//        return new ImageProcessingResponse(OK_STATUS, imagePath, "");
//    }
//
//}
