package org.project.View;

import javafx.scene.control.ListView;
import org.project.Controller.CommandBridge;
import org.project.Controller.CommandResult;
import org.project.Model.UMLClassNode;

public class GraphicalClassNodeFactory {

  /** This class is responsible for created a classBox for visualization */
  public static GraphicalClassNode createClassBox(
      UMLClassNode umlClassNode, String[] inspectorValues, CommandBridge commandBridge) {

    GraphicalClassNode graphicalClassNode = new GraphicalClassNode(umlClassNode.getClassName());

    String fieldName = inspectorValues[1];
    String fieldType = inspectorValues[2];
    String methodName = inspectorValues[4];
    String methodType = inspectorValues[3];
    String parameterName = inspectorValues[5];
    String parameterType = inspectorValues[6];

    ListView<String> fieldList = (ListView<String>) graphicalClassNode.getChildren().get(1);
    ListView<String> methodList = (ListView<String>) graphicalClassNode.getChildren().get(2);

    if (fieldType != null && fieldName != null && !fieldName.isEmpty()) {
      CommandResult result = commandBridge.addField(new String[] {fieldType, fieldName});
      if (result.isSuccess()) {

        fieldList.getItems().add(fieldType + " " + fieldName);
      }
    }

    if (methodType != null && methodName != null && !methodType.isEmpty()) {
      if (parameterType != null && parameterName != null && !parameterName.isEmpty()) {
        CommandResult result =
            commandBridge.addMethod(
                new String[] {methodType, methodName, parameterType, parameterName});
        if (result.isSuccess()) {
          String methodFormat =
              methodType + " " + methodName + "(" + parameterType + " " + parameterName + ")";
          methodList.getItems().add(methodFormat);
        }
      } else {
        CommandResult result = commandBridge.addMethod(new String[] {methodType, methodName});
        if (result.isSuccess()) {
          String methodFormat = methodType + " " + methodName + "()";
          methodList.getItems().add(methodFormat);
        }
      }
    }

    return graphicalClassNode;
  }

  public static GraphicalClassNode createClassBox(
      UMLClassNode umlClassNode, CommandBridge commandBridge) {

    GraphicalClassNode graphicalClassNode = new GraphicalClassNode(umlClassNode.getClassName());

    ListView<String> fieldList = (ListView<String>) graphicalClassNode.getChildren().get(1);
    ListView<String> methodList = (ListView<String>) graphicalClassNode.getChildren().get(2);

    // Populate fields
    for (UMLClassNode.Field field : umlClassNode.getFields()) {
      fieldList.getItems().add(field.getType() + " " + field.getName());
    }

    // Populate methods
    for (UMLClassNode.Method method : umlClassNode.getMethods()) {
      StringBuilder parameters = new StringBuilder();
      for (UMLClassNode.Method.Parameter param : method.getParameters()) {
        parameters.append(param.getType()).append(" ").append(param.getName()).append(", ");
      }
      if (parameters.length() > 0) {
        parameters.setLength(parameters.length() - 2);
      }
      methodList.getItems().add(method.getType() + " " + method.getName() + "(" + parameters + ")");
    }

    return graphicalClassNode;
  }
}
