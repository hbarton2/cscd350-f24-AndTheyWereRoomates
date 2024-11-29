package org.project.Model;

import java.util.ArrayList;
import java.util.List;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class CommandLineAutocomplete {

  public static void main(String[] args) throws Exception {
    // Initialize suggestions
    List<String> suggestions = new ArrayList<>();
    suggestions.add("apple");
    suggestions.add("application");
    suggestions.add("apricot");
    suggestions.add("ant");
    suggestions.add("angular");

    // Set up terminal and LineReader
    Terminal terminal = TerminalBuilder.builder().system(true).build();
    LineReader reader = LineReaderBuilder.builder()
      .terminal(terminal)
      .completer(new SuggestionCompleter(suggestions))
      .build();

    System.out.println(
      "Start typing and press 'Tab' to autocomplete. Press 'Escape' to clear. Type 'exit' to quit.");

    while (true) {
      String line;
      try {
        // Read user input
        line = reader.readLine("> ").trim();

        // Exit condition
        if ("exit".equalsIgnoreCase(line)) {
          System.out.println("Goodbye!");
          break;
        }

        // Display the confirmed input
        System.out.println("> " + line);

      } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
      }
    }
  }
}


