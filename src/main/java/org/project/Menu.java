package org.project;
import java.util.Scanner;


public class Menu {

    Scanner scanner;
    public Menu() {
        this.scanner = new Scanner(System.in);
    }
    public void runMenu() {
        System.out.println("Welcome to our UML Editor application");

        String userInput = "";
        do {
            System.out.print("~ ");
            userInput = this.scanner.nextLine();
            if(!inputCheck(userInput)) {
                continue;
            }

            commandCheck(userInput.split(" "));

            System.out.println("""
            Class Apple
            + seeds: Integer
            + eat(side: String)
            + destination: Orange""");


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

        if(str[0].equals("exit") && str.length < 2) {
            return false;
        }

        if(str.length != 2) {
            System.out.println("Invalid number of arguments");
            return false;
        }

        return true;

//        return switch (str[0]) {
//            case "add" -> addCommand(str[1]);
//            case "help" -> helpCommand(str[1]);
//            default -> true;
//        };


    }

    private void commandCheck(String[] input) {
        switch (input[0]) {
            case "add" -> addCommand(input);
            case "help" -> helpCommand(input[1]);
        };
    }

    private void addCommand(String[] input) {
        switch(input[1]) {
            case "class" -> addClass();
            case "method" -> addMethod();
            case "field" -> addField();
            case "parameter" -> addParameter();
            case "relationship" -> addRelationship();
        }
    }

    private void addClass() {
        System.out.println("Please enter a class name: ");
        System.out.println("added: Class Apple");

    }

    private void addField() {
        System.out.println("Please enter a field name and type: ");
        System.out.println("added: seeds:int");
    }

    private void addMethod() {
        System.out.println("Please enter a method name: ");
        System.out.println("added: Eat()");
    }

    private void addParameter() {
        System.out.println("Please enter a parameter name and type: ");
        System.out.println("added: side:String");
    }

    private void addRelationship() {
        System.out.println("Please enter a relationship name: ");
        System.out.println("added: destination = Orange");
    }

    private boolean helpCommand(String command) {

        switch(command) {
            case "add":
                System.out.println("Add command allows you to create a new class, method, field, relationship, or parameter.");
                System.out.println("Syntax: add [object]");
                return true;
            case "delete":
                System.out.println("Delete command allows you to delete existing class, method, field, relationship");
                System.out.println("Syntax: delete [object]");
                return true;
            case "rename":
                System.out.println("Rename command allows you to rename existing class, method, field");
                System.out.println("Syntax: rename [object]");
                return true;
            case "save":
                System.out.println("Save command allows you to save existing class");
                System.out.println("Syntax: save [object]");
                return true;
            case "load":
                System.out.println("Load command allows you to load existing class");
                System.out.println("Syntax: load [object]");
                return true;
            case "list":
                System.out.println("List command allows you to list existing class");
                System.out.println("Syntax: list [object]");
                return true;
            case "exit":
                System.out.println("Exits the program");
                return false;
            default:
                return false;
        }
    }

    private boolean help() {
        System.out.println("Syntax: help [command]");
        System.out.println();
        System.out.println("Existing commands are: ");
        System.out.println("- add:  create a new class, method, field, relationship, or parameter.");
        System.out.println("- delete: delete existing class, method, field, relationship");
        System.out.println("- rename: rename existing class, method, field");
        System.out.println("- save: save existing class");
        System.out.println("- load: load existing class");
        System.out.println("- list class: list existing class");
        System.out.println("- list classes: list existing classes");
        System.out.println("- list relationships: list existing relationships");
        System.out.println("- help: shows help message");
        System.out.println("- exit: exit application");
        return false;
    }
}
