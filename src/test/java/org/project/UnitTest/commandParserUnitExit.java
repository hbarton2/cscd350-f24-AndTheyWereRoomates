package org.project.UnitTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.Controller.CommandParser;
import org.project.Controller.CommandResult;
import org.project.Model.CommandRegistries;

public class commandParserUnitExit {
  private CommandParser parser;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @BeforeEach
  void setUp() {
    try {
      // Mock or create a valid instance of CommandRegistries
      CommandRegistries commandRegistries =
          CommandRegistries.getInstance("CLICommands.json"); // Use singleton
      parser = new CommandParser(commandRegistries);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    System.setOut(new PrintStream(outContent)); // Capture output for assertions
  }

  @Test
  void testRedoExit() {
    CommandResult result = parser.parseCommand("exit");
    assertTrue(result.isSuccess(), "Command should succeed for clearing screen.");
    assertTrue(
        result.getMessage().contains("Program exited successfully."),
        "Success message should match.");
  }
}
