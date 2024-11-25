package org.project.View;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import org.project.Model.UMLClassNode;
public class ClassBoxFactory {

    /**
     * This class is responsible for created a classBox for visualization
     */
    public static ClassBox createClassBox(UMLClassNode umlClassNode) {
        ClassBox classBox = new ClassBox(umlClassNode.getClassName());

        ListView<String> fieldList = (ListView<String>) classBox.getChildren().get(1);
        ListView<String> methodList = (ListView<String>) classBox.getChildren().get(2);

        for (UMLClassNode.Field field : umlClassNode.getFields()) {
            fieldList.getItems().add(field.getType() + " " + field.getName());
        }

        for (UMLClassNode.Method method : umlClassNode.getMethods()) {
            String formattedMethod = method.getName() + "()";
            if (!method.getParameters().isEmpty()) {
                formattedMethod = method.getName() + "(";
                for (UMLClassNode.Method.Parameter param : method.getParameters()) {
                    formattedMethod += param.getType() + " " + param.getName() + ", ";
                }
                formattedMethod = formattedMethod.substring(0, formattedMethod.length() - 2) + ")";
            }
            methodList.getItems().add(formattedMethod);
        }
        return classBox;

}
}