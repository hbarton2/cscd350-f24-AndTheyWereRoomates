package org.project;
import java.util.*;

/**
 * Represents a field in a class.
 */
public class Field {
    /**
     * The name of the field.
     */
    private String name;
    /**
     * The type of the field.
     */
    private String type;

    /**
     * Constructs a new Field.
     * @param name - The name of the field.
     * @param type - The type of the field.
     */
    public Field (final String name, final String type){
        this.name = name;
        this.type = type;
    }

    /**
     * Gets the name of the field.
     * @return the name of the field.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Gets the type of the field.
     * @return the type of the field.
     */
    public String getType(){
        return this.type;
    }

    /**
     * Sets the name of the field.
     * @param name - The new name of the field.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Sets the type of the field.
     * @param type - The new type of the field.
     */
    public void setType(final String type) {
        this.type = type;
    }
}
