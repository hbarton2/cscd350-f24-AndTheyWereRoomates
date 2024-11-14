package org.project.View;

import java.util.Scanner;
import org.project.Controller.CommandParser;
import org.project.Controller.CommandResult;
import org.project.Model.CommandRegistry;

public class Terminal {

  private final CommandParser commandParser;
  private boolean running = true;
  private final Scanner scanner;

  public Terminal(CommandRegistry commandRegistry) {
    this.commandParser = new CommandParser(commandRegistry);
    this.scanner = new Scanner(System.in); // Initialize scanner here to avoid dependency on Main
  }

  public void launch() {
    System.out.println("Welcome to the UML Editor CLI.");
    System.out.println("Type 'help' to see available commands, or 'exit' to quit.");

    while (running) {
      System.out.print("$ ");
      String input = scanner.nextLine().trim();
      processCommand(input);  // Use processCommand method for clarity
    }

    scanner.close();
  }

  /**
   * Processes a user command, determining if it is a special command like 'exit' or 'help'
   * or passing it to the CommandParser for execution.
   * @param input the raw input command from the user
   */
  private void processCommand(String input) {
    CommandResult result = switch (input.toLowerCase()) {
      case "exit" -> handleExitCommand();
      case "help" -> handleHelpCommand();
      default -> commandParser.parseCommand(input);
    };

    if (result != null) {
      // If result is non-null, process display and any exit logic
      displayResult(result);
      if ("exit".equals(result.getCommandName()) && result.isSuccess()) {
        running = false;
      }
    }
  }

  private CommandResult handleExitCommand() {
    System.out.println("Terminating Application...");
    running = false;
    return new CommandResult(true, "Application exited.", "exit");
  }

  private CommandResult handleHelpCommand() {
    System.out.println(commandParser.getCommandList());
    return null; // Returning null here as 'help' doesn’t need a result for display
  }

  /**
   * Displays the result of a command execution.
   * @param result the CommandResult containing success status and message
   */
  private void displayResult(CommandResult result) {
    if (result.isSuccess()) {
      System.out.println("Success: " + result.getMessage());
    } else {
      System.out.println("Error: " + result.getMessage());
    }
  }
}