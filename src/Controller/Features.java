package controller;

public interface Features {

  void loadImage();

  void saveImage(String imageName);

  void applySepia(String imageName);

  void applyBlur(String imageName);

  void applySharp(String imageName);

  void applyHorizontalFlip(String imageName);

  void applyVerticalFlip(String imageName);

  void applyColorCorrect(String imageName);

  void applyCompress(String imageName,double percentage);

  void applyBrightness(String imageName,int value);

  void applyLuma(String imageName);

  void applyValue(String imageName);

  void applyIntensity(String imageName);

  void applyRed(String imageName);

  void applyGreen(String imageName);

  void applyBlue(String imageName);

  void applyLevelAdjust(String imageName,int b,int m,int w);

  void applySplit(String percentage,String commandName,String imageName);

  void commandGenerator(String operation, String imageName);

}
