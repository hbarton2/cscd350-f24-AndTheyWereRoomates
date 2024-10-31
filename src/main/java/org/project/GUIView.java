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
            case "list" -> listCommand(input);
            case "help" -> helpCommand(input); // Add this line to handle the list command
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
    private boolean help() {
        System.out.println("Syntax for help: help <command you need help with>.");
        System.out.println();
        System.out.println("Existing commands are: ");
        System.out.println("- add:  create a new class, method, field, relationship, or parameter. ");
        System.out.println("- remove: delete existing class, method, field, relationship");
        System.out.println("- rename: rename existing class, method, field");
        System.out.println("- save: save existing class");
        System.out.println("- load: load existing class");
        System.out.println("- list: class: list existing class");
        System.out.println("- list: classes: list existing classes");
        System.out.println("- list: relationships: list existing relationships");
        System.out.println("- help: shows help message");
        System.out.println("- exit: exit application");
        return false;
    }
    private void helpCommand(String[] command) {

        switch(command[1]) {
            case "add":
                System.out.println("Add command allows you to create a new class, method, field, relationship, or parameter.");
                System.out.println("Syntax: add [object]");
                System.out.println();
                System.out.println("For class");
                System.out.println("Syntax: add class [name] - creates class with [name] in single command");
                System.out.println();
                System.out.println("For method");
                System.out.println("Syntax: add method [class name] - creates method and adds to class with [name]");
                System.out.println();
                System.out.println("For field");
                System.out.println("Syntax: add field [class name] - creates field and add it to class with [name]");
                return;
            case "remove":
                System.out.println("Remove command allows you to delete existing class, method, field, relationship");
                System.out.println("Syntax: remove class - prompts for class name to be removed");
                System.out.println();
                System.out.println("For class");
                System.out.println("Syntax: remove class [name] - removes class with [name] in single command");
                System.out.println();
                System.out.println("For method");
                System.out.println("Syntax: remove method [class name] [method name] - removes method with [method name] from class with [class name]");
                return;
            case "rename":
                System.out.println("Rename command allows you to rename existing class, method, field");
                System.out.println("Syntax: rename [object]");
                System.out.println();
                System.out.println("For method");
                System.out.println("Syntax: rename method [class name] [method name] - renames method with [method name] from class with [class name]");
                return;
            case "save":
                System.out.println("Save command allows you to save existing class");
                System.out.println("Syntax: save [object]");
                return;
            case "load":
                System.out.println("Load command allows you to load existing class");
                System.out.println("Syntax: load [object]");
                return;
            case "list":
                System.out.println("List command allows you to list existing class");
                System.out.println("Syntax: list [object]");
                System.out.println();
                System.out.println("For class");
                System.out.println("Syntax: list [class name] - list info about class with [class name]");
                return;
            case "exit":
                System.out.println("Exits the program");
                return;
            default:
                System.out.println("This command does not exist.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

