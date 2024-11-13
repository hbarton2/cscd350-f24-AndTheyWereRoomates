package org.project.Controller;

import java.util.Scanner;
import org.project.Model.CommandRegistry;

/**
 * Responsible for parsing user input and execution of the commands of the CommandRegistry.
 * Processing a string input and splitting into a string list of tokens,
 * identifies and executes the provided command registry.
 */
public class CommandParser {
  private final CommandRegistry commandRegistry;

  public CommandParser(String jsonFilePath) {
    this.commandRegistry = new CommandRegistry(jsonFilePath);
  }

  public void parseCommand(String input) {
    String[] tokens = input.trim().split("\\s+");
    if (tokens.length < 1) {
      System.out.println("Invalid command. Please provide a command.");
      return;
    }

    String commandName = tokens[0];
    CommandRegistry.CommandInfo commandInfo = commandRegistry.getCommandInfo(commandName);

    if (commandInfo != null) {
      System.out.println("Executing command: " + commandInfo);
      commandRegistry.executeCommand(commandName, tokens);
    } else {
      System.out.println("Error: Command '" + commandName + "' not found.");
    }
  }
}
