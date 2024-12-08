package org.project.Memento;

import java.util.Stack;

/**
 * Manages the state history for undo and redo operations using the Memento design pattern.
 *
 * <p>The {@code Caretaker} class maintains two stacks: one for undo operations and another
 * for redo operations. It allows saving states, undoing changes, and redoing changes.
 */
public class Caretaker {

  private final Stack<Memento> undoStack = new Stack<>();
  private final Stack<Memento> redoStack = new Stack<>();

  /**
   * Saves the current state to the undo stack and clears the redo stack.
   *
   * @param memento the {@code Memento} object representing the current state
   */
  public void saveState(Memento memento) {
    undoStack.push(memento);
    redoStack.clear();
  }

  /**
   * Performs an undo operation.
   *
   * <p>Moves the current state to the redo stack and returns the previous state from
   * the undo stack. If no undo state is available, returns {@code null}.
   *
   * @return the {@code Memento} object representing the previous state, or {@code null}
   *         if no state is available to undo
   */
  public Memento undo() {
    if (!undoStack.isEmpty()) {
      Memento currentState = undoStack.pop();
      redoStack.push(currentState);
      if (!undoStack.isEmpty()) {
        return undoStack.peek();
      }
    }
    return null;
  }

  /**
   * Performs a redo operation.
   *
   * <p>Moves a state from the redo stack back to the undo stack and returns it.
   * If no redo state is available, returns {@code null}.
   *
   * @return the {@code Memento} object representing the redone state, or {@code null}
   *         if no state is available to redo
   */
  public Memento redo() {
    if (!redoStack.isEmpty()) {
      Memento memento = redoStack.pop();
      undoStack.push(memento);
      return memento;
    }
    return null;
  }
}
