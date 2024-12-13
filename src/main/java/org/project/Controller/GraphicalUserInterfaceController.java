package org.project.Controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import org.project.Model.CommandLogic;
import org.project.Model.CommandRegistries;
import org.project.Model.DataStorage;
import org.project.Model.UMLClassNode;
import org.project.View.GraphicalClassNode;
import org.project.View.GraphicalClassNodeFactory;

public class GraphicalUserInterfaceController implements Initializable {

  private static final Logger LOGGER =
      Logger.getLogger(GraphicalUserInterfaceController.class.getName());

  @FXML public Menu menubar;
  @FXML public MenuItem loadButton;
  @FXML public Button deleteClassButton;
  @FXML public Button addClass;
  @FXML public Button setClassName;
  @FXML public Button renameFieldButton;
  @FXML public Button addField;
  @FXML public Button deleteField;
  @FXML public Button deleteMethod;
  @FXML public Button renameMethodButton;
  @FXML public Button addParameter;
  @FXML public Button addRelationButton;
  @FXML public Button deleteRelationButton;
  @FXML public ComboBox<String> methodTypeComboBox;
  @FXML public Button deleteParameter;
  @FXML public Button renameParameter;
  @FXML private VBox inspectorPane;
  @FXML private Button toggleInspectorButton;
  @FXML private TextField classNameInput;
  @FXML private TextField fieldNameInput;
  @FXML private ComboBox<String> dataTypeComboBox;
  @FXML private TextField methodNameInput;
  @FXML private TextField parameterNameInput;
  @FXML private ComboBox<String> parameterTypeComboBox;
  @FXML private ComboBox<String> fromComboBox;
  @FXML private ComboBox<String> toComboBox;
  @FXML private ComboBox<String> relationshipTypeComboBox;

  public MenuItem openButton;
  public MenuItem newProjectButton;
  public MenuItem saveButton;
  public Button addMethod;

  private final ObservableClass observableClass = new ObservableClass();
  private final List<String> defaultTypes = Arrays.asList("Boolean", "Double", "String", "Int");
  private GraphicalClassNode selectedGraphicalClassNode = null;
  private final CommandBridge commandBridge;
  public static final DataStorage DATA_STORAGE = DataStorage.getInstance();
  FileChooser fileChooser = new FileChooser();

  @FXML private ScrollPane scrollPane;
  @FXML private Group zoomGroup;
  @FXML private Pane canvas;
  @FXML private Label zoomLabel;

  private double zoomFactor = 1.0;
  private static final double ZOOM_INCREMENT = 0.1;
  private static final double MIN_ZOOM = 0.5;
  private static final double MAX_ZOOM = 3.0;
  private boolean draggingBackground = false;

  private DraggableMaker draggableMaker;

