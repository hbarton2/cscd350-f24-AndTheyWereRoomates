package org.project;

import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.TreeMap;

public class ClassCommands {

    public static void addClass(Scanner scanner, Storage storage, String[] input) {

        String userInput = "";
        boolean result = false;

        if(input.length == 2) {
            do {
                System.out.print("Enter class name: ");
                userInput = scanner.nextLine();
                if(userInput.isEmpty()) {
                    System.out.println("Enter valid class name");
                } else {
                    if(storage.list.containsKey(userInput)) {
                        System.out.println();
                        System.out.println("Class already exists");
                    } else {
                        storage.addClass(userInput);
                        result = true;
                    }
                }
            } while(!result);
        }
        else if(input.length == 3) {
            String className = input[2];
             if(storage.list.containsKey(className)) {
                 System.out.println("Class already exists");
             } else {
                 storage.addClass(className);
             }
        }
    }

    public static void removeClass(Scanner scanner, Storage storage, String[] input) {
        String userInput = "";
        boolean result = false;
        if(input.length == 2) {
            do {
                System.out.print("Enter class name: ");
                userInput = scanner.nextLine().toLowerCase().trim();
                if(userInput.isEmpty()) {
                    System.out.println("Enter non-empty class name");
                } else if(!storage.list.containsKey(userInput)) {
                    System.out.println("Class does not exist");
                } else {
                    result = storage.deleteClass(userInput);
                    System.out.println("Class removed: " + userInput);
                }
            } while(!result);
        } else if(input.length == 3) {
            String className = input[2];
            if(!storage.list.containsKey(className)) {
                System.out.println("Class does not exist");
            } else {
                storage.deleteClass(className);
                System.out.println("Class removed: " + className);
            }
        }
    }

    public static void renameClass(Scanner scanner, Storage storage, String[] input) {
        String userInput = "";
        boolean result = false;
        if(input.length == 2) {
            do {
                System.out.print("Enter class name: ");
                userInput = scanner.nextLine().toLowerCase().trim();
                if(userInput.isEmpty()) {
                    System.out.println("Enter non-empty class name");
                } else if(!storage.list.containsKey(userInput)) {
                    System.out.println("Class does not exist");
                } else {
                    String oldClassName = userInput;
                    System.out.print("Enter new name: ");
                    String newClassName = scanner.nextLine().toLowerCase().trim();

                    if(newClassName.isEmpty()) {
                        System.out.println("Enter non-empty class name");
                    } else if(storage.list.containsKey(newClassName)) {
                        System.out.println("Class with that name already exists");
                    } else {
                        storage.renameClass(oldClassName, newClassName);
                        result = true;
                    }
                }
            } while(!result);
        } else if(input.length == 3 || input.length > 4) {
            System.out.println("Invalid number of arguments ");
        } else if(input.length == 4) {
            String oldClassName = input[2];
            String newClassName = input[3];

            if(oldClassName.equals(newClassName)) {
                System.out.println("Class with name " + oldClassName +  " already exists");
            } else if(!storage.list.containsKey(oldClassName)) {
                System.out.println("Class" + oldClassName +  " does not exist");
            } else if(storage.list.containsKey(newClassName)) {
                System.out.println("Class with name " + newClassName +  " already exists");
            } else {
                storage.renameClass(oldClassName, newClassName);
            }
        }
    }
}
