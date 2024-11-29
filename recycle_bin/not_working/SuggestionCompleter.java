package org.project.Model;

import java.util.ArrayList;
import java.util.List;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

/**
 * SuggestionCompleter handles autocompletion and special key behavior.
 */
class SuggestionCompleter implements Completer {

  private final List<String> suggestions;
  private int suggestionIndex = 0; // Tracks the current suggestion index

  public SuggestionCompleter(List<String> suggestions) {
    this.suggestions = suggestions;
  }

  @Override
  public void complete(LineReader reader, ParsedLine line, List<Candidate> candidates) {
    String input = line.word(); // Current user input

    // Handle <Escape> to clear the current line
    if (reader.getLastBinding() != null && reader.getLastBinding().toString().equals("ESCAPE")) {
      reader.getBuffer().clear(); // Clear the buffer
      reader.callWidget(LineReader.REDRAW_LINE); // Redraw the line
      return;
    }

    // Ignore suggestions for empty or whitespace-only input
    if (input == null || input.trim().isEmpty()) {
      suggestionIndex = 0; // Reset suggestion index
      return;
    }

    // Get matching suggestions based on the current input
    List<String> matches = getMatchingSuggestions(input);

    if (!matches.isEmpty()) {
      // Ensure the suggestionIndex is within bounds
      if (suggestionIndex >= matches.size()) {
        suggestionIndex = 0; // Reset index if it exceeds the size
      }

      // Get the next suggestion
      String nextSuggestion = matches.get(suggestionIndex);

      // Replace the input line with the current suggestion
      reader.getBuffer().clear(); // Clear the buffer
      reader.getBuffer().write(nextSuggestion); // Write the suggestion
      reader.callWidget(LineReader.REDRAW_LINE); // Redraw the line

      // Confirm the input automatically after applying the suggestion
      reader.callWidget(LineReader.ACCEPT_LINE);

      // Increment the suggestion index for cycling suggestions
      suggestionIndex = (suggestionIndex + 1) % matches.size();
    } else {
      suggestionIndex = 0; // Reset if no matches
    }

    // Add matches to candidates (optional for multi-match display)
    for (String match : matches) {
      candidates.add(new Candidate(match));
    }
  }

  /**
   * Get suggestions matching the user's current input.
   *
   * @param input The user's current input.
   * @return A list of matching suggestions.
   */
  private List<String> getMatchingSuggestions(String input) {
    List<String> matches = new ArrayList<>();
    for (String suggestion : suggestions) {
      if (suggestion.startsWith(input)) {
        matches.add(suggestion);
      }
    }
    return matches;
  }
}