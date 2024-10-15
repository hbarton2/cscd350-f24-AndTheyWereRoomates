package org.project;
import java.util.*;
public class Parameter {
    private String name;
    private String type;
    public Parameter (final String name, final String type){
        this.name = name;
        this.type = type;
    }

    public String getName(){
        return this.name;
    }

    public String getType(){
        return this.type;
    }
}
