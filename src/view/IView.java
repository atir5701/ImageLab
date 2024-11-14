package view;

import java.awt.image.BufferedImage;
import java.io.File;

import controller.Features;

public interface IView {

  void addFeature(Features features);

  File getFilePath();

  File getSaveFilePath();

  boolean checkImage();

  void showImage(String name, BufferedImage image);

  void showImageNotPresent();

  void showHistogram(BufferedImage image);

  void splitPreview(String commandName);

  void showNumberError();

  void showSplitImage(BufferedImage image);

  void showSaveSuccess();

  void LevelAdjust();

  void showNullValueError();

  void showInvalidRangeError();

  void showOrderError();


  void compressImage();

}
