package org.project.Controller;

import java.util.List;
import javafx.scene.control.ComboBox;
import org.project.View.GraphicalClassNode;

public class ComboBoxObserver implements ObserverInterface {

  private final List<ComboBox<String>> comboBoxes;
  private final ObservableClass observableClass;
  private final List<String> defaultTypes;

  public ComboBoxObserver(
      List<ComboBox<String>> comboBoxes,
      ObservableClass observableClass,
      List<String> defaultTypes) {
    this.comboBoxes = comboBoxes;
    this.observableClass = observableClass;
    this.defaultTypes = defaultTypes;
  }

  @Override
  public void update(GraphicalClassNode selectedGraphicalClassNode) {
    for (ComboBox<String> comboBox : comboBoxes) {
      comboBox.getItems().clear();
      if (defaultTypes != null) {
        comboBox.getItems().addAll(defaultTypes);
      }
    }

    for (GraphicalClassNode graphicalClassNode : observableClass.getAllClassBoxes()) {
      for (ComboBox<String> comboBox : comboBoxes) {
        comboBox.getItems().add(graphicalClassNode.getName());
      }
    }
  }
}
