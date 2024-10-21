package org.project;
import java.util.Map;
import java.util.Scanner;


public class Menu {

    Scanner scanner;
    Storage storage;
    public Menu(Storage storage) {
        this.scanner = new Scanner(System.in);
        this.storage = storage;
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
            case "add" -> addCommand(input);
            case "remove" -> removeCommand(input);
            case "rename" ->renameCommand(input);
            case "change" -> changeCommand(input);
            case "help" -> helpCommand(input);
            case "list" -> listCommand(input);
        };
    }

    private void addCommand(String[] input) {
        switch(input[1]) {
            case "class" -> ClassCommands.addClass(this.scanner, this.storage, input);
            case "method" -> MethodCommands.addMethod(this.scanner, this.storage, input);
            case "field" -> FieldCommands.addField(this.scanner, this.storage, input);
            case "parameter" -> ParameterCommands.addParameter(this.scanner, this.storage, input);
            case "relationship" -> RelationshipMethods.addRelationship(this.scanner, this.storage, input);
        }
    }

    private void removeCommand(String[] input) {
        switch(input[1]) {
            case "class" -> ClassCommands.removeClass(this.scanner, this.storage, input);
            case "method" -> MethodCommands.removeMethod(this.scanner, this.storage, input);
            case "field" -> FieldCommands.removeField(this.scanner, this.storage, input);
            case "parameter" -> ParameterCommands.removeParameter(this.scanner, this.storage, input);
            case "relationship" -> RelationshipMethods.removeRelationship(this.scanner, this.storage, input);
        }
    }

    private void renameCommand(String[] input) {
        switch(input[1]) {
            case "class" -> ClassCommands.renameClass(this.scanner, this.storage, input);
            case "method" -> MethodCommands.renameMethod(this.scanner, this.storage, input);
            case "field" -> FieldCommands.renameField(this.scanner, this.storage, input);
        }
    }

    private void changeCommand(String[] input) {
        switch(input[1]) {
            case "parameter" -> ParameterCommands.changeParameter(this.scanner, this.storage, input);
        }
    }

    private void listCommand(String[] input) {
        if(input[1].equals("class")) {
            listClass(input);
        }
        else if(input[1].equals("classes")) {
            listClasses();
        }
        else {
            System.out.println("Invalid input");
        }
    }

    public void listClass(String[] input) {
        String className = input[2];
        if(className.isEmpty()) {
            System.out.println("Class name cannot be empty");
            return;
        }

        Class obj = this.storage.getClass(className);
        if(obj == null) {
            System.out.println("Class with this name does not exists");
        } else {
            System.out.println("Class name: " + obj.getName());
            System.out.println("Relationships: ");
            for (Relationship relate: obj.relation){
                System.out.println("----------------------");
                System.out.println("Source Class: " + relate.getSource());
                System.out.println("Destination class: " + relate.getDestination());
            }
            System.out.println("Fields:");
            for (Field field : obj.fields) {
                System.out.println("----------------------");
                System.out.println("Field name: " + field.getName());
                System.out.println("Field type: " + field.getType());
            }
            System.out.println("Methods: ");
            for (Method method : obj.methodlist) {
                System.out.println("----------------------");
                System.out.println("Method: " + method.getName());
                System.out.println();
                System.out.println("Parameters:");
                for (Parameter parameter : method.parameters) {
                    System.out.println("----------------------");
                    System.out.println("Name: " + parameter.getName());
                    System.out.println("Type: " + parameter.getType());
                }

            }
        }

        
    }

    public void listClasses() {
        System.out.println("Number of classes: " + this.storage.list.size());
        for (Map.Entry<String,Class> entry : this.storage.list.entrySet()) {
            System.out.println(entry.getValue().getName());
        }
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
                return;
            case "exit":
                System.out.println("Exits the program");
                return;
            default:
                System.out.println("This command does not exist.");
        }
    }

    private boolean help() {
        System.out.println("Syntax: help [command]");
        System.out.println();
        System.out.println("Existing commands are: ");
        System.out.println("- add:  create a new class, method, field, relationship, or parameter.");
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
