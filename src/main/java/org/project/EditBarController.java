package org.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.awt.event.MouseEvent;
import java.util.Collection;

public class EditBarController {
    @FXML
    private TextField className;

    @FXML
    private ListView<String> fieldList;
    @FXML
    private Button addFieldButton;
    @FXML
    private Button renameFieldButton;
    @FXML
    private Button deleteFieldButton;


    @FXML
    void addField(MouseEvent event) {
        fieldList.getItems().add("new Field");

    }

    public void addField(javafx.scene.input.MouseEvent mouseEvent) {
    }

    public void renameField(ActionEvent actionEvent) {
    }

    public void deleteField(ActionEvent actionEvent) {
    }

    public void addMethod(ActionEvent actionEvent) {
    }

    public void renameMethod(ActionEvent actionEvent) {
    }

    public void deleteMethod(ActionEvent actionEvent) {
    }

    public void deleteRelation(ActionEvent actionEvent) {
    }
}
