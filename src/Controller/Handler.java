package controller;


/**
 * The Handler interface defines a method for processing
 * commands.
 * Implementing classes should provide the logic to
 * handle the commands input into the system.
 */

public interface Handler {

  /**
   * Reads and executes a command from the given input array.
   * This method processes the input command, and retrieves
   * the corresponding command executor from the command map.
   * If the command is recognized, it is executed with the specified
   * ImageOperations instance. If the command is not found in the
   * command map, an IllegalArgumentException is thrown.
   *
   * @param input An array of strings representing the command and its
   *              arguments.
   *              The first element is expected to be the command name.
   * @throws IllegalArgumentException if the command is unknown or not
   *                                  found in the command map.
   */
  void readCommand(String[] input) throws IllegalArgumentException;
}
