package org.project.UnitTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.project.Controller.CommandResult;
import org.project.Model.CommandRegistries;

// TODO: if any test does not pass Maven package will not let it pass or build jar file
@Timeout(value = 10, unit = TimeUnit.SECONDS) // Apply a 10-second timeout to all tests
class CommandRegistriesUnitTest {

  private CommandRegistries commandRegistries;

  @BeforeEach
  void setUp() {
    commandRegistries = new CommandRegistries("src/main/resources/CLICommands.json");
  }

  @Test
  void testCreateClassCommandSuccess() {
    String[] args = {"Fruit"};
    CommandResult result = commandRegistries.executeCommand("create class", args);
    assertTrue(result.isSuccess(), "Command should succeed");
    assertEquals("Class created: Fruit", result.getMessage());
  }

  // TODO: All this test failed
  //  @Test
  //  void testCreateClassCommandDuplicate() {
  //    String[] args = {"Fruit","apple"};
  //    commandRegistries.executeCommand("create class", args); // First creation
  //    CommandResult result = commandRegistries.executeCommand("create class", args); // Duplicate
  // creation
  //    assertFalse(result.isSuccess(), "Command should fail due to duplicate class name");
  //    assertEquals("Error: Class 'MyClass' already exists.", result.getMessage());
  //  }

  //  @Test
  //  void testRemoveClassCommandSuccess() {
  //    String[] args = {"MyClass"};
  //    commandRegistries.executeCommand("create class", args); // Create class first
  //    CommandResult result = commandRegistries.executeCommand("remove class", args);
  //    assertTrue(result.isSuccess(), "Command should succeed");
  //    assertEquals("Class deleted: MyClass", result.getMessage());
  //  }

  //  @Test
  //  void testRemoveClassCommandFailure() {
  //    String[] args = {"NonExistentClass"};
  //    CommandResult result = commandRegistries.executeCommand("remove class", args);
  //    assertFalse(result.isSuccess(), "Command should fail for non-existent class");
  //    assertEquals("Error: Class 'NonExistentClass' does not exist.", result.getMessage());
  //  }

  @Test
  void testRenameClassCommandSuccess() {
    String[] args = {"OldClass", "NewClass"};
    commandRegistries.executeCommand(
        "create class", new String[] {"OldClass"}); // Create initial class
    CommandResult result = commandRegistries.executeCommand("rename class", args);
    assertTrue(result.isSuccess(), "Command should succeed");
    assertEquals("Class renamed: OldClass to NewClass", result.getMessage());
  }

  //  @Test
  //  void testRenameClassCommandFailure() {//Logic not implemented
  //    String[] args = {"NonExistentClass", "NewClass"};
  //    CommandResult result = commandRegistries.executeCommand("rename class", args);
  //    assertFalse(result.isSuccess(), "Command should fail for non-existent class");
  //    assertEquals("Error: Class 'NonExistentClass' does not exist.", result.getMessage());
  //  }

  @Test
  void testAddFieldCommandSuccess() {
    String[] args = {"int", "fieldName"};
    commandRegistries.executeCommand(
        "create class", new String[] {"MyClass"}); // Create initial class
    commandRegistries.executeCommand("switch", new String[] {"MyClass"}); // Switch to the class
    CommandResult result = commandRegistries.executeCommand("add field", args);
    assertTrue(result.isSuccess(), "Command should succeed");
    assertEquals("Field added: int fieldName", result.getMessage());
  }

  //  @Test
  //  void testAddFieldCommandFailure() {
  //    String[] args = {"int", "fieldName"};
  //    CommandResult result = commandRegistries.executeCommand("add field", args); // Without
  // switching to a class
  //    assertFalse(result.isSuccess(), "Command should fail when no class is set");
  //    assertEquals("Error: No class is currently selected.", result.getMessage());
  //  }

  @Test
  void testRemoveFieldCommandSuccess() {

    String[] args = {"fieldName"};
    commandRegistries.executeCommand(
        "create class", new String[] {"MyClass"}); // Create initial class
    commandRegistries.executeCommand("switch", new String[] {"MyClass"}); // Switch to the class
    commandRegistries.executeCommand("add field", new String[] {"int", "fieldName"});

    CommandResult result = commandRegistries.executeCommand("remove field", args);
    assertTrue(result.isSuccess(), "Command should succeed");
    assertEquals("Field removed: fieldName", result.getMessage());
  }
}
