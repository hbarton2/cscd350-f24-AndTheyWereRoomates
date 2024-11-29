package org.project.View;

import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.ParsedLine;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "cli", mixinStandardHelpOptions = true, version = "1.0",
  description = "CLI with Tab Autocomplete")
public class PicocliAutocompleteApp implements Callable<Integer> {

  private static int suggestionIndex = 0; // Tracks cycling through suggestions

  @Override
  public Integer call() {
    System.out.println("Type `exit` to quit.");
    return 0;
  }

  public static void main(String[] args) {
    CommandLine cmd = new CommandLine(new PicocliAutocompleteApp());

    // Create a LineReader with a custom completer
    LineReader reader = LineReaderBuilder.builder()
      .completer(new PicocliCompleter(cmd))
      .build();

    while (true) {
      String line = reader.readLine("> ").trim();
      if ("exit".equalsIgnoreCase(line)) {
        System.out.println("Goodbye!");
        break;
      }
      System.out.println("You entered: " + line);
    }
  }

  /**
   * Custom Completer for JLine to integrate with Picocli's commands and options.
   */
  static class PicocliCompleter implements Completer {
    private final CommandLine commandLine;

    public PicocliCompleter(CommandLine commandLine) {
      this.commandLine = commandLine;
    }

    @Override
    public void complete(LineReader reader, ParsedLine line, List<Candidate> candidates) {
      String currentInput = line.word();
      List<String> matches = getSuggestions(currentInput);

      if (!matches.isEmpty()) {
        // Cycle through suggestions if Tab is pressed multiple times
        String nextSuggestion = matches.get(suggestionIndex);
        reader.getBuffer().clear(); // Clear current input
        reader.getBuffer().write(nextSuggestion); // Write the suggestion
        reader.callWidget(LineReader.REDRAW_LINE); // Redraw the input line
        suggestionIndex = (suggestionIndex + 1) % matches.size(); // Increment and cycle
      } else {
        suggestionIndex = 0; // Reset if no matches
      }

      // Add matches as candidates for JLine to display
      for (String match : matches) {
        candidates.add(new Candidate(new AttributedStringBuilder()
          .append(match, AttributedStyle.BOLD.foreground(AttributedStyle.GREEN))
          .toAnsi()));
      }
    }

    private List<String> getSuggestions(String input) {
      List<String> options = new ArrayList<>(commandLine.getCommandSpec().optionsMap().keySet());
      List<String> matches = new ArrayList<>();

      for (String option : options) {
        if (option.startsWith(input)) {
          matches.add(option);
        }
      }
      return matches;
    }
  }
}
