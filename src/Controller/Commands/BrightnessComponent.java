package Controller.Commands;

import Model.Operations;

/**
 * A class that performs the task of obtaining the
 * Brightness Component from an image. The handler in the
 * class specified which specific component is to obtained
 * from the image.
 */

public class BrightnessComponent extends AbstractCommandExecuter {
  String currentImageName;
  String newImageName;
  String handler;

  /**
   * Construct a BrightnessComponent command object.
   * Validate the command length and initializes the image
   * names.
   *
   * @param cmd the command array obtained by splitting
   *            input using space.
   * @param commandLength the expected length of command array.
   */
  public BrightnessComponent(String[] cmd,int commandLength) {
    this.validCommandLength(cmd.length,commandLength);
    this.currentImageName = cmd[1];
    this.newImageName = cmd[2];
    this.handler = cmd[0];
    System.out.println(this.currentImageName);
    System.out.println(this.newImageName);
    System.out.println(this.handler);
  }

  /**
   * Execute the BrightnessComponent operation on the current image.
   * The method first check if the image on which operation
   * is to be done in present in the system or not.
   * We pass a handler which denotes which specific Brightness Component
   * is to be obtained from the input image.
   *
   * @param operations The operation instance which is
   *                   used to call the brighten method
   *                   which is to be executed on the input
   *                   image.
   */
  @Override
  public void execute(Operations operations) {
    this.imageCheck(operations,this.currentImageName);
    operations.getBrightnessComponent(this.currentImageName,this.newImageName,this.handler);
  }
}
