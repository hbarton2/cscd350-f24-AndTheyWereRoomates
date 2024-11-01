package org.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainController {


    @FXML
    private Pane canvas;

    @FXML
    private Button addClass;


    public void createClass(ActionEvent a){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/classNode.fxml"));
            VBox classRepresentation = loader.load(); // Assume ClassRepresentation is a VBox

            canvas.layoutBoundsProperty().addListener((observable, oldValue, newValue) ->{
                double centerX = newValue.getWidth()/2;
                double centerY = newValue.getCenterY()/2;
                classRepresentation.setLayoutX(centerX - classRepresentation.getWidth()/2);
                classRepresentation.setLayoutY(centerY - classRepresentation.getHeight()/2);

            });
            canvas.getChildren().add(classRepresentation);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}
