//package org.project;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.project.Controller.CommandParser;
//
//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//class CommandParserTest {
//
//  private CommandParser parser;
//  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//  private final PrintStream originalOut = System.out;
//
//  @BeforeEach
//  void setUp() {
//    parser = new CommandParser("src/main/resources/CLICommands.json");
//    System.setOut(new PrintStream(outContent));
//  }
//
//  @Test
//  void testCreateClassSuccess() {
//    parser.parseCommand("create class Car");
//    assertEquals("Car", parser.getCurrentClass());
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
//
//  @AfterEach
//  void tearDown() {
//    System.setOut(originalOut);
//    outContent.reset();
//  }
//}