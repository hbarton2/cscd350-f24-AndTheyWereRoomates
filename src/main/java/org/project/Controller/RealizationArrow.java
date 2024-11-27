package org.project.Controller;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class RealizationArrow implements ArrowStrategy {

    @Override
    public void drawArrow(Polygon arrowHead, Line line) {
        arrowHead.getPoints().clear();
        arrowHead.getPoints().addAll(
                -20.0, 10.0,
                0.0, 0.0,
                -20.0, -10.0
        );
        arrowHead.setFill(Color.RED);
        arrowHead.setStroke(Color.WHITE);

        line.setStroke(Color.WHITE);
        line.setStrokeWidth(2.0);
        line.getStrokeDashArray().addAll(10.0, 10.0);
    }
}
