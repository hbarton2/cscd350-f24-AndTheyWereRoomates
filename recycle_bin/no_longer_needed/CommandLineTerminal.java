package org.project.View;

import java.io.IOException;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import org.project.Controller.CommandParser;
import org.project.Controller.CommandResult;
import org.project.Model.CommandRegistries;

public class CommandLineTerminal {

  private final CommandParser commandParser;
  private final TextArea terminalArea;
  private final String prompt = "$ ";

  public CommandLineTerminal(CommandRegistries commands, TextArea terminalArea) throws IOException {
    this.commandParser = new CommandParser(commands);
    this.terminalArea = terminalArea;
  }

  //  public CommandResult handleUserInput(String input) {
  //    if (input.isBlank()) {
  //      return CommandResult.failure("Input is blank."); // Handle blank input case
  //    }
  //
  //    // Parse and execute the command
  //    return commandParser.parseCommand(input);
  //  }
  public CommandResult handleUserInput(String input) {
    if (input.isBlank()) {
      return CommandResult.failure("Command is blank.");
    }

    // Parse and execute the command
    CommandResult result = commandParser.parseCommand(input);

    return result; // Return only the CommandResult, no additional logs
  }

  private void appendToTerminal(String message) {
    Platform.runLater(() -> terminalArea.appendText(message));
  }

  private void clearTerminal() {
    Platform.runLater(
        () -> {
          terminalArea.clear();
          appendToTerminal(prompt);
        });
  }
}
