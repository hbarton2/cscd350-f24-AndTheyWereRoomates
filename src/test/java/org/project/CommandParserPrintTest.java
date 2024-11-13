package org.project;

import org.junit.jupiter.api.Test;
import org.project.Controller.CommandParser;

public class CommandParserPrintTest {
  @Test
  public void testPrint() {
    CommandParser testParse = new CommandParser("src/main/resources/CLICommands.json");

//    testParse.parseCommand("create class Animal");
//
//    testParse.getCurrentClass();
//    testParse.printCommandsStructure();
  }
}
