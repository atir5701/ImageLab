package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * This class implements the new methods needed to be implemented by the program.
 * It extends the older class and implements the new OperationsV2 interface.
 * It introduces the following new methods.
 * Compression.
 * Histogram Visualization.
 * Color Correction.
 * Levels Adjustment.
 * Split Preview.
 */

public class ImageOperationsV2 extends ImageOperations implements OperationsV2 {

  /**
   * Method to compress a given image by the provided percentage value.
   * It uses the Haar Wavelet Transform algorithm.
   *
   * @param currentImageName the name of the original image to be compressed.
   * @param newImageName     the name of the new compressed image.
   * @param percentage       percentage value by which the image is to be compressed.
   * @return true if operation done successfully, else false.
   */
  @Override
  public boolean compressImage(String currentImageName, String newImageName, double percentage) {
    ImageModel imageOld = this.imageMap.get(currentImageName);
    ImageModel imageNew = this.getNewImageModel(imageOld);
    int height = imageOld.getHeight();
    int width = imageOld.getWidth();
    int paddingSize = this.padding(Math.max(height, width));
    double[][] red = new double[paddingSize][paddingSize];
    double[][] green = new double[paddingSize][paddingSize];
    double[][] blue = new double[paddingSize][paddingSize];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        red[i][j] = imageOld.getPixelValue(i, j, 0);
        green[i][j] = imageOld.getPixelValue(i, j, 1);
        blue[i][j] = imageOld.getPixelValue(i, j, 2);
      }
    }
    red = this.pipeline(red, paddingSize, percentage);
    green = this.pipeline(green, paddingSize, percentage);
    blue = this.pipeline(blue, paddingSize, percentage);

    this.combineChannel(imageNew, red, green, blue);

    this.imageMap.put(newImageName, imageNew);
    return true;
  }

  /**
   * Helper method to combine all three separate channels.
   *
   * @param imageNew new combined image.
   * @param red      red channel of the image.
   * @param green    green channel of the image.
   * @param blue     blue channel of the image.
   */
  private void combineChannel(ImageModel imageNew, double[][] red,
                              double[][] green, double[][] blue) {
    for (int i = 0; i < imageNew.getHeight(); i++) {
      for (int j = 0; j < imageNew.getWidth(); j++) {
        imageNew.setPixelValue(i, j, 0, (int) red[i][j]);
        imageNew.setPixelValue(i, j, 1, (int) green[i][j]);
        imageNew.setPixelValue(i, j, 2, (int) blue[i][j]);
      }
    }
  }

  /**
   * Helper method for the compressImage function.
   *
   * @param matrix     the red/green/blue channel matrix.
   * @param size       size of the matrix.
   * @param percentage percentage value for compression.
   * @return new processed matrix.
   */
  protected double[][] pipeline(double[][] matrix, int size, double percentage) {
    matrix = this.haar(matrix, size);
    matrix = this.compress(matrix, percentage);
    matrix = this.inverseHarr(matrix, size);
    return matrix;
  }

  /**
   * Helper method implementing the inverseHaar transform.
   *
   * @param matrix the red/green/blue channel matrix.
   * @param size   size of the matrix.
   * @return new processed matrix.
   */
  protected double[][] inverseHarr(double[][] matrix, int size) {
    int c = 2;
    while (c <= size) {
      matrix = this.inverseTransformColumn(matrix, c);
      matrix = this.inverseTransformRow(matrix, c);
      c = c * 2;
    }
    return matrix;
  }

  /**
   * Helper method for rows in inverseHaar method.
   *
   * @param matrix the red/green/blue channel matrix.
   * @param c      num of iterations.
   * @return new processed matrix.
   */
  protected double[][] inverseTransformRow(double[][] matrix, int c) {
    for (int i = 0; i < matrix.length; i++) {
      List<Double> avg = new ArrayList<>();
      List<Double> diff = new ArrayList<>();
      for (int j = 0; j < c / 2; j++) {
        avg.add((matrix[i][j] + matrix[i][j + (c / 2)]) / Math.sqrt(2.0));
        diff.add((matrix[i][j] - matrix[i][j + (c / 2)]) / (Math.sqrt(2.0)));
      }
      for (int j = 0; j < c; j = j + 2) {
        matrix[i][j] = avg.get(j / 2);
        matrix[i][j + 1] = diff.get(j / 2);
      }
    }
    return matrix;
  }

  /**
   * Helper method for columns in inverseHaar method.
   *
   * @param matrix the red/green/blue channel matrix.
   * @param c      num of iterations.
   * @return new processed matrix.
   */
  protected double[][] inverseTransformColumn(double[][] matrix, int c) {
    for (int i = 0; i < matrix.length; i++) {
      List<Double> avg = new ArrayList<>();
      List<Double> diff = new ArrayList<>();
      for (int j = 0; j < c / 2; j++) {
        avg.add((matrix[j][i] + matrix[j + (c / 2)][i]) / Math.sqrt(2.0));
        diff.add((matrix[j][i] - matrix[j + (c / 2)][i]) / (Math.sqrt(2.0)));
      }
      for (int j = 0; j < c; j = j + 2) {
        matrix[j][i] = avg.get(j / 2);
        matrix[j + 1][i] = diff.get(j / 2);
      }
    }
    return matrix;
  }

  /**
   * Helper method implementing the Haar transform.
   *
   * @param matrix the red/green/blue channel matrix.
   * @param size   size of the matrix.
   * @return new processed matrix.
   */
  protected double[][] haar(double[][] matrix, int size) {
    int c = size;
    while (c > 1) {
      matrix = this.transformRow(matrix, c);
      matrix = this.transformColumn(matrix, c);
      c = c / 2;
    }
    return matrix;
  }

  /**
   * Helper method for rows in Haar method.
   *
   * @param matrix the red/green/blue channel matrix.
   * @param c      num of iterations.
   * @return new processed matrix.
   */
  protected double[][] transformRow(double[][] matrix, int c) {
    for (int i = 0; i < matrix.length; i++) {
      List<Double> avg = new ArrayList<>();
      List<Double> diff = new ArrayList<>();
      for (int j = 0; j < c; j = j + 2) {
        avg.add((matrix[i][j] + matrix[i][j + 1]) / (Math.sqrt(2.0)));
        diff.add((matrix[i][j] - matrix[i][j + 1]) / (Math.sqrt(2.0)));
      }
      for (int j = 0; j < avg.size(); j++) {
        matrix[i][j] = avg.get(j);
        matrix[i][avg.size() + j] = diff.get(j);
      }
    }
    return matrix;
  }

  /**
   * Helper method for columns in Haar method.
   *
   * @param matrix the red/green/blue channel matrix.
   * @param c      num of iterations.
   * @return new processed matrix.
   */
  protected double[][] transformColumn(double[][] matrix, int c) {
    for (int i = 0; i < matrix.length; i++) {
      List<Double> avg = new ArrayList<>();
      List<Double> diff = new ArrayList<>();
      for (int j = 0; j < c; j = j + 2) {
        avg.add((matrix[j][i] + matrix[j + 1][i]) / (Math.sqrt(2.0)));
        diff.add((matrix[j][i] - matrix[j + 1][i]) / (Math.sqrt(2.0)));
      }
      for (int j = 0; j < avg.size(); j++) {
        matrix[j][i] = ((avg.get(j)));
        matrix[j + avg.size()][i] = (diff.get(j));
      }
    }
    return matrix;
  }

  /**
   * Helper method performing the compression step in pipeline of compressImage.
   *
   * @param matrix     the red/green/blue channel matrix.
   * @param percentage percentage value for compression.
   * @return new processed matrix.
   */
  protected double[][] compress(double[][] matrix, double percentage) {
    double threshold = Double.MAX_VALUE;
    if (percentage == 100.0) {
      return applyThreshold(matrix, threshold);
    }
    List<Double> value = new ArrayList<>();
    for (double[] doubles : matrix) {
      for (int j = 0; j < matrix.length; j++) {
        double num = Math.round(doubles[j] * 1000.0) / 1000.0;
        value.add(Math.abs(num));
      }
    }
    List<Double> distinctPixel = new ArrayList<>(new HashSet<>(value));
    Collections.sort(distinctPixel);
    int index = (int) Math.round((distinctPixel.size() * (percentage / 100.0)));
    threshold = distinctPixel.get(index);
    return applyThreshold(matrix, threshold);
  }

  /**
   * Applying the calculated threshold on each pixel.
   *
   * @param matrix    the red/green/blue channel matrix.
   * @param threshold threshold value.
   * @return new processed matrix.
   */
  protected double[][] applyThreshold(double[][] matrix, double threshold) {
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix.length; j++) {
        if (Math.abs(matrix[i][j]) < threshold) {
          matrix[i][j] = 0.0;
        }
      }
    }
    return matrix;
  }

  /**
   * Helper method to add padding to the provided matrix.
   * Because the matrix should be a square matrix with size of 2^x, where x
   * is a natural number.
   *
   * @param value max of current width and height.
   * @return new size.
   */
  protected int padding(int value) {
    int size = 1;
    while (size < value) {
      size = size * 2;
    }
    return size;
  }

  /**
   * Method to visualize a histogram graph for the provided image.
   * It shows a line graph for all three red, green and blue channels.
   *
   * @param currentImageName name of image for which histogram is to be generated.
   * @param newImageName     histogram graph.
   * @return true if operation executed successfully, else false.
   */
  @Override
  public boolean histogram(String currentImageName,
                           String newImageName) {
    ImageModel oldImage = this.imageMap.get(currentImageName);
    ImageModel newImage = new ImageModel(256, 256);

    int[][][] matrix = oldImage.getPixelMatrix();
    this.setBackground(newImage);
    int[] red = this.getFrequency(matrix, 0);
    int[] green = this.getFrequency(matrix, 1);
    int[] blue = this.getFrequency(matrix, 2);

    int redPeak = Arrays.stream(red).max().getAsInt();
    int greenPeak = Arrays.stream(green).max().getAsInt();
    int bluePeak = Arrays.stream(blue).max().getAsInt();

    int redMin = Arrays.stream(red).min().getAsInt();
    int greenMin = Arrays.stream(green).min().getAsInt();
    int blueMin = Arrays.stream(blue).min().getAsInt();

    int max_value = Math.max(Math.max(redPeak, greenPeak), bluePeak);
    int min_value = Math.min(Math.min(redMin, greenMin), blueMin);

    red = this.applyNormalize(red, max_value, min_value);
    green = this.applyNormalize(green, max_value, min_value);
    blue = this.applyNormalize(blue, max_value, min_value);

    this.connectPeak(newImage, red, green, blue);
    this.imageMap.put(newImageName, newImage);
    return true;
  }

  /**
   * Helper method for histogram that generates a canvas, with grid lines.
   *
   * @param img empty image model.
   */
  private void setBackground(ImageModel img) {
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        if (i % (17) == 0 || j % (17) == 0) {
          img.setPixelValue(i, j, 0, 200);
          img.setPixelValue(i, j, 1, 200);
          img.setPixelValue(i, j, 2, 200);
        } else {
          img.setPixelValue(i, j, 0, 255);
          img.setPixelValue(i, j, 1, 255);
          img.setPixelValue(i, j, 2, 255);
        }
      }
    }
  }

  /**
   * Helper method to get frequency array of intensity values.
   *
   * @param matrix  image matrix.
   * @param channel red/green/blue.
   * @return array with frequencies for each 0-255 values.
   */
  protected int[] getFrequency(int[][][] matrix, int channel) {
    int[] n = new int[256];
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        n[matrix[i][j][channel]] = n[matrix[i][j][channel]] + 1;
      }
    }
    return n;
  }

  /**
   * Helper method to normalize a given row.
   *
   * @param arr       row.
   * @param maxValue max limit.
   * @param minValue min limit.
   * @return normalized array.
   */
  protected int[] applyNormalize(int[] arr, int maxValue,
                                 int minValue) {
    for (int i = 0; i < arr.length; i++) {
      arr[i] = Math.round((float) ((arr[i] - minValue) * 255) / (maxValue - minValue));
    }
    return arr;
  }

  /**
   * Method to connect the peaks of histogram.
   *
   * @param m     ImageModel.
   * @param red   channel.
   * @param green channel.
   * @param blue  channel.
   */
  private void connectPeak(ImageModel m, int[] red,
                           int[] green, int[] blue) {
    int prevRed = 255 - red[0];
    int prevGreen = 255 - green[0];
    int prevBlue = 255 - blue[0];
    for (int j = 1; j < 256; j++) {
      int currentRed = 255 - red[j];
      int currentGreen = 255 - green[j];
      int currentBlue = 255 - blue[j];
      this.drawLine(prevRed, currentRed, m, j, new int[]{255, 0, 0});
      this.drawLine(prevGreen, currentGreen, m, j, new int[]{0, 255, 0});
      this.drawLine(prevBlue, currentBlue, m, j, new int[]{0, 0, 255});
      prevRed = currentRed;
      prevGreen = currentGreen;
      prevBlue = currentBlue;
    }
  }

  /**
   * Helper method to draw lines on the histogram.
   *
   * @param start starting point of line.
   * @param end   ending point of line.
   * @param m     ImageModel.
   * @param index index.
   * @param rgb   rgb values.
   */
  private void drawLine(int start, int end, ImageModel m,
                        int index, int[] rgb) {
    if (start < end) {
      for (int i = start; i <= end; i++) {
        m.setPixelValue(i, index, 0, rgb[0]);
        m.setPixelValue(i, index, 1, rgb[1]);
        m.setPixelValue(i, index, 2, rgb[2]);
      }
    } else {
      for (int i = start; i >= end; i--) {
        m.setPixelValue(i, index, 0, rgb[0]);
        m.setPixelValue(i, index, 1, rgb[1]);
        m.setPixelValue(i, index, 2, rgb[2]);
      }
    }
  }

  /**
   * Method to apply color correction on a given image.
   *
   * @param currentImageName name of the image to be color corrected.
   * @param newImageName     color corrected image.
   * @return true if operation executed successfully, else false.
   */
  @Override
  public boolean colorCorrection(String currentImageName,
                                 String newImageName) {
    ImageModel oldImage = this.imageMap.get(currentImageName);
    ImageModel newImage = this.getNewImageModel(oldImage);
    int[][][] matrix = oldImage.getPixelMatrix();
    int[] red = this.getFrequency(matrix, 0);
    int[] green = this.getFrequency(matrix, 1);
    int[] blue = this.getFrequency(matrix, 2);
    int redPeak = this.getPeak(red);
    int greenPeak = this.getPeak(green);
    int bluePeak = this.getPeak(blue);
    int averagePeak = (redPeak + bluePeak + greenPeak) / 3;
    this.setCorrectedValue(newImage, matrix, averagePeak, redPeak, greenPeak, bluePeak);
    this.imageMap.put(newImageName, newImage);
    return true;
  }

  /**
   * Method to get the peak.
   *
   * @param arr channel whose peak is needed.
   * @return peak.
   */
  protected int getPeak(int[] arr) {
    int value = 0;
    int index = 0;
    for (int i = 10; i <= 245; i++) {
      if (arr[i] > value) {
        value = arr[i];
        index = i;
      }
    }
    return index;
  }

  /**
   * Method to set the new corrected value.
   *
   * @param m      ImageModel.
   * @param matrix image matrix.
   * @param avg    average peak.
   * @param red    red peak.
   * @param green  green peak.
   * @param blue   blue peak.
   */
  private void setCorrectedValue(ImageModel m, int[][][] matrix, int avg,
                                 int red, int green, int blue) {
    for (int i = 0; i < m.getHeight(); i++) {
      for (int j = 0; j < m.getWidth(); j++) {
        int red_value = matrix[i][j][0] + (avg - red);
        int green_value = matrix[i][j][1] + (avg - green);
        int blue_value = matrix[i][j][2] + (avg - blue);
        m.setPixelValue(i, j, 0, red_value);
        m.setPixelValue(i, j, 1, green_value);
        m.setPixelValue(i, j, 2, blue_value);
      }
    }
  }

  /**
   * Method to apply levels adjustment operation on the given image.
   *
   * @param currentImageName name of the image to be level adjusted.
   * @param newImageName     new level adjusted image.
   * @param b                b parameter.
   * @param m                m parameter.
   * @param w                w parameter.
   * @return true if operation executed successfully, else false.
   */

  @Override
  public boolean levelAdjustment(String currentImageName, String newImageName,
                                 int b, int m, int w) {
    ImageModel oldImage = this.imageMap.get(currentImageName);
    ImageModel newImage = this.getNewImageModel(oldImage);
    double[] coeff = this.getCoefficient(b, m, w);
    this.applyAdjustment(newImage, oldImage, coeff);
    this.imageMap.put(newImageName, newImage);
    return true;
  }

  /**
   * Helper method to calculate the Aa, Ab, Ac coefficients.
   *
   * @param b b value.
   * @param m m value.
   * @param w w value.
   * @return array storing the coefficients.
   */
  protected double[] getCoefficient(int b, int m, int w) {
    int bSquare = b * b;
    int mSquare = m * m;
    int wSquare = w * w;
    int a = bSquare * (m - w) - b * (mSquare - wSquare) + w * m * (m - w);
    double aA = -b * (128 - 255) + 128 * w - 255 * m;
    double bA = bSquare * (128 - 255) + 255 * mSquare - 128 * wSquare;
    double cA = bSquare * (255 * m - 128 * w) - b * (255 * mSquare - 128 * wSquare);
    return new double[]{aA / a, bA / a, cA / a};
  }

  /**
   * Helper method to apply the adjusted values.
   *
   * @param n     new ImageModel.
   * @param o     old ImageModel.
   * @param coeff coefficient array.
   */
  private void applyAdjustment(ImageModel n, ImageModel o, double[] coeff) {
    for (int i = 0; i < n.getHeight(); i++) {
      for (int j = 0; j < n.getWidth(); j++) {
        for (int k = 0; k <= 2; k++) {
          int x = o.getPixelValue(i, j, k);
          int y = (int) (coeff[0] * x * x + coeff[1] * x + coeff[2]);
          n.setPixelValue(i, j, k, y);
        }
      }
    }
  }

  /**
   * Method to apply split view of an operation on the given image.
   * Following images support splitPreview.
   * Blur.
   * Sharpen.
   * Sepia.
   * Red/Green/Blue components.
   * Value/Luma/Intensity components.
   * Color Correction.
   * Levels Adjustment.
   *
   * @param currentImageName name of the image to be viewed in split preview.
   * @param newImageName     name of new image in split preview.
   * @param percentage       percentage of split to be applied.
   */
  @Override
  public void splitPreview(String currentImageName, String newImageName, double percentage) {
    ImageModel oldImage = this.imageMap.get(currentImageName);
    int new_height = oldImage.getHeight();
    int new_width = (int) (oldImage.getWidth() * (percentage / 100.0));
    ImageModel newImage = new ImageModel(new_height, new_width);
    for (int i = 0; i < new_height; i++) {
      for (int j = 0; j < new_width; j++) {
        for (int k = 0; k <= 2; k++) {
          newImage.setPixelValue(i, j, k, oldImage.getPixelValue(i, j, k));
        }
      }
    }
    this.imageMap.put(newImageName, newImage);
  }

  /**
   * Helper method to merge an operated image and its other original part.
   *
   * @param currentImageName name of the current image.
   * @param newImageName     name of the new image.
   * @return true if executed successfully, else false.
   */
  public boolean regain(String currentImageName, String temporaryImageName, String newImageName) {
    ImageModel oldImage = this.imageMap.get(currentImageName);
    ImageModel newImage = this.imageMap.get(temporaryImageName);
    ImageModel finalImage = this.getNewImageModel(oldImage);
    int width = newImage.getWidth();
    for (int i = 0; i < oldImage.getHeight(); i++) {
      for (int j = 0; j < oldImage.getWidth(); j++) {
        for (int k = 0; k <= 2; k++) {
          if (j < width) {
            finalImage.setPixelValue(i, j, k, newImage.getPixelValue(i, j, k));
          } else {
            finalImage.setPixelValue(i, j, k, oldImage.getPixelValue(i, j, k));
          }
        }
      }
    }
    this.imageMap.put(newImageName, finalImage);
    this.imageMap.remove(temporaryImageName);
    return true;
  }

}
