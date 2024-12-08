package org.project.Controller;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

/**
 * Represents a realization arrow used in UML diagrams.
 *
 * <p>This arrow style is used to indicate a realization relationship between classes or
 * interfaces. It consists of a hollow triangle arrowhead and a dashed line.
 */
public class RealizationArrow implements ArrowStrategy {

  /**
   * Draws a realization arrow.
   *
   * <p>The arrow consists of a triangle-shaped arrowhead, filled with red and outlined
   * in white, and a dashed white line representing the arrow shaft.
   *
   * @param arrowHead the {@code Polygon} object representing the arrowhead
   * @param line the {@code Line} object representing the arrow shaft
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
    arrowHead.setFill(Color.RED);
    arrowHead.setStroke(Color.WHITE);

    line.setStroke(Color.WHITE);
    line.setStrokeWidth(2.0);
    line.getStrokeDashArray().addAll(10.0, 10.0);
  }
}
