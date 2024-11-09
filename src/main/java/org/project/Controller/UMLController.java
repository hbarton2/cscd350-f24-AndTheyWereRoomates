package org.project.Controller;

import org.project.Model.UMLModel;

/**
 * UMLController class act as the central controller for managing commands related to UMLModel.
 *
 * It interacts with the various commands for adding removing and renaming in the UMLModel.
 */
public class UMLController {

  /**
   * the storage instance used to manage the UML Model's data.
   */
  private final UMLModel.Storage storage;
  /**
   * The save instance used to handle saving operations.
   */
  private final UMLModel.Save save;
  /**
   * the load instance used to handle loading operations.
   */
  private final UMLModel.Load load;
  /**
   * The class commands instance for handling class operations.
   */
  public final ClassCommands classCommands;
  /**
   * The FieldCommands instance for handling field operations.
   */
  public final FieldCommands fieldCommands;
  /**
   * The Methods commands instance for handling method operations.
   */
  public final MethodCommands methodCommands;
  /**
   * The relationship commands instance for handling relationship operations.
   */
  public final RelationshipCommands relationshipCommands;
  /**
   * The ParameterCommands instance for handling parameter operations.
   */
  public final ParameterCommands parameterCommands;

  /**
   * Constructs a new instance of UMLController, and initialize all the different command modules
   * as well as, storage save and load functions as well.
   */
  public UMLController() {

    this.storage = new UMLModel.Storage();
    this.save = new UMLModel.Save(storage);
    this.load = new UMLModel.Load(storage);
    this.classCommands = new ClassCommands(storage);
    this.parameterCommands = new ParameterCommands(storage);
    this.methodCommands = new MethodCommands(storage);
    this.relationshipCommands = new RelationshipCommands(storage);
    this.fieldCommands = new FieldCommands(storage);
  }

  /**
   * Returns the associated storage object with this controller.
   *
   * @return The UMLModel.Storage instance.
   */
  public UMLModel.Storage getStorage() {
    return this.storage;
  }
  /**
   * ClassCommands manages the operations related to classes in UMLModel
   * this includes adding, removing, and renaming classes.
   */
  public class ClassCommands {

    private final UMLModel.Storage storage;

    /**
     * Constructs a new class instance associated with storage.
     *
     * @param storage The UMLModel.Storage instance to manage class operation.
     */
    public ClassCommands(final UMLModel.Storage storage) {
      this.storage = storage;
    }

    /**
     * Adds a class to the UML Model
     *
     * @param input The command arguments where input[2] is the chosen name for the class being added.
     */
    public void addClass(final String[] input) {

      if (input.length != 3) {
        System.out.println("Invalid number of arguments. Usage: add class <classname>");
      } else if (input.length == 3) {
        String className = input[2];
        if (storage.list.containsKey(className)) {
          System.out.println("Class already exists");
        } else {
          storage.addClass(className);
        }
      }
    }

    /**
     * Removes a class for the UML Model.
     *
     * @param input The command arguments where input [2] is the name of the class to be removed.
     * @return A message indicating the result of the operation.
     */

    public String removeClass(String[] input) {

      if (input.length != 3) {
        System.out.println("Invalid number of arguments. Usage: remove class <classname>");
        return "Invalid number of arguments. Usage: remove class <classname>";
      }
      String className = input[2];

      if (storage.getClass(className) == null) {
        System.out.println("Class does not exist");
        return "Class does not exist";
      } else {
        storage.deleteClass(className);
        System.out.println("Class removed: " + className);
      }

      return "";

    }

    /**
     * Renames an existing class in the UML Model.
     *
     * @param input The command arguments where input[2]  is the old class name and input[3] is the new class name.
     */
    public void renameClass(String[] input) {

      if (input.length != 4) {
        System.out.println(
          "Invalid number of arguments. Usage: rename class <old classname> <new classname>");
      }

      String oldClassName = input[2];
      String newClassName = input[3];

      if (oldClassName.equals(newClassName)) {
        System.out.println("Class with name " + oldClassName + " already exists");
      } else if (storage.getClass(oldClassName) == null) {
        System.out.println("Class" + oldClassName + " does not exist");
      } else if (storage.getClass(newClassName) != null) {
        System.out.println("Class with name " + newClassName + " already exists");
      } else {
        storage.renameClass(oldClassName, newClassName);
        System.out.println("The Class has been renamed to: " + newClassName);
      }

    }

  }

