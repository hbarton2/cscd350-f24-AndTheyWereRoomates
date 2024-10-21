package org.project;
import java.util.*;

/**
 * Represents a relationship between a source and a destination.
 */
public class Relationship {
    /**
     * The source of the relationship.
     */
    private String source;
    /**
     * The destination of the relationship.
     */
    private String destination;

    /**
     * Constructs a new Relationship.
     * @param source - The source of the relationship.
     * @param destination - The destination of the relatioship.
     */
    public Relationship (final String source, final String destination){
        this.source = source;
        this.destination = destination;
    }

    /**
     * Gets the source of the relationship.
     * @return the source of the relationship.
     */
    public String getSource(){
        return this.source;
    }

    /**
     * Gets the destination of the relationship.
     * @return the destination of the relationship.
     */
    public String getDestination(){
        return this.destination;
    }

    /**
     * Sets the source of the relationship.
     * @param source - The new source of the relationship.
     */
    public void setSource(final String source) {
        this.source = source;
    }

    /**
     * Sets the destination of the relationship.
     * @param destination - The new destination of the relationship.
     */
    public void setDestination(final String destination) {
        this.destination = destination;
    }
}
