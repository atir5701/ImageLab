package controller.commands;

import model.Operations;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

import javax.imageio.ImageIO;

/**
 * A class that performs the load operation on an
 * image. Class is responsible for loading images
 * from file path. Supports images with JPEG,PNG and
 * PPM formats only.
 */

public class Load extends AbstractCommandExecuter {
  private final String filePath;
  private final String currentImageName;
  private final String extension;
  private final Map<String, Consumer<Operations>> loadingImage;

  /**
   * Construct a load command object.
   * Validate the command length and initialize the image
   * names.
   *
   * @param cmd           the command array obtained by splitting
   *                      input using space.
   * @param commandLength the expected length of command array.
   */
  public Load(String[] cmd, int commandLength) {
    this.validCommandLength(cmd.length, commandLength);
    this.filePath = cmd[1];
    this.extension = this.filePath.substring(this.filePath.lastIndexOf(".") + 1).toLowerCase();
    this.currentImageName = cmd[2];
    this.loadingImage = new HashMap<>();
    this.loadingImage.put("png", this::loadPngJpeg);
    this.loadingImage.put("jpeg", this::loadPngJpeg);
    this.loadingImage.put("ppm", this::loadPPM);
  }

  /**
   * Execute the load operation.
   * The method checks the file extension and load the
   * image accordingly.
   *
   * @param operations The operation instance which is
   *                   used to call the suitable method
   *                   which is to be executed on the input
   *                   image.
   */
  @Override
  public void execute(Operations operations) throws IllegalArgumentException {
    Consumer<Operations> cmd = this.loadingImage.get(this.extension);
    if (cmd == null) {
      throw new IllegalArgumentException("This extension is not Supported");
    }
    cmd.accept(operations);
  }

  /**
   * Helper Method which is used to load the PNG or
   * JPEG format images by reading the file and converting
   * the pixel data into a 3-d matrix.
   * Method throws IOException if the filepath is incorrect.
   *
   * @param operations the operation interface to load the
   *                   image.
   */

  private void loadPngJpeg(Operations operations) {
    File file = new File(this.filePath);
    try {
      BufferedImage img = ImageIO.read(file);
      int height = img.getHeight();
      int width = img.getWidth();
      int[][][] arr = new int[height][width][3];
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int rgb = img.getRGB(j, i);
          arr[i][j][0] = (rgb >> 16) & 0xff;
          arr[i][j][1] = (rgb >> 8) & 0xff;
          arr[i][j][2] = (rgb) & 0xff;
        }
      }
      operations.loadImages(arr, this.currentImageName);
    } catch (IOException e) {
      throw new RuntimeException("Filepath provided is incorrect");
    }
  }

  /**
   * Helper method used to load a PPM image by reading the file and
   * converting the pixel data into a 3-d array.
   * PPM format is assumed to be a plain text with "P3" format.
   * Method throws IOException if the filepath is incorrect.
   *
   * @param operations operation interface used to load image.
   */
  private void loadPPM(Operations operations) {
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(this.filePath));
    } catch (IOException e) {
      throw new RuntimeException("Filepath provided is incorrect.");
    }
    StringBuilder builder = new StringBuilder();
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }
    sc = new Scanner(builder.toString());
    String token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3.");
      return;
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();
    int[][][] arr = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        arr[i][j][0] = sc.nextInt();
        arr[i][j][1] = sc.nextInt();
        arr[i][j][2] = sc.nextInt();
      }
    }
    operations.loadImages(arr, this.currentImageName);
  }
}
