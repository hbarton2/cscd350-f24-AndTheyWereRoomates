package org.project.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.project.Controller.CommandResult;

public class CommandLogic {

  private final Storage storage = new Storage();

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

  // Method to check if a class exists
  public boolean classExists(String className) {
    return storage.containsNode(className);
  }

  // Add a class to the set
  public void addClass(String className) {
    storage.addNode(className, new UMLClassNode(className));
  }

  // Remove a class from the set
  public void removeClass(String className) {
    storage.removeNode(className);
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

  public CommandResult addField(String[] args) {
    if (args.length < 2) {
      return CommandResult.failure("add field <field type> <field name>");
    }
    String fieldType = args[0];
    String fieldName = args[1];
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
    if (args.length < 1) {
      return CommandResult.failure("switch <existing classname>");
    } else if (args.length > 1) {
      return CommandResult.failure("switch <existing classname>");
    }

    String className = args[0];
    return CommandResult.success("Class Switched to: " + className);
  }

  public CommandResult listClasses(String[] args) {
    if (args.length > 0) {
      return CommandResult.failure("Syntax: list classes");
    }

    return CommandResult.success("Listing Classes...");
  }

  public CommandResult removeField(String[] args) {
    if (args.length < 1) {
      return CommandResult.failure("remove field <field name>");
    } else if (args.length > 1) {
      return CommandResult.failure("remove field <field name>");
    }

    String fieldName = args[0];
    return CommandResult.success("Field removed: " + fieldName);
  }

  public CommandResult renameField(String[] args) {
    if (args.length < 2) {
      return CommandResult.failure("rename field <existing field name> <new field name>");
    } else if (args.length > 2) {
      return CommandResult.failure("rename field <existing field name> <new field name>");
    }

    String fieldName = args[0];
    String newFieldName = args[1];
    return CommandResult.success("Field: " + fieldName + " to " + newFieldName);
  }

  public CommandResult addMethod(String[] args) {
    if (args.length < 2 || args.length % 2 != 0) {
      return CommandResult.failure(
          "add method <return type> <method name> [<parameter type> <parameter name> ...]");
    }

    String methodName = args[1];

    return CommandResult.success("Method added: " + methodName);
  }

  public CommandResult removeMethod(String[] args) {
    if (args.length < 2 || args.length % 2 != 0) {
      return CommandResult.failure(
          "remove method <method name> [<parameter type> <parameter name>...]");
    }

    String methodName = args[1];
    return CommandResult.success("Method deleted: " + methodName);
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
    return CommandResult.success("Renames Method: " + oldMethodName + " to " + newMethodName);
  }

  public CommandResult addParameters(String[] args) {
    if (args.length > 3) {
      return CommandResult.failure(
          "add parameter <method name> <parameter type> <parameter name> [<parameter type> <parameter name> ...]");
    } else if (args.length % 2 != 1) {
      return CommandResult.failure(
          "add parameter <method name> <parameter type> <parameter name> [<parameter type> <parameter name> ...]");
    }

    String methodName = args[0];
    return CommandResult.success("Parameters added to " + methodName);
  }

  public CommandResult removeParameters(String[] args) {
    if (args.length == 0) {
      return CommandResult.failure(
          "remove parameter <method name> <parameter name> [<parameter name> ...]");
    } else if (args.length < 2) {
      return CommandResult.success(
          "remove parameter <method name> <parameter name> [<parameter name> ...]");
    }

    String methodName = args[0];
    return CommandResult.success("Removed parameters from " + methodName);
  }

  public CommandResult renameParameters(String[] args) {
    if (args.length < 3) {
      return CommandResult.failure(
          "Usage: rename parameter <method name> <old parameter name> <new parameter name>");
    }
    String methodName = args[0];
    String oldParameterName = args[1];
    String newParameterName = args[2];

    return CommandResult.success(
        "Ready to rename parameter from "
            + oldParameterName
            + " to "
            + newParameterName
            + " in method "
            + methodName);
  }

  public CommandResult addRelationship(String[] args) {
    if (args.length == 0) {
      return CommandResult.failure("add relationship <relationship type> <target class name>");
    } else if (args.length < 2) {
      return CommandResult.failure("add relationship <relationship type> <target class name>");
    } else if (args.length > 2) {
      return CommandResult.failure("Too many arguments");
    }

    String relationType = args[0];
    String targetClass = args[1];
    return CommandResult.success(
        "Added relationship to " + targetClass + " type of " + relationType);
  }

  public CommandResult removeRelationship(String[] args) {
    if (args.length == 0) {
      return CommandResult.failure("remove relationship <relationship type> <target class name>");
    } else if (args.length < 2) {
      return CommandResult.failure("remove relationship <relationship type> <target class name>");
    } else if (args.length > 2) {
      return CommandResult.failure("remove relationship <relationship type> <target class name>");
    }
    String relationType = args[0];
    String targetClass = args[0];
    return CommandResult.success("Deleted relationship : " + targetClass + "Type: " + relationType);
  }

  public CommandResult listDetail(String[] args) {
    if (args.length != 0) {
      return CommandResult.failure("list detail ");
    }

    return CommandResult.success("Listing Detail...");
  }

  public CommandResult save(String[] args) {
    if (args.length == 0) {
      return CommandResult.failure("Specify file name");
    } else if (args.length > 1) {
      return CommandResult.failure("Too many arguments");
    }

    String fileName = args[0];
    return CommandResult.success("Saved" + fileName);
  }

  public CommandResult load(String[] args) {
    if (args.length == 0) {
      return CommandResult.failure("Specify fileName");
    } else if (args.length > 1) {
      return CommandResult.failure("Too many arguments");
    }
    return CommandResult.success("Loaded!");
  }

  public CommandResult undo(String[] args) {
    if (args.length != 0) {
      return CommandResult.failure("No arguments needed");
    }
    return CommandResult.success("Undone");
  }

  public CommandResult redo(String[] args) {
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
