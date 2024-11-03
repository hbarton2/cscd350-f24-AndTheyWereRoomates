package org.project.View;
import org.project.Controller.UMLController;
import org.project.Model.UMLModel;

import java.util.Map;
import java.util.Scanner;


public class CLIView {

    private Scanner scanner;
    private UMLController controller;
    private UMLModel.Class currentClass = null;

    public CLIView() {
        this.scanner = new Scanner(System.in);
        this.controller = new UMLController();
    }
    public void runMenu() {
        System.out.println("Welcome to our UML Editor application");
        System.out.println("If you would like a list of commands enter 'help' ");
        String userInput = "";
        do {
            System.out.print("~ ");
            userInput = this.scanner.nextLine().toLowerCase().trim();
            if(!inputCheck(userInput)) {
                continue;
            }

            commandCheck(userInput.split(" "));


        }while(!userInput.equals("exit"));
    }

    private boolean inputCheck(String input) {

        // check for empty str
        if(input.isEmpty()) {
            System.out.println("Please enter a command");
            return false;
        }

        // check for number of arguments
        String[] str = input.split(" ");

        if(str[0].equals("help") && str.length < 2) {
            return help();
        }

        return !str[0].equals("exit") || str.length >= 2;
    }

    private void commandCheck(String[] input) {
        switch (input[0]) {
            case  "set" -> {
                if("class".equals(input[1]) && input.length ==3){
                    setCurrentClass(input[2]);
                }else{
                    System.out.println("invalid syntax. Useage: set class <class name>");
                }
            }
            case "add" -> addCommand(input);
            case "remove" -> removeCommand(input);
            case "rename" ->renameCommand(input);
            case "change" -> changeCommand(input);
            case "help" -> helpCommand(input);
            case "list" -> listCommand(input);
            case "save" -> saveCommand(input);
            case "load" -> loadCommand(input);
        };
    }

    private void setCurrentClass(final String className){
        UMLModel.Class selectedClass = controller.getStorage().getClass(className);
        if(selectedClass != null){
            currentClass = selectedClass;
            System.out.println("you selected class " + className);
        }else{
            System.out.println(className + " does not exist");
        }
    }


    private void addCommand(String[] input) {

            if(currentClass == null && !"class".equals(input[1])){
                System.out.println("no class selected. Try: set class <class name>");
                return;
            }
            switch(input[1]) {
            case "class" -> controller.classCommands.addClass(input);
            case "method" -> controller.methodCommands.addMethod(new String[]{"add","method",currentClass.getName(),input[2],});
            case "field" -> controller.fieldCommands.addField(new String[]{"add","field", currentClass.getName(), input[2], input[3]});
            case "parameter" -> controller.parameterCommands.addParameter(new String[]{"add","parameter",currentClass.getName(),input[2],input[3],input[4]});
            case "relationship" -> controller.relationshipCommands.addRelationship(new String[]{"add","relationship",input[2],input[3]});
        }
    }

    private void removeCommand(String[] input) {
        if(currentClass == null && !"class".equals(input[1])){
            System.out.println("no class selected. Try: set class <class name>");
            return;
        }
        switch(input[1]) {
            case "class" -> controller.classCommands.removeClass(input);
            case "method" -> controller.methodCommands.removeMethod(new String[]{"remove","method", currentClass.getName(), input[2]});
            case "field" -> controller.fieldCommands.removeField(new String[]{"remove","field", currentClass.getName(), input[2], input[3]});
            case "parameter" -> controller.parameterCommands.removeParameter(new String[]{"remove","parameter", currentClass.getName(), input[2], input[3]});
            case "relationship" -> controller.relationshipCommands.removeRelationship(new String[]{"remove","relationship", input[2], input[3]});
        }
    }

    private void renameCommand(String[] input) {
        if(currentClass == null && !"class".equals(input[1])){
            System.out.println("no class selected. Try: set class <class name>");
            return;
        }
        switch(input[1]) {
            case "class" -> controller.classCommands.renameClass(new String[]{"rename","class", currentClass.getName(), input[3], input[2]});
            case "method" -> controller.methodCommands.renameMethod(new String[]{"rename","method", currentClass.getName(), input[2], input[3]});
            case "field" -> controller.fieldCommands.renameField(new String[]{"rename","field", currentClass.getName(), input[2], input[3], input[4]});
            case "parameter" -> controller.parameterCommands.changeParameter(new String[]{"rename","parameter", currentClass.getName(), input[2], input[3], input[4],input[5]});
        }
    }

