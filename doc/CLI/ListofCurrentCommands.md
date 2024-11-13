# List of Available Commands

## Class Commands
- **`create class <classname>`**
    - Creates a new class and sets it as the current class for quick-editing.

- **`remove class <classname>`**
    - Removes an existing class.

- **`rename class <existing classname> <new classname>`**
    - Renames an existing class.

- **`switch <existing classname>`**
    - Sets an existing class as the current class for quick-editing.

- **`list classes`**
    - Lists the names of all currently created classes without additional details.

## Field Commands
- **`add field <fieldname> <fieldtype> [classname]`**
    - Adds a field to a specified or currently set class.

- **`remove field <fieldname> [classname]`**
    - Removes a field from a specified or currently set class.

- **`rename field <existing fieldname> <new fieldname> <fieldtype> [classname]`**
    - Renames a field in a specified or currently set class.

## Method Commands
- **`add method <methodname> <returntype> [classname]`**
    - Adds a method to a specified or currently set class.

- **`remove method <methodname> [classname]`**
    - Removes a method from a specified or currently set class.

- **`rename method <existing methodname> <new methodname> <returntype> [classname]`**
    - Renames a method in a specified or currently set class.

## Parameter Commands
- **`add parameter <parametername> <parametertype> <methodname> [classname]`**
    - Adds a parameter to a specified method in a class.

- **`remove parameter <parametername> <methodname> [classname]`**
    - Removes a parameter from a specified method in a class.

- **`rename parameter <oldparametername> <newparametername> <methodname> [classname]`**
    - Renames a parameter in a specified method in a class.

## Relationship Commands
- **`add relationship <relationshiptype> <targetclassname> [classname]`**
    - Adds a relationship between the current or specified class and a target class.

- **`remove relationship <relationshiptype> <targetclassname> [classname]`**
    - Removes a relationship between the current or specified class and a target class.

## Miscellaneous Commands
- **`save`**
    - Saves the current project to a JSON file.

- **`load`**
    - Loads a project from a JSON file.

- **`undo`**
    - Undoes the most recent action.

- **`redo`**
    - Redoes the most recent undone action.

- **`help`**
    - Displays a list of available commands or details for a specific command.

- **`exit`**
    - Terminates the application.
