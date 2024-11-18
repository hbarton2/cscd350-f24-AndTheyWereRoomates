package org.project;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.project.Controller.CommandResult;

import static org.junit.Assert.*;

public class CommandResultTest {


  @Test
  public void testConstructorWithoutCommandName() {
    CommandResult result = new CommandResult(true, "Operation successful");

    assertTrue(result.isSuccess());
    assertEquals("Operation successful", result.getMessage());
    assertNull(result.getCommandName());
  }

  @Test public void testConstructorWithoutCommandNameFalse(){
    CommandResult result = new CommandResult(false, "Operation unsuccessful");
    assertFalse(result.isSuccess());
  }

  @Test
  public void testConstructorWithCommandName() {
    CommandResult result = new CommandResult(false, "Command failed", "testCommand");

    assertFalse(result.isSuccess());
    assertEquals("Command failed", result.getMessage());
    assertEquals("testCommand", result.getCommandName());
  }

  @Test
  public void testSuccessFactoryMethod() {
    CommandResult result = CommandResult.success("Operation completed");

    assertTrue(result.isSuccess());
    assertEquals("Operation completed", result.getMessage());
    assertNull(result.getCommandName());
  }

  @Test
  public void testFailureFactoryMethod() {
    CommandResult result = CommandResult.failure("Operation failed");

    assertFalse(result.isSuccess());
    assertEquals("Operation failed", result.getMessage());
    assertNull(result.getCommandName());
  }

  @Test
  public void testToStringWithoutCommandName() {
    CommandResult result = new CommandResult(true, "Operation successful");
    String expected = "CommandResult{success=true, message='Operation successful'}";

    assertEquals(expected, result.toString());
  }

  @Test
  public void testToStringWithCommandName() {
    CommandResult result = new CommandResult(false, "Command failed", "testCommand");
    String expected =
        "CommandResult{success=false, message='Command failed', commandName='testCommand'}";

    assertEquals(expected, result.toString());
  }

  @Test
  public void testIsSuccessTrue() {
    CommandResult result = new CommandResult(true, "Operation successful");
    assertTrue(result.isSuccess());
  }

  @Test public void testIsSuccessFalse() {
    CommandResult result = new CommandResult(false, "Command failed");
    assertFalse(result.isSuccess());
  }
}