  /**
   * FieldCommands manages the operations related to fields in the UML Model.
   * This includes adding, removing, and renaming fields.
   */
  public class FieldCommands {

    private final UMLModel.Storage storage;

    /**
     * This constructs a new FieldCommands  instance with the provided storage.
     *
     * @param storage The UMLModel.Storage instance to manage the field operations.
     */
    public FieldCommands(UMLModel.Storage storage) {
      this.storage = storage;
    }

    /**
     * Adds a field to a class in the UML Model.
     *
     * @param input The command arguments, where input[2] is the field name and input[3] is the field type.
     * @return A message that indicated the result of the operation.
     */
    public String addField(final String[] input) {
      String message = "";
      if (input.length != 5) {
        System.out.println(
          "Invalid number of arguments. Usage: add field <classname> <fieldname> <fieldtype>");
        message = "Invalid number of arguments. Usage: add field <classname> <fieldname> <fieldtype>";
      } else {
        String className = input[2];
        String fieldName = input[3];
        String fieldType = input[4];

        UMLModel.Class classObject = storage.getClass(className);
        if (classObject == null) {
          System.out.println("Class does not exist");
          message = "Class does not exist";
        } else if (fieldName.isEmpty() || fieldType.isEmpty()) {
          System.out.println("Fieldname or fieldtype is empty");
          message = "Field name or field type is empty";
        } else if (classObject.hasField(fieldName)) {
          System.out.println("Class already contains this field");
          message = "Class already contains this field";
        } else {
          classObject.addField(fieldName, fieldType);
          System.out.println("Field added. Field name: " + fieldName + " Field type: " + fieldType);
        }
      }

      return message;
    }

    /**
     * Removes a field for a class in the UML Model.
     *
     * @param input The command arguments, where input[2] is the class name and input[3] is the field name.
     */
    public void removeField(final String[] input) {
      if (input.length <= 3) {
        System.out.println("Invalid number of arguments");
      } else {
        String className = input[2];
        String fieldName = input[3];

        UMLModel.Class classObject = storage.getClass(className);
        if (classObject == null) {
          System.out.println("Class does not exist");
        } else if (!classObject.hasField(fieldName)) {
          System.out.println("Field does not exist");
        } else {
          classObject.removeField(fieldName);
          System.out.println("field has been removed: " + fieldName);
        }
      }
    }

    /**
     * Renames a field in the UML Model.
     *
     * @param input The command arguments, where input[2] is the class name, input[3] is the old field name, input[4] is the new field name, and input[5] is the new field type.
     */
    public void renameField(final String[] input) {
      if (input.length != 6) {
        System.out.println(
          "Invalid number of arguments. Usage: rename field <className> <oldFieldName> <newFieldName> <field type>");
      } else {
        String className = input[2];
        String oldFieldName = input[3];
        String newFieldName = input[4];
        String newFieldType = input[5];

        UMLModel.Class classObject = storage.getClass(className);
        if (classObject == null) {
          System.out.println("Class does not exist");
        } else if (!classObject.hasField(oldFieldName)) {
          System.out.println("Field does not exist");
        } else {
          classObject.renameField(oldFieldName, newFieldName, newFieldType);
          System.out.println(
            "you renamed field " + oldFieldName + " to " + newFieldName + " with the type "
              + newFieldType);
        }
      }
    }
  }

  /**
   * Commands tied to Methods
   */
  public class MethodCommands {

    private final UMLModel.Storage storage;
    /**
     * Creates a new class instance associated with storage
     * @param storage - the UMLModel.Storage instance to manage class operations
     */
    public MethodCommands(UMLModel.Storage storage) {
      this.storage = storage;
    }

    /**
     * Adds method to storage
     * @param input - The parameter inputs to add a method- 'add method [class name] [new method name]'
     * @return message - return a message saying if anything happened
     */
    public String addMethod(final String[] input) {

      String message = "";

      if (input.length != 4) {
        System.out.println(
          "Invalid number of arguments. Usage: add method <className> <newMethodName>");
        message = "Invalid number of arguments. Usage: add method <className> <newMethodName>";
      } else {
        String className = input[2];
        if (!storage.list.containsKey(className)) {
          System.out.println("Class " + className + " does not exist");
          message = "Class " + className + " does not exist";
        } else {
          String methodName = input[3];
          UMLModel.Class classObject = storage.getClass(className);

          if (classObject == null) {
            System.out.println("Class does not exist");
            message = "Class does not exist";
          } else if (classObject.hasMethod(methodName)) {
            System.out.println("Class already contains this method");
            message = "Class already contains this method";
          } else {
            classObject.addMethod(methodName);
            System.out.println("Method added. Method name: " + methodName);
          }
        }
      }

      return message;
    }

