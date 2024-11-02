package controller;

import model.OperationsV2;

/**
 * A class that performs the task of performing the
 * Color Correct on an image.
 * By performing color-correct on an image we
 * tend to make all the peaks in the all channels
 * lie at same intensity-value.
 * If there are two peaks in the channel then we take
 * the peak as the highest intensity value.
 */


class ColorCorrect extends AbstractCommandExecuter{
  private final String currentImageName;
  private final String newImageName;
  private final double percentage;

  ColorCorrect(String[] cmd,int commandLength){
    if(this.validCommandLength(cmd.length, commandLength)) {
      this.currentImageName = cmd[1];
      this.newImageName = cmd[2];
      this.percentage = 100;
    }else if(this.validCommandLength(cmd.length,5)){
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
      throw new IllegalArgumentException("Invalid command length");
    }
  }

  @Override
  public boolean execute(OperationsV2 operations) {
    this.imageCheck(operations, this.currentImageName);
    if (this.percentage == 100.00) {
      return operations.colorCorrection(this.currentImageName, this.newImageName);
    }
    String temp = this.newImageName+ this.newImageName.hashCode();
    operations.splitPreview(this.currentImageName, temp, this.percentage);
    boolean t = operations.colorCorrection(temp,temp);
    return operations.regain(this.currentImageName,temp,this.newImageName) & t;
  }
}
