package org.project;

import java.util.ArrayList;
import java.util.Iterator;

public class Class {
   private String name;
   private ArrayList<Method> methodlist;
   private ArrayList<Field> fields;
   private ArrayList<Relationship> relation;

   public Class(String name) {
      this.name = name;
      this.methodlist = new ArrayList<>();
      this.fields = new ArrayList<>();
      this.relation = new ArrayList<>();
   }

   public boolean addMethod(String methodName) {
      if (methodName == null || methodName.isEmpty()) {
         throw new IllegalArgumentException("Invalid method name, try again");
      }
       return methodlist.add(new Method(methodName));
   }

   public boolean removeMethod(String methodName) {
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

   public boolean renameMethod(String oldName, String newName) {
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

   public boolean removeRelation(String sourceName, String dest) {
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



   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }
}

