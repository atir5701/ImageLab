package controller;

import model.Operations;

/**
 * A class that performs the RGB-split operation on an
 * image. By performing the rgb-split operation on the
 * image we get 3 images each images corresponding to
 * a single channel in the original image.
 */


class RGBSplit extends AbstractCommandExecuter {
  String currentImageName;
  String redNewImage;
  String greenNewImage;
  String blueNewImage;

  /**
   * Construct RGB-split command object.
   * Validate the command length and initialize the image
   * names.
   *
   * @param cmd           the command array obtained by splitting
   *                      input using space.
   * @param commandLength the expected length of command array.
   */

  RGBSplit(String[] cmd, int commandLength) {
    this.validCommandLength(cmd.length, commandLength);
    this.currentImageName = cmd[1];
    this.redNewImage = cmd[2];
    this.greenNewImage = cmd[3];
    this.blueNewImage = cmd[4];
  }

  /**
   * Execute the rgb-split operation on the current image.
   * The method first check if the image on which operation
   * is to be done in present in the system or not.
   *
   * @param operations The operation instance which is
   *                   used to call the rgb-split method
   *                   which is to be executed on the input
   *                   image.
   * @return true if operation done successfully, else false.
   */
  @Override
  public boolean execute(Operations operations) {
    this.imageCheck(operations, this.currentImageName);
    return operations.splitRGB(this.currentImageName, this.redNewImage,
            this.greenNewImage, this.blueNewImage);
  }
}
