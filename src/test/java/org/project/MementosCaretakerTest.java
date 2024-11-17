package org.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.Controller.CommandResult;
import org.project.Model.CommandRegistries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MementosCaretakerTest {

    //Get rid of this if it's unneeded.
    private CommandRegistries commandRegistries;
    @BeforeEach
    void setUp() {
        commandRegistries = new CommandRegistries("src/main/resources/CLICommands.json");
    }

    @Test
    void testUndo(){
        /*
        -Create Class
        -Record how many mementos are in the Undo stack: Before
        -Undo last action
        -Record how many mementos are currently in the undo stack: After
        -Compare the variables: After < Before
            If before is larger it works and pass

        OR ALTERNATIVELY

        -Create Class
        -Undo last action
        -Record text result
            The result should be "Undone"
         */
    }


    @Test
    void testUndoWithParameter(){
        String[] args = {"Banana"};
        CommandResult result = commandRegistries.executeCommand("undo", args);
        assertTrue(!(result.isSuccess()), "Command should not pass");
        assertEquals("No arguments needed", result.getMessage());
    }

    @Test
    void testUndoNothing(){

        //This test doesn't work. Don't know how to create a class beforehand so currentClass isn't null

        String[] args = {};
        CommandResult result = commandRegistries.executeCommand("undo", args);
        assertTrue(!(result.isSuccess()), "Command should not pass");
        assertEquals("Error: Nothing to undo", result.getMessage());
    }

    @Test
    void testUndoWithIncorrectInputs(){
        //This test ensure that bad inputs aren't added into the stack.
        //IE: 'add class banana' doesn't get added to the stack and are ignored.

        /*
        -Create Class
        -Fail to create a class (IE: use 'add class ...' instead of 'create class')
        -Undo last action
        -Record how many mementos are in the Undo Stack
            If the stack is 0, then it passes.
         */

    }

    @Test
    void testRedo(){
        /*
        -Create Class
        -Record how many mementos are in the Redo stack: Before
        -Undo last action
        -Record how many mementos are currently in the Redo stack: After
        -Compare the variables: After > Before
            If after is larger it works and pass

        OR ALTERNATIVELY

        -Create Class
        -Undo last action
        -Redo last action
        -Record text result
            The result should be "Redone"
         */
    }

    @Test
    void testEmptyRedo(){

        String[] args = {};
        CommandResult result = commandRegistries.executeCommand("redo", args);
        assertTrue(!(result.isSuccess()), "Command should not pass");
        assertEquals("Error: Nothing to redo", result.getMessage());
    }

}
