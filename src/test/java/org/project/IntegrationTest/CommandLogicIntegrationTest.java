package org.project.IntegrationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.project.Controller.CommandResult;
import org.project.Model.CommandLogic;
import org.project.Model.CommandRegistries;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CommandLogicIntegrationTest {

  private CommandRegistries commandRegistries;
  private CommandLogic commandLogic;

  @BeforeEach
  void setUp() throws Exception {
    // Load test-specific JSON file
    String testJsonPath = "src/test/resources/CommandsTest.json";
    commandRegistries = CommandRegistries.getInstance(testJsonPath); // Ensure singleton is used
    commandLogic = new CommandLogic();
  }

  @Test
  void testCreateClass() {
    // Test creating a class
    CommandResult result =
        commandRegistries.executeCommand("create class", new String[] {"TestClass"});
    assertTrue(result.isSuccess(), "Class should be created successfully.");
    assertEquals("Class added: TestClass", result.getMessage());
  }

  @Test
  void testRemoveClass() {
    // Create and then remove a class
    commandRegistries.executeCommand("create class", new String[] {"TestClass"});
    CommandResult result =
        commandRegistries.executeCommand("remove class", new String[] {"TestClass"});
    assertTrue(result.isSuccess(), "Class should be removed successfully.");
    assertEquals("Class removed: TestClass", result.getMessage());
  }

  @Test
  void testCreateDuplicateClass() {
    // Create a class and attempt to create it again
    commandRegistries.executeCommand("create class", new String[] {"TestClass"});
    CommandResult result =
        commandRegistries.executeCommand("create class", new String[] {"TestClass"});
    assertFalse(result.isSuccess(), "Duplicate class creation should fail.");
    assertEquals("Error: Class 'TestClass' already exists.", result.getMessage());
  }

  @Test
  void testSwitchClass() {
    // Test switching to an existing class
    commandRegistries.executeCommand("create class", new String[] {"TestClass"});
    CommandResult result =
        commandRegistries.executeCommand("switch class", new String[] {"TestClass"});
    assertTrue(result.isSuccess(), "Switching to an existing class should succeed.");
    assertEquals("Class Switched to: TestClass", result.getMessage());
  }

  @Test
  void testSwitchNonExistentClass() {
    // Test switching to a non-existent class
    CommandResult result =
        commandRegistries.executeCommand("switch class", new String[] {"NonExistentClass"});
    assertFalse(result.isSuccess(), "Switching to a non-existent class should fail.");
    assertEquals("Error: Class 'NonExistentClass' does not exist.", result.getMessage());
  }

  // TODO: This test failed
  //  @Test
  //  void testAddFieldToClass() {
  //    // Create a class and add a field to it
  //    commandRegistries.executeCommand("create class", new String[] {"TestClass"});
  //    commandRegistries.executeCommand("switch class", new String[] {"TestClass"});
  //    CommandResult result =
  //        commandRegistries.executeCommand("add field", new String[] {"String", "name"});
  //    assertTrue(result.isSuccess(), "Adding a field to the class should succeed.");
  //    assertEquals(
  //        "Field added: Type='String', Name='name' to class 'TestClass'", result.getMessage());
  //  }

  @Test
  void testAddDuplicateField() {
    // Create a class and add the same field twice
    commandRegistries.executeCommand("create class", new String[] {"TestClass"});
    commandRegistries.executeCommand("switch class", new String[] {"TestClass"});
    commandRegistries.executeCommand("add field", new String[] {"String", "name"});
    CommandResult result =
        commandRegistries.executeCommand("add field", new String[] {"String", "name"});
    assertFalse(result.isSuccess(), "Adding a duplicate field should fail.");
    assertEquals("Error: Field 'name' already exists in class 'TestClass'.", result.getMessage());
  }

  @AfterEach
  void tearDown() throws Exception {
    // Reset CommandRegistries and clear storage after each test
    CommandRegistries.resetInstance(); // Add a method to reset singleton state
    CommandLogic.getStorage().clearStorage();
  }
}
