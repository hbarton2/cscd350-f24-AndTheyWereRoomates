import org.junit.jupiter.api.*;
import org.project.Controller.*;
import org.w3c.dom.html.HTMLButtonElement;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;

import static org.junit.jupiter.api.Assertions.*;
public class UMLControllerTests {

    private final UMLController test = new UMLController();
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private final ByteArrayOutputStream err = new ByteArrayOutputStream();
    private final PrintStream ogOutput = System.out;
    private final PrintStream ogErr = System.err;

    @Nested
    @DisplayName("Preconditions")
    public class Preconditions{
        @BeforeEach
        public void setup(){
            System.setOut(new PrintStream(output));
            System.setErr(new PrintStream(err));
            test.classCommands.addClass(new String[]{"add", "class", "exampleClass"});

        }
        @AfterEach
        public void restore(){
            System.setOut(ogOutput);
            System.setErr(ogErr);
        }

        @Nested
        @DisplayName("Class preconditions")
        public class ClassPreconditions{

            @Test
            @DisplayName("Incorrect Number of parameters for add class")
            public void incorrectNumberOfParamsAddClass(){
                try {
                    test.classCommands.addClass(new String[]{"add"});
                    assertEquals("Invalid number of arguments. Usage: add class <classname>\r\n", output.toString(),"Incorrect error output message incorrect number of params add class ");
                }catch(IllegalArgumentException e){
                    fail("IllegalArgumentException thrown when incorrect number of params");
                }
            }
            @Test
            @DisplayName("Adding Blank Class Returns Correctly")
            public void addingBlankClassReturnsPrompt(){

                try{
                    test.classCommands.addClass(new String[]{"add", "class",});
                    assertEquals("Invalid number of arguments. Usage: add class <classname>\r\n", output.toString(),"Incorrect error output message adding blank class");
                }catch(IllegalArgumentException e){
                    fail("IllegalArgumentException thrown when name is blank.");
                }catch(ArrayIndexOutOfBoundsException f){
                    fail("Index out of bounds exception.");
                }

            }
            @Test
            @DisplayName("Incorrect Number of parameters for remove class")
            public void incorrectNumberOfParamsRemoveClass(){
                try {
                    test.classCommands.removeClass(new String[]{"remove", "class"});
                    assertEquals("Invalid number of arguments. Usage: remove class <classname>\r\n", output.toString(),"Incorrect error output message removeClass");
                }catch(ArrayIndexOutOfBoundsException e){
                    fail("Index out of bounds exception thrown when invalid number of arguments.");
                }catch(IllegalArgumentException e){
                    fail("IllegalArgumentException thrown when incorrect number of params");
                }
            }
            @Test
            @DisplayName("Incorrect Number of parameters for rename class")
            public void incorrectNumberOfParamsRenameClass(){
                try {
                    test.classCommands.renameClass(new String[]{"rename", "class", "className"});
                    assertEquals("Invalid number of arguments. Usage: rename class <old classname> <new classname>\r\n", output.toString(),"Incorrect error output message renameClass");
                }catch(ArrayIndexOutOfBoundsException e){
                    fail("Index out of bounds exception thrown when incorrect number of params in rename class. ");
                }
            }

        }
        @Nested
        @DisplayName("Field preconditions")
        public class FieldPreconditions{
            @Test
            @DisplayName("Incorrect Number of parameters for add field")
            public void incorrectNumberOfParamsAddField(){
                try {
                    test.fieldCommands.addField(new String[]{"add", "field", "exampleClass","fun"});
                    assertEquals("Invalid number of arguments. Usage: add field <classname> <fieldname> <fieldtype>\r\n", output.toString(), "Incorrect error output message addField");
                }catch(IllegalArgumentException e){
                    fail("IllegalArgumentException thrown when incorrect number of params");
                }
            }

