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
    Directory directory = new ByteBuffersDirectory(); // In-memory DATA_STORAGE
    suggester = new AnalyzingInfixSuggester(directory, new StandardAnalyzer());
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
  }

  /**
   * Fetches suggestions for a given prefix.
   *
   * @param prefix The prefix to search for suggestions.
   * @return A list of suggested commands.
   * @throws IOException If an error occurs during lookup.
   */
  public List<String> getSuggestions(String prefix) throws IOException {
    List<LookupResult> results = suggester.lookup(prefix, false, 10); // Use String directly
    // Convert result to String
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
  }
}
