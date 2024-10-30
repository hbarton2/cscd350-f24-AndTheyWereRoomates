# cscd350-f24-AndTheyWereRoomates
[Project Link](https://github.com/hbarton2/cscd350-f24-AndTheyWereRoomates/tree/readMe)
# TABLE OF CONTENTS
Help Command
<br>Class.java 
<br>Field.java
<br>Menu.java
<br>Method.java
<br>Parameter.java
<br>Relationship.java
<br>Storage.java


# How to use Program
Type in the text bar with the text "Enter Command" above it. Once you typed out, click "Execute Command"

## List of Commands

### Class Commands
- Add Class - add class (class name) - This creates a new class
- Remove Class - remove class (class name) - This removes an existing class with the name
- Rename Class - rename class (old class name) (new class name) - This renames an existing class with the new name
### Field Commands
This requires an existing class exist before altering a field. 
- Add Field - add field (existing class name) (field name) (field type) - This creates a field in an existing class. This requires a field type to be clarified.
- Remove Field - remove field (existing class name) (field name)  (field type) - This removes an existing field in an existing class
- Rename Field - rename field (existing class name) (old field name) (new field name) - This renames an existing field in an existing class with the new name
### Method Commands
This requires an existing class before altering a method.
- Add Method - add method (existing class name) (method name) - This creates a method in an existing class
- Remove Method - remove method (existing class name) (method name) - This removes an existing method in an existing class
- Rename Method - rename field (existing class name) (old method name) (new method name) -  This renames an existing method in an existing class with the new name
### Parameter Commands
This requires an existing class and an existing method to alter a parameter
- Add Parameter - add parameter (existing class name) (existing method name) (parameter name) - This creates a parameter in an existing method in an existing class
- Remove Parameter - remove parameter (existing class name) (existing method name) (parameter name) - This removes an existing parameter in an existing method in an existing class
- Rename Parameter - remove parameter (existing class name) (existing method name) (new parameter name) (old parameter name) - This renames  an existing parameter in an existing method in an existing class
### Relationship Commands
- Add Relationship - WIP
- Remove Relationship - WIP

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
