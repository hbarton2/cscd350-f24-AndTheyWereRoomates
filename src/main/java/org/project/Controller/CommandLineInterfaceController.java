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
      case ENTER -> {
        processCommand(currentInput.trim());
        event.consume(); // Prevent default behavior of ENTER
      }
      case BACK_SPACE -> {
        handleBackspace();
        event.consume(); // Prevent default backspace behavior
      }
      case TAB -> {
        handleAutocomplete();
        event.consume(); // Prevent default tab behavior
      }
      case UP -> {
        navigateHistory(-1);
        event.consume(); // Prevent scrolling with the UP arrow
      }
      case DOWN -> {
        navigateHistory(1);
        event.consume(); // Prevent scrolling with the DOWN arrow
      }
      default -> {
        String text = event.getText();
        if (text != null && !text.isEmpty()) {
          currentInput += text;
          refreshTerminal();
        }
        event.consume(); // Prevent default behavior for other keys
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

  // TODO: THIS LOGIC HAS ISSUES
  private void handleBackspace() {
    int caretPosition = terminalArea.getCaretPosition();
    String textBeforeCaret = terminalArea.getText(caretPosition - 2, caretPosition);
    moveCaretToEnd();
//    if (!textBeforeCaret.equals("$ ") || textBeforeCaret.equals("$ ")) {
//      //appendToTerminal(textBeforeCaret); // give back that character that got deleted
//      moveCaretToEnd();
//    }

//    if (textBeforeCaret.equals("$ ")) {
//      //      appendToTerminal(textBeforeCaret);
//      moveCaretToEnd();
//    }
  }

  private void moveCaretToEnd() {
    Platform.runLater(
        () -> {
          int caretPosition = terminalArea.getCaretPosition();
          int expectedCaretPosition = promptPosition + currentInput.length();

          if (caretPosition < expectedCaretPosition) {
            terminalArea.positionCaret(
                expectedCaretPosition); // Move caret to the end of `currentInput`
          }
        });
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
            terminalArea.setText(fullText);
            terminalArea.positionCaret(
                fullText.length()); // Ensure caret is at the end of the input
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
      scrollToBottom();
      return;
    }

    // Update current input with the selected command
    currentInput = commandHistory.get(historyIndex);
    refreshTerminal();
    scrollToBottom();
  }

  private void scrollToBottom() {
    Platform.runLater(() -> terminalArea.setScrollTop(Double.MAX_VALUE));
  }
}
