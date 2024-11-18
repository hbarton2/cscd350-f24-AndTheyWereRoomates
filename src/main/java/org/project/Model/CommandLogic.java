package org.project.Model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.project.Controller.ClassNodeService;
import org.project.Controller.CommandResult;
import org.project.Memento.Caretaker;
import org.project.Memento.Memento;

public class CommandLogic {

  private final Storage storage = new Storage();

  private UMLClassNode currentClass;
  private String loadedfileName = "";
  private final Caretaker caretaker = new Caretaker();

  public CommandLogic() {
    saveState(new String[]{});
  }

  public CommandResult saveState(String[] args) {
    caretaker.saveState(new Memento(new HashMap<>(storage.getAllNodes())));
    return CommandResult.success("State saved");
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
    saveState(new String[]{});
  }

  public boolean fieldExists(List<UMLClassNode.Field> fieldList, String name) {
    for (UMLClassNode.Field field : fieldList) {
      if (field.getName().equals(name)) {
        return true;
      }
    }
    return false;
  }

  public boolean methodExists(List<UMLClassNode.Method> methodList, String name) {
    for (UMLClassNode.Method method : methodList) {
      if (method.getName().equals(name)) {
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
    switchClass(new String[]{className});
    return CommandResult.success("Class created: " + className);
  }

  // TODO: Need a class where the logic and storage device

  public CommandResult addField(String[] args) {
    if (args.length < 2) {
      return CommandResult.failure("add field <field type> <field name>");
    }
    if (fieldExists(currentClass.getFields(), args[1])) {
      return CommandResult.failure("Error: Field '" + args[1] + "' already exists.");
    }

    UMLClassNode.Field field = new UMLClassNode.Field(args[0], args[1]);
    currentClass.getFields().add(field);
    storage.addNode(currentClass.getClassName(), currentClass);
    saveState(new String[]{});

    return CommandResult.success("Field added: " + args[0] + " " + args[1]);
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
      saveState(new String[]{});
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
    if (classExists(newClassName)) {
      return CommandResult.failure("Error: Class '" + newClassName + "' already exists.");
    }
    UMLClassNode classNode = storage.getNode(className);
    if (classNode != null) {
      storage.removeNode(className);
      classNode.setClassName(newClassName);
      storage.addNode(newClassName, classNode);
      saveState(new String[]{});

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
    saveState(new String[]{});

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

    if (!fieldExists(fields, fieldName)) {
      return CommandResult.failure("Error: Field '" + fieldName + "' already exists.");
    }
    for (UMLClassNode.Field field : fields) {
      if (field.getName().equals(fieldName)) {
        fields.remove(field);
        saveState(new String[]{});
        break;
      }
    }
    return CommandResult.success("Field removed: " + fieldName);
  }

  public CommandResult renameField(String[] args) {
    if (args.length != 3) {
      return CommandResult.failure("rename field <existing field name> <new field name> <newType>");
    }
    saveState(new String[]{});

    String oldfieldName = args[0];
    String newFieldName = args[1];
    String newFieldType = args[2];
    if (!fieldExists(currentClass.getFields(), oldfieldName)) {
      return CommandResult.failure("Error: Field '" + oldfieldName + "' does not exist.");
    }
    if (fieldExists(currentClass.getFields(), newFieldName)) {
      return CommandResult.failure("Error: Field '" + newFieldName + "' already exists.");
    }

    for (UMLClassNode.Field field : currentClass.getFields()) {
      if (field.getName().equals(oldfieldName)) {
        field.setName(newFieldName);
        field.setType(newFieldType);
        return CommandResult.success("Field: " + oldfieldName + " to " + newFieldName);
      }
    }
    return CommandResult.failure("Error: Failed to rename field.");
  }

  public CommandResult addMethod(String[] args) { // TODO: Implement overloading
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
    saveState(new String[]{});
    return CommandResult.success("Method added: " + methodName);
  }

  public CommandResult removeMethod(String[] args) {
    if (args.length < 1) {
      return CommandResult.failure(
        "remove method <method name> [<parameter type> <parameter name>...]");
    }
    /*
    if (!methodExists(currentClass.getMethods(), args[0])) {
      return CommandResult.failure("Error: Method '" + args[0] + "' does not exist.");
    }
    */

    for (UMLClassNode.Method method : currentClass.getMethods()) {
      if (method.getName().equals(args[0])) {
        currentClass.getMethods().remove(method);
        saveState(new String[]{});
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
        saveState(new String[]{});
        return CommandResult.success("Method: " + oldMethodName + " to " + newMethodName);
      }
    }
    return CommandResult.failure("Error: Failed to rename method");
  }

  public CommandResult addParameters(String[] args) { // TODO: Checks for duplicate Parameter names
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
        saveState(new String[]{});
        return CommandResult.success("Method: " + args[0] + " to " + args[1]);
      }
    }

    return CommandResult.failure("Error: Failed to rename method");
  }

  public CommandResult removeParameters(String[] args) { // TODO: Refactor/Cleanup
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
        saveState(new String[]{});
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
      "Ready to rename parameter from " + args[1] + " to " + args[2] + " in method " + args[0]);
  }

