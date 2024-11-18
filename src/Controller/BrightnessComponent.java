package controller;

import model.OperationsV3;


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
  private final String maskImageName;

  /**
   * Construct a BrightnessComponent command object.
   * Validate the command length and initializes the image
   * names.
   * This constructor handles different command formats based on
   * the provided input.
   * It supports split and masking operations, with specific
   * validation and initialization steps for each case.
   * If the split operation is provided, the percentage is
   * set to the value provided by the user; otherwise,
   * it defaults to 100.
   * If a mask is provided, the mask image name is set,
   * and the masking operation is carried out accordingly.
   * A check is performed to ensure the mask image
   * exists if specified.
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
      this.maskImageName = null;
    } else if (this.validCommandLength(cmd.length, 5)) {
      this.currentImageName = cmd[1];
      this.newImageName = cmd[2];
      this.handler = cmd[0];
      this.maskImageName = null;
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
    } else if (this.validCommandLength(cmd.length, 4)) {
      this.currentImageName = cmd[1];
      this.newImageName = cmd[3];
      this.percentage = 100.00;
      this.maskImageName = cmd[2];
      this.handler = cmd[0];
    } else {
      throw new IllegalArgumentException("Invalid Command.");
    }
  }

  /**
   * Executes the brightness-component operation on the current image.
   * The method first checks if the image on which
   * the operation is to be performed is available in the system.
   * It handles both standard brightness-component operations and operations
   * involving image splitting and masking:
   * If the percentage is 100 and no mask image is provided,
   * the brightness-component operation is applied directly.
   * If the percentage is less than 100, the image is split.
   * The brightness-component operation is applied on one half,
   * and then the image is recombined with the remaining half.
   * If a mask image is provided, a masking operation is
   * applied and then the to the brightness-component image.
   * First a check is made is the mask image is present or not.
   *
   * @param operations the instance that provides the methods to perform the brightness
   *                   - component, split, and mask operations on the images.
   * @return true if the operation is completed successfully; false otherwise.
   * @throws IllegalArgumentException if the current image or mask
   *                                  image (if provided) is not found in the system.
   */

  @Override
  public boolean execute(OperationsV3 operations) {
    this.imageCheck(operations, this.currentImageName);
    if (this.percentage == 100.00 && this.maskImageName == null) {
      return operations.getBrightnessComponent(this.currentImageName,
              this.newImageName, this.handler);
    } else {
      String temp = this.newImageName + this.newImageName.hashCode();
      if(this.maskImageName != null) {
        this.imageCheck(operations, this.maskImageName);
        boolean t = operations.getBrightnessComponent(this.currentImageName, temp,this.handler);
        return operations.mask(this.currentImageName, temp, this.maskImageName,
                this.newImageName) & t;
      }
      operations.splitPreview(this.currentImageName, temp, this.percentage);
      boolean t = operations.getBrightnessComponent(temp, temp,this.handler);
      return operations.regain(this.currentImageName, temp, this.newImageName) & t;
    }
  }
}
