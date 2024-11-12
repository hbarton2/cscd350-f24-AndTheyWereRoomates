
Most of this is just commands copied over from the base CLI commands
New ideas are the way SET CLASS works and ideas for changing how all classes are listed

*QUICK-SET COMMANDS
Any class that are created have their name loaded in the backend to be quickly edited without needing to type out
the name of the class.
Example: Instead of "add method <methodname> <classname>", it can just be "add method <methodname>" where you
add a method to the most recently created class or whatever is the currently set class

*CLASS COMMANDS
- "create class <classname>" - creates the class and sets the class to be quickly edited
  Creating class -> new class -> set_class = new class

- "remove class <existing classname>" - removes the class with the input name
- "rename class <existing classname> <new classname>" - renames an already existing class
- "switch <existing classname>" - sets a class to another class be quickly edited

- "list all class" - list all currently created classes without any details on what is in it, but will list what relationships are connected to it.
  EXAMPLE:

  Apple
  Banana
  Lemon
  Juice -> Apple
  Cup -> Juice
  Bowl -> Apple -> Banana
  (Juice has a relationship with Apple)
  (Cup has a relationship with Juice)
  (Bowl has relationships with Apple AND Banana)

I dont know if there is an intuitive way to display relationships like this. If this looks confusing or unintuitive,
keep it out and just leave in all the currently created classes.
EXAMPLE:

    Apple
    Banana
    Lemon
    Juice
    Bowl

Maybe as an algorithms implementation, Classes with more elements in it (IE: Fields, Relations, Methods, etc)
appear higher on the list than others with less elements.

- "list <classname>" - list everything currently created for a currently named class
  QUICK-SET COMMAND - "list" - list everything in the class that is currently set

  EXAMPLE:
  Class Name: Apple
  -
  Field:
  Calories - INT
  Nutrients - STRING
  Method:
  Seed()
  Stem(Leaf DOUBLE)
  Relationship:
  Aggregation -> Tree
  Encapsulation -> Juice

****FIELD COMMANDS****
*These inputs require a TYPE, type being primatives. IE: double, string, boolean, int,
- "add field <fieldname> <fieldtype> <classname>" - Adds a field with the name and type to the class with the attributing name
  QUICK-SET COMMAND - "add field <fieldname> <fieldtype>" -Adds a field with the name and type to the currently set class

- "remove field <fieldname> <classname>" - removes a field with the attributing name in the attributed class
  QUICK-SET COMMAND - "remove field <fieldname>" - removes the field in the currently set class

- "rename field <old fieldname> <fieldname> <fieldtype> <classname>" - renames a field with the attributing name in the attributed class
  QUICK-SET COMMAND - "rename field <old fieldname> <fieldname> <fieldtype>" - renames the field in the currently set class

****METHOD COMMANDS****
- "add method <methodname> <classname>" - Adds a field with the name and type to the class with the attributing name
  QUICK-SET COMMAND - "add method <methodname>" -Adds a field with the name and type to the currently set class

- "remove method <existing methodname> <classname>" - removes a field with the attributing name in the attributed class
  QUICK-SET COMMAND - "remove method <existing methodname>" - removes the field in the currently set class

- "rename method <existing methodname> <new methodname> <classname>" - renames a field with the attributing name in the attributed class
  QUICK-SET COMMAND - "rename method <existing methodname> <methodname>" - renames the field in the currently set class

****PARAMETER COMMANDS****
- "add parameter <parametername> <parametertype> <classname>" - Adds a field with the name and type to the class with the attributing name
  QUICK-SET COMMAND - "add field <parametername> <parametertype>" -Adds a field with the name and type to the currently set class

- "remove parameter <parametername> <classname>" - removes a field with the attributing name in the attributed class
  QUICK-SET COMMAND - "remove field <parametername>" - removes the field in the currently set class

- "rename parameter <existing parametername> <parametername> <parametertype> <classname>" - renames a field with the attributing name in the attributed class
  QUICK-SET COMMAND - "rename field <existing parametername> <parametername> <parametertype>" - renames the field in the currently set class

****RELATIONSHIP COMMANDS****
-"add relationship <existing classname> <existing classname> <Relationship type>" - Creates a relationship between two classes based on the given type
QUICK-SET COMMAND - "add relationship <existing classname> <Relationship type>" - Creates a relationship between the currently set class and another based on the given type

-"remove relationship <existing classname> <existing classname>" - Removes a relationship between two classes
QUICK-SET COMMAND - "remove relationship <existing classname>" - Removes a relationship between the currently set class and another

*MISCLANIOUS COMMANDS*
-"save" - Saves everything as one json file, the program will ask for a name for the json file
-"load" - Loads a json file into the UML editor, the program will ask for the name of a compatible json file
-"exit" - Closes the UML editor
-"undo" - Undoes the most recent action
-"redo" - Redoes the most recent undo
Undo and Redo could have Mementos structure in its design, saving each action as a state maybe
I heard this functionality can work as a Stack where most recent actions are placed into a Stack
and when undo is typed out, we save our current state into another stack, and pop the last action
and set the program back to the previous save. And if we need to redo, we type redo, pop from the redo stack
and we undo our undo.
If a new action is done, the redo stack immediately empties/reset.
I dont know if there should at least be a limit on how many undos are possible in case that creates bloat