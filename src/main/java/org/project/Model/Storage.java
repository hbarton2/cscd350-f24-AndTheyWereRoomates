package org.project.Model;

import java.util.Map;
import java.util.TreeMap;

public class Storage {

  private static volatile Storage instance; // Volatile for safe double-checked locking
  private final Map<String, UMLClassNode> storage;

  private Storage() {
    this.storage = new TreeMap<>();
  }

  public static Storage getInstance() {
    if (instance == null) {
      synchronized (Storage.class) {
        if (instance == null) {
          instance = new Storage();
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

  public void setAllNodes(Map<String, UMLClassNode> state) {
    synchronized (this) {
      // Clear the current storage
      storage.clear();

      // Add all nodes from the provided state
      storage.putAll(state);
    }
  }

  public Map<Object, Object> getStorage() {
    synchronized (this) {
      // Return a copy of the storage as a Map<Object, Object>
      return new TreeMap<>(storage);
    }
  }
}
