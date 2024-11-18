package org.project.TestDrivenDevelopment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.project.Controller.ClassNodeService;
import org.project.Model.UMLClassNode;

public class MockClassNodeTDD {

  @Mock
  private UMLClassNode classNode; // Keep this as @Mock

  @InjectMocks
  private ClassNodeService classNodeService; // Use @InjectMocks here only

  @BeforeEach
  public void setUp() {
    // Initialize mocks
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testCreateClassNode() {
    // Create JSON data as input for the service (you can replace {...} with actual values)
    Gson gson = new Gson();
    JsonObject jsonData = gson.fromJson("""
      {
          "className": "ExampleClass",
          "fields": [
              {"type": "String", "name": "exampleField"},
              {"type": "int", "name": "exampleNumber"}
          ],
          "methods": [
              {"type": "void", "name": "exampleMethod", "parameters": [
                  {"type": "String", "name": "param1"},
                  {"type": "int", "name": "param2"}
              ], "isOverloaded": false}
          ],
          "relationships": [
              {"type": "inheritance", "target": "ParentClass"}
          ]
      }
      """, JsonObject.class);

    // Create a real instance of UMLClassNode with expected values for verification
    UMLClassNode expectedNode = classNodeService.createClassNodeFromJson(jsonData);

    // Mock behaviors of classNode based on expected values
    when(classNode.getClassName()).thenReturn(expectedNode.getClassName());
    when(classNode.getFields()).thenReturn(expectedNode.getFields());
    when(classNode.getMethods()).thenReturn(expectedNode.getMethods());
    when(classNode.getRelationships()).thenReturn(expectedNode.getRelationships());

    // Verify class name
    assertEquals("ExampleClass", expectedNode.getClassName());

    // Verify fields
    assertEquals(2, expectedNode.getFields().size());
    assertEquals("String", expectedNode.getFields().get(0).getType());
    assertEquals("exampleField", expectedNode.getFields().get(0).getName());

    // Verify methods
    assertEquals(1, expectedNode.getMethods().size());
    assertEquals("exampleMethod", expectedNode.getMethods().get(0).getName());
    assertEquals("void", expectedNode.getMethods().get(0).getType());

    // Verify relationships
    assertEquals(1, expectedNode.getRelationships().size());
    assertEquals("inheritance", expectedNode.getRelationships().get(0).getType());
  }

  @Test
  public void visually_see_class_node() {
    // Set up JSON data to create an example UMLClassNode
    Gson gson = new Gson();
    JsonObject jsonData = gson.fromJson("""
      {
          "className": "ExampleClass",
          "fields": [
              {"type": "String", "name": "exampleField"},
              {"type": "int", "name": "exampleNumber"}
          ],
          "methods": [
              {"type": "void", "name": "exampleMethod", "parameters": [
                  {"type": "String", "name": "param1"},
                  {"type": "int", "name": "param2"}
              ], "isOverloaded": false}
          ],
          "relationships": [
              {"type": "inheritance", "target": "ParentClass"}
          ]
      }
      """, JsonObject.class);

    // Create a UMLClassNode from JSON
    UMLClassNode node = classNodeService.createClassNodeFromJson(jsonData);

    // Print the node to visually inspect its contents
    System.out.println(node);
  }
}
