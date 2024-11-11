package controller;

import java.awt.image.BufferedImage;
import java.io.File;

import model.OperationsV2;
import view.IView;
import view.ViewHandler;


public class ImageApplicationFeatures implements Features {
  private final CommandHandler commandHandler;
  private final OperationsV2 model;
  private final IView view;


  public ImageApplicationFeatures(OperationsV2 model, IView view) {
    this.model = model;
    this.view = view;
    commandHandler = new CommandHandler(model, new ViewHandler(System.out));
  }


  private boolean checkImage(String imageName) {
    if (imageName == null) {
      view.showImageNotPresent();
      return false;
    }
    return true;
  }


  private BufferedImage histogram(String imageName) {
    String command = "histogram " + imageName + " " + imageName + "hist";
    commandHandler.readCommand(command.split(" "));
    return this.convertToDisplay(imageName + "hist");
  }

  private void displayImage(String name) {
    BufferedImage image = this.convertToDisplay(name);
    view.showImage(name, image);
    BufferedImage histogram = this.histogram(name);
    view.showHistogram(histogram);
  }


  private BufferedImage convertToDisplay(String name) {
    int[][][] arr = model.saveImage(name);
    int width = arr[0].length;
    int height = arr.length;
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int rgb = arr[i][j][0] << 16 | arr[i][j][1] << 8 |
                arr[i][j][2];
        img.setRGB(j, i, rgb);
      }
    }
    return img;
  }


  private void commandGenerator(String operation, String imageName) {
    String command = operation + " " + imageName + " " + imageName + "_" + operation;
    commandHandler.readCommand(command.split(" "));
    this.displayImage(imageName + "_" + operation);
  }

  @Override
  public void loadImage() {
    File path = view.getFilePath();
    if (path == null) {
      return;
    }

    if (!view.checkImage()) {
      return;
    }
    String temp = path.getName();
    String name = temp.substring(0, temp.lastIndexOf("."));
    String command = "load " + path.getAbsolutePath() + " " + name;
    commandHandler.readCommand(command.split(" "));
    this.displayImage(name);

  }


  @Override
  public void applySepia(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    this.commandGenerator("sepia", imageName);
  }

  @Override
  public void applyBlur(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    this.commandGenerator("blur", imageName);
  }

  @Override
  public void applySharp(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    this.commandGenerator("sharpen", imageName);
  }

  @Override
  public void applyHorizontalFlip(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    this.commandGenerator("horizontal-flip", imageName);
  }

  @Override
  public void applyVerticalFlip(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    this.commandGenerator("vertical-flip", imageName);
  }

  @Override
  public void applyColorCorrect(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    this.commandGenerator("color-correct", imageName);
  }

  @Override
  public void applyCompress(String imageName, double percentage) {

  }

  @Override
  public void applyBrightness(String imageName, int value) {

  }

  @Override
  public void applyLuma(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    this.commandGenerator("luma-component", imageName);
  }

  @Override
  public void applyValue(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    this.commandGenerator("value-component", imageName);
  }

  @Override
  public void applyIntensity(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    this.commandGenerator("intensity-component", imageName);
  }

  @Override
  public void applyRed(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    this.commandGenerator("red-component", imageName);
  }

  @Override
  public void applyGreen(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    this.commandGenerator("green-component", imageName);
  }

  @Override
  public void applyBlue(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    this.commandGenerator("blue-component", imageName);
  }

  @Override
  public void applyLevelAdjust(String imageName, int b, int m, int w) {

  }


}
