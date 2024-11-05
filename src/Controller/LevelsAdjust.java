package controller;

import model.OperationsV2;

class LevelsAdjust extends AbstractCommandExecuter {
  private final String currentImageName;
  private final String newImageName;
  private final int b;
  private final int m;
  private final int w;
  private final double percentage;

  LevelsAdjust(String[] cmd, int commandLength) throws NumberFormatException {
    if (this.validCommandLength(cmd.length, commandLength)) {
      this.currentImageName = cmd[4];
      this.newImageName = cmd[5];
      this.percentage = 100.00;
    } else if (this.validCommandLength(cmd.length, 8)) {
      this.currentImageName = cmd[4];
      this.newImageName = cmd[5];
      if (!cmd[6].equals("split")) {
        throw new IllegalArgumentException("Invalid Command");
      }
      try {
        this.percentage = Double.parseDouble(cmd[7]);
      } catch (NumberFormatException e) {
        throw new NumberFormatException("Percentage must be a number.");
      }
      if (this.percentage < 0 || this.percentage > 100) {
        throw new IllegalArgumentException("Percentage must be between 0 and 100.");
      }
    } else {
      throw new IllegalArgumentException("Invalid command length.");
    }
    try {
      this.b = Integer.parseInt(cmd[1]);
      this.m = Integer.parseInt(cmd[2]);
      this.w = Integer.parseInt(cmd[3]);
      if (!((b <= m && m <= w))) {
        throw new IllegalArgumentException("B,M and W value must be in ascending order");
      }
      if (!(b >= 0 && w <= 255)) {
        throw new IllegalArgumentException("B,M and W value must be between 0 and 255");
      }
    } catch (NumberFormatException e) {
      throw new NumberFormatException("B, M and W value should be Integral values");
    }
  }

  @Override
  public boolean execute(OperationsV2 operations) {
    this.imageCheck(operations, this.currentImageName);
    if (this.percentage == 100.00) {
      return operations.levelAdjustment(this.currentImageName, this.newImageName, this.
              b, this.m, this.w);
    }
    String temp = this.newImageName + this.newImageName.hashCode();
    operations.splitPreview(this.currentImageName, temp, this.percentage);
    boolean t = operations.levelAdjustment(temp, temp, this.b, this.m, this.w);
    return operations.regain(this.currentImageName, temp, this.newImageName) & t;

  }
}
