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
    this.commandHandler = new CommandHandler(model, new ViewHandler(System.out));
  }


  private boolean checkImage(String imageName) {
    if (imageName == null) {
      this.view.showImageNotPresent();
      return false;
    }
    return true;
  }


  private BufferedImage histogram(String imageName) {
    String command = "histogram " + imageName + " " + imageName + "hist";
    this.commandHandler.readCommand(command.split(" "));
    return this.convertToDisplay(imageName + "hist");
  }

  private void displayImage(String name) {
    BufferedImage image = this.convertToDisplay(name);
    this.view.showImage(name, image);
    BufferedImage histogram = this.histogram(name);
    this.view.showHistogram(histogram);
  }

  private void displaySplitImage(String name) {
    BufferedImage image = this.convertToDisplay(name);
    this.view.showSplitImage(image);
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

  @Override
  public void commandGenerator(String operation, String imageName) {
    String endName = operation;
    if (operation.length() != 1) {
      String[] temp = operation.split(" ");
      endName = temp[0];
    }
    String command = operation + " " + imageName + " " + imageName + "_" + endName;

    this.commandHandler.readCommand(command.split(" "));
    this.displayImage(imageName + "_" + endName);
  }

  private void commandGeneratorSplit(String operation, String percentage, String imageName) {
    String command = operation + " " + imageName + " " + imageName + "_" + operation
            + "_Split" + " split " + percentage;
    System.out.println(command);
    this.commandHandler.readCommand(command.split(" "));
    this.displaySplitImage(imageName + "_" + operation + "_Split");
  }

  @Override
  public void loadImage() {
    File path = this.view.getFilePath();
    if (path == null) {
      return;
    }
    if (!this.view.checkImage()) {
      return;
    }
    String temp = path.getName();
    String name = temp.substring(0, temp.lastIndexOf("."));
    String command = "load " + path.getAbsolutePath() + " " + name;
    this.commandHandler.readCommand(command.split(" "));
    this.displayImage(name);
  }

  @Override
  public void saveImage(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    File path = this.view.getSaveFilePath();
    if (path != null) {
      String command = "save " + path.getAbsolutePath() + " " + imageName;
      this.commandHandler.readCommand(command.split(" "));
      this.view.showSaveSuccess();
    }
  }

  @Override
  public void applySepia(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    this.view.splitPreview("sepia");
  }

  @Override
  public void applyBlur(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    this.view.splitPreview("blur");
  }

  @Override
  public void applySharp(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    this.view.splitPreview("sharpen");
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
    this.view.splitPreview("color-correct");
  }

  @Override
  public void applyLevelAdjust(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    this.view.LevelAdjust();
  }

  @Override
  public void getLevelAdjust(String imageName, String bValue, String mValue, String wValue,
                             String percentage) {

    if (!checkLevelAdjustProp(bValue, mValue, wValue)) {
      return;
    }
    double value;
    if(percentage.isEmpty()) {
      value = 100.;
    }else{
      if(!percentageCheck(percentage)) {
        return;
      }else{
        value = Double.valueOf(percentage);
      }
    }
    int b = Integer.parseInt(bValue);
    int m = Integer.parseInt(mValue);
    int w = Integer.parseInt(wValue);
    String command = "levels-adjust " + b + " " + m + " " + w + " " + imageName + " " +
            imageName + "_adjusted " +
            "split " + value;
    this.commandHandler.readCommand(command.split(" "));
    this.displaySplitImage(imageName + "_adjusted");
  }

  private boolean checkLevelAdjustProp(String bValue, String mValue, String wValue) {
    try {
      int b = Integer.parseInt(bValue);
      int m = Integer.parseInt(mValue);
      int w = Integer.parseInt(wValue);
      if (b < 0 || b > 255 || m < 0 || m > 255 || w < 0 || w > 255) {
        this.view.showInvalidRangeError();
        return false;
      }
      if (!(b < m && m < w)) {
        this.view.showOrderError();
        return false;
      }
      return true;
    } catch (NumberFormatException e) {
      this.view.showNullValueError();
    }
    return false;
  }

  @Override
  public boolean generateLevelAdjust(String bValue, String mValue, String wValue, String imageName) {
    if (!checkLevelAdjustProp(bValue, mValue, wValue)) {
      return false;
    }
    int b = Integer.parseInt(bValue);
    int m = Integer.parseInt(mValue);
    int w = Integer.parseInt(wValue);
    this.commandGenerator("levels-adjust " + b + " " + m + " " + w, imageName);
    return true;
  }

  @Override
  public void applyCompress(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    view.compressImage();
  }

  @Override
  public void getCompress(String percentage, String imageName) {
    if (!this.percentageCheck(percentage)) {
      return;
    }
    String command = "compress" + " " + percentage + " " + imageName + " "
            + imageName + "_Compress";
    this.commandHandler.readCommand(command.split(" "));
    this.displaySplitImage(imageName + "_Compress");
  }

  @Override
  public boolean generateCompress(String percentage, String imageName) {
    if (!this.percentageCheck(percentage)) {
      return false;
    }
    this.commandGenerator("compress " + percentage, imageName);
    return true;
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
    this.view.splitPreview("luma-component");
  }

  @Override
  public void applyValue(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    this.view.splitPreview("value-component");
  }

  @Override
  public void applyIntensity(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    this.view.splitPreview("intensity-component");
  }

  @Override
  public void applyRed(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    this.view.splitPreview("red-component");
  }

  @Override
  public void applyGreen(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    this.view.splitPreview("green-component");
  }

  @Override
  public void applyBlue(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    this.view.splitPreview("blue-component");
  }

  @Override
  public void applySplit(String percentage, String commandName, String imageName) {
    if (!this.percentageCheck(percentage)) {
      return;
    }
    this.commandGeneratorSplit(commandName, percentage, imageName);
  }

  private boolean percentageCheck(String percentage) {
    try {
      double value = Double.parseDouble(percentage);
      if (value < 0 || value > 100) {
        this.view.showNumberError();
        return false;
      }
      return true;
    } catch (NumberFormatException e) {
      this.view.showNumberError();
      return false;
    }
  }

}
