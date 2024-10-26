package org.project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GUIView extends Application {


    @Override
    public void start(Stage stage) {
        stage.setTitle("UML Editor");

        MenuBar  menuBar = new MenuBar();


        javafx.scene.control.Menu fileMenu = new javafx.scene.control.Menu("FILE");
        MenuItem openFile = new MenuItem("OPEN");
        MenuItem saveFile = new MenuItem("SAVE");

        fileMenu.getItems().addAll(openFile, saveFile);


        javafx.scene.control.Menu helpMenu = new javafx.scene.control.Menu("HELP");
        MenuItem tutorial = new MenuItem("TUTORIAl");

        helpMenu.getItems().add(tutorial);

        menuBar.getMenus().addAll(fileMenu, helpMenu);

        VBox sideBar = new VBox();






        HBox topbar = new HBox(menuBar);

        BorderPane mainlayout = new BorderPane();
        mainlayout.setTop(topbar);

        Scene scene = new Scene(mainlayout, 800,800);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

