package model;

import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


import static org.junit.Assert.assertEquals;

/**
 * This is the Class to perform testing on the
 * model of the architecture.
 */

public class ImageOperationsTest {
  Operations operations;

  @Before
  public void setUp() {
    this.operations = new ImageOperations();
    String file = "D:\\Northeastern\\PDP\\Assignment-4-MVC" +
            "\\images\\manhattan-small.png";
    int[][][] org = this.getMatrix(file);
    this.operations.loadImage(org, "man");
  }


  /**
   * Method to get the matrix representation of an input
   * image.
   *
   * @param file the file path where image is present.
   * @return a 3-d matrix representing the image.
   */
  private int[][][] getMatrix(String file) {
    File f = new File(file);
    int[][][] arr = null;
    try {
      BufferedImage img = ImageIO.read(f);
      int height = img.getHeight();
      int width = img.getWidth();
      arr = new int[height][width][3];
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int rgb = img.getRGB(j, i);
          arr[i][j][0] = (rgb >> 16) & 0xff;
          arr[i][j][1] = (rgb >> 8) & 0xff;
          arr[i][j][2] = (rgb) & 0xff;
        }
      }
    } catch (IOException e) {
      System.out.println("File Not Found");

    }
    return arr;
  }

  /**
   * Test Case to check if image gets brighten
   * with a suitable value.
   */
  @Test
  public void checkBrightness() {
    this.operations.brighten("man", "man-bright-50", 50);
    int[][][] manNew = this.operations.saveImage("man-bright-50");
    String file = "D:\\Northeastern\\PDP\\Assignment-4-MVC\\images" +
            "\\manhattan-small-brighter-by-50.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }


  /**
   * Test Case to check if image get darken when a
   * suitable negative value is given to the brightness
   * method.
   */
  @Test
  public void checkDarkeness() {
    this.operations.brighten("man", "man-darken-50", -50);
    int[][][] manNew = this.operations.saveImage("man-darken-50");
    String file2 = "D:\\Northeastern\\PDP\\Assignment-4-MVC\\images" +
            "\\manhattan-small-darker-by-50.png";
    int[][][] compare = this.getMatrix(file2);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }


  /**
   * Test Case to check if image gets vertically
   * flipped correctly or not.
   */
  @Test
  public void checkVerticalFlip() {
    this.operations.verticalFlip("man", "man-vflip");
    int[][][] manNew = this.operations.saveImage("man-vflip");
    String file2 = "D:\\Northeastern\\PDP\\Assignment-4-MVC\\images" +
            "\\manhattan-small-vertical.png";
    int[][][] compare = this.getMatrix(file2);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if image gets horizontally
   * flipped correctly or not.
   */
  @Test
  public void checkHorizontalFlip() {
    this.operations.horizontalFlip("man", "man-hflip");
    int[][][] manNew = this.operations.saveImage("man-hflip");
    String file2 = "D:\\Northeastern\\PDP\\Assignment-4-MVC\\images" +
            "\\manhattan-small-horizontal.png";
    int[][][] compare = this.getMatrix(file2);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if image get vertical flip
   * of a horizontal flipped image.
   */
  @Test
  public void checkHorizontalVerticalFlip() {
    this.operations.horizontalFlip("man", "man-hflip");
    this.operations.verticalFlip("man-hflip", "man-hv");
    int[][][] manNew = this.operations.saveImage("man-hv");
    String file2 = "D:\\Northeastern\\PDP\\Assignment-4-MVC\\images" +
            "\\manhattan-small-horizontal-vertical.png";
    int[][][] compare = this.getMatrix(file2);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if image get horizontal flip
   * of a vertically flipped image.
   */
  @Test
  public void checkVerticalHorizontalFlip() {
    this.operations.verticalFlip("man", "man-vflip");
    this.operations.horizontalFlip("man-vflip", "man-vh");
    int[][][] manNew = this.operations.saveImage("man-vh");
    String file = "D:\\Northeastern\\PDP\\Assignment-4-MVC\\images" +
            "\\manhattan-small-vertical-horizontal.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check sepia transformation of
   * image.
   */
  @Test
  public void checkSepia() {
    this.operations.sepia("man", "man-sepia");
    int[][][] manNew = this.operations.saveImage("man-sepia");
    String file = "D:\\Northeastern\\PDP\\Assignment-4-MVC\\images" +
            "\\manhattan-small-sepia.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check blur operation applied
   * on the image.
   */
  @Test
  public void checkBlur() {
    this.operations.blur("man", "man-blur");
    int[][][] manNew = this.operations.saveImage("man-blur");
    String file = "D:\\Northeastern\\PDP\\Assignment-4-MVC\\images" +
            "\\manhattan-small-blur.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check Sharpen operation applied
   * on the image.
   */
  @Test
  public void checkSharpen() {
    this.operations.sharpen("man", "man-sharp");
    int[][][] manNew = this.operations.saveImage("man-sharp");
    String file = "D:\\Northeastern\\PDP\\Assignment-4-MVC\\images" +
            "\\manhattan-small-sharper.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if value component
   * of image is generated correctly.
   */
  @Test
  public void checkValueComponent() {
    this.operations.getBrightnessComponent("man",
            "man-value", "value-component");
    int[][][] manNew = this.operations.saveImage("man-value");
    String file = "D:\\Northeastern\\PDP\\Assignment-4-MVC\\images" +
            "\\manhattan-small-value-greyscale.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if luma component
   * of image is generated correctly.
   */
  @Test
  public void checkLumaComponent() {
    this.operations.getBrightnessComponent("man",
            "man-luma", "luma-component");
    int[][][] manNew = this.operations.saveImage("man-luma");
    String file = "D:\\Northeastern\\PDP\\Assignment-4-MVC\\images" +
            "\\manhattan-small-luma-greyscale.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check the intensity function
   * is working correctly or not.
   */

  @Test
  public void checkIntensityComponent() {
    this.operations.getBrightnessComponent("man",
            "man-intensity", "intensity-component");
    int[][][] manNew = this.operations.saveImage("man-intensity");
    String file = "D:\\Northeastern\\PDP\\Assignment-4-MVC\\images" +
            "\\manhattan-small-intensity-greyscale.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          int diff = (compare[i][j][k] - manNew[i][j][k]);
          if (diff == -1) {
            diff = 0;
          }
          assertEquals(0, diff);
        }
      }
    }
  }

  /**
   * Test Case to get the red component from the image.
   */
  @Test
  public void checkRedComponent() {
    this.operations.getColorComponent("man",
            "man-red", 0);
    int[][][] manNew = this.operations.saveImage("man-red");
    String file = "D:\\Northeastern\\PDP\\Assignment-4-MVC\\images" +
            "\\manhattan-small-red.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to get the blue component from the image.
   */
  @Test
  public void checkBlueComponent() {
    this.operations.getColorComponent("man",
            "man-blue", 2);
    int[][][] manNew = this.operations.saveImage("man-blue");
    String file = "D:\\Northeastern\\PDP\\Assignment-4-MVC\\images" +
            "\\manhattan-small-blue.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to get the green component from the image.
   */
  @Test
  public void checkGreenComponent() {
    this.operations.getColorComponent("man",
            "man-green", 1);
    int[][][] manNew = this.operations.saveImage("man-green");
    String file = "D:\\Northeastern\\PDP\\Assignment-4-MVC\\images" +
            "\\manhattan-small-green.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if the rgb split
   * of image takes place correctly.
   */
  @Test
  public void checkRGBSplit() {
    this.operations.splitRGB("man",
            "man-red", "man-green", "man-blue");
    int[][][] manNew = this.operations.saveImage("man-red");
    String file = "D:\\Northeastern\\PDP\\Assignment-4-MVC\\images" +
            "\\manhattan-small-red.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
    manNew = this.operations.saveImage("man-green");
    file = "D:\\Northeastern\\PDP\\Assignment-4-MVC\\images" +
            "\\manhattan-small-green.png";
    compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }

    manNew = this.operations.saveImage("man-blue");
    file = "D:\\Northeastern\\PDP\\Assignment-4-MVC\\images" +
            "\\manhattan-small-blue.png";
    compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if the RGB Combine
   * works correctly.
   */

  @Test
  public void checkRGBCombine() {
    this.operations.splitRGB("man",
            "man-red", "man-green", "man-blue");
    this.operations.combineRGB("man-red", "man-green",
            "man-blue", "manNew");
    int[][][] manNew = this.operations.saveImage("manNew");
    String file = "D:\\Northeastern\\PDP\\Assignment-4-MVC\\images" +
            "\\manhattan-small.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if the RGB Combine
   * throws error if image are of different size.
   */

  @Test(expected = IllegalArgumentException.class)
  public void checkRGBCombineWithDifferentSize() {
    this.operations.splitRGB("man",
            "man-red", "man-green", "man-blue");
    String file1 = "D:\\Northeastern\\PDP\\Assignment-4-MVC\\images" +
            "\\bird.png";
    int[][][] newImg = this.getMatrix(file1);
    this.operations.loadImage(newImg, "bird");
    this.operations.splitRGB("bird", "bird-red",
            "bird-green", "bird-blue");
    this.operations.combineRGB("bird-blue", "man-green",
            "man-blue", "new-Image");
  }

  /**
   * Test Case to check if the image override
   * when loaded with same name.
   */
  @Test
  public void checkOverridingOfImage() {
    String file1 = "D:\\Northeastern\\PDP\\Assignment-4-MVC\\images" +
            "\\bird.png";
    int[][][] newImg = this.getMatrix(file1);
    this.operations.loadImage(newImg, "man");
    int[][][] reference = this.getMatrix(file1);
    int[][][] manNew = this.operations.saveImage("man");
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(reference[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }
}