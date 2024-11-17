package org.project.Memento;
import java.util.Stack;
public class Caretaker {
    private final Stack<Memento> undoStack = new Stack<>();
    private final Stack<Memento> redoStack = new Stack<>();
    public void saveState(Memento memento) {
        undoStack.push(memento);
        redoStack.clear();
    }
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

    public Memento redo() {
        if (!redoStack.isEmpty()) {
            Memento memento = redoStack.pop();
            undoStack.push(memento);
            return memento;
        }
        return null;
    }
}