    private void changeCommand(String[] input) {
        if(currentClass == null){
            System.out.println("no class selected. Try: set class <class name>");
            return;
        }
        if("parameter".equals(input[1])){
            controller.parameterCommands.changeParameter(input);
        }else{
            System.out.println("Invalid change command.");
        }
    }

    private void listCommand(String[] input) {
        if(input.length < 2){
            System.out.println("invalid syntax. Try: load class");
        }
        switch (input[1].toLowerCase()) {
            case "class" -> {
                if(currentClass != null){
                    listClass(new String[]{"List", "class", currentClass.getName()});
                }else{
                    System.out.println("not active class set. use set command to set a class you want to see. Useage: set class <classname>");
                }
            }
            case "classes" -> listClasses();
            default -> System.out.println("Invalid list command");

        }
    }

    public void listClass(String[] input) {
        UMLModel.Class obj = controller.getStorage().getClass(input[2]);
        if(obj == null){
            System.out.println("no class selected. Try: set class <class name>");
            return;
        }


            System.out.println("Class name: " + obj.getName());
            System.out.println("Relationships: ");
            for (UMLModel.Relationship relate: obj.getRelationships()){
                System.out.println("----------------------");
                System.out.println("Source Class: " + relate.getSource());
                System.out.println("Destination class: " + relate.getDestination());
            }
            System.out.println("Fields:");
            for (UMLModel.Field field : obj.getFields()) {
                System.out.println("----------------------");
                System.out.println("Field name: " + field.getName());
                System.out.println("Field type: " + field.getType());
            }
            System.out.println("Methods: ");
            for (UMLModel.Method method : obj.getMethodList()) {
                System.out.println("----------------------");
                System.out.println("Method: " + method.getName());
                System.out.println();
                System.out.println("Parameters:");
                for (UMLModel.Parameter parameter : method.parameters) {
                    System.out.println("----------------------");
                    System.out.println("Name: " + parameter.getName());
                    System.out.println("Type: " + parameter.getType());
                }

            }


        
    }

    public void listClasses() {
        Map<String, UMLModel.Class> classes = controller.getStorage().getClasses();
        if(classes.isEmpty()){
            System.out.println("no classes to get.");
        }else {
            for (String className : classes.keySet()) {
                System.out.println(className);
            }
        }
    }

    public void listRelationship() {
        Map<String, UMLModel.Class> classes = controller.getStorage().getClasses();
        for (UMLModel.Class clazz : classes.values()) {
            for(UMLModel.Relationship relationship : clazz.getRelationships()){
                System.out.println(relationship.getSource() + " -> " + relationship.getDestination());
            }
        }
    }

    public void saveCommand(String[] input) {
        UMLModel.Save save = new UMLModel.Save(controller.getStorage());
        System.out.print("Enter file name: ");
        String filePath = scanner.nextLine().concat(".json");
        if(save.save(filePath)){
            System.out.println("Sucessfully Saved");
        }else{
            System.out.println("Not Sucessfully Saved");
        }
    }

    public void loadCommand(String[] input) {
        UMLModel.Load load = new UMLModel.Load(controller.getStorage());
        System.out.print("Enter file name:");
        String filePath = scanner.nextLine().concat(".json");
        if (load.Load(filePath)){
            System.out.println("Sucessfully Loaded");
        }else{
            System.out.println("Not Sucessfully Loaded");
        }

    }

    private void helpCommand(String[] command) {

        switch(command[1]) {
            case "set":
                System.out.println("Set command allows you set the class your working on for modifying the class");
                System.out.println("Syntax: set class [classname]");
                return;
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
                System.out.println();
                System.out.println("For Parameter");
                System.out.println("Syntax: add parameter [class name] - creates field and add it to class with [name]");
                System.out.println();
                System.out.println("For Relationship");
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
                System.out.println("Syntax: remove method [method name] - removes method with [method name] from class with [class name]");
                return;
            case "rename":
                System.out.println("Rename command allows you to rename existing class, method, field");
                System.out.println("Syntax: rename [object]");
                System.out.println();
                System.out.println("For method");
                System.out.println("Syntax: rename method [method name] - renames method with [method name] from class with [class name]");
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

    private boolean help() {
        System.out.println("Syntax for help: help <command you need help with>.");
        System.out.println();
        System.out.println("Existing commands are: ");
        System.out.println("- set: sets the class so you can work on it. ");
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
}