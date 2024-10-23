package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import model.Operations;


/**
 * The CommandReader class is responsible for reading and executing
 * commands from a script file or the command-line. It utilizes a CommandHandler to
 * process commands parsed from the file.
 * Class implements the ImageAppController interface.
 * This class provides methods to read commands from a specified file
 * path, ensuring that the file is in the correct format and does not
 * contain comments.
 */


public class CommandReader implements ImageAppController {
  private final CommandHandler handler;
  private final InputStream in;
  private final PrintStream out;

  /**
   * Constructs a new CommandReader instance.
   * This constructor initializes the CommandHandler instance
   * used to execute commands read from the script/command-line.
   */


  public CommandReader(Operations operations, InputStream in, PrintStream out) {
    handler = new CommandHandler(operations, out);
    this.in = in;
    this.out = out;
  }

  /**
   * Reads commands from the specified script file and executes them.
   * This method reads each line of the file, ignoring empty lines and
   * comments (lines starting with '#'). It trims whitespace, splits
   * the lines into tokens, and converts the command to lowercase before
   * passing it to the CommandHandler for execution.
   *
   * @param filepath the path to the script file to be read.
   * @throws IOException if an error occurs while reading the file.
   */

  private void scriptReader(String filepath) throws IOException {
    try {
      File file = new File(filepath);
      BufferedReader br = new BufferedReader(new FileReader(file));
      String st;
      while ((st = br.readLine()) != null) {
        if (!st.isEmpty() && st.charAt(0) != '#') {
          st = st.trim();
          st = st.replaceAll("\\s+", " ");
          String[] tokens = st.split(" ");
          for (int i = 0; i < tokens.length; i++) {
            tokens[i] = tokens[i].trim();
          }
          tokens[0] = tokens[0].toLowerCase();
          try {
            handler.readCommand(tokens);
          } catch (Exception e) {
            this.out.print(e.getMessage() + "\n");
          }
        }
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("File not found");
    }
  }

  /**
   * Prompts the user for a script file path and validates the input.
   * User can also provide the input from the command line also.
   * This method checks that the provided file has a .txt extension,
   * and if valid, invokes the scriptReader method to process
   * the commands in the file. If the file extension is incorrect,it
   * throws an IllegalArgumentException.
   *
   * @throws IllegalArgumentException if the provided file is not a
   *                                  .txt file.
   * @throws IOException              if an error occurs while reading
   *                                  the file.
   */

  private void getCommand() throws IllegalArgumentException, IOException {
    Scanner scn = new Scanner(this.in);
    while (scn.hasNextLine()) {
      String script = scn.nextLine();
      script = script.trim();
      script = script.replaceAll("\\s+", " ");
      String[] init = script.split(" ");
      if (!(init[0].equals("run"))) {
        try {
          handler.readCommand(init);
        } catch (Exception e) {
          this.out.print(e.getMessage() + "\n");
        }
      } else {
        try {
          if (init.length != 2) {
            throw new IllegalArgumentException("run command is not valid");
          }
          String path = init[1];
          String extension = path.substring(path.lastIndexOf(".") + 1);
          if (!extension.equals("txt")) {
            throw new IllegalArgumentException("Provide txt File only");
          }
          this.scriptReader(path);
        } catch (Exception e) {
          this.out.print(e.getMessage() + "\n");
        }
      }
    }
  }

  /**
   * Starts the application by loading the necessary resources,
   * such as the script path required for the application's execution.
   * This method initiates the process by invoking getCommand. If an error
   * occurs during this process (e.g., file not found, permission issues),
   * an IOException will be caught, and an appropriate error message
   * will be printed to the console.
   * Implementations should ensure proper error handling and provide
   * informative error messages to guide users when issues arise.
   */

  @Override
  public void startApplication() {
    try {
      this.out.print("Enter the Command:\n");
      this.getCommand();
    } catch (IOException e) {
      throw new IllegalArgumentException("");
    }
  }


}
