package Controller.Commands;

import Model.Operations;

/**
 * A class that performs the Sepia operation on an
 * image. The sepia is applied to the image specified
 * in the command and result is saved as a new image.
 * After performing sepia a reddish tone is obtained on
 * the image.
 */

public class Sepia extends AbstractCommandExecuter{
  String currentImageName;
  String newImageName;

  /**
   * Construct a Sepia command object.
   * Validate the command length and initialize the image
   * names.
   *
   * @param cmd the command array obtained by splitting
   *            input using space.
   * @param commandLength the expected length of command array.
   */
  public Sepia(String[] cmd,int commandLength) {
    this.validCommandLength(cmd.length, commandLength);
    this.currentImageName = cmd[1];
    this.newImageName = cmd[2];
  }

  /**
   * Execute the Sepia operation on the current image.
   * The method first check if the image on which operation
   * is to be done in present in the system or not.
   *
   * @param operations The operation instance which is
   *                   used to call the sepia method
   *                   which is to be executed on the input
   *                   image.
   */
  @Override
  public void execute(Operations operations) {
    this.imageCheck(operations,this.currentImageName);
    operations.sepia(this.currentImageName,this.newImageName);
  }

}
