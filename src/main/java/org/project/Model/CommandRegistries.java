package org.project.Model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.project.Controller.CommandResult;

public class CommandRegistries extends CommandLogic {

  private static final Logger logger = Logger.getLogger(CommandRegistries.class.getName());
  // A map to store command information including syntax and description
  private final Map<String, CommandInfo> commandMap = new HashMap<>();

  // Constructor with optional custom file path for flexibility
  public CommandRegistries(String jsonFilePath) {
    loadCommands(jsonFilePath); // Load commands from JSON file
    initializeCommandHandlers(); // Initialize command handler methods
    CommandLogic commandLogic = new CommandLogic();
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
          String syntax =
              commandDetails.has("syntax") ? commandDetails.get("syntax").getAsString() : "";
          String description =
              commandDetails.has("description")
                  ? commandDetails.get("description").getAsString()
                  : "";
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

  /** Future nested keys ie quit and exit does same job */
  private final Map<String, CommandHandler> commandHandlers = new HashMap<>();

  // Initialize command handlers with specific implementations
  private void initializeCommandHandlers() {
    commandHandlers.put("switch", super::switchClass);
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
    commandHandlers.put("save as", this::saveNewfile);
    commandHandlers.put("save", this::save);
    commandHandlers.put("load file", this::load);
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


}
