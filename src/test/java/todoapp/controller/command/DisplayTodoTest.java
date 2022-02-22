package todoapp.controller.command;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import todoapp.controller.commandLine.ConfigurationUtils;
import todoapp.controller.commandLine.InvalidCommandException;
import todoapp.controller.commandLine.Option;
import todoapp.model.todoData.ITodoList;
import todoapp.model.todoData.Todo;
import todoapp.model.todoData.TodoList;

public class DisplayTodoTest {
  DisplayTodo displayTodoCommand;
  List<Option> receivedOptions;
  Set<String> requiredSubCommandNames;
  Option display, showIncomplete, showCategory, sortDate, sortPriority;
  Todo todo1, todo2, todo3, todo4;
  ITodoList todoList;

  @Before
  public void setUp() throws Exception {
    display = new Option.Builder(ConfigurationUtils.DISPLAY, ConfigurationUtils.DISPLAY).build();
    showIncomplete = new Option.Builder(ConfigurationUtils.DISPLAY, ConfigurationUtils.DISPLAY_SHOW_INCOMPLETE).build();
    showCategory = new Option.Builder(ConfigurationUtils.DISPLAY, ConfigurationUtils.DISPLAY_SHOW_CATEGORY).setHasArg().setMinNumArgs(1).build();
    sortDate = new Option.Builder(ConfigurationUtils.DISPLAY, ConfigurationUtils.DISPLAY_SORT_DATE).build();
    sortPriority = new Option.Builder(ConfigurationUtils.DISPLAY, ConfigurationUtils.DISPLAY_SORT_PRIORITY).build();

    receivedOptions = new ArrayList<>(Arrays.asList(display, showIncomplete, showCategory, sortDate));
    requiredSubCommandNames = ConfigurationUtils.REQUIRED_SUBCOMMAND_NAMES_FOR_EACH_BASE.get(
        ConfigurationUtils.DISPLAY);
    LocalDate date = LocalDate.of(2021,4,18);
    todo1 = new Todo(1,"Finish HW9",true, date, 1,"School");
    todo2 = new Todo(2,"Clean",false, null, 2,"House");
    todo3 = new Todo(3,"Laundry",false, date, 2,"House");
    todo4 = new Todo(4,"Mail",true, null, 3,"House");


    todoList = TodoList.createTodoList();
    todoList.addTodo(todo1);
    todoList.addTodo(todo2);
    todoList.addTodo(todo3);
    todoList.addTodo(todo4);

  }

  @Test
  public void createSubCommand() throws InvalidCommandException {
    displayTodoCommand = new DisplayTodo(ConfigurationUtils.DISPLAY, 4, receivedOptions, todoList, requiredSubCommandNames);
    AbstractSubCommand displayCommand = displayTodoCommand.createSubCommand(display);
    assertEquals(ConfigurationUtils.DISPLAY, displayCommand.name);
    assertEquals(null, displayCommand.args);

  }

  @Test
  public void populateSubCommandList() throws InvalidCommandException {
    displayTodoCommand = new DisplayTodo(ConfigurationUtils.DISPLAY, 4, receivedOptions, todoList, requiredSubCommandNames);

    List<AbstractSubCommand> list = Arrays.asList
        (displayTodoCommand.createSubCommand(display),
            displayTodoCommand.createSubCommand(showIncomplete),
            displayTodoCommand.createSubCommand(showCategory),
            displayTodoCommand.createSubCommand(sortDate));
    displayTodoCommand.populateSubCommandList();
    assertEquals(list, displayTodoCommand.subCommandList);
  }

  @Test
  public void execute() throws Exception {
    ((TodoList) todoList).setTodoList(new ArrayList<>());
    todoList.addTodo(todo1);
    todoList.addTodo(todo2);
    todoList.addTodo(todo3);
    todoList.addTodo(todo4);
    showCategory.setArgs(Arrays.asList("House"));
    displayTodoCommand = new DisplayTodo(ConfigurationUtils.DISPLAY, 4, receivedOptions, todoList, requiredSubCommandNames);
    displayTodoCommand.execute();
    List<Todo> expectedList = Arrays.asList(todo3, todo2);
    assertEquals(expectedList, displayTodoCommand.resultList);
  }


