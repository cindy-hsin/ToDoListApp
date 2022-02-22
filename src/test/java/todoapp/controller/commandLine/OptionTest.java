package todoapp.controller.commandLine;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class OptionTest {
  private Option addOption;
  private Option addTextOption;

  @Before
  public void setUp() throws Exception {
    addOption = new Option.Builder(ConfigurationUtils.ADD_TODO, ConfigurationUtils.ADD_TODO).build();
    addTextOption = new Option.Builder(ConfigurationUtils.ADD_TODO, ConfigurationUtils.ADD_TODO_TEXT).setHasArg().setMinNumArgs(1).build();
  }

  @Test
  public void isHasArg() {
    assertFalse(addOption.isHasArg());
  }

  @Test
  public void getArgs() {
    assertNull(addOption.getArgs());
  }

  @Test
  public void getName() {
    assertEquals(ConfigurationUtils.ADD_TODO, addOption.getName());
  }

  @Test
  public void getBaseOptionName() {
    assertEquals(ConfigurationUtils.ADD_TODO, addOption.getBaseOptionName());
  }

  @Test
  public void getMaxNumAppearance() {
    assertEquals(1, addOption.getMaxNumAppearance());
  }

  @Test (expected = InvalidCommandException.class)
  public void setArgsToOptionHasNoArg() throws InvalidCommandException {
    List<String> argsList = new ArrayList<>();
    argsList.add("Finish");
    argsList.add("HW9");
    addOption.setArgs(argsList);
  }

  @Test (expected = InvalidCommandException.class)
  public void setArgsExceedMaximum() throws InvalidCommandException {
    Option priorityOption = new Option.Builder(ConfigurationUtils.ADD_TODO, ConfigurationUtils.ADD_TODO_PRIORITY).setHasArg().setMinNumArgs(1).setMaxNumArgs(1).build();
    List<String> argsList = new ArrayList<>();
    argsList.add("1");
    argsList.add("5");
    priorityOption.setArgs(argsList);
  }

  @Test (expected = InvalidCommandException.class)
  public void setArgsLessThanMinimum() throws InvalidCommandException {
    Option fakeOption = new Option.Builder("FakeOption", "fake option").setHasArg().setMinNumArgs(2).setMaxNumArgs(5).build();
    List<String> argsList = new ArrayList<>();
    argsList.add("1");
    fakeOption.setArgs(argsList);
  }

  @Test
  public void setArgs() throws InvalidCommandException {
    List<String> argsList = new ArrayList<>();
    argsList.add("Finish");
    argsList.add("HW9");
    addTextOption.setArgs(argsList);
    assertEquals(argsList, addTextOption.getArgs());
  }

  @Test
  public void testEquals() throws InvalidCommandException {
    Option option = new Option.Builder(ConfigurationUtils.ADD_TODO, ConfigurationUtils.ADD_TODO_TEXT).build();

    assertTrue(option.equals(option));
    assertFalse(option.equals(null));
    assertFalse(option.equals("option"));
    assertTrue(option.equals(new Option.Builder(ConfigurationUtils.ADD_TODO, ConfigurationUtils.ADD_TODO_TEXT).build()));
    assertFalse(option.equals(new Option.Builder(ConfigurationUtils.ADD_TODO_CATEGORY, ConfigurationUtils.ADD_TODO_TEXT).build()));
    assertFalse(option.equals(new Option.Builder(ConfigurationUtils.ADD_TODO, ConfigurationUtils.ADD_TODO_PRIORITY).build()));
    assertFalse(option.equals(new Option.Builder(ConfigurationUtils.ADD_TODO, ConfigurationUtils.ADD_TODO_TEXT).setHasArg().build()));
    assertFalse(option.equals(new Option.Builder(ConfigurationUtils.ADD_TODO, ConfigurationUtils.ADD_TODO_TEXT).setMaxNumArgs(5).build()));
    assertFalse(option.equals(new Option.Builder(ConfigurationUtils.ADD_TODO, ConfigurationUtils.ADD_TODO_TEXT).setMinNumArgs(5).build()));
    assertFalse(option.equals(new Option.Builder(ConfigurationUtils.ADD_TODO, ConfigurationUtils.ADD_TODO_TEXT).setMaxNumAppearance(10).build()));
    Option option2 = new Option.Builder(ConfigurationUtils.ADD_TODO, ConfigurationUtils.ADD_TODO_TEXT).setHasArg().setMinNumArgs(1).build();
    Option filledOption2 = new Option.Builder(ConfigurationUtils.ADD_TODO, ConfigurationUtils.ADD_TODO_TEXT).setHasArg().setMinNumArgs(1).build();
    filledOption2.setArgs(Arrays.asList("args"));
    assertFalse(option2.equals(filledOption2));
  }

  @Test
  public void testHashCode(){
   Option addOption2 = new Option.Builder(ConfigurationUtils.ADD_TODO, ConfigurationUtils.ADD_TODO).build();
   assertTrue(addOption2.hashCode() == addOption2.hashCode());
  }

  @Test
  public void testToString() {
    String expected = "Option{name='--add-todo', baseOptionName='--add-todo', hasArg=false, maxNumArgs=50, minNumArgs=0, maxNumAppearance=1, args=null}";
    assertEquals(expected, addOption.toString());
  }
}