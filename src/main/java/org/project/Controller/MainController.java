package org.project.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.util.Optional;

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
    private TextField parameterNameInput;

    @FXML
    private ComboBox<String> parameterTypeComboBox;

    @FXML
    private Label parameterCountLabel;

    @FXML
    private TextField relationshipInput;

    private VBox selectedClassBox = null;

    private ObservableList<String> classNames = FXCollections.observableArrayList();

    public UMLController umlController;


    public MainController() {
        this.umlController = new UMLController();
    }

    @FXML
    public void initialize() {
        setupRelationshipAutocomplete();

    }


    public void createClass(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/classNode.fxml"));
            VBox classBox = loader.load();
            classBox.setOnMouseClicked(e -> selectClassBox(classBox));

            canvas.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
                double centerX = newValue.getWidth() / 2;
                double centerY = newValue.getHeight() / 2;
                classBox.setLayoutX(centerX - classBox.getWidth() / 2);
                classBox.setLayoutY(centerY - classBox.getHeight() / 2);
            });

            TextField classNameField = (TextField) classBox.getChildren().get(0);
            String className = classNameField.getText();
            if (!className.isEmpty() && !classNames.contains(className)) {
                classNames.add(className);
            }

            canvas.getChildren().add(classBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteClass(ActionEvent event) {
        if(selectedClassBox != null){
            canvas.getChildren().remove(selectedClassBox);
            selectedClassBox = null;
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

        TextField classNameField = (TextField) selectedClassBox.getChildren().get(0);
        classNameInput.setText(classNameField.getText());
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
            if (!classNames.contains(className)) {
                classNames.add(className);
            }
        }
    }

    @FXML
    public void handleRenameClass(ActionEvent event) {
        if (selectedClassBox != null){
            String newName = classNameInput.getText();
           TextField className = (TextField) selectedClassBox.getChildren().get(0);
           className.setText(newName);

        }
    }

    @FXML
    public void handleAddField(ActionEvent event) {
        if(selectedClassBox != null){
            String fieldName = fieldNameInput.getText();
            ListView<String> fieldList = (ListView<String>) selectedClassBox.getChildren().get(1);
            fieldList.getItems().add(dataTypeComboBox.getValue() + " " + fieldName);
        }
    }


    @FXML
    public void handleDeleteField(ActionEvent event) {
        if (selectedClassBox != null) {
            ListView<String> fieldList = (ListView<String>) selectedClassBox.getChildren().get(1);
            String selectedField = fieldList.getSelectionModel().getSelectedItem();

            if (selectedField != null) {
                fieldList.getItems().remove(selectedField);
            } else {
                System.out.println("No field selected");
            }
        } else {
            System.out.println("No class selected");
        }
    }

    @FXML
    public void handleRenameField(ActionEvent event) {
        if (selectedClassBox != null) {
            ListView<String> fieldList = (ListView<String>) selectedClassBox.getChildren().get(1);
            String selectedField = fieldList.getSelectionModel().getSelectedItem();

            if (selectedField != null) {
                String newFieldName = fieldNameInput.getText();
                String newFieldType = dataTypeComboBox.getValue();

                if (!newFieldName.isEmpty() && newFieldType != null) {
                    String updatedField = newFieldType + " " + newFieldName;
                    int selectIndex = fieldList.getSelectionModel().getSelectedIndex();
                    fieldList.getItems().set(selectIndex, updatedField);
                } else {
                    System.out.println("Enter new name");
                }
            } else {
                System.out.println("No field to rename");
            }

        } else {
            System.out.println("No class selected");
        }
    }

    @FXML
    public void addParameter(ActionEvent event) {
        if (selectedClassBox != null) {
            ListView<String> methodList = (ListView<String>) selectedClassBox.getChildren().get(2);

            if (methodList.getItems().isEmpty()) {
                System.out.println("Add a method first to add parameters.");
                return;
            }

            String parameterName = parameterNameInput.getText();
            String parameterType = parameterTypeComboBox.getValue();

            if (parameterName.isEmpty() || parameterType == null) {
                System.out.println("Enter both a parameter name and type.");
                return;
            }

            int lastMethodIndex = methodList.getItems().size() - 1;
            String currentMethod = methodList.getItems().get(lastMethodIndex);

            if (currentMethod.endsWith("()")) {
                currentMethod = currentMethod.replace("()", "(" + parameterType + " " + parameterName + ")");
            } else {
                currentMethod = currentMethod.replace(")", ", " + parameterType + " " + parameterName + ")");
            }

            methodList.getItems().set(lastMethodIndex, currentMethod);

            parameterNameInput.clear();
            parameterTypeComboBox.getSelectionModel().clearSelection();
        } else {
            System.out.println("No class selected.");
        }
    }


    @FXML
    public void handleAddMethod(ActionEvent event) {
        if (selectedClassBox != null) {
            String methodName = methodNameInput.getText();

            if (methodName.isEmpty()) {
                System.out.println("Enter a method name.");
                return;
            }

            String formattedMethod = methodName + "()";

            ListView<String> methodList = (ListView<String>) selectedClassBox.getChildren().get(2);
            methodList.getItems().add(formattedMethod);

            methodNameInput.clear();
        } else {
            System.out.println("No class selected.");
        }
    }

    @FXML
    public void handleDeleteMethod(ActionEvent event) {
    }

    @FXML
    public void handleRenameMethod(ActionEvent event) {
    }

    @FXML
    public void showRelationshipInput(ActionEvent event) {
        relationshipInput.clear();
        relationshipInput.setVisible(true);
        relationshipInput.requestFocus();
    }

    private void setupRelationshipAutocomplete() {
        relationshipInput.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE) {
                return;
            }

            String input = relationshipInput.getText();
            Optional<String> suggestion = classNames.stream()
                    .filter(name -> name.toLowerCase().startsWith(input.toLowerCase()))
                    .findFirst();

            if (suggestion.isPresent() && !input.isEmpty()) {
                String suggestedText = suggestion.get();
                relationshipInput.setText(suggestedText);
                relationshipInput.positionCaret(input.length());
                relationshipInput.selectEnd();
            }

            if (event.getCode() == KeyCode.ENTER) {
                relationshipInput.deselect();
            }
        });
    }

    @FXML
    public void addRelation(ActionEvent event) {
    }

    @FXML
    public void deleteRelation(ActionEvent event) {
    }
}
