package org.project.Memento;

import java.util.HashMap;
import java.util.Map;
import org.project.Model.UMLClassNode;

public class Memento {

  private final Map<String, UMLClassNode> state;

  public Memento(Map<String, UMLClassNode> state) {
    this.state = new HashMap<>();
    for (Map.Entry<String, UMLClassNode> entry : state.entrySet()) {
      this.state.put(entry.getKey(), new UMLClassNode(entry.getValue()));
    }
  }

  public Map<String, UMLClassNode> getState() {
    Map<String, UMLClassNode> copy = new HashMap<>();
    for (Map.Entry<String, UMLClassNode> entry : state.entrySet()) {
      copy.put(entry.getKey(), new UMLClassNode(entry.getValue()));
    }
    return copy;
  }
}