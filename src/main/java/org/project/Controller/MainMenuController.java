package org.project.Controller;

import java.io.IOException;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.project.Model.CommandRegistries;
import org.project.View.CommandLineTerminal;

public class MainMenuController {

  private static final Logger LOGGER = Logger.getLogger(MainMenuController.class.getName());

  @FXML private Button startGuiButton;

  @FXML private Button startCliButton;

  @FXML private Button exitButton;

  /**
   * Handles button clicks from the Main Menu.
   *
   * @param event The ActionEvent triggered by the button click.
   */
  @FXML
  public void handleButtonAction(ActionEvent event) {
    Object source = event.getSource();

    if (source == startGuiButton) {
      startGui();
    } else if (source == startCliButton) {
      startCli();
    } else if (source == exitButton) {
      exitApplication();
    } else {
      LOGGER.warning("Unrecognized action source: " + source);
    }
  }

  /** Launches the CLI application and closes the main menu. */
  private void startCli() {
    try {
      LOGGER.info("Launching CLI application...");
      CommandRegistries commands = new CommandRegistries("src/main/resources/CLICommands.json");
      CommandLineTerminal commandLineTerminal = new CommandLineTerminal(commands);
      commandLineTerminal.launch();

      // TODO: re-enable this feature when done
      // Close the main menu
      //      Stage stage = (Stage) startCliButton.getScene().getWindow();
      //      stage.close();
    } catch (IOException e) {
      LOGGER.severe("Error launching CLI: " + e.getMessage());
    }
  }

  /** Launches the GUI application and closes the main menu. */
  private void startGui() {
    try {
      LOGGER.info("Launching GUI application...");

      // Create a new Stage for the GUI
      Stage newStage = new Stage();

      // Load the FXML and set up the stage
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIGraphics.fxml"));
      Parent root = loader.load();

      // Create and set the Scene
      Scene scene = new Scene(root);
      newStage.setScene(scene);
      newStage.setTitle("UML Editor - Graphical User Interface");
      newStage.show();

      // Close the main menu
      Stage stage = (Stage) startGuiButton.getScene().getWindow();
      stage.close();
    } catch (IOException e) {
      LOGGER.severe("Error launching GUI: " + e.getMessage());
    }
  }

  /** Exits the application. */
  private void exitApplication() {
    LOGGER.info("Exiting application...");
    Stage stage = (Stage) exitButton.getScene().getWindow();
    stage.close();
  }
}
