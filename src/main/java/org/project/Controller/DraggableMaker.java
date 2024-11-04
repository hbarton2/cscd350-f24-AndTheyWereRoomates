package org.project.Controller;

import javafx.scene.Node;

public class DraggableMaker {

    private double mouseAnchorX;
    private double mouseAnchorY;

    public void makeDraggable(Node node){
        node.setOnMousePressed(e ->{
            mouseAnchorX = e.getX();
            mouseAnchorY = e.getY();
        });

        node.setOnMouseDragged( mouseEvent -> {
            node.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
            node.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
        });
    }
}
