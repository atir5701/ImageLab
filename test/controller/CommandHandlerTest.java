package controller;

import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import model.ImageOperationsV2;
import model.OperationsV2;
import view.ViewHandler;


import static org.junit.Assert.assertEquals;

/**
 * Class to perform all the testing related
 * to the Controller package.
 */

public class CommandHandlerTest {
  ImageAppController controller;
  OperationsV2 opr;
  String cmd = "Enter the Command:\n";
  String cmdload = "Enter the Command:\n" + "load executed successfully\n";
  StringBuffer out;
  ViewHandler v;

  @Before
  public void setUp() {
    opr = new ImageOperationsV2();
    out = new StringBuffer();
    v = new ViewHandler(out);
  }

  /**
   * Test Case to check if the load method is
   * working correctly.
   */
  @Test
  public void testLoad() {
    String command = "load images\\manhattan-small.png man";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "load executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the load throws
   * error if command is given incorrectly.
   */
  @Test
  public void testLoadIncorrectFormat() {
    String command = "load images\\manhattan-small.png ";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "Invalid command length\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the load method
   * throws error if extension is not
   * supported.
   */
  @Test
  public void testLoadWithIncorrectExtension() {
    String command = "load images\\manhattan-small.oip man";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "Extension of the image is not supported.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the load method
   * throws error if filepath is not valid.
   */
  @Test
  public void testLoadWithIncorrectFilePath() {
    String command = "load img\\manhattan-small.ppm man";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "Filepath provided is incorrect.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the brighten command is
   * called successfully.
   */
  @Test
  public void testBrighten() {
    String command = "load images\\manhattan-small.png man\n " +
            "brighten 100 man manbr";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "brighten executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test case to check the darken command is
   * executed successfully.
   */
  @Test
  public void testDarken() {
    String command = "load images\\manhattan-small.png man" +
            "\n brighten -100 man manbr";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "brighten executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test case to check if brighten command
   * throws error when increment value is not
   * an integer (double).
   */
  @Test
  public void testBrightenIfNoIntegerGiven() {
    String command = "load images\\manhattan-small.png man" +
            "\n brighten 100.56 man manbr";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Increment must be an integer.\n";
    assertEquals(expected, output);
  }

  /**
   * Test case to check if brighten command
   * throws error when increment value is not
   * an integer (string).
   */
  @Test
  public void testBrightenIfStringGiven() {
    String command = "load images\\manhattan-small.png man\n " +
            "brighten hundered man manbr";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Increment must be an integer.\n";
    assertEquals(expected, output);
  }

  /**
   * Test case to check if brighten command
   * gives error when given incorrectFormat.
   */
  @Test
  public void testBrightenIncorrectFormat() {
    String command = "load images\\manhattan-small.png man" +
            "\n brighten 100 manbr";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid command length\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check brighten throws error when image to be
   * brighten is not present in application.
   */
  @Test
  public void testBrightenWithoutImage() {
    String command = "brighten 100 man manbrighten";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test case to check if Horizontal flip
   * is called correctly.
   */
  @Test
  public void testHorizontalFlip() {
    String command = "load images\\manhattan-small.png man" +
            "\n horizontal-flip man man-horizontal-flip";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "horizontal-flip executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test case to check if Horizontal flip
   * throw error if invalid command format.
   */
  @Test
  public void testHorizontalFlipInvalidCommand() {
    String command = "load images\\manhattan-small.png man" +
            "\n horizontal-flip manbr";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid command length\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check horizontal-flip throw error,
   * if image to be loaded is not present.
   */
  @Test
  public void testHorizontalFlipImageNotFound() {
    String command = "load images\\manhattan-small.png man\n" +
            " horizontal-flip manbr manhroizontal";
    Reader in = new StringReader(command);


    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be " +
            "processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test case to check if vertical-flip is called
   * correctly.
   */
  @Test
  public void testVerticalFlip() {
    String command = "load images\\manhattan-small.png man" +
            "\n vertical-flip  man manbvflip";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "vertical-flip executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test case to check if Horizontal flip
   * throw error if invalid command format.
   */
  @Test
  public void testVerticalFlipInvalidCommand() {
    String command = "load images\\manhattan-small.png man" +
            "\n vertical-flip manbr";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid command length\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check horizontal-flip throw error,
   * if image to be loaded is not present.
   */
  @Test
  public void testVerticalFlipImageNotFound() {
    String command = "load images\\manhattan-small.png man\n " +
            "vertical-flip manbr manhroizontal";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check Blur method is called,
   * correctly.
   */
  @Test
  public void testBlur() {
    String command = "load images\\manhattan-small.png man\n" +
            "blur man manblur";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "blur executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test case to check if Blur
   * throw error if invalid command format.
   */
  @Test
  public void testBlurInvalidCommand() {
    String command = "load images\\manhattan-small.png man" +
            "\n blur manbr";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check blur throw error,
   * if image to be loaded is not present.
   */
  @Test
  public void testBlurImageNotFound() {
    String command = "load images\\manhattan-small.png man" +
            "\n blur manbr manhroizontal";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with blur works correctly.
   */
  @Test
  public void testBlurWithSplit() {
    String command = "load images\\manhattan-small.png man\n" +
            "blur man mansharpen split 50\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "blur executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with no value
   * blur.
   */
  @Test
  public void testBlurWithSplitNoValue() {
    String command = "load images\\manhattan-small.png man\n" +
            "blur man manred split\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with not numeric value
   * blur.
   */
  @Test
  public void testBlurWithSplitNoNumValue() {
    String command = "load images\\manhattan-small.png man\n" +
            "blur man manred split hundered\n";

    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be a number.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if error thrown is word is not split.
   */
  @Test
  public void testBlurWithNoSplit() {
    String command = "load images\\manhattan-small.png man\n" +
            "blur man manred sp 45\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the percentage is less than zero.
   */
  @Test
  public void testBlurSplitInvalid() {
    String command = "load images\\manhattan-small.png man\n" +
            "blur man manlevel split -23\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100.\n";

    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the percentage is grater than hundered.
   */
  @Test
  public void testBlurSplitValueMore100() {
    String command = "load images\\manhattan-small.png man\n" +
            "blur man manlevel split 123\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100.\n";
    assertEquals(expected, output);
  }


  /**
   * Test Case to check if the sharpen method is
   * called correctly.
   */
  @Test
  public void testSharpen() {
    String command = "load images\\manhattan-small.png man" +
            "\n sharpen  man mansharpen";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "sharpen executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test case to check if Sharpen
   * throw error if invalid command format.
   */
  @Test
  public void testSharpenInvalidCommand() {
    String command = "load images\\manhattan-small.png man" +
            "\n sharpen manbr";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check Sharpen throw error,
   * if image to be loaded is not present.
   */
  @Test
  public void testSharpenImageNotFound() {
    String command = "load images\\manhattan-small.png man" +
            "\n sharpen manbr manhroizontal";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with sharpen works correctly.
   */
  @Test
  public void testSharpenWithSplit() {
    String command = "load images\\manhattan-small.png man\n" +
            "sharpen man mansharpen split 50\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "sharpen executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with no value
   * sharpen.
   */
  @Test
  public void testSharpWithSplitNoValue() {
    String command = "load images\\manhattan-small.png man\n" +
            "sharpen man mansh split";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with not numeric value
   * sharpen.
   */
  @Test
  public void testSharpenWithSplitNoNumValue() {
    String command = "load images\\manhattan-small.png man\n" +
            "sharpen man manred split hundered\n";

    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be a number.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if error thrown if word is not split.
   */
  @Test
  public void testSharpenWithNoSplit() {
    String command = "load images\\manhattan-small.png man\n" +
            "sharpen man manred sp 45\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the percentage is less than zero.
   */
  @Test
  public void testSharpSplitInvalid() {
    String command = "load images\\manhattan-small.png man\n" +
            "sharpen man manlevel split -23\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the percentage is grater than hundered.
   */
  @Test
  public void testSharpSplitValueMore100() {
    String command = "load images\\manhattan-small.png man\n" +
            "sharpen man manlevel split 123\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100.\n";
    assertEquals(expected, output);
  }


  /**
   * Test Case to check if the sepia method is
   * called correctly.
   */
  @Test
  public void testSepia() {
    String command = "load images\\manhattan-small.png man" +
            "\n sepia  man mansepia";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "sepia executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test case to check if Sepia
   * throw error if invalid command format.
   */
  @Test
  public void testSepiaInvalidCommand() {
    String command = "load images\\manhattan-small.png man" +
            "\n sepia manbr";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check Sepia throw error,
   * if image to be loaded is not present.
   */
  @Test
  public void testSepiaImageNotFound() {
    String command = "load images\\manhattan-small.png man" +
            "\n sepia manbr manhroizontal";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with sepia works correctly.
   */
  @Test
  public void testSepiaWithSplit() {
    String command = "load images\\manhattan-small.png man\n" +
            "sepia man mansepia split 50\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "sepia executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with no value
   * sepia.
   */
  @Test
  public void testSepiaWithSplitNoValue() {
    String command = "load images\\manhattan-small.png man\n" +
            "sepia man mansepia split\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with not numeric value
   * sepia
   */
  @Test
  public void testSepiaWithSplitNoNumValue() {
    String command = "load images\\manhattan-small.png man\n" +
            "sepia man mansepia split hundered\n";

    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be a number.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if error thrown if word is not split.
   */
  @Test
  public void testSepiaWithNoSplit() {
    String command = "load images\\manhattan-small.png man\n" +
            "sepia man mansepia sp 45\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the percentage is less than zero.
   */
  @Test
  public void testSepiaSplitInvalid() {
    String command = "load images\\manhattan-small.png man\n" +
            "sepia man manlevel split -23\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100.\n";

    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the percentage is grater than hundered.
   */
  @Test
  public void testSepiaSplitValueMore100() {
    String command = "load images\\manhattan-small.png man\n" +
            "sepia man manlevel split 123\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100.\n";
    assertEquals(expected, output);
  }


  /**
   * Test Case to check if the brightness-component
   * method is called correctly.
   */
  @Test
  public void testBrightnessComponent() {
    String command = "load images\\manhattan-small.png man\n " +
            "value-component  man manvalue" +
            "\n intensity-component man maninten" +
            "\n luma-component man manluma";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "value-component executed successfully\n" +
            "intensity-component executed successfully\n" +
            "luma-component executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test case to check if brightness component
   * throw error if invalid command format.
   */
  @Test
  public void testBrightnessComponentInvalidCommand() {
    String command = "load images\\manhattan-small.png man\n" +
            " value-component man" +
            "\n intensity-component man\n" +
            "luma-component man";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command.\nInvalid Command.\n" +
            "Invalid Command.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check brightnessComponent
   * throw error, if image to be loaded is not present.
   */
  @Test
  public void testBrightnessComponentImageNotFound() {
    String command = "load images\\manhattan-small.png man\n " +
            "value-component manbr manhroizontal\n" +
            "intensity-component manbr manhorizontal "
            + "\n luma-component manbr manhorizontal";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n" +
            "The image to be processed is not present.\n" +
            "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with brightness-component works correctly.
   */
  @Test
  public void testBrightnessComponentWithSplit() {
    String command = "load images\\manhattan-small.png man\n" +
            "value-component man manvalue split 50\n" +
            "luma-component man manluma split 50\n" +
            "intensity-component man manintensity split 50\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "value-component executed successfully\n" +
            "luma-component executed successfully\n" +
            "intensity-component executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with no value
   * brightness-component.
   */
  @Test
  public void testBrightnessComponentWithSplitNoValue() {
    String command = "load images\\manhattan-small.png man\n" +
            "value-component man manvalue split\n" +
            "luma-component man manluma split \n" +
            "intensity-component man manintensity split \n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command.\n"
            + "Invalid Command.\n"
            + "Invalid Command.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with not numeric value
   * brightness-component.
   */
  @Test
  public void testBrightnessComponentWithSplitNoNumValue() {
    String command = "load images\\manhattan-small.png man\n" +
            "value-component man manvalue split hundered\n" +
            "luma-component man manluma split hundered\n" +
            "intensity-component man manintensity split hundered\n";

    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be a number.\n"
            + "Percentage must be a number.\n"
            + "Percentage must be a number.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if error thrown if word is not split.
   */
  @Test
  public void testBrightnessComponentWithNoSplit() {
    String command = "load images\\manhattan-small.png man\n" +
            "value-component man manvalue sp 45\n" +
            "luma-component man manluma sp 34\n" +
            "intensity-component man manintensity sp 23\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command\n"
            + "Invalid Command\n"
            + "Invalid Command\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the percentage is less than zero.
   */
  @Test
  public void testBirghtnessComponentSplitInvalid() {
    String command = "load images\\manhattan-small.png man\n" +
            "value-component man manlevel split -23\n"+
            "intensity-component man manlevel split -23\n"+
            "luma-component man manlevel split -23";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100.\n"
            + "Percentage must be between 0 and 100.\n"
            + "Percentage must be between 0 and 100.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the percentage is grater than hundered.
   */
  @Test
  public void testBrightnessComponentSplitValueMore100() {
    String command = "load images\\manhattan-small.png man\n" +
            "value-component man manlevel split 123\n"+
            "luma-component man manlevel split 123\n"+
            "intensity-component man manlevel split 123";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100.\n"
            + "Percentage must be between 0 and 100.\n"
            + "Percentage must be between 0 and 100.\n";
    assertEquals(expected, output);
  }


  /**
   * Test Case to check if the color-component
   * method is called correctly.
   */
  @Test
  public void testColorComponent() {
    String command = "load images\\manhattan-small.png man\n" +
            "red-component  man manred" +
            "\ngreen-component man mangreen\n " +
            "blue-component man manblue";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "red-component executed successfully\n" +
            "green-component executed successfully\n" +
            "blue-component executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test case to check if color component
   * throw error if invalid command format.
   */
  @Test
  public void testColorComponentInvalidCommand() {
    String command = "load images\\manhattan-small.png man\n " +
            "red-component man" +
            "\n green-component man   \nblue-component man";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command.\nInvalid Command.\n" +
            "Invalid Command.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check ColorComponent
   * throw error, if image to be loaded is not present.
   */
  @Test
  public void testColorComponentImageNotFound() {
    String command = "load images\\manhattan-small.png man\n" +
            " red-component manbr " +
            "manhroizontal\n blue-component " +
            "manbr manhorizontal \n " +
            "green-component manbr manhorizontal";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n" +
            "The image to be processed is not present.\n" +
            "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with color-component works correctly.
   */
  @Test
  public void testColorComponentWithSplit() {
    String command = "load images\\manhattan-small.png man\n" +
            "red-component man manred split 50\n" +
            "blue-component man manblue split 50\n" +
            "green-component man mangreen split 50\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "red-component executed successfully\n" +
            "blue-component executed successfully\n" +
            "green-component executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with no value
   * color-component.
   */
  @Test
  public void testColorComponentWithSplitNoValue() {
    String command = "load images\\manhattan-small.png man\n" +
            "red-component man manred split\n" +
            "blue-component man manblue split \n" +
            "green-component man mangreen split \n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command.\n"
            + "Invalid Command.\n"
            + "Invalid Command.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with not numeric value
   * color-component.
   */
  @Test
  public void testColorComponentWithSplitNoNumValue() {
    String command = "load images\\manhattan-small.png man\n" +
            "red-component man manred split hundered\n" +
            "blue-component man manblue split hundered\n" +
            "green-component man mangreen split hundered\n";

    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be a number.\n"
            + "Percentage must be a number.\n"
            + "Percentage must be a number.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if error thrown if word is not split.
   */
  @Test
  public void testColorComponentWithNoSplit() {
    String command = "load images\\manhattan-small.png man\n" +
            "red-component man manred sp 45\n" +
            "blue-component man manblue sp 34\n" +
            "green-component man mangreen sp 23\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command\n"
            + "Invalid Command\n"
            + "Invalid Command\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the percentage is less than zero.
   */
  @Test
  public void testColorComponentSplitInvalid() {
    String command = "load images\\manhattan-small.png man\n" +
            "red-component man manlevel split -23\n"+
    "blue-component man manlevel split -23\n"+
    "green-component man manlevel split -23";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100.\n"
            + "Percentage must be between 0 and 100.\n"
            + "Percentage must be between 0 and 100.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the percentage is grater than hundered.
   */
  @Test
  public void testColorComponentSplitValueMore100() {
    String command = "load images\\manhattan-small.png man\n" +
            "red-component man manlevel split 123\n"+
            "blue-component man manlevel split 123\n"+
            "green-component man manlevel split 123";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100.\n"
            + "Percentage must be between 0 and 100.\n"
            + "Percentage must be between 0 and 100.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if rgb-split is called
   * correctly.
   */
  @Test
  public void testRGBSplit() {
    String command = "load images\\manhattan-small.png " +
            "man\n rgb-split man " +
            "man-red man-green man-blue";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String exectued = cmdload + "rgb-split executed successfully\n";
    assertEquals(exectued, output);
  }

  /**
   * Test case to check if rgb-split component
   * throw error if invalid command format.
   */
  @Test
  public void testRGBSplitInvalidCommand() {
    String command = "load images\\manhattan-small.png man" +
            "\n rgb-split man red green";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid command length\n";
    assertEquals(expected, output);
  }


  /**
   * Test Case to check rgb-split
   * throw error, if image to be loaded is not present.
   */
  @Test
  public void testRGBSplitImageNotFound() {
    String command = "load images\\manhattan-small.png man\n " +
            "rgb-split man2 man-red "
            + "man-green man-blue";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if rgb-combine is called
   * correctly.
   */
  @Test
  public void testRGBCombine() {
    String command = "load images\\manhattan-small.png man\n " +
            "rgb-split man " +
            "man-red man-green man-blue\nrgb-combine " +
            "man-combine man-red man-green man-blue";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String exectued = cmdload + "rgb-split executed successfully\n"
            + "rgb-combine executed successfully\n";
    assertEquals(exectued, output);
  }

  /**
   * Test case to check if rgb-split component
   * throw error if invalid command format.
   */
  @Test
  public void testRGBCombineInvalidCommand() {
    String command = "load images\\manhattan-small.png man\n " +
            "rgb-split man " +
            "man-red man-green man-blue\nrgb-combine " +
            "man-combine man-red";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "rgb-split executed successfully\n" +
            "Invalid command length\n";
    assertEquals(expected, output);
  }


  /**
   * Test Case to check rgb-split
   * throw error, if image to be loaded is not present.
   */
  @Test
  public void testRGBCombineImageNotFound() {
    String command = "load images\\manhattan-small.png man\n " +
            "rgb-split man " +
            "man-red man-green man-blue\nrgb-combine " +
            "man-combine man-r man-g man-b";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "rgb-split executed successfully" +
            "\nThe image to be processed is not present.\n";
    assertEquals(expected, output);
  }


  /**
   * Test Case to check if save is called
   * correctly.
   */
  @Test
  public void testSave() {
    String command = "load images\\manhattan-small.png man\n " +
            "save test\\controller\\resultTest\\man.png man";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "save executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test case to check if save component
   * throw error if invalid command format.
   */
  @Test
  public void testSaveInvalidCommand() {
    String command = "load images\\manhattan-small.png man\n " +
            "save man";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid command length\n";
    assertEquals(expected, output);
  }


  /**
   * Test Case to check save
   * throw error, if image to be loaded is not present.
   */
  @Test
  public void testSaveImageNotFound() {
    String command = "load images\\manhattan-small.png man\n " +
            "save test\\controller\\resultTest\\man.png man-horizontal";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check save
   * throw error, if file is incorrect.
   */
  @Test
  public void testSaveImageIncorrectFilePath() {
    String command = "load images\\manhattan-small.png man\n " +
            "save im\\man.png man";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Filepath provided is incorrect\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check command executed
   * even if there are whitespaces in between.
   */
  @Test
  public void testCommandWithWhiteSpaces() {
    String command = "load images\\manhattan-small.png          man\n";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "load executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check command still exists
   * even when user gives invalid command.
   */
  @Test
  public void testCommandStillWorksAfterInvalidComamd() {
    String command = "load images/manhattan-small.png\n load images" +
            "/manhattan-small.png man";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "Invalid command length\nload executed " +
            "successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check save throw
   * error, if file-extension is incorrect.
   */
  @Test
  public void testSaveImageExtenstionNot() {
    String command = "load images\\manhattan-small.png man\n " +
            "save images\\man.ajd man";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "This extension is not Supported\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if run command throw error
   * if the invalid syntax.
   */
  @Test
  public void testRun() {
    String command = "run";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "Command is not valid\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if run command throw error
   * if the invalid filepath.
   */
  @Test
  public void testRunFileNotFound() {
    String command = "run test\\controller\\ScriptFiles\\src.txt";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "File not found\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if run command throw error
   * if the filepath is not txt.
   */
  @Test
  public void testRunFileWithNoTXT() {
    String command = "run test\\controller\\ScriptFiles\\src.pdf";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "Provide txt File only\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if run script file works
   * correct when correct operations are given
   * on png image.
   */
  @Test
  public void testRunFilePng() {
    String command = "run test\\controller\\ScriptFiles\\filepng.txt";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "load executed successfully\n" +
            "red-component executed successfully\n" +
            "green-component executed successfully\n" +
            "blue-component executed successfully\n" +
            "value-component executed successfully\n" +
            "intensity-component executed successfully\n" +
            "luma-component executed successfully\n" +
            "horizontal-flip executed successfully\n" +
            "save executed successfully\n" +
            "vertical-flip executed successfully\n" +
            "save executed successfully\n" +
            "brighten executed successfully\n" +
            "save executed successfully\n" +
            "brighten executed successfully\n" +
            "save executed successfully\n" +
            "rgb-split executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "rgb-combine executed successfully\n" +
            "save executed successfully\n" +
            "blur executed successfully\n" +
            "save executed successfully\n" +
            "sharpen executed successfully\n" +
            "save executed successfully\n" +
            "sepia executed successfully\n" +
            "save executed successfully\n";
    assertEquals(expected, output);

  }

  /**
   * Test Case to check if run script file works
   * correct when correct operations are given
   * on Jpg image.
   */
  @Test
  public void testRunFileJpg() {
    String command = "run test\\controller\\ScriptFiles\\filejpg.txt";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "load executed successfully\n"
            + "red-component executed successfully\n" +
            "green-component executed successfully\n" +
            "blue-component executed successfully\n" +
            "value-component executed successfully\n" +
            "intensity-component executed successfully\n" +
            "luma-component executed successfully\n" +
            "horizontal-flip executed successfully\n" +
            "save executed successfully\n" +
            "vertical-flip executed successfully\n" +
            "save executed successfully\n" +
            "brighten executed successfully\n" +
            "save executed successfully\n" +
            "brighten executed successfully\n" +
            "save executed successfully\n" +
            "rgb-split executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "rgb-combine executed successfully\n" +
            "save executed successfully\n" +
            "blur executed successfully\n" +
            "save executed successfully\n" +
            "sharpen executed successfully\n" +
            "save executed successfully\n" +
            "sepia executed successfully\n" +
            "save executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if run script file works
   * correct when correct operations are given
   * on PPM image.
   */
  @Test
  public void testRunFilePPM() {
    String command = "run test\\controller\\ScriptFiles\\fileppm.txt";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "load executed successfully\n" +
            "red-component executed successfully\n" +
            "green-component executed successfully\n" +
            "blue-component executed successfully\n" +
            "value-component executed successfully\n" +
            "intensity-component executed successfully\n" +
            "luma-component executed successfully\n" +
            "horizontal-flip executed successfully\n" +
            "save executed successfully\n" +
            "vertical-flip executed successfully\n" +
            "save executed successfully\n" +
            "brighten executed successfully\n" +
            "save executed successfully\n" +
            "brighten executed successfully\n" +
            "save executed successfully\n" +
            "rgb-split executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "rgb-combine executed successfully\n" +
            "save executed successfully\n" +
            "blur executed successfully\n" +
            "save executed successfully\n" +
            "sharpen executed successfully\n" +
            "save executed successfully\n" +
            "sepia executed successfully\n" +
            "save executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if Run Script support multiple load.
   */
  @Test
  public void testRunLoadMultiple() {
    String command = "run test\\controller\\ScriptFiles\\fileMultipleLoad.txt";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "load executed successfully\n" +
            "load executed successfully\n" +
            "red-component executed successfully\n" +
            "red-component executed successfully\n" +
            "green-component executed successfully\n" +
            "green-component executed successfully\n" +
            "blue-component executed successfully\n" +
            "blue-component executed successfully\n" +
            "value-component executed successfully\n" +
            "value-component executed successfully\n" +
            "intensity-component executed successfully\n" +
            "intensity-component executed successfully\n" +
            "luma-component executed successfully\n" +
            "luma-component executed successfully\n" +
            "horizontal-flip executed successfully\n" +
            "horizontal-flip executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "vertical-flip executed successfully\n" +
            "vertical-flip executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "brighten executed successfully\n" +
            "brighten executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "brighten executed successfully\n" +
            "brighten executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "rgb-split executed successfully\n" +
            "rgb-split executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "rgb-combine executed successfully\n" +
            "rgb-combine executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "blur executed successfully\n" +
            "blur executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "sharpen executed successfully\n" +
            "sharpen executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "blur executed successfully\n" +
            "blur executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "sepia executed successfully\n" +
            "sepia executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "sharpen executed successfully\n" +
            "save executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if run script file throws
   * error when the command in it is invalid.
   */

  @Test
  public void testRunFileError() {
    String command = "run test\\controller\\ScriptFiles\\fileerror.txt";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "Invalid command length\n" +
            "Filepath provided is incorrect.\n" +
            "Extension of the image is not supported.\n" +
            "load executed successfully\n" +
            "Invalid Command.\n" +
            "The image to be processed is not present.\n" +
            "Invalid Command.\n" +
            "The image to be processed is not present.\n" +
            "Invalid Command.\n" +
            "The image to be processed is not present.\n" +
            "Invalid Command.\n" +
            "The image to be processed is not present.\n" +
            "Invalid Command.\n" +
            "The image to be processed is not present.\n" +
            "Invalid Command.\n" +
            "The image to be processed is not present.\n" +
            "Invalid command length\n" +
            "The image to be processed is not present.\n" +
            "Invalid command length\n" +
            "The image to be processed is not present.\n" +
            "Invalid command length\n" +
            "The image to be processed is not present.\n" +
            "Increment must be an integer.\n" +
            "The image to be processed is not present.\n" +
            "Increment must be an integer.\n" +
            "Invalid command length\n" +
            "The image to be processed is not present.\n" +
            "Invalid command length\n" +
            "The image to be processed is not present.\n" +
            "Invalid Command.\n" +
            "The image to be processed is not present.\n" +
            "Invalid Command.\n" +
            "The image to be processed is not present.\n" +
            "Invalid Command.\n" +
            "The image to be processed is not present.\n" +
            "Invalid command length\n" +
            "Filepath provided is incorrect\n" +
            "This extension is not Supported\n" +
            "Unknown command: flipvertical\n" +
            "Unknown command: sharp\n" +
            "Unknown command: \n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check that the interconversion of the
   * files is supported.
   */
  @Test
  public void testInterConverstion() {
    String command = "run test\\controller\\ScriptFiles\\fileconversion.txt";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "load executed successfully\n" +
            "load executed successfully\n" +
            "load executed successfully\n" +
            "brighten executed successfully\n" +
            "brighten executed successfully\n" +
            "brighten executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n";
    assertEquals(expected, output);

  }

  /**
   * Test Case to check with scirpt file have
   * both incorrect and correct command.
   */
  @Test
  public void testRunWithCorrectIncorrect() {
    String command = "run test\\controller\\ScriptFiles\\filecorrectIncorrect.txt";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "load executed successfully\n" +
            "Invalid Command.\n" +
            "green-component executed successfully\n" +
            "blue-component executed successfully\n" +
            "horizontal-flip executed successfully\n" +
            "Invalid command length\n" +
            "vertical-flip executed successfully\n" +
            "save executed successfully\n" +
            "brighten executed successfully\n" +
            "save executed successfully\n" +
            "Increment must be an integer.\n" +
            "The image to be processed is not present.\n" +
            "The image to be processed is not present.\n" +
            "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if controller gives error when a command
   * do defined is given.
   */
  @Test
  public void testUnknownCommand() {
    String command = "enhance new Img";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String exectued = cmd + "Unknown command: enhance\n";
    assertEquals(exectued, output);
  }


  /**
   * Test Case to check if the compression is
   * called correctly in controller.
   */
  @Test
  public void testCompress() {
    String command = "load images\\manhattan-small.png man\n" +
            "compress 90 man mancompress";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "compress executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if no value is given in compress
   * command.
   */
  @Test
  public void testCompressNoValue() {
    String command = "load images\\manhattan-small.png man\n" +
            "compress percent man mancompress";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be a numeric value\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if negative percentage is given in compress
   * command.
   */
  @Test
  public void testCompressNegativeValue() {
    String command = "load images\\manhattan-small.png man\n" +
            "compress -120 man mancompress";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if invalid percentage is given in compress
   * command.
   */
  @Test
  public void testCompressInvalidValue() {
    String command = "load images\\manhattan-small.png man\n" +
            "compress 120 man mancompress";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if compress command has
   * incorrect format.
   */
  @Test
  public void testCompressInvalidFormat() {
    String command = "load images\\manhattan-small.png man\n" +
            "compress man mancompress";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid command length\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check compress throws error when image to be
   * compressed is not present.
   */
  @Test
  public void testCompressWithoutImage() {
    String command = "load images\\manhattan-small.png man\n" +
            "compress 90 man3 mancompress";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the histogram is
   * called correctly in controller.
   */
  @Test
  public void testHistogram() {
    String command = "load images\\manhattan-small.png man\n" +
            "histogram man man-hist";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "histogram executed successfully\n";
    assertEquals(expected, output);
  }


  /**
   * Test Case to check if histogram command has
   * incorrect format.
   */
  @Test
  public void testHistogramInvalidFormat() {
    String command = "load images\\manhattan-small.png man\n" +
            "histogram man";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid command length\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check Histogram throws error when image to be
   * compressed is not present.
   */
  @Test
  public void testHistogramWithoutImage() {
    String command = "load images\\manhattan-small.png man\n" +
            "histogram man3 mancompress";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }


  /**
   * Test Case to check if the color-correct is
   * called correctly in controller.
   */
  @Test
  public void testColorCorrect() {
    String command = "load images\\manhattan-small.png man\n" +
            "color-correct man man-correct";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "color-correct executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if Color Correct command has
   * incorrect format.
   */
  @Test
  public void testColorCorrectInvalidFormat() {
    String command = "load images\\manhattan-small.png man\n" +
            "color-correct man";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid command length\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check Color-correct throws error when image to be
   * compressed is not present.
   */
  @Test
  public void testColorCorrectWithoutImage() {
    String command = "load images\\manhattan-small.png man\n" +
            "color-correct man3 mancompress";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with color-correct works correctly.
   */
  @Test
  public void testColorCorrectWithSplit() {
    String command = "load images\\manhattan-small.png man\n" +
            "color-correct man manlevel split 50";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "color-correct executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with no value
   * color-correct.
   */
  @Test
  public void testColorCorrectWithSplitNoValue() {
    String command = "load images\\manhattan-small.png man\n" +
            "color-correct man manlevel split";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid command length\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with not numeric value
   * color-correct.
   */
  @Test
  public void testColorCorrectWithSplitNoNumValue() {
    String command = "load images\\manhattan-small.png man\n" +
            "color-correct man manlevel split hundered";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be a number.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if error thrown if word is not split.
   */
  @Test
  public void testColorCorrectWithNoSplit() {
    String command = "load images\\manhattan-small.png man\n" +
            "color-correct man manlevel st 23";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the percentage is less than zero.
   */
  @Test
  public void testColorCorrectSplitInvalid() {
    String command = "load images\\manhattan-small.png man\n" +
            "color-correct man manlevel split -23";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the percentage is grater than hundered.
   */
  @Test
  public void testColorCorrectSplitValueMore100() {
    String command = "load images\\manhattan-small.png man\n" +
            "color-correct man manlevel split 123";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the levels-adjust is
   * called correctly in controller.
   */
  @Test
  public void testLevelsAdjust() {
    String command = "load images\\manhattan-small.png man\n" +
            "levels-adjust 20 100 255 man manlevel";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "levels-adjust executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if no value is given in levels adjust.
   */
  @Test
  public void testLevelsAdjustNoValue() {
    String command = "load images\\manhattan-small.png man\n" +
            "levels-adjust 20 100 fifty man manlevel\n" +
            "levels-adjust twenty 100 200 man manlevel \n" +
            "levels-adjust 20 hundered 200 man manlevel\n" +
            "levels-adjust twenty hundered hunderedone man manlevel ";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "B, M and W value should be Integral values\n" +
            "B, M and W value should be Integral values\n" +
            "B, M and W value should be Integral values\n" +
            "B, M and W value should be Integral values\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if negative values are given in Levels-adjust
   * command.
   */
  @Test
  public void testLevelsAdjustNegativeValue() {
    String command = "load images\\manhattan-small.png man\n" +
            "levels-adjust -20 100 255 man manlevel \n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "B,M and W value must be between 0 and 255\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if invalid values are given in Levels-adjust
   * command.
   */
  @Test
  public void testLevelsAdjustInvalidValue() {
    String command = "load images\\manhattan-small.png man\n" +
            "levels-adjust 2000 10000 2550000 man manlevel\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "B,M and W value must be between 0 and 255\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if values are not in
   * ascending order.
   */
  @Test
  public void testLevelsAdjustValueNotAscending() {
    String command = "load images\\manhattan-small.png man\n" +
            "levels-adjust 100 20 255 man manlevel\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "B,M and W value must be in ascending order\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if levels-adjust command has
   * incorrect format.
   */
  @Test
  public void testLevelsAdjustInvalidFormat() {
    String command = "load images\\manhattan-small.png man\n" +
            "levels-adjust 20 man mancompress";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid command length.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check levels-adjust throws error when image to be
   * processed is not present.
   */
  @Test
  public void testLevelsAdjustWithoutImage() {
    String command = "load images\\manhattan-small.png man\n" +
            "levels-adjust 20 100 255 man3 mancompress";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with levels-adjust works correctly.
   */
  @Test
  public void testLevelWithSplit() {
    String command = "load images\\manhattan-small.png man\n" +
            "levels-adjust 20 100 255 man manlevel split 50";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "levels-adjust executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with no value
   * levels-adjust.
   */
  @Test
  public void testLevelWithSplitNoValue() {
    String command = "load images\\manhattan-small.png man\n" +
            "levels-adjust 20 100 255 man manlevel split";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid command length.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with not numeric value
   * levels-adjust.
   */
  @Test
  public void testLevelWithSplitNoNumValue() {
    String command = "load images\\manhattan-small.png man\n" +
            "levels-adjust 20 100 255 man manlevel split hundered";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be a number.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if error thrown if word is not split.
   */
  @Test
  public void testLevelWithNoSplit() {
    String command = "load images\\manhattan-small.png man\n" +
            "levels-adjust 20 100 255 man manlevel st 23";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the percentage is less than zero.
   */
  @Test
  public void testLevelSplitInvalid() {
    String command = "load images\\manhattan-small.png man\n" +
            "levels-adjust 20 100 255 man manlevel split -23";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the percentage is grater than hundered.
   */
  @Test
  public void testLevelSplitValue() {
    String command = "load images\\manhattan-small.png man\n" +
            "levels-adjust 20 100 255 man manlevel split 123";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100.\n";
    assertEquals(expected, output);
  }
}