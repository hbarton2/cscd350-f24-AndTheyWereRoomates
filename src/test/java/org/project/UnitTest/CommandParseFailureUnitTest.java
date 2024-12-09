package org.project.UnitTest;

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

public class CommandParseFailureUnitTest {

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
    void testAddClassPrompt(){
        CommandResult result = parser.parseCommand("create class");
        assertTrue(
                result.getMessage().contains("Usage: create class <class name>"),
                "Error message should indicate duplicate class.");
    }

    @Test
    void testRemoveClassPrompt(){
        CommandResult result = parser.parseCommand("remove class");
        assertTrue(
                result.getMessage().contains("remove class <classname>"),
                "Error message should indicate duplicate class.");
    }

    @Test
    void testRenameClassPrompt(){
        CommandResult result = parser.parseCommand("rename class");
        assertTrue(
                result.getMessage().contains("rename class <existing classname> <new classname>"),
                "Error message should indicate duplicate class.");
    }

    @Test
    void testSwitchClassPrompt(){
        CommandResult result = parser.parseCommand("switch class");
        assertTrue(
                result.getMessage().contains("switch class <existing classname>"),
                "Error message should indicate duplicate class.");
    }

    @Test
    void testListClassPrompt(){
        CommandResult result = parser.parseCommand("list class");
        assertTrue(
                result.getMessage().contains("Syntax: list classes"),
                "Error message should indicate duplicate class.");
    }



    @Test
    void testAddFieldPrompt(){
        CommandResult result = parser.parseCommand("add field");
        assertTrue(
                result.getMessage().contains("Usage: add field <field type> <field name>"),
                "Error message should indicate duplicate class.");
    }

    @Test
    void testRemoveFieldPrompt(){
        CommandResult result = parser.parseCommand("remove field");
        assertTrue(
                result.getMessage().contains("remove field <field name>"),
                "Error message should indicate duplicate class.");
    }

    @Test
    void testRenameFieldPrompt(){
        CommandResult result = parser.parseCommand("rename field");
        assertTrue(
                result.getMessage().contains("rename field <existing field name> <new field name> <newType>"),
                "Error message should indicate duplicate class.");
    }

    @Test
    void testAddMethodPrompt(){
        CommandResult result = parser.parseCommand("add method");
        assertTrue(
                result.getMessage().contains("add method <return type> <method name> [<parameter type> <parameter name> ...]"),
                "Error message should indicate duplicate class.");
    }

    @Test
    void testRemoveMethodPrompt(){
        CommandResult result = parser.parseCommand("remove method");
        assertTrue(
                result.getMessage().contains("remove method <method name> [<parameter type> <parameter name>...]"),
                "Error message should indicate duplicate class.");
    }

    @Test
    void testRenameMethodPrompt(){
        CommandResult result = parser.parseCommand("rename method");
        assertTrue(
                result.getMessage().contains("rename method <existing method name> <new method name> <return type>"),
                "Error message should indicate duplicate class.");
    }

    @Test
    void testAddParameterPrompt(){
        CommandResult result = parser.parseCommand("add parameter");
        assertTrue(
                result.getMessage().contains("add parameter <method name> <parameter type> <parameter name> [<parameter type> <parameter name> ...]"),
                "Error message should indicate duplicate class.");
    }

    @Test
    void testRemoveParameterPrompt(){
        CommandResult result = parser.parseCommand("remove parameter");
        assertTrue(
                result.getMessage().contains("remove parameter <method name> <parameter name> [<parameter name> ...]"),
                "Error message should indicate duplicate class.");
    }

    @Test
    void testRenameParameterPrompt(){
        CommandResult result = parser.parseCommand("rename parameter");
        assertTrue(
                result.getMessage().contains("Usage: rename parameter <method name> <old parameter name> <new parameter name>"),
                "Error message should indicate duplicate class.");
    }
    @Test
    void testAddRelationshipPrompt(){
        CommandResult result = parser.parseCommand("add relationship");
        assertTrue(
                result.getMessage().contains("add relationship <relationship type> <target class name>"),
                "Error message should indicate duplicate class.");
    }

    @Test
    void testRemoveRelationshipPrompt(){
        CommandResult result = parser.parseCommand("remove relationship");
        assertTrue(
                result.getMessage().contains("remove relationship <relationship type> <target class name>"),
                "Error message should indicate duplicate class.");
    }

    @Test
    void testListDetailPrompt(){
        CommandResult result = parser.parseCommand("list detail d");
        assertTrue(
                result.getMessage().contains("list detail"),
                "Error message should indicate duplicate class.");
    }

    @Test
    void testUndoPrompt(){
        CommandResult result = parser.parseCommand("undo class");
        assertTrue(
                result.getMessage().contains("No arguments needed"),
                "Error message should indicate duplicate class.");
    }

    @Test
    void testRedoPrompt(){
        CommandResult result = parser.parseCommand("undo class");
        assertTrue(
                result.getMessage().contains("No arguments needed"),
                "Error message should indicate duplicate class.");
    }

    @Test
    void testHelpPrompt(){
        CommandResult result = parser.parseCommand("help class");
        assertTrue(
                result.getMessage().contains("Usage: help"),
                "Error message should indicate duplicate class.");
    }
    @Test
    void testNewProjectPrompt(){
        CommandResult result = parser.parseCommand("new project class");
        assertTrue(
                result.getMessage().contains("Error: 'new project' does not take any arguments."),
                "Error message should indicate duplicate class.");
    }
}
