package org.project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GUIView extends Application {

    private TextArea outputArea;
    private UMLController controller; // UMLController instance

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("UML Editor");

        // Initialize UMLController
        controller = new UMLController();

        // Create GUI components
        Label inputLabel = new Label("Enter Command:");
        TextField inputField = new TextField();
        Button executeButton = new Button("Execute");
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefHeight(200);

        // Layout setup
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(inputLabel, inputField, executeButton, outputArea);

        // Handle button click to execute the command
        executeButton.setOnAction(event -> {
            String userInput = inputField.getText();
            handleCommand(userInput);
            inputField.clear();
        });

        Scene scene = new Scene(vbox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleCommand(String userInput) {
        if (!inputCheck(userInput)) {
            return;
        }
        String[] inputArray = userInput.split(" ");
        commandCheck(inputArray);
    }

    private boolean inputCheck(String input) {
        if (input.isEmpty()) {
            outputArea.appendText("Please enter a command\n");
            return false;
        }

        return true;
    }

    private void commandCheck(String[] input) {
        switch (input[0].toLowerCase()) {
            case "add" -> addCommand(input);
            case "remove" -> removeCommand(input);
            case "rename" -> renameCommand(input);
            default -> outputArea.appendText("Unknown command\n");
        }
    }

    private void addCommand(String[] input) {
        switch (input[1].toLowerCase()) {
            case "class" -> controller.classCommands.addClass(input);
            case "field" -> controller.fieldCommands.addField(input);
            case "method" -> controller.methodCommands.addMethod(input);
            case "parameter" -> controller.parameterCommands.addParameter(input);
            case "relationship" -> controller.relationshipCommands.addRelationship(input);
            default -> outputArea.appendText("Invalid add command\n");
        }
    }

    private void removeCommand(String[] input) {
        switch (input[1].toLowerCase()) {
            case "class" -> controller.classCommands.removeClass(input);
            case "field" -> controller.fieldCommands.removeField(input);
            case "method" -> controller.methodCommands.removeMethod(input);
            case "parameter" -> controller.parameterCommands.removeParameter(input);
            case "relationship" -> controller.relationshipCommands.removeRelationship(input);
            default -> outputArea.appendText("Invalid remove command\n");
        }
    }

    private void renameCommand(String[] input) {
        switch (input[1].toLowerCase()) {
            case "class" -> controller.classCommands.renameClass(input);
            case "field" -> controller.fieldCommands.renameField(input);
            case "method" -> controller.methodCommands.renameMethod(input);
            default -> outputArea.appendText("Invalid rename command\n");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

