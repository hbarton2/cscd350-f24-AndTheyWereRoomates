package org.project.Controller;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

/**
 * The {@code AggregationArrow} class implements the {@code ArrowStrategy} interface to define the
 * appearance and behavior of an aggregation arrow in a diagram. This arrow style is commonly used
 * in UML diagrams to represent aggregation relationships.
 *
 * <p>The arrow is represented by a polygon with a diamond shape and a line. The polygon is styled
 * with a gray fill and a white stroke, while the line is styled with a white stroke.
 */
public class AggregationArrow implements ArrowStrategy {

  /**
   * Draws an aggregation arrow consisting of a diamond-shaped arrowhead and a line.
   *
   * <p>The method configures the points, fill color, and stroke of the arrowhead, as well as the
   * stroke and width of the line to create the desired aggregation arrow appearance.
   *
   * @param arrowHead the {@code Polygon} object representing the arrowhead. The points and style of
   *     this polygon are configured to form a diamond shape.
   * @param line the {@code Line} object representing the shaft of the arrow. The style of this line
   *     is configured to match the arrowhead.
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

    arrowHead.setFill(Color.WHITE);
    arrowHead.setStroke(Color.BLACK);

    line.setStroke(Color.BLACK);
    line.setStrokeWidth(2.0);
  }
}
