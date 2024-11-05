package controller;

import model.OperationsV2;


/**
 * A class that performs the task of obtaining the
 * Brightness Component from an image. The handler in the
 * class specified which specific component is to obtained
 * from the image.
 */

class BrightnessComponent extends AbstractCommandExecuter {
  private final String currentImageName;
  private final String newImageName;
  private final String handler;
  private final double percentage;

  /**
   * Construct a BrightnessComponent command object.
   * Validate the command length and initializes the image
   * names.
   *
   * @param cmd           the command array obtained by splitting
   *                      input using space.
   * @param commandLength the expected length of command array.
   */
  BrightnessComponent(String[] cmd, int commandLength) {
    if (this.validCommandLength(cmd.length, commandLength)) {
      this.currentImageName = cmd[1];
      this.newImageName = cmd[2];
      this.handler = cmd[0];
      this.percentage = 100.00;
    } else if (this.validCommandLength(cmd.length, 5)) {
      this.currentImageName = cmd[1];
      this.newImageName = cmd[2];
      this.handler = cmd[0];
      if (!(cmd[3].equals("split"))) {
        throw new IllegalArgumentException("Invalid Command");
      }
      try {
        this.percentage = Double.parseDouble(cmd[4]);
      } catch (NumberFormatException e) {
        throw new NumberFormatException("Percentage must be a number.");
      }
      if (this.percentage < 0 || this.percentage > 100) {
        throw new IllegalArgumentException("Percentage must be between 0 and 100.");
      }
    } else {
      throw new IllegalArgumentException("Invalid Command.");
    }
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
   * @return true if operation done successfully, else false.
   */

  @Override
  public boolean execute(OperationsV2 operations) {
    this.imageCheck(operations, this.currentImageName);
    if (this.percentage == 100.00) {
      return operations.getBrightnessComponent(this.currentImageName,
              this.newImageName, this.handler);
    }
    String temp = this.newImageName + this.newImageName.hashCode();
    operations.splitPreview(this.currentImageName, temp, this.percentage);
    boolean check1 = operations.getBrightnessComponent(temp, temp, this.handler);
    return operations.regain(this.currentImageName, temp, this.newImageName) &
            check1;
  }
}
