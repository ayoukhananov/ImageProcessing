package image.processing.mycode.model;



//Properties of image processing
public interface ImageProcessingProperties {
    String getBucketName();

    String getImagesFolder();

    String getEnv();

    int getConnectionTimeOut();

    int getReadTimeOut();
}
