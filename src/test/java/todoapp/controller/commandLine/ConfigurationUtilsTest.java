package todoapp.controller.commandLine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ConfigurationUtilsTest {
  private Option load, addTodo, addText, addComplete, addDue, addPriority, addCategory,
  complete, display, showIncomplete, showCategory, sortDate, sortPriority;

  @Before
  public void setUp() throws Exception {
    load =  new Option.Builder(ConfigurationUtils.LOAD_CSV, ConfigurationUtils.LOAD_CSV).setHasArg().setMinNumArgs(1).setMaxNumArgs(1).build();
    addTodo = new Option.Builder(ConfigurationUtils.ADD_TODO, ConfigurationUtils.ADD_TODO).build();
    addText = new Option.Builder(ConfigurationUtils.ADD_TODO, ConfigurationUtils.ADD_TODO_TEXT).setHasArg().setMinNumArgs(1).build();
    addComplete = new Option.Builder(ConfigurationUtils.ADD_TODO, ConfigurationUtils.ADD_TODO_COMPLETED).build();
    addDue = new Option.Builder(ConfigurationUtils.ADD_TODO, ConfigurationUtils.ADD_TODO_DUE).setHasArg().setMinNumArgs(1).setMaxNumArgs(1).build();
    addPriority = new Option.Builder(ConfigurationUtils.ADD_TODO, ConfigurationUtils.ADD_TODO_PRIORITY).setHasArg().setMinNumArgs(1).setMaxNumArgs(1).build();
    addCategory =  new Option.Builder(ConfigurationUtils.ADD_TODO, ConfigurationUtils.ADD_TODO_CATEGORY).setHasArg().setMinNumArgs(1).build();
    complete = new Option.Builder(ConfigurationUtils.COMPLETE_TODO, ConfigurationUtils.COMPLETE_TODO).setHasArg().setMinNumArgs(1).setMaxNumArgs(1).setMaxNumAppearance(10).build();
    display = new Option.Builder(ConfigurationUtils.DISPLAY, ConfigurationUtils.DISPLAY).build();
    showIncomplete = new Option.Builder(ConfigurationUtils.DISPLAY, ConfigurationUtils.DISPLAY_SHOW_INCOMPLETE).build();
    showCategory = new Option.Builder(ConfigurationUtils.DISPLAY, ConfigurationUtils.DISPLAY_SHOW_CATEGORY).setHasArg().setMinNumArgs(1).build();
  }

  @Test
  public void makeOption() throws InvalidCommandException {
    assertEquals(ConfigurationUtils.makeOption(ConfigurationUtils.LOAD_CSV), load);
    assertEquals(ConfigurationUtils.makeOption(ConfigurationUtils.ADD_TODO), addTodo);
    assertEquals(ConfigurationUtils.makeOption(ConfigurationUtils.ADD_TODO_TEXT), addText);
    assertEquals(ConfigurationUtils.makeOption(ConfigurationUtils.ADD_TODO_COMPLETED), addComplete);
    assertEquals(ConfigurationUtils.makeOption(ConfigurationUtils.ADD_TODO_DUE), addDue);
    assertEquals(ConfigurationUtils.makeOption(ConfigurationUtils.ADD_TODO_PRIORITY), addPriority);
    assertEquals(ConfigurationUtils.makeOption(ConfigurationUtils.ADD_TODO_CATEGORY), addCategory);
    assertEquals(ConfigurationUtils.makeOption(ConfigurationUtils.COMPLETE_TODO), complete);
    assertEquals(ConfigurationUtils.makeOption(ConfigurationUtils.DISPLAY), display);
    assertEquals(ConfigurationUtils.makeOption(ConfigurationUtils.DISPLAY_SHOW_INCOMPLETE), showIncomplete);
    assertEquals(ConfigurationUtils.makeOption(ConfigurationUtils.DISPLAY_SHOW_CATEGORY),  showCategory);
  }
}