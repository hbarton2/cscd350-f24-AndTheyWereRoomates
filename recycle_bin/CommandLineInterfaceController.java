package org.project.Controller;

import java.util.Objects;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

public class CommandLineInterfaceController {

  @FXML private TextArea terminalArea;

  private final String prompt = "$ "; // Command prompt
  private String currentInput = ""; // Tracks the current user input
  private int promptPosition = 0; // Tracks the position of the latest prompt
  private boolean caretVisible = true; // Tracks the caret's visibility
  private Timeline caretBlinkTimeline; // Manages blinking caret behavior

  @FXML
  public void initialize() {
    // Disable default JavaFX styling
    System.setProperty("javafx.userAgentStylesheetUrl", "none");

    // Apply custom CSS styling
    terminalArea.getStylesheets()
      .add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());

    // Display welcome message and the first prompt
    appendToTerminal("Welcome to the UML Editor CLI\n");
    appendToTerminal("Type 'help' to see available commands, or 'exit' to quit.\n");
    appendToTerminal(prompt);

    // Set the prompt position
    promptPosition = terminalArea.getText().length();

    // Listen for key presses
    terminalArea.setOnKeyPressed(this::handleInput);

    // Start blinking caret
    startBlinkingCaret();

    // Allow text highlighting without interference
    terminalArea.setEditable(true);
  }

  private void handleInput(KeyEvent event) {
    switch (event.getCode()) {
      case ENTER -> {
        String command = currentInput.trim(); // Trim whitespace from user input
        appendToTerminal("\n"); // Move to a new line after the command
        processCommand(command); // Process the user command
        currentInput = ""; // Reset input
        appendToTerminal(prompt); // Add a new prompt
        promptPosition = terminalArea.getText().length(); // Update prompt position
        event.consume(); // Prevent default ENTER behavior
      }
      case BACK_SPACE -> {
        if (currentInput.isEmpty() || terminalArea.getCaretPosition() <= promptPosition) {
          event.consume(); // Prevent deletion beyond the prompt
        } else {
          currentInput = currentInput.substring(0, currentInput.length() - 1);
          refreshTerminal();
        }
      }
      case LEFT, UP, DOWN, RIGHT -> {
        event.consume(); // Disable cursor movement
      }
      default -> {
        // Add valid characters to the input
        String text = event.getText();
        if (text != null && !text.isEmpty()) {
          currentInput += text;
          refreshTerminal();
        }
      }
    }
  }

  private void processCommand(String command) {
    // Display the user's command
    appendToTerminal(command + "\n");

    // Handle the command
    switch (command.toLowerCase()) {
      case "help" -> appendToTerminal(
        "Available commands:\n - help: Show available commands\n - exit: Quit the application\n");
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
      terminalArea.positionCaret(terminalArea.getText().length()); // Move caret to end
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

  private void startBlinkingCaret() {
    caretBlinkTimeline = new Timeline(
      new KeyFrame(Duration.seconds(0.5), e -> toggleCaretVisibility()));
    caretBlinkTimeline.setCycleCount(Timeline.INDEFINITE);
    caretBlinkTimeline.play();
  }

  private void toggleCaretVisibility() {
    caretVisible = !caretVisible;
    refreshTerminal();
  }
}
