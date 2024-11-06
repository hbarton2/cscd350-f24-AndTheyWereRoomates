
					USER-STORIES
As a user,
I want to drag my class boxes,
So that I can rearrange the structure of my diagram

As a user,
I want to have my relationship arrows move with my class boxes
So that I can rearrange the structure of my relationship arrows

As a user,
I want to be able to save a diagram in the CLI and load it in the GUI with a layout that makes sense,
So that i do not have to don’t have to reorganize the class boxes and i can work between interfaces seamlessly.

As a user,
I want go to save a diagram,when i save it, i want the class box positions to be preserved,
So that when I load the file from either CLI or GUI, the layout remains consistent and does not have overlapping boxes.

As a user,
I want the ability to choose an option to undo my last action,
So that I am easily able to remove anything I didn’t mean to change.

As a user,
I want a redo function so that I can reapply changes that I previously undid, allowing efficient editing without redoing work manually.


					SCENARIOS

Given that the user is on GUI mode with a populated UML diagram
When the user clicks on a class box with their mouse
Then It should drag the class box with the mouse until the user lets go

Given that the user is on GUI mode with a populated UML diagram with relationships arrows
When the user clicks on a class box with their mouse
Then it should drag the class box along with its relationship arrows.

Given that the user is in CLI mode with a UML diagram populated
When the user saves the diagram and opens it in GUI mode
Then the saved classes should be arranged in a way that avoids overlapping.

Given that the user has saved work from a previous session
When the user loads that work into the GUI mode
Then it should display the classes in the same position they were saved in.

Given that the user is in GUI mode with a populated UML diagram
When I hit the undo button
Then it should go back one action at a time.

Given that the user is in GUI mode with a populated UML diagram
When the user hits the redo button
Then the last undo action should be reapplied.

Given that the user is in CLI mode with a populated UML diagram
When the user hits the redo button
Then the last undo action should be reapplied.

Given that the user is in GUI mode with a populated UML diagram
When I hit the undo button
Then it should go back one action at a time.

