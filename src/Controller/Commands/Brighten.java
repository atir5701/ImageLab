package Controller.Commands;

import Model.Operations;

/**
 * A class that performs the brightened operation on an
 * image. The brightened is applied to the image specified
 * in the command and result is saved as a new image.
 * The value by which image is to be brightened can be
 * positive or negative and based on this image is either
 * brightened or darkened respectively.
 */

public class Brighten extends AbstractCommandExecuter {
  private final String currentImageName;
  private final String newImageName;
  private int increment;

  /**
   * Construct a Brighten command object.
   * Validate the command length and initialize the image
   * names.
   *
   * @param cmd the command array obtained by splitting
   *            input using space.
   * @param commandLength the expected length of command array.
   * @throws NumberFormatException if the brightness increment cannot
   *                               be parsed as integer.
   */
  public Brighten(String[] cmd,int commandLength) throws NumberFormatException{
    this.validCommandLength(cmd.length,commandLength);
    this.currentImageName = cmd[2];
    this.newImageName = cmd[3];

    try{
      this.increment = Integer.parseInt(cmd[1]);
    }catch (NumberFormatException e){
      System.out.println("Invalid increment value");
    }
  }

  /**
   * Execute the brighten operation on the current image.
   * The method first check if the image on which operation
   * is to be done in present in the system or not.
   *
   * @param operations The operation instance which is
   *                   used to call the brighten method
   *                   which is to be executed on the input
   *                   image.
   */

  @Override
  public void execute(Operations operations) {
    this.imageCheck(operations,this.currentImageName);
    operations.brighten(this.currentImageName, this.newImageName, this.increment);
  }
}
