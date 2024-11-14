package org.project;

import java.util.Scanner;
import org.project.Model.CommandRegistry;
import org.project.View.CLIView;
import org.project.View.GUIView;
import org.project.View.Terminal;

public class Main {

  public static void main(String[] args) {
    boolean answer = true;
    Scanner scanner = new Scanner(System.in);

    while (answer) {
      System.out.println("Welcome to the Unified Modeling Language Editor.");
      System.out.println("Please choose an interface to start:");
      System.out.println("  - Graphical User Interface    (GUI): Type 'gui'     or 'g'");
      System.out.println("  - Command Line Interface      (CLI): Type 'cli'     or 'c'");
      System.out.println("  - NEW Command Line Interface  (CLI): Type 'new cli' or 'n'");
      System.out.println("  - Terminate launcher:                Type 'exit'    or 'quit'");
      System.out.print("Your choice: ");
      String input = scanner.nextLine().toLowerCase();

      switch (input) {
        case "gui", "g" -> {
          answer = false;
          GUIView.main(args);
        }
        case "cli", "c" -> {
          answer = false;
          new CLIView().runMenu();
        }
        case "new cli", "n" -> {
          answer = false;
          CommandRegistry commands = new CommandRegistry("src/main/resources/CLICommands.json");
          Terminal terminal = new Terminal(commands);
          terminal.launch();
        }
        case "exit", "quit" -> answer = false;
        default -> System.out.println("Please enter a valid option.");
      }
    }

    scanner.close();
    System.exit(0);
  }
}
