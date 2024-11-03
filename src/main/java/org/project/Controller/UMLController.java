package org.project.Controller;

import org.project.Model.UMLModel;

public class UMLController {


    private final UMLModel.Storage storage;

    private final UMLModel.Save save;
    private final UMLModel.Load load;
    public final ClassCommands classCommands;
    public final FieldCommands fieldCommands;
    public final MethodCommands methodCommands;
    public final RelationshipCommands relationshipCommands;
    public final ParameterCommands parameterCommands;

    public UMLController(){

        this.storage = new UMLModel.Storage();
        this.save = new UMLModel.Save(storage);
        this.load = new UMLModel.Load(storage);
        this.classCommands = new ClassCommands(storage);
        this.parameterCommands = new ParameterCommands(storage);
        this.methodCommands = new MethodCommands(storage);
        this.relationshipCommands = new RelationshipCommands(storage);
        this.fieldCommands = new FieldCommands(storage);
    }
    public UMLModel.Storage getStorage(){
        return this.storage;
    }
    public class ClassCommands{
        private final UMLModel.Storage storage;

        public ClassCommands(final UMLModel.Storage storage){
            this.storage = storage;
        }



        public void addClass(final String[] input) {

            if(input.length <= 2) {
                System.out.println("Invalid number of arguments. Usage: add class <classname>");
            }
            else if(input.length == 3) {
                String className = input[2];
                if(storage.list.containsKey(className)) {
                    System.out.println("Class already exists");
                } else {
                    storage.addClass(className);
                }
            }
        }

        public void removeClass(String[] input) {

            if(input.length <= 2) {
                System.out.println("Invalid number of arguments. Usage: remove class <classname>");
                return;
            }
            String className = input[2];

            if(storage.getClass(className) == null) {
                System.out.println("Class does not exist");
            } else {
                storage.deleteClass(className);
                System.out.println("Class removed: " + className);
            }

        }

        public void renameClass(String[] input) {

            if(input.length < 4) {
                System.out.println("Invalid number of arguments. Usage: rename class <old classname> <new classname>");
            }


            String oldClassName = input[2];
            String newClassName = input[3];

            if(oldClassName.equals(newClassName)) {
                System.out.println("Class with name " + oldClassName +  " already exists");
            } else if(storage.getClass(oldClassName) == null) {
                System.out.println("Class" + oldClassName +  " does not exist");
            } else if(storage.getClass(newClassName) != null) {
                System.out.println("Class with name " + newClassName +  " already exists");
            } else {
                storage.renameClass(oldClassName, newClassName);
                System.out.println("The Class has been renamed to: " + newClassName);
            }

        }

    }

    /**Commands tied to Field*/
    public class FieldCommands{
        private final UMLModel.Storage storage;

        public FieldCommands(UMLModel.Storage storage){
            this.storage = storage;
        }

        public void addField(final String[] input) {
            if(input.length < 5) {
                System.out.println("Invalid number of arguments. Usage: add field <classname> <fieldname> <fieldtype>");
            } else {
                String className = input[2];
                String fieldName = input[3];
                String fieldType = input[4];

                UMLModel.Class classObject = storage.getClass(className);
                if(classObject == null){
                    System.out.println("Class does not exist");
                }else if(classObject.hasField(fieldName)){
                    System.out.println("Class already contains this field");
                }else{
                    classObject.addField(fieldName,fieldType);
                    System.out.println("Field added. Field name: " + fieldName + " Field type: " + fieldType);
                }
            }
        }

        public void removeField(final String[] input) {
            if(input.length <= 3) {
                System.out.println("Invalid number of arguments");
            } else {
                String className = input[2];
                String fieldName = input[3];

                UMLModel.Class classObject = storage.getClass(className);
                if (classObject == null){
                    System.out.println("Class does not exist");
                }else if(!classObject.hasField(fieldName)){
                    System.out.println("Field does not exist");
                }else{
                    classObject.removeField(fieldName);
                    System.out.println("field has been removed: " + fieldName);
                }
            }
        }

