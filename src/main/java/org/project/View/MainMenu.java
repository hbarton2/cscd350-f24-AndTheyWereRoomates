package org.project.View;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainMenu extends Application {

  private boolean firstTimeShown = true;

  @Override
  public void start(Stage stage) throws IOException {

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainMenu.fxml"));
    Parent root = loader.load(); // Load the FXML
    Scene scene = new Scene(root); // Set dimensions

    stage.setScene(scene);
    stage.setTitle("Main Menu");
    stage.resizableProperty().setValue(Boolean.FALSE);

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

  public static void launchApplication() {
    launch();
  }
}
