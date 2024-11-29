package org.project.View;

import java.io.IOException;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.TerminalBuilder;
import org.project.Controller.CommandParser;
import org.project.Controller.CommandResult;
import org.project.Model.CommandRegistries;

public class CommandLineTerminal {

  private final CommandParser commandParser;
  private final LineReader lineReader;
  private final TextArea terminalArea;
  private boolean running = true;

  public CommandLineTerminal(CommandRegistries commands, TextArea terminalArea) throws IOException {
    this.commandParser = new CommandParser(commands);
    this.terminalArea = terminalArea;

    // Initialize JLine Terminal
    org.jline.terminal.Terminal terminal = TerminalBuilder.builder().system(true).build();

    // Fetch all command names from CommandRegistries for autocomplete
    List<String> commandList = commandParser.getAllCommandNames();

    // Initialize JLine LineReader with autocomplete support
    this.lineReader =
        LineReaderBuilder.builder()
            .terminal(terminal)
            .completer(new StringsCompleter(commandList)) // Provide raw command names
            .build();
  }

  public void launch() {
    appendToTerminal("Welcome to the UML Editor CLI.\n");
    appendToTerminal("Type 'help' to see available commands, or 'exit' to quit.\n");

    while (running) {
      try {
        String rawInput = lineReader.readLine("$ ");
        String sanitizedInput = sanitizeInput(rawInput);
        processCommand(sanitizedInput);
      } catch (Exception e) {
        appendToTerminal("Error: " + e.getMessage() + "\n");
      }
    }
  }

  private void processCommand(String input) {
    input = sanitizeInput(input);

    switch (input.toLowerCase()) {
      case "exit" -> handleExitCommand();
      case "help" -> handleHelpCommand();
      default -> {
        CommandResult result = commandParser.parseCommand(input);
        displayResult(result);
      }
    }
  }

  private String sanitizeInput(String input) {
    return input.replace("\\", "").trim();
  }

  private void handleExitCommand() {
    appendToTerminal("Terminating Application...\n");
    running = false;
  }

  private void handleHelpCommand() {
    appendToTerminal(commandParser.getCommandList() + "\n");
  }

  private void displayResult(CommandResult result) {
    if (result != null) {
      appendToTerminal((result.isSuccess() ? "Success: " : "Error: ") + result.getMessage() + "\n");
    }
  }

  private void appendToTerminal(String message) {
    Platform.runLater(() -> terminalArea.appendText(message));
  }
}
