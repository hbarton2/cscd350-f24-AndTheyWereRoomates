package org.project.Controller;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

/**
 * Represents a composition arrow used in UML diagrams.
 *
 * <p>This arrow style is commonly used to indicate a composition relationship between
 * two classes. The arrowhead is represented as a filled diamond shape.
 */
public class CompositionArrow implements ArrowStrategy {
  /**
   * Draws a composition arrow.
   *
   * <p>The arrow consists of a diamond-shaped arrowhead, filled with red color,
   * and a white line representing the shaft of the arrow.
   *
   * @param arrowHead the {@code Polygon} object representing the arrowhead
   * @param line      the {@code Line} object representing the arrow shaft
   */

  @Override
  public void drawArrow(Polygon arrowHead, Line line) {
    arrowHead.getPoints().clear();
    arrowHead
            .getPoints()
            .addAll(
                    -40.0, 0.0,
                    -20.0, -10.0,
                    0.0, 0.0,
                    -20.0, 10.0,
                    -40.0, 0.0);

    arrowHead.setFill(Color.RED);
    arrowHead.setStroke(Color.WHITE);

    line.setStroke(Color.WHITE);
    line.setStrokeWidth(2.0);
  }
}
