package org.project.Controller;

import java.util.List;
import javafx.scene.control.ComboBox;
import org.project.View.GraphicalClassNode;

/**
 * The {@code ComboBoxObserver} class implements the {@code ObserverInterface} and is responsible
 * for dynamically updating a list of {@code ComboBox} elements based on changes in the
 * observed class nodes.
 *
 * <p>This class is used to ensure that the options in the combo boxes reflect the available
 * class nodes and any default types.
 */

public class ComboBoxObserver implements ObserverInterface {

  private final List<ComboBox<String>> comboBoxes;
  private final ObservableClass observableClass;
  private final List<String> defaultTypes;

  /**
   * Constructs a new {@code ComboBoxObserver} with the specified combo boxes, observable class,
   * and default types.
   *
   * @param comboBoxes the list of {@code ComboBox} objects to be updated.
   * @param observableClass the {@code ObservableClass} that provides the list of graphical class nodes.
   * @param defaultTypes the list of default types to include in the combo boxes.
   */

  public ComboBoxObserver(
      List<ComboBox<String>> comboBoxes,
      ObservableClass observableClass,
      List<String> defaultTypes) {
    this.comboBoxes = comboBoxes;
    this.observableClass = observableClass;
    this.defaultTypes = defaultTypes;
  }

  /**
   * Updates the items in the combo boxes to include the default types and the names of all
   * graphical class nodes from the observable class.
   *
   * <p>This method clears the existing items in each combo box, adds the default types (if any),
   * and appends the names of all class nodes retrieved from the observable class.
   *
   * @param selectedGraphicalClassNode the currently selected {@code GraphicalClassNode}.
   *                                   This parameter is not used in this implementation but is
   *                                   required by the {@code ObserverInterface}.
   */

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
