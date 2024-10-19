package controller.commands;

import model.Operations;


/**
 * A class that performs the rgb-combine operation on an
 * image. By performing the rgb-combine operation the class
 * combines the three images representing the red,green and
 * blue channels into a single image.
 */

public class RGBCombine extends AbstractCommandExecuter {
  String newImageName;
  String redImage;
  String greenImage;
  String blueImage;

  /**
   * Construct a RGBCombine command object.
   * Validate the command length and initialize the image
   * names.
   *
   * @param cmd           the command array obtained by splitting
   *                      input using space.
   * @param commandLength the expected length of command array.
   */
  public RGBCombine(String[] cmd, int commandLength) {
    this.validCommandLength(cmd.length, commandLength);
    this.newImageName = cmd[1];
    this.redImage = cmd[2];
    this.greenImage = cmd[3];
    this.blueImage = cmd[4];
  }

  /**
   * Execute the rgb-combine operation on the current set
   * of channel images.
   * The method first check if all image on which operation
   * is to be done in present in the system or not.
   *
   * @param operations The operation instance which is
   *                   used to call the combineRGB method
   *                   which is to be executed on the input
   *                   images.
   */
  @Override
  public void execute(Operations operations) {
    this.imageCheck(operations, this.redImage);
    this.imageCheck(operations, this.greenImage);
    this.imageCheck(operations, this.blueImage);
    operations.combineRGB(this.redImage, this.greenImage, this.blueImage, this.newImageName);
  }
}
