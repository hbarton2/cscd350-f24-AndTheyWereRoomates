package org.project;

import java.io.IOException;
import java.util.Scanner;
import org.project.Model.CommandRegistries;
import org.project.View.CLIView;
import org.project.View.CommandLineTerminal;
import org.project.View.GUIView;

public class Main {

  public static void main(String[] args) throws IOException {
    boolean answer = true;
    Scanner scanner = new Scanner(System.in);

    while (answer) {
      System.out.println("Welcome to the Unified Modeling Language Editor.");
      System.out.println("Please choose an interface to start:");
      System.out.println("   - Graphical User Interface    (GUI): Type 'gui'     or 'g'");
      System.out.println("OLD- Command Line Interface      (CLI): Type 'cli'     or 'c'");
      System.out.println("NEW- NEW Command Line Interface  (CLI): Type 'new cli' or 'n'");
      System.out.println("   - Terminate launcher:                Type 'exit'    or 'quit'");
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
          CommandRegistries commands = new CommandRegistries("src/main/resources/CLICommands.json");
          CommandLineTerminal commandLineTerminal = new CommandLineTerminal(commands);
          commandLineTerminal.launch();
        }
        case "exit", "quit" -> answer = false;
        default -> System.out.println("Please enter a valid option.");
      }
    }

    scanner.close();
    System.exit(0);
  }
}
