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

            System.out.println("Your command was: " + userInput);

        }while(!userInput.equals("exit"));



    }

    private static boolean inputCheck(String input) {
        return switch (input.toLowerCase()) {
            case "add" -> true;
            case "edit" -> true;
            case "delete" -> true;
            case "help" -> true;
            case "exit" -> false;
            default -> false;
        };
    }
}
