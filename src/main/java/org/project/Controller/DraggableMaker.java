package org.project.Controller;

import java.util.function.BooleanSupplier;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.project.Model.UMLClassNode;
import org.project.View.GraphicalClassNode;

/** This provides functionality to make a JavaFx Node draggable. */
public class DraggableMaker {

  private final Pane canvas; // Reference to the canvas
  private double mouseAnchorX;
  private double mouseAnchorY;

  /**
   * Constructs a DraggableMaker with a reference to the canvas.
   *
   * @param canvas the canvas Pane that will expand dynamically
   */
  public DraggableMaker(Pane canvas) {
    this.canvas = canvas;
  }

  /**
   * Makes a generic JavaFX Node draggable.
   *
   * @param node the Node to make draggable
   */
  public void makeDraggable(GraphicalClassNode node) {
    node.setOnMousePressed(
        event -> {
          node.setxCoordinate(event.getSceneX() - node.getLayoutX());
          node.setyCoordinate(event.getSceneY() - node.getLayoutY());
          event.consume(); // Consume the event
        });

    node.setOnMouseDragged(
        event -> {
          double newX = event.getSceneX() - node.getxCoordinate();
          double newY = event.getSceneY() - node.getyCoordinate();

          // Set the new position of the node
          node.setLayoutX(newX);
          node.setLayoutY(newY);

          event.consume(); // Consume the event
        });
  }

  /**
   * Makes a GraphicalClassNode draggable and updates its associated UMLClassNode position.
   *
   * @param graphicalNode the GraphicalClassNode to make draggable
   * @param umlNode the associated UMLClassNode whose position will be updated
   */
  public void makeDraggable(GraphicalClassNode graphicalNode, UMLClassNode umlNode) {
    graphicalNode.setOnMousePressed(
        event -> {
          mouseAnchorX = event.getSceneX() - graphicalNode.getLayoutX();
          mouseAnchorY = event.getSceneY() - graphicalNode.getLayoutY();
        });

    graphicalNode.setOnMouseDragged(
        event -> {
          double newX = event.getSceneX() - mouseAnchorX;
          double newY = event.getSceneY() - mouseAnchorY;

          // Set the new position of the graphical node
          graphicalNode.setLayoutX(newX);
          graphicalNode.setLayoutY(newY);

          // Update the associated UMLClassNode's position
          umlNode.setPosition(newX, newY);

          // Ensure canvas size is adjusted dynamically
          ensureCanvasSize(newX, newY, graphicalNode);
        });
  }

  public void makeDraggable(
      GraphicalClassNode node, UMLClassNode umlNode, BooleanSupplier draggingBackgroundSupplier) {
    node.setOnMousePressed(
        event -> {
          if (draggingBackgroundSupplier.getAsBoolean()) return; // Skip if dragging background

          mouseAnchorX = event.getSceneX() - node.getLayoutX();
          mouseAnchorY = event.getSceneY() - node.getLayoutY();
          event.consume(); // Consume the event
        });

    node.setOnMouseDragged(
        event -> {
          if (draggingBackgroundSupplier.getAsBoolean()) return; // Skip if dragging background

          double newX = event.getSceneX() - mouseAnchorX;
          double newY = event.getSceneY() - mouseAnchorY;

          // Update node position
          node.setLayoutX(newX);
          node.setLayoutY(newY);

          // Update the associated UML node position
          umlNode.setPosition(newX, newY);

          // Adjust canvas size if needed
          ensureCanvasSize(newX, newY, node);

          event.consume(); // Consume the event
        });
  }

  /**
   * Ensures the canvas expands dynamically if a node is dragged near its edges.
   *
   * @param x the x-coordinate of the node
   * @param y the y-coordinate of the node
   * @param node the node being dragged
   */
  private void ensureCanvasSize(double x, double y, Node node) {
    double padding = 100; // Distance from edge to trigger expansion

    // Check if we need to expand the canvas width
    if (x + node.prefWidth(-1) + padding > canvas.getPrefWidth()) {
      canvas.setPrefWidth(canvas.getPrefWidth() + 500); // Increase canvas width
    }

    // Check if we need to expand the canvas height
    if (y + node.prefHeight(-1) + padding > canvas.getPrefHeight()) {
      canvas.setPrefHeight(canvas.getPrefHeight() + 500); // Increase canvas height
    }
  }
}
