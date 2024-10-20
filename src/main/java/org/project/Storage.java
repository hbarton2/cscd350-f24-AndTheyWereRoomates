package org.project;
import java.util.TreeMap;

/**
 * Represents a storage for managing a collection of classes.
 */
public class Storage {
    /**
     * A map to store the classes.
     */
    public TreeMap<String, Class> list = new TreeMap<>();

    /**
     * addClass adds a class to the stored list.
     * @param name of the class being added.
     * @throws IllegalArgumentException if name is null or empty.
     */
    public boolean addClass(String name) throws IllegalArgumentException{
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Invalid name, try again");
        list.put(name, new Class(name));
        return true;
    }

    /**
     * Retrieves a class.
     * @param name - The name of the class.
     * @return the class if found.
     */
    public Class getClass(String name) {
        return this.list.get(name);
    }

    /**
     * deleteClass removes a class by name from the stored list
     * @param name of the class being removed.
     * @throws IllegalArgumentException thrown if name is empty or null.
     */
    public boolean deleteClass(String name) throws IllegalArgumentException{
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Invalid name, try again");
        if (list.get(name) == null){
            System.out.println("Name of class not in list");
            return false;
        }
        list.remove(name);
        return true;

    }

    /**
     * renameClass changes the name field of the class and changes how it's accessed by the list.
     * @param oldName of the class being renamed.
     * @param newName is the new name of the class.
     * @throws IllegalArgumentException If either oldName or newName are null or empty.
     */
    public boolean renameClass(String oldName, String newName) throws IllegalArgumentException{
        if(oldName == null || oldName.isEmpty()) throw new IllegalArgumentException("Invalid old name, try again");
        if(newName == null || newName.isEmpty()) throw new IllegalArgumentException("Invalid new name, try again");
        if(list.get(oldName) == null){
            System.out.println("Old name is not a valid option in the list");
            return false;
        }
        Class placeholder = list.remove(oldName);
        placeholder.setName(newName);
        list.put(newName, placeholder);
        return true;


    }
}
