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

  private void initializeComponents() {
    try {
      CommandRegistries commandRegistries =
          CommandRegistries.getInstance("src/main/resources/CLICommands.json");
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

  private void handleInput(KeyEvent event) {
    try {
      switch (event.getCode()) {
        case ENTER -> {
          processCommand(currentInput.trim());
          event.consume();
        }
        case BACK_SPACE -> {
          handleBackspace();
          event.consume();
        }
        case TAB -> {
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
          String text = event.getText();
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

  private void handleBackspace() {
    if (!currentInput.isEmpty()) {
      currentInput = currentInput.substring(0, currentInput.length() - 1);
      refreshTerminal();
      moveCaretToEnd();
    }
  }

  private void handleAutocomplete() {
    try {
      List<String> suggestions = autoComplete.getSuggestions(currentInput);

      if (suggestions.isEmpty()) {
        return;
      }

      if (suggestions.size() == 1) {
        currentInput = suggestions.get(0);
        refreshTerminal();
      } else {
        appendToTerminal("\nSuggestions:\n");
        suggestions.forEach(suggestion -> appendToTerminal("- " + suggestion + "\n"));
        appendPrompt();
        moveCaretToEnd();
      }
    } catch (IOException e) {
      LOGGER.log(Level.WARNING, "Error fetching autocomplete suggestions", e);
      appendToTerminal("Error fetching autocomplete suggestions: " + e.getMessage() + "\n");
    }
  }

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

  private void appendToTerminal(String message) {
    Platform.runLater(() -> terminalArea.appendText(message));
    moveCaretToEnd();
  }

  private void appendPrompt() {
    Platform.runLater(
        () -> {
          terminalArea.appendText("$ ");
          promptPosition = terminalArea.getText().length();
          moveCaretToEnd();
        });
  }

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
    moveCaretToEnd();
  }

  private void moveCaretToEnd() {
    Platform.runLater(
        () -> {
          int expectedCaretPosition = promptPosition + currentInput.length();
          terminalArea.positionCaret(expectedCaretPosition); // Move caret to the end of the input
        });
  }
}
