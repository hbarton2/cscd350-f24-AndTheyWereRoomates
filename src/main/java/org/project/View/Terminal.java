package org.project.View;

import java.util.Scanner;
import org.project.Controller.CommandParser;
import org.project.Controller.CommandResult;
import org.project.Model.CommandRegistry;

/**
 * The Terminal class serves as the interface for the Command Line Interface (CLI)
 * of the UML editor. It captures user input, passes commands to the CommandParser,
 * and displays results or errors to the user.
 */
public class Terminal {

  private final CommandParser commandParser;
  private boolean running = true;

  public Terminal(CommandRegistry commandRegistry) {
    this.commandParser = new CommandParser(commandRegistry);
  }

  /**
   * Launches the Terminal CLI, capturing and executing commands until the user exits.
   */
  public void launch() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Welcome to the UML Editor CLI.");
    System.out.println("Type 'help' to see available commands, or 'exit' to quit.");

    while (running) {
      System.out.print("$ ");
      String input = scanner.nextLine().trim();

      // Handling 'exit' command within the loop
      if (input.equalsIgnoreCase("exit")) {
        running = false;
        System.out.println("Terminating Application...");
        break;
      }

      // Handling 'help' command to display available commands
      if (input.equalsIgnoreCase("help")) {
        System.out.println(commandParser.getCommandList()); // Assuming CommandParser provides a command list
        continue;
      }

      // Parsing and executing the command through CommandParser
      CommandResult result = commandParser.parseCommand(input);
      if ("exit".equals(result.getCommandName()) && result.isSuccess()) {
        running = false;
      }

      displayResult(result);
    }

    scanner.close();
  }

  /**
   * Displays the result of a command execution.
   * @param result CommandResult instance with success or error messages.
   */
  private void displayResult(CommandResult result) {
    if (result.isSuccess()) {
      System.out.println("Success: " + result.getMessage());
    } else {
      System.out.println("Error: " + result.getMessage());
    }
  }
}
