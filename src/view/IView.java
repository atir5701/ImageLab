package view;

import java.awt.image.BufferedImage;
import java.io.File;

import controller.Features;

public interface IView {

  void addFeature(Features features);

  File getFilePath();

  boolean checkImage();

  void showImage(String name, BufferedImage image);

  void showImageNotPresent();

  void showHistogram(BufferedImage image);

}
