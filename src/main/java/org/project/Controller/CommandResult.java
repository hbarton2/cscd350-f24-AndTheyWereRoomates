package org.project.Controller;

/**
 * Represents the results of a command execution, capturing information about the success of a
 * command, an optional message and command name. The class provides methods to create success and
 * failure results includes methods to retrieve the success status, message, and command name
 */
public class CommandResult {

  private final boolean success;
  private final String message;
  private final String commandName; // Optional field to store command name

  public CommandResult(boolean success, String message) {
    this(success, message, null);
  }

  public CommandResult(boolean success, String message, String commandName) {
    this.success = success;
    this.message = message;
    this.commandName = commandName;
  }

  public boolean isSuccess() {
    return success;
  }

  public String getMessage() {
    return message;
  }

  public String getCommandName() {
    return commandName;
  }

  @Override
  public String toString() {
    return "CommandResult{"
        + "success="
        + success
        + ", message='"
        + message
        + '\''
        + (commandName != null ? ", commandName='" + commandName + '\'' : "")
        + '}';
  }

  // Static factory methods
  public static CommandResult success(String message) {
    return new CommandResult(true, message);
  }

  public static CommandResult failure(String message) {
    return new CommandResult(false, message);
  }
}
