package org.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import org.project.Controller.ClassNodeService;
import org.project.Model.Storage;
import org.project.Model.UMLClassNode;

/*
    Top-down Integration Test: ClassNodeService with Storage
 */
public class IntegrationTestClassNode {
    @Test
    void testClassNodeServiceWithStorageIntegration() throws Exception {
        // Initialize ClassNodeService and Storage
        ClassNodeService classNodeService = new ClassNodeService();
        Storage storage = Storage.getInstance(); // Retrieve the singleton instance of Storage
        storage.clearStorage(); // Clear storage to start fresh

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
        UMLClassNode classNode = classNodeService.createClassNodeFromJson(jsonNode);

        // Store the node in Storage
        storage.addNode(classNode.getClassName(), classNode);

        // Validate Storage
        assertTrue(storage.containsNode("Car"));
        UMLClassNode retrievedNode = storage.getNode("Car");
        assertEquals("Car", retrievedNode.getClassName());
        assertEquals(1, retrievedNode.getFields().size());
        assertEquals("color", retrievedNode.getFields().get(0).getName());
        assertEquals(1, retrievedNode.getMethods().size());
        assertEquals("drive", retrievedNode.getMethods().get(0).getName());
        assertEquals(1, retrievedNode.getRelationships().size());
        assertEquals("Wheel", retrievedNode.getRelationships().get(0).getTarget());
    }
}
