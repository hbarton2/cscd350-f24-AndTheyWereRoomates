package org.project.Controller;

import org.project.Model.CommandLogic;
import org.project.Model.CommandRegistries;
import org.project.Model.DataStorage;

/**
 * Adapter that bridges {@code CommandLogic} and {@code CommandBridge}. Delegates command execution
 * to {@code CommandRegistries}.
 */
public class CommandBridgeAdapter implements CommandBridge {

  private final CommandRegistries commandRegistries;

  /**
   * Creates an adapter for {@code CommandLogic} if it is an instance of {@code CommandRegistries}.
   *
   * @param commandLogic the logic to be adapted
   * @throws IllegalArgumentException if {@code commandLogic} is not a {@code CommandRegistries}
   *     instance
   */
  public CommandBridgeAdapter(CommandLogic commandLogic) {
    if (commandLogic instanceof CommandRegistries) {
      this.commandRegistries = (CommandRegistries) commandLogic;
    } else {
      throw new IllegalArgumentException("CommandLogic must be an instance of CommandRegistries");
    }
  }

  /**
   * Executes a command.
   *
   * @param command the command name
   * @param args arguments for the command
   * @return result of the command execution
   */
  @Override
  public CommandResult executeCommand(String command, String[] args) {
    return commandRegistries.executeCommand(command, args);
  }

  /** Undoes the last command. */
  @Override
  public CommandResult undo() {
    return commandRegistries.undo(new String[] {});
  }

  /** Redoes the last undone command. */
  @Override
  public CommandResult redo() {
    return commandRegistries.redo(new String[] {});
  }

  /** Saves the current application state. */
  @Override
  public CommandResult saveState() {
    return commandRegistries.saveState(new String[] {});
  }

  /** Creates a new class. */
  @Override
  public CommandResult createClass(String[] args) {
    return commandRegistries.createClass(args);
  }

  /** Adds a field to a class. */
  @Override
  public CommandResult addField(String[] args) {
    return commandRegistries.addField(args);
  }

  /** Removes a class. */
  @Override
  public CommandResult removeClass(String[] args) {
    return commandRegistries.removeClass(args);
  }

  /** Renames a class. */
  @Override
  public CommandResult renameClass(String[] args) {
    return commandRegistries.renameClass(args);
  }

  /** Switches to a different class. */
  @Override
  public CommandResult switchClass(String[] args) {
    return commandRegistries.switchClass(args);
  }

  /** Lists all classes. */
  @Override
  public CommandResult listClasses(String[] args) {
    return commandRegistries.listClasses(args);
  }

  /** Removes a field from a class. */
  @Override
  public CommandResult removeField(String[] args) {
    return commandRegistries.removeField(args);
  }

  /** Renames a field in a class. */
  @Override
  public CommandResult renameField(String[] args) {
    return commandRegistries.renameField(args);
  }

  /** Adds a method to a class. */
  @Override
  public CommandResult addMethod(String[] args) {
    return commandRegistries.addMethod(args);
  }

  /** Removes a method from a class. */
  @Override
  public CommandResult removeMethod(String[] args) {
    return commandRegistries.removeMethod(args);
  }

  /** Renames a method in a class. */
  @Override
  public CommandResult renameMethod(String[] args) {
    return commandRegistries.renameMethod(args);
  }

  /** Adds parameters to a method. */
  @Override
  public CommandResult addParameters(String[] args) {
    return commandRegistries.addParameters(args);
  }

  /** Removes parameters from a method. */
  @Override
  public CommandResult removeParameters(String[] args) {
    return commandRegistries.removeParameters(args);
  }

  /** Renames parameters in a method. */
  @Override
  public CommandResult renameParameters(String[] args) {
    return commandRegistries.renameParameters(args);
  }

  /** Adds a relationship between classes. */
  @Override
  public CommandResult addRelationship(String[] args) {
    return commandRegistries.addRelationship(args);
  }

  /** Removes a relationship between classes. */
  @Override
  public CommandResult removeRelationship(String[] args) {
    return commandRegistries.removeRelationship(args);
  }

  /** Lists detailed information about a class or relationship. */
  @Override
  public CommandResult listDetail(String[] args) {
    return commandRegistries.listDetail(args);
  }

  /** Saves the current state to a new file. */
  @Override
  public CommandResult saveNewfile(String[] args) {
    return commandRegistries.saveAs(args);
  }

  /** Saves the current state to the default file. */
  @Override
  public CommandResult save(String[] args) {
    return commandRegistries.save(args);
  }

  /** Loads a state from a file. */
  @Override
  public CommandResult loadFile(String[] args) {
    return commandRegistries.loadFile(args);
  }

  /** Displays help information. */
  @Override
  public CommandResult help(String[] args) {
    return commandRegistries.help(args);
  }

  /** Exits the application. */
  @Override
  public CommandResult exit(String[] args) {
    return commandRegistries.exit(args);
  }

  /**
   * Retrieves the DATA_STORAGE used by {@code CommandLogic}.
   *
   * @return the DATA_STORAGE instance
   */
  public DataStorage getStorage() {
    return CommandLogic.getStorage();
  }
}
