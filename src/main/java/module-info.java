/** minimum requirements for v4.3.77+ */
module org.project {
  // Required JavaFX modules
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.graphics;
  requires javafx.swing;

  // Other required modules
  requires java.desktop;
  requires com.google.gson;
  requires org.jline;
  requires info.picocli;
  requires org.fxmisc.richtext;
  requires org.fxmisc.flowless;
  requires org.apache.lucene.suggest;
  requires org.apache.lucene.core;
  requires java.logging;

  // Exported packages for public access
  exports org.project; // <-- Ensure this is exported for your Application class
  exports org.project.Controller;
  exports org.project.Model;
  exports org.project.View;
  exports org.project.Memento;

  // Opened packages for reflective access (e.g., FXML and Gson)
  opens org.project.Controller to
      javafx.fxml,
      com.google.gson;
  opens org.project.Model to
      com.google.gson;
  opens org.project.View to
      javafx.fxml,
      com.google.gson;
  opens org.project.Memento to
      com.google.gson;
}
