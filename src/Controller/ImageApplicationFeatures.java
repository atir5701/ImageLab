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
  private double percentage;

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

  private void displaySplitImage(String name) {
    BufferedImage image = this.convertToDisplay(name);
    view.showSplitImage(image);
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
    String command = operation + " " + imageName + " " + imageName + "_" + operation;
    commandHandler.readCommand(command.split(" "));
    this.displayImage(imageName + "_" + operation);
  }

  private void commandGeneratorSplit(String operation,String commandName,String imageName) {
    String command = operation + " " + imageName + " " + imageName + "_" + operation
            +"_Split"+" split "+this.percentage;
    commandHandler.readCommand(command.split(" "));
    this.displaySplitImage(imageName + "_" + operation+"_Split");
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
  public void saveImage(String imageName){
    boolean t = this.checkImage(imageName);
    if(!t){
      return;
    }
    File path = view.getSaveFilePath();
    if(path!=null){
      String command = "save "+path.getAbsolutePath()+" "+imageName;
      commandHandler.readCommand(command.split(" "));
      view.showSaveSuccess();
    }
  }


  @Override
  public void applySepia(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    view.splitPreview("sepia");
  }

  @Override
  public void applyBlur(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    view.splitPreview("blur");
  }


  @Override
  public void applySplit(String percentage,String commandName,String imageName){
    try {
      double temp = Double.parseDouble(percentage);
      if(temp<0 || temp>100){
        view.showNumberError();
      }else {
        this.percentage = temp;
        this.commandGeneratorSplit(commandName, percentage, imageName);
      }
    } catch (NumberFormatException e) {
      view.showNumberError();
    }
  }

  @Override
  public void applySharp(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    view.splitPreview("sharpen");
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
    view.splitPreview("color-correct");
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
    view.splitPreview("luma-component");
  }

  @Override
  public void applyValue(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    view.splitPreview("value-component");
  }

  @Override
  public void applyIntensity(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    view.splitPreview("intensity-component");
  }

  @Override
  public void applyRed(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    view.splitPreview("red-component");
  }

  @Override
  public void applyGreen(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    view.splitPreview("green-component");
  }

  @Override
  public void applyBlue(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    view.splitPreview("blue-component");
  }

  @Override
  public void applyLevelAdjust(String imageName, int b, int m, int w) {

  }




}
