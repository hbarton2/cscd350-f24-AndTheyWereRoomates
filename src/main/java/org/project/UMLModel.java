package org.project;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
public class UMLModel {
    public static class Class {
        /** Name of the class. */
        private String name;
        /** A list of methods. */
        public ArrayList<Method> methodlist;
        /** A list of fields. */
        public ArrayList<Field> fields;
        /** A list of relationships. */
        public ArrayList<Relationship> relation;

        /**
         * Constructor to create a new class.
         * @param name - The name of the class.
         */
        public Class(final String name) {
            this.name = name;
            this.methodlist = new ArrayList<>();
            this.fields = new ArrayList<>();
            this.relation = new ArrayList<>();
        }

        /**
         * Adds a method to the class
         * @param methodName - The name of the method to add.
         * @return - True if the method was added successfully, otherwise returns false.
         * @throws IllegalArgumentException if the methodName is null or empty.
         */
        public boolean addMethod(final String methodName) {
            if (methodName == null || methodName.isEmpty()) {
                throw new IllegalArgumentException("Invalid method name, try again");
            }
            return methodlist.add(new Method(methodName));
        }

        /**
         * Removes a method from the class
         * @param methodName - The name of the method to remove
         * @return - True if the method was removed successfully, otherwise returns false.
         * @throws IllegalArgumentException if the methodName is null or empty.
         */
        public boolean removeMethod(final String methodName) {
            if (methodName == null || methodName.isEmpty()) {
                throw new IllegalArgumentException("Invalid method name, try again");
            }
            ArrayList<Method> matchingMethods = new ArrayList<>();

            for (Method method : methodlist) {
                if (method.getName().equals(methodName)) {
                    matchingMethods.add(method);
                }
            }
            if (matchingMethods.isEmpty()) {
                return false;
            }
            if (matchingMethods.size() == 1) {
                methodlist.remove(matchingMethods.get(0));
                System.out.println("Removed method ");
                return true;
            }


            for (int i = 0; i < matchingMethods.size(); i++) {
                Method method = matchingMethods.get(i);
                System.out.println((i + 1) + ": " + overloadHelper(method));
            }
            Scanner scanner = new Scanner(System.in);
            System.out.println("Choose a number to delete that method: ");
            int choice = scanner.nextInt();

            if (choice < 1 || choice > matchingMethods.size()) {
                System.out.println("Try again");
                return false;
            }
            methodlist.remove(matchingMethods.get(choice - 1));
            System.out.println("Removed successfully");
            return true;
        }
        private String overloadHelper(Method method) {
            String data = method.getName() + "(";
            ArrayList<Parameter> params = method.getParameter();

            for (int i = 0; i < params.size(); i++) {
                Parameter param = params.get(i);
                data += param.getName() + " " + param.getType();

                if (i < params.size() - 1) {
                    data += ", ";
                }
            }
            data += ")";
            return data;
        }

        /**
         * Renames a method
         * @param oldName - The current name of the method
         * @param newName - The new name of the method
         * @return - True if the method was renamed successfully, otherwise returns false.
         * @throws IllegalArgumentException if oldName is null or empty or newName is null or empty.
         */
        public boolean renameMethod(final String oldName, final String newName) {
            if (oldName == null || oldName.isEmpty() || newName == null || newName.isEmpty()) {
                throw new IllegalArgumentException("Invalid oldName or newName, try again");
            }
            for (Method method: methodlist) {
                if (method.getName().equals(newName)) {
                    return false;
                }
            }
            for (Method method: methodlist) {
                if (method.getName().equals(oldName)) {
                    method.setName(newName);
                    return true;
                }
            }
            return false;
        }

        /**
         * Removes a relationship between the source and destination classes.
         * @param sourceName - The name of the source class.
         * @param dest - The name of the destination class.
         * @return - True if the relationship was removed successfully, otherwise returns false.
         * @throws IllegalArgumentException if the sourceName or destination is null or empty.
         */
        public boolean removeRelation(final String sourceName, final String dest) {
            if (sourceName == null || sourceName.isEmpty() || dest == null || dest.isEmpty()) {
                throw new IllegalArgumentException("Invalid sourceName or destName, try again");
            }
            Iterator<Relationship> iterator = relation.iterator();
            while (iterator.hasNext()) {
                Relationship relationship = iterator.next();
                if (relationship.getSource().equals(sourceName) && relationship.getDestination().equals(dest)) {
                    iterator.remove();
                    return true;
                }
            }
            return false;
        }


