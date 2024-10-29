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
        this.maxWidth(100);

        this.className = new Label(className);
        this.className.setStyle("fx-font-weight: bold; fx-font-size: 18px");

        this.fieldList = new ListView<>();
        this.fieldList.maxHeight(50);

        this.methodList = new ListView<>();
        this.methodList.maxHeight(50);

        this.relationList = new ListView<>();
        this.relationList.maxHeight(50);

        this.getChildren().addAll(this.className, fieldList, methodList, relationList);

        makeDraggable();


    }

    public Label getClassName(){return this.className;}

    public ListView<String> getFieldList(){
        return this.fieldList;
    }

    public ListView<String> getMethodList(){return this.methodList;}

    public ListView<String> getRelationList(){return this.relationList;}

    public boolean setClassName(Label className){

       this.className = className;
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
        Label newName = new Label(name);
        return this.setClassName(newName);
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
