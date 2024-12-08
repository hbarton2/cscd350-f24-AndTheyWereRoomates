package org.project.Controller;

import java.util.ArrayList;
import java.util.List;

import org.project.View.GraphicalClassNode;

/**
 * Manages a list of graphical class nodes and notifies observers of changes.
 *
 * <p>The {@code ObservableClass} class implements the observer design pattern, allowing
 * observers to register and be notified whenever there are changes to the selected
 * graphical class node or the list of class nodes.
 */
public class ObservableClass {
    private GraphicalClassNode selectedGraphicalClassNode;
    private final List<ObserverInterface> observable = new ArrayList<>();
    private final List<GraphicalClassNode> graphicalClassNodeList = new ArrayList<>();

    /**
     * Adds an observer to the list of observers.
     *
     * @param observer the {@code ObserverInterface} to be added
     */
    public void addObserver(ObserverInterface observer) {
        observable.add(observer);
    }

    /**
     * Removes an observer from the list of observers.
     *
     * @param observer the {@code ObserverInterface} to be removed
     */
    public void removeObserver(ObserverInterface observer) {
        observable.remove(observer);
    }

    /**
     * Adds a graphical class node to the list and notifies observers.
     *
     * @param graphicalClassNode the {@code GraphicalClassNode} to be added
     */
    public void addClassBox(GraphicalClassNode graphicalClassNode) {
        graphicalClassNodeList.add(graphicalClassNode);
        notifyObservers();
    }

    /**
     * Removes a graphical class node from the list and notifies observers.
     *
     * @param graphicalClassNode the {@code GraphicalClassNode} to be removed
     */
    public void removeClasBox(GraphicalClassNode graphicalClassNode) {
        graphicalClassNodeList.remove(graphicalClassNode);
        notifyObservers();
    }

    /**
     * Sets the selected graphical class node and notifies observers.
     *
     * @param graphicalClassNode the {@code GraphicalClassNode} to set as selected
     */
    public void setSelectedClassBox(GraphicalClassNode graphicalClassNode) {
        this.selectedGraphicalClassNode = graphicalClassNode;
        notifyObservers();
    }

    /**
     * Gets the currently selected graphical class node.
     *
     * @return the selected {@code GraphicalClassNode}, or {@code null} if none is selected
     */
    public GraphicalClassNode getSelectedClassBox() {
        return selectedGraphicalClassNode;
    }

    /**
     * Gets a list of all graphical class nodes.
     *
     * @return a new list containing all {@code GraphicalClassNode} objects
     */
    public List<GraphicalClassNode> getAllClassBoxes() {
        return new ArrayList<>(graphicalClassNodeList);
    }

    /**
     * Notifies all registered observers of changes to the selected class box or the list of class nodes.
     */
    public void notifyObservers() {
        for (ObserverInterface observer : observable) {
            observer.update(selectedGraphicalClassNode);
        }
    }
}