            @Test
            @DisplayName("Incorrect Number of parameters for remove field")
            public void incorrectNumberOfParamsRemoveField(){
                try {
                    test.fieldCommands.removeField(new String[]{"remove", "field","exampleClass"});
                    assertEquals("Invalid number of arguments. Usage: remove field <classname> <fieldname>\r\n", output.toString(), "Incorrect error output message parameters remove field");
                }catch(ArrayIndexOutOfBoundsException e){
                    fail("Index out of bounds exception thrown when invalid number of arguments.");
                }catch(IllegalArgumentException e){
                    fail("IllegalArgumentException thrown when incorrect number of params");
                }
            }
            @Test
            @DisplayName("Incorrect Number of parameters for rename field")
            public void incorrectNumberOfParamsRenameField(){
                try {
                    test.fieldCommands.renameField(new String[]{"rename", "class", "exampleClass"});
                    assertEquals("Invalid number of arguments. Usage: rename field <className> <oldFieldName> <newFieldName>\r\n", output.toString(), "Incorrect error output message rename Field");
                }catch(ArrayIndexOutOfBoundsException e){
                    fail("Index out of bounds exception. Check if code still runs after error is caught.");
                }
            }
            @Test
            @DisplayName("Invalid Class name in add field")
            public void incorrectClassNameAddField(){
                try {
                    test.fieldCommands.addField(new String[]{"Add", "field", "falseClass", "fieldName", "fieldType" });
                    assertEquals("Class does not exist\r\n",output.toString(), "Wrong output message for when class name doesn't exist.");
                }catch(ArrayIndexOutOfBoundsException e){
                    fail("Index out of bounds exception thrown when ");
                }
            }
        }
        @Nested
        @DisplayName("Method Preconditions")
        public class methodPreconditions{
            @Test
            @DisplayName("Incorrect Number of parameters for add method")
            public void incorrectNumberOfParamsAddMethod(){
                try {
                    test.methodCommands.addMethod(new String[]{"add", "method", "exampleClass"});
                    assertEquals("Invalid number of arguments. Usage: add method <className> <newMethodName>\r\n", output.toString(), "Incorrect error output message addMethod");
                }catch(IllegalArgumentException e){
                    fail("IllegalArgumentException thrown when incorrect number of params");
                }catch(IndexOutOfBoundsException e){
                    fail("IndexOutOfBoundsException thrown when incorrect number of params");
                }
            }

            @Test
            @DisplayName("Incorrect Number of parameters for remove method")
            public void incorrectNumberOfParamsRemoveMethod(){
                try {
                    test.methodCommands.removeMethod(new String[]{"remove", "method","exampleClass"});
                    assertEquals("Invalid number of arguments. Usage: add method <className> <removingMethodName>\r\n", output.toString(), "Incorrect error output message parameters remove method");
                }catch(ArrayIndexOutOfBoundsException e){
                    fail("IndexOutOfBounds exception thrown when invalid number of arguments.");
                }catch(IllegalArgumentException e){
                    fail("IllegalArgumentException thrown when incorrect number of params");
                }
            }
            @Test
            @DisplayName("Incorrect Number of parameters for rename field")
            public void incorrectNumberOfParamsRenameMethod(){
                try {
                    test.methodCommands.renameMethod(new String[]{"rename", "class", "ExampleClass"});
                    assertEquals("Invalid number of arguments. Usage: rename method <className> <oldMethodName> <newMethodName>\r\n", output.toString(), "Incorrect error output message rename method");
                }catch(ArrayIndexOutOfBoundsException e){
                    fail("Index out of bounds exception.");
                }
            }
            @Test
            @DisplayName("Invalid Class name in add method")
            public void incorrectClassNameAddMethod(){
                try {
                    test.methodCommands.addMethod(new String[]{"Add", "method", "falseClass", "methodName"});
                    assertEquals("Class falseClass does not exist\r\n",output.toString(), "Wrong output message for when class name doesn't exist.");
                }catch(ArrayIndexOutOfBoundsException e){
                    fail("Index out of bounds exception thrown when ");
                }
            }
        }
        @Nested
        @DisplayName("Parameter Preconditions")
        public class ParameterPreconditions{
            @Test
            @DisplayName("Incorrect Number of parameters for add parameter")
            public void incorrectNumberOfParamsAddParameter(){
                try {
                    test.parameterCommands.addParameter(new String[]{"add", "parameter", "exampleClass"});
                    assertEquals("Invalid number of arguments. Usage: add parameter <className> <methodName> <parameterName> <parameterType> \r\n", output.toString(), "Incorrect error output message addParameter");
                }catch(IllegalArgumentException e){
                    fail("IllegalArgumentException thrown when incorrect number of params");
                }catch(IndexOutOfBoundsException e){
                    fail("IndexOutOfBoundsException thrown when incorrect number of params");
                }
            }

