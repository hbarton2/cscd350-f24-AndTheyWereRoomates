package org.project.Controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;

public class CommandParser {

  private JsonObject commandsJson;

  public CommandParser(String jsonFilePath) {
    loadCommands(jsonFilePath);
  }

  private void loadCommands(String jsonFilePath) {
    try (FileReader reader = new FileReader(jsonFilePath)) {
      commandsJson = JsonParser.parseReader(reader).getAsJsonObject();
      System.out.println("Loaded commands structure:");
      printCommandsStructure();
    } catch (IOException e) {
      System.out.println("Error: Could not load CLICommands.json.");
      e.printStackTrace();
    }
  }

  public void printCommandsStructure() {
    if (commandsJson == null) {
      System.out.println("Commands JSON is null.");
      return;
    }
    JsonObject commandGroups = commandsJson.getAsJsonObject("commands");
    for (String group : commandGroups.keySet()) {
      System.out.println("Command Group: " + group);
      JsonObject commands = commandGroups.getAsJsonObject(group);
      for (String command : commands.keySet()) {
        System.out.println("  Command: " + command);
        JsonObject commandDetails = commands.getAsJsonObject(command);

        // Check for the existence of each field before attempting to access it
        if (commandDetails.has("syntax")) {
          System.out.println("    Syntax: " + commandDetails.get("syntax").getAsString());
        } else {
          System.out.println("    Syntax: [Not Provided]");
        }

        if (commandDetails.has("description")) {
          System.out.println("    Description: " + commandDetails.get("description").getAsString());
        } else {
          System.out.println("    Description: [Not Provided]");
        }
      }
    }
  }

}
