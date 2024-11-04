package org.project.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

  
  
import org.project.View.ClassBox;

import org.w3c.dom.Text;

import java.awt.*;
import javafx.scene.control.Label;
import java.io.IOException;
import java.util.List;
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
    private ComboBox<String> fromComboBox;

    @FXML
    private ComboBox<String> toComboBox;

    @FXML
    private ComboBox<String> relationshipTypeComboBox;

    @FXML
    private TextField className;

    @FXML
    private TextField relationshipInput;

    private ClassBox selectedClassBox = null;

    private ObservableList<String> classNames = FXCollections.observableArrayList();

    public UMLController umlController;


    public MainController() {
        this.umlController = new UMLController();
    }

    @FXML
    public void initialize() {

    }


    public void createClass(ActionEvent event) {

        ClassBox classBox = new ClassBox("New Class #" + (umlController.getStorage().getClasses().size() + 1));
        classBox.setOnMouseClicked(e -> selectClassBox(classBox));

        // Calculate the center of the canvas
        double centerX = (canvas.getWidth() - classBox.getPrefWidth()) / 2;
        double centerY = (canvas.getHeight() - classBox.getPrefHeight()) / 2;

        // Set the position of the classBox to the center of the canvas
        classBox.setLayoutX(centerX);
        classBox.setLayoutY(centerY);

        fromComboBox.getItems().add(classBox.getName());
        toComboBox.getItems().add(classBox.getName());
        canvas.getChildren().add(classBox);

        umlController.classCommands.addClass(new String[]{"add", "class", classBox.getName()});
        System.out.println("Size: " + umlController.getStorage().getClasses().size());
    }

    @FXML
    public void deleteClass(ActionEvent event) {
        if(selectedClassBox != null) {
            Label className = (Label) selectedClassBox.getChildren().get(0);
            String classNameRemove = className.getText();


            String message = umlController.classCommands.removeClass(new String[]{"remove", "class", classNameRemove});

            if(message.isEmpty()) {
                // GUI update
                canvas.getChildren().remove(selectedClassBox);
                selectedClassBox = null;

                fromComboBox.getItems().remove(classNameRemove);
                toComboBox.getItems().remove(classNameRemove);

            } else {
                showAlert("Class", message);
            }
        }
    }

    private void selectClassBox(ClassBox classBox) {
        if (selectedClassBox != null) {
            selectedClassBox.setEffect(null);
        }

        selectedClassBox = classBox;

        DropShadow highlight = new DropShadow();
        highlight.setColor(Color.BLUE);
        highlight.setWidth(10);
        highlight.setHeight(10);
        selectedClassBox.setEffect(highlight);

        Label classNameLabel = (Label) selectedClassBox.getChildren().get(0);
        classNameInput.setText(classNameLabel.getText());
    }

    @FXML
    public void toggleInspector(ActionEvent event) {
        boolean isInspectorVisible = inspectorPane.isVisible();
        inspectorPane.setVisible(!isInspectorVisible);
        toggleInspectorButton.setText(isInspectorVisible ? "Show" : "Hide");
    }

    @FXML
    public void handleSetClassName(ActionEvent event) {
        if (selectedClassBox != null) {
            String newName = classNameInput.getText();
            if(umlController.getStorage().hasClass(newName)) {
                showAlert("Class Creation Error", "A class with the name \"" + newName + "\" already exists.");
                return;
            }
            Label className = (Label) selectedClassBox.getChildren().get(0);
            String currentName = className.getText();
            // Rename class in storage
            umlController.getStorage().renameClass(currentName, newName);
            // Rename class in View
            className.setText(newName);

            int fromIndex = fromComboBox.getItems().indexOf(currentName);
            int toIndex = toComboBox.getItems().indexOf(currentName);

            if (fromIndex >= 0) {
                fromComboBox.getItems().set(fromIndex, newName);
            }
            if (toIndex >= 0) {
                toComboBox.getItems().set(toIndex, newName);
            }
        }
    }

    @FXML
    public void handleAddField(ActionEvent event) {
        if(selectedClassBox != null){

            if(dataTypeComboBox.getValue() == null){
                showAlert("Error", "Chose the type of field");
                return;
            }

            String fieldName = fieldNameInput.getText();
            ListView<String> fieldList = (ListView<String>) selectedClassBox.getChildren().get(1);
            String message = umlController.fieldCommands.addField(new String[]{"add", "field", selectedClassBox.getName(), fieldName, dataTypeComboBox.getValue()});


            if(message.isEmpty()) {
                fieldNameInput.clear();
                fieldList.getItems().add(dataTypeComboBox.getValue() + " " + fieldName);
            }
            else {
                showAlert("Error", message);
            }
            System.out.println(umlController.getStorage().getClass(selectedClassBox.getName()).fields.toString());


        }
    }


    @FXML
    public void handleDeleteField(ActionEvent event) {
        if (selectedClassBox != null) {
            ListView<String> fieldList = (ListView<String>) selectedClassBox.getChildren().get(1);
            String selectedField = fieldList.getSelectionModel().getSelectedItem();
            String fieldType = selectedField.split(" ")[0].toLowerCase().trim();
            String fieldName = selectedField.split(" ")[1].toLowerCase().trim();

            if (selectedField != null) {
                umlController.fieldCommands.removeField(new String[]{"add", "field", selectedClassBox.getName(), fieldName, fieldType}); // "add field [class name] [field name] [field type]"
                fieldList.getItems().remove(selectedField);
            }
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

                if (!newFieldName.isEmpty()  && newFieldType != null) {
                    String updatedField = newFieldType + " " + newFieldName;
                    int selectIndex = fieldList.getSelectionModel().getSelectedIndex();
                    fieldList.getItems().set(selectIndex, updatedField);

                    fieldNameInput.clear();

                    // update storage
                    String oldFieldName = selectedField.split(" ")[1].toLowerCase().trim();
                    umlController.fieldCommands.renameField(new String[]{"rename", "field", selectedClassBox.getName(), oldFieldName, newFieldName, newFieldType});
                    System.out.println(umlController.getStorage().getClass(selectedClassBox.getName()).fields.toString());
                    System.out.println("Size of fields: " + umlController.getStorage().getClass(selectedClassBox.getName()).fields.size());
                }
            }
        }
    }


    @FXML
    public void addParameter(ActionEvent event) {
        if (selectedClassBox != null) {
            ListView<String> methodList = (ListView<String>) selectedClassBox.getChildren().get(2);

            if (!methodList.getItems().isEmpty()) {
                String parameterName = parameterNameInput.getText();
                String parameterType = parameterTypeComboBox.getValue();

                if (!parameterName.isEmpty() && parameterType != null) {
                    int lastMethodIndex = methodList.getItems().size() - 1;
                    String currentMethod = methodList.getItems().get(lastMethodIndex);

                    if (currentMethod.endsWith("()")) {
                        currentMethod = currentMethod.replace("()", "(" + parameterType + " " + parameterName + ")");
                    } else {
                        currentMethod = currentMethod.replace(")", ", " + parameterType + " " + parameterName + ")");
                    }

                    parameterNameInput.clear();
                    methodList.getItems().set(lastMethodIndex, currentMethod);
                }
            }
        }
    }



    @FXML
    public void handleAddMethod(ActionEvent event) {
        if (selectedClassBox != null) {
            String methodName = methodNameInput.getText();

            if (!methodName.isEmpty()) {

                String message = umlController.methodCommands.addMethod(new String[]{"add", "method", selectedClassBox.getName(), methodName});
                if(message.isEmpty()) {
                    String formattedMethod = methodName + "()";
                    ListView<String> methodList = (ListView<String>) selectedClassBox.getChildren().get(2);
                    methodList.getItems().add(formattedMethod);

                    methodNameInput.clear();
                }
                else {
                    showAlert("Error", message);
                }


            }
        }
    }


    @FXML
    public void handleDeleteMethod(ActionEvent event) {
        if (selectedClassBox != null) {
            ListView<String> methodList = (ListView<String>) selectedClassBox.getChildren().get(2);
            String selectedMethod = methodList.getSelectionModel().getSelectedItem();

            if (selectedMethod != null) {

                umlController.methodCommands.removeMethod(new String[]{"delete", "method", selectedClassBox.getName(), selectedMethod});
                System.out.println("Number of methods: " + umlController.getStorage().getClass(selectedClassBox.getName()).fields.size());
                methodList.getItems().remove(selectedMethod);

                methodNameInput.clear();
            }
        }
    }

    @FXML
    public void handleRenameMethod(ActionEvent event) {
        if (selectedClassBox != null) {
            ListView<String> methodList = (ListView<String>) selectedClassBox.getChildren().get(2);
            String selectedMethod = methodList.getSelectionModel().getSelectedItem();

            if (selectedMethod != null) {
                String newMethodName = methodNameInput.getText();

                if (!newMethodName.isEmpty()) {
                    String oldMethodName = selectedMethod.substring(0, selectedMethod.indexOf("("));

                    umlController.methodCommands.renameMethod(new String[]{"rename", "method", selectedClassBox.getName(), oldMethodName, newMethodName});



                    int selectedIndex = methodList.getSelectionModel().getSelectedIndex();
                    String updatedMethod = newMethodName + selectedMethod.substring(selectedMethod.indexOf('('));
                    methodList.getItems().set(selectedIndex, updatedMethod);

                    methodNameInput.clear();
                }
            }
        }
    }
    @FXML
    public void drawRelationLine(VBox fromBox, VBox toBox, String relationType) {
        if (fromBox == null || toBox == null) {
            return;
        }

        Label fromClassNameLabel = (Label) fromBox.getChildren().get(0);
        Label toClassNameLabel = (Label) toBox.getChildren().get(0);


        String fromClassName = fromClassNameLabel.getText();
        String toClassName = toClassNameLabel.getText();
        String relationshipId = fromClassName + "->" + toClassName + ":" + relationType;


        Line line = new Line();
        line.setId(relationshipId);


        line.startXProperty().bind(fromBox.layoutXProperty().add(fromBox.widthProperty()));
        line.startYProperty().bind(fromBox.layoutYProperty().add(fromBox.heightProperty().divide(2)));


        line.endXProperty().bind(toBox.layoutXProperty().subtract(20));
        line.endYProperty().bind(toBox.layoutYProperty().add(toBox.heightProperty().divide(2)));

        Polygon arrowHead = new Polygon();
        arrowHead.setId(relationshipId);


        arrowHead.getPoints().addAll(
                -20.0, 0.0,
                0.0, -10.0,
                20.0, 0.0,
                0.0, 10.0,
                -20.0, 0.0
        );

        switch (relationType) {
            case "Aggregation":
                arrowHead.setStroke(Color.BLACK);
                arrowHead.setFill(Color.TRANSPARENT);
                break;
            case "Composition":
                arrowHead.setStroke(Color.BLACK);
                arrowHead.setFill(Color.BLACK);
                break;
            case "Generalization":

                arrowHead.getPoints().clear();
                arrowHead.getPoints().addAll(
                        -20.0, 10.0,
                        0.0, 0.0,
                        -20.0, -10.0
                );
                arrowHead.setFill(Color.BLACK);
                break;
            case "Realization":

                arrowHead.getPoints().clear();
                arrowHead.getPoints().addAll(
                        -20.0, 10.0,
                        0.0, 0.0,
                        -20.0, -10.0
                );
                arrowHead.setStroke(Color.BLACK);
                arrowHead.setFill(Color.TRANSPARENT);
                line.getStrokeDashArray().addAll(10.0, 10.0);
                break;
            default:
                return;
        }

        arrowHead.layoutXProperty().bind(line.endXProperty());
        arrowHead.layoutYProperty().bind(line.endYProperty());

        canvas.getChildren().addAll(line, arrowHead);
    }


    @FXML
    public void addRelation(ActionEvent event) {
        String relationType = relationshipTypeComboBox.getValue();
        String fromClassName = fromComboBox.getValue();
        String toClassName = toComboBox.getValue();

        if (fromClassName == null || toClassName == null || fromClassName.equals(toClassName)) {
            return;
        }
        VBox fromBox = findClassName(fromClassName);
        VBox toBox = findClassName(toClassName);

        drawRelationLine(fromBox, toBox, relationType);
    }
    private void updateArrowHeadRotation(Line line, Polygon arrowHead) {
        double angle = Math.atan2(line.getEndY() - line.getStartY(), line.getEndX() - line.getStartX()) * (180 / Math.PI);
        arrowHead.setRotate(angle);
    }

    private VBox findClassName(String classBoxName) {
        for (javafx.scene.Node node : canvas.getChildren()) {
            if (node instanceof VBox) {
                VBox classBox = (VBox) node;
                 Label classNameLabel = (Label) classBox.getChildren().get(0);
                if (classNameLabel.getText().equals(classBoxName)) {
                    return classBox;
                }
            }
        }
        return null;
    }

    @FXML
    public void deleteRelation(ActionEvent event) {
        String fromClassName = fromComboBox.getValue();
        String toClassName = toComboBox.getValue();
        String relationType = relationshipTypeComboBox.getValue();

        if (fromClassName == null || toClassName == null || relationType == null) {
            return;
        }

        String relationshipId = fromClassName + "->" + toClassName + ":" + relationType;

        canvas.getChildren().removeIf(node -> relationshipId.equals(node.getId()));
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
