package org.project.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.project.Model.AutoComplete;
import org.project.Model.CommandInfo;
import org.project.Model.CommandRegistries;

/**
 * Responsible for parsing user input and delegating command execution to the CommandRegistries.
 * Provides methods to parse commands and retrieve a formatted list of available commands.
 */
public class CommandParser {
  private final CommandRegistries commandRegistries;
  private final AutoComplete autoComplete;

  public CommandParser(CommandRegistries commandRegistries) throws IOException {
    this.commandRegistries = commandRegistries;
    this.autoComplete = new AutoComplete(); // Initialize AutoComplete

    // Load all commands into AutoComplete
    for (String commandName : commandRegistries.getAllCommands().keySet()) {
      autoComplete.addCommand(commandName);
      System.out.println(commandName + " Loaded"); // TODO: TEST DEBUG
    }
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
    return commandRegistries.executeCommand(commandName, args);
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

    for (Map.Entry<String, CommandInfo> entry : commandRegistries.getAllCommands().entrySet()) {
      String commandName = entry.getKey();
      CommandInfo commandInfo = entry.getValue();

      commandList.append("- ").append(commandName)
        .append(": ").append(commandInfo.description())
        .append("\n  Syntax: ").append(commandInfo.syntax())
        .append("\n\n");
    }

    return commandList.toString();
  }

  /**
   * Handles autocomplete functionality.
   * @param partialInput The current input entered by the user.
   * @return A list of matching commands or a single completed command.
   */
//  public List<String> autocomplete(String partialInput) throws IOException {
//    return autoComplete.getSuggestions(partialInput);
//  }
  public List<String> autocomplete(String partialInput) throws IOException {
    System.out.println("Autocomplete Input: " + partialInput);
    List<String> suggestions = autoComplete.getSuggestions(partialInput);
    System.out.println("Suggestions: " + suggestions);
    return suggestions;
  }


  /**
   * Releases resources used by AutoComplete.
   */
  public void close() throws IOException {
    autoComplete.close(); // Release resources
  }

  public List<String> getAllCommandNames() {
    return new ArrayList<>(commandRegistries.getAllCommands().keySet());
  }
}
