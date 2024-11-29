package org.project.Controller;

import java.util.Objects;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;

public class CommandLineInterfaceController {

  @FXML
  private TextArea terminalArea;

  private final String prompt = "$ "; // Command prompt
  private String currentInput = "";   // Tracks the current user input
  private int promptPosition = 0;     // Tracks the position of the latest prompt

  @FXML
  public void initialize() {

//    terminalArea.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());

    // Display welcome message and the first prompt
    appendToTerminal("Welcome to the UML Editor CLI\n");
    appendToTerminal("Type 'help' to see available commands, or 'exit' to quit.\n");
    appendToTerminal(prompt);

    // Set the prompt position
    promptPosition = terminalArea.getText().length();

    // Listen for key presses
    terminalArea.setOnKeyPressed(this::handleInput);

    // Disable direct edits to text outside the KeyEvent
    terminalArea.setEditable(false);
  }

  private void handleInput(KeyEvent event) {
    switch (event.getCode()) {
      case ENTER -> {
        // Process the command entered by the user
        processCommand(currentInput);
        currentInput = ""; // Reset input
        appendToTerminal(prompt); // Add a new prompt
        promptPosition = terminalArea.getText().length(); // Update prompt position
        event.consume(); // Prevent default ENTER behavior
      }
      case BACK_SPACE -> {
        // Handle backspace within the current input
        if (!currentInput.isEmpty()) {
          currentInput = currentInput.substring(0, currentInput.length() - 1);
          refreshTerminal(); // Redraw terminal with updated input
        }
        event.consume(); // Prevent default BACKSPACE behavior
      }
      case LEFT, UP, DOWN, RIGHT -> {
        // Disable cursor movement keys
        event.consume();
      }
      default -> {
        // Append valid characters to the current input
        String text = event.getText();
        if (text != null && text.length() == 1) {
          currentInput += text;
          refreshTerminal(); // Redraw terminal with updated input
        }
      }
    }
  }

  private void processCommand(String command) {
    // Display the user's command
    appendToTerminal(command + "\n");

    // Handle the command
    switch (command.toLowerCase()) {
      case "help" -> appendToTerminal("Available commands:\n - help: Show available commands\n - exit: Quit the application\n");
      case "exit" -> Platform.exit();
      case "clear" -> clearTerminal();
      default -> appendToTerminal("Unknown command: " + command + "\n");
    }
  }

  private void refreshTerminal() {
    // Redraw the terminal with the prompt and current input
    String fullText = terminalArea.getText(0, promptPosition) + currentInput;
    Platform.runLater(() -> {
      terminalArea.setText(fullText);
      terminalArea.positionCaret(fullText.length()); // Move caret to end
    });
  }

  private void appendToTerminal(String message) {
    Platform.runLater(() -> {
      terminalArea.appendText(message);
      promptPosition = terminalArea.getText().length(); // Update prompt position
      terminalArea.positionCaret(promptPosition); // Move caret to end
    });
  }

  private void clearTerminal() {
    Platform.runLater(() -> {
      terminalArea.clear();
      appendToTerminal(prompt); // Add the prompt after clearing
      promptPosition = terminalArea.getText().length();
    });
  }
}
