import java.util.Scanner;

import Controller.CommandHandler;

public class Main {
  public static void main(String[] args) {
    Scanner scn = new Scanner(System.in);
    CommandHandler e = new CommandHandler();
    while (scn.hasNextLine()) {
      String cmd = scn.nextLine();
      String[] c = cmd.split(" ");
      e.readCommand(c);
    }
  }
}