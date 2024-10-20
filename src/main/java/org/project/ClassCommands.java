package org.project;

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
}
