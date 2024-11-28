package org.project.Controller;

import java.util.TreeMap;
import javafx.scene.Node;

/** This provides functionality to make a JavaFx Node draggable. */
public class DraggableMaker {

  private double mouseAnchorX;
  private double mouseAnchorY;

  /**
   * Makes the specified node draggable by tracking mouse press and drag events.
   *
   * @param node the Node that will be made draggable
   */
  public void makeDraggable(Node node) {
    node.setOnMousePressed(
        e -> {
          mouseAnchorX = e.getX();
          mouseAnchorY = e.getY();
        });

    node.setOnMouseDragged(
        mouseEvent -> {
          node.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
          node.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
        });
  }

  public void makeDraggable(Node node, TreeMap<String, double[]> nodePositions, String nodeName) {
    node.setOnMousePressed(
        e -> {
          mouseAnchorX = e.getX();
          mouseAnchorY = e.getY();
        });
    node.setOnMouseDragged(
        mouseEvent -> {
          double newX = mouseEvent.getSceneX() - mouseAnchorX;
          double newY = mouseEvent.getSceneY() - mouseAnchorY;
          node.setLayoutX(newX);
          node.setLayoutY(newY);
          // Update the position in the TreeMap
          nodePositions.put(nodeName, new double[] {newX, newY});
        });
  }
}
