package org.project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.project.Controller.CommandParser;
import org.project.Controller.CommandResult;
import org.project.Model.CommandInfo;
import org.project.Model.CommandRegistries;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CommandParserTest {

    @Mock
    private CommandRegistries commandRegistries;  // Mock CommandRegistries

    @InjectMocks
    private CommandParser commandParser;  // CommandParser is injected with the mocked CommandRegistries

    @BeforeEach
    void setUp() {
        // Initialize mocks annotated with @Mock and inject them into the CommandParser
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testParseCommand_ValidCommand() {
        // Arrange
        String input = "testCommand arg1 arg2";
        CommandResult expectedResult = CommandResult.success("Executed testCommand with args: arg1, arg2");

        // Mocking the behavior of commandRegistries.executeCommand
        when(commandRegistries.executeCommand("testCommand", new String[]{"arg1", "arg2"}))
                .thenReturn(expectedResult);

        // Act
        CommandResult result = commandParser.parseCommand(input);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("Executed testCommand with args: arg1, arg2", result.getMessage());

        // Verify that executeCommand was called with the correct arguments
        verify(commandRegistries).executeCommand("testCommand", new String[]{"arg1", "arg2"});
    }

    @Test
    void testParseCommand_InvalidCommand() {
        // Arrange
        String input = "invalidCommand arg1";
        CommandResult expectedResult = CommandResult.failure("Unknown command: invalidCommand");

        // Mocking the behavior of commandRegistries.executeCommand
        when(commandRegistries.executeCommand("invalidCommand", new String[]{"arg1"}))
                .thenReturn(expectedResult);

        // Act
        CommandResult result = commandParser.parseCommand(input);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals("Unknown command: invalidCommand", result.getMessage());

        // Verify that executeCommand was called with the correct arguments
        verify(commandRegistries).executeCommand("invalidCommand", new String[]{"arg1"});
    }

    @Test
    void testGetCommandList() {
        // Arrange
        Map<String, CommandInfo> commands = Map.of(
                "testCommand", new CommandInfo("Executes test command", "testCommand [args]")
        );
        when(commandRegistries.getAllCommands()).thenReturn(commands);

        // Act
        String commandList = commandParser.getCommandList();

        // Assert
        assertNotNull(commandList);
        assertTrue(commandList.contains("testCommand"));
        assertTrue(commandList.contains("Executes test command"));
    }
}
