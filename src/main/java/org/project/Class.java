package org.project;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents Class with its constructor, fields, and methods.
 */
public class Class {
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
      Iterator<Method> iterator = methodlist.iterator();
      while (iterator.hasNext()) {
         Method method = iterator.next();
         if (method.getName().equals(methodName)) {
            iterator.remove();
            return true;
         }
      }
      return false;
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

