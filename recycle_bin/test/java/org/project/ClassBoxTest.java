package org.project.View;

import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClassBoxTest extends ApplicationTest {

    private ClassBox classBox;

    @BeforeEach
    void setUp() {
        // Initialize GraphicalClassNode with a test class name
        classBox = new ClassBox("TestClass");
    }

    // Test for correct class name initialization
    @Test
    void testGetName() {
        assertEquals("TestClass", classBox.getName(), "Expected the class name to be 'TestClass'");
    }

    // Test adding a field to fieldsListView
    @Test
    void testAddField() {
        // Base case: add one field
        classBox.addField("field1");
        assertEquals(1, classBox.fieldsListView.getItems().size(), "Expected fieldsListView to contain 1 item");
        assertEquals("field1", classBox.fieldsListView.getItems().get(0), "Expected fieldsListView to contain 'field1'");

        // Add another field
        classBox.addField("field2");
        assertEquals(2, classBox.fieldsListView.getItems().size(), "Expected fieldsListView to contain 2 items");
        assertEquals("field2", classBox.fieldsListView.getItems().get(1), "Expected fieldsListView to contain 'field2'");
    }

    // Test adding a method to methodsListView
    @Test
    void testAddMethod() {
        // Base case: add one method
        classBox.addMethod("method1");
        assertEquals(1, classBox.methodsListView.getItems().size(), "Expected methodsListView to contain 1 item");
        assertEquals("method1", classBox.methodsListView.getItems().get(0), "Expected methodsListView to contain 'method1'");

        // Add another method
        classBox.addMethod("method2");
        assertEquals(2, classBox.methodsListView.getItems().size(), "Expected methodsListView to contain 2 items");
        assertEquals("method2", classBox.methodsListView.getItems().get(1), "Expected methodsListView to contain 'method2'");
    }

    // Test that GraphicalClassNode inherits from VBox and has a specific style
    @Test
    void testClassBoxIsVBoxAndHasStyle() {
        assertTrue(classBox instanceof VBox, "Expected GraphicalClassNode to be an instance of VBox");
        assertEquals("-fx-border-width: 5; -fx-border-color: black", classBox.getStyle(), "Expected GraphicalClassNode to have a specific style applied");
    }

    // Test the initial preferred size of the GraphicalClassNode
    @Test
    void testPrefSize() {
        assertEquals(200, classBox.getPrefWidth(), "Expected preferred width to be 200");
        assertEquals(300, classBox.getPrefHeight(), "Expected preferred height to be 300");
    }
}
