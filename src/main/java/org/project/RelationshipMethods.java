package org.project;

import java.util.Scanner;

public class RelationshipMethods {

    public static void addRelationship(Scanner scanner, Storage storage, String[] input){
        if(input.length != 4){
            System.out.println("Invalid number of arguments");

        }else{
            String source = input[2];
            String destination = input[3];


            //These if-statements check if the classes exist. If not the relationship cannot be created
            if(!storage.list.containsKey(source))System.out.println("Source Class does not exist");
            if(!storage.list.containsKey(destination)) System.out.println("Destination Class does not exist");

            Class srcClass = storage.getClass(source);

            srcClass.addRelation(srcClass.getName(), destination);
        }
    }

    public static void removeRelationship(Scanner scanner, Storage storage, String[] input){
        if(input.length != 4){
            System.out.println("Invalid number of arguments");
        }else{

            String source = input[2];
            String destination = input[3];

            //Checks to see the source class exists
            if(!storage.list.containsKey(source)) System.out.println("source class does not exist");

            Class srcClass = storage.getClass(source);

            srcClass.removeRelation(srcClass.getName(), destination);
        }
    }
}
