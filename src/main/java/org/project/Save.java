package org.project;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Save {

    private Storage storage;



    public Save(Storage storage){
        this.storage = storage;
    }


    public boolean save(String fileName){

        try{
            FileWriter writer = new FileWriter(fileName);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(this.storage.list, writer);

            writer.close();

            return true;


        }catch (IOException e){
            e.printStackTrace();
        }

        return false;



    }
}
