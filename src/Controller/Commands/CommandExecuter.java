package controller.commands;

import model.Operations;

/**
 * This interface defines the necessary method for
 * executing commands.
 * It ensures that the input command is validated
 * and the image on which operation needs to be
 * carried out exists.
 * If the command is valid then the command is
 * executed using the Operation Interface.
 */

public interface CommandExecuter {

  /**
   * Method to validate the length of the input
   * command.
   *
   * @param length1 the actual length of the command.
   * @param length2 the expected length of the
   *                command.
   * @throws IllegalArgumentException if the lengths do not match.
   */
  void validCommandLength(int length1, int length2)
          throws IllegalArgumentException;

  /**
   * Method to check if the image on which operations need to carried
   * out is present in the system or not.
   *
   * @param operations the instance of operation interface.
   * @param imageName  the name of image which is to be
   *                   checked.
   * @throws IllegalArgumentException if the image is not found.
   */
  void imageCheck(Operations operations, String imageName) throws IllegalArgumentException;

  /**
   * Method to execute the command provided as input.
   *
   * @param operations The operation instance which is
   *                   used to call the suitable method
   *                   which is to be executed on the input
   *                   image.
   */
  void execute(Operations operations);

}
