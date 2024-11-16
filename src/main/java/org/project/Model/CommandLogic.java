package org.project.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.project.Controller.CommandResult;

public class CommandLogic {

  private final Storage storage = new Storage();

  private UMLClassNode currentClass;

  public CommandLogic(){
  }

  // Example command handler methods
  //  private CommandResult createClass(String[] args) {// TODO: should not be able to duplicate
  // same class
  //    if (args.length  == 0) {
  //      return CommandResult.failure("create class <class name>");
  //    }else if (args.length > 1){
  //      return CommandResult.failure("create class <class name>");
  //    }
  //
  //    String className = args[0];
  //    return CommandResult.success("Class created: " + className);
  //  }

  // Store class names

  public boolean classExists(String className) {
    return storage.containsNode(className);
  }

  // Add a class to the set
  public void addClass(String className) {
    storage.addNode(className, new UMLClassNode(className));
    currentClass = storage.getNode(className);
  }


  public boolean fieldExists(List<UMLClassNode.Field> fieldList, String name){
    for(UMLClassNode.Field field : fieldList) {
      if(field.getName().equals(name)){
        return true;
      }
    }
    return false;
  }
  public boolean methodExists(List<UMLClassNode.Method> methodList, String name){
    for(UMLClassNode.Method method : methodList) {
      if(method.getName().equals(name)){
        return true;
      }
    }
    return false;
  }

  // Example handler for 'create class' with validation
  public CommandResult createClass(String[] args) {
    if (args.length != 1) {
      return CommandResult.failure("Usage: create class <class name>");
    }

    String className = args[0];
    if (className.isBlank()) {
      return CommandResult.failure("Error: Class name cannot be empty.");
    }

    if (!className.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
      return CommandResult.failure(
          "Error: Invalid class name. Use letters, numbers, and underscores only.");
    }

    if (classExists(className)) {
      return CommandResult.failure("Error: Class '" + className + "' already exists.");
    }

    // Add class to the registry
    addClass(className);
    return CommandResult.success("Class created: " + className);
  }

  // TODO: Need a class where the logic and storage device

  public CommandResult addField(String[] args) {//TODO: does not check for field duplicates
    if (args.length < 2) {
      return CommandResult.failure("add field <field type> <field name>");
    }
    String fieldType = args[0];
    String fieldName = args[1];

    UMLClassNode.Field field = new UMLClassNode.Field(fieldType, fieldName);
    currentClass.getFields().add(field);
    storage.addNode(currentClass.getClassName(), currentClass);

    return CommandResult.success("Field added: " + fieldType + " " + fieldName);

  }

  public CommandResult removeClass(String[] args) {
    if (args.length < 1) {
      return CommandResult.failure("remove class <classname>");
    } else if (args.length > 1) {
      return CommandResult.failure("remove class <classname>");
    }
    if (!classExists(args[0])) {
      return CommandResult.failure("Error: Class '" + args[0] + "' does not exist.");
    } else {
      storage.removeNode(args[0]);
      return CommandResult.success("Class removed: " + args[0]);
    }
  }

  public CommandResult renameClass(String[] args) {
    if (args.length < 2) {
      return CommandResult.failure("rename class <existing classname> <new classname>");
    } else if (args.length > 2) {
      return CommandResult.failure("rename class <existing classname> <new classname>");
    }
    String className = args[0];
    String newClassName = args[1];
    if (!classExists(className)) {
      return CommandResult.failure("Error: Class '" + className + "' does not exist.");
      }
    if(classExists(newClassName)){
      return CommandResult.failure("Error: Class '" + newClassName + "' already exists.");
    }
    UMLClassNode classNode = storage.getNode(className);
    if (classNode != null) {
      storage.removeNode(className);
      classNode.setClassName(newClassName);
      storage.addNode(newClassName, classNode);
      return CommandResult.success("Class renamed: " + className + " to " + newClassName);
    } else {
      return CommandResult.failure("Error: Failed to rename class.");
    }
   // UMLClassNode classNode = storage.getNode(className);
   // classNode.setClassName(newClassName);
   // return CommandResult.success("Class renamed: " + className);
  }

  public CommandResult switchClass(String[] args) {
    if (args.length != 1) {
      return CommandResult.failure("switch class <class name>");
    }
    if (!classExists(args[0])) {
      return CommandResult.failure("Error: Class '" + args[0] + "' does not exist.");
    }
    currentClass = storage.getNode(args[0]);

    return CommandResult.success("Class Switched to: " + args[0]);
  }

  public CommandResult listClasses(String[] args) {
    if (args.length > 0) {
      return CommandResult.failure("Syntax: list classes");
    }

      storage.printAllNodes();
    return CommandResult.success("Classes listed!");
  }

