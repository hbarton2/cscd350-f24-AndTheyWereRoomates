package org.project.UnitTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.Controller.CommandParser;
import org.project.Controller.CommandResult;
import org.project.Model.CommandRegistries;

class CommandParserUnitTest {

  private CommandParser parser;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @BeforeEach
  void setUp() {
    try {
      // Mock or create a valid instance of CommandRegistries
      CommandRegistries commandRegistries =
          CommandRegistries.getInstance("src/main/resources/CLICommands.json"); // Use singleton
      parser = new CommandParser(commandRegistries);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    System.setOut(new PrintStream(outContent)); // Capture output for assertions
  }

  @Test
  void testCreateClassSuccess() {
    CommandResult result = parser.parseCommand("create class Car");
    assertTrue(result.isSuccess(), "Command should succeed for valid class creation.");
    assertTrue(result.getMessage().contains("Class added: Car"), "Success message should match.");
  }

  @Test
  void testCreateClassAlreadyExists() {
    parser.parseCommand("create class Car");
    CommandResult result = parser.parseCommand("create class Car");
    assertTrue(
        result.getMessage().contains("Error: Class 'Car' already exists."),
        "Error message should indicate duplicate class.");
  }

  @Test
  void testCreateClassInvalidName() {
    CommandResult result = parser.parseCommand("create class 123Car");
    assertTrue(
        result.getMessage().contains("Error: Invalid class name."),
        "Error message should indicate invalid class name.");
  }

  @Test
  void testRemoveClassSuccess() {
    parser.parseCommand("create class Car");
    CommandResult result = parser.parseCommand("remove class Car");
    assertTrue(result.isSuccess(), "Command should succeed for valid class removal.");
    assertTrue(result.getMessage().contains("Class removed: Car"), "Success message should match.");
  }

  @Test
  void testRemoveClassNotExists() {
    CommandResult result = parser.parseCommand("remove class NonExistentClass");
    assertTrue(
        result.getMessage().contains("Error: Class 'NonExistentClass' does not exist."),
        "Error message should indicate non-existent class.");
  }

  @Test
  void testRenameClassSuccess() {
    parser.parseCommand("create class Car");
    CommandResult result = parser.parseCommand("rename class Car Pinto");
    assertTrue(result.isSuccess(), "Command should succeed for valid class renaming.");
    assertTrue(
        result.getMessage().contains("Class renamed: Car to Pinto"),
        "Success message should match.");
  }

  @Test
  void testHelpCommand() {
    CommandResult result = parser.parseCommand("help");
    assertTrue(result.isSuccess(), "Help command should succeed.");
    assertTrue(
        result.getMessage().contains("Available Commands:"),
        "Help message should list available commands.");
  }

  @AfterEach
  void tearDown() {
    System.setOut(originalOut); // Reset System.out
    outContent.reset();
    CommandRegistries.resetInstance(); // Reset CommandRegistries for isolation
  }
}
