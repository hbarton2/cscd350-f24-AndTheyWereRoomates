package org.project.Controller;

import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

/**
 * The {@code ArrowStrategy} interface defines a contract for drawing different types of arrows
 * in diagrams, such as UML class diagrams. Implementing classes are responsible for
 * defining the appearance and behavior of specific arrow styles.
 */

public interface ArrowStrategy {

  /**
   * Draws an arrow with a specific style, consisting of an arrowhead and a line.
   *
   * <p>Implementations of this method should configure the points, styling, and other visual
   * properties of the arrowhead and line to achieve the desired arrow appearance.
   *
   * @param arrowHead the {@code Polygon} object representing the arrowhead. Implementations
   *                  should define the shape and style of this polygon.
   * @param line the {@code Line} object representing the shaft of the arrow. Implementations
   *             should configure the style and width of this line to match the arrowhead.
   */

  void drawArrow(Polygon arrowHead, Line line);
}