  public CommandResult removeField(String[] args) {
    if (args.length < 1) {
      return CommandResult.failure("remove field <field name>");
    } else if (args.length > 1) {
      return CommandResult.failure("remove field <field name>");
    }

    String fieldName = args[0];
    List<UMLClassNode.Field> fields = currentClass.getFields();

    if(!fieldExists(fields, fieldName)){
      return CommandResult.failure("Error: Field '" + fieldName + "' already exists.");
    }
    for(UMLClassNode.Field field : fields){
      if(field.getName().equals(fieldName)){
        fields.remove(field);
        break;
      }
    }
    return CommandResult.success("Field removed: " + fieldName);
  }

  public CommandResult renameField(String[] args) {
    if (args.length < 2) {
      return CommandResult.failure("rename field <existing field name> <new field name>");
    } else if (args.length > 2) {
      return CommandResult.failure("rename field <existing field name> <new field name>");
    }

    String oldfieldName = args[0];
    String newFieldName = args[1];
    if (!fieldExists(currentClass.getFields(), oldfieldName)) {
      return CommandResult.failure("Error: Field '" + oldfieldName + "' does not exist.");
    }
    if(fieldExists(currentClass.getFields(), newFieldName)){
      return CommandResult.failure("Error: Field '" + newFieldName + "' already exists.");
    }

    for(UMLClassNode.Field field : currentClass.getFields()) {
      if(field.getName().equals(oldfieldName)){
        field.setName(newFieldName);
        return CommandResult.success("Field: " + oldfieldName + " to " + newFieldName);
      }
    }
    return CommandResult.failure("Error: Failed to rename field.");
  }

  public CommandResult addMethod(String[] args) {//TODO: Implement overloading
    if (args.length < 2 || args.length % 2 != 0) {
      return CommandResult.failure(
          "add method <return type> <method name> [<parameter type> <parameter name> ...]");
    }
    String returnType = args[0];
    String methodName = args[1];
    List<UMLClassNode.Method.Parameter> params = new ArrayList<>();

    for (int i = 2; i < args.length; i += 2) {
      String paramType = args[i];
      String paramName = args[i + 1];
      params.add(new UMLClassNode.Method.Parameter(paramType, paramName));
    }
    UMLClassNode.Method method = new UMLClassNode.Method(returnType, methodName, params, false);
    currentClass.getMethods().add(method);
    return CommandResult.success("Method added: " + methodName);
  }

  public CommandResult removeMethod(String[] args) {
    if (args.length < 2 || args.length % 2 != 0) {
      return CommandResult.failure(
          "remove method <method name> [<parameter type> <parameter name>...]");
    }
    if (!methodExists(currentClass.getMethods(), args[0])) {
      return CommandResult.failure("Error: Method '" + args[0] + "' does not exist.");
    }
    for (UMLClassNode.Method method : currentClass.getMethods()) {
      if (method.getName().equals(args[0])) {
        currentClass.getMethods().remove(method);
        return CommandResult.success("Method removed: " + method.getName());
      }
    }
    return CommandResult.success("Method deleted: " + args[0]);
  }

  public CommandResult renameMethod(String[] args) {
    if (args.length < 3) {
      return CommandResult.failure(
          "rename method <existing method name> <new method name> <return type>");
    } else if (args.length > 3) {
      return CommandResult.failure(
          "rename method <existing method name> <new method name> <return type>");
    }

    String oldMethodName = args[0];
    String newMethodName = args[1];
    String methodType = args[2];
    if (!methodExists(currentClass.getMethods(), oldMethodName)) {
      return CommandResult.failure("Error: Method '" + oldMethodName + "' does not exist.");
    }
    if (methodExists(currentClass.getMethods(), newMethodName)) {
      return CommandResult.failure("Error: Method '" + newMethodName + "' already exists.");
    }

    for (UMLClassNode.Method method : currentClass.getMethods()) {
      if (method.getName().equals(oldMethodName)) {
        method.setName(newMethodName);
        method.setType(methodType);
        return CommandResult.success("Method: " + oldMethodName + " to " + newMethodName);
      }
    }
    return CommandResult.failure("Error: Failed to rename method");
  }


  public CommandResult addParameters(String[] args) {//TODO: Checks for duplicate Parameter named
    if (args.length < 3) {
      return CommandResult.failure(
          "add parameter <method name> <parameter type> <parameter name> [<parameter type> <parameter name> ...]");
    } else if (args.length % 2 != 1) {
      return CommandResult.failure(
          "add parameter <method name> <parameter type> <parameter name> [<parameter type> <parameter name> ...]");
    }
    if (!methodExists(currentClass.getMethods(), args[0])) {
      return CommandResult.failure("Error: Method '" + args[0] + "' does not exist.");
    }

    List<UMLClassNode.Method.Parameter> params = new ArrayList<>();
    for (int i = 1; i < args.length; i += 2) {
      String paramType = args[i];
      String paramName = args[i + 1];
      params.add(new UMLClassNode.Method.Parameter(paramType, paramName));
    }

    for (UMLClassNode.Method method : currentClass.getMethods()) {
      if (method.getName().equals(args[0])) {
        method.getParameters().addAll(params);
        return CommandResult.success("Method: " + args[0] + " to " + args[1]);
      }
    }

    return CommandResult.failure("Error: Failed to rename method");
  }

