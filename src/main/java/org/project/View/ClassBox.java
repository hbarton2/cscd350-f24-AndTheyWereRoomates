package org.project.View;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import org.project.Controller.DraggableMaker;

/**
 * This represents a draggable class box that displays the name, fields, and methods.
 */
public class ClassBox extends VBox {
    private Label classNameLabel;
    private final ListView<String> fieldsListView;
    private final ListView<String> methodsListView;
    private double xCoordinate;
    private double yCoordinate;

    /**
     * Constructs a ClassBox with a specified class name.
     * @param className the name of the class that will be displayed in this box
     */
    public ClassBox(String className) {
        classNameLabel = new Label(className);
        fieldsListView = new ListView<>();
        methodsListView = new ListView<>();
        xCoordinate = 0.0;
        yCoordinate = 0.0;


        classNameLabel.setMouseTransparent(true);
        this.setStyle("-fx-border-width: 5; -fx-border-color: black; -fx-background-color: white");
        classNameLabel.setStyle("-fx-font-size: 18px;");
        this.setPrefSize(200,300);
        this.getChildren().addAll(classNameLabel, fieldsListView, methodsListView);
        DraggableMaker draggableMaker = new DraggableMaker();
        draggableMaker.makeDraggable(this);


    }

    /**
     * Adds a field to the list of fields in the ClassBox.
     * @param field the field name that will be added
     */
    public void addField(String field) {
        fieldsListView.getItems().add(field);
    }

    /**
     * Adds a method to the lists of  methods in the ClassBox.
     * @param method the method name that will be added
     */
    public void addMethod(String method) {
        methodsListView.getItems().add(method);
    }

    /**
     * Returns the name of the class displayed in this Classbox.
     * @return the class name as a String.
     */
    public String getName() {
        return this.classNameLabel.getText();
    }

    public void setxCoordinate(double xcoor){
        this.xCoordinate = xcoor;
    }


    public void setyCoordinate(double ycoor){
        this.yCoordinate = ycoor;
    }


}
