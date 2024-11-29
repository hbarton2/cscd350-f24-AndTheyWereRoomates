package org.project;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

public class JavaFXTerminal extends Application {

  @Override
  public void start(Stage primaryStage) {
    CodeArea terminalArea = new CodeArea();
    terminalArea.setStyle("-fx-background-color: black; -fx-text-fill: white;");
    terminalArea.appendText("Welcome to the UML Editor CLI\n");
    terminalArea.appendText("Type 'help' to see available commands, or 'exit' to quit.\n$ ");

    // Handle input (basic example)
    terminalArea.setOnKeyPressed(event -> {
      switch (event.getCode()) {
        case ENTER -> {
          String[] lines = terminalArea.getText().split("\n");
          String lastLine = lines[lines.length - 1].trim();
          processCommand(lastLine, terminalArea);
        }
        default -> {
          // Allow typing
        }
      }
    });

    VBox root = new VBox(new VirtualizedScrollPane<>(terminalArea));
    Scene scene = new Scene(root, 800, 600);

    primaryStage.setScene(scene);
    primaryStage.setTitle("JavaFX Terminal");
    primaryStage.show();
  }

  private void processCommand(String command, CodeArea terminalArea) {
    if (command.endsWith("help")) {
      terminalArea.appendText("\nAvailable commands:\n - help\n - exit\n - clear\n$ ");
    } else if (command.endsWith("exit")) {
      Platform.exit();
    } else if (command.endsWith("clear")) {
      terminalArea.clear();
      terminalArea.appendText("Welcome to the UML Editor CLI\n$ ");
    } else {
      terminalArea.appendText("\nUnknown command: " + command + "\n$ ");
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
