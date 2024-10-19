package controller.commands;

import model.Operations;


/**
 * A class that performs the task of obtaining the
 * Color Component from an image. The handler in the
 * class specified which specific component is to obtained
 * from the image.
 */

public class ColorComponent extends AbstractCommandExecuter {
  String currentImageName;
  String newImageName;
  String handler;

  /**
   * Construct a BrightnessComponent command object.
   * Validate the command length and initializes the image
   * names.
   *
   * @param cmd           the command array obtained by splitting
   *                      input using space.
   * @param commandLength the expected length of command array.
   */

  public ColorComponent(String[] cmd, int commandLength) {
    this.validCommandLength(cmd.length, commandLength);
    this.currentImageName = cmd[1];
    this.newImageName = cmd[2];
    this.handler = cmd[0];
  }

  /**
   * Execute the Color Component operation based on the specified
   * handle provided in the command.
   * The method first check if the image on which operation
   * is to be done in present in the system or not.
   *
   * @param operations The operation instance which is
   *                   used to call the suitable method
   *                   which is to be executed on the input
   *                   image.
   * @throws IllegalArgumentException if the specified handler is invalid.
   */
  @Override
  public void execute(Operations operations) throws IllegalArgumentException {
    this.imageCheck(operations, this.currentImageName);
    int color;
    switch (handler) {
      case "red-component":
        color = 0;
        break;
      case "green-component":
        color = 1;
        break;
      case "blue-component":
        color = 2;
        break;
      default:
        throw new IllegalArgumentException("Invalid command provided");
    }
    operations.getColorComponent(this.currentImageName, this.newImageName, color);
  }

}
