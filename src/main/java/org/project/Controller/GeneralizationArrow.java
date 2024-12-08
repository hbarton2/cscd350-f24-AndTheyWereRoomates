package org.project.Controller;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

/**
 * Represents a generalization arrow used in UML diagrams.
 *
 * <p>This arrow style is commonly used to indicate a generalization relationship
 * (inheritance) between two classes. The arrowhead is represented as a hollow triangle.
 */
public class GeneralizationArrow implements ArrowStrategy {

  /**
   * Draws a generalization arrow.
   *
   * <p>The arrow consists of a triangle-shaped arrowhead, filled with gray color,
   * and outlined in white, and a white line representing the shaft of the arrow.
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
                    -20.0, 10.0,
                    0.0, 0.0,
                    -20.0, -10.0);
    arrowHead.setFill(Color.GRAY);
    arrowHead.setStroke(Color.WHITE);

    line.setStroke(Color.WHITE);
    line.setStrokeWidth(2.0);
  }
}
