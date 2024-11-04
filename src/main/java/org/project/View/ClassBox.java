package org.project.View;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import org.project.Controller.DraggableMaker;

public class ClassBox extends VBox {
    private final ListView<String> fieldsListView;
    private final ListView<String> methodsListView;

    public ClassBox(String className) {
        Label classNameLabel = new Label(className);
        fieldsListView = new ListView<>();
        methodsListView = new ListView<>();

        this.setStyle("-fx-border-width: 5; -fx-border-color: black");
        this.setPrefSize(200,300);

        this.getChildren().addAll(classNameLabel, fieldsListView, methodsListView);

        new DraggableMaker().makeDraggable(this);


    }

    public void addField(String field) {
        fieldsListView.getItems().add(field);
    }

    public void addMethod(String method) {
        methodsListView.getItems().add(method);
    }
}
