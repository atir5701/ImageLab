package model;

import java.util.HashMap;
import java.util.Map;

/**
 * The ImageOperations class implements the {@link Operations} interface,
 * providing implementations for various image manipulation and
 * processing tasks.
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

  /**
   * Loads an image from a 3D integer array representing pixel values
   * and stores it in new object of Image model.
   * In the 3-D Matrix :
   * The first dimension is the height of the image.
   * The second dimension is the width of the image.
   * The third dimension is the channel associated with the
   * image.
   *
   * @param matrix a 3D integer array containing pixel
   *               values for the image.
   *               Each pixel is expected to have three
   *               components (R, G, B).
   * @param name   the name under which the image will be
   *               stored in the image map.
   */
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

  /**
   * Checks whether an image with the specified name exists
   * in the image map.
   *
   * @param name the name of the image to check for
   *             in the image map.
   * @return true if the image exists; false otherwise.
   */
  @Override
  public boolean checkImage(String name) {
    return this.imageMap.containsKey(name);
  }

  /**
   * Obtain the pixel matrix of the image whose name is
   * provided in the function parameter.
   *
   * @param name the name of the image whose pixel matrix
   *             if to be obtained.
   * @return a 3-D array representing the pixel values of image.
   */
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

  /**
   * Obtain the specific color component from the specified image.
   * The color component can be either Red, Green or Blue.
   * In the resulting image, all three channels for each pixel will have the
   * same value, which corresponds to the value of the selected color component
   * from the original image.
   *
   * @param currentImage the name of the original image from which color
   *                     component is to be obtained.
   * @param newImage     the name of the new image created from the specified
   *                     color component.
   * @param channel      the color component which is to be extracted.
   *                     0 is for Red; 1 is for Green; 2 is for Blue.
   */
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
        int value = o.getPixelValue(i, j, 0);
        value = Math.max(o.getPixelValue(i, j, 1), value);
        value = Math.max(o.getPixelValue(i, j, 2), value);

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
        int value = o.getPixelValue(i, j, 0);
        value += o.getPixelValue(i, j, 1);
        value += o.getPixelValue(i, j, 2);
        value /= 3;
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
        double value = 0.2126 * o.getPixelValue(i, j, 0);
        value += 0.7152 * o.getPixelValue(i, j, 1);
        value += 0.0722 * o.getPixelValue(i, j, 2);
        n.setPixelValue(i, j, 0, (int) value);
        n.setPixelValue(i, j, 1, (int) value);
        n.setPixelValue(i, j, 2, (int) value);
      }
    }
  }

  /**
   * Obtain the specific brightness component from the specified image.
   * The brightness component can be value, luma or intensity.
   * Value is the max value of the three component for each pixel.
   * Intensity is the average of three component for each pixel.
   * Luma is weighted sum of three component for each pixel.
   *
   * @param currentImage the name of the original image  from which
   *                     specified brightness component is to be
   *                     obtained.
   * @param newImage     the name of the new image created from specified
   *                     brightness component.
   * @param handle       specifies which brightness component to use.
   *                     Can be value-component; luma-component;
   *                     intensity-component.
   */
  @Override
  public void getBrightnessComponent(String currentImage, String newImage,
                                     String handle) throws IllegalArgumentException {
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

  /**
   * Perform horizontal flip of the specified image and create a new image
   * with flipped pixel values.
   * Horizontal flip is basically the mirror image of the original image.
   *
   * @param currentImage the name of the original image which is to be
   *                     horizontally flipped.
   * @param newImage     the name of the new image created by horizontally
   *                     flipping the original image.
   */
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

  /**
   * Perform vertical flip of the specified image and create a new image
   * with flipped pixel values.
   * Vertical flip is basically making an image up-side down.
   *
   * @param currentImage the name of the original image which is to be
   *                     vertically flipped.
   * @param newImage     the name of the new image created by vertically
   *                     flipping the original image.
   */
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

  /**
   * Brightens the specified image by adding the pixel's color value
   * to a specified value.
   * The value can be positive for brightening and can be negative
   * for darkening the image.
   *
   * @param currentImage the name of the original image whose pixels
   *                     value is to be changed.
   * @param newImage     the name of the new image obtained by changing
   *                     the pixel value in the original image with the
   *                     intensity value provided.
   * @param intensity    the value which is to be added to each pixel. Can
   *                     be positive or negative.
   */
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

  /**
   * Split the specified image into its constituent color component images.
   * So extracting a red, green and blue channel images from the specified
   * image.
   *
   * @param currentImage  the name of the original image which is to be split
   *                      into the constituent color component channels.
   * @param newRedImage   the new image obtained from the red-channel of the
   *                      original image.
   * @param newGreenImage the new image obtained from the green-channel of the
   *                      original image.
   * @param newBlueImage  the new image obtained from the blue-channel of the
   *                      original image.
   */
  @Override
  public void splitRGB(String currentImage, String newRedImage,
                       String newGreenImage, String newBlueImage) {
    this.getColorComponent(currentImage, newRedImage, 0);
    this.getColorComponent(currentImage, newGreenImage, 1);
    this.getColorComponent(currentImage, newBlueImage, 2);
  }

  /**
   * Combine the red, green and blue color component from three specified images
   * to obtain a new image with the combined RGB value.
   * So in the new image the red channel has all pixel value from any channel in the
   * red image. The green channel has all pixel value from any channel of the green image.
   * The blue channel has all pixel value from any channel of the blue image.
   *
   * @param redImage   the name of the image which makes red channel.
   * @param greenImage the name of the image which makes green channel.
   * @param blueImage  the name of the image which makes blue channel.
   * @param newImage   the name of the new image formed from combination
   *                   of the three images.
   * @throws IllegalArgumentException if the provided images do not have same dimensions.
   */
  @Override
  public void combineRGB(String redImage, String greenImage,
                         String blueImage, String newImage) throws IllegalArgumentException {

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
                value += filter[a + filterSize][b + filterSize]
                        * old.getPixelValue(i + a, j + b, k);
              }
            }
          }
          n.setPixelValue(i, j, k, (int) value);
        }
      }
    }
  }

  /**
   * Blur the specified image by applying a suitable kernel over the
   * entire image. Gaussian blue filer is used, after applying the filer
   * a new blurred image is obtained.
   *
   * @param currentImage the name of the original image which is to be
   *                     blurred.
   * @param newImage     the name of the new image obtained after blurring
   *                     the original image.
   */
  @Override
  public void blur(String currentImage, String newImage) {
    ImageModel imageOld = this.imageMap.get(currentImage);
    ImageModel imageNew = this.getNewImageModel(imageOld);
    double[][] filter = {{0.0625, 0.125, 0.0625}, {0.125, 0.25, 0.125}, {0.0625, 0.125, 0.0625}};
    this.applyFilter(filter, imageOld, imageNew);
    this.imageMap.put(newImage, imageNew);
  }

  /**
   * Sharpen the specified image by applying a suitable kernel over the
   * entire image. Sharpens the edges of the image.
   *
   * @param currentImage the name of the original image which is to be
   *                     Sharpen.
   * @param newImage     the name of the new image obtained after sharpening
   *                     the original image.
   */
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

  /**
   * Applying a Sepia color transformation on the image to give it a
   * characteristic reddish brown tone. By applying this the image tends
   * to appear as the photograph taken in the 19th and early 20th century.
   *
   * @param currentImage the name of the original image on which the sepia
   *                     color transformation is to be applied.
   * @param newImage     the name of the new image obtained after applying
   *                     sepia transformation on th original image.
   */
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
