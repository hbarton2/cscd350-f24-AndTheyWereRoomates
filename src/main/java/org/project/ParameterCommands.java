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

        method.deleteParameter(parameterName);
        System.out.println("Parameter " + parameterName + " removed from " + className + " " + methodName);

    }

}
