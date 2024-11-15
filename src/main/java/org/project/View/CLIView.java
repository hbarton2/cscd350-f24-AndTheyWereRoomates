package org.project.View;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;
import org.project.Controller.UMLController;
import org.project.Model.UMLModel;

/**
 * CLIView is a command-line interface for the UML Editor application, providing interactive
 * commands for creating, modifying, and managing UML classes, fields, methods, parameters, and
 * relationships.
 */

public class CLIView {

  private Scanner scanner;
  private UMLController controller;
  private UMLModel.Class currentClass = null;

  /**
   * Constructs a new CLIView instance which creates a scanner and UMLController.
   */
  public CLIView() {
    this.scanner = new Scanner(System.in);
    this.controller = new UMLController();
  }

  /**
   * Displays the menu and prompts the user for a valid input until the user inputs the exit command
   * that's being checked with the inputCheck method. While in the loop, it will check if the
   * entered text is a working command.
   */
  public void runMenu() {
    System.out.println("Welcome to our UML Editor application");
    System.out.println("If you would like a list of commands enter 'help' ");
    String userInput = "";
    do {
      System.out.print("~ ");
      userInput = this.scanner.nextLine().toLowerCase().trim();
      if (!inputCheck(userInput)) {
        continue;
      }

      commandCheck(userInput.split(" "));


    } while (!userInput.equals("exit"));
  }

  /**
   * inputCheck checks if the string is empty, if it says help, exit, or it has multiple words
   * separated by space.
   *
   * @param input is the user input represented as a string.
   * @return a boolean showing the validity of the input.
   */

  private boolean inputCheck(String input) {

    // check for empty str
    if (input.isEmpty()) {
      System.out.println("Please enter a command");
      return false;
    }

    // check for number of arguments
    String[] str = input.split(" ");

    if (str[0].equals("help") && str.length < 2) {
      return help();
    }

    return !str[0].equals("exit") || str.length >= 2;
  }

  /**
   * commandCheck checks the input for a matching command choice, then calls the corresponding
   * command.
   *
   * @param input is the user input represented as a list of strings.
   */

  private void commandCheck(String[] input) {
    switch (input[0]) {
      case "set" -> {
        if ("class".equals(input[1]) && input.length == 3) {
          setCurrentClass(input[2]);
        } else {
          System.out.println("invalid syntax. Usage: set class <class name>");
        }
      }
      case "add" -> addCommand(input);
      case "remove" -> removeCommand(input);
      case "rename" -> renameCommand(input);
      case "change" -> changeCommand(input);
      case "help" -> helpCommand(input);
      case "list" -> listCommand(input);
      case "save" -> saveCommand(input);
      case "load" -> loadCommand(input);
    }
  }

  /**
   * setCurrentClass takes an inputted class name and sets that class as the class being changed.
   * Outputs "you selected class " if there's a matching class and "<className> does not exist" if
   * there is no matching class.
   *
   * @param className is the name of the class being chosen.
   */

  private void setCurrentClass(final String className) {
    UMLModel.Class selectedClass = controller.getStorage().getClass(className);
    if (selectedClass != null) {
      currentClass = selectedClass;
      System.out.println("you selected class " + className);
    } else {
      System.out.println(className + " does not exist");
    }
  }

  /**
   * addCommand handles adding a class, method, field, parameter, or relationship. Calls the
   * appropriate UMLController add function for each type. Outputs "no class selected. Try: set
   * class <class name>" and returns if there is no set class, and it's not adding a class.
   *
   * @param input is the user input.
   */
  private void addCommand(String[] input) {

    if (currentClass == null && !"class".equals(input[1])) {
      System.out.println("no class selected. Try: set class <class name>");
      return;
    }
    switch (input[1]) {
      case "class" -> controller.classCommands.addClass(input);
      case "method" -> controller.methodCommands.addMethod(
        new String[]{"add", "method", currentClass.getName(), input[2],});
      case "field" -> controller.fieldCommands.addField(
        new String[]{"add", "field", currentClass.getName(), input[2], input[3]});
      case "parameter" -> controller.parameterCommands.addParameter(
        new String[]{"add", "parameter", currentClass.getName(), input[2], input[3], input[4]});
      case "relationship" -> controller.relationshipCommands.addRelationship(
        new String[]{"add", "relationship", input[2], input[3], input[4]});
    }
  }

