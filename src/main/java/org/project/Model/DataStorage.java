package org.project.Model;

import java.util.Map;
import java.util.TreeMap;

public class DataStorage {

  private static volatile DataStorage instance; // Volatile for safe double-checked locking
  private final Map<String, UMLClassNode> storage;

  private DataStorage() {
    this.storage = new TreeMap<>();
  }

  public static DataStorage getInstance() {
    if (instance == null) {
      synchronized (DataStorage.class) {
        if (instance == null) {
          instance = new DataStorage();
        }
      }
    }
    return instance;
  }

  public synchronized void addNode(String key, UMLClassNode node) {
    if (storage.containsKey(key)) {
      throw new IllegalArgumentException("Class with the name '" + key + "' already exists.");
    }
    storage.put(key, node);
  }

  public synchronized UMLClassNode getNode(String key) {
    return storage.get(key);
  }

  public synchronized void clearStorage() {
    storage.clear();
  }

  public synchronized void printAllNodes() {
    for (UMLClassNode node : storage.values()) {
      System.out.println(node.toString());
    }
  }

  // Check if a node exists by its class name
  public synchronized boolean containsNode(String className) {
    return storage.containsKey(className);
  }

  // Remove a node by its class name
  public synchronized void removeNode(String className) {
    storage.remove(className);
  }

  // Get the total number of stored nodes
  public synchronized Map<String, UMLClassNode> getAllNodes() {
    return new TreeMap<>(storage); // Return a copy to prevent external modification
  }

  public synchronized void setAllNodes(Map<String, UMLClassNode> state) {
    storage.clear(); // Ensure the current DATA_STORAGE is cleared
    storage.putAll(state); // Add all nodes from the provided state
  }

  public Map<Object, Object> getStorage() {
    synchronized (this) {
      // Return a copy of the DATA_STORAGE as a Map<Object, Object>
      return new TreeMap<>(storage);
    }
  }

  public synchronized void updateNode(String className, UMLClassNode currentClass) {
    if (!storage.containsKey(className)) {
      throw new IllegalArgumentException("Class with the name '" + className + "' does not exist.");
    }
    storage.put(className, currentClass);
  }

  public static void resetInstance() {
    synchronized (DataStorage.class) {
      if (instance != null) {
        instance.clearStorage(); // Clear the current DATA_STORAGE
        instance = null; // Reset the instance to null
      }
    }
  }
}