  /**
   * This sets the default path to be the user's home directory.
   *
   * @param url - These are not setup
   * @param resourceBundle - Not setup
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    draggableMaker = new DraggableMaker(canvas);
    scrollPane.setOnMousePressed(event -> draggingBackground = true);
    scrollPane.setOnMouseReleased(event -> draggingBackground = false);
    scrollPane.setPannable(true);

    String home = System.getProperty("user.home"); // This could be changed later
    fileChooser.setInitialDirectory(new File(home));

    //    canvas.setStyle("-fx-background-color: black;");
    List<ComboBox<String>> comboBoxList =
        Arrays.asList(dataTypeComboBox, parameterTypeComboBox, methodTypeComboBox);
    List<ComboBox<String>> classOnlyBoxes = Arrays.asList(fromComboBox, toComboBox);
    ComboBoxObserver comboBoxObserver =
        new ComboBoxObserver(comboBoxList, observableClass, defaultTypes);
    ComboBoxObserver classOnlyObserver =
        new ComboBoxObserver(classOnlyBoxes, observableClass, null);
    observableClass.addObserver(comboBoxObserver);
    observableClass.addObserver(classOnlyObserver);
    for (ComboBox<String> comboBox : comboBoxList) {
      comboBox.getItems().addAll(defaultTypes);
    }

    // Add zoom functionality
    canvas.setOnScroll(
        event -> {
          if (event.getDeltaY() > 0) { // Scroll up to zoom in
            zoomFactor = Math.min(zoomFactor + ZOOM_INCREMENT, MAX_ZOOM);
          } else { // Scroll down to zoom out
            zoomFactor = Math.max(zoomFactor - ZOOM_INCREMENT, MIN_ZOOM);
          }

          // Apply the zoom factor
          zoomGroup.setScaleX(zoomFactor);
          zoomGroup.setScaleY(zoomFactor);

          // Update the zoom label
          zoomLabel.setText(String.format("Zoom: %.0f%%", zoomFactor * 100));

          event.consume();
        });

    scrollPane.setPannable(true);

    zoomLabel.setText("Zoom: 100%");
  }

  public GraphicalUserInterfaceController() {
    CommandLogic commandLogic = CommandRegistries.getInstance("CLICommands.json");
    this.commandBridge = new CommandBridgeAdapter(commandLogic);
  }

  private String[] getInspectorValues() {
    String className = classNameInput.getText();
    String fieldName = fieldNameInput.getText();
    String fieldType = dataTypeComboBox.getValue();
    String methodName = methodNameInput.getText();
    String methodType = methodTypeComboBox.getValue();
    String parameterName = parameterNameInput.getText();
    String parameterType = parameterTypeComboBox.getValue();
    return new String[] {
      className, fieldName, fieldType, methodType, methodName, parameterName, parameterType
    };
  }

  /**
   * This method creates a Class object with a default name of "Class Name #"
   *
   * @param event - When the "Create Class" button is clicked
   */
  public void createClass(ActionEvent event) {
    String[] inspectorValues = getInspectorValues();
    String className =
        inspectorValues[0].isEmpty()
            ? "New_Class_" + (canvas.getChildren().size() + 1)
            : inspectorValues[0];

    // Create the class and retrieve its node
    CommandResult result =
        commandBridge.createClass(new String[] {className}); // Updates DATA_STORAGE
    UMLClassNode node1 = commandBridge.getStorage().getNode(className);

    if (result.isSuccess()) {
      result = commandBridge.switchClass(new String[] {className});

      if (result.isSuccess()) {
        // Create and configure the GraphicalClassNode
        GraphicalClassNode graphicalClassNode =
            GraphicalClassNodeFactory.createClassBox(
                CommandLogic.getStorage().getNode(className),
                inspectorValues,
                commandBridge,
                canvas);

        graphicalClassNode.setOnMouseClicked(e -> selectClassBox(graphicalClassNode));

        // Calculate position
        int numberOfClasses =
            (int)
                canvas.getChildren().stream()
                    .filter(node -> node instanceof GraphicalClassNode)
                    .count();
        double spacing = 20.0;
        double offsetX = (numberOfClasses % 5) * (graphicalClassNode.getPrefWidth() + spacing);
        double offsetY = (numberOfClasses / 5) * (graphicalClassNode.getPrefHeight() + spacing);
        double startX = 50.0; // Starting X position
        double startY = 50.0; // Starting Y position
        node1.setPosition(startX + offsetX, startY + offsetY);
        graphicalClassNode.setLayoutX(startX + offsetX);
        graphicalClassNode.setLayoutY(startY + offsetY);

        // Make the node draggable and link it with the UML node
        draggableMaker.makeDraggable(graphicalClassNode, node1, () -> draggingBackground);

        // Add the node to the canvas
        canvas.getChildren().add(graphicalClassNode);
        observableClass.addClassBox(graphicalClassNode);
      } else {
        showAlert("Error", result.getMessage());
      }
    }
  }

  /**
   * This method deletes the class from the model and gui view
   *
   * @param event - Represents the action of the button being clicked
   */
  @FXML
  public void deleteClass(ActionEvent event) {
    if (selectedGraphicalClassNode != null) {
      Label className = (Label) selectedGraphicalClassNode.getChildren().get(0);
      String classNameRemove = className.getText();

      CommandResult result = commandBridge.removeClass(new String[] {classNameRemove});

      if (result.isSuccess()) {
        // Remove relationships involving the class
        canvas
            .getChildren()
            .removeIf(
                node -> {
                  String nodeId = node.getId();
                  return nodeId != null
                      && (nodeId.startsWith(classNameRemove + "->")
                          || nodeId.contains("->" + classNameRemove));
                });

        // GUI update
        canvas.getChildren().remove(selectedGraphicalClassNode);
        observableClass.removeClasBox(selectedGraphicalClassNode);
        selectedGraphicalClassNode = null;
        classNameInput.clear();

      } else {
        showAlert("Class Deletion Error", result.getMessage());
      }
    }
  }

  /**
   * This method saves the box that was last selected by the user
   *
   * @param graphicalClassNode - Representing the box that was selected
   */
  private void selectClassBox(GraphicalClassNode graphicalClassNode) {

    if (selectedGraphicalClassNode != null
        && selectedGraphicalClassNode.equals(graphicalClassNode)) {
      selectedGraphicalClassNode.setEffect(null);
      selectedGraphicalClassNode = null;
      classNameInput.clear();
    } else {
      if (selectedGraphicalClassNode != null) {
        selectedGraphicalClassNode.setEffect(null);
      }

      selectedGraphicalClassNode = graphicalClassNode;

      DropShadow highlight = new DropShadow();
      highlight.setColor(Color.RED);
      highlight.setWidth(120);
      highlight.setHeight(120);
      selectedGraphicalClassNode.setEffect(highlight);

      Label classNameLabel = (Label) selectedGraphicalClassNode.getChildren().get(0);
      classNameInput.setText(classNameLabel.getText());
      commandBridge.switchClass(new String[] {graphicalClassNode.getName()});
    }
  }

