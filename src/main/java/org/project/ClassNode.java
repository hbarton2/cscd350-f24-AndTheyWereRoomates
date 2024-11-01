package org.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ClassNode extends VBox{

    @FXML
    private VBox classRepresentation;

    @FXML
    private TextField className;
    @FXML
    private ListView fieldList;
    @FXML
    private ListView methodList;



    @FXML
    public void initialize(){
        DraggableMaker draggableMaker = new DraggableMaker();
        className.setText("Class Name");
        classRepresentation.setStyle("-fx-border-width: 5; -fx-border-color: black");
        draggableMaker.makeDraggable(classRepresentation);

    }

}
