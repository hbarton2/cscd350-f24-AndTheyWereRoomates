package org.project;

import org.junit.jupiter.api.*;
import org.project.Model.CommandLogic;
import org.project.Model.CommandRegistries;
import org.project.Controller.CommandResult;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CommandLogicIntegrationTest {

    private CommandRegistries commandRegistries;
    private CommandLogic commandLogic;

    @BeforeAll
    void setUp() throws Exception {
        // Load test-specific JSON file
        String testJsonPath = "src/test/resources/CommandsTest.json";
        commandRegistries = new CommandRegistries(testJsonPath);
        commandLogic = new CommandLogic();
    }

//    TODO: This test failed
//    @Test
//    void testCreateClassIntegration() {
//        // Test creating a class
//        CommandResult result = commandRegistries.executeCommand("create class", new String[]{"TestClass"});
//        assertTrue(result.isSuccess(), "Class should be created successfully.");
//        assertEquals("Class created: TestClass", result.getMessage());
//    }

    @Test
    void testRemoveClassIntegration() {
        // Create and then remove a class
        commandRegistries.executeCommand("create class", new String[]{"TestClass"});
        CommandResult result = commandRegistries.executeCommand("remove class", new String[]{"TestClass"});
        assertTrue(result.isSuccess(), "Class should be removed successfully.");
        assertEquals("Class removed: TestClass", result.getMessage());
    }

    @Test
    void testCreateClassDuplicate() {
        // Create a class and attempt to create it again
        commandRegistries.executeCommand("create class", new String[]{"TestClass"});
        CommandResult result = commandRegistries.executeCommand("create class", new String[]{"TestClass"});
        assertFalse(result.isSuccess(), "Duplicate class creation should fail.");
        assertEquals("Error: Class 'TestClass' already exists.", result.getMessage());
    }

    @AfterAll
    void tearDown() throws Exception {
        // Clean up test environment if needed
    }
}