    /**
     * Removes to a method command.
     * @param input - The parameter inputs to remove a method. 'add method' [class Name] [method name]
     */
    // add(0) method(1) Class Name(2) Method Name(3)
    public void removeMethod(final String[] input) {
      if (input.length != 4) {
        System.out.println(
          "Invalid number of arguments. Usage: add method <className> <removingMethodName>");
      } else {
        String className = input[2];
        if (!storage.list.containsKey(className)) {
          System.out.println("Class " + className + " does not exist");
        } else {
          String methodName = input[3];
          UMLModel.Class classObject = storage.getClass(className);

          if (classObject == null) {
            System.out.println("Class does not exist");
          } else if (!classObject.hasMethod(methodName)) {
            System.out.println("Class doesn't contains this method");
          } else {
            classObject.removeMethod(methodName);
            System.out.println("Method " + methodName + " removed");
          }
        }
      }
    }

    /**
     * Renames a method to a different name
     * @param input - The parameter inputs to rename a method. 'rename method [class name] [method name] [new method name]'
     */
    // add(0) method(1) Class Name(2) Method Name(3) NewMethod Name(4)
    public void renameMethod(final String[] input) {
      if (input.length != 5) {
        System.out.println(
          "Invalid number of arguments. Usage: add method <className> <oldMethodName> <newMethodName>");
      } else {
        String className = input[2];

        if (!storage.list.containsKey(className)) {
          System.out.println("Class " + className + " does not exist");
        } else {
          String oldMethodName = input[3];
          String newMethodName = input[4];
          UMLModel.Class classObject = storage.getClass(className);

          if (classObject == null) {
            System.out.println("Class does not exist");
            //Checks if the new name already exists
          } else if (classObject.hasMethod(newMethodName)) {
            System.out.println("Class already contains method: " + newMethodName);
            //Checks if the old name exists
          } else if (!classObject.hasMethod(oldMethodName)) {
            System.out.println("Class doesn't contains method: " + oldMethodName);
          } else {

            classObject.renameMethod(oldMethodName, newMethodName);
            System.out.println("Method " + oldMethodName + " has been renamed to " + newMethodName);
          }
        }
      }
    }
  }

  /**
   * Commands tied to Parameters
   */
  public class ParameterCommands {

    private final UMLModel.Storage storage;
    /**
     * Creates a new class instance associated with storage
     * @param storage - the UMLModel.Storage instance to manage class operations
     */
    public ParameterCommands(UMLModel.Storage storage) {
      this.storage = storage;
    }

      /**
       * Adds parameter to storage
       * @param input - The parameter inputs to add a parameter- 'add parameter [class name] [method name] [new name]'
       * @return - nothing is returned (All parameters methods here have 'return' but it's all void)
       */
    public void addParameter(final String[] input) {
      if (input.length <= 5) {
        System.out.println(
          "Invalid number of arguments. Usage: add parameter <className> <methodName> <parameterName> <parameterType> ");
        return;
      }

      String className = input[2];
      String methodName = input[3];

      if (!storage.list.containsKey(className)) {
        System.out.println("Class " + className + " does not exist");
        return;
      }

      UMLModel.Class classObject = storage.getClass(className);
      if (!classObject.hasMethod(methodName)) {
        System.out.println("Method " + methodName + " does not exist");
        return;
      }

      UMLModel.Method method = classObject.getMethod(methodName);

      System.out.print("Enter parameter name: ");
      String parameterName = input[4];
      if (parameterName.isEmpty()) {
        System.out.println("Parameter name cannot be empty");
        return;
      }

      System.out.print("Enter parameter type: ");
      String parameterType = input[5];
      if (parameterType.isEmpty()) {
        System.out.println("Parameter type cannot be empty");
        return;
      }

      method.addParameter(parameterName, parameterType);
      System.out.println(
        "Parameter " + parameterName + " added to " + className + " " + methodName);
    }
      /**
       * Removes parameter from storage
       * @param input - The parameter inputs to remove a parameter- 'add parameter [class name] [method name] [parameter name]'
       * @return - nothing is returned
       */
    public void removeParameter(String[] input) {
      if (input.length <= 4) {
        System.out.println(
          "Invalid number of arguments. Usage: add parameter <className> <methodName> <parameterName> ");
        return;
      }

      String className = input[2];
      String methodName = input[3];

      if (!storage.list.containsKey(className)) {
        System.out.println("Class " + className + " does not exist");
        return;
      }

      UMLModel.Class classObject = storage.getClass(className);
      if (!classObject.hasMethod(methodName)) {
        System.out.println("Method " + methodName + " does not exist");
        return;
      }

      UMLModel.Method method = classObject.getMethod(methodName);
      System.out.print("Enter parameter name: ");
      String parameterName = input[4];
      if (parameterName.isEmpty()) {
        System.out.println("Parameter name cannot be empty");
        return;
      }

      if (!method.hasParameter(parameterName)) {
        System.out.println("Parameter " + parameterName + " does not exist");
      }

      method.deleteParameter(parameterName);
      System.out.println(
        "Parameter " + parameterName + " removed from " + className + " " + methodName);

    }

