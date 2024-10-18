package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * The ScriptReader class is responsible for reading and executing
 * commands from a script file. It utilizes a CommandHandler to
 * process commands parsed from the file.
 * This class provides methods to read commands from a specified file
 * path, ensuring that the file is in the correct format and does not
 * contain comments.
 * It also includes a main method to facilitate execution.
 */

public class ScriptReader {
    Handler e;

  /**
   * Constructs a new ScriptReader instance.
   * This constructor initializes the CommandHandler instance
   * used to execute commands read from the script.
   */

    ScriptReader(){
      e = new CommandHandler();
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

    public void scriptReader(String filepath) throws IOException {
      File file = new File(filepath);
      BufferedReader br = new BufferedReader(new FileReader(file));
      String st;
      while ((st = br.readLine()) != null) {
        if(!st.isEmpty() && st.charAt(0)!='#'){
          st = st.trim();
          st = st.replaceAll("\\s+"," ");
          String [] tokens = st.split(" ");
          for(int i=0;i<tokens.length;i++){
            tokens[i] = tokens[i].trim();
          }
          tokens[0] = tokens[0].toLowerCase();
          e.readCommand(tokens);
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

    public void getScriptPath() throws IllegalArgumentException, IOException {
      Scanner scn = new Scanner(System.in);
      System.out.println("Enter Script file path : ");
      String script = scn.nextLine();
      String extension = script.substring(script.lastIndexOf(".")+1);
      if (!extension.equals("txt")){
        throw new IllegalArgumentException("Provide txt File only");
      }
      this.scriptReader(script);
    }

  /**
   * The main method that initiates the script reading process.
   * This method creates an instance of ScriptReader and
   * prompts the user to enter the script file path.
   *
   * @param args command-line arguments.
   * @throws IOException if an error occurs while reading the script file.
   */

    public static void main(String[] args) throws IOException {
      ScriptReader reader = new ScriptReader();
      reader.getScriptPath();
    }
}