  /**
   * When the hide/show button is clicked, depending on the status of the inspector. It will hide
   * and show the inspector
   *
   * @param event - Representing the action of the button being clicked
   */
  @FXML
  public void toggleInspector(ActionEvent event) {
    boolean isInspectorVisible = inspectorPane.isVisible();
    inspectorPane.setVisible(!isInspectorVisible);
    toggleInspectorButton.setText(isInspectorVisible ? "Show" : "Hide");
  }

  /**
   * When the "Set Class Name" button is clicked. It will grab the input box text and set that to be
   * the new class name.
   *
   * @param event - Representing the action of the button being clicked
   */
  @FXML
  public void handleSetClassName(ActionEvent event) {
    if (selectedGraphicalClassNode != null) {
      String newName = classNameInput.getText();
      Label className = (Label) selectedGraphicalClassNode.getChildren().get(0);
      String currentName = className.getText();

      // Rename class in DATA_STORAGE
      CommandResult result = commandBridge.renameClass(new String[] {currentName, newName});
      if (!result.isSuccess()) {
        showAlert("Class Rename Error", result.getMessage());
        return;
      }

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
      if (paramTypeIndex >= 0) {
        parameterTypeComboBox.getItems().set(paramTypeIndex, newName);
      }

      // Update relationships
      canvas
          .getChildren()
          .forEach(
              node -> {
                String nodeId = node.getId();
                if (nodeId != null
                    && (nodeId.startsWith(currentName + "->")
                        || nodeId.contains("->" + currentName))) {
                  String newNodeId = nodeId.replace(currentName, newName);
                  node.setId(newNodeId);
                }
              });
    }
  }

  /**
   * When the "Add Field" button is clicked, it will grab the text inside the field text box and add
   * it to the selected Class
   *
   * @param event - Representing the action of the button being clicked
   */
  @FXML
  public void handleAddField(ActionEvent event) {
    if (selectedGraphicalClassNode != null) {

      if (dataTypeComboBox.getValue() == null) {
        showAlert("Error", "Choose the type of field");
        return;
      }

      String fieldName = fieldNameInput.getText();
      String fieldType = dataTypeComboBox.getValue();
      ListView<String> fieldList =
          (ListView<String>) selectedGraphicalClassNode.getChildren().get(1);
      CommandResult result = commandBridge.addField(new String[] {fieldType, fieldName});

      if (result.isSuccess()) {
        fieldNameInput.clear();
        fieldList.getItems().add(fieldType + " " + fieldName);
      } else {
        showAlert("Error", result.getMessage());
      }
    }
  }

  /**
   * Inside the selected class the user will have a field selected. When the user clicks on "Delete
   * Field" the selected field will then be deleted.
   *
   * @param event - Representing the action of the event being pressed
   */
  @FXML
  public void handleDeleteField(ActionEvent event) {
    if (selectedGraphicalClassNode != null) {
      ListView<String> fieldList =
          (ListView<String>) selectedGraphicalClassNode.getChildren().get(1);
      String selectedField = fieldList.getSelectionModel().getSelectedItem();

      if (selectedField == null) {
        showAlert("Error", "Select a field to delete");
        return;
      }

      // Extract the field name from the selected field string
      String fieldName = selectedField.split(" ")[1];
      CommandResult result = commandBridge.removeField(new String[] {fieldName});

      if (result.isSuccess()) {
        fieldList.getItems().remove(selectedField);
      } else {
        showAlert("Error", result.getMessage());
      }
    } else {
      showAlert("Error", "Select a class to delete a field from");
    }
  }

  /**
   * Inside the selected class the user has a field selected. When the user clicks "Rename Field"
   * button this method will give the text inside the field box and rename the field
   *
   * @param event - Representing the action that the button is clicked
   */
  @FXML
  public void handleRenameField(ActionEvent event) {
    if (selectedGraphicalClassNode != null) {
      ListView<String> fieldList =
          (ListView<String>) selectedGraphicalClassNode.getChildren().get(1);
      String selectedField = fieldList.getSelectionModel().getSelectedItem();

      if (selectedField != null) {
        String newFieldName = fieldNameInput.getText();
        String newFieldType = dataTypeComboBox.getValue();

        if (!newFieldName.isEmpty() && newFieldType != null) {
          String updatedField = newFieldType + " " + newFieldName;
          int selectIndex = fieldList.getSelectionModel().getSelectedIndex();
          fieldList.getItems().set(selectIndex, updatedField);

          fieldNameInput.clear();

          // Extract the old field name from the selected field string
          String oldFieldName = selectedField.split(" ")[1];
          CommandResult result =
              commandBridge.renameField(new String[] {oldFieldName, newFieldName, newFieldType});

          if (!result.isSuccess()) {
            showAlert("Error", result.getMessage());
          }
        }
      }
    }
  }

