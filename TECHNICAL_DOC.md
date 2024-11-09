# UML Editor - CLI Technical Documentation

---
## Table of Contents

1. [CLI Introduction](#cli-introduction)
2. [ClI Overview](#cli-overview)
2. [CLI Usage](#cli-usage)
   * [General Commands](#general-commands)
   * [Add Commands](#add-commands)
   * [Remove Commands](#remove-commands)
   * [Rename Commands](#rename-commands)
3. [Sample Workflow](#sample-workflow)
4. [Known Bugs](#known-bugs)
---
## CLI Introduction
Brief description ...


---
## CLI Overview
A breakdown of the CLI commands and their functionalities.

## CLI Usage
Upon booting up in CLI mode, you'll be presented with the terminal. You should see a line saying

`Welcome to our UML Editor application
If you would like a list of commands enter 'help'`

From here you'll be able to type in commands and start editing. Down below are all the commands you'll be able to type into the command line.

---
## General Commands
* Please make sure to read CLI Usage before reading this section.
* These commands are general commands that can be used in the CLI.

1. set class `<class name>` - sets the class to edit. A class needs to be set to edit its internals such as fields, methods, and parameters.
   * Example: `set class Apple`

2. help - displays all possible commands

3. help `<command>` - displays expanded commands for the given command.
   * Example: `help add class`

4. list class - Lists everything in the currently selected class

5. list classes - List all created classes
   * Example: `list classes`

6. save - saves the current project as one json. It will ask you to input a name after typing in the command.
    * Example: `save` -> `Enter file name: MyProject`

7. load - loads a json as a UML class. It will ask you to input a name after typing load
    * Example: `load` -> `Enter file name: MyProject`
   
8. exit - exits the program
---
## Add Commands
* Please make sure to read CLI Usage before reading this section.
* These commands add things to the editor.

1. add class `<name>` - creates a class with that name
   * Example: `add class Apple`

2. add method `<name>` - creates a method with the given name
    * Example: `add method eat`

3. add field `<name> <type>` - creates a field with the given name and type. You will need to type in a type otherwise it won't work.
    * Example: `add field color String`

4. add parameter `<existing method> <name> <type>` - creates a parameter with the given name and type. This requires a method to exist in order to work
    * Example: `add parameter eat food String`

5. add relationship `<existing class name> <different existing class name>` - creates a relationship between two existing classes.
    * Example: `add relationship Apple Banana`
---
## Remove Commands
* Please make sure to read CLI Usage before reading this section.
* These commands remove things in the editor.

1. remove class - removes the class that is currently set
These commands remove things in the editor.

2. remove method `<name>` - removes a method with the given name
   * Example: `remove method method1`

3. remove field `<name>` - removes a field with the given name
    * Example: `remove field field1`

4. remove parameter `<existing method> <name>` - removes a parameter with the given name
    * Example: `remove parameter method1 param1`

5. remove relationship `<existing class name> <different existing class name>` -  removes a relationship between two existing classes.
    * Example: `remove relationship Apple Banana`
---
## Rename Commands
* Please make sure to read CLI Usage before reading this section.
* These commands rename things in the editor.

1. rename class `<old name> <new name>` - renames the currently selected class to the new name
   * Example: `rename class Apple Banana`

2. rename method `<existing method> <new name>` - renames a method in currently selected class to the new name
   * Example: `rename method method1 method2`

3. rename field `<existing field> <new name> <new type>` - renames a method in currently selected class to the new name
   * Example: `rename field field1 field2 String`

4. rename parameter `<existing method> <existing parameter> <new name> <new type>` - renames a parameter in currently selected class to the new name
   * Example: `rename parameter method1 param1 param2 String`
---

## Known Bugs
1. Save / load does not work at the moment
2. Add field (format) should be `add field <type> <name>` not `add field <name> <type>`

---
## Sample Workflow
Provide a simple walkthrough of how to use the CLI to create a UML diagram.


---
// DELETE THIS if you don't need it.

As a primer, typing '_help_' into the menu will bring up all the possible commands you can type into the command line, and typing 'help' with one of those commands will bring up an expanded explanation to all of the associated commands.

Once the program starts, the first thing you need to add is a class.
Type 'add class ' and the name of the class you wish to add to create a class into the program. (ex: 'add class _apple_') and press the enter key.

You can add as many classes as you want, but you can't edit anything in the classes themselves. For example, if you try to add a method, you'll get a prompt telling you

`no class selected. Try: set class <class name>
`
<br> <br>
You can't edit anything in a class until you set the class you want to edit. To set a class, type in 'set class ' and the name of an existing class you created (in this walkthrough: 'set class _apple_')
Once you do that, you'll see a confirmation text saying the class was set, and from here you can edit the class. You can use the rest of the add, remove, and rename commands.

To test that out, type 'add method ' and the name of the method you want to add. (ex: 'add method _banana_')
You can test out the other add commands, but keep in mind that adding a field, relationship, and parameter requires two, two and three inputs respectively in order to work (Detailed explanation in CLI Commands)

If you wish to edit another class, you'll need to type 'set class' with the name of another existing class that is created.

To see what you've added to the currently set class, type 'list class' to see how your class is set up.
What should display is everything added to the currently selected class.
If you want to see all the classes you created, type 'list classes' and it'll display all currently created classes.

Since you have something in the UML editor, to save the program, type 'save' to save everything you have into a json file.
The program should prompt text asking to type a name for the file. Name the file whatever you want and press the enter key and you should see a json file with that name in the project folder.

If you want to load the file, type 'load'. This will prompt text asking for a file name in the project folder. The program will then prompt you to type in the name of a json file.
As a heads-up, you don't need to include ".json" in your input, the program looks for a json file with the name of what you input.
Assuming you typed in the name of the file, all your classes and every attribute tied to the classes should be loaded. (although if you're following along, it might be hard to notice considering you presumably didn't add anything after saving)

If you wish to see it work, you can restart the program (typing 'exit' closes the program) and on bootup, type in the load command along with the name of the json you saved, and type 'list classes' to see all the classes you saved.

This should give you the idea of how the UML editor works. If you need a reminder or further explanation for the commands, type 'help' for all the commands, and help and a command name after (ex: 'help add') to get a detailed explanation to all the commands.


# GUI Instructions

The UML Editor application also provides a Graphical User Interface (GUI) for users who prefer a visual editing experience. Below are instructions for navigating and using the GUI.

## Getting Started with the GUI

Requirements:
- Java 17 or higher
- JVM

1. **Launching the Application in GUI Mode**:
    - Start the UML Editor in GUI mode by double clicking the JAR file. You should see a blank canvas area on the left, where classes will appear, and a control panel on the right.

2. **Adding a Class**:
    - To add a new class, click the **Add Class** button on the top right of the control panel. A new class box will appear on the canvas.
    - Type the desired class name in the **Class Name** field and click **Set Class Name** to assign it.

3. **Editing Class Properties**:
    - **Adding Fields**: Enter a field name in the **Field Name** input box, select a data type from the dropdown, and click **Add**. The field will be added to the selected class.
    - **Adding Methods**: Type the method name in the **Method Name** box and click **Add**. The method will appear within the selected class box.
    - **Adding Parameters**: To add parameters to a method, select the method first. Then, specify the parameter name and type in the **Add Parameter** section and click **Add**.

4. **Renaming and Deleting Fields or Methods**:
    - **Rename Field/Method**: Select the field or method, modify the name in the input box, and click **Rename Field** or **Rename Method**.
    - **Delete Field/Method**: Select the field or method and click **Delete Field** or **Delete Method** to remove it.

5. **Managing Relationships**:
    - **Add Relationship**: Select the classes involved from the **From** and **To** dropdowns, then choose a relationship type (e.g., Aggregation, Composition, Generalization, Realization) and click **Add Relation**.
    - **Delete Relationship**: To remove a 