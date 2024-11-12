package org.project.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.project.Model.UMLModel;
import org.project.View.ClassBox;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class GUIViewController  implements Initializable {

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
  private MenuBar menuBar;

  @FXML
  private MenuItem saveButton;

  @FXML
  private  MenuItem openButton;

  private ClassBox selectedClassBox = null;

  public UMLController umlController;

  FileChooser fileChooser = new FileChooser();


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    String home = System.getProperty("user.home");
    fileChooser.setInitialDirectory(new File(home));
  }

  /**
   * This is the constructor for GUIViewController.
   * Initializes UMLController
   */
  public GUIViewController() {
    this.umlController = new UMLController();
  }



  /**
   * This method creates a Class object with a default name of "Class Name #"
   * @param event - When the "Create Class" button is clicked
   */
  public void createClass(ActionEvent event) {

    ClassBox classBox = new ClassBox(
      "New Class #" + (umlController.getStorage().getClasses().size() + 1));
    classBox.setOnMouseClicked(e -> selectClassBox(classBox));

    // Calculate the center of the canvas
    double centerX = (canvas.getWidth() - classBox.getPrefWidth()) / 2;
    double centerY = (canvas.getHeight() - classBox.getPrefHeight()) / 2;

    // Set the position of the classBox to the center of the canvas
    classBox.setLayoutX(centerX);
    classBox.setLayoutY(centerY);

    fromComboBox.getItems().add(classBox.getName());
    toComboBox.getItems().add(classBox.getName());
    dataTypeComboBox.getItems().add(classBox.getName());
    parameterTypeComboBox.getItems().add(classBox.getName());
    canvas.getChildren().add(classBox);

    umlController.classCommands.addClass(new String[]{"add", "class", classBox.getName()});
    System.out.println("Size: " + umlController.getStorage().getClasses().size());
  }


  /**
   * This method deletes the class from the model and gui view
   * @param event - Represents the action of the button being clicked
   */
  @FXML
  public void deleteClass(ActionEvent event) {
    if (selectedClassBox != null) {
      Label className = (Label) selectedClassBox.getChildren().get(0);
      String classNameRemove = className.getText();

      String message = umlController.classCommands.removeClass(
        new String[]{"remove", "class", classNameRemove});

      
      if (message == null || message.isEmpty()) {
        // Remove relationships involving the class
        canvas.getChildren().removeIf(node -> {
          String nodeId = node.getId();
          return nodeId != null && (nodeId.startsWith(classNameRemove + "->") || nodeId.contains("->" + classNameRemove));
        });

        // GUI update
        canvas.getChildren().remove(selectedClassBox);
        selectedClassBox = null;

        fromComboBox.getItems().remove(classNameRemove);
        toComboBox.getItems().remove(classNameRemove);
        dataTypeComboBox.getItems().remove(classNameRemove);
        parameterTypeComboBox.getItems().remove(classNameRemove);


      } else {
        showAlert("Class", message);
      }
    }
  }

  /**
   *  This method saves the box that was last selected by the user
   * @param classBox - Representing the box that was selected
   */
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

  /**
   * When the hide/show button is clicked, depending on the status of the inspector. It will hide and show the inspector
   * @param event - Representing the action of the button being clicked
   */
  @FXML
  public void toggleInspector(ActionEvent event) {
    boolean isInspectorVisible = inspectorPane.isVisible();
    inspectorPane.setVisible(!isInspectorVisible);
    toggleInspectorButton.setText(isInspectorVisible ? "Show" : "Hide");
  }

  /**
   * When the "Set Class Name" button is clicked. It will grab the input box text and set that to be the new class name.
   * @param event - Representing the action of the button being clicked
   */

  @FXML
  public void handleSetClassName(ActionEvent event) {
    if (selectedClassBox != null) {
      String newName = classNameInput.getText();
      if (umlController.getStorage().hasClass(newName)) {
        showAlert("Class Creation Error",
          "A class with the name \"" + newName + "\" already exists.");
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
      int dataTypeIndex = dataTypeComboBox.getItems().indexOf(currentName);
      int paramTypeIndex = parameterTypeComboBox.getItems().indexOf(currentName);

      if (fromIndex >= 0) {
        fromComboBox.getItems().set(fromIndex, newName);
      }
      if (toIndex >= 0) {
        toComboBox.getItems().set(toIndex, newName);
      }
      if (dataTypeIndex >= 0) {
        dataTypeComboBox.getItems().set(dataTypeIndex, newName);
      }
      if (paramTypeIndex >=0){
        parameterTypeComboBox.getItems().set(paramTypeIndex, newName);
      }

      // Update relationships
      canvas.getChildren().forEach(node -> {
        String nodeId = node.getId();
        if (nodeId != null && (nodeId.startsWith(currentName + "->") || nodeId.contains("->" + currentName))) {
          String newNodeId = nodeId.replace(currentName, newName);
          node.setId(newNodeId);
        }
      });
    }
  }

  /**
   * When the "Add Field" button is clicked, it will grab the text inside the field text box and add it to the selected Class
   * @param event - Representing the action of the button being clicked
   */
  @FXML
  public void handleAddField(ActionEvent event) {
    if (selectedClassBox != null) {

      if (dataTypeComboBox.getValue() == null) {
        showAlert("Error", "Chose the type of field");
        return;
      }

      String fieldName = fieldNameInput.getText();
      ListView<String> fieldList = (ListView<String>) selectedClassBox.getChildren().get(1);
      String message = umlController.fieldCommands.addField(
        new String[]{"add", "field", selectedClassBox.getName(), fieldName,
          dataTypeComboBox.getValue()});

      if (message.isEmpty()) {
        fieldNameInput.clear();
        fieldList.getItems().add(dataTypeComboBox.getValue() + " " + fieldName);
      } else {
        showAlert("Error", message);
      }
      System.out.println(
        umlController.getStorage().getClass(selectedClassBox.getName()).fields.toString());


    }
  }


  /**
   * Inside the selected class the user will have a field selected. When the user clicks on "Delete Field" the selected field will then be deleted.
   * @param event - Representing the action of the event being pressed
   */
  @FXML
  public void handleDeleteField(ActionEvent event) {
    if (selectedClassBox != null) {
      ListView<String> fieldList = (ListView<String>) selectedClassBox.getChildren().get(1);
      String selectedField = fieldList.getSelectionModel().getSelectedItem();
      String fieldType = selectedField.split(" ")[0].toLowerCase().trim();
      String fieldName = selectedField.split(" ")[1].toLowerCase().trim();

      if (selectedField != null) {
        umlController.fieldCommands.removeField(
          new String[]{"add", "field", selectedClassBox.getName(), fieldName,
            fieldType}); // "add field [class name] [field name] [field type]"
        fieldList.getItems().remove(selectedField);
      }
    }
  }


  /**
   * Inside the selected class the user has a field selected. When the user clicks "Rename Field" button this method will gave
   * the text inside the field box and rename the field
   * @param event - Representing the action that the button is clicked
   */
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

          // update storage
          String oldFieldName = selectedField.split(" ")[1].toLowerCase().trim();
          umlController.fieldCommands.renameField(
            new String[]{"rename", "field", selectedClassBox.getName(), oldFieldName, newFieldName,
              newFieldType});
          System.out.println(
            umlController.getStorage().getClass(selectedClassBox.getName()).fields.toString());
          System.out.println("Size of fields: " + umlController.getStorage()
            .getClass(selectedClassBox.getName()).fields.size());
        }
      }
    }
  }


  /**
   * When the user has a class selected and a method in the class selected. When the user clicks on "Add Parameter". It wil grab the input in the field method
   * and add it the selected method in the selected class.
   * @param event - Representing the action that the button is clicked
   */
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
            currentMethod = currentMethod.replace("()",
              "(" + parameterType + " " + parameterName + ")");
          } else {
            currentMethod = currentMethod.replace(")",
              ", " + parameterType + " " + parameterName + ")");
          }

          parameterNameInput.clear();
          methodList.getItems().set(lastMethodIndex, currentMethod);

        }
      }
    }
  }

  /**
   * Adds a new method to the selected class box
   * @param event the action event triggered by clicking on the add method button.
   */
  @FXML
  public void handleAddMethod(ActionEvent event) {
    if (selectedClassBox != null) {
      String methodName = methodNameInput.getText();

      if (!methodName.isEmpty()) {

        String message = umlController.methodCommands.addMethod(
          new String[]{"add", "method", selectedClassBox.getName(), methodName});
        if (message.isEmpty()) {
          String formattedMethod = methodName + "()";
          ListView<String> methodList = (ListView<String>) selectedClassBox.getChildren().get(2);
          methodList.getItems().add(formattedMethod);

          methodNameInput.clear();
        } else {
          showAlert("Error", message);
        }


      }
    }
  }

  /**
   * Deletes selected method from the selected class box.
   * @param event the action event is triggered by clicking on the delete method button
   */
  @FXML
  public void handleDeleteMethod(ActionEvent event) {
    if (selectedClassBox != null) {
      ListView<String> methodList = (ListView<String>) selectedClassBox.getChildren().get(2);
      String selectedMethod = methodList.getSelectionModel().getSelectedItem();

      if (selectedMethod != null) {

        umlController.methodCommands.removeMethod(
          new String[]{"delete", "method", selectedClassBox.getName(), selectedMethod});
        System.out.println("Number of methods: " + umlController.getStorage()
          .getClass(selectedClassBox.getName()).fields.size());
        methodList.getItems().remove(selectedMethod);

        methodNameInput.clear();
      }
    }
  }

  /**
   * Renames the selected method in the selected class box.
   * @param event the action event triggered by renaming a method
   */
  @FXML
  public void handleRenameMethod(ActionEvent event) {
    if (selectedClassBox != null) {
      ListView<String> methodList = (ListView<String>) selectedClassBox.getChildren().get(2);
      String selectedMethod = methodList.getSelectionModel().getSelectedItem();

      if (selectedMethod != null) {
        String newMethodName = methodNameInput.getText();

        if (!newMethodName.isEmpty()) {
          String oldMethodName = selectedMethod.substring(0, selectedMethod.indexOf("("));

          umlController.methodCommands.renameMethod(
            new String[]{"rename", "method", selectedClassBox.getName(), oldMethodName,
              newMethodName});

          int selectedIndex = methodList.getSelectionModel().getSelectedIndex();
          String updatedMethod =
            newMethodName + selectedMethod.substring(selectedMethod.indexOf('('));
          methodList.getItems().set(selectedIndex, updatedMethod);

          methodNameInput.clear();
        }
      }
    }
  }

  /**
   * Draws a relationship line with an arrowhead between two class boxes,
   * based on the selected relationship type.
   * @param fromBox the VBox representing the source class box
   * @param toBox the Vbox representing the destination class box
   * @param relationType the type of relationship (Aggregation, Composition, Generalization, Realization)
   */
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

    // Remove the existing relationship if it exists
    canvas.getChildren().removeIf(node -> {
      String nodeId = node.getId();
      return (nodeId != null && (nodeId.startsWith(fromClassName + "->" + toClassName) || nodeId.startsWith(toClassName + "->" + fromClassName)));
    });

   Line line = createAndBindLine(fromBox, toBox, relationshipId);

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
        arrowHead.setFill(Color.GRAY);
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
        arrowHead.setFill(Color.GRAY);
        line.getStrokeDashArray().addAll(10.0, 10.0);
        break;
      default:
        return;
    }

    arrowHead.layoutXProperty().bind(line.endXProperty());
    arrowHead.layoutYProperty().bind(line.endYProperty());

    canvas.getChildren().addAll(line, arrowHead);
  }

    /**
     * Returns the top, bottom, left, and right points of the class box.
     * @param box the VBox representing the class box
     * @return the points of the class box
     */
    private DoubleBinding[][] classBoxPoints(VBox box) {
        DoubleBinding topX = Bindings.createDoubleBinding(() -> box.getLayoutX() + box.getWidth() / 2, box.layoutXProperty(), box.widthProperty());
        DoubleBinding topY = Bindings.createDoubleBinding(() -> box.getLayoutY(), box.layoutYProperty());
        DoubleBinding bottomX = Bindings.createDoubleBinding(() -> box.getLayoutX() + box.getWidth() / 2, box.layoutXProperty(), box.widthProperty());
        DoubleBinding bottomY = Bindings.createDoubleBinding(() -> box.getLayoutY() + box.getHeight(), box.layoutYProperty(), box.heightProperty());
        DoubleBinding leftX = Bindings.createDoubleBinding(() -> box.getLayoutX(), box.layoutXProperty());
        DoubleBinding leftY = Bindings.createDoubleBinding(() -> box.getLayoutY() + box.getHeight() / 2, box.layoutYProperty(), box.heightProperty());
        DoubleBinding rightX = Bindings.createDoubleBinding(() -> box.getLayoutX() + box.getWidth(), box.layoutXProperty(), box.widthProperty());
        DoubleBinding rightY = Bindings.createDoubleBinding(() -> box.getLayoutY() + box.getHeight() / 2, box.layoutYProperty(), box.heightProperty());

        return new DoubleBinding[][]{{topX, topY}, {bottomX, bottomY}, {leftX, leftY}, {rightX, rightY}};
    }


    /**
     * Creates a line between two class boxes and binds the line to the class boxes.
     * @param fromBox the VBox representing the source class box
     * @param toBox the VBox representing the destination class box
     * @param relationshipId the id of the relationship
     * @return the line between the two class boxes
     */
    private Line createAndBindLine(VBox fromBox, VBox toBox, String relationshipId) {
        Line line = new Line();
        line.setId(relationshipId);

        DoubleBinding[][] fromPoints = classBoxPoints(fromBox);
        DoubleBinding[][] toPoints = classBoxPoints(toBox);

        DoubleProperty startX = new SimpleDoubleProperty();
        DoubleProperty startY = new SimpleDoubleProperty();
        DoubleProperty endX = new SimpleDoubleProperty();
        DoubleProperty endY = new SimpleDoubleProperty();

        Runnable updateLine = () -> {
            double minDistance = Double.MAX_VALUE;
            for (DoubleBinding[] fromPoint : fromPoints) {
                for (DoubleBinding[] toPoint : toPoints) {
                    double distance = Math.sqrt(Math.pow(fromPoint[0].get() - toPoint[0].get(), 2) + Math.pow(fromPoint[1].get() - toPoint[1].get(), 2));
                    if (distance < minDistance) {
                        minDistance = distance;
                        startX.set(fromPoint[0].get());
                        startY.set(fromPoint[1].get());
                        endX.set(toPoint[0].get());
                        endY.set(toPoint[1].get());
                    }
                }
            }
            line.startXProperty().bind(startX);
            line.startYProperty().bind(startY);
            line.endXProperty().bind(endX);
            line.endYProperty().bind(endY);
        };

        fromBox.layoutXProperty().addListener((obs, oldVal, newVal) -> updateLine.run());
        fromBox.layoutYProperty().addListener((obs, oldVal, newVal) -> updateLine.run());
        toBox.layoutXProperty().addListener((obs, oldVal, newVal) -> updateLine.run());
        toBox.layoutYProperty().addListener((obs, oldVal, newVal) -> updateLine.run());

        updateLine.run();
        return line;
    }

  /**
   * Adds a relationship line between two selected classes,
   * based on the selected relationship type and class names.
   * @param event the action event triggered by clicking the add relationship button
   */
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

  /**
   * Finds and returns a class box by its name.
   * @param classBoxName the name of the class box to find
   * @return the VBox representing the class box with the specified name, or null if not found
   */
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

  /**
   * Deletes the selected relationship between two classes from the canvas,
   * based on the selected classes and relationship type in the "From" and "To" Combobox.
   * @param event the action even triggered by clicking the delete relationship button
   */
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

  /**
   * Displays an alert pop up with a title and content.
   * @param title the title of the alert pop up
   * @param content the message content to be displayed in the alert
   */
  private void showAlert(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }


  private String getFileName(){
    String filename = "";
    TextInputDialog dialog = new TextInputDialog("Enter File Name");
    dialog.setTitle("Enter File Name");
    dialog.setContentText("Enter A file name: ");
    Optional<String> result = dialog.showAndWait();
    return result.orElse("");
  }

  /**
   * Exits the program when the exit button is clicked.
   * @param event the action event for exiting the program
   */
  @FXML
  public void exitProgram(ActionEvent event) {
    System.exit(0);
  }

  @FXML
  public void onSave(ActionEvent event){
    String fileName = getFileName();

  }

  /**
   * When the open button is clicked it will ask the user to choose a file they like to upload.
   * *It will only allow them to enter a .json file*
   * @param event - The action for opening a file.
   */
  @FXML
  public void onOpen(ActionEvent event){
    FileChooser.ExtensionFilter jsonFilter =  new FileChooser.ExtensionFilter("Json Files", "*.json");
    fileChooser.getExtensionFilters().add(jsonFilter);

    File file = fileChooser.showOpenDialog(new Stage());
  }

}
