package controller;

import org.junit.Before;
import org.junit.Test;

import model.ImageOperationsV3;
import model.OperationsV3;
import view.IView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MVCControllerTest {
  IView view;
  OperationsV3 model;
  StringBuilder log;
  MVCController controller;
  Features f;

  @Before
  public void setUp() {
    log = new StringBuilder();
    model = new ImageOperationsMock(log);
    view = new GuiViewMock(log);
    controller = new MVCController(model, view);
    f = new ImageApplicationFeatures(model, view);
  }

  @Test
  public void addFeaturesTesting() {
    controller.startGUIBasedApplication();
    String expected = "Features object added to the action listeners of the buttons";
    assertEquals(expected, log.toString());
  }

  @Test
  public void addLoadImage() {
    f.loadImage();
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly";
    assertEquals(expected, log.toString());
  }

  @Test
  public void saveCorrectCall() {
    f.loadImage();
    f.saveImage("manhattan-smallhist");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "getSaveFilePath called successfully.\n" +
            "Save image manhattan-smallhist\n" +
            "Success Message Displayed.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void showErrorSave() {
    f.saveImage(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void applyBlurCorrectCall() {
    f.loadImage();
    f.applyBlur("manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "blur preview called successfully.\n" +
            "SplitPreview on manhattan-small with 50.0\n" +
            "Blur manhattan-small_blur_Split-1921825657 to manhattan-small_blur_Split-1921825657\n" +
            "Regain manhattan-small to manhattan-small_blur_Split\n" +
            "Save image manhattan-small_blur_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testShowErrorBlur() {
    f.applyBlur(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testBlurPreview() {
    f.loadImage();
    f.applySplit("50", "blur", "manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "SplitPreview on manhattan with 50.0\n" +
            "Blur manhattan_blur_Split8433089 to manhattan_blur_Split8433089\n" +
            "Regain manhattan to manhattan_blur_Split\n" +
            "Save image manhattan_blur_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void negativePercentageBlur() {
    f.loadImage();
    f.applySplit("-60", "blur", "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void invalidPercentageBlur() {
    f.loadImage();
    f.applySplit("160", "blur", "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void stringPercentageForBlur() {
    f.loadImage();
    f.applySplit("adjbf", "blur", "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void blurFinalApply() {
    f.loadImage();
    f.commandGenerator("blur", "manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Blur manhattan to manhattan_blur\n" +
            "Save image manhattan_blur\n" +
            "Image manhattan_blur displayed correctly.\n" +
            "Histogram manhattan_blur to manhattan_blurhist\n" +
            "Save image manhattan_blurhist\n" +
            "Histogram of image showed correctly";
    assertEquals(expected, log.toString());
  }

  @Test
  public void applySepiaCorrectCall() {
    f.loadImage();
    f.applySepia("manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "sepia preview called successfully.\n" +
            "SplitPreview on manhattan-small with 50.0\n" +
            "Sepia manhattan-small_sepia_Split1183982764 to " +
            "manhattan-small_sepia_Split1183982764\n" +
            "Regain manhattan-small to manhattan-small_sepia_Split\n" +
            "Save image manhattan-small_sepia_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testShowErrorSepia() {
    f.applySepia(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testSepiaPreview() {
    f.loadImage();
    f.applySplit("50", "sepia", "manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "SplitPreview on manhattan with 50.0\n" +
            "Sepia manhattan_sepia_Split892461746 to manhattan_sepia_Split892461746\n" +
            "Regain manhattan to manhattan_sepia_Split\n" +
            "Save image manhattan_sepia_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void negativePercentageSepia() {
    f.loadImage();
    f.applySplit("-60", "blur", "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void invalidPercentageSepia() {
    f.loadImage();
    f.applySplit("160", "blur", "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void stringPercentageForSepia() {
    f.loadImage();
    f.applySplit("adjbf", "blur", "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void sepiaFinalApply() {
    f.loadImage();
    f.commandGenerator("sepia", "manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Sepia manhattan to manhattan_sepia\n" +
            "Save image manhattan_sepia\n" +
            "Image manhattan_sepia displayed correctly.\n" +
            "Histogram manhattan_sepia to manhattan_sepiahist\n" +
            "Save image manhattan_sepiahist\n" +
            "Histogram of image showed correctly";
    assertEquals(expected, log.toString());
  }


  @Test
  public void applySharpenCorrectCall() {
    f.loadImage();
    f.applySharp("manhattan-smallBlur");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "sharpen preview called successfully.\n" +
            "SplitPreview on manhattan-smallBlur with 50.0\n" +
            "Sharpen manhattan-smallBlur_sharpen_Split799684496 to manhattan-small" +
            "Blur_sharpen_Split799684496\n" +
            "Regain manhattan-smallBlur to manhattan-smallBlur_sharpen_Split\n" +
            "Save image manhattan-smallBlur_sharpen_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testShowErrorSharp() {
    f.applySharp(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testSharpPreview() {
    f.loadImage();
    f.applySplit("50", "sharpen", "manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "SplitPreview on manhattan with 50.0\n" +
            "Sharpen manhattan_sharpen_Split968469711 to manhattan_sharpen_Split968469711\n" +
            "Regain manhattan to manhattan_sharpen_Split\n" +
            "Save image manhattan_sharpen_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void negativePercentageSharp() {
    f.loadImage();
    f.applySplit("-60", "sharpen", "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void invalidPercentageSharp() {
    f.loadImage();
    f.applySplit("160", "sharpen", "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void stringPercentageForSharp() {
    f.loadImage();
    f.applySplit("adjbf", "sharpen", "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void sharpFinalApply() {
    f.loadImage();
    f.commandGenerator("sharpen", "manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Sharpen manhattan to manhattan_sharpen\n" +
            "Save image manhattan_sharpen\n" +
            "Image manhattan_sharpen displayed correctly.\n" +
            "Histogram manhattan_sharpen to manhattan_sharpenhist\n" +
            "Save image manhattan_sharpenhist\n" +
            "Histogram of image showed correctly";
    assertEquals(expected, log.toString());
  }

  @Test
  public void applyHorizontalFlipCorrectCall() {
    f.loadImage();
    f.applyHorizontalFlip("manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "HorizontalFlip manhattan-small to manhattan-small_horizontal-flip\n" +
            "Save image manhattan-small_horizontal-flip\n" +
            "Image manhattan-small_horizontal-flip displayed correctly.\n" +
            "Histogram manhattan-small_horizontal-flip to manhattan-small_horizontal-fliphist\n" +
            "Save image manhattan-small_horizontal-fliphist\n" +
            "Histogram of image showed correctly";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testShowErrorHorizontalFlip() {
    f.applyHorizontalFlip(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void applyVerticalFlipCorrectCall() {
    f.loadImage();
    f.applyHorizontalFlip("manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "HorizontalFlip manhattan-small to manhattan-small_horizontal-flip\n" +
            "Save image manhattan-small_horizontal-flip\n" +
            "Image manhattan-small_horizontal-flip displayed correctly.\n" +
            "Histogram manhattan-small_horizontal-flip to manhattan-small_horizontal-fliphist\n" +
            "Save image manhattan-small_horizontal-fliphist\n" +
            "Histogram of image showed correctly";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testShowErrorVerticalFlip() {
    f.applyVerticalFlip(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void applyLumaCorrectCall() {
    f.loadImage();
    f.applyLuma("manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "luma-component preview called successfully.\n" +
            "SplitPreview on manhattan-small with 50.0\n" +
            "GetBrightnessComponent manhattan-small_luma-component_Split1191784813" +
            " to manhattan-small_luma-component_Split1191784813 luma-component\n" +
            "Regain manhattan-small to manhattan-small_luma-component_Split\n" +
            "Save image manhattan-small_luma-component_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testShowErrorLuma() {
    f.applyLuma(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testLumaPreview() {
    f.loadImage();
    f.applySplit("50", "luma-component", "manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "SplitPreview on manhattan with 50.0\n" +
            "GetBrightnessComponent manhattan_luma-component_Split-1762141657 to" +
            " manhattan_luma-component_Split-1762141657 luma-component\n" +
            "Regain manhattan to manhattan_luma-component_Split\n" +
            "Save image manhattan_luma-component_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void negativePercentageLuma() {
    f.loadImage();
    f.applySplit("-60", "luma-component", "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void invalidPercentageLuma() {
    f.loadImage();
    f.applySplit("160", "luma-component", "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void stringPercentageForLuma() {
    f.loadImage();
    f.applySplit("adjbf", "luma-component", "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void lumaFinalApply() {
    f.loadImage();
    f.commandGenerator("luma-component", "manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "GetBrightnessComponent manhattan to manhattan_luma-component luma-component\n" +
            "Save image manhattan_luma-component\n" +
            "Image manhattan_luma-component displayed correctly.\n" +
            "Histogram manhattan_luma-component to manhattan_luma-componenthist\n" +
            "Save image manhattan_luma-componenthist\n" +
            "Histogram of image showed correctly";
    assertEquals(expected, log.toString());
  }

  @Test
  public void applyColorCorrectCorrectCall() {
    f.loadImage();
    f.applyColorCorrect("manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "color-correct preview called successfully.\n" +
            "SplitPreview on manhattan-small with 50.0\n" +
            "ColorCorrection manhattan-small_color-correct_Split-1779365834 to " +
            "manhattan-small_color-correct_Split-1779365834\n" +
            "Regain manhattan-small to manhattan-small_color-correct_Split\n" +
            "Save image manhattan-small_color-correct_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testShowErrorColorCorrect() {
    f.applyColorCorrect(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testColorCorrectPreview() {
    f.loadImage();
    f.applySplit("50", "color-correct", "manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "SplitPreview on manhattan with 50.0\n" +
            "ColorCorrection manhattan_color-correct_Split-1181917124 to " +
            "manhattan_color-correct_Split-1181917124\n" +
            "Regain manhattan to manhattan_color-correct_Split\n" +
            "Save image manhattan_color-correct_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void negativePercentageColorCorrect() {
    f.loadImage();
    f.applySplit("-60", "color-correct",
            "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void invalidPercentageColorCorrect() {
    f.loadImage();
    f.applySplit("160", "color-correct",
            "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void stringPercentageForColorCorrect() {
    f.loadImage();
    f.applySplit("adjbf", "color-correct",
            "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void colorCorrectFinalApply() {
    f.loadImage();
    f.commandGenerator("color-correct", "manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "ColorCorrection manhattan to manhattan_color-correct\n" +
            "Save image manhattan_color-correct\n" +
            "Image manhattan_color-correct displayed correctly.\n" +
            "Histogram manhattan_color-correct to manhattan_color-correcthist\n" +
            "Save image manhattan_color-correcthist\n" +
            "Histogram of image showed correctly";
    assertEquals(expected, log.toString());
  }

  @Test
  public void applyRedCorrectCall() {
    f.loadImage();
    f.applyRed("manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "red-component preview called successfully.\n" +
            "SplitPreview on manhattan-small with 50.0\n" +
            "GetColorComponent manhattan-small_red-component_Split2135654967 " +
            "to manhattan-small_red-component_Split2135654967 0\n" +
            "Regain manhattan-small to manhattan-small_red-component_Split\n" +
            "Save image manhattan-small_red-component_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testShowErrorRed() {
    f.applyRed(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testRedCorrectPreview() {
    f.loadImage();
    f.applySplit("50", "red-component", "manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "SplitPreview on manhattan with 50.0\n" +
            "GetColorComponent manhattan_red-component_Split-1561863619" +
            " to manhattan_red-component_Split-1561863619 0\n" +
            "Regain manhattan to manhattan_red-component_Split\n" +
            "Save image manhattan_red-component_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void negativePercentageRedCorrect() {
    f.loadImage();
    f.applySplit("-60", "red-component",
            "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void invalidPercentageRedCorrect() {
    f.loadImage();
    f.applySplit("160", "red-component",
            "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void stringPercentageForRedCorrect() {
    f.loadImage();
    f.applySplit("adjbf", "red-component",
            "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void redComponentFinalApply() {
    f.loadImage();
    f.commandGenerator("color-correct", "manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "ColorCorrection manhattan to manhattan_color-correct\n" +
            "Save image manhattan_color-correct\n" +
            "Image manhattan_color-correct displayed correctly.\n" +
            "Histogram manhattan_color-correct to manhattan_color-correcthist\n" +
            "Save image manhattan_color-correcthist\n" +
            "Histogram of image showed correctly";
    assertEquals(expected, log.toString());
  }

  @Test
  public void applyGreenCorrectCall() {
    f.loadImage();
    f.applyGreen("manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "green-component preview called successfully.\n" +
            "SplitPreview on manhattan-small with 50.0\n" +
            "GetColorComponent manhattan-small_green-component_Split-390606359 " +
            "to manhattan-small_green-component_Split-390606359 1\n" +
            "Regain manhattan-small to manhattan-small_green-component_Split\n" +
            "Save image manhattan-small_green-component_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testShowErrorGreen() {
    f.applyGreen(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testBlueCorrectPreview() {
    f.loadImage();
    f.applySplit("50", "blue-component", "manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "SplitPreview on manhattan with 50.0\n" +
            "GetColorComponent manhattan_blue-component_Split-1226927196 to " +
            "manhattan_blue-component_Split-1226927196 2\n" +
            "Regain manhattan to manhattan_blue-component_Split\n" +
            "Save image manhattan_blue-component_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void negativePercentageBlueCorrect() {
    f.loadImage();
    f.applySplit("-60", "blue-component",
            "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void invalidPercentageBlueCorrect() {
    f.loadImage();
    f.applySplit("160", "blue-component",
            "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void stringPercentageForBlueCorrect() {
    f.loadImage();
    f.applySplit("adjbf", "blue-component",
            "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void blueComponentFinalApply() {
    f.loadImage();
    f.commandGenerator("blue-component", "manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "GetColorComponent manhattan to manhattan_blue-component 2\n" +
            "Save image manhattan_blue-component\n" +
            "Image manhattan_blue-component displayed correctly.\n" +
            "Histogram manhattan_blue-component to manhattan_blue-componenthist\n" +
            "Save image manhattan_blue-componenthist\n" +
            "Histogram of image showed correctly";
    assertEquals(expected, log.toString());
  }

  @Test
  public void applyBlueCorrectCall() {
    f.loadImage();
    f.applyBlue("manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "blue-component preview called successfully.\n" +
            "SplitPreview on manhattan-small with 50.0\n" +
            "GetColorComponent manhattan-small_blue-component_Split1726999274 " +
            "to manhattan-small_blue-component_Split1726999274 2\n" +
            "Regain manhattan-small to manhattan-small_blue-component_Split\n" +
            "Save image manhattan-small_blue-component_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testShowErrorBlue() {
    f.applyBlue(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void applyLevelAdjustCorrectCall() {
    f.loadImage();
    f.applyLevelAdjust("manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Level-Adjustment split frame called successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testShowErrorLevelAdjust() {
    f.applyLevelAdjust(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testLevelAdjustValueNotGiven() {
    f.loadImage();
    f.getLevelAdjust("manhattan-small", " ", " ", " ", "50");
    String temp = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "All values (B, M, and W) must be provided and all must be whole value.";
    assertEquals(temp, log.toString());
  }

  @Test
  public void testLevelAdjustValueNotInteger() {
    f.loadImage();
    f.getLevelAdjust("manhattan-small", "afbf", "skfba", "ajfbsf", "50");
    String temp = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "All values (B, M, and W) must be provided and all must be whole value.";
    assertEquals(temp, log.toString());
  }

  @Test
  public void testLevelAdjustNotInRangeNegative() {
    f.loadImage();
    f.getLevelAdjust("manhattan-small", "-123", "-255", "-2", "50");
    String temp = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "All values (B, M, and W) must be between 0 to 255";
    assertEquals(temp, log.toString());
  }

  @Test
  public void testLevelAdjustNotInRange() {
    f.loadImage();
    f.getLevelAdjust("manhattan-small", "1230", "2550", "2000", "50");
    String temp = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "All values (B, M, and W) must be between 0 to 255";
    assertEquals(temp, log.toString());
  }

  @Test
  public void negativePercentageLevelAdjust() {
    f.loadImage();
    f.getLevelAdjust("manhattan","60", "100","240","-123");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void invalidPercentageLevelAdjustCorrect() {
    f.loadImage();
    f.getLevelAdjust("manhattan","60", "100","240","123");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void stringPercentageForLevelAdjustCorrect() {
    f.loadImage();
    f.getLevelAdjust("manhattan","60", "100","240","afk");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testLevelAdjustNotInAscending() {
    f.loadImage();
    f.getLevelAdjust("manhattan-small", "123", "25", "20", "50");
    String temp = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Value of B,M and W must be in ascending order (B < M < W)";
    assertEquals(temp, log.toString());
  }

  @Test
  public void testLevelAdjustApply() {
    f.loadImage();
    f.generateLevelAdjust("24", "50", "100", "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "LevelAdjustment manhattan-small to manhattan-small_levels-adjust with 24 50 100\n" +
            "Save image manhattan-small_levels-adjust\n" +
            "Image manhattan-small_levels-adjust displayed correctly.\n" +
            "Histogram manhattan-small_levels-adjust to manhattan-small_levels-adjusthist\n" +
            "Save image manhattan-small_levels-adjusthist\n" +
            "Histogram of image showed correctly";
    assertEquals(expected, log.toString());
  }




  @Test
  public void testCompressError() {
    f.applyCompress(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }



}
