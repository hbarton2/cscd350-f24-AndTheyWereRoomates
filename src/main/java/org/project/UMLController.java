package org.project;

import java.util.Scanner;

public class UMLController {


    private final UMLModel.Storage storage;
    private final UMLModel.Save save;
    private final UMLModel.Load load;
    public final ClassCommands classCommands;
    public final FieldCommands fieldCommands;
    public final MethodCommands methodCommands;
    public final RelationshipCommands relationshipCommands;
    public final ParameterCommands parameterCommands;

    public UMLController(){

        this.storage = new UMLModel.Storage();
        this.save = new UMLModel.Save(storage);
        this.load = new UMLModel.Load(storage);
        this.classCommands = new ClassCommands(storage);
        this.parameterCommands = new ParameterCommands(storage);
        this.methodCommands = new MethodCommands(storage);
        this.relationshipCommands = new RelationshipCommands(storage);
        this.fieldCommands = new FieldCommands(storage);
    }
    public UMLModel.Storage getStorage(){
        return this.storage;
    }
    public class ClassCommands{
        private final UMLModel.Storage storage;

        public ClassCommands(final UMLModel.Storage storage){
            this.storage = storage;
        }



        public void addClass(final String[] input) {

            if(input.length <= 2) {
                System.out.println("Invalid number of arguments. Useage: add class <classname>");
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

        public void removeClass(String[] input) {

            if(input.length <= 2) {
                System.out.println("Invalid number of arguments. Useage: remove class <classname>");
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

        public void renameClass(String[] input) {

            if(input.length <= 3) {
                System.out.println("Invalid number of arguments. Useage: rename class <old classname> <new classname>");
            }


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

    /**Commands tied to Field*/
    public class FieldCommands{
        private final UMLModel.Storage storage;

        public FieldCommands(UMLModel.Storage storage){
            this.storage = storage;
        }

        public void addField(final String[] input) {
            if(input.length <= 3) {
                System.out.println("Invalid number of arguments. Useage: add field <classname> <fieldname> <fieldtype>");
            } else {
                String className = input[2];
                String fieldName = input[3];
                String fieldType = input[4];

                UMLModel.Class classObject = storage.getClass(className);
                if(classObject == null){
                    System.out.println("Class does not exist");
                }else if(classObject.hasField(fieldName)){
                    System.out.println("Class already contains this field");
                }else{
                    classObject.addField(fieldName,fieldType);
                    System.out.println("Field added. Field name: " + fieldName + " Field type: " + fieldType);

                }
            }
        }

        public void removeField(final String[] input) {
            if(input.length >= 3) {
                System.out.println("Invalid number of arguments");
            } else {
                String className = input[2];
                String fieldName = input[3];

                UMLModel.Class classObject = storage.getClass(className);
                if (classObject == null){
                    System.out.println("Class does not exist");
                }else if(!classObject.hasField(fieldName)){
                    System.out.println("Field does not exist");
                }else{
                    classObject.removeField(fieldName);
                    System.out.println("field has been removed: " + fieldName);
                }
            }
        }

        public void renameField(final String[] input) {
            if(input.length >= 3) {
                System.out.println("Invalid number of arguments. Useage: rename field <className> <oldFieldName> <newFieldName>");
            } else {
                String className = input[2];
                String oldFieldName = input[3];
                String newFieldName = input[3];

                UMLModel.Class classObject = storage.getClass(className);
                if (classObject == null){
                    System.out.println("Class does not exist");
                }else if(!classObject.hasField(oldFieldName)){
                    System.out.println("Field does not exist");
                }else{
                    classObject.renameField(oldFieldName,newFieldName);
                    System.out.println("the field called: " + oldFieldName + " has been renamed to: " + newFieldName);
                }
            }
        }
    }

    /**Commands tied to Methods*/
    public class MethodCommands{
        private final UMLModel.Storage storage;

        public MethodCommands(UMLModel.Storage storage){
            this.storage = storage;
        }
        public void addMethod(final String[] input) {
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
    public class ParameterCommands{
        private final UMLModel.Storage storage;

        public ParameterCommands(UMLModel.Storage storage){
            this.storage = storage;
        }
        public void addParameter(final String[] input) {
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
    public class RelationshipCommands{
        private final UMLModel.Storage storage;

        public RelationshipCommands(UMLModel.Storage storage){
            this.storage = storage;
        }
        public boolean addRelationship(final String[] input){
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

        public boolean removeRelationship(final String[] input){
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
