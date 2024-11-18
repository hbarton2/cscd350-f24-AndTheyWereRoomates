package org.project.TestDrivenDevelopment;


import org.junit.jupiter.api.Test;



import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;

import org.project.Model.UMLModel;

import static org.junit.jupiter.api.Assertions.*;
public class PrintNodeTDD {
  private UMLModel.Storage storage;
  private UMLModel.Class exampleClass;

  @Test
  public void testBlah(){System.out.println("blah");}

  @BeforeEach
  void setUp() {
    storage = new UMLModel.Storage();
    exampleClass = new UMLModel.Class("blah");
  }

  @Test
  public void wholeLotOfBlah(){
    assertTrue(exampleClass.addField("blah", "String"));
    assertTrue(exampleClass.addField("blahh", "int"));
    assertEquals(2, exampleClass.getFields().size());

    UMLModel.Field field1 =exampleClass.getFields().get(0);
    UMLModel.Field field2 = exampleClass.getFields().get(1);
    assertEquals("blah", field1.getName());
    assertEquals("String", field1.getType());
    assertEquals("blahh", field2.getName());
    assertEquals("int", field2.getType());

    UMLModel.Method method1 = new UMLModel.Method("blah");
    method1.addParameter("blah1", "String");
    method1.addParameter("blah2", "int");
    assertTrue(exampleClass.addMethod("blah"));
    exampleClass.getMethodList().add(method1);

    UMLModel.Method overloadedMethod = new UMLModel.Method("blah");
    overloadedMethod.addParameter("blah", "String");
    assertTrue(exampleClass.addMethod("blah"));
    exampleClass.getMethodList().add(overloadedMethod);

    assertEquals(2, exampleClass.getMethodList().size());

    UMLModel.Method retrievedMethod1 = exampleClass.getMethodList().get(0);
    assertEquals("blah", retrievedMethod1.getName());
    assertEquals(2, retrievedMethod1.getParameter().size());
    assertEquals("blah", retrievedMethod1.getParameter().get(0).getName());
    assertEquals("String", retrievedMethod1.getParameter().get(0).getType());
    UMLModel.Method retrievedOverloadedMethod =exampleClass.getMethodList().get(1);
    assertEquals("blah", retrievedOverloadedMethod.getName());
    assertEquals(1, retrievedOverloadedMethod.getParameter().size());
    assertEquals("blah", retrievedOverloadedMethod.getParameter().get(0).getName());
    assertEquals("String", retrievedOverloadedMethod.getParameter().get(0).getType());

    assertTrue(exampleClass.addRelation("blah", "blah2", "inheritance"));
    assertTrue(exampleClass.addRelation("blah2", "blah3", "association"));
    assertEquals(2, exampleClass.getRelationships().size());

    UMLModel.Relationship relationship1 =exampleClass.getRelationships().get(0);
    UMLModel.Relationship relationship2 =exampleClass.getRelationships().get(1);
    assertEquals("blah", relationship1.getDestination());
    assertEquals("inheritance", relationship1.getType());
    assertEquals("blah2", relationship2.getDestination());
    assertEquals("association", relationship2.getType());

    storage.addClass(exampleClass.getName());
    storage.list.put(exampleClass.getName(), exampleClass);

    UMLModel.Class retrievedClass = storage.getClass("blah");
    assertNotNull(retrievedClass);
    assertEquals("blah", retrievedClass.getName());

    assertEquals(2, retrievedClass.getFields().size());
    assertEquals(2, retrievedClass.getMethodList().size());
    assertEquals(2, retrievedClass.getRelationships().size());

  }
  @Test
  void testNodesClassStorageJsonFile() {
    String filePath = "src/main/java/org/project/Model/NodesClassStorage.json";

    try (FileReader reader = new FileReader(filePath)) {

      assertTrue(exampleClass.addField("blah", "String"));
      assertTrue(exampleClass.addField("blahh", "int"));
      assertEquals(4, exampleClass.getFields().size());

      UMLModel.Field field1 = exampleClass.getFields().get(0);
      UMLModel.Field field2 = exampleClass.getFields().get(1);
      assertEquals("blah", field1.getName());
      assertEquals("String", field1.getType());
      assertEquals("blahh", field2.getName());
      assertEquals("int", field2.getType());

      // Add methods correctly without duplicating entries
      UMLModel.Method method1 = new UMLModel.Method("blah");
      method1.addParameter("blah1", "String");
      method1.addParameter("blah2", "int");
      exampleClass.getMethodList().add(method1); // Only add once without using addMethod() redundantly

      UMLModel.Method overloadedMethod = new UMLModel.Method("blah");
      overloadedMethod.addParameter("blah", "String");
      exampleClass.getMethodList().add(overloadedMethod);

      assertEquals(6, exampleClass.getMethodList().size());

      UMLModel.Method retrievedMethod1 = exampleClass.getMethodList().get(0);
      assertEquals("blah", retrievedMethod1.getName());
      assertEquals(2, retrievedMethod1.getParameter().size());
      assertEquals("blah1", retrievedMethod1.getParameter().get(0).getName());
      assertEquals("String", retrievedMethod1.getParameter().get(0).getType());

      UMLModel.Method retrievedOverloadedMethod = exampleClass.getMethodList().get(1);
      assertEquals("blah", retrievedOverloadedMethod.getName());
      assertEquals(1, retrievedOverloadedMethod.getParameter().size());
      assertEquals("blah", retrievedOverloadedMethod.getParameter().get(0).getName());
      assertEquals("String", retrievedOverloadedMethod.getParameter().get(0).getType());

      // Add relationships correctly
      assertTrue(exampleClass.addRelation("blah", "blah2", "inheritance"));
      assertTrue(exampleClass.addRelation("blah2", "blah3", "association"));
      assertEquals(2, exampleClass.getRelationships().size());

      UMLModel.Relationship relationship1 = exampleClass.getRelationships().get(0);
      UMLModel.Relationship relationship2 = exampleClass.getRelationships().get(1);
      assertEquals("blah2", relationship1.getDestination());
      assertEquals("inheritance", relationship1.getType());
      assertEquals("blah3", relationship2.getDestination());
      assertEquals("association", relationship2.getType());

      // Add class to storage with the correct name
      storage.addClass(exampleClass.getName());
      UMLModel.Class retrievedClass = storage.getClass(exampleClass.getName());
      assertNotNull(retrievedClass);
      assertEquals("ExampleClass", retrievedClass.getName());

      // Verify the entire structure
      assertEquals(2, retrievedClass.getFields().size());
      assertEquals(2, retrievedClass.getMethodList().size());
      assertEquals(2, retrievedClass.getRelationships().size());

    } catch (IOException e) {
      fail("Failed to read NodesClassStorage.json file: " + e.getMessage());
    }
  }



}

