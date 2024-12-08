package org.project.View;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.project.Controller.DraggableMaker;

/** This represents a draggable class box that displays the name, fields, and methods. */
public class GraphicalClassNode extends VBox {

  private final Label classNameLabel;
  private final ListView<String> fieldsListView;
  private final ListView<String> methodsListView;
  private double xCoordinate;
  private double yCoordinate;

  /**
   * Constructs a GraphicalClassNode with a specified class name.
   *
   * @param className the name of the class that will be displayed in this box
   * @param canvas the canvas Pane where the class node will be placed
   */
  public GraphicalClassNode(String className, Pane canvas) {
    // Initialize components
    this.classNameLabel = new Label(className);
    this.fieldsListView = new ListView<>();
    this.methodsListView = new ListView<>();
    this.xCoordinate = 0.0;
    this.yCoordinate = 0.0;

    // Style the class box
    classNameLabel.setMouseTransparent(true);
    this.setStyle(
        "-fx-border-width: 5; -fx-border-color: lightgreen; -fx-background-color: WHITE;");
    classNameLabel.setStyle("-fx-font-size: 18px;");
    this.setPrefSize(200, 300);
    this.getChildren().addAll(classNameLabel, fieldsListView, methodsListView);

    // Make the node draggable
    DraggableMaker draggableMaker = new DraggableMaker(canvas);
    draggableMaker.makeDraggable(this);
  }

  /**
   * Adds a field to the list of fields in the GraphicalClassNode.
   *
   * @param field the field name that will be added
   */
  public void addField(String field) {
    fieldsListView.getItems().add(field);
  }

  /**
   * Adds a method to the list of methods in the GraphicalClassNode.
   *
   * @param method the method name that will be added
   */
  public void addMethod(String method) {
    methodsListView.getItems().add(method);
  }

  /**
   * Returns the name of the class displayed in this class box.
   *
   * @return the class name as a String
   */
  public String getName() {
    return this.classNameLabel.getText();
  }

  // Getter and Setter for xCoordinate
  public double getxCoordinate() {
    return xCoordinate;
  }

  public void setxCoordinate(double xCoordinate) {
    this.xCoordinate = xCoordinate;
  }

  // Getter and Setter for yCoordinate
  public double getyCoordinate() {
    return yCoordinate;
  }

  public void setyCoordinate(double yCoordinate) {
    this.yCoordinate = yCoordinate;
  }
}