      /**
       * Renames parameter in storage
       * @param input - The parameter inputs to rename a parameter- 'add parameter [class name] [method name] [parameter name] [new parameter name]'
       * @return - nothing is returned
       */
    public void changeParameter(String[] input) {
      if (input.length != 7) {
        System.out.println(
          "Invalid number of arguments. Usage: rename parameter <className> <methodName> <parameterName> <newParameterName> <newParameterType>");
        return;
      }

      String className = input[2];
      String methodName = input[3];
      if (!storage.list.containsKey(className)) {
        System.out.println("Class " + className + " does not exist");
        return;
      }

      UMLModel.Class classObject = storage.getClass(className);
      if (!classObject.hasMethod(methodName)) {
        System.out.println("Method " + methodName + " does not exist");
        return;
      }
      String parameterName = input[4];
      String newParamName = input[5];
      String newParamType = input[6];
      UMLModel.Method method = classObject.getMethod(methodName);
      if (parameterName.isEmpty()) {
        System.out.println("Parameter name cannot be empty");
        return;
      }
      if (newParamName.isEmpty()) {
        System.out.println("New parameter name cannot be empty");
        return;
      }
      if (newParamType.isEmpty()) {
        System.out.println("New parameter type cannot be empty");
        return;
      }

      if (!method.hasParameter(parameterName)) {
        System.out.println("Parameter " + parameterName + " does not exist");
        return;
      }
      method.changeParameter(parameterName, newParamName, newParamType);
      System.out.println(
        "Parameter " + parameterName + " renamed to " + newParamName + " with type "
          + newParamType);


    }
  }

  /**
   * Commands tied to Relationship
   */
  public class RelationshipCommands {

    private final UMLModel.Storage storage;

    /**
     * Creates a new class instance associated with storage
     * @param storage - the UMLModel.Storage instance to manage class operations
     */
    public RelationshipCommands(UMLModel.Storage storage) {
      this.storage = storage;
    }
    /**
     * Adds parameter to storage
     * @param input - The parameter inputs to add a relationship between classes - 'add relationship [class name] [class name]'
     * @return - true/false, returns true if the program is able to execute.
     */
    public boolean addRelationship(final String[] input) {
      if (input.length <= 3) {
        System.out.println("Invalid number of arguments");
        return false;

      } else {
        String source = input[2];
        String destination = input[3];
        String type = input[4];

        //These if-statements check if the classes exist. If not the relationship cannot be created
          if (!storage.list.containsKey(source)) {
              System.out.println("Source Class does not exist");
          }
          if (!storage.list.containsKey(destination)) {
              System.out.println("Destination Class does not exist");
          }

        UMLModel.Class srcClass = storage.getClass(source);

        srcClass.addRelation(source, destination, type);
        System.out.println("Successful");
        return true;
      }
    }

    /**
     * Adds parameter to storage
     * @param input - The parameter inputs to remove a relationship between classes - 'add relationship [class name] [class name]'
     * @return - true/false, returns true if the program is able to execute.
     */
    public boolean removeRelationship(final String[] input) {
      if (input.length <= 3) {
        System.out.println("Invalid number of arguments");
        return false;
      } else {

        String source = input[2];
        String destination = input[3];
        String type = input[4];

        //Checks to see the source class exists
          if (!storage.list.containsKey(source)) {
              System.out.println("source class does not exist");
          }

        UMLModel.Class srcClass = storage.getClass(source);
        UMLModel.Class destClass = storage.getClass(destination);

        Boolean removed = srcClass.removeRelation(source, destination, type);

        if (removed) {
          System.out.println("Successful");
          return true;
        } else {
          System.out.println("Successful");
          return false;
        }
      }
    }
  }

}
