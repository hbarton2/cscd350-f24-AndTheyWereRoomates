package org.project.Model;

import java.util.ArrayList;
import java.util.List;

public class UMLClassNode {

  private String className;
  private List<Field> fields;
  private List<Method> methods;
  private List<Relationship> relationships;

  // Constructor
  public UMLClassNode(
      String className,
      List<Field> fields,
      List<Method> methods,
      List<Relationship> relationships) {
    this.className = className;
    this.fields = fields;
    this.methods = methods;
    this.relationships = relationships;
  }

  // Constructor with only className
  public UMLClassNode(String className) {
    this.className = className;
    this.fields = new ArrayList<>();
    this.methods = new ArrayList<>();
    this.relationships = new ArrayList<>();
  }

  public UMLClassNode(UMLClassNode other) {
    this.className = other.className;
    this.fields = new ArrayList<>();
    for (Field field : other.fields) {
      this.fields.add(new Field(field));
    }
    this.methods = new ArrayList<>();
    for (Method method : other.methods) {
      this.methods.add(new Method(method));
    }
    this.relationships = new ArrayList<>();
    for (Relationship relationship : other.relationships) {
      this.relationships.add(new Relationship(relationship));
    }
  }

  // Getters
  public String getClassName() {
    return className;
  }

  public List<Field> getFields() {
    return fields;
  }

  public List<Method> getMethods() {
    return methods;
  }

  public List<Relationship> getRelationships() {
    return relationships;
  }

  // Setters
  public void setClassName(String className) {
    this.className = className;
  }

  @Override
  public String toString() {
    return "UMLClassNode{"
        + "className='"
        + className
        + '\''
        + ", fields="
        + fields
        + ", methods="
        + methods
        + ", relationships="
        + relationships
        + '}';
  }

  // Inner classes for Field, Method, and Relationship
  public static class Field {

    private String type;
    private String name;

    public Field(String type, String name) {
      this.type = type;
      this.name = name;
    }

    public Field(Field other) {
      this.type = other.type;
      this.name = other.name;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return "Field{" + "type='" + type + '\'' + ", name='" + name + '\'' + '}';
    }
  }

  public static class Method {

    private String type;
    private String name;
    private List<Parameter> parameters;
    private boolean isOverloaded;

    public Method(String type, String name, List<Parameter> parameters, boolean isOverloaded) {
      this.type = type;
      this.name = name;
      this.parameters = parameters;
      this.isOverloaded = isOverloaded;
    }

    public Method(Method other) {
      this.type = other.type;
      this.name = other.name;
      this.parameters = new ArrayList<>();
      for (Parameter parameter : other.parameters) {
        this.parameters.add(new Parameter(parameter.getType(), parameter.getName()));
      }
      this.isOverloaded = other.isOverloaded;
    }

    public String getType() {
      return type;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public void setType(String type) {
      this.type = type;
    }

    public List<Parameter> getParameters() {
      return parameters;
    }

    public void setParameters(List<Parameter> parameters) {}

    public boolean isOverloaded() {
      return isOverloaded;
    }

    @Override
    public String toString() {
      return "Method{"
          + "type='"
          + type
          + '\''
          + ", name='"
          + name
          + '\''
          + ", parameters="
          + parameters
          + ", isOverloaded="
          + isOverloaded
          + '}';
    }

    public static class Parameter {

      private String type;
      private String name;

      public Parameter(String type, String name) {
        this.type = type;
        this.name = name;
      }

      public String getType() {
        return type;
      }

      public String getName() {
        return name;
      }

      @Override
      public String toString() {
        return "Parameter{" + "type='" + type + '\'' + ", name='" + name + '\'' + '}';
      }
    }
  }

  public static class Relationship {

    private String type;
    private String target;

    public Relationship(String type, String target) {
      this.type = type;
      this.target = target;
    }

    public Relationship(Relationship other) {
      this.type = other.type;
      this.target = other.target;
    }

    public String getType() {
      return type;
    }

    public String getTarget() {
      return target;
    }

    @Override
    public String toString() {
      return "Relationship{" + "type='" + type + '\'' + ", target='" + target + '\'' + '}';
    }
  }
}