  /**
   * When the user has a class selected and a method in the class selected. When the user clicks on
   * "Add Parameter". It wil grab the input in the field method and add it the selected method in
   * the selected class.
   *
   * @param event - Representing the action that the button is clicked
   */
  @FXML
  public void handleAddParameter(ActionEvent event) {
    if (selectedGraphicalClassNode != null) {
      ListView<String> methodList =
          (ListView<String>) selectedGraphicalClassNode.getChildren().get(2);

      if (!methodList.getItems().isEmpty()) {
        String parameterName = parameterNameInput.getText();
        String parameterType = parameterTypeComboBox.getValue();

        if (!parameterName.isEmpty() && parameterType != null) {
          int selectMethodIndex = methodList.getSelectionModel().getSelectedIndex();
          if (selectMethodIndex >= 0) {
            String currentMethod = methodList.getItems().get(selectMethodIndex);

            if (currentMethod.endsWith("()")) {
              currentMethod =
                  currentMethod.replace("()", "(" + parameterType + " " + parameterName + ")");
            } else {
              currentMethod =
                  currentMethod.replace(")", ", " + parameterType + " " + parameterName + ")");
            }

            parameterNameInput.clear();
            methodList.getItems().set(selectMethodIndex, currentMethod);
          }
        }
      }
    }
  }

  /**
   * Adds a new method to the selected class box
   *
   * @param event the action event triggered by clicking on the add method button.
   */
  @FXML
  public void handleAddMethod(ActionEvent event) {

    if (selectedGraphicalClassNode != null) {
      String methodName = methodNameInput.getText();
      String returnType = methodTypeComboBox.getValue();

      if (!methodName.isEmpty()) {

        CommandResult result = commandBridge.addMethod(new String[] {returnType, methodName});

        if (result.isSuccess()) {
          String formattedMethod = returnType + " " + methodName + "()";
          ListView<String> methodList =
              (ListView<String>) selectedGraphicalClassNode.getChildren().get(2);
          methodList.getItems().add(formattedMethod);

        } else {
          showAlert("Error", result.getMessage());
        }

        methodNameInput.clear();
      }
    }
  }

  /**
   * Deletes selected method from the selected class box.
   *
   * @param event the action event is triggered by clicking on the delete method button
   */
  @FXML
  public void handleDeleteMethod(ActionEvent event) {
    if (selectedGraphicalClassNode != null) {
      ListView<String> methodList =
          (ListView<String>) selectedGraphicalClassNode.getChildren().get(2);
      String selectedMethod = methodList.getSelectionModel().getSelectedItem();

      if (selectedMethod != null) {
        String methodName = selectedMethod.split(" ")[1];
        CommandResult result = commandBridge.removeMethod(new String[] {methodName});

        if (result.isSuccess()) {
          methodList.getItems().remove(selectedMethod);
        } else {
          showAlert("Error", result.getMessage());
        }
        methodNameInput.clear();
      }
    }
  }

  /**
   * Renames the selected method in the selected class box.
   *
   * @param event the action event triggered by renaming a method
   */
  @FXML
  public void handleRenameMethod(ActionEvent event) { // TODO: delete or not to delete?
    //    if (selectedGraphicalClassNode != null) {
    //      ListView<String> methodList =
    //          (ListView<String>) selectedGraphicalClassNode.getChildren().get(2);
    //      String selectedMethod = methodList.getSelectionModel().getSelectedItem();
    //
    //      if (selectedMethod != null) {
    //        String newMethodName = methodNameInput.getText();
    //
    //        if (!newMethodName.isEmpty()) {
    //          String oldMethodName = selectedMethod.substring(0, selectedMethod.indexOf("("));
    //
    //          umlController.methodCommands.renameMethod(
    //              new String[] {
    //                "rename",
    //                "method",
    //                selectedGraphicalClassNode.getName(),
    //                oldMethodName,
    //                newMethodName
    //              });
    //
    //          int selectedIndex = methodList.getSelectionModel().getSelectedIndex();
    //          String updatedMethod =
    //              newMethodName + selectedMethod.substring(selectedMethod.indexOf('('));
    //          methodList.getItems().set(selectedIndex, updatedMethod);
    //
    //          methodNameInput.clear();
    //        }
    //      }
    //    }
  }

