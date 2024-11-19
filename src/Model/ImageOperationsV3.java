package model;

/**
 * This class implements the new methods needed to be implemented by the program.
 * It extends the older class ImageOperationV2 and implements
 * the new OperationsV3 interface.
 * It introduces the following new methods.
 * Masking of image.
 * DownScaling of image.
 */


public class ImageOperationsV3 extends ImageOperationsV2 implements OperationsV3 {

  /**
   * Applies a mask to the current image. Pixels of the
   * current image are replaced with
   * pixels from a temporary image wherever the
   * corresponding mask image pixels are black
   * (R=0, G=0, B=0). The result is saved as a new image.
   *
   * @param currentImageName the name of the current image to
   *                         which the mask will be applied
   * @param temp             the name of the temporary image providing
   *                         replacement pixel values
   * @param maskImageName    the name of the mask image used to
   *                         determine which pixels to replace
   * @param newImageName     the name under which the
   *                         resulting image will be saved
   * @return true if the masking operation is successful
   * @throws IllegalArgumentException if the dimensions of
   *                                  the mask and the current image differ
   */
  @Override
  public boolean mask(String currentImageName, String temp, String maskImageName,
                      String newImageName) {
    ImageModel oldImage = this.imageMap.get(currentImageName);
    ImageModel mask = this.imageMap.get(maskImageName);
    if (mask.getHeight() != oldImage.getHeight() && mask.getWidth()
            != oldImage.getWidth()) {
      throw new IllegalArgumentException("The Dimensions of mask" +
              " and the image to be processed " +
              "are not same.");
    }
    ImageModel tempImage = this.imageMap.get(temp);
    int[][][] newMatrix = oldImage.getPixelMatrix();
    for (int i = 0; i < oldImage.getHeight(); i++) {
      for (int j = 0; j < oldImage.getWidth(); j++) {
        if (mask.getPixelValue(i, j, 0) == 0
                && mask.getPixelValue(i, j, 1) == 0 &&
                mask.getPixelValue(i, j, 2) == 0) {
          for (int k = 0; k <= 2; k++) {
            newMatrix[i][j][k] = tempImage.getPixelValue(i, j, k);
          }
        }
      }
    }
    ImageModel newImage = this.getNewImageModel(oldImage, newMatrix);
    this.imageMap.put(newImageName, newImage);
    return true;
  }

  /**
   * Downscales the current image to a specified target height
   * and width using bi-linear interpolation.
   * The resulting image is saved under a new name.
   *
   * @param currentImageName the name of the current image to be downscaled
   * @param targetHeight     the target height of the downscaled image
   * @param targetWidth      the target width of the downscaled image
   * @param newImageName     the name under which the downscaled image will be saved
   * @throws IllegalArgumentException if targetHeight or
   *                                  targetWidth is less than or equal to zero
   */
  @Override
  public void downScale(String currentImageName, int targetHeight,
                        int targetWidth, String newImageName) {
    ImageModel currentImage = this.imageMap.get(currentImageName);
    int[][][] output = new int[targetHeight][targetWidth][3];
    int height = currentImage.getHeight();
    int width = currentImage.getWidth();
    double xFactor = (double) height / targetHeight;
    double yFactor = (double) width / targetWidth;

    for (int i = 0; i < targetHeight; i++) {
      for (int j = 0; j < targetWidth; j++) {
        double xsrc = i * xFactor;
        double ysrc = j * yFactor;

        int xf = Math.min((int) Math.floor(xsrc), height - 1);
        int xc = Math.min((int) Math.ceil(xsrc), height - 1);
        int yf = Math.min((int) Math.floor(ysrc), width - 1);
        int yc = Math.min((int) Math.ceil(ysrc), width - 1);

        for (int k = 0; k < 3; k++) {
          int ca = currentImage.getPixelValue(xf, yf, k);
          int cb = currentImage.getPixelValue(xc, yf, k);
          int cc = currentImage.getPixelValue(xf, yc, k);
          int cd = currentImage.getPixelValue(xc, yc, k);

          double weightX = xsrc - xf;
          double weightY = ysrc - yf;

          double m = ca * (1 - weightX) + cb * weightX;
          double n = cc * (1 - weightX) + cd * weightX;
          int value = (int) Math.round(m * (1 - weightY) + n * weightY);
          this.setPixelValue(i, j, k, value, output);
        }
      }
    }
    ImageModel newImage = new ImageModel(targetHeight, targetWidth, output);
    this.imageMap.put(newImageName, newImage);
  }
}
