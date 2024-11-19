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
  public void AddFeaturesTesting() {
    controller.startGUIBasedApplication();
    String expected = "Features object added to the action listeners of the buttons.\n";
    assertEquals(expected, log.toString());
  }

  @Test
  public void AddLoadImage() {
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
  public void testSaveCorrectCall() {
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
  public void testShowErrorSave() {
    f.saveImage(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testapplyBlurCorrectCall() {
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
  public void testapplySepiaCorrectCall() {
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
  public void testapplySharpenCorrectCall() {
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
  public void testapplyLumaCorrectCall() {
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
  public void testapplyHorizontalFlipCorrectCall() {
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
  public void testapplyVerticalFlipCorrectCall() {
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
  public void testapplyValueCorrectCall() {
    f.loadImage();
    f.applyValue("manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "value-component preview called successfully.\n" +
            "SplitPreview on manhattan-small with 50.0\n" +
            "GetBrightnessComponent manhattan-small_value-component_Split1455224919" +
            " to manhattan-small_value-component_Split1455224919 value-component\n" +
            "Regain manhattan-small to manhattan-small_value-component_Split\n" +
            "Save image manhattan-small_value-component_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testShowErrorValue() {
    f.applyValue(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testapplyIntensityCorrectCall() {
    f.loadImage();
    f.applyIntensity("manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "intensity-component preview called successfully.\n" +
            "SplitPreview on manhattan-small with 50.0\n" +
            "GetBrightnessComponent manhattan-small_intensity-component_Split987202969 " +
            "to manhattan-small_intensity-component_Split987202969 intensity-component\n" +
            "Regain manhattan-small to manhattan-small_intensity-component_Split\n" +
            "Save image manhattan-small_intensity-component_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testShowErrorIntensity() {
    f.applyIntensity(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testapplyColorCorrectCorrectCall() {
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
  public void testapplyRedCorrectCall() {
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
  public void testapplyGreenCorrectCall() {
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
  public void testapplyBlueCorrectCall() {
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
  public void testapplyLeveladjustCorrectCall(){
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
  public void testShowErrorLeveladjust() {
    f.applyLevelAdjust(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

//  @Test
//  public void test
//
//
  @Test
  public void testapplyCompressCorrectCall() {
    f.loadImage();
    f.applyCompress("manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Compress split frame called successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testShowErrorCompress() {
    Features f = new ImageApplicationFeatures(model, view);
    f.applyCompress(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testShowErrorBrightness() {
    f.applyBrightness(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testShowErrorDownScale() {
    f.applyBrightness(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }


  @Test
  public void testapplySepia() {
    f.loadImage();
    f.applySepia("manhattan-smallSepia");
    String expected = " checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "sepia preview called successfully.\n" +
            "SplitPreview on manhattan-smallSepia with 50.0\n" +
            "Sepia manhattan-smallSepia_sepia_Split2008669134 to manhattan-small" +
            "Sepia_sepia_Split2008669134\n" +
            "Regain manhattan-smallSepia to manhattan-smallSepia_sepia_Split\n" +
            "Save image manhattan-smallSepia_sepia_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }


}
