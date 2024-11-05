package model;

/**
 * New version of the operations interface that introduces new methods
 * provided by the ImageProcessingProgram.
 * It extends the older version Operations interface.
 */
public interface OperationsV2 extends Operations {

  /**
   * Method to compress a provided image.
   * It uses the haar wavelet transform algorithm.
   *
   * @param currentImageName name of the image to be compressed.
   * @param newImageName     name of the new compressed image.
   * @param percentage       percentage of compression to be done.
   * @return true if executed successfully, else false.
   */
  boolean compressImage(String currentImageName, String newImageName, double percentage);

  /**
   * Method to visualize a histogram chart.
   * It shows the line charts for frequencies of all three channels.
   *
   * @param currentImageName name of the image to be visualized.
   * @param newImageName     name of the histogram chart.
   * @return true if executed successfully, else false.
   */
  boolean histogram(String currentImageName, String newImageName);

  /**
   * Method to color correct a provided image.
   *
   * @param currentImageName name of the image to be color corrected.
   * @param newImageName     name of the color corrected image.
   * @return true if executed successfully, else false.
   */
  boolean colorCorrection(String currentImageName, String newImageName);

  /**
   * Method to apply levels adjustment on a provided image.
   *
   * @param currentImageName name of the image to be processed.
   * @param newImageName     name of the new processed image.
   * @param b                b value.
   * @param m                m value.
   * @param w                w value.
   * @return true if executed successfully, else false.
   */
  boolean levelAdjustment(String currentImageName,
                          String newImageName, int b, int m, int w);

  /**
   * Method to view an image operation in split preview.
   *
   * @param currentImageName name of the image to be viewed.
   * @param newImageName     name for the image in split view.
   * @param percentage       percentage of split to be applied.
   */
  void splitPreview(String currentImageName, String newImageName, double percentage);

  /**
   * Method to merge the processed image and its original part for
   * split preview.
   *
   * @param currentImageName name of the current image.
   * @param newImageName     name of the new image.
   * @param temp             name of the temporary image.
   * @return true if executed successfully, else false.
   */
  boolean regain(String currentImageName, String temp, String newImageName);
}