        /**
         * Gets the name of the class.
         * @return - The name of the class.
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the name of the class.
         * @param name - The name to assign to the class.
         */
        public void setName(final String name) {
            this.name = name;
        }

        /**
         * Adds a field to the class.
         * @param name - The name of the field.
         * @param type - The type of the field.
         * @return true if the field was added successfully, otherwise returns false.
         * @throws IllegalArgumentException if the name is null or empty.
         */
        public boolean addField(final String name, final String type){
            if(name == null || name.isEmpty()) throw new IllegalArgumentException("Invalid name, try again");

            if(hasField(name)) return false;

            return fields.add(new Field(name, type));
        }

        /**
         * Removes a field from the class
         * @param name - The name of the field to remove.
         * @return true if the field was removed successfully, otherwise returns false.
         * @throws IllegalArgumentException if the name is null or empty.
         */
        public boolean removeField(final String name){
            if(name == null || name.isEmpty()) throw new IllegalArgumentException("Invalid name, try again");

            if(!hasField(name)) return false;

            Field field = findField(name);

            return fields.remove(field);

        }

        /**
         * Renames a field
         * @param old - The current name of the field
         * @param newName - The new name of the field
         * @return true if the field was renamed successfully, otherwise returns false.
         */
        public boolean renameField(final String old, final String newName){
            if(name == null || name.isEmpty()) throw new IllegalArgumentException("Invalid name, try again");

            if(!hasField(old)) return false;

            Field field = findField(old);

            field.setName(newName);
            return true;
        }

        /**
         * Finds a field by its name
         * @param name - The name of the field
         * @return The field if found, otherwise returns null.
         * @throws IllegalArgumentException if the source or destination is null or empty.
         */
        private Field findField(final String name){
            for(Field field: fields){
                if(field.getName().equals(name)){
                    return field;
                }
            }
            return null;
        }

        /**
         * Checks if the class has a field
         * @param name - The name of the field
         * @return true if the field exists, otherwise returns false.
         */
        private boolean hasField(final String name){
            for(Field field: fields){
                if(field.getName().equals(name)){
                    return true;
                }
            }
            return false;
        }

        /**
         * Adds a relationship between the source and destination classes.
         //* @param source - The source class
         //* @param dest - The destination class.
         * @return true if the relationships was added successfully, otherwise returns false.
         * @throws IllegalArgumentException if the source or destination is null or empty.
         */
        public boolean hasMethod(String methodName){
            for(Method method: methodlist){
                if(method.getName().equals(methodName)){
                    return true;
                }
            }

            return false;
        }

        public Method getMethod(String methodName){
            for(Method method: methodlist){
                if(method.getName().equals(methodName) && (method.getParameter().isEmpty())) {
                    return method;
                }
            }
            return null;
        }

        /**
         * Adds a relationship between the source and destination classes.
         * @param source - The source class
         * @param dest - The destination class.
         * @return true if the relationships was added successfully, otherwise returns false.
         * @throws IllegalArgumentException if the source or destination is null or empty.
         */
        public boolean addRelation(final String source, final String dest){
            if(source == null || source.isEmpty()) throw new IllegalArgumentException("Invalid source, try again");
            if(dest == null || dest.isEmpty()) throw new IllegalArgumentException("Invalid destination, try again");

            return relation.add(new Relationship(source, dest));


        }
    }
    public static class Field {
        /**
         * The name of the field.
         */
        private String name;
        /**
         * The type of the field.
         */
        private String type;

        /**
         * Constructs a new Field.
         * @param name - The name of the field.
         * @param type - The type of the field.
         */
        public Field (final String name, final String type){
            this.name = name;
            this.type = type;
        }

        /**
         * Gets the name of the field.
         * @return the name of the field.
         */
        public String getName(){
            return this.name;
        }

        /**
         * Gets the type of the field.
         * @return the type of the field.
         */
        public String getType(){
            return this.type;
        }

        /**
         * Sets the name of the field.
         * @param name - The new name of the field.
         */
        public void setName(final String name) {
            this.name = name;
        }

