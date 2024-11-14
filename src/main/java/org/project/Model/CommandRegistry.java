package org.project.Model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.project.Controller.CommandResult;

public class CommandRegistry {

  // A map to store command information including syntax and description
  private final Map<String, CommandInfo> commandMap = new HashMap<>();

  // Constructor with optional custom file path for flexibility
  public CommandRegistry(String jsonFilePath) {
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
      System.out.println("Commands loaded successfully from " + jsonFilePath);
    } catch (IOException e) {
      System.out.println("Error loading commands from JSON file.");
      e.printStackTrace();
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

  private final Map<String, CommandHandler> commandHandlers = new HashMap<>();

  // Initialize command handlers with specific implementations
  private void initializeCommandHandlers() {
    commandHandlers.put("create class", this::createClass);
    commandHandlers.put("add field", this::addField);
    // Add more command handlers as needed...
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
  private CommandResult createClass(String[] args) {
    if (args.length < 1) {
      return CommandResult.failure("Please specify a class name.");
    }
    String className = args[0];
    return CommandResult.success("Class created: " + className);
  }

  private CommandResult addField(String[] args) {
    if (args.length < 2) {
      return CommandResult.failure("Please specify a field type and field name.");
    }
    String fieldType = args[0];
    String fieldName = args[1];
    return CommandResult.success("Field added: " + fieldType + " " + fieldName);
  }

  // Additional command handler methods as needed...
}
