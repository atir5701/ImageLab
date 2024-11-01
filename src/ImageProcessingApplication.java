import java.io.InputStreamReader;

import controller.CommandReader;
import controller.ImageAppController;
import model.ImageOperationsV2;
import model.OperationsV2;
import java.io.StringReader;

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
    OperationsV2 operations = new ImageOperationsV2();
    ImageAppController src = new CommandReader(operations,new InputStreamReader(System.in),System.out);
    if (args.length == 0) {
      src.startApplication();
    }else{
      src.startApplicationWithFile(args);
    }
  }
}