  /**
   * removeCommand handles removing a class, method, field, parameter, or relationship. Calls the
   * appropriate UMLController remove function for each type. Outputs "no class selected. Try: set
   * class <class name>" and returns if there is no set class, and it's not removing a class.
   *
   * @param input is the user input.
   */

  private void removeCommand(String[] input) {
    if (currentClass == null && !"class".equals(input[1])) {
      System.out.println("no class selected. Try: set class <class name>");
      return;
    }
    switch (input[1]) {
      case "class" -> controller.classCommands.removeClass(input);
      case "method" -> controller.methodCommands.removeMethod(
        new String[]{"remove", "method", currentClass.getName(), input[2]});
      case "field" -> controller.fieldCommands.removeField(
        new String[]{"remove", "field", currentClass.getName(), input[2], input[3]});
      case "parameter" -> controller.parameterCommands.removeParameter(
        new String[]{"remove", "parameter", currentClass.getName(), input[2], input[3]});
      case "relationship" -> controller.relationshipCommands.removeRelationship(
        new String[]{"remove", "relationship", input[2], input[3], input[4]});
    }
  }

  /**
   * renameCommand handles renaming a class, method, field, or parameter. Calls the appropriate
   * UMLController rename function for each type. Outputs "no class selected. Try: set class <class
   * name>" and returns if there is no set class and it's not renaming a class
   *
   * @param input is the user input.
   */

  private void renameCommand(String[] input) {
    if (currentClass == null && !"class".equals(input[1])) {
      System.out.println("no class selected. Try: set class <class name>");
      return;
    }
    switch (input[1]) {
      case "class" -> controller.classCommands.renameClass(
        new String[]{"rename", "class", currentClass.getName(), input[3], input[2]});
      case "method" -> controller.methodCommands.renameMethod(
        new String[]{"rename", "method", currentClass.getName(), input[2], input[3]});
      case "field" -> controller.fieldCommands.renameField(
        new String[]{"rename", "field", currentClass.getName(), input[2], input[3], input[4]});
      case "parameter" -> controller.parameterCommands.changeParameter(
        new String[]{"rename", "parameter", currentClass.getName(), input[2], input[3], input[4],
          input[5]});
    }
  }

  /**
   * changeCommand calls the changeParameter function if there is a matching set class. Outputs "no
   * class selected. Try: set class <class name>" and returns if there is no set class.
   *
   * @param input is the user input.
   */
  private void changeCommand(String[] input) {
    if (currentClass == null) {
      System.out.println("no class selected. Try: set class <class name>");
      return;
    }
    if ("parameter".equals(input[1])) {
      controller.parameterCommands.changeParameter(input);
    } else {
      System.out.println("Invalid change command.");
    }
  }

  /**
   * listCommand checks if the input is valid. If the input is invalid length it prints "invalid
   * syntax. Try: load class" Then checks a valid input for whether it's choosing to list a class or
   * multiple classes. If it's not choosing to list a class or multiple classes, print "Invalid List
   * command"
   *
   * @param input is a string list representing the input.
   */

  private void listCommand(String[] input) {
    if (input.length < 2) {
      System.out.println("invalid syntax. Try: load class");
    }
    switch (input[1].toLowerCase()) {
      case "class" -> {
        if (currentClass != null) {
          listClass(new String[]{"List", "class", currentClass.getName()});
        } else {
          System.out.println(
            "not active class set. use set command to set a class you want to see. Usage: set class <classname>");
        }
      }
      case "classes" -> listClasses();
      default -> System.out.println("Invalid list command");

    }
  }

  /**
   * listClass checks if the input holds a valid class, if input class is invalid, prints "no class
   * selected. Try: set class <class name>" and returns. If input is valid, prints the class name,
   * all relationships, all fields, and all methods / parameters.
   *
   * @param input is a list of strings representing the user input.
   */

