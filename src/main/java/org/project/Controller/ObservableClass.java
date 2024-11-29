package org.project.Controller;

import java.util.ArrayList;
import java.util.List;
import org.project.View.GraphicalClassNode;

public class ObservableClass {
  private GraphicalClassNode selectedGraphicalClassNode;
  private final List<ObserverInterface> observable = new ArrayList<>();
  private final List<GraphicalClassNode> graphicalClassNodeList = new ArrayList<>();

  public void addObserver(ObserverInterface observer) {
    observable.add(observer);
  }

  public void removeObserver(ObserverInterface observer) {
    observable.remove(observer);
  }

  public void addClassBox(GraphicalClassNode graphicalClassNode) {
    graphicalClassNodeList.add(graphicalClassNode);
    notifyObservers();
  }

  public void removeClasBox(GraphicalClassNode graphicalClassNode) {
    graphicalClassNodeList.remove(graphicalClassNode);
    notifyObservers();
  }

  public void setSelectedClassBox(GraphicalClassNode graphicalClassNode) {
    this.selectedGraphicalClassNode = graphicalClassNode;
    notifyObservers();
  }

  public GraphicalClassNode getSelectedClassBox() {
    return selectedGraphicalClassNode;
  }

  public List<GraphicalClassNode> getAllClassBoxes() {
    return new ArrayList<>(graphicalClassNodeList);
  }

  public void notifyObservers() {
    for (ObserverInterface observer : observable) {
      observer.update(selectedGraphicalClassNode);
    }
  }
}
