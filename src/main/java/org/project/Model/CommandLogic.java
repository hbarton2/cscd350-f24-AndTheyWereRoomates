package org.project.Model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.project.Controller.ClassNodeService;
import org.project.Controller.CommandResult;
import org.project.Controller.GraphicalUserInterfaceController;
import org.project.Memento.Caretaker;
import org.project.Memento.Memento;

// TODO: There is a lot of logic it's not kosher
public class CommandLogic {

  private static final DataStorage STORAGE = DataStorage.getInstance();

  private UMLClassNode currentClass;
  private String loadedfileName = "";
  private final Caretaker caretaker = new Caretaker();

  public CommandLogic() {
    saveState(new String[] {});
  }

  public CommandResult saveState(String[] args) {
    caretaker.saveState(new Memento(new HashMap<>(STORAGE.getAllNodes())));
    return CommandResult.success("State saved");
  }

  // Store class names
  public static DataStorage getStorage() {
    return STORAGE;
  }

  public boolean classExists(String className) {
    return STORAGE.containsNode(className);
  }

  public boolean fieldExists(List<UMLClassNode.Field> fieldList, String name) {
    for (UMLClassNode.Field field : fieldList) {
      if (field.getName().equals(name)) {
        return true; // Field exists
      }
    }

    return false; // Field does not exist
  }

  public boolean methodExists(List<UMLClassNode.Method> methodList, String name) {
    for (UMLClassNode.Method method : methodList) {
      if (method.getName().equals(name)) {
        return true;
      }
    }
    return false;
  }

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

    // Add the class to DATA_STORAGE and set it as the current class
    CommandResult result = addClass(className);
    if (result.isSuccess()) {
      currentClass = STORAGE.getNode(className); // Set the newly added class as the current class
    }

