package controller;

import model.Operations;

/**
 * A class that performs the task of performing
 * Vertical Flip on an image. By performing
 * Vertical Flip on the image we get an image
 * which is rotated on x-axis.
 */

class VerticalFlip extends AbstractCommandExecuter {
  String currentImageName;
  String newImageName;

  /**
   * Construct a VerticalFlip command object.
   * Validate the command length and initialize the image
   * names.
   *
   * @param cmd           the command array obtained by splitting
   *                      input using space.
   * @param commandLength the expected length of command array.
   */

  VerticalFlip(String[] cmd, int commandLength) {
    this.validCommandLength(cmd.length, commandLength);
    this.currentImageName = cmd[1];
    this.newImageName = cmd[2];
  }


  /**
   * Execute the flip operation on the current image.
   * The method first check if the image on which operation
   * is to be done in present in the system or not.
   *
   * @param operations The operation instance which is
   *                   used to call the suitable method
   *                   which is to be executed on the input
   *                   image.
   * @return true if operation done successfully, else false.
   */
  @Override
  public boolean execute(Operations operations) {
    this.imageCheck(operations, this.currentImageName);
    return operations.verticalFlip(this.currentImageName, this.newImageName);
  }
}
