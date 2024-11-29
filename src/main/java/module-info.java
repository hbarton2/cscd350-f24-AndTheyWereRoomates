/** minimum requirements for v4.0.56+ */
module org.project {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.graphics;
  requires java.desktop;
  requires com.google.gson;
  requires org.apache.lucene.suggest;
  requires org.apache.lucene.core;
  requires org.jline;
  requires javafx.swing;
  requires info.picocli;
  requires java.logging;

  exports org.project.Controller;
  exports org.project.Model;
  exports org.project.View;
  exports org.project.Memento;

  opens org.project.Controller to
      javafx.fxml,
      com.google.gson;
  opens org.project.Model to
      javafx.fxml,
      com.google.gson;
  opens org.project.View to
      javafx.fxml,
      com.google.gson,
      info.picocli;
  opens org.project.Memento to
      javafx.fxml,
      com.google.gson;
}
