package image.processing.model;


import image.processing.api.PathInfo;


public interface ImageRepository {
    ImageInfo loadImageByUrl(String imgUrl) throws Exception;

    void saveImage(ImageInfo image, PathInfo info) throws Exception;

}
