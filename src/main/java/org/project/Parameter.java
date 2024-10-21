package org.project;
import java.util.*;

/**
 * Represents a parameter.
 */
public class Parameter {
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
