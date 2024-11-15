package org.project.Model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.project.Controller.CommandResult;

public class CommandRegistries {

  private static final Logger logger = Logger.getLogger(CommandRegistries.class.getName());
  // A map to store command information including syntax and description
  private final Map<String, CommandInfo> commandMap = new HashMap<>();

  // Constructor with optional custom file path for flexibility
  public CommandRegistries(String jsonFilePath) {
    loadCommands(jsonFilePath);  // Load commands from JSON file
    initializeCommandHandlers();  // Initialize command handler methods
  }

  // Method to load commands from a specified JSON file
  private void loadCommands(String jsonFilePath) {
    try (FileReader reader = new FileReader(jsonFilePath)) {
      JsonObject commandsJson = JsonParser.parseReader(reader).getAsJsonObject();
      JsonObject commandGroups = commandsJson.getAsJsonObject("commands");

      for (String group : commandGroups.keySet()) {
        JsonObject commands = commandGroups.getAsJsonObject(group);
        for (String commandName : commands.keySet()) {
          JsonObject commandDetails = commands.getAsJsonObject(commandName);
          String syntax = commandDetails.has("syntax") ? commandDetails.get("syntax").getAsString() : "";
          String description = commandDetails.has("description") ? commandDetails.get("description").getAsString() : "";
          commandMap.put(commandName, new CommandInfo(syntax, description));
        }
      }
      logger.info("Commands loaded successfully from " + jsonFilePath);
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Error loading commands from JSON file: " + jsonFilePath, e);
    }
  }

  // Return a map of all commands for listing purposes
  public Map<String, CommandInfo> getAllCommands() {
    return commandMap;
  }

  // Functional interface for command handlers
  @FunctionalInterface
  interface CommandHandler {
    CommandResult handle(String[] args);
  }

  /**
   * Future nested keys ie quit and exit does same job
   */
  private final Map<String, CommandHandler> commandHandlers = new HashMap<>();

  // Initialize command handlers with specific implementations
  private void initializeCommandHandlers() {
    commandHandlers.put("switch", this::switchClass);
    commandHandlers.put("list classes", this::listClasses);
    commandHandlers.put("list detail", this::listDetail);
    commandHandlers.put("create class", this::createClass); // TODO: Logic
    commandHandlers.put("remove class", this::removeClass);
    commandHandlers.put("rename class", this::renameClass);
    commandHandlers.put("add field", this::addField);
    commandHandlers.put("remove field", this::removeField);
    commandHandlers.put("rename field", this::renameField);
    commandHandlers.put("add method", this::addMethod);
    commandHandlers.put("remove method", this::removeMethod);
    commandHandlers.put("rename method", this::renameMethod);
    commandHandlers.put("add parameter", this::addParameters);
    commandHandlers.put("remove parameter", this::removeParameters);
    commandHandlers.put("rename parameter", this::renameParameters);
    commandHandlers.put("add relationship", this::addRelationship);
    commandHandlers.put("remove relationship", this::removeRelationship);
    commandHandlers.put("save", this::save);
    commandHandlers.put("load", this::load);
    commandHandlers.put("undo", this::undo);
    commandHandlers.put("redo", this::redo);
    commandHandlers.put("help", this::help);
    commandHandlers.put("exit", this::exit);
    // TODO: Add more command handlers as needed...
  }

  // Execute a command by finding and running the appropriate handler
  public CommandResult executeCommand(String command, String[] args) {
    CommandHandler handler = commandHandlers.get(command);
    if (handler == null) {
      return CommandResult.failure("Command not found: " + command);
    }
    return handler.handle(args);
  }

  // Example command handler methods
//  private CommandResult createClass(String[] args) {// TODO: should not be able to duplicate same class
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
  private final Set<String> createdClasses = new HashSet<>();

  // Method to check if a class exists
  public boolean classExists(String className) {
    return createdClasses.contains(className);
  }

