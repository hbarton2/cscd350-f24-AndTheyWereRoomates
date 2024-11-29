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

  public void handleUserInput(String input) {
    if (input.isBlank()) {
      return; // Ignore blank inputs
    }

    switch (input.toLowerCase()) {
      case "help" -> appendToTerminal(commandParser.getCommandList() + "\n");
      case "exit" -> {
        appendToTerminal("Exiting application...\n");
        Platform.exit();
      }
      case "clear" -> clearTerminal();
      default -> {
        CommandResult result = commandParser.parseCommand(input);
        displayResult(result);
      }
    }
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

  private void displayResult(CommandResult result) {
    if (result != null) {
      String message = result.isSuccess() ? "Success: " : "Error: ";
      appendToTerminal(message + result.getMessage() + "\n");
    }
  }

  public int getPromptPosition() {
    return terminalArea.getText().length();
  }
}
