package controller;

import model.OperationsV2;


/**
 * A class that performs the task of obtaining the
 * Color Component from an image. The handler in the
 * class specified which specific component is to obtained
 * from the image.
 */

class ColorComponent extends AbstractCommandExecuter {
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

  ColorComponent(String[] cmd, int commandLength) {
    if(this.validCommandLength(cmd.length, commandLength)){
      this.handler = cmd[0];
      this.currentImageName = cmd[1];
      this.newImageName = cmd[2];
      this.percentage = 100.00;
    }else if(this.validCommandLength(cmd.length, 5)){
      this.handler = cmd[0];
      this.currentImageName = cmd[1];
      this.newImageName = cmd[2];
      if(!(cmd[3].equals("split"))){
        throw new IllegalArgumentException("Invalid Command");
      }
      try{
        this.percentage = Double.parseDouble(cmd[4]);
      }catch(NumberFormatException e) {
        throw new NumberFormatException("Percentage must be a number.");
      }
      if (this.percentage < 0 || this.percentage > 100) {
        throw new IllegalArgumentException("Percentage must be between 0 and 100.");
      }
    }else{
      throw new IllegalArgumentException("Invalid Command.");
    }
  }

  /**
   * Execute the Color Component operation based on the specified
   * handle provided in the command.
   * The method first check if the image on which operation
   * is to be done in present in the system or not.
   *
   * @param operations The operation instance which is
   *                   used to call the suitable method
   *                   which is to be executed on the input
   *                   image.
   * @return true if operation done successfully, else false.
   * @throws IllegalArgumentException if the specified handler is invalid.
   */
  @Override
  public boolean execute(OperationsV2 operations) throws IllegalArgumentException {
    this.imageCheck(operations, this.currentImageName);
    int color;
    switch (handler) {
      case "red-component":
        color = 0;
        break;
      case "green-component":
        color = 1;
        break;
      case "blue-component":
        color = 2;
        break;
      default:
        throw new IllegalArgumentException("Invalid command provided");
    }
    if(this.percentage == 100.00) {
      return operations.getColorComponent(this.currentImageName, this.newImageName, color);
    }
    operations.splitPreview(this.currentImageName,this.newImageName,this.percentage);
    boolean t = operations.getColorComponent(this.currentImageName, this.newImageName, color);
    return operations.regain(this.currentImageName, this.newImageName) &
            t;
  }

}
