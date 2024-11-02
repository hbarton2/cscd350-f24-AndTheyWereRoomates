package org.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;

public class MainController {

    @FXML
    private Pane canvas;

    @FXML
    private VBox inspectorPane;

    @FXML
    private Button toggleInspectorButton;

    @FXML
    private TextField classNameInput;

    @FXML
    private TextField fieldNameInput;

    @FXML
    private ComboBox<String> dataTypeComboBox;

    @FXML
    private TextField methodNameInput;

    @FXML
    private ComboBox<String> parametersComboBox;

    private VBox selectedClassBox = null;

    public void createClass(ActionEvent a) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/classNode.fxml"));
            VBox classBox = loader.load();
            classBox.setOnMouseClicked(event -> selectClassBox(classBox));

            canvas.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
                double centerX = newValue.getWidth() / 2;
                double centerY = newValue.getHeight() / 2;
                classBox.setLayoutX(centerX - classBox.getWidth() / 2);
                classBox.setLayoutY(centerY - classBox.getHeight() / 2);
            });

            canvas.getChildren().add(classBox);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void selectClassBox(VBox classBox) {
        if (selectedClassBox != null) {
            selectedClassBox.setEffect(null);
        }

        selectedClassBox = classBox;

        DropShadow highlight = new DropShadow();
        highlight.setColor(Color.BLUE);
        highlight.setWidth(20);
        highlight.setHeight(20);
        selectedClassBox.setEffect(highlight);

        if (selectedClassBox.getChildren().get(0) instanceof TextField) {
            TextField classNameField = (TextField) selectedClassBox.getChildren().get(0);
            classNameInput.setText(classNameField.getText());
        }
    }

    @FXML
    public void toggleInspector(ActionEvent event) {
        boolean isInspectorVisible = inspectorPane.isVisible();
        inspectorPane.setVisible(!isInspectorVisible);
        toggleInspectorButton.setText(isInspectorVisible ? "Show" : "Hide");
    }

    @FXML
    public void handleSaveClassName(ActionEvent event) {
        if (selectedClassBox != null && selectedClassBox.getChildren().get(0) instanceof TextField) {
            String className = classNameInput.getText();
            TextField classNameField = (TextField) selectedClassBox.getChildren().get(0);
            classNameField.setText(className);
        }
    }

    @FXML
    public void handleAddField(ActionEvent event) {

    }

    @FXML
    public void handleAddMethod(ActionEvent event) {

    }
}
