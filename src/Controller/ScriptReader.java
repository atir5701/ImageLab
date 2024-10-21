package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import model.Operations;

/**
 * The ScriptReader class is responsible for reading and executing
 * commands from a script file. It utilizes a CommandHandler to
 * process commands parsed from the file.
 * This class provides methods to read commands from a specified file
 * path, ensuring that the file is in the correct format and does not
 * contain comments.
 */

public class ScriptReader {
  Handler handle;

  /**
   * Constructs a new ScriptReader instance.
   * This constructor initializes the CommandHandler instance
   * used to execute commands read from the script.
   */

  public ScriptReader(Operations operations) {
    handle = new CommandHandler(operations);
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
        handle.readCommand(tokens);
      }
    }
  }

  /**
   * Prompts the user for a script file path and validates the input.
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

  private void getScriptPath() throws IllegalArgumentException, IOException {
    Scanner scn = new Scanner(System.in);
    System.out.println("Enter the Command : ");
    String script = scn.nextLine();
    script = script.trim();
    script = script.replaceAll("\\s+", " ");
    String[] init = script.split(" ");
    if (!(init[0].equals("run"))) {
      throw new IllegalArgumentException("Invalid Command Provided to Run the Script");
    }
    String path = init[1];
    String extension = path.substring(path.lastIndexOf(".") + 1);
    if (!extension.equals("txt")) {
      throw new IllegalArgumentException("Provide txt File only");
    }
    this.scriptReader(path);
  }

  /**
   * Method to start the Application by getting the script path.
   * This method invokes the getScriptPath() method, which
   * involve reading from a file. If an error occurs during this process,
   * an IOException will be caught and an error message will be printed to
   * the console.
   */

  public void startApplication() {
    try {
      this.getScriptPath();
    } catch (IOException e) {
      System.out.println("Error in reading script file");
    }
  }

}