  /**
   * Draws a relationship line with an arrowhead between two class boxes, based on the selected
   * relationship type.
   *
   * @param fromBox the VBox representing the source class box
   * @param toBox the Vbox representing the destination class box
   * @param relationType the type of relationship (Aggregation, Composition, Generalization,
   *     Realization)
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
    canvas
        .getChildren()
        .removeIf(
            node -> {
              String nodeId = node.getId();
              return (nodeId != null
                  && (nodeId.startsWith(fromClassName + "->" + toClassName)
                      || nodeId.startsWith(toClassName + "->" + fromClassName)));
            });

    Line line = createAndBindLine(fromBox, toBox, relationshipId);

    Polygon arrowHead = new Polygon();
    arrowHead.setId(relationshipId);

    ArrowStrategy arrows;
    switch (relationType) {
      case "Aggregation":
        arrows = new AggregationArrow();
        break;
      case "Composition":
        arrows = new CompositionArrow();
        break;
      case "Generalization":
        arrows = new GeneralizationArrow();
        break;
      case "Realization":
        arrows = new RealizationArrow();
        break;
      default:
        return;
    }
    arrows.drawArrow(arrowHead, line);

    arrowHead.layoutXProperty().bind(line.endXProperty());
    arrowHead.layoutYProperty().bind(line.endYProperty());

    canvas.getChildren().addAll(line, arrowHead);
  }

  /**
   * Returns the top, bottom, left, and right points of the class box.
   *
   * @param box the VBox representing the class box
   * @return the points of the class box
   */
  private DoubleBinding[][] classBoxPoints(VBox box) {
    DoubleBinding topX =
        Bindings.createDoubleBinding(
            () -> box.getLayoutX() + box.getWidth() / 2,
            box.layoutXProperty(),
            box.widthProperty());
    DoubleBinding topY =
        Bindings.createDoubleBinding(() -> box.getLayoutY(), box.layoutYProperty());
    DoubleBinding bottomX =
        Bindings.createDoubleBinding(
            () -> box.getLayoutX() + box.getWidth() / 2,
            box.layoutXProperty(),
            box.widthProperty());
    DoubleBinding bottomY =
        Bindings.createDoubleBinding(
            () -> box.getLayoutY() + box.getHeight(), box.layoutYProperty(), box.heightProperty());
    DoubleBinding leftX =
        Bindings.createDoubleBinding(() -> box.getLayoutX(), box.layoutXProperty());
    DoubleBinding leftY =
        Bindings.createDoubleBinding(
            () -> box.getLayoutY() + box.getHeight() / 2,
            box.layoutYProperty(),
            box.heightProperty());
    DoubleBinding rightX =
        Bindings.createDoubleBinding(
            () -> box.getLayoutX() + box.getWidth(), box.layoutXProperty(), box.widthProperty());
    DoubleBinding rightY =
        Bindings.createDoubleBinding(
            () -> box.getLayoutY() + box.getHeight() / 2,
            box.layoutYProperty(),
            box.heightProperty());

    return new DoubleBinding[][] {
      {topX, topY}, {bottomX, bottomY}, {leftX, leftY}, {rightX, rightY}
    };
  }