  public void listClass(String[] input) {
    UMLModel.Class obj = controller.getStorage().getClass(input[2]);
    if (obj == null) {
      System.out.println("no class selected. Try: set class <class name>");
      return;
    }

    System.out.println("Class name: " + obj.getName());
    System.out.println("Relationships: ");
    for (UMLModel.Relationship relate : obj.getRelationships()) {
      System.out.println("----------------------");
      System.out.println("Source Class: " + relate.getSource());
      System.out.println("Destination class: " + relate.getDestination());
    }
    System.out.println("Fields:");
    for (UMLModel.Field field : obj.getFields()) {
      System.out.println("----------------------");
      System.out.println("Field name: " + field.getName());
      System.out.println("Field type: " + field.getType());
    }
    System.out.println("Methods: ");
    for (UMLModel.Method method : obj.getMethodList()) {
      System.out.println("----------------------");
      System.out.println("Method: " + method.getName());
      System.out.println();
      System.out.println("Parameters:");
      for (UMLModel.Parameter parameter : method.parameters) {
        System.out.println("----------------------");
        System.out.println("Name: " + parameter.getName());
        System.out.println("Type: " + parameter.getType());
      }

    }


  }

  /**
   * listClasses is a method that lists the names of each class currently existing. If there are no
   * classes, print "no classes to get."
   */
  public void listClasses() {
    Map<String, UMLModel.Class> classes = controller.getStorage().getClasses();
    if (classes.isEmpty()) {
      System.out.println("no classes to get.");
    } else {
      for (String className : classes.keySet()) {
        System.out.println(className);
      }
    }
  }

  /**
   * listRelationship grabs all classes, and for each class, checks if there is a relationship then
   * prints the relationships.
   */
  public void listRelationship() {
    Map<String, UMLModel.Class> classes = controller.getStorage().getClasses();
    for (UMLModel.Class clazz : classes.values()) {
      for (UMLModel.Relationship relationship : clazz.getRelationships()) {
        System.out.println(relationship.getSource() + " -> " + relationship.getDestination());
      }
    }
  }

  /**
   * saveCommand takes a string list of a user input. Prompts the user for a file name, converts it
   * to a .json file, and saves it in src/main/resources/saves. Prints "Successfully Saved" if
   * successful, and "Not Successfully Saved" if unsuccessful.
   *
   * @param input is a String list representing user input.
   */
  public void saveCommand(String[] input) {
    UMLModel.Save save = new UMLModel.Save(controller.getStorage());
    System.out.print("Enter file name: ");
    String fileName = scanner.nextLine().concat(".json");

    // Specify the path within resources/saves directory
    String filePath = "src/main/resources/saves/" + fileName;

    // Ensure directory exists
    try {
      Files.createDirectories(Paths.get("src/main/resources/saves"));
    } catch (IOException e) {
      System.out.println("Could not create directory for saving files.");
      e.printStackTrace();
      return;
    }

    // Save the file and check success
    if (save.save(filePath)) {
      System.out.println("Successfully Saved");
    } else {
      System.out.println("Unable to save file.");
    }
  }


  /**
   * loadCommand prompts the user for a file name, loads the .json file from src/main/resources/saves,
   * and loads it into the application. Prints "Successfully Loaded" if successful, and "Not Successfully Loaded" if unsuccessful.
   *
   * @param input is a string list that is never used.
   */
  public void loadCommand(String[] input) {
    UMLModel.Load load = new UMLModel.Load(controller.getStorage());
    System.out.print("Enter file name:");
    String fileName = scanner.nextLine().concat(".json");

    // Specify the path within resources/saves directory
    String filePath = "src/main/resources/saves/" + fileName;

    // Load the file and check success
    if (load.load(filePath)) { // Updated method call here
      System.out.println("Successfully Loaded");
    } else {
      System.out.println("Not Successfully Loaded");
    }
  }


