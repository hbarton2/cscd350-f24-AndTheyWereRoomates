package org.project.TestDrivenDevelopment;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.ComboBox;
import org.mockito.Mock;
import org.project.Controller.GraphicalUserInterfaceController;
import org.project.View.GraphicalClassNode;

public class GUIViewRefractTDD {

  //@Mock UMLController umlController;

  @Mock private Canvas canvas;

  @Mock ComboBox<String> fromComboBox;

  @Mock ComboBox<String> toComboBox;

  @Mock ComboBox<String> dataTypeComboBox;

  @Mock ComboBox<String> parameterTypeComboBox;

  @Mock GraphicalClassNode classBox;

  GraphicalUserInterfaceController controller; // Replace with the actual controller class name

  //  @BeforeEach
  //  void setUp() {
  //      MockitoAnnotations.openMocks(this);
  //      controller = new GraphicalUserInterfaceController();
  //      controller.canvas = canvas;
  //      controller.fromComboBox = fromComboBox;
  //      controller.toComboBox = toComboBox;
  //      controller.dataTypeComboBox = dataTypeComboBox;
  //      controller.parameterTypeComboBox = parameterTypeComboBox;
  //      controller.umlController = umlController;
  //  }
  //
  //  @Test
  //  public void testCreateClassSuccessfully() {
  //      // Setup mock behavior
  //      when(umlController.getStorage().hasClass(anyString())).thenReturn(false);  // Simulate no
  // existing class
  //      when(classBox.getPrefWidth()).thenReturn(100.0);
  //      when(classBox.getPrefHeight()).thenReturn(100.0);
  //      when(classBox.getName()).thenReturn("TestClass");
  //      when(canvas.getWidth()).thenReturn(500.0);
  //      when(canvas.getHeight()).thenReturn(500.0);
  //
  //      // Setup the expected input values
  //      String[] inspectorValues = {"TestClass", "testField", "String", "testMethod", "param1",
  // "String"};
  //      yourController.getInspectorValues = () -> inspectorValues;
  //
  //      // Call createClass
  //      yourController.createClass(null);  // Pass ActionEvent if needed
  //
  //      // Verify interactions with mocked components
  //      verify(umlController).getStorage();
  //      verify(umlController.classCommands).addClass(any());
  //      verify(canvas).getChildren();  // Ensure the classBox is added to canvas
  //
  //      // Verify ComboBox updates
  //      verify(fromComboBox).getItems().add("TestClass");
  //      verify(toComboBox).getItems().add("TestClass");
  //  }
  //
  //  @Test
  //  void testCreateClassWithExistingName() {
  //      // Setup mock behavior to simulate existing class
  //      when(umlController.getStorage().hasClass("TestClass")).thenReturn(true);
  //
  //      // Call createClass with conflicting name
  //      yourController.createClass(null);
  //
  //      // Verify that the alert is shown (you can verify the showAlert method call)
  //      verify(yourController).showAlert(eq("Class Creation Error"), eq("A class with the name
  // \"TestClass\" already exists."));
  //  }
}
