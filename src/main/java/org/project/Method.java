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
        if(hasParameter(parameterName)){
            System.out.println("This method already has this parameter");
            return false;
        }
        parameters.add(new Parameter(parameterName,parameterType));
        return true;
    }

    public boolean changeParameter(final String oldParamName, final String newParamName, final String newParamType){
        if(!oldParamName.equals(newParamName) && hasParameter(newParamName)){
            System.out.println("method already has this parameter");
            return false;
        }
        for(Parameter param: parameters){
            if(param.getName().equals(oldParamName)){
                param.setName(newParamName);
                param.setType(newParamType);
                System.out.println("the parameter has ban changed to " + newParamName + " parame of type: " + newParamType);
                return true;
            }

        }
        System.out.println("parameter now found try another name");
        return false;
    }

    public boolean deleteParameter(final String parameterName){
        for(Parameter param: parameters){
            if(param.getName().equals(parameterName)){
                parameters.remove(param);
                System.out.println("the parameter has been deleted");
                return true;
            }

        }
        System.out.println("Parameter was not found in method");
        return false;
    }

    public boolean hasParameter(final String parameterName){
        for(Parameter param: parameters){
            if(param.getName().equals(parameterName)){
                return true;
            }
        }
        return false;
    }

    public String getName(){
        return this.name = name;
    }

    public void setName(String name){
        this.name = name;
    }

    public ArrayList<Parameter> getParameter(){
        return parameters;
    }
}
