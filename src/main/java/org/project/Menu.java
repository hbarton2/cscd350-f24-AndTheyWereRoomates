package org.project;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;


public class Menu {

    Scanner scanner;
    Storage storage;
    public Menu(Storage storage) {
        this.scanner = new Scanner(System.in);
        this.storage = storage;
    }
    public void runMenu() {
        System.out.println("Welcome to our UML Editor application");
        System.out.println("If you would like a list of commands enter 'help' ");
        String userInput = "";
        do {
            System.out.print("~ ");
            userInput = this.scanner.nextLine().toLowerCase();
            if(!inputCheck(userInput)) {
                continue;
            }

            commandCheck(userInput.split(" "));


        }while(!userInput.equals("exit"));
    }

    private boolean inputCheck(String input) {

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

        if(str[0].equals("exit") && str.length < 2) {
            return false;
        }

        if(str.length != 2) {
            System.out.println("Invalid number of arguments");
            return false;
        }

        return true;
    }

    private void commandCheck(String[] input) {
        switch (input[0]) {
            case "add" -> addCommand(input);
            case "help" -> helpCommand(input[1]);
            case "list" -> listCommand(input);
        };
    }


    private void addCommand(String[] input) {
        switch(input[1]) {
            case "class" -> addClass();
            case "method" -> addMethod();
            case "field" -> addField();
            case "parameter" -> addParameter();
            case "relationship" -> addRelationship();
        }
    }

    private void addClass() {
        System.out.print("Class name: ");
        String input = this.scanner.nextLine();
        this.storage.addClass(input);

    }

    private void addField() {
        System.out.print("Enter class name: ");
        String input = this.scanner.nextLine();
        Class objClass = this.storage.getClass(input);
        if(objClass == null) {
            System.out.println("Invalid name");
        }
        else {
            System.out.print("Enter field name:" );
            String fieldName = this.scanner.nextLine();
            System.out.print("Enter field type:");
            String fieldType = this.scanner.nextLine();
            objClass.addField(fieldName, fieldType);
        }
    }

    private void addMethod() {
        System.out.print("Class name: ");
        String input = this.scanner.nextLine();
        Class objClass = this.storage.getClass(input);
        if(objClass == null) {
            System.out.println("Invalid name");
        }
        else {
            System.out.print("Enter method name:" );
            input = this.scanner.nextLine();
            objClass.addMethod(input);
        }
    }

    private void addParameter() {
        System.out.print("Enter class name: ");
        String input = this.scanner.nextLine();
        Class objClass = this.storage.getClass(input);
        if(objClass == null) {
            System.out.println("Invalid name");
        }
        else {
            System.out.print("Enter method name: ");
            input = this.scanner.nextLine();
            ArrayList<Method> methods = objClass.methodlist;
            Method objMethod;
            for (Method method : methods) {
                if(method.getName().equals(input)) {
                    objMethod = method;
                    System.out.print("Enter parameter name:" );
                    String parameterName = this.scanner.nextLine();
                    System.out.print("Enter parameter type:");
                     String parameterType = this.scanner.nextLine();
                    objMethod.addParameter(parameterName, parameterType);
                } else {
                    System.out.println("Invalid input");
                }
            }
            
        }
    }

    private void addRelationship() {
        System.out.println("Please enter the class name of the source class: ");
        String input1 = this.scanner.nextLine();
        Class obj1 = this.storage.list.get(input1);
        if(input1 == null || input1.isBlank()){
            System.out.println("Class does not exist.");
        }
        System.out.println("Please enter the class name of the destination class : ");
        String input2 = this .scanner.nextLine();
        if(input2 == null || input2.isBlank()){
            System.out.println("Class does not exist.");
        }
        Class obj2 = this.storage.list.get(input2);
        obj1.addRelation(input1,input2);
    }

    private void listCommand(String[] input) {
        if(input[1].equals("class")) {
            listClass(input);
        }
        else if(input[1].equals("classes")) {
            listClasses();
        }
        else {
            System.out.println("Invalid input");
        }
    }

    public void listClass(String[] input) {
        System.out.print("You class is: " );
        String name = this.scanner.nextLine();
        Class obj = this.storage.list.get(name);
        if(obj == null) {
            System.out.println("Class with this name does not exists");
        } else {
            System.out.println("Class name: " + obj.getName());
            System.out.println("Relationships: ");
            for (Relationship relate: obj.relation){
                System.out.println("----------------------");
                System.out.println("Source Class: " + relate.getSource());
                System.out.println("Destination class: " + relate.getDestination());
            }
            System.out.println("Fields:");
            for (Field field : obj.fields) {
                System.out.println("----------------------");
                System.out.println("Field name: " + field.getName());
                System.out.println("Field type: " + field.getType());
            }
            System.out.println("Methods: ");
            for (Method method : obj.methodlist) {
                System.out.println("----------------------");
                System.out.println("Method: " + method.getName());
                System.out.println();
                System.out.println("Parameters:");
                for (Parameter parameter : method.parameters) {
                    System.out.println("----------------------");
                    System.out.println("Name: " + parameter.getName());
                    System.out.println("Type" + parameter.getType());
                }

            }
        }

        
    }

    public void listClasses() {
        System.out.println("Number of classese: " + this.storage.list.size());
        for (Map.Entry<String,Class> entry : this.storage.list.entrySet()) {
            System.out.println(entry.getValue().getName());
        }
    }

    private boolean helpCommand(String command) {

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

    private boolean help() {
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