  @Test (expected = InvalidCommandException.class)
  public void executeConflictSort() throws Exception {
    receivedOptions = new ArrayList<>(Arrays.asList(display, showIncomplete, sortDate, sortPriority));
    displayTodoCommand = new DisplayTodo(ConfigurationUtils.DISPLAY, 4, receivedOptions, todoList, requiredSubCommandNames);
    displayTodoCommand.execute();
  }

  @Test
  public void testEquals() throws InvalidCommandException {
    displayTodoCommand = new DisplayTodo(ConfigurationUtils.DISPLAY, 4, receivedOptions, todoList, requiredSubCommandNames);
    assertTrue(display.equals(display));
    assertFalse(displayTodoCommand.equals(null));
    assertFalse(displayTodoCommand.equals("display"));

    DisplayTodo displayTodoCommand2 = new DisplayTodo(ConfigurationUtils.DISPLAY, 2, receivedOptions, todoList, requiredSubCommandNames);
    assertFalse(displayTodoCommand.equals(displayTodoCommand2));
    List<Option> receivedOptions3  = new ArrayList<>(Arrays.asList(display, showIncomplete, showCategory, sortDate, showCategory));
    DisplayTodo displayTodoCommand3 = new DisplayTodo(ConfigurationUtils.DISPLAY, 4, receivedOptions3, todoList, requiredSubCommandNames);
    assertFalse(displayTodoCommand.equals(displayTodoCommand3));
  }

  @Test
  public void testHashCode() throws InvalidCommandException {
    displayTodoCommand = new DisplayTodo(ConfigurationUtils.DISPLAY, 4, receivedOptions, todoList, requiredSubCommandNames);
    DisplayTodo displayTodoCommand2 = new DisplayTodo(ConfigurationUtils.DISPLAY, 4, receivedOptions, todoList, requiredSubCommandNames);
    assertTrue(displayTodoCommand.hashCode() == displayTodoCommand2.hashCode());
  }

  @Test
  public void testToString() {
    receivedOptions = new ArrayList<>(Arrays.asList(display));
    ((TodoList) todoList).setTodoList(new ArrayList<>());
    todoList.addTodo(todo1);
    displayTodoCommand = new DisplayTodo(ConfigurationUtils.DISPLAY, 4, receivedOptions, todoList, requiredSubCommandNames);
    System.out.println(displayTodoCommand);
    String expected = "DisplayTodo{subCommandList=[], "
        + "receivedOptions=[Option{name='--display', baseOptionName='--display', hasArg=false, maxNumArgs=50, minNumArgs=0, maxNumAppearance=1, args=null}], "
        + "todoList=TodoList{innerList=[Todo{id=1, text='Finish HW9', completed=true, dueDate=2021-04-18, priority=1, category='School'}]}, "
        + "requiredSubCommandNames=[--display], name='--display', resultList=null}";
    assertEquals(expected, displayTodoCommand.toString());
  }

  // Testing AbstractSubCommand's equals()
  @Test
  public void testSubEquals() throws InvalidCommandException {
    displayTodoCommand = new DisplayTodo(ConfigurationUtils.DISPLAY, 4, receivedOptions, todoList, requiredSubCommandNames);
    AbstractSubCommand showIncompleteCommand = displayTodoCommand.createSubCommand(showIncomplete);
    assertTrue(showIncompleteCommand.equals(showIncompleteCommand));
    assertFalse(showIncompleteCommand.equals(null));
    assertFalse(showIncompleteCommand.equals("null"));
  }

  @Test
  public void testSubHashCode() throws InvalidCommandException {
    displayTodoCommand = new DisplayTodo(ConfigurationUtils.DISPLAY, 4, receivedOptions, todoList, requiredSubCommandNames);
    AbstractSubCommand showIncompleteCommand1 = displayTodoCommand.createSubCommand(showIncomplete);
    AbstractSubCommand showIncompleteCommand2 = displayTodoCommand.createSubCommand(showIncomplete);
    assertEquals(showIncompleteCommand1.hashCode(), showIncompleteCommand2.hashCode());
  }





}