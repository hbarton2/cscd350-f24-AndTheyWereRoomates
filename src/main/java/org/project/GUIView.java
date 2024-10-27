package org.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUIView extends Application {


    @Override
    public void start(Stage stage) {



        try{
            Parent root = FXMLLoader.load(getClass().getResource("/yes.fxml"));
            Scene scene = new Scene(root, 1000 , 1000);
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        launch(args);
    }
}

