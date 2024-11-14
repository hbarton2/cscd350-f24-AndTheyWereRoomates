package org.project.Model;

public class CommandInfo {
  private final String syntax;
  private final String description;

  public CommandInfo(String syntax, String description) {
    this.syntax = syntax;
    this.description = description;
  }

  public String getSyntax() {
    return syntax;
  }

  public String getDescription() {
    return description;
  }
}
