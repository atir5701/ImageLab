package controller;

import model.OperationsV2;

/**
 * A class that performs the sharpen operation on an
 * image. The Sharpen is applied to the image specified
 * in the command and result is saved as a new image.
 */

class Sharpen extends AbstractCommandExecuter {
  private final String currentImageName;
  private final String newImageName;
  private final double percentage;

  /**
   * Construct a Sharpen command object.
   * Validate the command length and initialize the image
   * names.
   * As this command supports the split operation also so two check are made for
   * the command length. If split is provided then percentage
   * is set to the value provided by user else the value is set to 100.
   *
   * @param cmd           the command array obtained by splitting
   *                      input using space.
   * @param commandLength the expected length of command array.
   */
  Sharpen(String[] cmd, int commandLength) {
    if (this.validCommandLength(cmd.length, commandLength)) {
      this.currentImageName = cmd[1];
      this.newImageName = cmd[2];
      this.percentage = 100.00;
    } else if (this.validCommandLength(cmd.length, 5)) {
      this.currentImageName = cmd[1];
      this.newImageName = cmd[2];
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
   * Execute the blur operation on the current image.
   * The method first check if the image on which operation
   * is to be done in present in the system or not.
   * For the split operation a check if done on the value of the percentage,
   * if the percentage is 100 then directly levels-adjust is applied.
   * Else first the image is split, after split the operation is done on split
   * half and at end the image is combines with the remaining half.
   *
   * @param operations The operation instance which is
   *                   used to call the sharpen method
   *                   which is to be executed on the input
   *                   image.
   * @return true if operation done successfully, else false.
   */
  @Override
  public boolean execute(OperationsV2 operations) {
    this.imageCheck(operations, this.currentImageName);
    if (this.percentage == 100.00) {
      return operations.sharpen(this.currentImageName, this.newImageName);
    }
    String temp = this.newImageName + this.newImageName.hashCode();
    operations.splitPreview(this.currentImageName, temp, this.percentage);
    boolean t = operations.sharpen(temp, temp);
    return operations.regain(this.currentImageName, temp, this.newImageName) & t;
  }

}
