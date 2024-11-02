package model;

public interface OperationsV2 extends Operations {

  boolean compressImage(String currentImageName, String newImageName, double percentage);

  boolean histogram(String currentImageName, String newImageName);

  boolean colorCorrection(String currentImageName, String newImageName);

  boolean levelAdjustment(String currentImageName, String newImageName, int b, int m, int w);

  void splitPreview(String currentImageName, String newImageName,double percentage);
//  public boolean splitView()
  boolean regain(String currentImageName,String temporaryName,String newImageName);
}
