package org.project;
import java.util.*;

public class Method {
    private String name;
    private ArrayList<Parameter> parameters;
    public Method(final String name){
        this.name = name;
        this.parameters = new ArrayList<>();
    }

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

    public boolean deleteParameter(final String parameterName){

        if(parameterName.isBlank() || parameterName == null){
            System.out.println("Input was Null/Blank");
            return false;
        }

        for(Parameter param: parameters){
            if(param.getName().equals(parameterName)){
                parameters.remove(param);
                System.out.println("The parameter has been deleted");
                return true;
            }
        }

        System.out.println("Parameter was not found in method");
        return false;
    }

    public void deleteAllParameter(){
        this.parameters.clear();
        System.out.println("All parameters in method has been deleted");
    }

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

    public String getName(){
        return this.name;
    }

    public void setName(final String name){
        this.name = name;
    }

    public ArrayList<Parameter> getParameter(){
        return parameters;
    }
}
