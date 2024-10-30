# cscd350-f24-AndTheyWereRoomates
[Project Link](https://github.com/hbarton2/cscd350-f24-AndTheyWereRoomates/tree/readMe)

# Command Line Interface Instructions
Upon selecting CLI, you'll be brought to the Command Line Interface.

## List of Commands
These are the commands you type into the command line interface add/adjust code in the UML.
If you wish to pull up a menu to see commands while running the UML editor, type ```help``` into the command window to pull up a list of commands.

### Class Commands
These are the commands you use
- Add Class -
  <br>```add class (class name)```
  <br>This creates a new class
- Remove Class -
  <br>```remove class (class name)```
  <br>This removes an existing class with the name
- Rename Class -
  <br>```rename class (old class name) (new class name)```
  <br>This renames an existing class with the new name
### Field Commands
**To use any of the Field commands, there needs to be an existing class first**
- Add Field -
  <br>```add field (existing class name) (field name) (field type)```
  <br>This creates a field in an existing class. This requires a field type to be clarified.
- Remove Field -
  <br>```remove field (existing class name) (field name)  (field type)```
  <br>This removes an existing field in an existing class
- Rename Field -
  <br>```rename field (existing class name) (old field name) (new field name)```
  <br>This renames an existing field in an existing class with the new name
### Method Commands
**To use any of the Method commands, there needs to be an existing class first**
- Add Method -
  <br>```add method (existing class name) (method name)```
  <br>This creates a method in an existing class
- Remove Method -
  <br>```remove method (existing class name) (method name)```
  <br>This removes an existing method in an existing class
- Rename Method -
  <br>```rename field (existing class name) (old method name) (new method name)```
  <br>This renames an existing method in an existing class with the new name
### Parameter Commands
**To use any of the Parameter commands, there needs to be an existing class and an existing method**
- Add Parameter -
  <br>a```dd parameter (existing class name) (existing method name) (parameter name) (parameter type)```
  <br>This creates a parameter in an existing method in an existing class
- Remove Parameter -
  <br>```remove parameter (existing class name) (existing method name) (parameter name)```
  <br>This removes an existing parameter in an existing method in an existing class
- Rename Parameter -
  <br>```remove parameter (existing class name) (existing method name) (old parameter name) (new parameter name) (new parameter type)```
  <br>This renames and changes parameter type of an existing parameter in an existing method in an existing class
### Relationship Commands
**To use any of the Relationship commands, there needs to be two existing classes**
- Add Relationship -
  <br>```add relationship (class source) (class destination)```
  <br>This defines a connection between one class to another
- Remove Relationship -
  <br>```remove relationship (class source)```
  <br>This removes a connection between one class to another

### Misc. Commands
These are commands that don't fall under any given category and are for actions such as save and load.
WIP

**-Editor note: Everything Below here is more than likely to be gutted and removed as it's covering stuff from Sprint 1.**

## Help Command
#### Commands
Help - Brings up a detailed list of usable commands 
<br>Add - Add command allows you to create a new class, method, field, relationship, or parameter. Syntax: 'add [object to add]'
<br>Delete - Delete command allows you to delete existing class, method, field, relationship. Syntax: 'add [object to delete]'
<br>Rename - Rename command allows you to rename existing class, method, field. Syntax: 'add [object to rename]'
<br>Save - Save command allows you to save existing class. Syntax: 'save [object]' 
<br>Load - Load command allows you to load existing class. Syntax: 'load [object]'
<br>List - List command allows you to list existing class. Syntax: 'list [object]'

<br>Exit - Exit the program
## Class.java 

#### Class(String name)
Composed of:
* Name
* Method List
* Field list
* Relation List
<br>Upon Creation, the input name is assigned to the Class.
#### addMethod(String methodName) 
Creates a new method to the class, stored in the Method List

#### removeMethod(String methodName)
Removes a method from the class in the Method List

#### renameMethod(String oldName, String newName)
Renames a method from the class in the Method List. 
<br>Requires a new name for the method

#### addRelation(final String source, final String dest)
Creates a new relation to the class, stored in the Relation List
<br>Requires a source and a destintination link

#### removeRelation(String sourceName, String dest)
Removes a relation from the class in the Relation List. 
<br>Requires both a source name and destination link

#### getName()
Returns the name of the class
#### setName(String name)
Sets the name of the class based on the name input
#### addField(String name, String type)
Creates a new field to the class, stored in the Field List

#### renameField(String old, String newName)
Removes a field from the class in the Field List

#### Field findField( final String name)
Finds a field from the class in the Field List and returns it if it exists

#### hasField(final String name)
Checks if a given field exists in the class

## Field.java 
#### Field (final String name, final String type)
Input takes a name and a type
#### getName()
Returns the name of the field
#### getType()
Returns the type of the field
#### setName()
Sets the name of the field based on the string input
#### setType()
Sets the name of the type based on the string input

## Menu.java 

#### runMenu()
Runs the main program until the user types in 'Exit'

#### inputCheck(String input)
Reads in user inputs.
<br> If input is 'help', it will display the help commands
<br> If input is 'exit', it will close the program
<br> If input is anything else, it will display 'Invalid number of arguements'

#### commandCheck(String[] input)
Executes the code based on the first input
<br>Code checks based on inputs seperated by a space.
Example: _Add_ ...

#### addCommand(String[] input)
Executes the code based on the second input
<br>Code checks based on inputs after the space.
Example: Add _Method_

#### addClass()
Creates a class
#### addMethod()
Adds a method to the created class.
 Requires user to input a name.
#### addParameter()
Adds a parameter to the created class.  Requires user to input a name.
#### addRelationship()
Adds a relationship to the created class.  Requires user to input a name.

#### helpCommand(String command)
Displays detailed information for any commands.
#### help()
Creates a list of useable commands.

## Method.java 
#### Method(final String name)
Composed of:
* Name
* Parameter list
<br>Upon Creation, the input name is assigned to the Method name.

#### addParameter(final String parameterName, final String parameterType)
Creates a new paramater to add to its list. Requires a name and a parameter type.

#### changeParameter(final String oldParamName, final String newParamName, final String newParamType)
Changes a parameter type in the method. Requires a new name and parameter type.

#### deleteParameter(final String parameterName)
Deletes a given parameter in the method. Requires a name.

#### deleteAllParameter()
Completely wipes all parameters in the method.

#### hasParameter(final String parameterName)
Checks if a given parameter exists in the method

#### getName()
Returns the name of the method

#### setName(final String name)
Sets the name of the method

#### ArrayList<Parameter> getParameter()
Returns the entire list of parameters in the method

## Parameter.java 
#### Parameter (final String name, final String type)
Composed of:
* Name
* Type
<br>Upon Creation, the Name and Type will be set.

#### String getName()
Returns the name of the parameter

#### String getType()
Returns the type of the parameter

#### setName(final String name)
Sets the name for the parameter

#### setType(final String type)
Sets the type for the parameter

## Relationship.java 
#### Relationship (final String source, final String destination)
Composed of:
* Source
* Destination
<br>Upon Creation, the Source and Destination will be set.

#### getSource()
Returns the source name for the Relationship

#### getDestination()
Returns the destination name for the Relationship

#### setSource(final String source)
Sets the name of the source for the Relationship

#### setDestination(final String destination)
Sets the destination for the Relationship

## Storage.java 
Contains a dedicated TreeMap to contain the classes
#### addClass(String name)
Adds a new class to the given storage

#### deleteClass(String name)
Deletes a class in the storage

#### renameClass(String oldName, String newName)
Renames a class in storage. Requires a new name
