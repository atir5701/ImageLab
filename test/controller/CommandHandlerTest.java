package controller;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import model.ImageOperations;
import model.Operations;

import static org.junit.Assert.assertEquals;

/**
 * Class to perform all the testing related
 * to the Controller package.
 */

public class CommandHandlerTest {
  ImageAppController controller;
  Operations opr;
  String cmd = "Enter the Command:\n";
  String cmdload = "Enter the Command:\n" + "load executed successfully\n";
  ByteArrayOutputStream out;

  @Before
  public void setUp() {
    opr = new ImageOperations();
    out = new ByteArrayOutputStream();
  }

  /**
   * Test Case to check if the load method is
   * working correctly.
   */
  @Test
  public void testLoad() {
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC" +
            "\\images\\manhattan-small.png man";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
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
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC" +
            "\\images\\manhattan-small.png ";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "Invalid Command\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the load method
   * throws error if extension is not
   * supported.
   */
  @Test
  public void testLoadWithIncorrectExtension() {
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.oip man";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
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
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "img\\manhattan-small.ppm man";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
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
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\images" +
            "\\manhattan-small.png man\n brighten 100 man manbr";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
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
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\images" +
            "\\manhattan-small.png man\n brighten -100 man manbr";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
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
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n brighten 100.56 man manbr";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
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
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n brighten hundered man manbr";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
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
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n brighten 100 manbr";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check brighten throws error when image to be
   * brighten is not present in application.
   */
  @Test
  public void testBrightenWithoutImage() {
    String command = "brighten 100 man manbrighten";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
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
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n horizontal-flip man " +
            "man-horizontal-flip";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
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
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n horizontal-flip manbr";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check horizontal-flip throw error,
   * if image to be loaded is not present.
   */
  @Test
  public void testHorizontalFlipImageNotFound() {
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n horizontal-flip " +
            "manbr manhroizontal";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    System.setIn(in);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream o = new PrintStream(out);
    System.setOut(o);
    controller = new CommandReader(opr, in, o);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test case to check if vertical-flip is called
   * correctly.
   */
  @Test
  public void testVerticalFlip() {
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n vertical-flip  man " +
            "manbvflip";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    System.setIn(in);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream o = new PrintStream(out);
    System.setOut(o);
    controller = new CommandReader(opr, in, o);
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
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n vertical-flip manbr";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check horizontal-flip throw error,
   * if image to be loaded is not present.
   */
  @Test
  public void testVerticalFlipImageNotFound() {
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n vertical-flip manbr manhroizontal";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
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
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n blur man manblur";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
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
  public void testBlueInvalidCommand() {
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n blur manbr";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check blur throw error,
   * if image to be loaded is not present.
   */
  @Test
  public void testBlurImageNotFound() {
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n blur manbr manhroizontal";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the sharpen method is
   * called correctly.
   */
  @Test
  public void testSharpen() {
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n sharpen  man mansharpen";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
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
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n sharpen manbr";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check Sharpen throw error,
   * if image to be loaded is not present.
   */
  @Test
  public void testSharpenImageNotFound() {
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n sharpen manbr manhroizontal";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the sepia method is
   * called correctly.
   */
  @Test
  public void testSepia() {
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n sepia  man mansepia";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
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
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n sepia manbr";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check Sepia throw error,
   * if image to be loaded is not present.
   */
  @Test
  public void testSepiaImageNotFound() {
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n sepia manbr manhroizontal";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the brightness-component
   * method is called correctly.
   */
  @Test
  public void testBrightnessComponent() {
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n value-component  man manvalue" +
            "\n intensity-component man maninten\n luma-component man manluma";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
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
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n value-component man" +
            "\n intensity-component man   \n" + "luma-component man";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command\nInvalid Command\n" +
            "Invalid Command\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check brightnessComponent
   * throw error, if image to be loaded is not present.
   */
  @Test
  public void testBrightnessComponentImageNotFound() {
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n value-component " +
            "manbr manhroizontal\nintensity-component manbr manhorizontal "
            + "\n luma-component manbr manhorizontal";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n" +
            "The image to be processed is not present.\n" +
            "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the color-component
   * method is called correctly.
   */
  @Test
  public void testColorComponent() {
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n red-component  man manred" +
            "\n green-component man mangreen\n blue-component man manblue";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
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
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n red-component man" +
            "\n green-component man   \nblue-component man";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command\nInvalid Command\n" +
            "Invalid Command\n";
    assertEquals(expected, output);
  }


  /**
   * Test Case to check ColorComponent
   * throw error, if image to be loaded is not present.
   */
  @Test
  public void testColorComponentImageNotFound() {
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n red-component manbr " +
            "manhroizontal\n blue-component manbr manhorizontal \n " +
            "green-component manbr manhorizontal";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n" +
            "The image to be processed is not present.\n" +
            "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if rgb-split is called
   * correctly.
   */
  @Test
  public void testRGBSplit() {
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n rgb-split man " +
            "man-red man-green man-blue";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
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
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n rgb-split man" +
            "red green";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command\n";
    assertEquals(expected, output);
  }


  /**
   * Test Case to check rgb-split
   * throw error, if image to be loaded is not present.
   */
  @Test
  public void testRGBSplitImageNotFound() {
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n rgb-split man2 man-red "
            + "man-green man-blue";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
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
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n rgb-split man " +
            "man-red man-green man-blue\nrgb-combine " +
            "man-combine man-red man-green man-blue";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
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
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n rgb-split man " +
            "man-red man-green man-blue\nrgb-combine " +
            "man-combine man-red";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "rgb-split executed successfully\n" +
            "Invalid Command\n";
    assertEquals(expected, output);
  }


  /**
   * Test Case to check rgb-split
   * throw error, if image to be loaded is not present.
   */
  @Test
  public void testRGBCombineImageNotFound() {
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n rgb-split man " +
            "man-red man-green man-blue\nrgb-combine " +
            "man-combine man-r man-g man-b";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
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
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n save " +
            "D:\\Northeastern\\PDP\\Assignment-4-MVC\\images\\man.png man";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
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
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\"
            + "images\\manhattan-small.png man\n save man";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command\n";
    assertEquals(expected, output);
  }


  /**
   * Test Case to check save
   * throw error, if image to be loaded is not present.
   */
  @Test
  public void testSaveImageNotFound() {
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n save " +
            "D:\\Northeastern\\PDP\\Assignment-4-MVC\\images\\man.png " +
            "man-horizontal";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
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
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n save " +
            "D:\\Northeastern\\PDP\\Assignment-4-MVC\\im\\man.png " +
            "man";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
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
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png          man\n";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
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
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "Invalid Command\nload executed " +
            "successfully\n";
    assertEquals(expected, output);
  }


  /**
   * Test Case to check save throw
   * error, if file-extension is incorrect.
   */
  @Test
  public void testSaveImageExtenstionNot() {
    String command = "load D:\\Northeastern\\PDP\\Assignment-4-MVC\\" +
            "images\\manhattan-small.png man\n save " +
            "D:\\Northeastern\\PDP\\Assignment-4-MVC\\images\\man.ajd " +
            "man";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
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
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "run command is not valid\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if run command throw error
   * if the invalid filepath.
   */
  @Test
  public void testRunFileNotFound() {
    String command = "run D:\\Northeastern\\PDP\\src.txt";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
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
    String command = "run D:\\Northeastern\\PDP\\src.pdf";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
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
    String command = "run D:\\Northeastern\\PDP\\Assignment-4-MVC" +
            "\\test\\ScirptFiles\\filepng.txt";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
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
   * on Jpeg image.
   */
  @Test
  public void testRunFileJpeg() {
    String command = "run D:\\Northeastern\\PDP\\Assignment-4-MVC" + "\\test\\ScirptFiles\\filejpeg.txt";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
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
    String command = "run D:\\Northeastern\\PDP\\Assignment-4-MVC" +
            "\\test\\ScirptFiles\\fileppm.txt";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
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
    String command = "run D:\\Northeastern\\PDP\\Assignment-4-MVC" + "\\test\\ScirptFiles\\fileMultipleLoad.txt";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
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
            "sepia executed successfully\n" +
            "sepia executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if run script file throws
   * error when the command in it is invalid.
   */

  @Test
  public void testRunFileError() {
    String command = "run D:\\Northeastern\\PDP\\Assignment-4-MVC" + "\\test\\ScirptFiles\\fileerror.txt";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "Invalid Command\n" + "Filepath provided is incorrect.\n" + "Extension of the image is not supported.\n" + "Invalid Command\n" + "The image to be processed is not present.\n" + "Invalid Command\n" + "The image to be processed is not present.\n" + "Invalid Command\n" + "The image to be processed is not present.\n" + "Invalid Command\n" + "The image to be processed is not present.\n" + "Invalid Command\n" + "The image to be processed is not present.\n" + "Invalid Command\n" + "The image to be processed is not present.\n" + "Invalid Command\n" + "The image to be processed is not present.\n" + "Invalid Command\n" + "The image to be processed is not present.\n" + "Invalid Command\n" + "The image to be processed is not present.\n" + "Increment must be an integer.\n" + "The image to be processed is not present.\n" + "Invalid Command\n" + "The image to be processed is not present.\n" + "Invalid Command\n" + "The image to be processed is not present.\n" + "Invalid Command\n" + "The image to be processed is not present.\n" + "Invalid Command\n" + "The image to be processed is not present.\n" + "Invalid Command\n" + "The image to be processed is not present.\n" + "Invalid Command\n" + "The image to be processed is not present.\n" + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check that the interconversion of the
   * files is supported.
   */
  @Test
  public void testInterConverstion() {
    String command = "run D:\\Northeastern\\PDP\\Assignment-4-MVC" + "\\test\\ScirptFiles\\fileconversion.txt";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
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
    String command = "run D:\\Northeastern\\PDP\\Assignment-4-MVC" + "\\test\\ScirptFiles\\filecorrectIncorrect.txt";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "load executed successfully\n" +
            "Invalid Command\n" +
            "green-component executed successfully\n" +
            "blue-component executed successfully\n" +
            "horizontal-flip executed successfully\n" +
            "Invalid Command\n" +
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
    InputStream in = new ByteArrayInputStream(command.getBytes());
    PrintStream o = new PrintStream(out);
    controller = new CommandReader(opr, in, o);
    controller.startApplication();
    String output = out.toString();
    String exectued = cmd + "Unknown command: enhance\n";
    assertEquals(exectued, output);
  }

}