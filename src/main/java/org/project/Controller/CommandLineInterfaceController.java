package org.project.Controller;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
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

  private final List<String> commandHistory = new ArrayList<>(); // Stores command history
  private int historyIndex = -1; // Tracks current position in command history
  private CommandLineTerminal cliTerminal;
  private AutoComplete autoComplete; // For autocomplete functionality
  private String currentInput = ""; // Tracks the current user input
  private int promptPosition = 0; // Tracks the position of the latest prompt

  private static final Logger LOGGER =
      Logger.getLogger(CommandLineInterfaceController.class.getName());

  @FXML
  public void initialize() {
    try {
      // Apply custom CSS
      System.setProperty("javafx.userAgentStylesheetUrl", "none");
      terminalArea
          .getStylesheets()
          .add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());

      // Redirect System.out to the terminalArea
      System.setOut(
          new PrintStream(new TextAreaOutputStream(terminalArea)) {
            @Override
            public void println(String x) {
              if (!x.startsWith("at java.base") && !x.contains("lambda$")) {
                super.println(x);
              }
            }
          });

      // Initialize components
      initializeComponents();

      // Display welcome message and the first prompt
      appendToTerminal("Welcome to the UML Editor CLI.\n");
      appendToTerminal("Type 'help' to see available commands, or 'exit' to quit.\n");
      appendPrompt();

      // Listen for user key inputs
      terminalArea.setOnKeyPressed(this::handleInput);

      // Allow caret and focus management
      terminalArea.setEditable(true);
      terminalArea.requestFocus();
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Error initializing CommandLineInterfaceController", e);
    }
  }

  private void initializeComponents() {
    try {
      CommandRegistries commandRegistries =
          CommandRegistries.getInstance("src/main/resources/CLICommands.json");
      cliTerminal = new CommandLineTerminal(commandRegistries, terminalArea);
      autoComplete = new AutoComplete();

      // Populate autocomplete suggestions
      commandRegistries
          .getAllCommandNames()
          .forEach(
              (key, value) -> {
                try {
                  String command = (String) key;
                  autoComplete.addCommand(command);
                } catch (IOException e) {
                  LOGGER.log(Level.WARNING, "Error adding command to AutoComplete: " + key, e);
                }
              });
    } catch (IOException e) {
      LOGGER.log(
          Level.SEVERE, "Error initializing components in CommandLineInterfaceController", e);
    }
  }

  private void handleInput(KeyEvent event) {
    switch (event.getCode()) {
      case ENTER -> processCommand(currentInput.trim());
      case BACK_SPACE -> handleBackspace(event);
      case TAB -> handleAutocomplete();
      case UP -> navigateHistory(-1);
      case DOWN -> navigateHistory(1);
      default -> {
        String text = event.getText();
        if (text != null && !text.isEmpty()) {
          currentInput += text; // Update only the current input
          refreshTerminal(); // Refresh without appending the prompt again
        }
        event.consume(); // Prevent default text area behavior
      }
    }
  }

  private void processCommand(String command) {

    if (!command.isEmpty()) {
      commandHistory.add(command); // Add command to history
      historyIndex = commandHistory.size(); // Reset history index
    }

    try {
      // Retrieve the CommandResult from handleUserInput
      CommandResult result = cliTerminal.handleUserInput(command);

      // Display the result based on success or failure
      if (result.isSuccess()) {
        appendToTerminal("Success: " + result.getMessage() + "\n");
      } else {
        appendToTerminal("Error: " + result.getMessage() + "\n");
      }
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, "Error processing command: " + command, e);
      appendToTerminal("Unexpected error: " + e.getMessage() + "\n");
    }

    currentInput = ""; // Reset input
    appendPrompt(); // Add a new prompt
  }

  private void handleBackspace(KeyEvent event) {
    // Determine the position of the caret
    int caretPosition = terminalArea.getCaretPosition();

    // Allow backspace only if the caret is after the prompt "$ "
    // and currentInput is not empty
    if (currentInput.isEmpty() || caretPosition <= promptPosition) {
      event.consume(); // Prevent deletion before or inside the prompt
    } else {
      // Safely remove the last character from currentInput
      currentInput = currentInput.substring(0, currentInput.length() - 1);
      refreshTerminal();
    }
  }

  private void handleAutocomplete() {
    try {
      List<String> suggestions = autoComplete.getSuggestions(currentInput);

      if (suggestions.isEmpty()) {
        return; // No suggestions
      }

      if (suggestions.size() == 1) {
        // Single match: Autofill the input
        currentInput = suggestions.getFirst(); // Use the first suggestion
        refreshTerminal();
        return;
      }

      // Multiple matches: Display suggestions without overwriting terminal
      Platform.runLater(
          () -> {
            appendToTerminal("\nSuggestions:\n");
            suggestions.forEach(suggestion -> appendToTerminal("- " + suggestion + "\n"));
            appendPrompt(); // Re-add the prompt for continued input
            refreshTerminal(); // Update the terminal
          });
    } catch (IOException e) {
      LOGGER.log(Level.WARNING, "Error fetching autocomplete suggestions", e);
      appendToTerminal("\nError fetching autocomplete suggestions: " + e.getMessage() + "\n");
    }
  }

  private void refreshTerminal() {
    Platform.runLater(
        () -> {
          try {
            String fullText = terminalArea.getText(0, promptPosition) + currentInput;

            // Safely update the TextArea
            terminalArea.setText(fullText);
            terminalArea.positionCaret(fullText.length()); // Move caret to the end
          } catch (IndexOutOfBoundsException e) {
            LOGGER.log(Level.WARNING, "Error refreshing terminal", e);
          }
        });
  }

  private void appendToTerminal(String message) {
    Platform.runLater(() -> terminalArea.appendText(message));
  }

  private void appendPrompt() {
    Platform.runLater(
        () -> {
          terminalArea.appendText("$ ");
          promptPosition = terminalArea.getText().length(); // Update the prompt position
        });
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
      currentInput = ""; // Clear input if beyond the last command
      refreshTerminal();
      return;
    }

    // Update current input with the selected command
    currentInput = commandHistory.get(historyIndex);
    refreshTerminal();
  }
}