  /**
   * Creates a line between two class boxes and binds the line to the class boxes.
   *
   * @param fromBox the VBox representing the source class box
   * @param toBox the VBox representing the destination class box
   * @param relationshipId the id of the relationship
   * @return the line between the two class boxes
   */
  private Line createAndBindLine(VBox fromBox, VBox toBox, String relationshipId) {
    Line line = new Line();
    line.setId(relationshipId);
    line.setStroke(Color.WHITE);

    DoubleBinding[][] fromPoints = classBoxPoints(fromBox);
    DoubleBinding[][] toPoints = classBoxPoints(toBox);

    DoubleProperty startX = new SimpleDoubleProperty();
    DoubleProperty startY = new SimpleDoubleProperty();
    DoubleProperty endX = new SimpleDoubleProperty();
    DoubleProperty endY = new SimpleDoubleProperty();

    Runnable updateLine =
        () -> {
          double minDistance = Double.MAX_VALUE;
          for (DoubleBinding[] fromPoint : fromPoints) {
            for (DoubleBinding[] toPoint : toPoints) {
              double distance =
                  Math.sqrt(
                      Math.pow(fromPoint[0].get() - toPoint[0].get(), 2)
                          + Math.pow(fromPoint[1].get() - toPoint[1].get(), 2));
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
   * Adds a relationship line between two selected classes, based on the selected relationship type
   * and class names.
   *
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

    CommandResult result = commandBridge.addRelationship(new String[] {relationType, toClassName});

    if (result.isSuccess()) {
      VBox fromBox = findClassName(fromClassName);
      VBox toBox = findClassName(toClassName);

      drawRelationLine(fromBox, toBox, relationType);
    } else {
      showAlert("Error", result.getMessage());
    }
  }

  /**
   * Finds and returns a class box by its name.
   *
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
   * Deletes the selected relationship between two classes from the canvas, based on the selected
   * classes and relationship type in the "From" and "To" Combobox.
   *
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

    CommandResult result =
        commandBridge.removeRelationship(new String[] {relationType, toClassName});

    if (result.isSuccess()) {
      String relationshipId = fromClassName + "->" + toClassName + ":" + relationType;

      canvas.getChildren().removeIf(node -> relationshipId.equals(node.getId()));
    } else {
      showAlert("Error", result.getMessage());
    }
  }

  /**
   * Displays an alert pop up with a title and content.
   *
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

  private String getFileName() {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Enter File Name");
    dialog.setHeaderText("Export Image");
    dialog.setContentText("Enter a file name:");

    Optional<String> result = dialog.showAndWait();

    // Check if the dialog was canceled
    if (result.isEmpty()) {
      return ""; // Indicate cancellation by returning null
    }

    String filename = result.get().trim(); // Get the input and trim spaces

    // Check if the filename is blank
    if (filename.isEmpty()) {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setTitle("File Name Error");
      alert.setHeaderText("File Name cannot be blank");
      alert.showAndWait(); // Wait for the user to acknowledge
      //      return null;
    }

    // Ensure the filename does not include invalid characters
    filename = filename.replaceAll("[^a-zA-Z0-9-_\\.]", "_"); // Replace invalid characters with '_'

    return filename;
  }

  /** Exits the program when the exit button is clicked. */
  @FXML
  public void exitProgram() {
    System.exit(0);
  }

  @FXML
  public void onSave(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));

    // Set the initial directory to the user's home directory
    File initialDirectory = new File(System.getProperty("user.home"));
    if (initialDirectory.exists()) {
      fileChooser.setInitialDirectory(initialDirectory);
    }

    // Show the save dialog and get the selected file
    File file = fileChooser.showSaveDialog(new Stage());
    if (file != null) {
      String filename = file.getName();
      if (!filename.endsWith(".json")) {
        filename += ".json";
      }

      try {
        // Construct the full file path
        String jarLocation = new File(System.getProperty("user.dir")).getAbsolutePath();
        String filePath = jarLocation + File.separator + filename;

        // Use the ClassNodeService to save the data
        ClassNodeService classNodeService = new ClassNodeService();
        classNodeService.StorageSaveToJsonArray(DATA_STORAGE, filePath);

        // Show success alert
        showAlert("Success", "File saved to: " + filePath);
      } catch (Exception e) {
        // Show failure alert with exception message
        showAlert("Save Error", "Error saving file: " + e.getMessage());
      }
    } else {
      // Show error alert if no file was selected
      showAlert("Save Error", "No file selected.");
    }
  }

  /**
   * When the open button is clicked it will ask the user to choose a file they like to upload. *It
   * will only allow them to enter a .json file*
   *
   * @param event - The action for opening a file.
   */
  @FXML
  public void onOpen(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));

    // Set the initial directory to the user's home directory
    File initialDirectory = new File(System.getProperty("user.home"));
    if (initialDirectory.exists()) {
      fileChooser.setInitialDirectory(initialDirectory);
    }

