package org.project.View;

import java.io.IOException;
import java.util.List;
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
  private boolean running = true;

  public CommandLineTerminal(CommandRegistries commands) throws IOException {
    // Initialize CommandParser
    this.commandParser = new CommandParser(commands);

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
    System.out.println("Welcome to the UML Editor CLI.");
    System.out.println("Type 'help' to see available commands, or 'exit' to quit.");

    while (running) {
      try {
        // Read input from JLine with autocomplete
        String rawInput = lineReader.readLine("$ ");
        String sanitizedInput = sanitizeInput(rawInput); // Sanitize input
        processCommand(sanitizedInput);
      } catch (Exception e) {
        System.err.println("Error: " + e.getMessage());
      }
    }
  }

  private void processCommand(String input) {
    input = sanitizeInput(input); // Remove escape characters

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
    System.out.println("Terminating Application...");
    running = false;
  }

  private void handleHelpCommand() {
    System.out.println(commandParser.getCommandList());
  }

  private void displayResult(CommandResult result) {
    if (result != null) {
      if (result.isSuccess()) {
        System.out.println("Success: " + result.getMessage());
      } else {
        System.out.println("Error: " + result.getMessage());
      }
    }
  }
}