package org.project.UnitTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
          CommandRegistries.getInstance("CLICommands.json"); // Use singleton
      parser = new CommandParser(commandRegistries);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    System.setOut(new PrintStream(outContent)); // Capture output for assertions
  }

  @Test
  void removeClassToManyArgs() {
    CommandResult result = parser.parseCommand("remove class yes and no");
    assertEquals("remove class <classname>", result.getMessage());
  }

  @Test
  void testNoClassSelected() {
    CommandResult result = parser.parseCommand("add field int Banana");
    assertTrue(
        result
            .getMessage()
            .contains("Error: No class selected. Use 'switch class <class name>' first."),
        "Success message should match.");

    result = parser.parseCommand("rename field int Banana Peanut");
    assertTrue(
        result.getMessage().contains("No class is selected use, switch class <className>"),
        "Success message should match.");

    result = parser.parseCommand("remove method banana");
    assertTrue(
        result.getMessage().contains("No class is selected use, switch class <className>"),
        "Success message should match.");

    result = parser.parseCommand("add method int banana");
    assertTrue(
        result.getMessage().contains("No class is selected use, switch class <className>"),
        "Success message should match.");

    result = parser.parseCommand("remove method greanbeans int banana");
    assertTrue(
        result.getMessage().contains("No class is selected use, switch class <className>"),
        "Success message should match.");

    result = parser.parseCommand("rename method greanbeans int banana");
    assertTrue(
        result.getMessage().contains("No class is selected use, switch class <className>"),
        "Success message should match.");

    result = parser.parseCommand("add parameter banana int peach");
    assertTrue(
        result.getMessage().contains("No class is selected use, switch class <className>"),
        "Success message should match.");

    result = parser.parseCommand("remove parameter banana int peach");
    assertTrue(result.getMessage().contains("No class selected."), "Success message should match.");

    result = parser.parseCommand("add relationship aggregation penut");
    assertTrue(
        result.getMessage().contains("No class is selected use, switch class <className>"),
        "Success message should match.");

    result = parser.parseCommand("remove relationship aggregation penut");
    assertTrue(
        result.getMessage().contains("No class is selected use, switch class <className>"),
        "Success message should match.");
  }

  @Test
  void renameClassToManyArgs() {
    CommandResult result = parser.parseCommand("rename class john apple seed");
    assertEquals("rename class <existing classname> <new classname>", result.getMessage());
  }

  @Test
  void listClassesToManyArgs() {
    CommandResult result = parser.parseCommand("list classes all");
    assertEquals("Syntax: list classes", result.getMessage());
  }

  @Test
  void listClasses() {
    parser.parseCommand("remove class Apple");
    parser.parseCommand("remove class Limo");
    parser.parseCommand("remove class apple");
    parser.parseCommand("remove class banana");
    parser.parseCommand("remove class null");

    CommandResult result = parser.parseCommand("list classes");
    assertEquals("No classes to display.", result.getMessage());
  }

  @Test
  void removeFieldToManyArgs() {
    CommandResult result = parser.parseCommand("remove field Yes yes");
    assertEquals("remove field <field name>", result.getMessage());
  }

  @Test
  void renameMethodToManyArgs() {
    CommandResult result = parser.parseCommand("rename method yes no yes no yes");
    assertEquals(
        "rename method <existing method name> <new method name> <return type>",
        result.getMessage());
  }

  @Test
  void addParamTooManyArgs() {
    CommandResult result = parser.parseCommand("add parameter yes");
    assertEquals(
        "add parameter <method name> <parameter type> <parameter name> [<parameter type> <parameter name> ...]",
        result.getMessage());
  }

  @Test
  void testCreateClassSuccess() {
    CommandResult result = parser.parseCommand("create class Ferari");
    assertTrue(result.isSuccess(), "Command should succeed for valid class creation.");
    assertTrue(
        result.getMessage().contains("Class added: Ferari"), "Success message should match.");
    parser.parseCommand("delete class Ferari");
  }

  @Test
  void testCreateClassAlreadyExists() {
    parser.parseCommand("create class Car");
    CommandResult result = parser.parseCommand("create class Car");
    assertTrue(
        result.getMessage().contains("Error: Class 'Car' already exists."),
        "Error message should indicate duplicate class.");
    parser.parseCommand("delete class Car");
  }

  @Test
  void testCreateClassInvalidName() {
    CommandResult result = parser.parseCommand("create class 123Car");
    assertTrue(
        result.getMessage().contains("Error: Invalid class name."),
        "Error message should indicate invalid class name.");
  }

  @Test
  void testCreateClassEmptyName() {
    CommandResult result = parser.parseCommand("create class " + null);
    assertTrue(result.isSuccess(), "Command should succeed for valid class creation.");
    assertTrue(
        result.getMessage().contains("Error: Class name cannot be empty."),
        "Success message should match.");
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
    CommandResult result = parser.parseCommand("rename class Car Limo");
    assertTrue(result.isSuccess(), "Command should succeed for valid class renaming.");
    assertTrue(
        result.getMessage().contains("Class renamed: Car to Limo"),
        "Success message should match.");
    parser.parseCommand("delete class Limo");
  }

  @Test
  void testRenameClassNotExist() {
    CommandResult result = parser.parseCommand("rename class Car Pinto");
    assertFalse(
        result.getMessage().contains("Error: Class Car does not exist."),
        "Error Message should indicate a class not existing");
  }

  @Test
  void testRenameClassAlreadyExists() {
    parser.parseCommand("create class Car");
    CommandResult result = parser.parseCommand("rename class Car Car");
    assertFalse(
        result.getMessage().contains("Error: Class Pinto already exists."),
        "Error Message should indicate an already existing class");
    parser.parseCommand("delete class Car");
  }

  @Test
  void testRenameNullCheck() {
    parser.parseCommand("create class Car");
    CommandResult result = parser.parseCommand("rename class Car " + null);
    assertFalse(
        result.getMessage().contains("Error: Failed to rename class."),
        "Error Message should indicate an already existing class");

    parser.parseCommand("switch class Car");
    parser.parseCommand("add field int Green");

    result = parser.parseCommand("rename field Green " + null + null);
    assertFalse(
        result.getMessage().contains("Error: Failed to rename field."),
        "Error Message should indicate an already existing class");

    parser.parseCommand("add method int Yellow");

    result = parser.parseCommand("rename method Yellow " + null + null);
    assertFalse(
        result.getMessage().contains("Error: Failed to rename field."),
        "Error Message should indicate an already existing class");

    parser.parseCommand("delete class Car");
  }

  @Test
  void testAddFieldSuccess() {
    parser.parseCommand("create class Apple");
    parser.parseCommand("switch class Apple");
    CommandResult result = parser.parseCommand("add field int Banana");
    assertTrue(result.isSuccess(), "Command should succeed for valid field removal.");
    assertTrue(
        result.getMessage().contains("Field added: Type='int', Name='Banana' to class 'Apple'"),
        "Success message should match.");

    parser.parseCommand("delete class Apple");
  }

  @Test
  void testRemoveFieldSuccess() {
    parser.parseCommand("create class Apple");
    parser.parseCommand("switch class Apple");
    parser.parseCommand("add field int Banana");

    CommandResult result = parser.parseCommand("remove field Banana");
    assertTrue(result.isSuccess(), "Command should succeed for valid field removal.");
    assertTrue(
        result.getMessage().contains("Field removed: Banana"), "Success message should match.");
    parser.parseCommand("delete class Apple");
  }

  @Test
  void testRemoveFieldExists() {
    parser.parseCommand("create class Apple");
    parser.parseCommand("switch class Apple");

    CommandResult result = parser.parseCommand("remove field Banana");
    assertFalse(
        result.getMessage().contains("Error: Field 'Banana' already exists."),
        "Success message should match.");
    parser.parseCommand("delete class Apple");
  }

  @Test
  void testRenameFieldSuccess() {
    parser.parseCommand("create class apple");
    parser.parseCommand("switch class apple");
    parser.parseCommand("add field int banana");

    CommandResult result = parser.parseCommand("rename field banana pear int");
    assertTrue(result.isSuccess(), "Command should succeed for valid field renaming.");
    assertTrue(
        result.getMessage().contains("Field: banana to pear"), "Success message should match.");
    parser.parseCommand("delete class apple");
  }

  @Test
  void testRenameFieldNotExist() {
    parser.parseCommand("create class apple");
    parser.parseCommand("switch class apple");

    CommandResult result = parser.parseCommand("rename field banana pear int");
    assertTrue(
        result.getMessage().contains("Error: Field 'banana' does not exist."),
        "Success message should match.");
    parser.parseCommand("delete class apple");
  }

  @Test
  void testRenameFieldExists() {
    parser.parseCommand("create class Apple");
    parser.parseCommand("add field int Banana");
    parser.parseCommand("add field int Pear");

    CommandResult result = parser.parseCommand("rename field Banana Pear int");
    assertTrue(
        result.getMessage().contains("Error: Field 'Pear' already exists."),
        "Success message should match.");
    parser.parseCommand("delete class Apple");
  }

  @Test
  void testAddMethodSuccess() {
    parser.parseCommand("create class apple");
    parser.parseCommand("switch class apple");
    CommandResult result = parser.parseCommand("add method int banana");
    assertTrue(result.isSuccess(), "Command should succeed for valid method creation.");
    assertTrue(
        result.getMessage().contains("Method added: banana"), "Success message should match.");
    parser.parseCommand("delete class apple");
  }

  @Test
  void testAddMethodWithParametersSuccess() {
    parser.parseCommand("create class apple");
    parser.parseCommand("switch class apple");
    CommandResult result =
        parser.parseCommand("add method int banana string cashew string pistachio");
    assertTrue(result.isSuccess(), "Command should succeed for valid method creation.");
    assertTrue(
        result.getMessage().contains("Method added: banana"), "Success message should match.");
    parser.parseCommand("delete class apple");
  }

  @Test
  void testRemoveMethodSuccess() {
    parser.parseCommand("create class apple");
    parser.parseCommand("switch class apple");
    parser.parseCommand("add method int banana");
    CommandResult result = parser.parseCommand("remove method banana");
    assertTrue(result.isSuccess(), "Command should succeed for valid method removal.");
    assertTrue(
        result.getMessage().contains("Method removed: banana"), "Success message should match.");
    parser.parseCommand("delete class apple");
  }

  @Test
  void testRenameMethodSuccess() {
    parser.parseCommand("create class apple");
    parser.parseCommand("switch class apple");
    parser.parseCommand("add method int banana");
    CommandResult result = parser.parseCommand("rename method banana pear int");
    assertTrue(result.isSuccess(), "Command should succeed for valid method rename.");
    assertTrue(
        result.getMessage().contains("Method: banana to pear"), "Success message should match.");
    parser.parseCommand("delete class apple");
  }

  @Test
  void testRenameMethodNotExist() {
    parser.parseCommand("create class apple");
    parser.parseCommand("switch class apple");
    CommandResult result = parser.parseCommand("rename method banana pear int");
    // assertTrue(result.isSuccess(), "Command should succeed for valid field removal.");
    assertTrue(
        result.getMessage().contains("Error: Method 'banana' does not exist."),
        "Success message should match.");
    parser.parseCommand("delete class apple");
  }

  @Test
  void testRenameMethodExists() {
    parser.parseCommand("create class apple");
    parser.parseCommand("switch class apple");
    parser.parseCommand("add method int banana");
    parser.parseCommand("add method int pear");
    CommandResult result = parser.parseCommand("rename method banana pear int");
    // assertTrue(result.isSuccess(), "Command should succeed for valid field removal.");
    assertTrue(
        result.getMessage().contains("Error: Method 'pear' already exists."),
        "Success message should match.");
    parser.parseCommand("delete class apple");
  }

  @Test
  void testAddParameterSuccess() {
    parser.parseCommand("create class apple");
    parser.parseCommand("switch class apple");
    parser.parseCommand("add method int banana");
    CommandResult result = parser.parseCommand("add parameter banana int peach");
    assertTrue(result.isSuccess(), "Command should succeed for valid parameter creation.");
    assertTrue(
        result.getMessage().contains("Method: banana to int"), "Success message should match.");
    parser.parseCommand("delete class apple");
  }

  @Test
  void testAddParameterNotExist() {
    parser.parseCommand("create class apple");
    parser.parseCommand("switch class apple");
    CommandResult result = parser.parseCommand("add parameter banana int peach");
    assertTrue(
        result.getMessage().contains("Error: Method 'banana' does not exist."),
        "Success message should match.");
    parser.parseCommand("delete class apple");
  }

  @Test
  void testRemoveParameterSuccess() {
    parser.parseCommand("create class apple");
    parser.parseCommand("switch class apple");
    parser.parseCommand("add method int banana");
    parser.parseCommand("add parameter banana int peach");
    CommandResult result = parser.parseCommand("remove parameter banana peach");
    assertTrue(result.isSuccess(), "Command should succeed for valid parameter removal.");
    assertTrue(
        result.getMessage().contains("Parameters removed from banana"),
        "Success message should match.");
    parser.parseCommand("delete class apple");
  }

  @Test
  void testRemoveParameterNoMethod() {
    parser.parseCommand("create class apple");
    parser.parseCommand("switch class apple");
    parser.parseCommand("add parameter banana int peach");
    CommandResult result = parser.parseCommand("remove parameter banana peach");
    assertTrue(
        result.getMessage().contains("Error: Method banana not found."),
        "Success message should match.");
    parser.parseCommand("delete class apple");
  }

  @Test
  void testRenameParameterSuccess() {
    parser.parseCommand("create class apple");
    parser.parseCommand("switch class apple");
    parser.parseCommand("add method int banana");
    parser.parseCommand("add parameter banana int peach");
    CommandResult result = parser.parseCommand("rename parameter banana peach peanut");
    assertTrue(result.isSuccess(), "Command should succeed for valid parameter rename.");
    assertTrue(
        result
            .getMessage()
            .contains("Ready to rename parameter from peach to peanut in method banana"),
        "Success message should match.");

    parser.parseCommand("delete class apple");
  }

  @Test
  void testAddRelationshipSuccess() {
    parser.parseCommand("create class apple");
    parser.parseCommand("create class banana");
    parser.parseCommand("switch class apple");
    CommandResult result = parser.parseCommand("add relationship aggregation banana");
    assertTrue(result.isSuccess(), "Command should succeed for valid Relationship creation.");
    assertTrue(
        result.getMessage().contains("Added relationship to banana type of aggregation"),
        "Success message should match.");
    parser.parseCommand("delete class apple");
  }

  @Test
  void testAddRelationshipConnectToSelf() {
    parser.parseCommand("create class apple");
    parser.parseCommand("switch class apple");
    CommandResult result = parser.parseCommand("add relationship aggregation apple");
    // assertTrue(result.isSuccess(), "Command should succeed for valid Relationship creation.");
    assertTrue(
        result.getMessage().contains("Error: Can't connect to itself"),
        "Success message should match.");
    parser.parseCommand("delete class apple");
  }

  @Test
  void testAddRelationshipNotExist() {
    parser.parseCommand("create class apple");
    parser.parseCommand("switch class apple");
    CommandResult result = parser.parseCommand("add relationship aggregation NONEXISTENT");
    // assertTrue(result.isSuccess(), "Command should succeed for valid Relationship creation.");
    assertTrue(
        result.getMessage().contains("Error: Class 'NONEXISTENT' does not exist"),
        "Success message should match.");
    parser.parseCommand("delete class apple");
  }

  @Test
  void testRemoveRelationshipSuccess() {
    parser.parseCommand("create class apple");
    parser.parseCommand("create class banana");
    parser.parseCommand("switch class apple");
    parser.parseCommand("add relationship aggregation banana");
    CommandResult result = parser.parseCommand("remove relationship aggregation banana");
    // assertTrue(result.isSuccess(), "Command should succeed for valid Relationship creation.");
    assertTrue(
        result.getMessage().contains("Relationship removed from banana type of aggregation"),
        "Success message should match.");
    parser.parseCommand("delete class apple");
    parser.parseCommand("delete class banana");
  }

  @Test
  void testRemoveRelationshipNotExistent() {
    parser.parseCommand("create class apple");
    parser.parseCommand("create class banana");
    parser.parseCommand("switch class apple");
    parser.parseCommand("add relationship aggregation banana");
    CommandResult result = parser.parseCommand("remove relationship aggregation SOUPPPSPASPS");
    // assertTrue(result.isSuccess(), "Command should succeed for valid Relationship creation.");
    assertTrue(
        result.getMessage().contains("Error: Relationship SOUPPPSPASPS not found."),
        "Success message should match.");
    parser.parseCommand("delete class apple");
    parser.parseCommand("delete class banana");
  }

  @Test
  void testListDetailSuccess() {
    parser.parseCommand("create class apple");
    parser.parseCommand("switch class apple");
    parser.parseCommand("add method int banana");
    parser.parseCommand("add parameter banana int peach");
    CommandResult result = parser.parseCommand("list detail");
    assertTrue(result.isSuccess(), "Command should succeed for valid class list.");
    assertTrue(result.getMessage().contains("Listing Detail..."), "Success message should match.");
    parser.parseCommand("delete class apple");
  }

  @Test
  void testListDetailNotSelected() {
    parser.parseCommand("create class apple");

    CommandResult result = parser.parseCommand("list detail");
    assertTrue(
        result.getMessage().contains("Error: No class selected"), "Success message should match.");
    parser.parseCommand("delete class apple");
  }

  @Test
  void testUndoSuccess() {
    parser.parseCommand("create class apple");
    parser.parseCommand("switch class apple");
    parser.parseCommand("add method int banana");

    CommandResult result = parser.parseCommand("undo");
    assertTrue(result.isSuccess(), "Command should succeed for valid undo.");
    assertTrue(result.getMessage().contains("Undone"), "Success message should match.");
    parser.parseCommand("delete class apple");
  }

  @Test
  void testUndoNoUndo() {
    parser.parseCommand("create class apple");
    parser.parseCommand("switch class apple");

    CommandResult result = parser.parseCommand("undo");
    // assertTrue(result.isSuccess(), "Command should succeed for valid field removal.");
    assertTrue(
        result.getMessage().contains("Error: Nothing to undo"), "Success message should match.");
    parser.parseCommand("delete class apple");
  }

  @Test
  void testUndoNoClass() {
    CommandResult result = parser.parseCommand("undo");
    assertTrue(
        result.getMessage().contains("Error: Nothing to undo"), "Success message should match.");
  }

  @Test
  void testRedoSuccess() {
    parser.parseCommand("create class apple");
    parser.parseCommand("switch class apple");
    parser.parseCommand("add method int BEANIEBABY");
    parser.parseCommand("undo");
    CommandResult result = parser.parseCommand("redo");
    assertTrue(result.isSuccess(), "Command should succeed for Redo.");
    assertTrue(result.getMessage().contains("Redone"), "Success message should match.");
    parser.parseCommand("delete class apple");
  }

  @Test
  void testURedoNoUndo() {
    parser.parseCommand("create class apple");
    parser.parseCommand("switch class apple");

    CommandResult result = parser.parseCommand("redo");
    // assertTrue(result.isSuccess(), "Command should succeed for valid field removal.");
    assertTrue(
        result.getMessage().contains("Error: Nothing to redo"), "Success message should match.");
    parser.parseCommand("delete class apple");
  }

  @Test
  void testClear() {
    CommandResult result = parser.parseCommand("clear");
    assertTrue(result.isSuccess(), "Command should succeed for clearing screen.");
    assertTrue(result.getMessage().contains("Terminal cleared"), "Success message should match.");
  }

  @Test
  void testNewProject() {
    CommandResult result = parser.parseCommand("new project");
    assertTrue(result.isSuccess(), "Command should succeed for clearing screen.");
    assertTrue(
        result
            .getMessage()
            .contains("New project initialized. All previous data has been cleared."),
        "Success message should match.");
  }

  @Test
  void testSaveAndLoad() {
    parser.parseCommand("new project");
    parser.parseCommand("create class PEANUTS");
    parser.parseCommand("switch class PEANUTS");
    parser.parseCommand("add field INT DUMMY");
    parser.parseCommand("add method INT PEACHES");
    parser.parseCommand("add method STRING ALMONDS");

    CommandResult result = parser.parseCommand("save");
    assertTrue(result.isSuccess(), "Command should succeed for valid field removal.");
    assertTrue(
        result.getMessage().contains("Saved to src/main/resources/saves/temp_save.json"),
        "Success message should match.");

    parser.parseCommand("remove class PEANUTS");

    result = parser.parseCommand("load");
    // assertTrue(result.isSuccess(), "Command should succeed for valid field removal.");
    assertTrue(
        result.getMessage().contains("Loaded from src/main/resources/saves/temp_save.json"),
        "Success message should match.");

    parser.parseCommand("remove class PEANUTS");
  }

  @Test
  void testSaveAs() {
    parser.parseCommand("new project");

    CommandResult result = parser.parseCommand("save as src/main/resources/saves/DUMMY");
    assertTrue(result.isSuccess(), "Command should succeed for valid field removal.");
    assertTrue(result.getMessage().contains("Saved to"), "Success message should match.");
  }

  //  @Test
  //  void testLoad() {
  //    CommandResult result = parser.parseCommand("load");
  //    // assertTrue(result.isSuccess(), "Command should succeed for valid field removal.");
  //    assertTrue(
  //        result.getMessage().contains("Loaded from src/main/resources/saves/temp_save.json"),
  //            "Success message should match.");
  //  }

  @Test
  void testLoadFile() {

    CommandResult result = parser.parseCommand("load file src/main/resources/saves/test");
    // assertTrue(result.isSuccess(), "Command should succeed for valid field removal.");
    assertTrue(result.getMessage().contains("Loaded from"), "Success message should match.");
  }

  @Test
  void testLoadFileNotExist() {

    CommandResult result = parser.parseCommand("load file NONEXISTENT");
    // assertTrue(result.isSuccess(), "Command should succeed for valid field removal.");
    assertTrue(result.getMessage().contains("File not found:"), "Success message should match.");
  }

  @Test
  void testLoadFileNotExisting() {
    CommandResult result = parser.parseCommand("load file NONEXISTENT");
    // assertTrue(result.isSuccess(), "Command should succeed for valid field removal.");
    assertTrue(result.getMessage().contains("File not found:"), "Success message should match.");
  }

  @Test
  void testListClassSuccess() {
    parser.parseCommand("create class Car");
    CommandResult result = parser.parseCommand("list classes");
    assertTrue(
        result.getMessage().contains("Car"), "Error message should indicate duplicate class.");
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
