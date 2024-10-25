package org.project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GUIView extends Application {

    private TextArea outputArea;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("UML Editor");

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

        String[] str = input.split(" ");
        if (str[0].equals("help") && str.length < 2) {
            return help();
        }

        if (str[0].equals("exit") && str.length < 2) {
            System.exit(0);
        }

        if (str.length != 2) {
            outputArea.appendText("Invalid number of arguments\n");
            return false;
        }

        return true;
    }

    private void commandCheck(String[] input) {
        switch (input[0]) {
            case "add" -> addCommand(input);
            case "help" -> helpCommand(input[1]);
        }
    }

    private void addCommand(String[] input) {
        switch (input[1]) {
            case "class" -> addClass();
            case "method" -> addMethod();
            case "field" -> addField();
            case "parameter" -> addParameter();
            case "relationship" -> addRelationship();
        }
    }

    private void addClass() {
        outputArea.appendText("Please enter a class name: \n");
        outputArea.appendText("added: Class Apple\n");
    }

    private void addField() {
        outputArea.appendText("Please enter a field name and type: \n");
        outputArea.appendText("added: seeds:int\n");
    }

    private void addMethod() {
        outputArea.appendText("Please enter a method name: \n");
        outputArea.appendText("added: Eat()\n");
    }

    private void addParameter() {
        outputArea.appendText("Please enter a parameter name and type: \n");
        outputArea.appendText("added: side:String\n");
    }

    private void addRelationship() {
        outputArea.appendText("Please enter a relationship name: \n");
        outputArea.appendText("added: destination = Orange\n");
    }

    private boolean helpCommand(String command) {
        switch (command) {
            case "add":
                outputArea.appendText("Add command allows you to create a new class, method, field, relationship, or parameter.\n");
                outputArea.appendText("Syntax: add [object]\n");
                return true;
            case "delete":
                outputArea.appendText("Delete command allows you to delete existing class, method, field, relationship\n");
                outputArea.appendText("Syntax: delete [object]\n");
                return true;
            case "rename":
                outputArea.appendText("Rename command allows you to rename existing class, method, field\n");
                outputArea.appendText("Syntax: rename [object]\n");
                return true;
            case "save":
                outputArea.appendText("Save command allows you to save existing class\n");
                outputArea.appendText("Syntax: save [object]\n");
                return true;
            case "load":
                outputArea.appendText("Load command allows you to load existing class\n");
                outputArea.appendText("Syntax: load [object]\n");
                return true;
            case "list":
                outputArea.appendText("List command allows you to list existing class\n");
                outputArea.appendText("Syntax: list [object]\n");
                return true;
            case "exit":
                System.exit(0);
            default:
                return false;
        }
    }

    private boolean help() {
        outputArea.appendText("Syntax: help [command]\n");
        outputArea.appendText("Existing commands are: \n");
        outputArea.appendText("- add: create a new class, method, field, relationship, or parameter.\n");
        outputArea.appendText("- delete: delete existing class, method, field, relationship\n");
        outputArea.appendText("- rename: rename existing class, method, field\n");
        outputArea.appendText("- save: save existing class\n");
        outputArea.appendText("- load: load existing class\n");
        outputArea.appendText("- list: list existing class\n");
        outputArea.appendText("- exit: exit application\n");
        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

