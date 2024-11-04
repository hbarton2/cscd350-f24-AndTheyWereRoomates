package org.project.View;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.project.Controller.UMLController;
import org.project.Model.UMLModel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CLIViewTests {

    private CLIView cliView;

    @Mock
    private UMLController mockController;
    @Mock
    private UMLModel.Class mockClass;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cliView = new CLIView();
        cliView.controller = mockController;
    }

    // Test inputCheck with empty input
    @Test
    void inputCheckEmptyInput() {
        // Base case: input is empty
        assertEquals(false, cliView.inputCheck(""), "Expected inputCheck to return false for empty input");
    }

    // Test setCurrentClass with a non-existing class
    @Test
    void setCurrentClassNonExisting() {
        String className = "NonExistingClass";
        when(mockController.getStorage().getClass(className)).thenReturn(null);

        // Capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        cliView.setCurrentClass(className);

        assertEquals(className + " does not exist\n", outContent.toString());
    }

    // Test setCurrentClass with an existing class
    @Test
    void setCurrentClassExisting() {
        String className = "ExistingClass";
        when(mockController.getStorage().getClass(className)).thenReturn(mockClass);

        // Capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        cliView.setCurrentClass(className);

        assertEquals("you selected class " + className + "\n", outContent.toString());
    }

    // Test listClasses with no classes
    @Test
    void listClassesNoClasses() {
        when(mockController.getStorage().getClasses()).thenReturn(Map.of());

        // Capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        cliView.listClasses();

        assertEquals("no classes to get.\n", outContent.toString());
    }

    // Test helpCommand with an unknown command
    @Test
    void helpCommandUnknownCommand() {
        // Capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String[] command = {"help", "unknown"};
        cliView.helpCommand(command);

        assertEquals("This command does not exist.\n", outContent.toString());
    }
}
