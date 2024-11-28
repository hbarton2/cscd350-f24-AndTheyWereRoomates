package org.project.Controller;

import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public interface ArrowStrategy {
  void drawArrow(Polygon arrowHead, Line line);
}
