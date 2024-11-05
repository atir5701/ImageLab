import java.io.InputStreamReader;

import controller.CommandReader;
import controller.ImageAppController;
import model.ImageOperationsV2;
import model.OperationsV2;
import view.ProgramView;
import view.ViewHandler;

import java.io.Reader;
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
   *
   * @param args the standard argument given to read data from
   *             command prompt.
   */
  public static void main(String[] args) {
    OperationsV2 operations = new ImageOperationsV2();
    ProgramView view = new ViewHandler(System.out);
    ImageAppController src;
    if (args.length == 0) {
      src = new CommandReader(operations, new InputStreamReader(System.in), view);
    } else {
      StringBuilder nonInteractive = new StringBuilder();
      for (String arg : args) {
        nonInteractive.append(arg).append(" ");
      }
      nonInteractive.append("\n").append("quit");
      Reader in = new StringReader(nonInteractive.toString());
      src = new CommandReader(operations, in, view);
    }
    src.startApplication();
  }
}
