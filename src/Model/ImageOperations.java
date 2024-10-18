package Model;

import java.util.HashMap;
import java.util.Map;

/**
 * The ImageOperations class implements the {@link Operations} interface,
 * providing implementations for various image manipulation and
 * processing tasks.
 * <p>
 * This class also maintains a map that stores instances of all images
 * loaded and processed during the execution of the application,
 * allowing efficient access and manipulation throughout the
 * script's runtime.
 */

public class ImageOperations implements Operations {

  private final Map<String, ImageModel> imageMap;

  /**
   * Constructs an ImageOperation objet and initializes an
   * empty HashMap to store the image instances.
   * Map will be used to keep a track of all images which are
   * loaded and processed during execution.
   */
  public ImageOperations() {
    this.imageMap = new HashMap<>();
  }

  @Override
  public void loadImages(int[][][] matrix, String name) {
    ImageModel m = new ImageModel(matrix.length, matrix[0].length);
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        for (int k = 0; k <= 2; k++) {
          m.setPixelValue(i, j, k, matrix[i][j][k]);
        }
      }
    }
    this.imageMap.put(name, m);
  }

  @Override
  public boolean checkImage(String name) {
    return this.imageMap.containsKey(name);
  }

  @Override
  public int[][][] getImage(String name) {
    return this.imageMap.get(name).getPixelMatrix();
  }

  /**
   * Creates and returns a new ImageModel object with the dimensions
   * as of the old ImageModel provided as input parameter.
   *
   * @param old the existing ImageModel whose dimensions are to be
   *            copied to the new ImageModel.
   * @return a new ImageModel.
   */
  private ImageModel getNewImageModel(ImageModel old) {
    int height = old.getHeight();
    int width = old.getWidth();
    return new ImageModel(height, width);
  }

  @Override
  public void getColorComponent(String currentImage, String newImage, int channel) {
    ImageModel imageOld = this.imageMap.get(currentImage);
    ImageModel imageNew = this.getNewImageModel(imageOld);
    int height = imageNew.getHeight();
    int width = imageNew.getWidth();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        for (int k = 0; k <= 2; k++) {
          imageNew.setPixelValue(i, j, k, imageOld.getPixelValue(i, j, channel));
        }
      }
    }
    this.imageMap.put(newImage, imageNew);
  }

  /**
   * Method to obtain the value-brightness component from the specified
   * input image.
   * Value is obtained by calculating by the max value of each channel
   * at each pixel location.
   *
   * @param o the original ImageModel whose value component is to be
   *          obtained.
   * @param n the new ImageModel which stores the value component of
   *          the original ImageModel.
   */
  private void getValueComponent(ImageModel o, ImageModel n) {
    for (int i = 0; i < o.getHeight(); i++) {
      for (int j = 0; j < o.getWidth(); j++) {
        int value = Math.max(o.getPixelValue(i, j, 0), Math.max(o.getPixelValue(i, j, 1),
                o.getPixelValue(i, j, 2)));
        n.setPixelValue(i, j, 0, value);
        n.setPixelValue(i, j, 1, value);
        n.setPixelValue(i, j, 2, value);
      }
    }
  }

  /**
   * Method to obtain the intensity-brightness component from the specified
   * input image.
   * Intensity is obtained by calculating by the average value of each channel
   * at each pixel location.
   *
   * @param o the original ImageModel whose intensity component is to be
   *          obtained.
   * @param n the new ImageModel which stores the intensity component of
   *          the original ImageModel.
   */
  private void getIntensityComponent(ImageModel o, ImageModel n) {
    for (int i = 0; i < o.getHeight(); i++) {
      for (int j = 0; j < o.getWidth(); j++) {
        int value = (o.getPixelValue(i, j, 0) + o.getPixelValue(i, j, 1)
                + o.getPixelValue(i, j, 2)) / 3;
        n.setPixelValue(i, j, 0, value);
        n.setPixelValue(i, j, 1, value);
        n.setPixelValue(i, j, 2, value);
      }
    }
  }

  /**
   * Method to obtain the luma-brightness component from the specified
   * input image.
   * Luma is obtained by calculating by the weighted sum of each channel
   * at each pixel location.
   *
   * @param o the original ImageModel whose luma component is to be
   *          obtained.
   * @param n the new ImageModel which stores the luma component of
   *          the original ImageModel.
   */
  private void getLumaComponent(ImageModel o, ImageModel n) {
    for (int i = 0; i < o.getHeight(); i++) {
      for (int j = 0; j < o.getWidth(); j++) {
        double value = 0.2126 * o.getPixelValue(i, j, 0) + 0.7152 * o.getPixelValue(i, j, 1) +
                0.0722 * o.getPixelValue(i, j, 2);
        n.setPixelValue(i, j, 0, (int) value);
        n.setPixelValue(i, j, 1, (int) value);
        n.setPixelValue(i, j, 2, (int) value);
      }
    }
  }

  @Override
  public void getBrightnessComponent(String currentImage, String newImage, String handle) throws IllegalArgumentException {
    ImageModel imageOld = this.imageMap.get(currentImage);
    ImageModel imageNew = this.getNewImageModel(imageOld);
    switch (handle) {
      case "value-component":
        this.getValueComponent(imageOld, imageNew);
        break;
      case "intensity-component":
        this.getIntensityComponent(imageOld, imageNew);
        break;
      case "luma-component":
        this.getLumaComponent(imageOld, imageNew);
        break;
      default:
        throw new IllegalArgumentException("Invalid command provided.");
    }
    this.imageMap.put(newImage, imageNew);
  }

  @Override
  public void horizontalFlip(String currentImage, String newImage) {
    ImageModel imageOld = this.imageMap.get(currentImage);
    ImageModel imageNew = this.getNewImageModel(imageOld);
    int height = imageNew.getHeight();
    int width = imageNew.getWidth();
    for (int i = 0; i < height; i++) {
      int col = 0;
      for (int j = width - 1; j >= 0; j--) {
        for (int k = 0; k <= 2; k++) {
          imageNew.setPixelValue(i, col, k, imageOld.getPixelValue(i, j, k));
        }
        col = col + 1;
      }
    }
    this.imageMap.put(newImage, imageNew);
  }

  @Override
  public void verticalFlip(String currentImage, String newImage) {
    ImageModel imageOld = this.imageMap.get(currentImage);
    ImageModel imageNew = this.getNewImageModel(imageOld);
    int height = imageNew.getHeight();
    int width = imageNew.getWidth();
    int row = 0;
    for (int i = height - 1; i >= 0; i--) {
      for (int j = 0; j < width; j++) {
        for (int k = 0; k <= 2; k++) {
          imageNew.setPixelValue(row, j, k, imageOld.getPixelValue(i, j, k));
        }
      }
      row = row + 1;
    }
    this.imageMap.put(newImage, imageNew);
  }

  @Override
  public void brighten(String currentImage, String newImage, int intensity) {
    ImageModel imageOld = this.imageMap.get(currentImage);
    ImageModel imageNew = this.getNewImageModel(imageOld);
    int height = imageNew.getHeight();
    int width = imageNew.getWidth();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        for (int k = 0; k <= 2; k++) {
          int value = imageOld.getPixelValue(i, j, k) + intensity;
          imageNew.setPixelValue(i, j, k, value);
        }
      }
    }
    this.imageMap.put(newImage, imageNew);
  }

  @Override
  public void splitRGB(String currentImage, String newRedImage, String newGreenImage, String newBlueImage) {
    this.getColorComponent(currentImage, newRedImage, 0);
    this.getColorComponent(currentImage, newGreenImage, 1);
    this.getColorComponent(currentImage, newBlueImage, 2);
  }

  @Override
  public void combineRGB(String redImage, String greenImage, String blueImage, String newImage)
          throws IllegalArgumentException {

    ImageModel red = this.imageMap.get(redImage);
    ImageModel green = this.imageMap.get(greenImage);
    ImageModel blue = this.imageMap.get(blueImage);

    if (red.getHeight() != green.getHeight() || red.getHeight() != blue.getHeight()
            || red.getWidth() != green.getWidth() || red.getWidth() != blue.getWidth()) {
      throw new IllegalArgumentException("Images to be combined do not have same dimensions");
    }

    ImageModel imageNew = this.getNewImageModel(red);
    for (int i = 0; i < red.getHeight(); i++) {
      for (int j = 0; j < red.getWidth(); j++) {
        imageNew.setPixelValue(i, j, 0, red.getPixelValue(i, j, 0));
        imageNew.setPixelValue(i, j, 1, green.getPixelValue(i, j, 0));
        imageNew.setPixelValue(i, j, 2, blue.getPixelValue(i, j, 0));
      }
    }
    this.imageMap.put(newImage, imageNew);
  }


  /**
   * Apply a filter to the ImageModel and store the result in the new
   * ImageModel.
   * For each pixel in the original Image we multiply filter element-wise
   * with the neighborhood pixel and the result is added to get the new
   * pixel value.
   *
   * @param filter a 2-D array which is the filter to be applied.
   * @param old    the original ImageModel on which filter is to
   *               be applied.
   * @param n      the new ImageModel where the new pixel value
   *               is stored.
   */

  private void applyFilter(double[][] filter, ImageModel old, ImageModel n) {
    int filterSize = filter.length / 2;
    int height = old.getHeight();
    int width = old.getWidth();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        for (int k = 0; k <= 2; k++) {
          double value = 0;
          for (int a = -filterSize; a <= filterSize; a++) {
            for (int b = -filterSize; b <= filterSize; b++) {
              int x = i + a;
              int y = j + b;
              if (x >= 0 && y >= 0 && y < width && x < height) {
                value = value + filter[a + filterSize][b + filterSize] *
                        old.getPixelValue(i + a, j + b, k);
              }
            }
          }
          n.setPixelValue(i, j, k, (int) value);
        }
      }
    }
  }

  @Override
  public void blur(String currentImage, String newImage) {
    ImageModel imageOld = this.imageMap.get(currentImage);
    ImageModel imageNew = this.getNewImageModel(imageOld);
    double[][] filter = {
            {0.0625, 0.125, 0.0625},
            {0.125, 0.25, 0.125},
            {0.0625, 0.125, 0.0625}
    };
    this.applyFilter(filter, imageOld, imageNew);
    this.imageMap.put(newImage, imageNew);
  }

  @Override
  public void sharpen(String currentImage, String newImage) {
    ImageModel imageOld = this.imageMap.get(currentImage);
    ImageModel imageNew = this.getNewImageModel(imageOld);
    double[][] filter = {
            {-0.125, -0.125, -0.125, -0.125, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, 0.25, 1, 0.25, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, -0.125, -0.125, -0.125, -0.125}
    };
    this.applyFilter(filter, imageOld, imageNew);
    this.imageMap.put(newImage, imageNew);
  }

  @Override
  public void sepia(String currentImage, String newImage) {
    ImageModel imageOld = this.imageMap.get(currentImage);
    ImageModel imageNew = this.getNewImageModel(imageOld);
    int height = imageNew.getHeight();
    int width = imageNew.getWidth();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = imageOld.getPixelValue(i, j, 0);
        int g = imageOld.getPixelValue(i, j, 1);
        int b = imageOld.getPixelValue(i, j, 2);
        int newr = (int) (0.393 * r + 0.769 * g + 0.189 * b);
        int newg = (int) (0.349 * r + 0.686 * g + 0.168 * b);
        int newb = (int) (0.272 * r + 0.534 * g + 0.131 * b);
        imageNew.setPixelValue(i, j, 0, newr);
        imageNew.setPixelValue(i, j, 1, newg);
        imageNew.setPixelValue(i, j, 2, newb);
      }
    }
    this.imageMap.put(newImage, imageNew);
  }
}
