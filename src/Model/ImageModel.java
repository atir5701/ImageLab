package Model;

/**
 * This class represents an image, so the class
 * contains all the variables and method needed to handle
 * an image. An image is a 3-d matrix of pixels.
 */

public class ImageModel {
  private final int height;
  private final int width;
  private final int[][][] pixels;

  /**
   * Constructs an ImageModel object with the
   * specified height and width. Initializes the
   * pixel array store the RGB values.
   *
   * @param height height of the image.
   * @param width  width of the image.
   */
  public ImageModel(int height, int width) {
    this.height = height;
    this.width = width;
    this.pixels = new int[height][width][3];
  }

  /**
   * Method to obtain the height of the image.
   *
   * @return the height of the image.
   */
  protected int getHeight() {
    return this.height;
  }

  /**
   * Method to obtain the width of the image.
   *
   * @return the width of the image.
   */
  protected int getWidth() {
    return this.width;
  }

  /**
   * Method to obtain the Pixel Value at a specific coordinate
   * of the image.
   *
   * @param x the row of the array.
   * @param y the column of the array.
   * @param z the color channel.
   * @return the value of the specified pixel.
   */
  protected int getPixelValue(int x, int y, int z) {
    return this.pixels[x][y][z];
  }

  /**
   * Method to set the value of pixel in image array at
   * specific location.
   * The value must be between equal 0 and 255 so a clamping is
   * also carried out.
   *
   * @param x     the row of the array.
   * @param y     the column of the array.
   * @param z     the color channel.
   * @param value the value to be set at the specified
   *              coordinate.
   */
  protected void setPixelValue(int x, int y, int z, int value) {
    this.pixels[x][y][z] = Math.max(0, Math.min(255, value));
  }

  /**
   * Method to obtain the Pixel Matrix of the image.
   *
   * @return a 3-d array containing the pixel values of image.
   */
  protected int[][][] getPixelMatrix() {
    return this.pixels;
  }

}
