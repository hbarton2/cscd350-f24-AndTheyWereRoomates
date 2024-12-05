package org.project.IntegrationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.Controller.ClassNodeService;
import org.project.Model.Storage;
import org.project.Model.UMLClassNode;

/*
   Top-down Integration Test: ClassNodeService with Storage
*/
// TODO: All test failed due to boolean and need to be fixed integration failure
public class ClassNodeIntegrationTest {

  private Storage storage;
  private ClassNodeService classNodeService;
  private UMLClassNode classNode;

  @BeforeEach
  void setUp() throws Exception {
    // Initialize ClassNodeService and Storage
    classNodeService = new ClassNodeService();
    storage = Storage.getInstance();
    storage.clearStorage();

    // Create a JSON object for a UMLClassNode
    JsonObject jsonNode = new JsonObject();
    jsonNode.addProperty("className", "Car");

    JsonArray fields = new JsonArray();
    JsonObject field = new JsonObject();
    field.addProperty("type", "String");
    field.addProperty("name", "color");
    fields.add(field);
    jsonNode.add("fields", fields);

    JsonArray methods = new JsonArray();
    JsonObject method = new JsonObject();
    method.addProperty("type", "void");
    method.addProperty("name", "drive");
    JsonArray parameters = new JsonArray();
    method.add("parameters", parameters);
    methods.add(method);
    jsonNode.add("methods", methods);

    JsonArray relationships = new JsonArray();
    JsonObject relationship = new JsonObject();
    relationship.addProperty("type", "association");
    relationship.addProperty("target", "Wheel");
    relationships.add(relationship);
    jsonNode.add("relationships", relationships);

    // Create UMLClassNode using ClassNodeService
    classNode = classNodeService.createClassNodeFromJson(jsonNode);

    // Store the node in Storage
    storage.addNode(classNode.getClassName(), classNode);
  }

  @Test
  void testClassExistsInStorage() {
    assertTrue(storage.containsNode("Car"), "Class 'Car' should exist in storage");
  }

  @Test
  void testClassName() {
    UMLClassNode retrievedNode = storage.getNode("Car");
    assertEquals("Car", retrievedNode.getClassName(), "Class name should be 'Car'");
  }

  @Test
  void testFieldExists() {
    UMLClassNode retrievedNode = storage.getNode("Car");
    assertEquals(1, retrievedNode.getFields().size(), "Class should have exactly one field");
    assertEquals(
        "color", retrievedNode.getFields().get(0).getName(), "Field name should be 'color' ");
  }

  @Test
  void testMethodExists() {
    UMLClassNode retrievedNode = storage.getNode("Car");
    assertEquals(1, retrievedNode.getMethods().size(), "Class should have exactly one method");
    assertEquals(
        "drive", retrievedNode.getMethods().get(0).getName(), "Method name should be 'drive' ");
  }

  @Test
  void testRelationshipExists() {
    UMLClassNode retrievedNode = storage.getNode("Car");
    assertEquals(
        1, retrievedNode.getRelationships().size(), "Class should have exactly one relationship");
    assertEquals(
        "Wheel",
        retrievedNode.getRelationships().get(0).getTarget(),
        "Relationship target should be 'Wheel' ");
  }
}
