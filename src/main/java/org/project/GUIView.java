package org.project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Map;

public class GUIView extends Application {

    private TextArea outputArea;
    private UMLController controller; // UMLController instance

    @Override
    public void start(Stage primaryStage) {
        controller = new UMLController(); // Initialize your controller

        // Setup UI Components
        primaryStage.setTitle("UML Editor");
        Label inputLabel = new Label("Enter Command:");
        TextField inputField = new TextField();
        Button executeButton = new Button("Execute");
        outputArea = new TextArea();
        outputArea.setEditable(false);

        VBox vbox = new VBox(10, inputLabel, inputField, executeButton, outputArea);
        vbox.setPadding(new Insets(20));
        primaryStage.setScene(new Scene(vbox, 800, 600));
        primaryStage.show();

        // Handle button click
        executeButton.setOnAction(event -> {
            String userInput = inputField.getText();
            handleCommand(userInput);
            inputField.clear();
        });
    }


    private void handleCommand(String userInput) {
        if (userInput.isEmpty()) {
            outputArea.appendText("Please enter a command.\n");
            return;
        }

        String[] inputArray = userInput.split(" ");
        commandCheck(inputArray);
    }

    private void commandCheck(String[] input) {
        if(input.length < 2){
            outputArea.appendText("Invalid Command");
            return;
        }
        switch (input[0].toLowerCase()) {
            case "add" -> addCommand(input);
            case "remove" -> removeCommand(input);
            case "rename" -> renameCommand(input);
            case "list" -> listCommand(input); // Add this line to handle the list command
            default -> outputArea.appendText("Unknown command\n");
        }
    }

    private void addCommand(String[] input) {
        if(input.length < 3){
            outputArea.appendText("Invalid Command");
            return;
        }
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
        if(input.length < 3){
            outputArea.appendText("Invalid Command");
            return;
        }
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
        if(input.length < 4){
            outputArea.appendText("Invalid Command");
            return;
        }
        switch (input[1].toLowerCase()) {
            case "class" -> controller.classCommands.renameClass(input);
            case "field" -> controller.fieldCommands.renameField(input);
            case "method" -> controller.methodCommands.renameMethod(input);
            default -> outputArea.appendText("Invalid rename command\n");
        }
    }
    private void listCommand(String[] input) {
        if (input.length < 2) {
            outputArea.appendText("Please specify what to list (e.g., list class <classname> or list classes).\n");
            return;
        }

        switch (input[1].toLowerCase()) {
            case "class" -> listClass(input);
            case "classes" -> listClasses();
            default -> outputArea.appendText("Invalid list command\n");
        }
    }

    // List a specific class
    private void listClass(String[] input) {
        if (input.length < 3) {
            outputArea.appendText("Please specify the class name.\n");
            return;
        }

        String className = input[2];
        UMLModel.Class obj = controller.getStorage().getClass(className);

        if (obj == null) {
            outputArea.appendText("Class with this name does not exist.\n");
        } else {
            outputArea.appendText("Class name: " + obj.getName() + "\n");
            outputArea.appendText("Relationships:\n");
            for (UMLModel.Relationship relate : obj.relation) {
                outputArea.appendText("----------------------\n");
                outputArea.appendText("Source Class: " + relate.getSource() + "\n");
                outputArea.appendText("Destination class: " + relate.getDestination() + "\n");
            }
            outputArea.appendText("Fields:\n");
            for (UMLModel.Field field : obj.fields) {
                outputArea.appendText("----------------------\n");
                outputArea.appendText("Field name: " + field.getName() + "\n");
                outputArea.appendText("Field type: " + field.getType() + "\n");
            }
            outputArea.appendText("Methods:\n");
            for (UMLModel.Method method : obj.methodlist) {
                outputArea.appendText("----------------------\n");
                outputArea.appendText("Method: " + method.getName() + "\nParameters:\n");
                for (UMLModel.Parameter parameter : method.parameters) {
                    outputArea.appendText("Name: " + parameter.getName() + "\n");
                    outputArea.appendText("Type: " + parameter.getType() + "\n");
                }
            }
        }
    }

    // List all classes
    private void listClasses() {
        Map<String, UMLModel.Class> classes = controller.getStorage().getClasses();

        if (classes == null || classes.isEmpty()) {
            outputArea.appendText("No classes available.\n");
            return;
        }

        outputArea.appendText("Classes:\n");
        for (String className : classes.keySet()) {
            outputArea.appendText(className + "\n");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

