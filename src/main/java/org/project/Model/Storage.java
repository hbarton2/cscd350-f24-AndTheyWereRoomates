package org.project.Model;

import java.util.Map;
import java.util.TreeMap;

public class Storage {

  // Static variable to hold the single instance
  private static Storage instance;

  // The actual TreeMap to store data
  private TreeMap<String, UMLClassNode> storage;

  // Private constructor to prevent instantiation from outside the class
  Storage() {
    this.storage = new TreeMap<>();
  }

  // Public method to provide access to the instance Singleton Design pattern
  public static Storage getInstance() {
    if (instance == null) {
      instance = new Storage(); // Create the instance if it doesn't exist
    }
    return instance;
  }

  // Method to get the storage
  public TreeMap<String, UMLClassNode> getStorage() {
    return storage;
  }

  // Method to add a node
  public void addNode(String key, UMLClassNode node) {
    storage.put(key, node);
  }

  // Method to get a node by key
  public UMLClassNode getNode(String key) {
    return storage.get(key);
  }

  // Method to check if a node exists by key
  public boolean containsNode(String key) {
    return storage.containsKey(key);
  }

  // Method to remove a node by key
  public void removeNode(String key) {
    storage.remove(key);
  }

  // Method to print all nodes
  public void printAllNodes() {
    for (UMLClassNode node : storage.values()) {
      System.out.println(node.toString());
    }
  }

  public Map<String, UMLClassNode> getAllNodes() {
    return new TreeMap<>(storage);
  }

  public void setAllNodes(Map<String, UMLClassNode> nodes) {
    storage.clear();
    storage.putAll(nodes);
  }

  // Method to get the size of the storage
  public int getSize() {
    return storage.size();
  }

  // Method to clear all nodes
  public void clearStorage() {
    storage.clear();
  }

}
