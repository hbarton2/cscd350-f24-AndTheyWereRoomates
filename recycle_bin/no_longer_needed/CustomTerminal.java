package org.project.Controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class CustomTerminal extends Application {

  private final String prompt = "$ "; // Command prompt
  private String currentInput = "";   // Tracks the current user input
  private int promptPosition = 0;     // Tracks the position of the latest prompt
  private TextArea terminalArea;      // The terminal TextArea

  @Override
  public void start(Stage primaryStage) {
    // Disable default JavaFX styles (Modena)
    System.setProperty("javafx.userAgentStylesheetUrl", "none");

    // Initialize the TextArea
    terminalArea = new TextArea();
    terminalArea.setStyle(
      "-fx-background-color: black;" +
        "-fx-text-fill: white;" +
        "-fx-caret-color: cyan;" +
        "-fx-font-family: Consolas;" +
        "-fx-font-size: 18px;"
    );

    terminalArea.setWrapText(true);
    terminalArea.setEditable(false); // Prevent direct edits outside of KeyEvents

    // Add the initial terminal messages
    appendToTerminal("Welcome to the UML Editor CLI\n");
    appendToTerminal("Type 'help' to see available commands, or 'exit' to quit.\n");
    appendToTerminal(prompt);

    // Track the prompt position
    promptPosition = terminalArea.getText().length();

    // Add event handler for key presses
    terminalArea.setOnKeyPressed(this::handleInput);

    // Force focus on the TextArea
    Platform.runLater(() -> terminalArea.requestFocus());

    // Set up the Stage and Scene
    Scene scene = new Scene(terminalArea, 1000, 700);
    primaryStage.setTitle("Custom Terminal");
    primaryStage.setScene(scene);
    primaryStage.show();
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
      terminalArea.setEditable(true); // Temporarily enable editing to move the caret
      terminalArea.setText(fullText);
      terminalArea.positionCaret(fullText.length()); // Move caret to end
      terminalArea.setEditable(false); // Disable editing again
    });
  }

  private void appendToTerminal(String message) {
    Platform.runLater(() -> {
      terminalArea.setEditable(true); // Temporarily enable editing to append text
      terminalArea.appendText(message);
      promptPosition = terminalArea.getText().length(); // Update prompt position
      terminalArea.positionCaret(promptPosition); // Move caret to end
      terminalArea.setEditable(false); // Disable editing again
    });
  }

  private void clearTerminal() {
    Platform.runLater(() -> {
      terminalArea.setEditable(true); // Temporarily enable editing to clear text
      terminalArea.clear();
      appendToTerminal(prompt); // Add the prompt after clearing
      promptPosition = terminalArea.getText().length();
      terminalArea.setEditable(false); // Disable editing again
    });
  }

  public static void main(String[] args) {
    launch(args);
  }
}
