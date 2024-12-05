package org.project.Controller;

import org.project.Model.CommandLogic;
import org.project.Model.CommandRegistries;
import org.project.Model.Storage;

public class CommandBridgeAdapter implements CommandBridge {

  private final CommandRegistries commandRegistries;

  public CommandBridgeAdapter(CommandLogic commandLogic) {
    if (commandLogic instanceof CommandRegistries) {
      this.commandRegistries = (CommandRegistries) commandLogic;
    } else {
      throw new IllegalArgumentException("CommandLogic must be an instance of CommandRegistries");
    }
  }

  @Override
  public CommandResult executeCommand(String command, String[] args) {
    return commandRegistries.executeCommand(command, args);
  }

  @Override
  public CommandResult undo() {
    return commandRegistries.undo(new String[] {});
  }

  @Override
  public CommandResult redo() {
    return commandRegistries.redo(new String[] {});
  }

  @Override
  public CommandResult saveState() {
    return commandRegistries.saveState(new String[] {});
  }

  @Override
  public CommandResult createClass(String[] args) {
    return commandRegistries.createClass(args);
  }

  @Override
  public CommandResult addField(String[] args) {
    return commandRegistries.addField(args);
  }

  @Override
  public CommandResult removeClass(String[] args) {
    return commandRegistries.removeClass(args);
  }

  @Override
  public CommandResult renameClass(String[] args) {
    return commandRegistries.renameClass(args);
  }

  @Override
  public CommandResult switchClass(String[] args) {
    return commandRegistries.switchClass(args);
  }

  @Override
  public CommandResult listClasses(String[] args) {
    return commandRegistries.listClasses(args);
  }

  @Override
  public CommandResult removeField(String[] args) {
    return commandRegistries.removeField(args);
  }

  @Override
  public CommandResult renameField(String[] args) {
    return commandRegistries.renameField(args);
  }

  @Override
  public CommandResult addMethod(String[] args) {
    return commandRegistries.addMethod(args);
  }

  @Override
  public CommandResult removeMethod(String[] args) {
    return commandRegistries.removeMethod(args);
  }

  @Override
  public CommandResult renameMethod(String[] args) {
    return commandRegistries.renameMethod(args);
  }

  @Override
  public CommandResult addParameters(String[] args) {
    return commandRegistries.addParameters(args);
  }

  @Override
  public CommandResult removeParameters(String[] args) {
    return commandRegistries.removeParameters(args);
  }

  @Override
  public CommandResult renameParameters(String[] args) {
    return commandRegistries.renameParameters(args);
  }

  @Override
  public CommandResult addRelationship(String[] args) {
    return commandRegistries.addRelationship(args);
  }

  @Override
  public CommandResult removeRelationship(String[] args) {
    return commandRegistries.removeRelationship(args);
  }

  @Override
  public CommandResult listDetail(String[] args) {
    return commandRegistries.listDetail(args);
  }

  @Override
  public CommandResult saveNewfile(String[] args) {
    return commandRegistries.saveAs(args);
  }

  @Override
  public CommandResult save(String[] args) {
    return commandRegistries.save(args);
  }

  @Override
  public CommandResult load(String[] args) {
    return commandRegistries.load(args);
  }

  @Override
  public CommandResult help(String[] args) {
    return commandRegistries.help(args);
  }

  @Override
  public CommandResult exit(String[] args) {
    return commandRegistries.exit(args);
  }

  public Storage getStorage() {
    return CommandLogic.getStorage();
  }
}
