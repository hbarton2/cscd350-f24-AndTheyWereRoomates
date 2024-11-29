package org.project.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;

public class CommandLineInterfaceController {

  @FXML private TextArea terminalArea;

  private final String prompt = "$ "; // Command prompt
  private String currentInput = ""; // Tracks the current user input
  private int promptPosition = 0; // Tracks the position of the latest prompt
  private final List<String> commandHistory = new ArrayList<>(); // Stores command history
  private int historyIndex = -1; // Tracks current position in command history

  @FXML
  public void initialize() {
    // Disable default JavaFX styling
    System.setProperty("javafx.userAgentStylesheetUrl", "none");

    // Apply custom CSS styling
    terminalArea
        .getStylesheets()
        .add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());

    // Display welcome message and the first prompt
    appendToTerminal("Welcome to the UML Editor CLI\n");
    appendToTerminal("Type 'help' to see available commands, or 'exit' to quit.\n");
    appendToTerminal(prompt);

    // Set the prompt position
    promptPosition = terminalArea.getText().length();

    // Listen for key presses
    terminalArea.setOnKeyPressed(this::handleInput);

    // Allow caret and focus management
    terminalArea.setEditable(true);
    terminalArea.requestFocus();
  }

  private void handleInput(KeyEvent event) {
    String text = event.getText();

    switch (event.getCode()) {
      case ENTER -> processCommand(currentInput.trim());
      case BACK_SPACE -> handleBackspace(event);
      case UP -> navigateHistory(-1);
      case DOWN -> navigateHistory(1);
      case LEFT, RIGHT -> event.consume(); // Disable cursor movement
      default -> {
        if (text != null && !text.isEmpty()) {
          currentInput += text;
          refreshTerminal();
        }
        event.consume(); // Prevent duplicate input
      }
    }
  }

  private void handleBackspace(KeyEvent event) {
    if (currentInput.isEmpty() || terminalArea.getCaretPosition() <= promptPosition) {
      event.consume(); // Prevent deletion beyond the prompt
    } else {
      currentInput = currentInput.substring(0, currentInput.length() - 1);
      refreshTerminal();
    }
  }

  private void navigateHistory(int direction) {
    if (commandHistory.isEmpty()) {
      return; // No history to navigate
    }

    // Adjust history index
    historyIndex += direction;

    // Clamp the history index within bounds
    if (historyIndex < 0) {
      historyIndex = 0;
    } else if (historyIndex >= commandHistory.size()) {
      historyIndex = commandHistory.size();
      currentInput = ""; // Blank input for beyond the last command
      refreshTerminal();
      return;
    }

    // Update current input with the selected command
    currentInput = commandHistory.get(historyIndex);
    refreshTerminal();
  }

  private void processCommand(String command) {
    appendToTerminal(command + "\n");

    if (!command.isEmpty()) {
      commandHistory.add(command); // Add to history
      historyIndex = commandHistory.size(); // Reset history index
    }

    switch (command.toLowerCase()) {
      case "help" -> appendToTerminal(
          "Available commands:\n - help: Show available commands\n - exit: Quit the application\n");
      case "exit" -> Platform.exit();
      case "clear" -> clearTerminal();
      default -> appendToTerminal("Unknown command: " + command + "\n");
    }

    currentInput = ""; // Reset input
    appendToTerminal(prompt); // Add a new prompt
    promptPosition = terminalArea.getText().length(); // Update prompt position
  }

  private void refreshTerminal() {
    String fullText = terminalArea.getText(0, promptPosition) + currentInput;

    Platform.runLater(
        () -> {
          terminalArea.setText(fullText);
          terminalArea.positionCaret(terminalArea.getText().length());
        });
  }

  private void appendToTerminal(String message) {
    Platform.runLater(
        () -> {
          terminalArea.appendText(message);
          promptPosition = terminalArea.getText().length();
          terminalArea.positionCaret(promptPosition);
        });
  }

  private void clearTerminal() {
    Platform.runLater(
        () -> {
          terminalArea.clear();
          appendToTerminal(prompt);
          promptPosition = terminalArea.getText().length();
        });
  }
}
