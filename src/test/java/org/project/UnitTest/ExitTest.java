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

class ExitTest {

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
        parser.parseCommand("exit");
    }
}