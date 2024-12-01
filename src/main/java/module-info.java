/** minimum requirements for v4.3.77+ */
module org.project {
  // Required JavaFX modules
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.graphics;
  requires javafx.swing;

  // Other required modules
  requires java.desktop;
  requires java.logging;
  requires com.google.gson;
  requires org.jline;
  requires info.picocli;
  requires org.apache.lucene.suggest;
  requires org.apache.lucene.core;
  requires org.apache.lucene.codecs;
  requires org.apache.lucene.analysis.common;
  requires org.apache.xbean.reflect; // Added xbean dependency
  requires org.fxmisc.richtext;

  // Exported packages for public access
  exports org.project;
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
