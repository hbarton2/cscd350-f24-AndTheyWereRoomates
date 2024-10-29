package org.project;

import java.util.Scanner;

public class UMLController {


    private UMLModel.Storage storage;
    private UMLModel.Save save;
    private UMLModel.Load load;

    public UMLController(){

        this.storage = new UMLModel.Storage();
        this.save = new UMLModel.Save(storage);
        this.load = new UMLModel.Load(storage);
    }
    class ClassCommands{

        public ClassCommands(final String name){


        }

        public void addClass(Scanner scanner, Storage storage, String[] input) {

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

        public void removeClass(Scanner scanner, Storage storage, String[] input) {
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

        public void renameClass(Scanner scanner, Storage storage, String[] input) {
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
                            System.out.println("The Class has been renamed to: " + newClassName);
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
                    System.out.println("The Class has been renamed to: " + newClassName);
                }
            }
        }
    }

    /**Commands tied to Field*/
    class FieldCommands{
        public void addField(Scanner scanner, Storage storage, String[] input) {
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

        public void removeField(Scanner scanner, Storage storage, String[] input) {
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

        public void renameField(Scanner scanner, Storage storage, String[] input) {
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

    /**Commands tied to Methods*/
    class MethodCommands{
        public void addMethod(Scanner scanner, Storage storage, String[] input) {
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
                                // Parse parameter pairs
                                classObject.addMethod(methodName);
                                Method method = classObject.getMethod(methodName);
                                for (int i = 1; i < userInputParts.length - 1; i += 2) {
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
        }

        public void removeMethod(Scanner scanner, Storage storage, String[] input) {
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

        public void renameMethod(Scanner scanner, Storage storage, String[] input) {
            if(input.length < 4) {
                System.out.println("Invalid number of arguments");
            } else {
                String className = input[2];
                String oldMethodName = input[3];
                if(!storage.list.containsKey(className)) {
                    System.out.println("Class " + className + " does not exist");
                    return;
                }

                System.out.print("Enter new method name: ");
                String newMethodName = scanner.nextLine().toLowerCase().trim();
                if(newMethodName.isEmpty()) {
                    System.out.println("Empty input");
                    return;
                }

                if(oldMethodName.equals(newMethodName)) {
                    System.out.println("Method " + oldMethodName + " already exists");
                    return;
                }

                Class classObject = storage.getClass(className);
                classObject.renameMethod(oldMethodName, newMethodName);
                System.out.println("Method " + oldMethodName + " renamed to " + newMethodName);
            }
        }
    }

    /**Commands tied to Parameters*/
    class ParameterCommands{
        public void addParameter(Scanner scanner, Storage storage, String[] input) {
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

        public void removeParameter(Scanner scanner, Storage storage, String[] input) {
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

        public void changeParameter(Scanner scanner, Storage storage, String[] input) {
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

    /**Commands tied to Relationship*/
    class RelationshipCommands{
        public boolean addRelationship(Scanner scanner, Storage storage, String[] input){
            if(input.length != 4){
                System.out.println("Invalid number of arguments");
                return false;

            }else{
                String source = input[2];
                String destination = input[3];


                //These if-statements check if the classes exist. If not the relationship cannot be created
                if(!storage.list.containsKey(source))System.out.println("Source Class does not exist");
                if(!storage.list.containsKey(destination)) System.out.println("Destination Class does not exist");

                Class srcClass = storage.getClass(source);



                srcClass.addRelation(srcClass.getName(), destination);
                System.out.println("Successful");
                return true;
            }
        }

        public boolean removeRelationship(Scanner scanner, Storage storage, String[] input){
            if(input.length != 4){
                System.out.println("Invalid number of arguments");
                return false;
            }else{

                String source = input[2];
                String destination = input[3];

                //Checks to see the source class exists
                if(!storage.list.containsKey(source)) System.out.println("source class does not exist");

                Class srcClass = storage.getClass(source);

                srcClass.removeRelation(srcClass.getName(), destination);
                System.out.println("Successful");
                return true;
            }
        }
    }
}
