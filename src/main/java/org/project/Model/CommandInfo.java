package org.project.Model;

/**
 * Represents information about a command, including its syntax and description.
 *
 * <p>The {@code CommandInfo} record stores the syntax for using a command and a brief
 * description of its purpose.
 *
 * @param syntax the syntax or format of the command
 * @param description a brief description of the command
 */
public record CommandInfo(String syntax, String description) {}
