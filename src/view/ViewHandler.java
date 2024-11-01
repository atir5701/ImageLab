package view;

import java.io.IOException;

public class ViewHandler implements ProgramView{
  Appendable out;

  public ViewHandler(Appendable out) {
    this.out = out;
  }

  @Override
  public void setOutput(String output){
    try {
      this.out.append(output);
    } catch (IOException e) {
      throw new IllegalArgumentException("Something went wrong");
    }
  }
}
