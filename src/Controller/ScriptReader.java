package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ScriptReader {
    CommandHandler e;
    ScriptReader(){
      e = new CommandHandler();
    }

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


    public static void main(String[] args) throws IOException {
      ScriptReader reader = new ScriptReader();
      reader.getScriptPath();
    }
}
