package org.project.View;

import java.util.Scanner;
import org.project.Controller.CommandResult;
import org.project.Model.CommandRegistry;

public class Terminal {

  private final CommandRegistry commandRegistry;
  private boolean running = true;

  public Terminal(CommandRegistry commandRegistry) {
    this.commandRegistry = commandRegistry;
  }

  public void launch() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Type 'help' to see available commands.");

    while (running) {
      System.out.print("$ ");
      String input = scanner.nextLine().trim();

      if (input.equalsIgnoreCase("exit")) {
        System.out.println("Terminating Application...");
        running = false;
        continue;
      }

      // Split the input into command and arguments
      String[] tokens = input.split("\\s+");
      String command = tokens[0];
      String[] args = new String[tokens.length - 1];
      System.arraycopy(tokens, 1, args, 0, tokens.length - 1);

      // Execute command with command name and arguments
//      CommandResult result = commandRegistry.executeCommand(command, args);
//      displayResult(result);
    }

    scanner.close();
  }


  private void displayResult(CommandResult result) {
    if (result.isSuccess()) {
      System.out.println("Success: " + result.getMessage());
    } else {
      System.out.println("Error: " + result.getMessage());
    }
  }

//  public static void main(String[] args) {
//    CommandRegistry commandRegistry = new CommandRegistry();
//    Terminal terminal = new Terminal(commandRegistry);
//    terminal.launch();
//  }
}
