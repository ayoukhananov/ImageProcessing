package image.processing.model;


public interface FileSystemService {
  void write(String imagePath, byte[] buffer, String contentType);
}
