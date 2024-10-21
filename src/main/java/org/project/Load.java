package org.project;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

public class Load {

     private Storage storage;


     public Load(Storage storage){
         this.storage = storage;
     }

     public boolean Load(String fileName) {

         try {
             FileReader reader = new FileReader(fileName);
             Gson gson = new Gson();
             Type type = new TypeToken<TreeMap<String, Class>>() {}.getType();
             this.storage.list = gson.fromJson(reader, type);
             reader.close();
             return true;
         }catch(IOException e){
             e.printStackTrace();
         }
         return false;
     }
}