        public void renameField(final String[] input) {
            if(input.length < 5) {
                System.out.println("Invalid number of arguments. Usage: rename field <className> <oldFieldName> <newFieldName>");
            } else {
                String className = input[2];
                String oldFieldName = input[3];
                String newFieldName = input[4];
                String newFieldType = input[5];

                UMLModel.Class classObject = storage.getClass(className);
                if (classObject == null){
                    System.out.println("Class does not exist");
                }else if(!classObject.hasField(oldFieldName)){
                    System.out.println("Field does not exist");
                }else{
                    classObject.renameField(oldFieldName,newFieldName,newFieldType);
                    System.out.println("you renamed field "+ oldFieldName+" to "+newFieldName+" with the type " + newFieldType);
                }
            }
        }
    }

    /**Commands tied to Methods*/
    public class MethodCommands{
        private final UMLModel.Storage storage;

        public MethodCommands(UMLModel.Storage storage){
            this.storage = storage;
        }

        // add(0) method(1) Class Name(2) Method Name(3)
        public void addMethod(final String[] input) {
            if(input.length < 3) {
                System.out.println("Invalid number of arguments. Usage: add method <className> <newMethodName>");
            } else {
                String className = input[2];
                if(!storage.list.containsKey(className)) {
                    System.out.println("Class " + className + " does not exist");
                } else {
                    String methodName = input[3];
                    UMLModel.Class classObject = storage.getClass(className);

                    if(classObject == null){
                        System.out.println("Class does not exist");
                    }else if(classObject.hasMethod(methodName)){
                        System.out.println("Class already contains this method");
                    }else{
                        classObject.addMethod(methodName);
                        System.out.println("Method added. Method name: " + methodName);
                    }
                }
            }
        }

        // add(0) method(1) Class Name(2) Method Name(3)
        public void removeMethod(final String[] input) {
            if(input.length < 3) {
                System.out.println("Invalid number of arguments. Usage: add method <className> <removingMethodName>");
            } else {
                String className = input[2];
                if(!storage.list.containsKey(className)) {
                    System.out.println("Class " + className + " does not exist");
                } else {
                    String methodName = input[3];
                    UMLModel.Class classObject = storage.getClass(className);

                    if(classObject == null){
                        System.out.println("Class does not exist");
                    }else if(!classObject.hasMethod(methodName)){
                        System.out.println("Class doesn't contains this method");
                    }else{
                        classObject.addMethod(methodName);
                        System.out.println("Method " + methodName + " removed");
                    }
                }
            }
        }

        // add(0) method(1) Class Name(2) Method Name(3) NewMethod Name(4)
        public void renameMethod(final String[] input) {
            if(input.length < 4) {
                System.out.println("Invalid number of arguments. Usage: add method <className> <oldMethodName> <newMethodName>");
            } else {
                String className = input[2];

                if(!storage.list.containsKey(className)) {
                    System.out.println("Class " + className + " does not exist");
                }else{
                    String oldMethodName = input[3];
                    String newMethodName = input[4];
                    UMLModel.Class classObject = storage.getClass(className);

                    if(classObject == null){
                        System.out.println("Class does not exist");
                        //Checks if the new name already exists
                    }else if(classObject.hasMethod(newMethodName)){
                        System.out.println("Class already contains method: " + newMethodName);
                        //Checks if the old name exists
                    }else if(!classObject.hasMethod(oldMethodName)) {
                        System.out.println("Class doesn't contains method: " + oldMethodName);
                    }else{

                        classObject.renameMethod(oldMethodName,newMethodName);
                        System.out.println("Method " + oldMethodName + " has been renamed to " + newMethodName);
                    }
                }
            }
        }
    }

    /**Commands tied to Parameters*/
    public class ParameterCommands{
        private final UMLModel.Storage storage;

        public ParameterCommands(UMLModel.Storage storage){
            this.storage = storage;
        }
        public void addParameter(final String[] input) {
            if (input.length <= 5) {
                System.out.println("Invalid number of arguments. Usage: add parameter <className> <methodName> <parameterName> <parameterType> ");
                return;
            }

            String className = input[2];
            String methodName = input[3];

            if(!storage.list.containsKey(className)) {
                System.out.println("Class " + className + " does not exist");
                return;
            }

            UMLModel.Class classObject = storage.getClass(className);
            if(!classObject.hasMethod(methodName)) {
                System.out.println("Method " + methodName + " does not exist");
                return;
            }

            UMLModel.Method method = classObject.getMethod(methodName);

            System.out.print("Enter parameter name: ");
            String parameterName = input[4];
            if(parameterName.isEmpty()) {
                System.out.println("Parameter name cannot be empty");
                return;
            }

            System.out.print("Enter parameter type: ");
            String parameterType = input[5];
            if(parameterType.isEmpty()) {
                System.out.println("Parameter type cannot be empty");
                return;
            }

            method.addParameter(parameterName, parameterType);
            System.out.println("Parameter " + parameterName + " added to " + className + " " + methodName);
        }

