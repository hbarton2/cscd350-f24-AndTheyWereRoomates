package org.project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MethodTest {
    Method method = new Method("Blue");
    Method method2 = new Method("Green");

    //getName Functionality
    @Test
    void getName(){
        Method method3 = new Method("Green");
        //Base Case, Get Name
        assertEquals("Green", method3.getName());
        assertEquals("Green", method2.getName());
    }

    //setName Functionality
    @Test
    void setName(){
        //Base Case, Set Name
        method2.setName("Blue");
        assertEquals("Blue",method2.getName());
    }

    //addParameters
    @Test
    void addParameters() {
        //Base Case
        assertEquals(true,method.addParameter("Stem","STRING"));
        //Pre-Existing Name
        assertNotEquals(true,method.addParameter("Stem","STRING"));
        //Null-Blank Edge Case
        assertNotEquals(true,method.addParameter(null,"STRING"));
        assertNotEquals(true,method.addParameter("","STRING"));
        assertNotEquals(true,method.addParameter("Green",null));
        assertNotEquals(true,method.addParameter("Green",""));
    }

    //Change parameters
    @Test
    void changeParameters(){
        //Set-up
        method.addParameter("Stem","STRING");
        method.addParameter("WORM","LONG");

        //Nonexistant Name
        assertNotEquals(true,method.changeParameter("Pineapple","Core","INT"));
        //Base Case
        assertEquals(true,method.changeParameter("Stem","Core","INT"));
        //Name Exists edge case
        assertNotEquals(true,method.changeParameter("Core","WORM","ENUM"));
        //Null-Blank Edge Case
        assertNotEquals(true,method.changeParameter(null,"Stem","String"));
        assertNotEquals(true,method.changeParameter("","Stem","String"));
        assertNotEquals(true,method.changeParameter("Core",null,"String"));
        assertNotEquals(true,method.changeParameter("Core","","String"));
        assertNotEquals(true,method.changeParameter("Core","Stem",null));
        assertNotEquals(true,method.changeParameter("Core","Stem",""));

    }

    //Delete Parameters
    @Test
    void deleteParameter(){
        //Set-up
        method.addParameter("Core","INT");
        method.addParameter("Stem","STRING");

        //Base Case
        assertEquals(true,method.deleteParameter("Stem"));
        //Non-existent Edge Case
        assertNotEquals(true,method.deleteParameter("Stem"));
        //Null-Blank Edge Case
        assertNotEquals(true,method.deleteParameter(""));
        assertNotEquals(true,method.deleteParameter(null));
    }

    //Delete all parameters
    @Test
    void deleteAllParameters(){
        //Setup
        Method method3 = new Method("Green");
        method.addParameter("Core","INT");
        method.addParameter("Stem","STRING");
        method.addParameter("Meat","INT");
        method.addParameter("WORM","LONG");
        method.addParameter("Leaf","Enum");
        method.deleteAllParameter();

        //Base Case
        assertEquals(method3.getParameter(), method.getParameter());
    }

    @Test
    void hasParameter(){
        //Setup
        method.addParameter("Core","INT");
        method.addParameter("Stem","STRING");
        method.addParameter("Meat","INT");
        method.addParameter("WORM","LONG");
        method.addParameter("Leaf","Enum");

        //Base case
        assertEquals(true, method.hasParameter("Core"));
        assertEquals(true, method.hasParameter("Meat"));
        //Name not found
        assertNotEquals(true, method.hasParameter("Green"));
        //Null-Empty check
        assertNotEquals(true, method.hasParameter(""));
        assertNotEquals(true, method.hasParameter(null));



    }
}
