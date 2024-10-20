package org.project;

import java.util.Scanner;

public class FieldCommands {

    public static void addField(Scanner scanner, Storage storage, String[] input) {
        if(input.length != 3) {
            System.out.println("Invalid number of arguments");
        } else {
            String className = input[2];
            if(!storage.list.containsKey(className)) {
                System.out.println("Class " + className + " does not exist");
            } else {
                Class classObject = storage.getClass(className);
                String fieldName;
                String fieldType;
                boolean result = false;
                do{
                    System.out.print("Enter field name: ");
                    fieldName = scanner.nextLine().toLowerCase().trim();

                    if(fieldName.isEmpty()) {
                        System.out.println("Invalid field name");
                        continue;
                    }

                    System.out.print("Enter field type: ");
                    fieldType = scanner.nextLine().toLowerCase().trim();

                    if(fieldType.isEmpty()) {
                        System.out.println("Invalid field type");
                        continue;
                    }

                    if(!classObject.addField(fieldName, fieldType)) {
                        System.out.println("Field " + fieldName + " already exists");
                        continue;
                    }

                    classObject.addField(fieldName, fieldType);
                    System.out.println(fieldName + " added successfully to " + className );
                    result = true;

                }while(!result);
            }
        }
    }

    public static void removeField(Scanner scanner, Storage storage, String[] input) {
        if(input.length != 3) {
            System.out.println("Invalid number of arguments");
        } else {
            String className = input[2];
            if(!storage.list.containsKey(className)) {
                System.out.println("Class " + className + " does not exist");
            } else {
                Class classObject = storage.getClass(className);
                String fieldName;

                if(classObject.fields.isEmpty()) {
                    System.out.println("Class " + className + " does not have any fields");
                    return;
                }

                System.out.print("Enter field name: ");
                fieldName = scanner.nextLine().toLowerCase().trim();
                if(fieldName.isEmpty()) {
                    System.out.println("Invalid field name");
                } else {
                    if(!classObject.removeField(fieldName)) {
                        System.out.println("Field " + fieldName + " does not exist");
                    } else {
                        classObject.removeField(fieldName);
                        System.out.println(fieldName + " removed successfully from " + className );
                    }
                }


            }
        }
    }

    public static void renameField(Scanner scanner, Storage storage, String[] input) {
        if(input.length != 3) {
            System.out.println("Invalid number of arguments");
        } else {
            String className = input[2];
            if(!storage.list.containsKey(className)) {
                System.out.println("Class " + className + " does not exist");
            } else {
                Class classObject = storage.getClass(className);
                String oldFieldName;

                if(classObject.fields.isEmpty()) {
                    System.out.println("Class " + className + " does not have any fields");
                } else {
                    System.out.print("Enter field old name: ");
                    oldFieldName = scanner.nextLine().toLowerCase().trim();
                    if(oldFieldName.isEmpty()) {
                        System.out.println("Invalid field name");
                    } else {
                        System.out.print("Enter field new name: ");
                        String newFieldName = scanner.nextLine().toLowerCase().trim();

                        if(oldFieldName.equals(newFieldName)) {
                            System.out.println("Field " + oldFieldName + " already exists");
                            return;
                        }

                        if(!classObject.renameField(oldFieldName, newFieldName)) {
                            System.out.println("Field " + oldFieldName + " does not exist");
                            return;
                        }

                        classObject.renameField(oldFieldName, newFieldName);
                        System.out.println(oldFieldName + " renamed to " + newFieldName + " in class " + className );
                    }
                }
            }
        }
    }
}
