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
import org.w3c.dom.Text;

import java.awt.*;
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

    private VBox selectedClassBox = null;

    private ObservableList<String> classNames = FXCollections.observableArrayList();

    public UMLController umlController;


    public MainController() {
        this.umlController = new UMLController();
    }

    @FXML
    public void initialize() {

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

            fromComboBox.getItems().add(className);
            toComboBox.getItems().add(className);
            canvas.getChildren().add(classBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteClass(ActionEvent event) {
        if(selectedClassBox != null) {
            TextField className = (TextField) selectedClassBox.getChildren().get(0);
            String classNameRemove = className.getText();
            canvas.getChildren().remove(selectedClassBox);
            selectedClassBox = null;

            fromComboBox.getItems().remove(classNameRemove);
            toComboBox.getItems().remove(classNameRemove);
        }
    }

    private void selectClassBox(VBox classBox) {
        if (selectedClassBox != null) {
            selectedClassBox.setEffect(null);
        }

        selectedClassBox = classBox;

        DropShadow highlight = new DropShadow();
        highlight.setColor(Color.BLUE);
        highlight.setWidth(10);
        highlight.setHeight(10);
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
    public void handleSetClassName(ActionEvent event) {
        if (selectedClassBox != null) {
            String newName = classNameInput.getText();
            TextField className = (TextField) selectedClassBox.getChildren().get(0);
            String currentName = className.getText();
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
            String fieldName = fieldNameInput.getText();
            ListView<String> fieldList = (ListView<String>) selectedClassBox.getChildren().get(1);
            fieldList.getItems().add(dataTypeComboBox.getValue() + " " + fieldName);

            fieldNameInput.clear();
        }
    }


    @FXML
    public void handleDeleteField(ActionEvent event) {
        if (selectedClassBox != null) {
            ListView<String> fieldList = (ListView<String>) selectedClassBox.getChildren().get(1);
            String selectedField = fieldList.getSelectionModel().getSelectedItem();

            if (selectedField != null) {
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

                if (!newFieldName.isEmpty() && newFieldType != null) {
                    String updatedField = newFieldType + " " + newFieldName;
                    int selectIndex = fieldList.getSelectionModel().getSelectedIndex();
                    fieldList.getItems().set(selectIndex, updatedField);

                    fieldNameInput.clear();
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
                String formattedMethod = methodName + "()";
                ListView<String> methodList = (ListView<String>) selectedClassBox.getChildren().get(2);
                methodList.getItems().add(formattedMethod);

                methodNameInput.clear();
            }
        }
    }


    @FXML
    public void handleDeleteMethod(ActionEvent event) {
        if (selectedClassBox != null) {
            ListView<String> methodList = (ListView<String>) selectedClassBox.getChildren().get(2);
            String selectedMethod = methodList.getSelectionModel().getSelectedItem();

            if (selectedMethod != null) {
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
        String fromClassName = ((TextField) fromBox.getChildren().get(0)).getText();
        String toClassName = ((TextField) toBox.getChildren().get(0)).getText();
        String relationshipId = fromClassName + "->" + toClassName + ":" + relationType;

        Line line = new Line();


        line.startXProperty().bind(fromBox.layoutXProperty().add(fromBox.widthProperty()));
        line.startYProperty().bind(fromBox.layoutYProperty().add(fromBox.heightProperty().divide(2)));

        line.endXProperty().bind(toBox.layoutXProperty().subtract(20));
        line.endYProperty().bind(toBox.layoutYProperty().add(toBox.heightProperty().divide(2)));

        Polygon arrowHead = new Polygon();
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
                line.endXProperty().bind(toBox.layoutXProperty().subtract(40));
                line.endYProperty().bind(toBox.layoutYProperty().add(toBox.heightProperty().divide(2)));

                arrowHead.layoutXProperty().bind(line.endXProperty().add(arrowHead.getBoundsInLocal().getWidth() / 2));
                arrowHead.layoutYProperty().bind(line.endYProperty());
                break;
            case "Composition":
                arrowHead.setStroke(Color.BLACK);
                arrowHead.setFill(Color.BLACK);
                line.endXProperty().bind(toBox.layoutXProperty().subtract(40)); // Adjust this number to control how far left the arrowhead moves
                line.endYProperty().bind(toBox.layoutYProperty().add(toBox.heightProperty().divide(2)));

                arrowHead.layoutXProperty().bind(line.endXProperty().add(arrowHead.getBoundsInLocal().getWidth() / 2));
                arrowHead.layoutYProperty().bind(line.endYProperty());
                break;
            case "Generalization":
                arrowHead.getPoints().clear();
                arrowHead.getPoints().addAll(
                        -20.0, 10.0,
                        0.0, 0.0,
                        -20.0, -10.0
                );
                arrowHead.setFill(Color.BLACK);

                line.endXProperty().bind(toBox.layoutXProperty().subtract(0));
                line.endYProperty().bind(toBox.layoutYProperty().add(toBox.heightProperty().divide(2)));

                arrowHead.layoutXProperty().bind(line.endXProperty());
                arrowHead.layoutYProperty().bind(line.endYProperty());
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

                line.endXProperty().bind(toBox.layoutXProperty().subtract(0));
                line.endYProperty().bind(toBox.layoutYProperty().add(toBox.heightProperty().divide(2)));

                arrowHead.layoutXProperty().bind(line.endXProperty());
                arrowHead.layoutYProperty().bind(line.endYProperty());
                break;
            default:
                return;
        }
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
                TextField className = (TextField) classBox.getChildren().get(0);
                if (className.getText().equals(classBoxName)) {
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

        if (fromClassName == null || toClassName == null) {
            return;
        }

        String relationshipId = fromClassName + "->" + toClassName;

        canvas.getChildren().removeIf(node -> relationshipId.equals(node.getId()));
    }
}