    // Show the open file dialog and get the selected file
    File file = fileChooser.showOpenDialog(new Stage());
    if (file != null) {
      String fileName = file.getAbsolutePath();

      try {
        // Construct the file path and validate existence
        String jarLocation = new File(System.getProperty("user.dir")).getAbsolutePath();
        java.nio.file.Path filePath = java.nio.file.Paths.get(fileName);

        if (!Files.exists(filePath)) {
          showAlert("Load Error", "File not found: " + filePath.toAbsolutePath());
          return;
        }

        // Read the JSON file content
        String jsonContent = Files.readString(filePath);

        // Parse the JSON content into DATA_STORAGE
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(jsonContent, JsonArray.class);

        ClassNodeService classNodeService = new ClassNodeService();
        for (JsonElement element : jsonArray) {
          if (element.isJsonObject()) {
            JsonObject jsonObject = element.getAsJsonObject();
            UMLClassNode classNode = classNodeService.createClassNodeFromJson(jsonObject);
            DATA_STORAGE.addNode(classNode.getClassName(), classNode);
          } else {
            // Handle non-JsonObject cases
            System.err.println("Skipping non-JsonObject element: " + element);
          }
        }

        // Update the canvas after loading the file
        refreshCanvas();

        // Show success alert
        showAlert("Success", "File loaded from: " + filePath.toAbsolutePath());
      } catch (IOException e) {
        showAlert("Load Error", "Error reading file: " + e.getMessage());
      } catch (JsonSyntaxException e) {
        showAlert("Load Error", "Error parsing JSON: " + e.getMessage());
      } catch (Exception e) {
        showAlert("Load Error", "Unexpected error: " + e.getMessage());
      }
    } else {
      // Show error alert if no file was selected
      showAlert("Load Error", "No file selected.");
    }
  }

  @FXML
  public void onRedo(ActionEvent event) {
    CommandResult result = commandBridge.redo();
    if (result.isSuccess()) {
      refreshCanvas();
    } else {
      showAlert("Redo Failed", result.getMessage());
    }
  }

  @FXML
  public void onUndo(ActionEvent event) {
    CommandResult result = commandBridge.undo();
    if (result.isSuccess()) {
      refreshCanvas(); // Refresh the UI to reflect the restored state
    } else {
      showAlert("Redo Failed", result.getMessage());
    }
  }

  private void refreshCanvas() {
    canvas.getChildren().clear();

    int numberOfClasses = 0;
    double spacing = 20.0;
    double startX = 50.0;
    double startY = 50.0;

    for (UMLClassNode classNode : commandBridge.getStorage().getAllNodes().values()) {
      String className = classNode.getClassName();

      // Pass canvas as an additional argument
      GraphicalClassNode graphicalClassNode =
          GraphicalClassNodeFactory.createClassBox(classNode, commandBridge, canvas);

      graphicalClassNode.setOnMouseClicked(e -> selectClassBox(graphicalClassNode));
      double[] position = classNode.getPosition();

      if (position[0] == 0.0 && position[1] == 0.0) {
        double offsetX = (numberOfClasses % 5) * (graphicalClassNode.getPrefWidth() + spacing);
        double offsetY = (numberOfClasses / 5) * (graphicalClassNode.getPrefHeight() + spacing);

        position[0] = startX + offsetX;
        position[1] = startY + offsetY;

        classNode.setPosition(position[0], position[1]);
        numberOfClasses++;
      }

      graphicalClassNode.setLayoutX(position[0]);
      graphicalClassNode.setLayoutY(position[1]);

      // Use the existing DraggableMaker instance
      draggableMaker.makeDraggable(graphicalClassNode, classNode);

      canvas.getChildren().add(graphicalClassNode);
      observableClass.addClassBox(graphicalClassNode);
    }

    drawAllRelationships();
  }

  private void drawAllRelationships() {
    for (UMLClassNode fromNode : commandBridge.getStorage().getAllNodes().values()) {
      for (UMLClassNode.Relationship relationship : fromNode.getRelationships()) {
        UMLClassNode toNode = commandBridge.getStorage().getNode(relationship.getTarget());
        VBox fromBox = findClassName(fromNode.getClassName());
        VBox toBox = findClassName(toNode.getClassName());
        if (fromBox != null && toBox != null) {
          drawRelationLine(fromBox, toBox, relationship.getType());
        }
      }
    }
  }

  @FXML
  public void handleDeleteParam(ActionEvent event) {}

  @FXML
  public void handleRenameParam(ActionEvent event) {}

  public void newProjectHandler(ActionEvent event) {
    canvas.getChildren().clear();
  }

  @FXML
  public void backToMainMenu(ActionEvent actionEvent) {
    try {
      // Debugging log to verify source and path
      LOGGER.info("Source of actionEvent: " + actionEvent.getSource());
      String fxmlPath = "/MainMenu.fxml";
      LOGGER.info("Attempting to load: " + fxmlPath);

      // Load FXML for Main Menu
      FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
      if (loader.getLocation() == null) {
        LOGGER.severe("MainMenu.fxml not found at path: " + fxmlPath);
        return;
      }
      VBox mainMenuRoot = loader.load();

      // Set up the Scene and Stage
      Scene mainMenuScene = new Scene(mainMenuRoot);
      Stage mainMenuStage = new Stage();
      mainMenuStage.setScene(mainMenuScene);
      mainMenuStage.setTitle("UML Editor - Main Menu");
      mainMenuStage.show();

      // Close current stage
      Object source = actionEvent.getSource();
      if (source instanceof Button) {
        ((Stage) ((Button) source).getScene().getWindow()).close();
      } else if (source instanceof MenuItem) {
        ((Stage) ((MenuItem) source).getParentPopup().getOwnerWindow()).close();
      } else {
        LOGGER.warning("Unrecognized source for backToMainMenu action.");
      }
    } catch (Exception e) {
      LOGGER.severe("Error occurred while returning to Main Menu: " + e.getMessage());
      LOGGER.throwing(getClass().getName(), "backToMainMenu", e);
    }
  }

  @FXML
  public void onExportImage(ActionEvent event) throws IOException {
    // Get the directory where the JAR file is located
    String jarLocation = new File(System.getProperty("user.dir")).getAbsolutePath();

    // Get the file name for the exported image
    String fileName = getFileName();

    if (fileName.isBlank() || fileName == null) {
      // Show confirmation popup
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Export Failed or Canceled");
      alert.setHeaderText("Export Operation Failed or Canceled");
      alert.showAndWait(); // Wait for the user to acknowledge
      return;
    }

    // Saving the current scene as an image
    WritableImage image = canvas.snapshot(null, null);

    // Construct the path for the image in the same directory as the JAR
    File file = new File(jarLocation, fileName + ".png");

    // Writes the image to the file
    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);

    // Use a TextArea to display the full path
    TextArea textArea = new TextArea("Image exported to: \n" + file.getAbsolutePath());
    textArea.setEditable(false); // Make it read-only
    textArea.setWrapText(true); // Enable text wrapping
    textArea.setPrefWidth(400); // Set preferred width
    textArea.setPrefHeight(100); // Set preferred height

    // Show confirmation popup
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Export Successful");
    alert.setHeaderText("Image Exported Successfully");
    alert.getDialogPane().setContent(textArea); // Add TextArea to the Alert
    alert.showAndWait(); // Wait for the user to acknowledge
  }

  public void loadProjectWithoutGUI(String fileName) throws IOException {
    if (!fileName.endsWith(".json")) {
      fileName += ".json";
    }

    File file = new File(fileName);
    if (!file.isAbsolute()) {
      String jarLocation = new File(System.getProperty("user.dir")).getAbsolutePath();
      file = new File(jarLocation, fileName);
    }

    if (!file.exists() || !file.isFile()) {
      throw new IOException("Project file not found: " + file.getAbsolutePath());
    }

    CommandResult result = commandBridge.loadFile(new String[] {file.getAbsolutePath()});
    if (!result.isSuccess()) {
      throw new IOException("Failed to load UML project: " + result.getMessage());
    }

    renderDiagramOnCanvas(file.getName().replace(".json", ""));
  }

  @FXML
  public void exportImageNonInteractive(String fileName) throws IOException {
    // Initialize an invisible canvas if it's not already initialized
    if (canvas == null) {
      initializeInvisibleCanvas();
    }

    if (fileName == null || fileName.isBlank()) {
      throw new IOException("File name cannot be null or blank");
    }

    // Get the directory where the JAR file is located
    String jarLocation = new File(System.getProperty("user.dir")).getAbsolutePath();

    // Construct the file path for the image
    File imageFile = new File(jarLocation, fileName + ".png");

    // Load the UML diagram and render it on the canvas
    if (!renderDiagramOnCanvas(fileName)) {
      throw new IOException("Failed to render UML diagram. File not found or invalid: " + fileName);
    }

    // Capture the canvas as an image
    WritableImage image = canvas.snapshot(null, null);

    // Save the image
    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", imageFile);

    System.out.println("Image exported to: " + imageFile.getAbsolutePath());
  }

  /**
   * Renders the UML diagram on the canvas based on the given JSON file name.
   *
   * @param fileName The name of the JSON file to load (without the .json extension).
   * @return true if the diagram was successfully rendered, false otherwise.
   */
  private boolean renderDiagramOnCanvas(String fileName) {
    try {
      String jarLocation = new File(System.getProperty("user.dir")).getAbsolutePath();

      // Ensure fileName is not an absolute path
      File jsonFile = new File(fileName);
      if (!jsonFile.isAbsolute()) {
        jsonFile = new File(jarLocation, fileName + ".json");
      }

      System.out.println("Resolved file path: " + jsonFile.getAbsolutePath());
      System.out.println("File exists: " + jsonFile.exists());

      if (!jsonFile.exists() || !jsonFile.isFile()) {
        System.err.println("Load Error: File not found: " + jsonFile.getAbsolutePath());
        return false;
      }

      String jsonContent = Files.readString(jsonFile.toPath());
      JsonArray jsonArray = new Gson().fromJson(jsonContent, JsonArray.class);

      for (JsonElement element : jsonArray) {
        if (!element.isJsonObject()) {
          System.err.println("Invalid element found in JSON array: " + element);
          return false;
        }
      }

      System.out.println("JSON structure is valid.");
      CommandResult result = commandBridge.loadFile(new String[] {jsonFile.getAbsolutePath()});
      if (!result.isSuccess()) {
        System.err.println("Load Error: " + result.getMessage());
        return false;
      }

      refreshCanvas();
      return true;
    } catch (Exception e) {
      System.err.println("Error rendering diagram: " + e.getMessage());
      return false;
    }
  }

  /** Initializes an invisible canvas for rendering UML diagrams. */
  private void initializeInvisibleCanvas() {
    canvas = new Pane(); // Or `new Canvas(width, height)` if using a Canvas
    canvas.setPrefSize(800, 600); // Set appropriate dimensions for the UML diagram
  }
}
