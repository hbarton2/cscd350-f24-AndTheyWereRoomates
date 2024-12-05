package org.project.TestDrivenDevelopment;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;
import org.project.Controller.CommandParser;
import org.project.Model.CommandRegistries;

public class CommandParserPrintTest {

  // TODO: this test failed
  @Test
  public void testPrint() throws Exception {
    // Setup
    CommandRegistries commandRegistries = null;
    commandRegistries.getAllCommands();
    CommandParser testParse = new CommandParser(commandRegistries);

    // Capture System.out
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outContent));

    // Invoke the method to print commands
    testParse.parseCommand("create class Animal");
    testParse.parseCommand("help"); // Assuming 'help' prints command list

    // Reset System.out
    System.setOut(originalOut);

    // Assert the captured output
    String printedOutput = outContent.toString();
    System.out.println(printedOutput); // Print captured output for debugging
    assertTrue(printedOutput.contains("Class Animal created and set as current."));
    assertTrue(printedOutput.contains("Available Commands:"));
  }
}
