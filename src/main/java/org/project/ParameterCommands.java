package org.project;

import java.util.Scanner;

public class ParameterCommands {

    public static void addParameter(Scanner scanner, Storage storage, String[] input) {
        if (input.length != 4) {
            System.out.println("Invalid number of arguments");
            return;
        }

        String className = input[2];
        String methodName = input[3];

        if(!storage.list.containsKey(className)) {
            System.out.println("Class " + className + " does not exist");
            return;
        }

        Class classObject = storage.getClass(className);
        if(!classObject.hasMethod(methodName)) {
            System.out.println("Method " + methodName + " does not exist");
            return;
        }

        Method method = classObject.getMethod(methodName);

        System.out.print("Enter parameter name: ");
        String parameterName = scanner.nextLine().toLowerCase().trim();
        if(parameterName.isEmpty()) {
            System.out.println("Parameter name cannot be empty");
            return;
        }

        System.out.print("Enter parameter type: ");
        String parameterType = scanner.nextLine().toLowerCase().trim();
        if(parameterType.isEmpty()) {
            System.out.println("Parameter type cannot be empty");
            return;
        }

        method.addParameter(parameterName, parameterType);
        System.out.println("Parameter " + parameterName + " added to " + className + " " + methodName);
    }

    public static void removeParameter(Scanner scanner, Storage storage, String[] input) {
        if (input.length != 4) {
            System.out.println("Invalid number of arguments");
            return;
        }

        String className = input[2];
        String methodName = input[3];

        if(!storage.list.containsKey(className)) {
            System.out.println("Class " + className + " does not exist");
            return;
        }

        Class classObject = storage.getClass(className);
        if(!classObject.hasMethod(methodName)) {
            System.out.println("Method " + methodName + " does not exist");
            return;
        }

        Method method = classObject.getMethod(methodName);
        System.out.print("Enter parameter name: ");
        String parameterName = scanner.nextLine().toLowerCase().trim();
        if(parameterName.isEmpty()) {
            System.out.println("Parameter name cannot be empty");
            return;
        }

        if(!method.hasParameter(parameterName)) {
            System.out.println("Parameter " + parameterName + " does not exist");
        }

        method.deleteParameter(parameterName);
        System.out.println("Parameter " + parameterName + " removed from " + className + " " + methodName);

    }

    public static void changeParameter(Scanner scanner, Storage storage, String[] input) {
        if (input.length != 4) {
            System.out.println("Invalid number of arguments");
            return;
        }

        String className = input[2];
        String methodName = input[3];
        if(!storage.list.containsKey(className)) {
            System.out.println("Class " + className + " does not exist");
            return;
        }

        Class classObject = storage.getClass(className);
        if(!classObject.hasMethod(methodName)) {
            System.out.println("Method " + methodName + " does not exist");
            return;
        }

        Method method = classObject.getMethod(methodName);
//        System.out.print("Enter parameter name: ");
//        String parameterName = scanner.nextLine().toLowerCase().trim();
//        if(parameterName.isEmpty()) {
//            System.out.println("Parameter name cannot be empty");
//            return;
//        }

//        if(!method.hasParameter(parameterName)) {
//            System.out.println("Parameter " + parameterName + " does not exist");
//            return;
//        }

        method.deleteAllParameter();
        boolean result = false;
        do {
            System.out.print("Enter list of parameters (e.g., param1 string param2 int): ");
            String userInput = scanner.nextLine().toLowerCase().trim();

            if(userInput.isEmpty()) {
                System.out.println("Empty input");
            } else {
                String[] userInputParts = userInput.split(" ");

                // Check if there are parameters to process
                if (userInputParts.length < 2 || (userInputParts.length) % 2 != 0) {
                    System.out.println("Invalid parameter list format.");
                } else {
                    // Parse parameter pairs
                    method = classObject.getMethod(methodName);
                    for (int i = 0; i < userInputParts.length - 1; i += 2) {
                        String paramName = userInputParts[i];
                        String paramType = userInputParts[i + 1];
                        method.addParameter(paramName, paramType);
                    }

                    System.out.println("Method added successfully.");
                    result = true;
                }
            }
        }while(!result);
    }

}