        /**
         * Sets the type of the field.
         * @param type - The new type of the field.
         */
        public void setType(final String type) {
            this.type = type;
        }
    }
    public static class Method {
        /**
         * The name of the method.
         */
        private String name;
        /**
         * A list of parameters.
         */
        public ArrayList<Parameter> parameters;

        /**
         * Constructor to create a new Method.
         * @param name - The name of the method.
         */
        public Method(final String name){
            this.name = name;
            this.parameters = new ArrayList<>();
        }

        /**
         * Adds a parameter to the method.
         * @param parameterName - The name of the parameter.
         * @param parameterType - The type of the parameter.
         * @return true if the parameter was added, otherwise returns false.
         */
        public boolean addParameter(final String parameterName, final String parameterType){

            if(parameterName.isBlank() || parameterName == null || parameterType.isBlank() || parameterType == null){
                System.out.println("Inputs were Null/Blank");
                return false;
            }

            if(hasParameter(parameterName)){
                System.out.println("This method already has this parameter");
                return false;
            }

            parameters.add(new Parameter(parameterName,parameterType));
            return true;
        }

        /**
         * Changes an existing parameter with a new name and type.
         * @param oldParamName - Current name of the parameter.
         * @param newParamName - New name for the parameter.
         * @param newParamType - The new type of the parameter.
         * @return true if the parameter was successfully changed, otherwise returns false.
         */
        public boolean changeParameter(final String oldParamName, final String newParamName, final String newParamType){

            if(newParamName.isBlank() || newParamName == null || newParamType.isBlank() || newParamType == null || oldParamName.isBlank() || oldParamName == null){
                System.out.println("Inputs were Null/Blank");
                return false;
            }

            if(!oldParamName.equals(newParamName) && hasParameter(newParamName)){
                System.out.println("This method already has this parameter");
                return false;
            }

            for(Parameter param: parameters){
                if(param.getName().equals(oldParamName)){
                    param.setName(newParamName);
                    param.setType(newParamType);
                    System.out.println("The parameter has been changed to " + newParamName + ", param of type: " + newParamType);
                    return true;
                }
            }

            System.out.println("Parameter not found try another name");
            return false;
        }

        /**
         * Deletes a parameter from the method by its name.
         * @param parameterName - The name of the parameter.
         * @return true if the parameter was successfully deleted, otherwise returns false.
         */
        public boolean deleteParameter(final String parameterName){

            if(parameterName.isBlank() || parameterName == null){
                System.out.println("Input was Null/Blank");
                return false;
            }

            for(Parameter param: parameters){
                if(param.getName().equals(parameterName)){
                    parameters.remove(param);
                    return true;
                }
            }

            System.out.println("Parameter was not found in method");
            return false;
        }

        /**
         * Deletes all the parameters from the method.
         */
        public void deleteAllParameter(){
            this.parameters.clear();
            System.out.println("All parameters in method has been deleted");
        }

        /**
         * Checks if a parameter with the name provided exists.
         * @param parameterName - The name of the parameter.
         * @return true if the parameter exists, otherwise returns false.
         */
        public boolean hasParameter(final String parameterName){

            if(parameterName.isBlank() || parameterName == null){
                System.out.println("Input was Null/Blank");
                return false;
            }

            for(Parameter param: parameters){
                if(param.getName().equals(parameterName)){
                    return true;
                }
            }
            return false;
        }

        /**
         * Gets the name of the method.
         * @return the name of the method.
         */
        public String getName(){
            return this.name;
        }

        /**
         * Sets the name of the method.
         * @param name - The new name for the method.
         */
        public void setName(final String name){
            this.name = name;
        }

        /**
         * Gets the list of parameters of the method.
         * @return the list of parameters.
         */
        public ArrayList<Parameter> getParameter(){
            return parameters;
        }
    }
    public static class Parameter {
        /**
         * The name of the parameter.
         */
        private String name;
        /**
         * The type of the parameter.
         */
        private String type;
        /**
         * Constructs a new Parameter.
         * @param name - The name of the parameter.
         * @param type - The type of the parameter.
         */
        public Parameter (final String name, final String type){
            this.name = name;
            this.type = type;
        }

        /**
         * Gets the name of the parameter.
         * @return the name of the parameter.
         */
        public String getName(){
            return this.name;
        }

