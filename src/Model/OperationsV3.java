package model;

public interface OperationsV3 extends OperationsV2 {

  boolean mask(String currentImageName, String temp, String maskImageName,
               String newImageName);

  void downScale(String currentImageName, int targetHeight, int targetWidth,
                 String newImageName);

}
