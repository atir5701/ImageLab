package controller;

import model.OperationsV2;

/**
 * This is a mock class of the ImageOperationsV2 class.
 * The purpose of this class is to provide mock implementations
 * of the various methods defined in the ImageOperationV2 class.
 * This class extends the OperationsV2 interface.
 */
public class ImageOperationsV2Mock implements OperationsV2 {
  private final StringBuilder log;

  public ImageOperationsV2Mock(StringBuilder log) {
    this.log = log;
  }


  @Override
  public boolean compressImage(String currentImageName, String newImageName, double percentage) {
    log.append("Compress ").append(currentImageName).append(" to ").append(newImageName);
    return true;
  }

  @Override
  public boolean histogram(String currentImageName, String newImageName) {
    log.append("Histogram ").append(currentImageName).append(" to ").append(newImageName);
    return true;
  }

  @Override
  public boolean colorCorrection(String currentImageName, String newImageName) {
    log.append("ColorCorrection ").append(currentImageName).append(" to ").append(newImageName);
    return true;
  }

  @Override
  public boolean levelAdjustment(String currentImageName, String newImageName,
                                 int b, int m, int w) {
    log.append("LevelAdjustment ").append(currentImageName).append(" to ").append(newImageName).append(" with ").append(b).append(" ").append(m).append(" ").append(w);
    return true;
  }

  @Override
  public void splitPreview(String currentImageName, String newImageName,
                           double percentage) {
    log.append("SplitPreview ").append(currentImageName).append(" to ").append(newImageName);
  }

  @Override
  public boolean regain(String currentImageName,String temp, String newImageName) {
    log.append("Regain ").append(currentImageName).append(" to ").append(newImageName);
    return true;
  }

  @Override
  public boolean loadImage(int[][][] matrix, String name) {
    log.append("Load image ").append(name);
    return true;
  }

  @Override
  public int[][][] saveImage(String name) {
    log.append("Save image ").append(name);
    return new int[0][][];
  }

  @Override
  public boolean checkImage(String name) {
    return true;
  }

  @Override
  public boolean getColorComponent(String currentImage, String newImage, int channel) {
    log.append("GetColorComponent ").append(currentImage).append(" to ").append(newImage).append(" ").append(channel);
    return true;
  }

  @Override
  public boolean getBrightnessComponent(String currentImage, String newImage, String handle) {
    log.append("GetBrightnessComponent ").append(currentImage).append(" to ").append(newImage).append(" ").append(handle);
    return true;
  }

  @Override
  public boolean horizontalFlip(String currentImage, String newImage) {
    log.append("HorizontalFlip ").append(currentImage).append(" to ").append(newImage);
    return true;
  }

  @Override
  public boolean verticalFlip(String currentImage, String newImage) {
    log.append("VerticalFlip ").append(currentImage).append(" to ").append(newImage);
    return true;
  }

  @Override
  public boolean brighten(String currentImage, String newImage, int intensity) {
    log.append("Brighten ").append(currentImage).append(" to ").append(newImage).append(" ").append(intensity);
    return true;
  }

  @Override
  public boolean splitRGB(String currentImage, String newRedImage,
                          String newGreenImage, String newBlueImage) {
    log.append("Split RBG ").append(currentImage).append(" to ").append(newRedImage).append(" ").append(newGreenImage).append(" ").append(newBlueImage);
    return true;
  }

  @Override
  public boolean combineRGB(String redImage, String greenImage,
                            String blueImage, String newImage) throws IllegalArgumentException {
    log.append("CombineRGB ").append(redImage).append(" ").append(greenImage).append(" ").append(blueImage).append(" to ").append(newImage);
    return true;
  }

  @Override
  public boolean blur(String currentImage, String newImage) {
    log.append("Blur ").append(currentImage).append(" to ").append(newImage);
    return true;
  }

  @Override
  public boolean sharpen(String currentImage, String newImage) {
    log.append("Sharpen ").append(currentImage).append(" to ").append(newImage);
    return true;
  }

  @Override
  public boolean sepia(String currentImage, String newImage) {
    log.append("Sepia ").append(currentImage).append(" to ").append(newImage);
    return true;
  }
}
