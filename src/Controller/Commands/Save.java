package controller.commands;

import model.Operations;


import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javax.imageio.ImageIO;

/**
 * A class that performs the Save operation on an
 * image. Image needs to be saves in a specified file
 * path with the suitable extension.
 */

public class Save extends AbstractCommandExecuter {
  private final String filePath;
  private final String currentImageName;
  private final String extension;
  private final Map<String, Consumer<Operations>> savingImage;

  /**
   * Construct a Save command object.
   * Validate the command length and initialize the image
   * names.
   *
   * @param cmd           the command array obtained by splitting
   *                      input using space.
   * @param commandLength the expected length of command array.
   */

  public Save(String[] cmd, int commandLength) {
    this.validCommandLength(cmd.length, commandLength);
    this.filePath = cmd[1];
    this.extension = this.filePath.substring(this.filePath.lastIndexOf(".") + 1).toLowerCase();
    this.currentImageName = cmd[2];
    this.savingImage = new HashMap<>();
    this.savingImage.put("png", this::savePngJpeg);
    this.savingImage.put("jpeg", this::savePngJpeg);
    this.savingImage.put("ppm", this::savePPM);
  }

  /**
   * Execute the save operation on the current image.
   * The method first check if the image on which operation
   * is to be done in present in the system or not.
   * On the basis of the suitable extension in which the image
   * is to be stored a suitable private method is called.
   *
   * @param operations The operation instance which is
   *                   used to call the suitable method
   *                   which is to be executed on the input
   *                   image.
   */
  @Override
  public void execute(Operations operations) {
    this.imageCheck(operations, this.currentImageName);
    Consumer<Operations> cmd = this.savingImage.get(this.extension);
    if (cmd == null) {
      throw new IllegalArgumentException("This extension is not Supported");
    }
    cmd.accept(operations);
  }

  /**
   * Helper method used to save image in the PNG or JPEG
   * format.
   * Convert the 3-d image pixel matrix into a BufferedImage
   * and then write it at the suitable path.
   *
   * @param operations The operation instance which is
   *                   used to get the image which is to
   *                   be saved.
   */

  private void savePngJpeg(Operations operations) {
    int[][][] arr = operations.getImage(this.currentImageName);
    File file = new File(this.filePath);
    int width = arr[0].length;
    int height = arr.length;
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    try {
      for (int i = 0; i < arr.length; i++) {
        for (int j = 0; j < arr[i].length; j++) {
          int rgb = arr[i][j][0] << 16 | arr[i][j][1] << 8 |
                  arr[i][j][2];
          img.setRGB(j, i, rgb);
        }
      }
      ImageIO.write(img, extension, file);
    } catch (IOException e) {
      System.out.println("File Not Found");
    }

  }

  /**
   * Helper method used to save image in the PPM
   * format.
   * Writes the image data to a PPM file in ASCII
   * format (P3 format), including the image's
   * dimensions and pixel data.
   *
   * @param operations The operation instance which is
   *                   used to get the image which is to
   *                   be saved.
   */
  private void savePPM(Operations operations) {
    int[][][] arr = operations.getImage(this.currentImageName);
    try {
      File output = new File(filePath);
      BufferedWriter bw = new BufferedWriter(new FileWriter(output));
      bw.write("P3\n");
      bw.write("#" + this.currentImageName + ".PPM Image\n");
      bw.write(arr[0].length + " " + arr.length + "\n");
      bw.write("255\n");

      for (int i = 0; i < arr.length; i++) {
        for (int j = 0; j < arr[i].length; j++) {
          bw.write(arr[i][j][0] + " " + arr[i][j][1] + " " + arr[i][j][2] + " ");
        }
        bw.write("\n");
      }
      bw.close();
    } catch (IOException e) {
      System.out.println("File Not Found");
    }
  }

}