    return result;
  }

  // Private helper function to add a class to DATA_STORAGE
  private CommandResult addClass(String className) {
    if (classExists(className)) {
      return CommandResult.failure("Error: Class '" + className + "' already exists.");
    }

    // Add the class to DATA_STORAGE
    UMLClassNode newClass = new UMLClassNode(className);
    STORAGE.addNode(className, newClass);
    saveState(new String[] {}); // Save state after adding
    return CommandResult.success("Class added: " + className);
  }

  public CommandResult addField(String[] args) {
    if (args.length != 2) {
      return CommandResult.failure("Usage: add field <field type> <field name>");
    }

    if (currentClass == null) {
      return CommandResult.failure(
          "Error: No class selected. Use 'switch class <class name>' first.");
    }

    if (fieldExists(currentClass.getFields(), args[1])) {
      return CommandResult.failure(
          "Error: Field '"
              + args[1]
              + "' already exists in class '"
              + currentClass.getClassName()
              + "'.");
    }

    // Create and add the field
    UMLClassNode.Field field = new UMLClassNode.Field(args[0], args[1]);
    currentClass.getFields().add(field);

    // Update DATA_STORAGE with current class
    STORAGE.updateNode(currentClass.getClassName(), currentClass);

    // Save the current state
    saveState(new String[] {});

    // Only return the success message without any additional logging
    return CommandResult.success(
        "Field added: Type='"
            + args[0]
            + "', Name='"
            + args[1]
            + "' to class '"
            + currentClass.getClassName()
            + "'.");
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
      STORAGE.removeNode(args[0]);
      saveState(new String[] {});
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
    UMLClassNode classNode = STORAGE.getNode(className);
    if (classNode != null) {
      STORAGE.removeNode(className);
      classNode.setClassName(newClassName);
      STORAGE.addNode(newClassName, classNode);
      saveState(new String[] {});

      return CommandResult.success("Class renamed: " + className + " to " + newClassName);
    } else {
      return CommandResult.failure("Error: Failed to rename class.");
    }
  }

  public CommandResult switchClass(String[] args) {
    if (args.length != 1) {
      return CommandResult.failure("switch class <existing classname>");
    }
    if (!classExists(args[0])) {
      return CommandResult.failure("Error: Class '" + args[0] + "' does not exist.");
    }
    currentClass = STORAGE.getNode(args[0]);
    return CommandResult.success("Class Switched to: " + args[0]);
  }

  public CommandResult listClasses(String[] args) {
    if (args.length > 0) {
      return CommandResult.failure("Syntax: list classes");
    }

    // Get all nodes from DATA_STORAGE
    Map<String, UMLClassNode> nodes = STORAGE.getAllNodes();

    if (nodes.isEmpty()) {
      return CommandResult.success("No classes to display.");
    }

    StringBuilder result = new StringBuilder("Classes:\n");

    // Iterate over all nodes and append their details
    for (UMLClassNode node : nodes.values()) {
      result.append(node.toString()).append("\n");
    }

    return CommandResult.success(result.toString());
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
        saveState(new String[] {});
        break;
      }
    }
    return CommandResult.success("Field removed: " + fieldName);
  }

  public CommandResult renameField(String[] args) {
    if (args.length != 3) {
      return CommandResult.failure("rename field <existing field name> <new field name> <newType>");
    }
    saveState(new String[] {});

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

  public CommandResult addMethod(String[] args) {
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
    saveState(new String[] {});
    return CommandResult.success("Method added: " + methodName);
  }

  public CommandResult removeMethod(String[] args) {
    if (args.length < 1) {
      return CommandResult.failure(
          "remove method <method name> [<parameter type> <parameter name>...]");
    }

    for (UMLClassNode.Method method : currentClass.getMethods()) {
      if (method.getName().equals(args[0])) {
        currentClass.getMethods().remove(method);
        saveState(new String[] {});
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
        saveState(new String[] {});
        return CommandResult.success("Method: " + oldMethodName + " to " + newMethodName);
      }
    }
    return CommandResult.failure("Error: Failed to rename method");
  }

  public CommandResult addParameters(String[] args) {
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
        saveState(new String[] {});
        return CommandResult.success("Method: " + args[0] + " to " + args[1]);
      }
    }

    return CommandResult.failure("Error: Failed to rename method");
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
        saveState(new String[] {});
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
    saveState(new String[] {});
    return CommandResult.success("Added relationship to " + args[1] + " type of " + args[0]);
  }

  public CommandResult removeRelationship(String[] args) {
    if (args.length != 2) {
      return CommandResult.failure("remove relationship <relationship type> <target class name>");
    }

    for (UMLClassNode.Relationship relationship : currentClass.getRelationships()) {
      if (relationship.getTarget().equals(args[1]) || relationship.getType().equals(args[0])) {
        currentClass.getRelationships().remove(relationship);
        saveState(new String[] {});
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

  public CommandResult save(String[] args) {
    if (args.length != 0) {
      return CommandResult.failure("Syntax: save");
    }

    try {
      // Get the JAR's directory and save to temp_save.json
      String jarLocation = new File(System.getProperty("user.dir")).getAbsolutePath();
      String filePath = jarLocation + File.separator + "temp-save.json";

      // Save the DATA_STORAGE data to JSON
      ClassNodeService classNodeService = new ClassNodeService();
      classNodeService.StorageSaveToJsonArray(STORAGE, filePath);

      return CommandResult.success("Saved to " + filePath);
    } catch (Exception e) {
      return CommandResult.failure("Error saving file: " + e.getMessage());
    }
  }

  public CommandResult saveAs(String[] args) {
    if (args.length != 1) {
      return CommandResult.failure("Syntax: save as <fileName>");
    }

    try {
      // Get the JAR's directory and construct the file path
      String jarLocation = new File(System.getProperty("user.dir")).getAbsolutePath();
      String filename = args[0];
      if (!filename.endsWith(".json")) {
        filename += ".json";
      }
      String filePath = jarLocation + File.separator + filename;

      // Save the DATA_STORAGE data to the specified file
      ClassNodeService classNodeService = new ClassNodeService();
      classNodeService.StorageSaveToJsonArray(STORAGE, filePath);

      return CommandResult.success("Saved to " + filePath);
    } catch (Exception e) {
      return CommandResult.failure("Error saving file: " + e.getMessage());
    }
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
      STORAGE.setAllNodes(memento.getState());
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
      STORAGE.setAllNodes(memento.getState());
      updateCurrentClass();
      return CommandResult.success("Redone");
    }
    return CommandResult.failure("Error: Nothing to redo");
  }

  private void updateCurrentClass() {
    if (currentClass != null) {
      currentClass = STORAGE.getNode(currentClass.getClassName());
    } else if (!STORAGE.getStorage().isEmpty()) {
      currentClass = STORAGE.getAllNodes().values().iterator().next();
    }
  }

  public CommandResult help(String[] args) {
    // Check if extra arguments are passed
    if (args.length != 0) {
      return CommandResult.failure("Usage: help");
    }

    // Ensure CommandRegistries is initialized with the correct file path
    CommandRegistries commandRegistries = CommandRegistries.getInstance("CLICommands.json");

    // Get all available commands and their syntax
    StringBuilder helpMessage = new StringBuilder("Available Commands:\n\n");
    Map<String, CommandInfo> commands = commandRegistries.getAllCommands();

    for (Map.Entry<String, CommandInfo> entry : commands.entrySet()) {
      String commandName = entry.getKey();
      CommandInfo commandInfo = entry.getValue();
      helpMessage
          .append("- ")
          .append(commandName)
          .append(": ")
          .append(commandInfo.description())
          .append("\n  Syntax: ")
          .append(commandInfo.syntax())
          .append("\n\n");
    }

    // Return the formatted help message
    return CommandResult.success(helpMessage.toString());
  }

  public CommandResult exit(String[] args) {
    // Validate that no arguments are provided
    if (args.length != 0) {
      return CommandResult.failure("No arguments needed for the 'exit' command.");
    }

    try {
      // Perform any necessary cleanup tasks here
      // For example: saving the current state, closing resources, etc.
      saveState(new String[] {}); // Save the current application state
      System.out.println("Saving current state...");

      // Notify the user about the program termination
      System.out.println("Exiting the UML Editor...");

      // Gracefully shut down the application
      System.exit(0);

      return CommandResult.success("Program exited successfully.");
    } catch (Exception e) {
      // Handle any errors that occur during the shutdown process
      return CommandResult.failure("Error occurred while exiting: " + e.getMessage());
    }
  }

  public CommandResult newProject(String[] args) {
    if (args.length != 0) {
      return CommandResult.failure("Error: 'new project' does not take any arguments.");
    }

    // Reset the application to its initial state
    DataStorage.resetInstance(); // Clear and reset the DATA_STORAGE
    DataStorage.getInstance(); // Reinitialize the DATA_STORAGE singleton

    saveState(new String[] {}); // Save the cleared state

    currentClass = null; // Reset the current class
    loadedfileName = ""; // Clear the loaded file name

    return CommandResult.success("New project initialized. All previous data has been cleared.");
  }

  public CommandResult clear(String[] args) {
    if (args.length != 0) {
      return CommandResult.failure("No arguments needed for the 'clear' command.");
    }

    return CommandResult.success("Terminal cleared.");
  }

  public CommandResult loadFile(String[] args) {
    if (args.length != 1) {
      return CommandResult.failure("Syntax: load file <fileName>");
    }

    try {
      // Get the directory where the JAR file is located
      String jarLocation = new File(System.getProperty("user.dir")).getAbsolutePath();

      // Construct the file path based on the JAR's location
      String filename = args[0];
      if (!filename.endsWith(".json")) {
        filename += ".json";
      }
      Path filePath = Path.of(jarLocation, filename);

      // Debugging: Log the resolved file path
      System.out.println("Resolved file path: " + filePath.toAbsolutePath());

      // Validate that the file exists
      if (!Files.exists(filePath)) {
        return CommandResult.failure("File not found: " + filePath.toAbsolutePath());
      }

      // Read the JSON file content
      String jsonContent = Files.readString(filePath);

      // Debugging: Log the file content
      System.out.println("File content: " + jsonContent);

      // Parse the JSON content into DATA_STORAGE
      Gson gson = new Gson();
      JsonArray jsonArray = gson.fromJson(jsonContent, JsonArray.class);

      ClassNodeService classNodeService = new ClassNodeService();
      for (JsonElement element : jsonArray) {
        if (element.isJsonObject()) {
          JsonObject jsonObject = element.getAsJsonObject();
          UMLClassNode classNode = classNodeService.createClassNodeFromJson(jsonObject);
          STORAGE.addNode(classNode.getClassName(), classNode);
        } else {
          // Handle non-JsonObject cases
          System.err.println("Skipping non-JsonObject element: " + element);
        }
      }

      // Store the loaded file name
      loadedfileName = filename;

      return CommandResult.success("Loaded from " + filePath.toAbsolutePath());

    } catch (IOException e) {
      return CommandResult.failure("Error reading file: " + e.getMessage());
    } catch (JsonSyntaxException e) {
      return CommandResult.failure("Error parsing JSON: " + e.getMessage());
    } catch (Exception e) {
      return CommandResult.failure("Unexpected error: " + e.getMessage());
    }
  }

  public CommandResult load(String[] args) {
    if (args.length != 0) {
      return CommandResult.failure("Syntax: load");
    }

    try {
      newProject(args);

      // Get the JAR's directory and locate the temp_save.json file
      String jarLocation = new File(System.getProperty("user.dir")).getAbsolutePath();
      String filePath = jarLocation + File.separator + "temp-save.json";

      // Read the JSON content
      String jsonContent = Files.readString(Path.of(filePath));
      loadedfileName = "temp-save.json";

      Gson gson = new Gson();
      JsonArray jsonArray = gson.fromJson(jsonContent, JsonArray.class);

      // Load the data into DATA_STORAGE
      ClassNodeService classNodeService = new ClassNodeService();
      for (JsonElement element : jsonArray) {
        JsonObject jsonObject = element.getAsJsonObject();
        UMLClassNode classNode = classNodeService.createClassNodeFromJson(jsonObject);
        STORAGE.addNode(classNode.getClassName(), classNode);
      }
      return CommandResult.success("Loaded from " + filePath);
    } catch (IOException e) {
      return CommandResult.failure("Error reading file: " + e.getMessage());
    } catch (JsonSyntaxException e) {
      return CommandResult.failure("Error parsing JSON: " + e.getMessage());
    }
  }

  public CommandResult imageExport(String[] args) {
    if (args.length != 1) {
      return CommandResult.failure("Syntax: image export <fileName>");
    }

    String fileName = args[0];
    if (!fileName.endsWith(".json")) {
      fileName += ".json";
    }

    try {
      // Create an instance of GraphicalUserInterfaceController
      GraphicalUserInterfaceController controller = new GraphicalUserInterfaceController();

      // Load the UML project into the controller
      controller.loadProjectWithoutGUI(fileName);

      // Export the image
      controller.exportImageNonInteractive(fileName.replace(".png", ""));

      return CommandResult.success("Image successfully exported to: " + fileName);
    } catch (IOException e) {
      return CommandResult.failure("Error exporting image: " + e.getMessage());
    } catch (Exception e) {
      return CommandResult.failure("Unexpected error: " + e.getMessage());
    }
  }
}
