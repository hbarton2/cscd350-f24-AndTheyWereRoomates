package org.project.Model;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.suggest.Lookup.LookupResult;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.BytesRef;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AutoComplete {
  private final AnalyzingInfixSuggester suggester;

  public AutoComplete() throws IOException {
    Directory directory = new ByteBuffersDirectory(); // In-memory storage
    suggester = new AnalyzingInfixSuggester(directory, new StandardAnalyzer());
  }

  public void addCommand(String command) throws IOException {
    BytesRef key = new BytesRef(command); // Convert command to BytesRef
    Set<BytesRef> contexts = null; // No filtering contexts
    long weight = 1; // Default weight for commands
    BytesRef payload = null; // No additional metadata

    suggester.add(key, contexts, weight, payload); // Add command to suggester
    suggester.refresh(); // Refresh the suggester to make the command available
  }

  public List<String> getSuggestions(String prefix) throws IOException {
    List<LookupResult> results = suggester.lookup(prefix, false, 10); // Get top 10 suggestions
    return results.stream()
      .map(result -> result.key.toString()) // Convert LookupResult to String
      .collect(Collectors.toList());
  }

  public void close() throws IOException {
    suggester.close(); // Close resources
  }
}