        public void removeParameter(String[] input) {
            if (input.length <= 4) {
                System.out.println("Invalid number of arguments. Usage: add parameter <className> <methodName> <parameterName> ");
                return;
            }

            String className = input[2];
            String methodName = input[3];

            if(!storage.list.containsKey(className)) {
                System.out.println("Class " + className + " does not exist");
                return;
            }

            UMLModel.Class classObject = storage.getClass(className);
            if(!classObject.hasMethod(methodName)) {
                System.out.println("Method " + methodName + " does not exist");
                return;
            }

            UMLModel.Method method = classObject.getMethod(methodName);
            System.out.print("Enter parameter name: ");
            String parameterName = input[4];
            if(parameterName.isEmpty()) {
                System.out.println("Parameter name cannot be empty");
                return;
            }

            if(!method.hasParameter(parameterName)) {
                System.out.println("Parameter " + parameterName + " does not exist");
            }

            method.deleteParameter(parameterName);
            System.out.println("Parameter " + parameterName + " removed from " + className + " " + methodName);

        }

        public void changeParameter(String[] input) {
            if (input.length <= 6) {
                System.out.println("Invalid number of arguments. Usage: rename parameter <className> <methodName> <parameterName> <newParameterName> <newParameterType>");
                return;
            }

            String className = input[2];
            String methodName = input[3];
            if(!storage.list.containsKey(className)) {
                System.out.println("Class " + className + " does not exist");
                return;
            }

            UMLModel.Class classObject = storage.getClass(className);
            if(!classObject.hasMethod(methodName)) {
                System.out.println("Method " + methodName + " does not exist");
                return;
            }
            String parameterName = input[4];
            String newParamName = input[5];
            String newParamType = input[6];
            UMLModel.Method method = classObject.getMethod(methodName);
            if(parameterName.isEmpty()) {
                System.out.println("Parameter name cannot be empty");
                return;
            }
            if(newParamName.isEmpty()) {
                System.out.println("New parameter name cannot be empty");
                return;
            }
            if(newParamType.isEmpty()) {
                System.out.println("New parameter type cannot be empty");
                return;
            }


            if(!method.hasParameter(parameterName)) {
                System.out.println("Parameter " + parameterName + " does not exist");
                return;
            }
            method.changeParameter(parameterName,newParamName, newParamType);
            System.out.println("Parameter " + parameterName + " renamed to " + newParamName + " with type " + newParamType);


            


        }
    }

    /**Commands tied to Relationship*/
    public class RelationshipCommands{
        private final UMLModel.Storage storage;

        public RelationshipCommands(UMLModel.Storage storage){
            this.storage = storage;
        }
        public boolean addRelationship(final String[] input){
            if(input.length <= 3){
                System.out.println("Invalid number of arguments");
                return false;

            }else{
                String source = input[2] ;
                String destination = input[3];


                //These if-statements check if the classes exist. If not the relationship cannot be created
                if(!storage.list.containsKey(source))System.out.println("Source Class does not exist");
                if(!storage.list.containsKey(destination)) System.out.println("Destination Class does not exist");

                UMLModel.Class srcClass = storage.getClass(source);



                srcClass.addRelation(source, destination);
                System.out.println("Successful");
                return true;
            }
        }

        public boolean removeRelationship(final String[] input){
            if(input.length <= 3){
                System.out.println("Invalid number of arguments");
                return false;
            }else{

                String source = input[2];
                String destination = input[3];

                //Checks to see the source class exists
                if(!storage.list.containsKey(source)) System.out.println("source class does not exist");

                UMLModel.Class srcClass = storage.getClass(source);
                UMLModel.Class destClass = storage.getClass(destination);

                Boolean removed = srcClass.removeRelation(source, destination);

                if(removed){
                    System.out.println("Successful");
                    return true;
                }else {
                    System.out.println("Successful");
                    return false;
                }
            }
        }
    }

}
