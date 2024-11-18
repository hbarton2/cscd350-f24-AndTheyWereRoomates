package org.project.UnitTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.project.Controller.CommandParser;
import org.project.Model.CommandRegistries;

class CommandParserUnitTest {

  private CommandParser parser;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @BeforeEach
  void setUp() {
    try {
      // Mock or create a valid instance of CommandRegistries
      CommandRegistries commandRegistries = new CommandRegistries(
        "src/main/resources/CLICommands.json");
      parser = new CommandParser(commandRegistries);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    System.setOut(new PrintStream(outContent)); // Capture output for assertions
  }

  //TODO: ALL this test failed check logic 
//  @Test
//  void testCreateClassSuccess() {
//    parser.parseCommand("create class Car");
//    assertTrue(outContent.toString().contains("Class Car created and set as current."));
//  }
//
//  @Test
//  void testCreateClassAlreadyExists() {
//    parser.parseCommand("create class Car");
//    parser.parseCommand("create class Car");
//    assertTrue(outContent.toString().contains("Error: Class Car already exists."));
//  }
//
//  @Test
//  void testCreateClassInvalidName() {
//    parser.parseCommand("create class 123Car");
//    assertTrue(outContent.toString().contains("Error: 123Car is an invalid class name."));
//  }
//
//  @Test
//  void testRemoveClassSuccess() {
//    parser.parseCommand("create class Car");
//    parser.parseCommand("remove class Car");
//    assertTrue(outContent.toString().contains("Class Car removed."));
//  }
//
//  @Test
//  void testRemoveClassNotExists() {
//    parser.parseCommand("remove class NonExistentClass");
//    assertTrue(outContent.toString().contains("Error: Class NonExistentClass does not exist."));
//  }

  @AfterEach
  void tearDown() {
    System.setOut(originalOut); // Reset System.out
    outContent.reset();
  }
}
