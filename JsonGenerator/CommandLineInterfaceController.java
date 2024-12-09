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
import org.project.View.TextAreaOutputStream;

/**
 * Controller for the Command Line Interface (CLI) of the UML Editor application.
 *
 * <p>This class handles user input, command processing, and interaction with the terminal area in
 * the GUI. It supports features like command history, auto-completion, and custom styling.
 */
public class CommandLineInterfaceController {
  @FXML private TextArea terminalArea;

  private final List<String> commandHistory = new ArrayList<>();
  private int historyIndex = -1;
  private String currentInput = "";
  private int promptPosition = 0;

  private CommandParser commandParser;
  private AutoComplete autoComplete;

  private static final Logger LOGGER =
      Logger.getLogger(CommandLineInterfaceController.class.getName());

  /**
   * Initializes the CLI controller.
   *
   * <p>Sets up the terminal area, redirects system output, applies custom CSS, initializes
   * components, and displays the welcome message and prompt.
   */
  @FXML
  public void initialize() {
    try {
      System.setProperty("javafx.userAgentStylesheetUrl", "null");

      // Apply custom CSS
      terminalArea
          .getStylesheets()
          .add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());

      // Redirect System.out to the terminalArea
      System.setOut(new PrintStream(new TextAreaOutputStream(terminalArea)));

      // Initialize components
      initializeComponents();

      // Display welcome message and prompt
      appendToTerminal("Welcome to the UML Editor CLI.\nType 'help' to see available commands.\n");
      appendPrompt();

      // Set up key input handling
      terminalArea.setOnKeyPressed(this::handleInput);
      terminalArea.setEditable(true);
      terminalArea.requestFocus();

    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Error initializing CommandLineInterfaceController", e);
    }
  }

  /** Initializes the components required for command parsing and auto-completion. */
  private void initializeComponents() {
    try {
      //      CommandRegistries commandRegistries =
      // CommandRegistries.getInstance("src/main/resources/CLICommands.json");
      CommandRegistries commandRegistries = CommandRegistries.getInstance("CLICommands.json");
      commandParser = new CommandParser(commandRegistries);
      autoComplete = new AutoComplete();

      // Populate autocomplete suggestions
      commandRegistries
          .getAllCommandNames()
          .forEach(
              (key, value) -> {
                try {
                  autoComplete.addCommand((String) key);
                } catch (IOException e) {
                  LOGGER.log(Level.WARNING, "Error adding command to AutoComplete: " + key, e);
                }
              });
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Error initializing components", e);
    }
  }

  /**
   * Handles user input in the terminal area.
   *
   * <p>Supports commands like Enter, Backspace, Tab (for auto-complete), and arrow keys for
   * navigating command history.
   *
   * @param event the {@code KeyEvent} triggered by the user
   */
  private void handleInput(KeyEvent event) {
    try {
      switch (event.getCode()) {
        case ENTER -> {
          processCommand(currentInput.trim());
          event.consume();
          scrollToBottom();
        }
        case BACK_SPACE -> {
          handleBackspace();
          event.consume();
        }
        case TAB -> {
          scrollToBottom();
          handleAutocomplete();
          event.consume();
        }
        case UP -> {
          navigateHistory(-1);
          event.consume();
        }
        case DOWN -> {
          navigateHistory(1);
          event.consume();
        }
        default -> {
          String text = event.getText(); // This respects Shift and Caps Lock
          if (text != null && !text.isEmpty()) {
            currentInput += text;
            refreshTerminal();
          }
          event.consume();
        }
      }
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Error handling input event", e);
      appendToTerminal("Error handling input: " + e.getMessage() + "\n");
    }
  }

  /**
   * Processes a command entered by the user.
   *
   * <p>Parses and executes the command, and displays the result or error messages in the terminal.
   *
   * @param command the command entered by the user
   */
  private void processCommand(String command) {
    if (!command.isEmpty()) {
      commandHistory.add(command);
      historyIndex = commandHistory.size();
    }

    try {
      CommandResult result = commandParser.parseCommand(command);

      if (result.isSuccess()) {
        appendToTerminal("Success: " + result.getMessage() + "\n");
      } else {
        appendToTerminal("Error: " + result.getMessage() + "\n");
      }
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, "Error processing command: " + command, e);
      appendToTerminal("Unexpected error: " + e.getMessage() + "\n");
    }

    currentInput = "";
    appendPrompt();
  }

  /** Handles the backspace key to delete the last character in the current input. */
  private void handleBackspace() {
    if (!currentInput.isEmpty()) {
      currentInput = currentInput.substring(0, currentInput.length() - 1);
      refreshTerminal();
      moveCaretToEnd();
    }
  }

  /** Handles the Tab key for auto-completion of commands. */
  private void handleAutocomplete() {
    try {
      List<String> suggestions = autoComplete.getSuggestions(currentInput);

      // If no suggestions, simply return
      if (suggestions.isEmpty()) {
        return;
      }

      // Clear any previous suggestions from the terminal display
      clearAutocompleteDisplay();

      if (suggestions.size() == 1) {
        // Autofill if there's a single suggestion
        currentInput = suggestions.get(0);
        refreshTerminal();
      } else {
        // Display the suggestions below the current prompt
        appendToTerminal("\nSuggestions:\n");
        suggestions.forEach(suggestion -> appendToTerminal("- " + suggestion + "\n"));
        moveCaretToEnd();
      }
    } catch (IOException e) {
      LOGGER.log(Level.WARNING, "Error fetching autocomplete suggestions", e);
      appendToTerminal("Error fetching autocomplete suggestions: " + e.getMessage() + "\n");
    }
  }

  /** Updates the terminal area with the current input. */
  private void refreshTerminal() {
    Platform.runLater(
        () -> {
          try {
            String fullText = terminalArea.getText(0, promptPosition) + currentInput;
            terminalArea.setText(fullText);
            terminalArea.positionCaret(fullText.length());
            moveCaretToEnd();
          } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error refreshing terminal", e);
          }
        });
  }

  /**
   * Appends a message to the terminal area.
   *
   * @param message the message to display
   */
  private void appendToTerminal(String message) {
    Platform.runLater(() -> terminalArea.appendText(message));
    moveCaretToEnd();
  }

  /** Appends a command prompt to the terminal area. */
  private void appendPrompt() {
    Platform.runLater(
        () -> {
          terminalArea.appendText("$ ");
          promptPosition = terminalArea.getText().length();
          moveCaretToEnd();
        });
  }

  /** Navigates through the command history based on the given direction. */
  private void navigateHistory(int direction) {
    if (commandHistory.isEmpty()) return;

    historyIndex += direction;
    if (historyIndex < 0) historyIndex = 0;
    if (historyIndex >= commandHistory.size()) {
      historyIndex = commandHistory.size();
      currentInput = "";
    } else {
      currentInput = commandHistory.get(historyIndex);
    }
    refreshTerminal();
    scrollToBottom();
    moveCaretToEnd();
  }

  /** Moves the caret to the end of the terminal input. */
  private void moveCaretToEnd() {
    Platform.runLater(
        () -> {
          int expectedCaretPosition = promptPosition + currentInput.length();
          terminalArea.positionCaret(expectedCaretPosition); // Move caret to the end of the input
        });
  }

  private void handleShiftKey(KeyEvent event) {
    if (event.isShiftDown()) {
      appendToTerminal("Shift key pressed!\n"); // Example behavior
    }
  }

  /** Clears auto-completion suggestions from the terminal area. */
  private void clearAutocompleteDisplay() {
    Platform.runLater(
        () -> {
          // Get the current text in the terminal up to the prompt
          String terminalText = terminalArea.getText(0, promptPosition) + currentInput;

          // Set the terminal's text to this base content
          terminalArea.setText(terminalText);
          scrollToBottom();
          // Move the caret to the correct position after clearing suggestions
          moveCaretToEnd();
        });
  }

  /** Scrolls the terminal area to the bottom. */
  private void scrollToBottom() {
    Platform.runLater(() -> terminalArea.setScrollTop(Double.MAX_VALUE));
  }
}
