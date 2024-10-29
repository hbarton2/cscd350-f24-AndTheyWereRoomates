package org.project;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.util.Map;

public class GUIView extends Application {
    private VBox drawer;
    private TextField classNameField;
    private ListView<String> attributesList;
    private ListView<String> methodsList;
    private ListView<String> relationsList;
    private UMLClassRep selectedClassNode;
    @Override
    public void start(Stage stage) {


        BorderPane root = new BorderPane();
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        MenuItem loadItem = new MenuItem("Load");
        MenuItem saveItem = new MenuItem("Save");
        fileMenu.getItems().addAll(loadItem, saveItem);

        Menu helpMenu = new Menu("Help");
        MenuItem tutorialItem = new MenuItem("Tutorial");
        helpMenu.getItems().add(tutorialItem);

        Menu classMenu = new Menu("Class");
        MenuItem addClassItem = new MenuItem("Add");
        classMenu.getItems().add(addClassItem);

        Menu exitMenu = new Menu("Exit");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> stage.close());
        exitMenu.getItems().add(exitItem);

        menuBar.getMenus().addAll(fileMenu, helpMenu, classMenu, exitMenu);

        root.setTop(menuBar);

        Pane canvas = new Pane();
        canvas.setStyle("-fx-background-color: #f4f4f4;");
        root.setCenter(canvas);

        setupDrawer(root);

        Scene scene = new Scene(root, 1000, 1000);
        stage.setTitle("UML Diagram Editor");
        stage.setScene(scene);
        stage.show();

        addClassItem.setOnAction(event -> {
            UMLClassRep umlClass = new UMLClassRep("NewClass" + (canvas.getChildren().size() + 1));
            umlClass.setLayoutX(100 + canvas.getChildren().size() * 20);
            umlClass.setLayoutY(100 + canvas.getChildren().size() * 20);
            canvas.getChildren().add(umlClass);

            umlClass.setOnMouseClicked(e -> showClassProperties(umlClass));
        });










    }

    private void setupDrawer(BorderPane root) {
        drawer = new VBox();
        drawer.setPadding(new Insets(10));
        drawer.setSpacing(10);
        drawer.setPrefWidth(200);
        drawer.setStyle("-fx-background-color: #eeeeee;");

        Label propertiesTitle = new Label("Class Properties");
        classNameField = new TextField();
        classNameField.setPromptText("Class Name");
        Button saveClassName = new Button("Save");

        attributesList = new ListView<>();
        Button addAttributeButton = new Button("Add Attribute");
        addAttributeButton.setOnAction(e -> {
            if (selectedClassNode != null) {
                attributesList.getItems().add("NewAttribute");
                selectedClassNode.getFieldList().getItems().add("NewAttribute");
            }
        });

        methodsList = new ListView<>();
        Button addMethodButton = new Button("Add Method");
        addMethodButton.setOnAction(e -> {
            if (selectedClassNode != null) {
                methodsList.getItems().add("newMethod()");
                selectedClassNode.getMethodList().getItems().add("newMethod()");
            }
        });

        relationsList = new ListView<>();
        Button addRelationButton = new Button("Add Relation");
        addRelationButton.setOnAction(e -> {
            if (selectedClassNode != null) {
                relationsList.getItems().add("newRelation()");
                selectedClassNode.getRelationList().getItems().add("newRelation()");
            }
        });

        saveClassName.setOnAction(actionEvent -> {

            if (selectedClassNode != null) {
                String newClassName = classNameField.getText();
                classNameField = new TextField(newClassName);
            }

        });

        drawer.getChildren().addAll(propertiesTitle, classNameField, saveClassName, new Label("Attributes:"), attributesList, addAttributeButton,
                new Label("Methods:"), methodsList, addMethodButton, new Label("Relations:"), relationsList);
        drawer.setVisible(false);
        root.setRight(drawer);



    }

    private void showClassProperties(UMLClassRep umlClass) {
        selectedClassNode = umlClass;
        drawer.setVisible(true);

        classNameField.setText(umlClass.getClassName());
        attributesList.getItems().setAll(umlClass.getFieldList().getItems());
        methodsList.getItems().setAll(umlClass.getMethodList().getItems());
        relationsList.getItems().setAll(umlClass.getFieldList().getItems());

        classNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (selectedClassNode != null) {
                selectedClassNode.setClassName(newValue);
            }
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}