            @Test
            @DisplayName("Incorrect Number of parameters for remove parameter")
            public void incorrectNumberOfParamsRemoveParameter(){
                try {
                    test.parameterCommands.removeParameter(new String[]{"remove", "method","exampleClass"});
                    assertEquals("Invalid number of arguments. Usage: add parameter <className> <methodName> <parameterName> \r\n", output.toString(), "Incorrect error output message parameters remove parameter");
                }catch(ArrayIndexOutOfBoundsException e){
                    fail("IndexOutOfBounds exception thrown when invalid number of arguments.");
                }catch(IllegalArgumentException e){
                    fail("IllegalArgumentException thrown when incorrect number of params");
                }
            }
            @Test
            @DisplayName("Incorrect Number of parameters for rename parameter")
            public void incorrectNumberOfParamsRenameParameter(){
                try {
                    test.parameterCommands.changeParameter(new String[]{"rename", "class", "ExampleClass"});
                    assertEquals("Invalid number of arguments. Usage: rename parameter <className> <methodName> <parameterName> <newParameterName> <newParameterType>\r\n", output.toString(), "Incorrect error output message rename parameter");
                }catch(ArrayIndexOutOfBoundsException e){
                    fail("Index out of bounds exception.");
                }
            }
            @Test
            @DisplayName("Invalid Class name in add parameter")
            public void incorrectClassNameAddParameter(){
                try {
                    test.parameterCommands.addParameter(new String[]{"Add", "parameter", "falseClass", "methodName", "parameterName", "parameterType"});
                    assertEquals("Class falseClass does not exist\r\n",output.toString(), "Wrong output message for when class name doesn't exist.");
                }catch(ArrayIndexOutOfBoundsException e){
                    fail("Index out of bounds exception thrown when ");
                }
            }
            @Test
            @DisplayName("Invalid method name in add parameter")
            public void incorrectMethodNameAddParameter(){
                try {
                    test.parameterCommands.addParameter(new String[]{"Add", "parameter", "exampleClass", "methodName", "parameterName", "parameterType"});
                    assertEquals("Method methodName does not exist\r\n",output.toString(), "Wrong output message for when method name doesn't exist.");
                }catch(ArrayIndexOutOfBoundsException e){
                    fail("Index out of bounds exception thrown when ");
                }
            }
        }
        @Nested
        @DisplayName("Relationship Preconditions")
        public class RelationshipPreconditions{

            @Test
            @DisplayName("Invalid number of params for addRelationship")
            public void incorrectNumberOfParamsAddRelationship(){
                try{
                    test.relationshipCommands.addRelationship(new String[] {"add"});
                    assertEquals("Invalid number of arguments. Usage: add Relationship <className> <anotherClassName>", output.toString(), "Incorrect error response to invalid number of arguments in addRelationship.");
                }catch(IllegalArgumentException e){
                    fail("Invalid number of arguments in addRelationship throws IllegalArgumentException");
                }catch(IndexOutOfBoundsException e){
                    fail("Invalid number of arguments in addRelationship throws IndexOutOfBoundsException");
                }
            }
            @Test
            @DisplayName("Invalid className for addClass")
            public void invalidClassNameForAddRelationship(){
                try{
                    test.relationshipCommands.addRelationship(new String[]{"add", "relationship", "exampleClass", "FakeClass"});
                    assertEquals("Destination Class does not exist", output.toString(), "Incorrect error code when invalid class name is included.");
                }catch(IllegalArgumentException e){
                    fail("Invalid class name in addRelationship throws IllegalArgumentException()");
                }catch(IndexOutOfBoundsException e){
                    fail("Invalid class name in addRelationship throws IllegalArgumentException()");
                }
            }
            @Test
            @DisplayName("Invalid number of params for removeRelationship")
            public void invalidNumberOfParamsRemoveRelationship(){
                try{
                    test.relationshipCommands.removeRelationship(new String[] {"add"});
                    assertEquals("Invalid number of arguments. Usage: remove Relationship <className> <anotherClassName> <type>", output.toString(), "Incorrect error response to invalid number of arguments in removeRelationship.");
                }catch(IllegalArgumentException e){
                    fail("Invalid number of arguments in removeRelationship throws IllegalArgumentException");
                }catch(IndexOutOfBoundsException e){
                    fail("Invalid number of arguments in removeRelationship throws IndexOutOfBoundsException");
                }
            }
            @Test
            @DisplayName("Invalid className for addClass")
            public void invalidClassNameForRemoveRelationship(){
                try{
                    test.relationshipCommands.removeRelationship(new String[]{"add", "relationship", "exampleClass", "FakeClass"});
                    assertEquals("Destination Class does not exist", output.toString(), "Incorrect error code when invalid class name is included.");
                }catch(IllegalArgumentException e){
                    fail("Invalid class name in addRelationship throws IllegalArgumentException()");
                }catch(IndexOutOfBoundsException e){
                    fail("Invalid class name in addRelationship throws IllegalArgumentException()");
                }
            }
        }
    }
}