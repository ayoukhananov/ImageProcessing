package image.processing.dto;


public class CropAction {
  private String imageUrl;
  private int xPoint;
  private int yPoint;
  private int width;
  private int height;

  public CropAction() {
  }

  public CropAction(final String imageUrl, final int xPoint, final int yPoint, final int width, final int height) {
    this.imageUrl = imageUrl;
    this.xPoint = xPoint;
    this.yPoint = yPoint;
    this.width = width;
    this.height = height;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(final String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public int getxPoint() {
    return xPoint;
  }

  public void setxPoint(final int xPoint) {
    this.xPoint = xPoint;
  }

  public int getyPoint() {
    return yPoint;
  }

  public void setyPoint(final int yPoint) {
    this.yPoint = yPoint;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(final int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(final int height) {
    this.height = height;
  }
}
