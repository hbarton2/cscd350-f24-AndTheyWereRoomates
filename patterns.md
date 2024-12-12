This document lists all the patterns used in the project.

# Mementos Pattern
Stored in the folder named "Memento" has the java files that creates the Mementos Pattern.
Mementos is used for the Undo and Redo functionality

### Memento.java / Caretaker.java -
These two are the main java files that hold the functionality for the Mementos pattern.
#### Memento.java 
contains the functionalities to save the current state of the program.
#### Caretaker.java 
contains the logic to undo and redo functions, storing the saves.

### CommandLog.java -
#### Line 56 , 115 , 126 , 131 , 155 , 174 , 194 , 217 , 254 , 269 , 298 , 328 , 358 , 390 , 402
All these lines contain the following line of code to create a new save for the undo functionality
#### Line 484 - 493
This segment of the code contains the line of code to execute the undo command.
#### Line 500 - 516
This segment of code contains the line of code to execute the redo functionality.

### CommandRegistries.java -
#### Line 85
Loading the command for the save-state command

# MVC Pattern
The java files are all sectioned off into their respective folders that handle specific parts of the code (Besides Memento)
The files in Model contain the data, files in View are what the user should see, files in Controller have all the functions
controlling how Model and View return.

# Singleton
### Storage.java
Storage.java contains a Singleton instance variable to hold information.
#### Line 9
A private static variable for the Storage instance.

#### Line 20 - 25
Creates an instance when needed.

# Chain of Responsibilities
Through multiple java files, the way a code is handled is through one program taking
in code and then passing it to another file that will parse the information if the input is valid

When processing a command (in new CLI), the program runs the input through multiple java files.
If the program comes across something in the input that's wrong, it'll flag the input as a failure 
and returning text explaining why it failed.

#### ProcessCommand() / CommandLineTerminal.java  (Line 87 - 100)
Checks if the input was blank (Line 90 - 93)
checks if the input was "exit" or "help" (Line 95 - 99)

#### parseCommand() / CommandParser.java (Line 35 - 48)
Checks if the token (input length) length is 0 (Line 38 - 40)

#### executeCommand() / CommandRegistries.java (Line 96 - 102)
Checks if the input command is a valid command (Line 98 - 100)

#### various / CommandLogic.java
Command Logic contains all the functions to alter the data.
These commands have more specific termination requirements for termination.
(Ex: Class creation checks the name for valid inputs)
Usually the code will terminate for these two reasons:
* Name was blank
* The Length of the input is too small (IE: if some commands require a type)
* Name of a given property exists/doesn't exist

# Adapter
For the GUI, there are two files that adapts all the CLI commands from the code to GUI functionalities.
#### CommandBridgeAdapter.java
This converts everything from the CLI commands into GUI functionality

#### CommandBridge.java
An interface class that allows quick access for the GUI to preform commands.

An interface class that allows quick access for the GUI to preform commands.
# Strategy
We implemented Strategy pattern to isolate the logic to draw the arrows.
### ArrowInterface
ArrowStrategy interface to isolate drawing logic
### Where it was implemented
* AggregationArrow.java
* RealizationArrow.java
* GeneralizationArrow.java
* CompositionArrow.java <br>
  These classes contain the logic to draw the different types of arrows
### Where these classes are being used
In the GraphicalUserInterfaceController.java in lines 540-593.
It calls the appropriate logic depending on what the user has selected.

# Observer
The Observer Pattern is implemented in the project to ensure that all ComboBoxes(UI elements that hold data types for fields, method, and etc) are updated whenever a class is created or removed.
### Classes created to implement Observer pattern
#### ObserverInterface.java
#### ObservableClass.java
#### ComboBoxObserver.java
### Where it was implemented
#### GraphicalUserInterfaceController.java in lines 71, 94-104, 166, 200, 875, and 877.
# Factory
We used this pattern to isolate the creation logic of the class box for the GUI.
### Classes created to implement Factory pattern
#### ClassBoxFactory.java
### Where it was implemented
#### GraphicalUserInterfaceController.java in lines 146 and 868

# Singleton
### Where it was implemented
* CommandRegistries.java
Lines 30-33
This was used to ensure we weren't constantly getting the same json file. 

# Bridge
### Where it was implemented
* ArrowStrategy.java
Which spreads out and connects to the different arrow java
* Composition Arrow
* Realization Arrow
* Aggregation Arrow
* Generalization Arrow
This allows the arrows to be one of many different types of arrow types.