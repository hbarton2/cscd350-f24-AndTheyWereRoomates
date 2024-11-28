package org.project.Controller;

import org.jline.console.impl.Builtins;
import org.project.Model.Storage;

public interface CommandBridge {

  CommandResult executeCommand(String command, String[] args);

  CommandResult undo();

  CommandResult redo();

  CommandResult saveState();

  CommandResult createClass(String[] args);

  CommandResult addField(String[] args);

  CommandResult removeClass(String[] args);

  CommandResult renameClass(String[] args);

  CommandResult switchClass(String[] args);

  CommandResult listClasses(String[] args);

  CommandResult removeField(String[] args);

  CommandResult renameField(String[] args);

  CommandResult addMethod(String[] args);

  CommandResult removeMethod(String[] args);

  CommandResult renameMethod(String[] args);

  CommandResult addParameters(String[] args);

  CommandResult removeParameters(String[] args);

  CommandResult renameParameters(String[] args);

  CommandResult addRelationship(String[] args);

  CommandResult removeRelationship(String[] args);

  CommandResult listDetail(String[] args);

  CommandResult saveNewfile(String[] args);

  CommandResult save(String[] args);

  CommandResult load(String[] args);

  CommandResult help(String[] args);

  CommandResult exit(String[] args);

  Storage getStorage();
}
