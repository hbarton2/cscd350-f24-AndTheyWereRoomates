package org.project.Memento;

import java.util.HashMap;
import java.util.Map;
import org.project.Model.UMLClassNode;

/**
 * Represents a snapshot of the state in the Memento design pattern.
 *
 * <p>The {@code Memento} class stores a deep copy of the state, ensuring that modifications to the
 * original state do not affect the saved snapshot.
 */
public class Memento {

  private final Map<String, UMLClassNode> state;

  /**
   * Creates a new {@code Memento} with a deep copy of the given state.
   *
   * @param state a {@code Map} containing the current state to be saved
   */
  public Memento(Map<String, UMLClassNode> state) {
    this.state = new HashMap<>();
    for (Map.Entry<String, UMLClassNode> entry : state.entrySet()) {
      this.state.put(entry.getKey(), new UMLClassNode(entry.getValue()));
    }
  }

  /**
   * Retrieves a deep copy of the saved state.
   *
   * @return a {@code Map} containing a deep copy of the saved state
   */
  public Map<String, UMLClassNode> getState() {
    Map<String, UMLClassNode> copy = new HashMap<>();
    for (Map.Entry<String, UMLClassNode> entry : state.entrySet()) {
      copy.put(entry.getKey(), new UMLClassNode(entry.getValue()));
    }
    return copy;
  }
}
