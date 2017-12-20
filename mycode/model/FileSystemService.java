package image.processing.mycode.model;


public interface FileSystemService {
  void write(String bucketName, String imagePath, byte[] buffer, String contentType);
}