  // Add a class to the set
  public void addClass(String className) {
    createdClasses.add(className);
  }

  // Remove a class from the set
  public void removeClass(String className) {
    createdClasses.remove(className);
  }

  // Example handler for 'create class' with validation
  private CommandResult createClass(String[] args) {
    if (args.length != 1) {
      return CommandResult.failure("Usage: create class <class name>");
    }

    String className = args[0];
    if (className.isBlank()) {
      return CommandResult.failure("Error: Class name cannot be empty.");
    }

    if (!className.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
      return CommandResult.failure("Error: Invalid class name. Use letters, numbers, and underscores only.");
    }

    if (classExists(className)) {
      return CommandResult.failure("Error: Class '" + className + "' already exists.");
    }

    // Add class to the registry
    addClass(className);
    return CommandResult.success("Class created: " + className);
  }
  // TODO: Need a class where the logic and storage device

  private CommandResult addField(String[] args) {
    if (args.length < 2) {
      return CommandResult.failure("add field <field type> <field name>");
    }
    String fieldType = args[0];
    String fieldName = args[1];
    return CommandResult.success("Field added: " + fieldType + " " + fieldName);
  }

  private CommandResult removeClass(String[] args) {
    if (args.length < 1) {
      return CommandResult.failure("remove class <classname>");
    }else if (args.length > 1){
      return CommandResult.failure("remove class <classname>");
    }
    String className = args[0];
    return CommandResult.success("Class deleted: " + className);
  }

  private CommandResult renameClass(String[] args) {
    if (args.length < 2) {
      return CommandResult.failure("rename class <existing classname> <new classname>");
    }else if (args.length > 2){
      return CommandResult.failure("rename class <existing classname> <new classname>");
    }

    String oldClassName = args[0];
    String newClassName = args[1];
    return CommandResult.success("Class renamed: " + oldClassName + " to " + newClassName);
  }

  private CommandResult switchClass (String[] args){
    if (args.length < 1 ){
      return CommandResult.failure("switch <existing classname>");
    }else if(args.length > 1){
      return CommandResult.failure("switch <existing classname>");
    }

    String className = args[0];
    return CommandResult.success("Class Switched to: " + className);


  }

  private CommandResult listClasses(String[] args){
    if (args.length > 0){
      return CommandResult.failure("Syntax: list classes");
    }

    return CommandResult.success("Listing Classes...");
  }

  private CommandResult removeField (String[] args){
    if(args.length < 1){
      return CommandResult.failure("remove field <field name>");
    } else if (args.length > 1){
      return CommandResult.failure("remove field <field name>");
    }

    String fieldName = args[0];
    return CommandResult.success("Field removed: " + fieldName);
  }

  private CommandResult renameField (String[] args){
    if(args.length < 2){
      return CommandResult.failure("rename field <existing field name> <new field name>");
    }else if(args.length > 2){
      return CommandResult.failure("rename field <existing field name> <new field name>");
    }

    String fieldName = args[0];
    String newFieldName = args[1];
    return CommandResult.success("Field: " + fieldName + " to " + newFieldName);
  }

  private CommandResult addMethod (String[] args){
    if (args.length < 2 || args.length % 2 != 0) {
      return CommandResult.failure("add method <return type> <method name> [<parameter type> <parameter name> ...]");
    }

    String methodName = args[1];

    return CommandResult.success("Method added: " + methodName);
  }


  private CommandResult removeMethod (String[] args){
    if (args.length < 2 || args.length % 2 != 0) {
      return CommandResult.failure("remove method <method name> [<parameter type> <parameter name>...]");
    }

    String methodName = args[1];
    return CommandResult.success("Method deleted: " + methodName);
  }

  private CommandResult renameMethod (String[] args){
    if (args.length < 3){
      return CommandResult.failure("rename method <existing method name> <new method name> <return type>");
    }else if (args.length > 3){
      return CommandResult.failure("rename method <existing method name> <new method name> <return type>");
    }

    String oldMethodName = args[0];
    String newMethodName = args[1];
    return CommandResult.success("Renames Method: " + oldMethodName + " to " + newMethodName);
  }

