package org.project.View;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import org.jline.console.impl.Builtins;
import org.jline.console.impl.ConsoleEngineImpl;
import org.project.Controller.CommandResult;
import org.project.Model.CommandLogic;
import org.project.Model.CommandRegistries;
import org.project.Model.UMLClassNode;
import org.project.Controller.CommandBridgeAdapter;
import org.project.Controller.CommandBridge;
import org.project.Model.UMLModel;

public class ClassBoxFactory {

    /**
     * This class is responsible for created a classBox for visualization
     */
    public static ClassBox createClassBox(UMLClassNode umlClassNode, String[] inspectorValues, CommandBridge commandBridge) {

        ClassBox classBox = new ClassBox(umlClassNode.getClassName());

        String fieldName = inspectorValues[1];
        String fieldType = inspectorValues[2];
        String methodName = inspectorValues[4];
        String methodType = inspectorValues[3];
        String parameterName = inspectorValues[5];
        String parameterType = inspectorValues[6];

        ListView<String> fieldList = (ListView<String>) classBox.getChildren().get(1);
        ListView<String> methodList = (ListView<String>) classBox.getChildren().get(2);

        if (fieldType != null &&  fieldName != null && !fieldName.isEmpty()){
            CommandResult result = commandBridge.addField(new String[]{fieldType, fieldName});
            if(result.isSuccess()){

                fieldList.getItems().add(fieldType + " " + fieldName);
            }

        }

        if (methodType != null &&  methodName != null && !methodType.isEmpty()){
      if (parameterType != null && parameterName != null && !parameterName.isEmpty()) {
        CommandResult result = commandBridge.addMethod(new String[] {methodType, methodName, parameterType, parameterName});
        if (result.isSuccess()) {
          String methodFormat = methodType + " " + methodName + "(" + parameterType + " " + parameterName + ")";
          methodList.getItems().add(methodFormat);
        }
      }else{
          CommandResult result = commandBridge.addMethod(new String[] {methodType, methodName});
          if (result.isSuccess()) {
              String methodFormat = methodType + " " + methodName + "()";
              methodList.getItems().add(methodFormat);
          }

      }
        }

        return classBox;

}
}