  /**
   * helpCommand takes a String list of the command, and parses it into a printed option. Prints the
   * corresponding response depending on which option help is called for. Details the command so
   * that the user understands how it's used and what it's used for.
   *
   * @param command is the user input checked for
   */
  private void helpCommand(String[] command) {
    switch (command[1]) {
      case "set":
        System.out.println("Syntax: set class [classname]");
        System.out.println(
          "Set command allows you set the class your working on for modifying the class");
        return;
      case "add":
        System.out.println(
          "Add command allows you to create a new class, method, field, relationship, or parameter.");
        System.out.println("Syntax: add [object]");
        System.out.println();
        System.out.println("For class");
        System.out.println("Syntax: add class [name] - creates class with [name]");
        System.out.println();
        System.out.println("For method");
        System.out.println(
          "Syntax: add method [name] - creates method and adds to the currently set class ");
        System.out.println();
        System.out.println("For field");
        System.out.println(
          "Syntax: add field [name] [type] - creates field and add it to the currently set class with [name] and the type [type]");
        System.out.println();
        System.out.println("For Parameter");
        System.out.println(
          "Syntax: add parameter [method name] [name] [type] - creates Parameter and add it to the currently set class with [name]");
        System.out.println();
        System.out.println("For Relationship");
        System.out.println(
          "Syntax: add relationship [class name] [another class] - creates Relationship between [class name] and [another class] ");

        return;
      case "remove":
        System.out.println(
          "Remove command allows you to delete existing class, method, field, parameter, or relationship");
        System.out.println("Syntax: remove [object] - prompts for class name to be removed");
        System.out.println();
        System.out.println("For class");
        System.out.println("Syntax: remove class - removes the currently set class");
        System.out.println();
        System.out.println("For method");
        System.out.println(
          "Syntax: remove method [method name] - removes method with [method name] from the currently set class");
        System.out.println();
        System.out.println("For field");
        System.out.println(
          "Syntax: remove field [field name] - removes field with [field name] from the currently set class");
        System.out.println();
        System.out.println("For parameter");
        System.out.println(
          "Syntax: remove field [method name] [parameter name] - removes [parameter name] from [method name] in the currently set class");
        System.out.println();
        System.out.println("For relationship");
        System.out.println(
          "Syntax: remove relationship [class name] [another class] - removes a Relationship between [class name] and [another class]");
        return;
      case "rename":
        System.out.println(
          "Rename command allows you to rename existing class, method, field, or parameter");
        System.out.println("Syntax: rename [object] - prompts for class name to be renamed");
        System.out.println();
        System.out.println("For class");
        System.out.println(
          "Syntax: rename class [old class name] [new class name] - renames the currently set class with the new name");
        System.out.println();
        System.out.println("For method");
        System.out.println(
          "Syntax: rename method [existing method] [new method name] - renames method with [new method name] in the currently set class");
        System.out.println();
        System.out.println("For field");
        System.out.println(
          "Syntax: rename field [existing field name] [new field name] [new field type] - renames field with a new name and type in the currently set class");
        System.out.println();
        System.out.println("For parameter");
        System.out.println(
          "Syntax: rename parameter [existing method] [existing parameter name] [new parameter name] [new parameter type]\n"
            +
            "-renames parameter with [new parameter name] [new parameter type]");
        return;
      case "save":
        System.out.println("Syntax: save");
        System.out.println("Save command allows you to save all your classes into a file");
        System.out.println("You'll be prompted to input the file name");
        return;
      case "load":
        System.out.println("Syntax: load");
        System.out.println("Load command allows you to load a json file");
        System.out.println(
          "You'll be prompted to input the file name. You don't need to include '.json' at the end");
        return;
      case "list":
        System.out.println("List command allows you to list existing class");
        System.out.println("Syntax: list class - list all info for your set class");
        System.out.println();
        System.out.println("Syntax: list classes - list all classes");
        return;
      case "exit":
        System.out.println("Exits the program");
        return;
      default:
        System.out.println("This command does not exist.");
    }
  }

  /**
   * The help commands shows the user all available options for commands. The help command also
   * shows the baseline for how the command should be formatted.
   *
   * @return a false boolean.
   */
  private boolean help() {
    System.out.println("Syntax for help: help <command you need help with>.");
    System.out.println();
    System.out.println("Existing commands are: ");
    System.out.println("- set: sets the class so you can work on it. ");
    System.out.println("- add:  create a new class, method, field, relationship, or parameter.");
    System.out.println("- remove: delete existing class, method, field, relationship");
    System.out.println("- rename: rename existing class, method, field");
    System.out.println("- save: save all your classes into a file");
    System.out.println("- load: load an existing json file");
    System.out.println("- list: class: list existing class");
    System.out.println("- list: classes: list existing classes");
    System.out.println("- list: relationships: list existing relationships");
    System.out.println("- help: shows help message");
    System.out.println("- exit: exit application");
    return false;

  }
}
