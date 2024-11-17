This document lists all the patterns used in the project.

## Mementos Pattern
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

## MVC Pattern
The java files are all sectioned off into their respective folders that handle specific parts of the code (Besides Memento)


## Pattern 3
...
## Pattern 4
...
## Pattern 5
...
## Pattern 6
...