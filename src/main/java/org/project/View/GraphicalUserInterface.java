package org.project.View;

import java.io.IOException;
import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GraphicalUserInterface extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    // Load the FXML file
    Parent root =
        FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/GUIGraphics.fxml")));

    // Set up the scene
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle("UML Editor");

    // Show the stage
    stage.show();
  }

  /** Launch the GUI */
  public static void launchApplication() {
    launch();
  }
}
