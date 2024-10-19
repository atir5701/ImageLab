package controller;

import java.io.IOException;


/**
 * The Handler interface defines a method for processing
 * commands.
 * Implementing classes should provide the logic to
 * handle the commands input into the system.
 */

public interface Handler {

  /**
   * Reads and executes a command.
   *
   * @param command an array of strings representing the
   *                command and its arguments.
   */
  void readCommand(String[] command) throws IOException;
}
