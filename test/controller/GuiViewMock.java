package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import view.IView;

public class GuiViewMock implements IView {

  private final StringBuilder log;

  public GuiViewMock(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void addFeature(Features features) {
    log.append("Features object added to the action listeners of the buttons");
  }

  @Override
  public File getFilePath() {
    log.append("\ngetFilePath called successfully.");
    return new File("images/manhattan-small.png");
  }

  @Override
  public File getSaveFilePath() {
    log.append("\ngetSaveFilePath called successfully.");
    return new File("res/manhattan-small.png");
  }

  @Override
  public boolean checkImage() {
    log.append("checkImage called successfully.");
    return true;
  }

  @Override
  public void showImage(String name, BufferedImage image) {
      log.append("\nImage ").append(name).append(" displayed correctly.");
  }

  @Override
  public void showHistogram(BufferedImage image) {
    log.append("\nHistogram of image showed correctly");

  }

  @Override
  public boolean splitPreview(String commandName) {
    log.append("\n").append(commandName).append(" preview called successfully.");
    return true;
  }

  @Override
  public void showSplitImage(BufferedImage image) {
    log.append("\nSplit Image displayed successfully.");
  }

  @Override
  public void showSaveSuccess() {
    log.append("\nSuccess Message Displayed.");
  }

  @Override
  public void levelAdjust() {
    log.append("\nLevel-Adjustment split frame called successfully.");
  }

  @Override
  public void showImageNotPresent() {
    log.append("\nImage not present error displayed successfully.");
  }

  @Override
  public void compressImage() {
    log.append("\nCompress split frame called successfully.");
  }

  @Override
  public void brightness() {
    log.append("\nBrightness split frame called successfully.");
  }

  @Override
  public void showError(String error) {
    log.append("\nError message displayed successfully.");
  }

  @Override
  public void downScale(int height, int width) {
      log.append("\nDownscale split frame called successfully.").append("\n")
              .append("with current height and width are ").append(height).append(" ")
              .append(width);
  }
}
