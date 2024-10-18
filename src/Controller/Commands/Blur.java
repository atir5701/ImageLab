package Controller.Commands;

import Model.Operations;

/**
 * A class that performs the blur operation on an
 * image. The blur is applied to the image specified
 * in the command and result is saved as a new image.
 */

public class Blur extends AbstractCommandExecuter{
  String currentImageName;
  String newImageName;

  /**
   * Construct a Blur command object.
   * Validate the command length and initialize the image
   * names.
   *
   * @param cmd the command array obtained by splitting
   *            input using space.
   * @param commandLength the expected length of command array.
   */
  public Blur(String[] cmd,int commandLength) {
    this.validCommandLength(cmd.length, commandLength);
    this.currentImageName = cmd[1];
    this.newImageName = cmd[2];
  }

  /**
   * Execute the blur operation on the current image.
   * The method first check if the image on which operation
   * is to be done in present in the system or not.
   *
   * @param operations The operation instance which is
   *                   used to call the blur method
   *                   which is to be executed on the input
   *                   image.
   */
  @Override
  public void execute(Operations operations) {
    this.imageCheck(operations,this.currentImageName);
    operations.blur(this.currentImageName,this.newImageName);
  }

}
