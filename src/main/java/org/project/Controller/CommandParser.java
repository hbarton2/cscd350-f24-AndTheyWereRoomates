package org.project.Controller;

import java.util.Map;
import org.project.Model.CommandInfo;
import org.project.Model.CommandRegistry;

/**
 * Responsible for parsing user input and delegating command execution to the CommandRegistry.
 * Provides methods to parse commands and retrieve a formatted list of available commands.
 */
public class CommandParser {
  private final CommandRegistry commandRegistry;

  public CommandParser(CommandRegistry commandRegistry) {
    this.commandRegistry = commandRegistry;
  }

  /**
   * Parses and executes the command given as input.
   * @param input The raw user input containing the command and arguments.
   * @return A CommandResult indicating the success or failure of the command.
   */
  public CommandResult parseCommand(String input) {
    String[] tokens = input.trim().split("\\s+");

    if (tokens.length == 0) {
      return CommandResult.failure("Invalid command. Please provide a command.");
    }

    // Determine command and arguments
    String commandName = tokens[0] + (tokens.length > 1 ? " " + tokens[1] : "");
    String[] args = tokens.length > 2 ? extractArgs(tokens) : new String[0];

    // Execute command
    return commandRegistry.executeCommand(commandName, args);
  }

  private String[] extractArgs(String[] tokens) {
    String[] args = new String[tokens.length - 2];
    System.arraycopy(tokens, 2, args, 0, args.length);
    return args;
  }

  /**
   * Provides a formatted list of all available commands and their descriptions.
   * @return A formatted string listing all commands with syntax and descriptions.
   */
  public String getCommandList() {
    StringBuilder commandList = new StringBuilder("Available Commands:\n");

    for (Map.Entry<String, CommandInfo> entry : commandRegistry.getAllCommands().entrySet()) {
      String commandName = entry.getKey();
      CommandInfo commandInfo = entry.getValue();

      commandList.append("- ").append(commandName)
        .append(": ").append(commandInfo.getDescription())
        .append("\n  Syntax: ").append(commandInfo.getSyntax())
        .append("\n\n");
    }

    return commandList.toString();
  }
}
