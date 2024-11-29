package org.project.TestDrivenDevelopment;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.Reference;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class CommandLineInjection {

  public static void main(String[] args) throws Exception {
    // Set up terminal and LineReader
    Terminal terminal = TerminalBuilder.builder().system(true).build();
    LineReader reader = LineReaderBuilder.builder()
      .terminal(terminal)
      .build();

    // Define the inject-word widget
    reader.getWidgets().put("inject-word", () -> {
      injectWord(reader, "injected-word");
      return true;
    });

    // Bind the Tab key (\t) to the inject-word widget
    reader.getKeyMaps().get(LineReader.MAIN).bind(new Reference("inject-word"), "\t");

    System.out.println("Start typing. Press 'Tab' to inject 'injected-word'. Type 'exit' to quit.");

    while (true) {
      // Read user input
      String line = reader.readLine("> ");

      if ("exit".equalsIgnoreCase(line.trim())) {
        System.out.println("Goodbye!");
        break;
      }

      System.out.println("You entered: " + line.trim());
    }
  }

  /**
   * Injects a word into the current command line buffer and ensures it's visible.
   *
   * @param reader The LineReader handling user input.
   * @param word   The word to inject into the buffer.
   */
  private static void injectWord(LineReader reader, String word) {
    String currentBuffer = reader.getBuffer().toString();
    int cursorPos = reader.getBuffer().cursor();

    // Inject the word at the cursor position
    String newLine = currentBuffer.substring(0, cursorPos) + word + currentBuffer.substring(cursorPos);

    // Clear the buffer and update the line
    reader.getBuffer().clear();
    reader.getBuffer().write(newLine);

    // Move the cursor to the new position after the injected word
    reader.getBuffer().cursor(cursorPos + word.length());

    // Redraw the line to reflect changes
    reader.callWidget(LineReader.REDRAW_LINE);
  }
}
