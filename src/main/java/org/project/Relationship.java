package org.project;
import java.util.*;

public class Relationship {
    private String source;
    private String destination;
    public Relationship (final String source, final String destination){
        this.source = source;
        this.destination = destination;
    }

    public String getSource(){
        return this.source;
    }

    public String getDestination(){
        return this.destination;
    }
}
