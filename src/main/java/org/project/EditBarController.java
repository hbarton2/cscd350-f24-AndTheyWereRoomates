package org.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.Collection;

public class EditBarController {
    @FXML
    private TextField className;

    @FXML
    private ListView fieldList;
    @FXML
    private Button addFieldButton;
    @FXML
    private Button renameFieldButton;
    @FXML
    private Button deleteFieldButton;

    private ObservableList<String> obFieldlist;

    public void setAddFieldButton(ActionEvent event) {
    }
}
