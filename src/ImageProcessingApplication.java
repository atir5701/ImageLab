import controller.CommandReader;
import controller.ImageAppController;
import model.ImageOperations;
import model.Operations;

/**
 * This is the main class of the entire application.
 * This class is the entry point to the entire program.
 */

public class ImageProcessingApplication {

  /**
   * Main function to start the application.
   * Function initializes the objects of view, controller
   * and model.
   * From here the entire control is given to the controller class.
   * @param args the standard argument given to read data from
   *             command prompt.
   */
  public static void main(String[] args) {
    Operations operations = new ImageOperations();
    ImageAppController src = new CommandReader(operations,System.in,System.out);
    src.startApplication();
  }
}
