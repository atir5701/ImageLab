package Controller.Commands;

import Model.Operations;

/**
 * An Abstract class that implements the {@link CommandExecuter} interface.
 * This class provides the common validation methods for the various commands.
 * This class validates the length of the command and checks if the specified image
 * exists in the provided context. The execute method remains
 * abstract for subclasses to implement specific behavior.
 */

public abstract class AbstractCommandExecuter implements CommandExecuter{

  @Override
  public void validCommandLength(int length1,int length2) throws IllegalArgumentException{
     if(length1 != length2){
       throw new IllegalArgumentException("Invalid Command");
     }
  }

  @Override
  public void imageCheck(Operations operations,String imageName) throws IllegalArgumentException {
    if (!(operations.checkImage(imageName))) {
      throw new IllegalArgumentException("The image to be processed is not present");
    }
  }

  @Override
  abstract public void execute(Operations operations);


}
