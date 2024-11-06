package org.project.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUIView extends Application {

  @Override
  public void start(Stage stage) {

    try {

      FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));
      Parent root = loader.load();
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.setTitle("UML EDITOR");
      stage.show();

    } catch (Exception e) {
      e.printStackTrace();
    }


  }

  public static void main(String[] args) {
    launch(args);
  }
}

