package org.project.Controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class CommandParser {

  private JsonObject commandsJson;
  private String currentClass = null;
  private final Set<String> classes = new HashSet<>();  // Track created classes
  private final Map<String, Map<String, Runnable>> commandGroups = new HashMap<>();  // Command groups

  public CommandParser(String jsonFilePath) {
    try {
      FileReader reader = new FileReader(jsonFilePath);
      commandsJson = JsonParser.parseReader(reader).getAsJsonObject();
      reader.close();
      initializeCommands();
    } catch (IOException e) {
      System.out.println("Error: Could not load CLICommands.json.");
      e.printStackTrace();
    }
  }

  private void initializeCommands() {
    // Initialize each command group and map commands to their Runnables
    Map<String, Runnable> classCommands = new HashMap<>();
    classCommands.put("create", () -> handleCreateClass());
    classCommands.put("remove", () -> handleRemoveClass());
    commandGroups.put("classCommands", classCommands);

    // Add additional command groups here if needed
  }

  public void parseCommand(String input) {
    String[] tokens = input.split(" ");
    if (tokens.length < 2) {
      System.out.println("Error: Incomplete command.");
      return;
    }

    String commandGroup = determineCommandGroup(tokens[0]);
    if (commandGroup != null && commandGroups.containsKey(commandGroup)) {
      Map<String, Runnable> commands = commandGroups.get(commandGroup);
      if (commands.containsKey(tokens[1])) {
        commands.get(tokens[1]).run();
      } else {
        System.out.println("Error: Invalid command.");
      }
    } else {
      System.out.println("Error: Unrecognized command group.");
    }
  }

  private String determineCommandGroup(String command) {
    return command.equals("create") || command.equals("remove") ? "classCommands" : null;
  }

  private void handleCreateClass() {
    JsonObject errors = commandsJson.getAsJsonObject("commands")
      .getAsJsonObject("classCommands")
      .getAsJsonObject("create")
      .getAsJsonObject("errors");
    String className = getClassName();

    if (className == null) {
      printSyntaxError("create class <classname>");
      return;
    }

    if (classes.contains(className)) {
      printError(errors.getAsJsonArray("classExists"));
    } else if (!isValidName(className)) {
      printError(errors.getAsJsonArray("invalidName"));
    } else {
      classes.add(className);
      currentClass = className;
      System.out.println("Class " + className + " created and set as current.");
    }
  }

  private void handleRemoveClass() {
    JsonObject errors = commandsJson.getAsJsonObject("commands")
      .getAsJsonObject("classCommands")
      .getAsJsonObject("remove")
      .getAsJsonObject("errors");
    String className = getClassName();

    if (className == null) {
      printSyntaxError("remove class <classname>");
      return;
    }

    if (!classes.contains(className)) {
      printError(errors.getAsJsonArray("classNotFound"));
    } else if (className.equals(currentClass)) {
      printError(errors.getAsJsonArray("classInUse"));
    } else {
      classes.remove(className);
      System.out.println("Class " + className + " removed.");
    }
  }

  private String getClassName() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter class name: ");
    String input = scanner.nextLine();
    String[] tokens = input.split(" ");
    return tokens.length > 2 ? tokens[2] : null;
  }

  private boolean isValidName(String name) {
    return name.matches("[A-Za-z_][A-Za-z0-9_]*");
  }

  private void printSyntaxError(String syntax) {
    System.out.println("Error: Incorrect syntax. Usage: " + syntax);
  }

  private void printError(JsonArray errors) {
    errors.forEach(error -> System.out.println(error.getAsString()));
  }

  public String getCurrentClass() {
    return currentClass;
  }

  public boolean classExists(String className) {
    return classes.contains(className);
  }
}
