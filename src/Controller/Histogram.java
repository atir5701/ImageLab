package controller;

import model.OperationsV2;


class Histogram extends AbstractCommandExecuter {
  private final String currentImageName;
  private final String newImageName;

  Histogram(String[] cmd, int commandLength) {
    if (!this.validCommandLength(cmd.length, commandLength)) {
      throw new IllegalArgumentException("Invalid command length");
    }
    this.currentImageName = cmd[1];
    this.newImageName = cmd[2];
  }

  @Override
  public boolean execute(OperationsV2 operations) {
    this.imageCheck(operations, this.currentImageName);
    return operations.histogram(this.currentImageName, this.newImageName);
  }


}