  private CommandResult addParameters (String[] args){
    if (args.length > 3){
      return CommandResult.failure("add parameter <method name> <parameter type> <parameter name> [<parameter type> <parameter name> ...]");
    }else if (args.length % 2 != 1){
      return CommandResult.failure("add parameter <method name> <parameter type> <parameter name> [<parameter type> <parameter name> ...]");
    }

    String methodName = args[0];
    return CommandResult.success("Parameters added to " + methodName);
  }


  private CommandResult removeParameters (String[] args){
    if(args.length == 0){
      return CommandResult.failure("remove parameter <method name> <parameter name> [<parameter name> ...]");
    }else if(args.length < 2){
      return CommandResult.success("remove parameter <method name> <parameter name> [<parameter name> ...]");
    }

    String methodName = args[0];
    return CommandResult.success("Removed parameters from " + methodName);
  }

  private CommandResult renameParameters(String[] args) {
    if (args.length < 3) {
      return CommandResult.failure("Usage: rename parameter <method name> <old parameter name> <new parameter name>");
    }
    String methodName = args[0];
    String oldParameterName = args[1];
    String newParameterName = args[2];

    return CommandResult.success("Ready to rename parameter from " + oldParameterName + " to " + newParameterName + " in method " + methodName);
  }

  private CommandResult addRelationship (String[] args){
    if (args.length == 0){
      return CommandResult.failure("add relationship <relationship type> <target class name>");
    }else if(args.length < 2){
      return CommandResult.failure("add relationship <relationship type> <target class name>");
    }else if(args.length > 2){
      return CommandResult.failure("Too many arguments");
    }

    String relationType = args[0];
    String targetClass = args[1];
    return CommandResult.success("Added relationship to " + targetClass + " type of " + relationType);
  }

  private CommandResult removeRelationship (String[] args){
    if(args.length == 0){
      return CommandResult.failure("remove relationship <relationship type> <target class name>");
    }else if(args.length < 2){
      return CommandResult.failure("remove relationship <relationship type> <target class name>");
    }else if(args.length > 2){
      return CommandResult.failure("remove relationship <relationship type> <target class name>");
    }
    String relationType = args[0];
    String targetClass = args[0];
    return CommandResult.success("Deleted relationship : " + targetClass + "Type: " + relationType);
  }

  private CommandResult listDetail (String[] args){
    if(args.length != 0){
      return CommandResult.failure("list detail ");
    }

    return CommandResult.success("Listing Detail...");
  }

  private CommandResult save (String[] args){
    if(args.length == 0 ){
      return CommandResult.failure("Specify file name");
    }else if(args.length > 1){
      return CommandResult.failure("Too many arguments");
    }

    String fileName = args[0];
    return CommandResult.success("Saved" + fileName);
  }

  private CommandResult load (String[] args){
    if(args.length == 0){
      return CommandResult.failure("Specify fileName");
    }else if(args.length > 1){
      return CommandResult.failure("Too many arguments");
    }
    return CommandResult.success("Loaded!");
  }

  private CommandResult undo (String[] args){
    if(args.length != 0){
      return CommandResult.failure("No arguments needed");
    }
    return CommandResult.success("Undone");
  }

  private CommandResult redo (String[] args){
    if(args.length != 0){
      return CommandResult.failure("No arguments needed");
    }
    return CommandResult.success("Undone");
  }

  private CommandResult help (String[] args){
    if(args.length != 0){
      return CommandResult.failure("No arguments needed");
    }
    return CommandResult.success("Help Menu: ");
  }

  private CommandResult exit (String[] args){
    if(args.length != 0){
      return CommandResult.failure("No arguments needed");
    }
    return CommandResult.success("Exiting Program");
  }

  // TODO: Additional command handler methods as needed...
}
