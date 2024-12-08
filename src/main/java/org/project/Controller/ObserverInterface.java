package org.project.Controller;

import org.project.View.GraphicalClassNode;

/**
 * Interface for implementing the observer design pattern.
 *
 * <p>Classes implementing this interface are notified of changes in the state of an {@code
 * ObservableClass}.
 */
public interface ObserverInterface {

  /**
   * Called to notify the observer of a state change.
   *
   * @param selectedGraphicalClassNode the currently selected {@code GraphicalClassNode}, or {@code
   *     null} if no class node is selected
   */
  void update(GraphicalClassNode selectedGraphicalClassNode);
}
