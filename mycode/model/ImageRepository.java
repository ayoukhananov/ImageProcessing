package image.processing.mycode.model;


import image.processing.mycode.api.PathInfo;


public interface ImageRepository {
    ImageInfo loadImageByUrl(String imgUrl) throws Exception;

    void saveImage(ImageInfo image, PathInfo info) throws Exception;

    //List<ImageSize> getImageSizeList(String advProjectId) throws Exception;

}
