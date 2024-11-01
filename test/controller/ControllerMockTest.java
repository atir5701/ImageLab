package controller;

/**
 * Split Preview test still left.
 */


import org.junit.Before;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import model.OperationsV2;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ControllerMockTest {
  StringBuffer out;
  Reader in;
  OperationsV2 mock;
  StringBuilder log;
  ImageAppController controller;


  @Before
  public void setUp() {
    log = new StringBuilder();
    mock = new ImageOperationsV2Mock(log);
    out = new StringBuffer();
  }

  @Test
  public void checkBright(){
    in = new StringReader("brighten 50 man man-bright");
    controller = new CommandReader(mock,in,out);
    controller.startApplication();
    String expected = "Brighten man to man-bright 50";
    assertEquals(expected,log.toString());
  }

  @Test
  public void checkCompress(){
    in = new StringReader("compress 50 man mancomp");
    controller = new CommandReader(mock,in,out);
    controller.startApplication();
    String expected = "Compress man to mancomp";
    assertEquals(expected,log.toString());
  }

  @Test
  public void checkHistogram(){
    in = new StringReader("histogram man manhist");
    controller = new CommandReader(mock,in,out);
    controller.startApplication();
    String expected = "Histogram man to manhist";
    assertEquals(expected,log.toString());
  }

  @Test
  public void checkColorCorrection(){
    in = new StringReader("color-correct man mancolor-correct");
    controller = new CommandReader(mock,in,out);
    controller.startApplication();
    String expected = "ColorCorrection man to mancolor-correct";
    assertEquals(expected,log.toString());
  }

  @Test
  public void checkLevelAdjust(){
    in = new StringReader("levels-adjust 10 20 233 man man-level");
    controller = new CommandReader(mock,in,out);
    controller.startApplication();
    String expected = "LevelAdjustment man to man-level with 10 20 233";
    assertEquals(expected,log.toString());
  }

//  @Test
//  public void checkSplitPreviewWithBlur(){
//    in = new StringReader("blur man manb split 50");
//    controller = new CommandReader(mock,in,out);
//    controller.startApplication();
//    String expected = "SplitPreview man to manb 50";
//    assertEquals(expected,log.toString());
//  }

  @Test
  public void checkLoadImage(){
    in = new StringReader("load images/manhattan-small.png man");
    controller = new CommandReader(mock,in,out);
    controller.startApplication();
    String expected = "Load image man";
    assertEquals(expected,log.toString());
  }

  @Test
  public void checkSaveImage(){
    in = new StringReader("save images/manhattan.png man");
    controller = new CommandReader(mock,in,out);
    controller.startApplication();
    String expected = "Save image man";
    assertEquals(expected,log.toString());
  }

  @Test
  public void checkRedComponent(){
    in = new StringReader("red-component man manred");
    controller = new CommandReader(mock,in,out);
    controller.startApplication();
    String expected = "GetColorComponent man to manred 0";
    assertEquals(expected,log.toString());
  }

  @Test
  public void checkGreenComponent(){
    in = new StringReader("green-component man mangreen");
    controller = new CommandReader(mock,in,out);
    controller.startApplication();
    String expected = "GetColorComponent man to mangreen 1";
    assertEquals(expected,log.toString());
  }

  @Test
  public void checkBlueComponent(){
    in = new StringReader("blue-component man manblue");
    controller = new CommandReader(mock,in,out);
    controller.startApplication();
    String expected = "GetColorComponent man to manblue 2";
    assertEquals(expected,log.toString());
  }

  @Test
  public void checkValueComponent(){
    in = new StringReader("value-component man manvalue");
    controller = new CommandReader(mock,in,out);
    controller.startApplication();
    String expected = "GetBrightnessComponent man to manvalue value-component";
    assertEquals(expected,log.toString());
  }

  @Test
  public void checkIntensityComponent(){
    in = new StringReader("intensity-component man manin");
    controller = new CommandReader(mock,in,out);
    controller.startApplication();
    String expected = "GetBrightnessComponent man to manin intensity-component";
    assertEquals(expected,log.toString());
  }

  @Test
  public void checkLumaComponent(){
    in = new StringReader("luma-component man manluma");
    controller = new CommandReader(mock,in,out);
    controller.startApplication();
    String expected = "GetBrightnessComponent man to manluma luma-component";
    assertEquals(expected,log.toString());
  }

  @Test
  public void checkHorizontalFlip(){
    in = new StringReader("horizontal-flip man manhf");
    controller = new CommandReader(mock,in,out);
    controller.startApplication();
    String expected = "HorizontalFlip man to manhf";
    assertEquals(expected,log.toString());
  }


  @Test
  public void checkVerticalFlip(){
    in = new StringReader("vertical-flip man manvf");
    controller = new CommandReader(mock,in,out);
    controller.startApplication();
    String expected = "VerticalFlip man to manvf";
    assertEquals(expected,log.toString());
  }

  @Test
  public void checkSplitRGB(){
    in = new StringReader("rgb-split man manr mang manb");
    controller = new CommandReader(mock,in,out);
    controller.startApplication();
    String expected = "Split RBG man to manr mang manb";
    assertEquals(expected,log.toString());
  }

  @Test
  public void checkCombineRGB(){
    in = new StringReader("rgb-combine man manr mang manb");
    controller = new CommandReader(mock,in,out);
    controller.startApplication();
    String expected = "CombineRGB manr mang manb to man";
    assertEquals(expected,log.toString());
  }

  @Test
  public void checkBlur(){
    in = new StringReader("blur man manblur");
    controller = new CommandReader(mock,in,out);
    controller.startApplication();
    String expected = "Blur man to manblur";
    assertEquals(expected,log.toString());
  }

  @Test
  public void checkSharpen(){
    in = new StringReader("sharpen man mansharp");
    controller = new CommandReader(mock,in,out);
    controller.startApplication();
    String expected = "Sharpen man to mansharp";
    assertEquals(expected,log.toString());
  }

  @Test
  public void checkSepia(){
    in = new StringReader("sepia man mansepia");
    controller = new CommandReader(mock,in,out);
    controller.startApplication();
    String expected = "Sepia man to mansepia";
    assertEquals(expected,log.toString());
  }



}
