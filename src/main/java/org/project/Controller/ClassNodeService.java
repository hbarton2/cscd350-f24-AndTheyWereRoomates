package org.project.Controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.Gson;
import org.project.Model.UMLClassNode;
import org.project.Model.UMLClassNode.Field;
import org.project.Model.UMLClassNode.Method;
import org.project.Model.UMLClassNode.Method.Parameter;
import org.project.Model.UMLClassNode.Relationship;

import java.util.ArrayList;
import java.util.List;

public class ClassNodeService {

  private final Gson json = new Gson();

  public UMLClassNode createClassNodeFromJson(JsonObject jsonData) {
    // Parse class name
    String className = jsonData.get("className").getAsString();

    // Parse fields
    List<Field> fields = new ArrayList<>();
    JsonArray jsonFields = jsonData.getAsJsonArray("fields");
    for (int i = 0; i < jsonFields.size(); i++) {
      JsonObject jsonField = jsonFields.get(i).getAsJsonObject();
      String type = jsonField.get("type").getAsString();
      String name = jsonField.get("name").getAsString();
      fields.add(new Field(type, name));
    }

    // Parse methods
    List<Method> methods = new ArrayList<>();
    JsonArray jsonMethods = jsonData.getAsJsonArray("methods");
    for (int i = 0; i < jsonMethods.size(); i++) {
      JsonObject jsonMethod = jsonMethods.get(i).getAsJsonObject();
      String type = jsonMethod.get("type").getAsString();
      String name = jsonMethod.get("name").getAsString();
      boolean isOverloaded = jsonMethod.get("isOverloaded").getAsBoolean();

      // Parse parameters
      List<Parameter> parameters = new ArrayList<>();
      JsonArray jsonParameters = jsonMethod.getAsJsonArray("parameters");
      for (int j = 0; j < jsonParameters.size(); j++) {
        JsonObject jsonParam = jsonParameters.get(j).getAsJsonObject();
        String paramType = jsonParam.get("type").getAsString();
        String paramName = jsonParam.get("name").getAsString();
        parameters.add(new Parameter(paramType, paramName));
      }

      methods.add(new Method(type, name, parameters, isOverloaded));
    }

    // Parse relationships
    List<Relationship> relationships = new ArrayList<>();
    JsonArray jsonRelationships = jsonData.getAsJsonArray("relationships");
    for (int i = 0; i < jsonRelationships.size(); i++) {
      JsonObject jsonRelationship = jsonRelationships.get(i).getAsJsonObject();
      String relType = jsonRelationship.get("type").getAsString();
      String target = jsonRelationship.get("target").getAsString();
      relationships.add(new Relationship(relType, target));
    }

    // Create and return the UMLClassNode instance
    return new UMLClassNode(className, fields, methods, relationships);
  }

  public Gson getJson() {
    return json;
  }
}
