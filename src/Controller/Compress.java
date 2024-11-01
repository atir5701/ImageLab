package controller;

import model.OperationsV2;

/**
 * A class that performs the task of obtaining the
 * compress version of an image.
 * Harr method for compression is used. This is a lossy
 * compression method.
 */


class Compress extends AbstractCommandExecuter{
  private final String currentImageName;
  private final String newImageName;
  private final double percentage;

  Compress(String[] cmd,int commandLength) throws NumberFormatException{
    if(!this.validCommandLength(cmd.length,commandLength)){
      throw new IllegalArgumentException("Invalid command length");
    }
    this.currentImageName = cmd[2];
    this.newImageName = cmd[3];
    try{
      this.percentage = Double.parseDouble(cmd[1]);
      if(this.percentage <  0.0 || this.percentage > 100.0){
        throw new IllegalArgumentException("Percentage must be between 0 and 100");
      }
    }catch(NumberFormatException e){
      throw new NumberFormatException("Percentage must be a numeric value");
    }
  }

  @Override
  public boolean execute(OperationsV2 operations) {
    this.imageCheck(operations,this.currentImageName);
    return operations.compressImage(this.currentImageName,this.newImageName,this.percentage);
  }
}
