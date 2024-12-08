package org.project.Model;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.suggest.Lookup.LookupResult;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.BytesRef;

public class AutoComplete {

  private final AnalyzingInfixSuggester suggester;

  public AutoComplete() throws IOException {
    Directory directory = new ByteBuffersDirectory(); // In-memory storage
    suggester = new AnalyzingInfixSuggester(directory, new StandardAnalyzer());
    //    System.out.println("AutoComplete suggester initialized."); // TODO: Debug log
  }

  /**
   * Adds a command to the suggester.
   *
   * @param command The command to add.
   * @throws IOException If an error occurs during addition.
   */
  public void addCommand(String command) throws IOException {
    BytesRef key = new BytesRef(command);
    suggester.add(key, null, 1, null); // Add command with default weight
    suggester.refresh(); // Refresh the suggester to apply changes
    //    System.out.println("Command added to suggester: " + command); // TODO: Debug log
  }

  /**
   * Fetches suggestions for a given prefix.
   *
   * @param prefix The prefix to search for suggestions.
   * @return A list of suggested commands.
   * @throws IOException If an error occurs during lookup.
   */
  public List<String> getSuggestions(String prefix) throws IOException {
    //    System.out.println("Fetching suggestions for prefix: " + prefix); // TODO: Debug log
    List<LookupResult> results = suggester.lookup(prefix, false, 10); // Use String directly
    // Convert result to String
    //    System.out.println("Suggestions found: " + suggestions); // TODO: Debug log
    return results.stream()
            .map(result -> result.key.toString()) // Convert result to String
            .collect(Collectors.toList());
  }

  /**
   * Closes the suggester and releases resources.
   *
   * @throws IOException If an error occurs during closing.
   */
  public void close() throws IOException {
    suggester.close(); // Close resources
    //    System.out.println("AutoComplete suggester closed."); // TODO: Debug log
  }
}