  public CommandResult addRelationship(String[] args) {
    if (args.length != 2) {
      return CommandResult.failure("add relationship <relationship type> <target class name>");
    }

    if (!classExists(args[1])) {
      return CommandResult.failure("Error: Class '" + args[1] + "' does not exist.");
    }
    if (currentClass.getClassName().equals(args[1])) {
      return CommandResult.failure("Error: Can't connect to itself");
    }

    UMLClassNode.Relationship relationship = new UMLClassNode.Relationship(args[0], args[1]);
    currentClass.getRelationships().add(relationship);
    saveState(new String[]{});
    return CommandResult.success("Added relationship to " + args[1] + " type of " + args[0]);
  }


  public CommandResult removeRelationship(String[] args) {
    if (args.length != 2) {
      return CommandResult.failure("remove relationship <relationship type> <target class name>");
    }

    for (UMLClassNode.Relationship relationship : currentClass.getRelationships()) {
      if (relationship.getTarget().equals(args[1]) || relationship.getType().equals(args[0])) {
        currentClass.getRelationships().remove(relationship);
        saveState(new String[]{});
        return CommandResult.success(
          "Relationship removed from " + args[1] + " type of " + args[0]);
      }
    }
    return CommandResult.failure("Error: Relationship " + args[1] + " not found.");
  }

  public CommandResult listDetail(String[] args) {
    if (args.length != 0) {
      return CommandResult.failure("list detail ");
    }
    if (currentClass == null) {
      return CommandResult.failure("Error: No class selected");
    }

    System.out.println(currentClass.toString());
    return CommandResult.success("Listing Detail...");
  }

  public CommandResult saveNewfile(String[] args) {
    if (args.length != 1) {
      return CommandResult.failure("Syntax : save <fileName>");
    }
    String filename = args[0];
    if (!filename.endsWith(".json")) {
      filename += ".json";
    }
    String filePath = "src/main/resources/saves/" + filename;

    ClassNodeService classNodeService = new ClassNodeService();
    classNodeService.StorageSaveToJsonArray(this.storage, filePath);
    return CommandResult.success("Saved");
  }

  public CommandResult save(String[] args) {
    if (args.length != 0) {
      return CommandResult.failure("syntax: save");
    }

    String filePath = "src/main/resources/saves/" + loadedfileName;
    ClassNodeService classNodeService = new ClassNodeService();
    classNodeService.StorageSaveToJsonArray(this.storage, filePath);
    return CommandResult.success("Saved");
  }

  public CommandResult load(String[] args) {
    if (args.length != 1) {
      return CommandResult.failure("syntax: load file <filName");
    }
    try {
      // Read JSON file into a String
      String jsonContent =
        Files.readString(Path.of("src/main/resources/saves/" + args[0] + ".json"));
      loadedfileName = args[0] + ".json";

      // Parse the JSON string into a JsonArray
      Gson gson = new Gson();
      JsonArray jsonArray = gson.fromJson(jsonContent, JsonArray.class);

      // Create an instance of ClassNodeService
      ClassNodeService classNodeService = new ClassNodeService();

      // Iterate over the array and create UMLClassNode for each JSON object
      for (JsonElement element : jsonArray) {
        JsonObject jsonObject = element.getAsJsonObject();
        UMLClassNode classNode = classNodeService.createClassNodeFromJson(jsonObject);
        storage.addNode(classNode.getClassName(), classNode);
      }
    } catch (IOException e) {
      System.err.println("Error reading JSON file: ");
    } catch (JsonSyntaxException e) {
      System.err.println("Error parsing JSON: " + e.getMessage());
    }
    return CommandResult.success("Loaded!");
  }

  public CommandResult undo(String[] args) {
    if (args.length != 0) {
      return CommandResult.failure("No arguments needed");
    }
    if (currentClass == null) {
      return CommandResult.failure("Error: No class selected");
    }
    Memento memento = caretaker.undo();
    if (memento != null) {
      storage.setAllNodes(memento.getState());
      updateCurrentClass();
      return CommandResult.success("Undone");
    }
    return CommandResult.failure("Error: Nothing to undo");
  }

  public CommandResult redo(String[] args) {
    if (args.length != 0) {
      return CommandResult.failure("No arguments needed");
    }
    Memento memento = caretaker.redo();
    if (memento != null) {
      storage.setAllNodes(memento.getState());
      updateCurrentClass();
      return CommandResult.success("Redone");
    }
    return CommandResult.failure("Error: Nothing to redo");
  }

  private void updateCurrentClass() {
    if (currentClass != null) {
      currentClass = storage.getNode(currentClass.getClassName());
    } else if (!storage.getStorage().isEmpty()) {
      currentClass = storage.getAllNodes().values().iterator().next();
    }

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
