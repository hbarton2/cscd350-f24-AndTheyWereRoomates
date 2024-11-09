package org.project.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUIView extends Application {


  /**
   * Loads the GUI
   * @param stage - Loads "Main.fxml", Represents the GUI
   */
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

  /**
   * Launches the GUI
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }
}

