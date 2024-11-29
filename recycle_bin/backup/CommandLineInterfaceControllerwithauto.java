package org.project.Controller;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Objects;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import org.project.Model.AutoComplete;
import org.project.Model.CommandRegistries;
import org.project.View.CommandLineTerminal;
import org.project.View.TextAreaOutputStream;

public class CommandLineInterfaceController {

  @FXML private TextArea terminalArea;

  private CommandLineTerminal cliTerminal;
  private AutoComplete autoComplete; // For autocomplete functionality
  private String currentInput = ""; // Tracks the current user input
  private int promptPosition = 0; // Tracks the position of the latest prompt

  @FXML
  public void initialize() throws IOException {
    // Disable default JavaFX styling
    System.setProperty("javafx.userAgentStylesheetUrl", "none");

    // Apply custom CSS styling
    terminalArea
      .getStylesheets()
      .add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());

    // Redirect System.out to the terminalArea
    System.setOut(new PrintStream(new TextAreaOutputStream(terminalArea)));

    // Optional: Redirect System.err if needed
    System.setErr(new PrintStream(new TextAreaOutputStream(terminalArea)));

    // Initialize the CLI logic and AutoComplete
    CommandRegistries commandRegistries =
      new CommandRegistries("src/main/resources/CLICommands.json");
    cliTerminal = new CommandLineTerminal(commandRegistries, terminalArea);
    autoComplete = new AutoComplete();

    // Populate autocomplete suggestions
    commandRegistries.getAllCommandNames().forEach((key, value) -> {
      try {
        String command = (String) key;
        autoComplete.addCommand(command);
      } catch (IOException e) {
        System.err.println("Error adding command to AutoComplete: " + e.getMessage());
      }
    });

    // Display welcome message and the first prompt
    appendToTerminal("Welcome to the UML Editor CLI.\n");
    appendToTerminal("Type 'help' to see available commands, or 'exit' to quit.\n");
    appendPrompt();

    // Listen for user key inputs
    terminalArea.setOnKeyPressed(this::handleInput);

    // Allow caret and focus management
    terminalArea.setEditable(true);
    terminalArea.requestFocus();
  }

  private void handleInput(KeyEvent event) {
    switch (event.getCode()) {
      case ENTER -> processCommand(currentInput.trim());
      case BACK_SPACE -> handleBackspace(event);
      case TAB -> handleAutocomplete();
      default -> {
        String text = event.getText();
        if (text != null && !text.isEmpty()) {
          currentInput += text;
          refreshTerminal();
        }
        event.consume(); // Prevent default text area behavior
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

  private void handleAutocomplete() {
    try {
      List<String> suggestions = autoComplete.getSuggestions(currentInput);

      if (suggestions.isEmpty()) {
        return;// No suggestions
      } else if (suggestions.size() == 1) {
        // Single match: Autofill the input
        currentInput = suggestions.getFirst();
        refreshTerminal();
      } else {
        // Multiple matches: Display suggestions
        appendToTerminal("\nSuggestions:\n");
        suggestions.forEach(suggestion -> appendToTerminal("- " + suggestion + "\n"));
        appendPrompt();
        refreshTerminal();
      }
    } catch (IOException e) {
      appendToTerminal("\nError fetching autocomplete suggestions: " + e.getMessage() + "\n");
    }
  }

  private void processCommand(String command) {
    appendToTerminal(command + "\n");

    if (!command.isEmpty()) {
      cliTerminal.handleUserInput(command); // Process the command
    }

    currentInput = ""; // Reset input
    appendPrompt(); // Add a new prompt
  }

  private void appendPrompt() {
    // Command prompt
    String prompt = "$ ";
    appendToTerminal(prompt);
    promptPosition = terminalArea.getText().length();
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
    Platform.runLater(() -> terminalArea.appendText(message));
  }
}
