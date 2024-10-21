package org.project;
import java.util.*;

/**
 * Represents a method
 */
public class Method {
    /**
     * The name of the method.
     */
    private String name;
    /**
     * A list of parameters.
     */
    public ArrayList<Parameter> parameters;

    /**
     * Constructor to create a new Method.
     * @param name - The name of the method.
     */
    public Method(final String name){
        this.name = name;
        this.parameters = new ArrayList<>();
    }

    /**
     * Adds a parameter to the method.
     * @param parameterName - The name of the parameter.
     * @param parameterType - The type of the parameter.
     * @return true if the parameter was added, otherwise returns false.
     */
    public boolean addParameter(final String parameterName, final String parameterType){

        if(parameterName.isBlank() || parameterName == null || parameterType.isBlank() || parameterType == null){
            System.out.println("Inputs were Null/Blank");
            return false;
        }

        if(hasParameter(parameterName)){
            System.out.println("This method already has this parameter");
            return false;
        }

        parameters.add(new Parameter(parameterName,parameterType));
        return true;
    }

    /**
     * Changes an existing parameter with a new name and type.
     * @param oldParamName - Current name of the parameter.
     * @param newParamName - New name for the parameter.
     * @param newParamType - The new type of the parameter.
     * @return true if the parameter was successfully changed, otherwise returns false.
     */
    public boolean changeParameter(final String oldParamName, final String newParamName, final String newParamType){

        if(newParamName.isBlank() || newParamName == null || newParamType.isBlank() || newParamType == null || oldParamName.isBlank() || oldParamName == null){
            System.out.println("Inputs were Null/Blank");
            return false;
        }

        if(!oldParamName.equals(newParamName) && hasParameter(newParamName)){
            System.out.println("This method already has this parameter");
            return false;
        }

        for(Parameter param: parameters){
            if(param.getName().equals(oldParamName)){
                param.setName(newParamName);
                param.setType(newParamType);
                System.out.println("The parameter has been changed to " + newParamName + ", param of type: " + newParamType);
                return true;
            }
        }

        System.out.println("Parameter not found try another name");
        return false;
    }

    /**
     * Deletes a parameter from the method by its name.
     * @param parameterName - The name of the parameter.
     * @return true if the parameter was successfully deleted, otherwise returns false.
     */
    public boolean deleteParameter(final String parameterName){

        if(parameterName.isBlank() || parameterName == null){
            System.out.println("Input was Null/Blank");
            return false;
        }

        for(Parameter param: parameters){
            if(param.getName().equals(parameterName)){
                parameters.remove(param);
                return true;
            }
        }

        System.out.println("Parameter was not found in method");
        return false;
    }

    /**
     * Deletes all the parameters from the method.
     */
    public void deleteAllParameter(){
        this.parameters.clear();
        System.out.println("All parameters in method has been deleted");
    }

    /**
     * Checks if a parameter with the name provided exists.
     * @param parameterName - The name of the parameter.
     * @return true if the parameter exists, otherwise returns false.
     */
    public boolean hasParameter(final String parameterName){

        if(parameterName.isBlank() || parameterName == null){
            System.out.println("Input was Null/Blank");
            return false;
        }

        for(Parameter param: parameters){
            if(param.getName().equals(parameterName)){
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the name of the method.
     * @return the name of the method.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Sets the name of the method.
     * @param name - The new name for the method.
     */
    public void setName(final String name){
        this.name = name;
    }

    /**
     * Gets the list of parameters of the method.
     * @return the list of parameters.
     */
    public ArrayList<Parameter> getParameter(){
        return parameters;
    }
}
