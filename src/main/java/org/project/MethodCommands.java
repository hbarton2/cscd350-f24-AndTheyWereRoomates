package org.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MethodCommands {

    public static void addMethod(Scanner scanner, Storage storage, String[] input) {
        if(input.length < 3) {
            System.out.println("Invalid number of arguments");
        } else {
            String className = input[2];
            if(!storage.list.containsKey(className)) {
                System.out.println("Class " + className + " does not exist");
            } else {

                Class classObject = storage.getClass(className);
                boolean result = false;

                do {
                    System.out.print("Enter method name and list of parameters (e.g., methodName param1 string param2 int): ");
                    String userInput = scanner.nextLine().toLowerCase().trim();

                    if(userInput.isEmpty()) {
                        System.out.println("Empty input");
                    } else {
                        String[] userInputParts = userInput.split(" ");
                        String methodName = userInputParts[0];

                        // Check if there are parameters to process
                        if (userInputParts.length < 3 || (userInputParts.length - 1) % 2 != 0) {
                            System.out.println("Invalid parameter list format.");
                        } else {

                            Method method = new Method(methodName);

                            // Parse parameter pairs
                            List<Parameter> parameterList = new ArrayList<>();
                            for (int i = 1; i < userInputParts.length - 1; i += 2) {
                                String paramName = userInputParts[i];
                                String paramType = userInputParts[i + 1];
                                method.addParameter(paramName, paramType);
                            }

                            // Add the method to the class
                            classObject.addMethod(methodName);
                            System.out.println("Method added successfully.");
                            result = true;
                        }
                    }
                }while(!result);


            }


        }
    }

    public static void removeMethod(Scanner scanner, Storage storage, String[] input) {
        if(input.length < 4) {
            System.out.println("Invalid number of arguments");
        } else {
            String className = input[2];
            String methodName = input[3];

            if(!storage.list.containsKey(className)) {
                System.out.println("Class " + className + " does not exist");
            } else {
                Class classObject = storage.getClass(className);
                classObject.removeMethod(methodName);
            }
        }
    }

    public static void renameMethod(Scanner scanner, Storage storage, String[] input) {
        if(input.length < 4) {
            System.out.println("Invalid number of arguments");
        } else {
            String className = input[2];
            String methodName = input[3];
            if(!storage.list.containsKey(className)) {
                System.out.println("Class " + className + " does not exist");
            } else {
                Class classObject = storage.getClass(className);
                classObject.renameMethod(methodName, className);
            }
        }
    }
}
