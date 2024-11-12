/**
 * minimum requirements for v
 */
module org.project {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.graphics;
  requires java.desktop;
  requires com.google.gson;

  exports org.project.Controller;
  exports org.project.Model;
  exports org.project.View;

  opens org.project.Controller to javafx.fxml, com.google.gson;
  opens org.project.Model to javafx.fxml, com.google.gson;
  opens org.project.View to javafx.fxml;
}
