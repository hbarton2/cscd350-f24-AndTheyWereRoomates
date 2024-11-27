/** minimum requirements for v3.0.56+ */
module org.project {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.graphics;
  requires java.desktop;
  requires com.google.gson;
  requires org.apache.lucene.suggest;
  requires org.apache.lucene.core;
  requires java.logging;
  requires org.jline;
  requires javafx.swing;

  exports org.project.Controller;
  exports org.project.Model;
  exports org.project.View;
  exports org.project.Memento;

  opens org.project.Controller to
      javafx.fxml,
      com.google.gson;
  opens org.project.Model to
      javafx.fxml,
      com.google.gson,
      javafx.swing;
  opens org.project.View to
      javafx.fxml,
      com.google.gson;
  opens org.project.Memento to
      javafx.fxml,
      com.google.gson;
}
