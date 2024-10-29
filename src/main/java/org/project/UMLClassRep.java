package org.project;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class UMLClassRep extends VBox {

    private Label className;
    private ListView<String> fieldList;
    private ListView<String> methodList;
    private ListView<String> relationList;


    public UMLClassRep(String className){


        this.setStyle("-fx-border-color: black; fx-border-style: solid; -fx-border-width: 5");
        this.setSpacing(5);
        this.setAlignment(Pos.TOP_CENTER);
        this.setFillWidth(true);
        this.maxWidth(50);
        this.setPrefHeight(400);

        this.className = new Label(className);
        this.className.setStyle("fx-font-weight: bold; fx-font-size: 18px");

        this.fieldList = new ListView<>();

        this.methodList = new ListView<>();

        this.relationList = new ListView<>();

        this.getChildren().addAll(this.className, fieldList, methodList, relationList);

        makeDraggable();


    }

    public String getClassName(){return this.className.getText();}

    public ListView<String> getFieldList(){
        return this.fieldList;
    }

    public ListView<String> getMethodList(){return this.methodList;}

    public ListView<String> getRelationList(){return this.relationList;}

    public boolean setClassName(String className){

        Label label = new Label(className);
        this.className = label;
        return true;
    }

    public boolean setmethodList(ListView<String> methodList){
        this.methodList = methodList;
        return true;
    }

    public boolean setfieldList(ListView<String> fieldList){
        this.fieldList = fieldList;
        return true;
    }

    public boolean setRelationList(ListView<String> relationList){
        this.relationList = relationList;
        return true;
    }

    public boolean addClassName(String name){
        return this.setClassName(name);
    }

    public boolean addMethod(String method){
        return methodList.getItems().add(method);
    }

    public boolean addField(String field){
        return fieldList.getItems().add(field);
    }

    private void makeDraggable(){
        final double[] offset = new double[2];

        this.setOnMousePressed(event -> {
            offset[0] = event.getSceneX() - this.getLayoutX();
            offset[1] = event.getSceneY() - this.getLayoutY();
        });

        this.setOnMouseDragged(event -> {
            this.setLayoutX(event.getSceneX() - offset[0]);
            this.setLayoutY(event.getSceneY() - offset[1]);
        });
    }








}
