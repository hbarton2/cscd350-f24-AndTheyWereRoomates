package org.project.Model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {
  private final JsonObject commandsJson;
  private final Map<String, CommandInfo> commandMap = new HashMap<>();
  private final Map<String, Runnable> commandActions = new HashMap<>();

  public CommandRegistry(String jsonFilePath) {
    this.commandsJson = loadCommands(jsonFilePath);
    if (commandsJson != null) {
      initializeCommands();
      initializeCommandActions();  // Link commands to their logic
    }
  }

  private JsonObject loadCommands(String jsonFilePath) {
    try (FileReader reader = new FileReader(jsonFilePath)) {
      return JsonParser.parseReader(reader).getAsJsonObject();
    } catch (IOException e) {
      System.out.println("Error: Could not load CLICommands.json.");
      e.printStackTrace();
      return null;
    }
  }

  private void initializeCommands() {
    JsonObject commandGroups = commandsJson.getAsJsonObject("commands");
    for (String group : commandGroups.keySet()) {
      JsonObject commands = commandGroups.getAsJsonObject(group);
      for (String command : commands.keySet()) {
        JsonObject commandDetails = commands.getAsJsonObject(command);
        String syntax = commandDetails.has("syntax") ? commandDetails.get("syntax").getAsString() : "[Not Provided]";
        String description = commandDetails.has("description") ? commandDetails.get("description").getAsString() : "[Not Provided]";
        CommandInfo commandInfo = new CommandInfo(group, command, syntax, description);
        commandMap.put(command, commandInfo);
      }
    }
    System.out.println("Commands loaded successfully into CommandRegistry.");
  }

  private void initializeCommandActions() {
    commandActions.put("create", this::createClass);
    commandActions.put("remove", this::removeClass);
    // Add more command-to-method mappings as needed
  }

  public void executeCommand(String commandName, String[] args) {
    if (commandActions.containsKey(commandName)) {
      commandActions.get(commandName).run();
    } else {
      System.out.println("Error: Command '" + commandName + "' not found.");
    }
  }

  public CommandInfo getCommandInfo(String commandName) {
    return commandMap.get(commandName);
  }

  private void createClass() {
    System.out.println("Executing createClass logic...");
    // Your class creation logic here
  }

  private void removeClass() {
    System.out.println("Executing removeClass logic...");
    // Your class removal logic here
  }

  public record CommandInfo(String group, String name, String syntax, String description) {

    @Override
      public String toString() {
        return "Command: " + name + "\nGroup: " + group + "\nSyntax: " + syntax + "\nDescription: "
          + description;
      }
    }
}