  public CommandResult removeParameters(String[] args) {//TODO: Refactor/Cleanup
    if (args.length == 0) {
      return CommandResult.failure(
          "remove parameter <method name> <parameter name> [<parameter name> ...]");
    } else if (args.length < 2) {
      return CommandResult.success(
          "remove parameter <method name> <parameter name> [<parameter name> ...]");
    }

    String methodName = args[0];
    List<String> parameterNamesToRemove = new ArrayList<>();
    for (int i = 1; i < args.length; i++) {
      parameterNamesToRemove.add(args[i]);
    }

    if (currentClass == null) {
      return CommandResult.failure("Error: No class selected.");
    }

    for (UMLClassNode.Method method : currentClass.getMethods()) {
      if (method.getName().equals(methodName)) {
        method.getParameters().removeIf(param -> parameterNamesToRemove.contains(param.getName()));

        return CommandResult.success("Parameters removed from " + methodName);
      }
    }

    return CommandResult.failure("Error: Method " + methodName + " not found.");

  }

  public CommandResult renameParameters(String[] args) {
    if (args.length < 3) {
      return CommandResult.failure(
          "Usage: rename parameter <method name> <old parameter name> <new parameter name>");
    }

    return CommandResult.success(
        "Ready to rename parameter from "
            + args[1]
            + " to "
            + args[2]
            + " in method "
            + args[0]);
  }

  public CommandResult addRelationship(String[] args) {
    if (args.length != 2){
      return CommandResult.failure("add relationship <relationship type> <target class name>");
    }

    if (!classExists(args[1])){
      return CommandResult.failure("Error: Class '" + args[1] + "' does not exist.");
    }
    if (currentClass.getClassName().equals(args[1])) {
      return CommandResult.failure("Error: Can't connect to itself");
    }

    UMLClassNode.Relationship relationship = new UMLClassNode.Relationship(args[0], args[1]);
    currentClass.getRelationships().add(relationship);
    return CommandResult.success(
        "Added relationship to " + args[1] + " type of " + args[0]);
  }

  public CommandResult removeRelationship(String[] args) {
    if(args.length != 2){
      return CommandResult.failure("remove relationship <relationship type> <target class name>");
    }

    for(UMLClassNode.Relationship relationship : currentClass.getRelationships()){
      if (relationship.getTarget().equals(args[1]) || relationship.getType().equals(args[0])){
        currentClass.getRelationships().remove(relationship);
        return CommandResult.success("Relationship removed from " + args[1] + " type of " + args[0]);
      }
    }
    return CommandResult.failure("Error: Relationship " + args[1] + " not found.");
  }

  public CommandResult listDetail(String[] args) {
    if (args.length != 0) {
      return CommandResult.failure("list detail ");
    }
    System.out.println(currentClass.toString());

    return CommandResult.success("Listing Detail...");
  }

  public CommandResult save(String[] args) {//TODO: Save isn't currently working
    if (args.length == 0) {
      return CommandResult.failure("Specify file name");
    } else if (args.length > 1) {
      return CommandResult.failure("Too many arguments");
    }

    String fileName = args[0];
    return CommandResult.success("Saved" + fileName);
  }

  public CommandResult load(String[] args) {//TODO: Load does not work
    if (args.length == 0) {
      return CommandResult.failure("Specify fileName");
    } else if (args.length > 1) {
      return CommandResult.failure("Too many arguments");
    }
    return CommandResult.success("Loaded!");
  }

  public CommandResult undo(String[] args) {//TODO: UNDO does not work
    if (args.length != 0) {
      return CommandResult.failure("No arguments needed");
    }
    return CommandResult.success("Undone");
  }

  public CommandResult redo(String[] args) {//TODO: REDO does not work
    if (args.length != 0) {
      return CommandResult.failure("No arguments needed");
    }
    return CommandResult.success("Undone");
  }

  public CommandResult help(String[] args) {
    if (args.length != 0) {
      return CommandResult.failure("No arguments needed");
    }
    return CommandResult.success("Help Menu: ");
  }

  public CommandResult exit(String[] args) {
    if (args.length != 0) {
      return CommandResult.failure("No arguments needed");
    }
    return CommandResult.success("Exiting Program");
  }

  // TODO: Additional command handler methods as needed...
}
