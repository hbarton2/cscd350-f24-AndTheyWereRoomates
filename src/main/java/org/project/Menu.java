package org.project;

import java.util.Objects;
import java.util.Scanner;

public class Menu {
    public static void runMenu() {
        System.out.println("Welcome to our UML Editor application");

        String userInput = "";
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print("~ ");
            userInput = scanner.nextLine();
            if(!inputCheck(userInput)) {
                continue;
            }
        }while(!userInput.equals("exit"));
    }

    private static boolean inputCheck(String input) {

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

        return switch (str[0]) {
            case "help" -> helpCommand(str[1]);
            case "exit" -> false;
            default -> true;
        };


    }

    private static boolean helpCommand(String command) {

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

    private static boolean help() {
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
