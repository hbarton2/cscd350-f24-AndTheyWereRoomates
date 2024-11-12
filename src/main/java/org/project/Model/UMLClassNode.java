package org.project.Model;

import java.util.List;

public class UMLClassNode {

  private String className;
  private List<Field> fields;
  private List<Method> methods;
  private List<Relationship> relationships;

  // Constructor
  public UMLClassNode(String className, List<Field> fields, List<Method> methods,
    List<Relationship> relationships) {
    this.className = className;
    this.fields = fields;
    this.methods = methods;
    this.relationships = relationships;
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

  @Override
  public String toString() {
    return "UMLClassNode{" +
      "className='" + className + '\'' +
      ", fields=" + fields +
      ", methods=" + methods +
      ", relationships=" + relationships +
      '}';
  }

  // Inner classes for Field, Method, and Relationship
  public static class Field {

    private String type;
    private String name;

    public Field(String type, String name) {
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
      return "Field{" +
        "type='" + type + '\'' +
        ", name='" + name + '\'' +
        '}';
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

    public String getType() {
      return type;
    }

    public String getName() {
      return name;
    }

    public List<Parameter> getParameters() {
      return parameters;
    }

    public boolean isOverloaded() {
      return isOverloaded;
    }

    @Override
    public String toString() {
      return "Method{" +
        "type='" + type + '\'' +
        ", name='" + name + '\'' +
        ", parameters=" + parameters +
        ", isOverloaded=" + isOverloaded +
        '}';
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
        return "Parameter{" +
          "type='" + type + '\'' +
          ", name='" + name + '\'' +
          '}';
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

    public String getType() {
      return type;
    }

    public String getTarget() {
      return target;
    }

    @Override
    public String toString() {
      return "Relationship{" +
        "type='" + type + '\'' +
        ", target='" + target + '\'' +
        '}';
    }
  }
}
