package org.project;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
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
    private ListView<String> fieldList;
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
        classMenu.getItems().addAll(addClassItem);


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
        Button saveClassButton = new Button("Save Class Name");

        saveClassButton.setOnAction(actionEvent -> {
            String className = classNameField.getText();
            selectedClassNode.setClassName(className);

        });



        fieldList = new ListView<>();
        Button addFieldButton = new Button("Add Field");
        Button renameFieldButton = new Button("Rename Field");
        fieldList.setCellFactory(TextFieldListCell.forListView());

        renameFieldButton.setOnAction(actionEvent -> {

            int selectIndex = fieldList.getSelectionModel().getSelectedIndex();
            if(selectIndex != -1){
                fieldList.setEditable(true);
                selectedClassNode.getFieldList().getItems().add(selectIndex, fieldList.getItems().get(selectIndex));
            }
        });

        addFieldButton.setOnAction(e -> {
            if (selectedClassNode != null) {
                fieldList.getItems().add("NewField");
                selectedClassNode.getFieldList().getItems().add("NewField");
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


        drawer.getChildren().addAll(propertiesTitle, classNameField, saveClassButton, new Label("Attributes:"), fieldList, addFieldButton, renameFieldButton,
                new Label("Methods:"), methodsList, addMethodButton, new Label("Relations:"), relationsList);
        drawer.setVisible(false);
        root.setRight(drawer);



    }

    private void showClassProperties(UMLClassRep umlClass) {
        selectedClassNode = umlClass;
        drawer.setVisible(true);

        classNameField.setText(umlClass.getClassName().getText());
        fieldList.getItems().setAll(umlClass.getFieldList().getItems());
        methodsList.getItems().setAll(umlClass.getMethodList().getItems());
        relationsList.getItems().setAll(umlClass.getFieldList().getItems());

    }
    public static void main(String[] args) {
        launch(args);
    }
}

