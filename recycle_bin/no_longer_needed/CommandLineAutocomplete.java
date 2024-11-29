package org.project.View;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CommandLineAutocomplete extends Application {

  private int suggestionIndex = 0; // Track the current suggestion index
  private String lastInput = "";  // Track the last input to reset cycling if input changes
  private List<String> suggestions; // Predefined suggestions

  @Override
  public void start(Stage primaryStage) {
    // Initialize suggestions
    suggestions = new ArrayList<>();
    suggestions.add("apple");
    suggestions.add("application");
    suggestions.add("apricot");
    suggestions.add("banana");

    // Create a TextArea for the console
    TextArea console = new TextArea();
    console.setPromptText("Start typing and press 'Tab' to autocomplete. Type 'exit' to quit.");
    console.setWrapText(true);

    // Set up layout
    VBox root = new VBox(console);
    root.setPadding(new Insets(10));
    Scene scene = new Scene(root, 600, 400);

    // Handle key events
    console.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.TAB) {
        event.consume(); // Prevent default Tab behavior
        String input = getLastLine(console.getText()).trim();
        if (!input.equals(lastInput)) {
          suggestionIndex = 0; // Reset cycling if the input changes
        }
        lastInput = input;

        String completed = cycleSuggestions(input);
        replaceLastLine(console, completed);
      } else if (event.getCode() == KeyCode.ENTER) {
        String input = getLastLine(console.getText()).trim();
        if (input.equalsIgnoreCase("exit")) {
          console.appendText("\nGoodbye!");
          primaryStage.close();
        }
        lastInput = ""; // Clear the last input
        suggestionIndex = 0; // Reset cycling index
      }
    });

    primaryStage.setTitle("JavaFX Console with Autocomplete");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  /**
   * Cycles through matching suggestions based on user input.
   *
   * @param input The current user input
   * @return The next suggestion that matches the input prefix
   */
  private String cycleSuggestions(String input) {
    List<String> matches = new ArrayList<>();
    for (String suggestion : suggestions) {
      if (suggestion.startsWith(input)) {
        matches.add(suggestion);
      }
    }

    if (matches.isEmpty()) {
      suggestionIndex = 0; // Reset the index if no matches are found
      return input; // No matches; return the original input
    }

    // Cycle through matches
    String result = matches.get(suggestionIndex);
    suggestionIndex = (suggestionIndex + 1) % matches.size(); // Increment and wrap around
    return result;
  }

  /**
   * Gets the last line of text from the console.
   *
   * @param text The entire console text
   * @return The last line of input
   */
  private String getLastLine(String text) {
    String[] lines = text.split("\n");
    return lines[lines.length - 1];
  }

  /**
   * Replaces the last line in the console with the specified text.
   *
   * @param console The TextArea console
   * @param text    The text to replace the last line
   */
  private void replaceLastLine(TextArea console, String text) {
    String currentText = console.getText();
    int lastLineIndex = currentText.lastIndexOf("\n");
    if (lastLineIndex == -1) {
      console.setText(text);
    } else {
      console.setText(currentText.substring(0, lastLineIndex + 1) + text);
    }
    console.positionCaret(console.getText().length()); // Move caret to the end
  }

  public static void main(String[] args) {
    launch(args);
  }
}
