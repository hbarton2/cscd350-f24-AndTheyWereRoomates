package org.project.View;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GUIView extends Application {

  private boolean firstTimeShown = true; // Flag to track first-time display

  /**
   * Loads the GUI and ensures it opens on top
   *
   * @param stage - Loads "Main.fxml", represents the GUI
   */
  @Override
  public void start(Stage stage) throws IOException {

    // Load the FXML file
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);

    // Set up the scene and stage properties
    stage.setScene(scene);
    stage.setTitle("UML EDITOR");

    // Add an event listener to minimize and restore the window only once
    stage.addEventHandler(
        WindowEvent.WINDOW_SHOWN,
        e -> {
          if (firstTimeShown) {
            stage.setIconified(true); // Minimize the window briefly
            stage.setIconified(false); // Restore the window to bring it to the front
            firstTimeShown = false; // Set the flag to prevent further minimize/restore
          }
        });

    stage.show(); // Show initially
  }

  /**
   * Launches the GUI
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }
}
