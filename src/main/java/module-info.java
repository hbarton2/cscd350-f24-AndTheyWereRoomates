/**
 * minimum requirements
 */
module org.project {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.graphics;
  requires java.desktop;
  requires com.google.gson;

  exports org.project.Controller;
  exports org.project.View to javafx.graphics;

  opens org.project.Controller to javafx.fxml;
}