        /**
         * Gets the type of the parameter.
         * @return the type of the parameter.
         */
        public String getType(){
            return this.type;
        }

        /**
         * Sets the name of the parameter.
         * @param name - The name of the parameter.
         */
        public void setName(final String name){
            this.name = name;
        }

        /**
         * Sets the type of the parameter.
         * @param type - The new type of the parameter.
         */
        public void setType(final String type){
            this.type = type;
        }
    }
    public static class Relationship {
        /**
         * The source of the relationship.
         */
        private String source;
        /**
         * The destination of the relationship.
         */
        private String destination;

        /**
         * Constructs a new Relationship.
         * @param source - The source of the relationship.
         * @param destination - The destination of the relatioship.
         */
        public Relationship (final String source, final String destination){
            this.source = source;
            this.destination = destination;
        }

        /**
         * Gets the source of the relationship.
         * @return the source of the relationship.
         */
        public String getSource(){
            return this.source;
        }

        /**
         * Gets the destination of the relationship.
         * @return the destination of the relationship.
         */
        public String getDestination(){
            return this.destination;
        }

        /**
         * Sets the source of the relationship.
         * @param source - The new source of the relationship.
         */
        public void setSource(final String source) {
            this.source = source;
        }

        /**
         * Sets the destination of the relationship.
         * @param destination - The new destination of the relationship.
         */
        public void setDestination(final String destination) {
            this.destination = destination;
        }
    }
    public static class Load {

        private Storage storage;


        public Load(Storage storage){
            this.storage = storage;
        }

        public boolean Load(String fileName) {

            try {
                FileReader reader = new FileReader(fileName);
                Gson gson = new Gson();
                Type type = new TypeToken<TreeMap<String, org.project.Class>>() {}.getType();
                this.storage.list = gson.fromJson(reader, type);
                reader.close();
                return true;
            }catch(IOException e){
                e.printStackTrace();
            }
            return false;
        }
    }
    public static class Save {

        private Storage storage;



        public Save(Storage storage){
            this.storage = storage;
        }


        public boolean save(String fileName){

            try{
                FileWriter writer = new FileWriter(fileName);
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(this.storage.list, writer);

                writer.close();

                return true;


            }catch (IOException e){
                e.printStackTrace();
            }

            return false;



        }
    }
    public static class Storage {
        /**
         * A map to store the classes.
         */
        public TreeMap<String, UMLModel.Class> list = new TreeMap<>();

        /**
         * addClass adds a class to the stored list.
         * @param name of the class being added.
         * @throws IllegalArgumentException if name is null or empty.
         */
        public boolean addClass(String name) throws IllegalArgumentException{
            if (name == null || name.isEmpty()) throw new IllegalArgumentException("Invalid name, try again");
            list.put(name, new UMLModel.Class(name));
            return true;
        }

        /**
         * Retrieves a class.
         * @param name - The name of the class.
         * @return the class if found.
         */
        public UMLModel.Class getClass(final String name) {
            return this.list.get(name);
        }

        /**
         * deleteClass removes a class by name from the stored list
         * @param name of the class being removed.
         * @throws IllegalArgumentException thrown if name is empty or null.
         */
        public boolean deleteClass(String name) throws IllegalArgumentException{
            if (name == null || name.isEmpty()) throw new IllegalArgumentException("Invalid name, try again");
            if (list.get(name) == null){
                System.out.println("Name of class not in list");
                return false;
            }
            list.remove(name);
            return true;

        }

        /**
         * renameClass changes the name field of the class and changes how it's accessed by the list.
         * @param oldName of the class being renamed.
         * @param newName is the new name of the class.
         * @throws IllegalArgumentException If either oldName or newName are null or empty.
         */
        public boolean renameClass(String oldName, String newName) throws IllegalArgumentException{
            if(oldName == null || oldName.isEmpty()) throw new IllegalArgumentException("Invalid old name, try again");
            if(newName == null || newName.isEmpty()) throw new IllegalArgumentException("Invalid new name, try again");
            if(list.get(oldName) == null){
                System.out.println("Old name is not a valid option in the list");
                return false;
            }
            UMLModel.Class placeholder = list.remove(oldName);
            placeholder.setName(newName);
            list.put(newName, placeholder);
            return true;


        }
    }
}
