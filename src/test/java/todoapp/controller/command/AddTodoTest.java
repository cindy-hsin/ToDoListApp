package todoapp.controller.command;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
//import org.graalvm.compiler.core.common.type.ArithmeticOpTable.BinaryOp.Add;
import org.junit.Before;
import org.junit.Test;
import todoapp.controller.commandLine.ConfigurationUtils;
import todoapp.controller.commandLine.InvalidCommandException;
import todoapp.controller.commandLine.Option;
import todoapp.model.todoData.ITodoList;
import todoapp.model.todoData.Todo;
import todoapp.model.todoData.TodoList;

public class AddTodoTest {

  AddTodo addTodoCommand;

  List<Option> receivedOptions;
  Set<String> requiredSubCommandNames;
  Option addTodo, addText, addComplete, addDue, addPriority, addCategory;
  Todo todo;
  LocalDate date;
  ITodoList todoList;


  @Before
  public void setUp() throws Exception {
    addTodo = new Option.Builder(ConfigurationUtils.ADD_TODO, ConfigurationUtils.ADD_TODO).build();
    addText = new Option.Builder(ConfigurationUtils.ADD_TODO, ConfigurationUtils.ADD_TODO_TEXT).setHasArg()
        .setMinNumArgs(1).build();
    addText.setArgs(Arrays.asList("Finish", "HW9"));
    addComplete = new Option.Builder(ConfigurationUtils.ADD_TODO, ConfigurationUtils.ADD_TODO_COMPLETED)
        .build();
    addDue = new Option.Builder(ConfigurationUtils.ADD_TODO, ConfigurationUtils.ADD_TODO_DUE).setHasArg()
        .setMinNumArgs(1).setMaxNumArgs(1).build();
    addDue.setArgs(Arrays.asList("04/18/2021"));
    addPriority = new Option.Builder(ConfigurationUtils.ADD_TODO, ConfigurationUtils.ADD_TODO_PRIORITY)
        .setHasArg().setMinNumArgs(1).setMaxNumArgs(1).build();
    addPriority.setArgs(Arrays.asList("1"));
    addCategory =  new Option.Builder(ConfigurationUtils.ADD_TODO, ConfigurationUtils.ADD_TODO_CATEGORY).setHasArg().setMinNumArgs(1).setMaxNumArgs(1).build();
    addCategory.setArgs(Arrays.asList("school"));
    receivedOptions = new ArrayList<>(Arrays
        .asList(addTodo, addText, addComplete, addPriority, addDue, addCategory)); //, addDue, addCategory));
    requiredSubCommandNames = ConfigurationUtils.REQUIRED_SUBCOMMAND_NAMES_FOR_EACH_BASE
        .get(ConfigurationUtils.ADD_TODO);
    date = LocalDate.of(2021, 4, 18);
    todo = new Todo(1, "Finish HW9", true, date, 1, "school");
    todoList = TodoList.createTodoList();
  }


  @Test
  public void createSubCommand() throws InvalidCommandException {
    addTodoCommand = new AddTodo(ConfigurationUtils.ADD_TODO, 2, receivedOptions, todoList,
        requiredSubCommandNames);
    AbstractSubCommand addNewTodoCommand = addTodoCommand.createSubCommand(addTodo);
    assertEquals(ConfigurationUtils.ADD_TODO, addNewTodoCommand.name);
    assertEquals(null, addNewTodoCommand.args);
  }


  @Test
  public void populateSubCommandList() throws InvalidCommandException {
    // ((TodoList) todoList).setTodoList(new ArrayList<>());   // changing the inner list. only for testing purpose
    //ITodoList todoList = TodoList.createTodoList();
    addTodoCommand = new AddTodo(ConfigurationUtils.ADD_TODO, 2, receivedOptions, todoList,
        requiredSubCommandNames);

    List<AbstractSubCommand> list = Arrays.asList
        (addTodoCommand.createSubCommand(addTodo),
            addTodoCommand.createSubCommand(addText),
            addTodoCommand.createSubCommand(addComplete),
            addTodoCommand.createSubCommand(addPriority),
            addTodoCommand.createSubCommand(addDue),
            addTodoCommand.createSubCommand(addCategory));
    addTodoCommand.populateSubCommandList();
    assertEquals(list, addTodoCommand.subCommandList);
  }

  @Test(expected = InvalidCommandException.class)
  public void validateRequiredSubCommand() throws InvalidCommandException {
    //ITodoList todoList = TodoList.createTodoList();
    //((TodoList) todoList).setTodoList(new ArrayList<>());
    System.out.println(todoList);
    List<Option> receivedOptions2 = new ArrayList<>(
        Arrays.asList(addTodo, addComplete, addPriority));
    // Missing addText option
    addTodoCommand = new AddTodo(ConfigurationUtils.ADD_TODO, 2, receivedOptions2, todoList,
        requiredSubCommandNames);
    addTodoCommand.populateSubCommandList();
    addTodoCommand.validateRequiredSubCommand();
  }


  @Test
  public void execute() throws Exception {
    // ITodoList todoList = TodoList.createTodoList();
    ((TodoList) todoList).setTodoList(new ArrayList<>());
    addTodoCommand = new AddTodo(ConfigurationUtils.ADD_TODO, 2, receivedOptions, todoList,
        requiredSubCommandNames);
    addTodoCommand.execute();
    assertEquals(todo, todoList.getTodo(0));
  }

  @Test
  public void getReceivedOptions() {
    addTodoCommand = new AddTodo(ConfigurationUtils.ADD_TODO, 2, receivedOptions, todoList,
        requiredSubCommandNames);
    assertEquals(receivedOptions, addTodoCommand.getReceivedOptions());
  }

  @Test
  public void compareTo() {
    addTodoCommand = new AddTodo(ConfigurationUtils.ADD_TODO, 2, receivedOptions, todoList,
        requiredSubCommandNames);
    AbstractBaseCommand load = new LoadCsv(ConfigurationUtils.LOAD_CSV, 1, receivedOptions, todoList,
        requiredSubCommandNames);
    assertEquals(1, addTodoCommand.compareTo(load));
    assertEquals(-1, load.compareTo(addTodoCommand));
    assertEquals(0, load.compareTo(load));
  }

  @Test
  public void testEquals() throws InvalidCommandException {
    addTodoCommand = new AddTodo(ConfigurationUtils.ADD_TODO, 2, receivedOptions, todoList,
        requiredSubCommandNames);
    assertTrue(addTodoCommand.equals(addTodoCommand));
    assertFalse(addTodoCommand.equals(null));
    AddTodo sameTodo = new AddTodo(ConfigurationUtils.ADD_TODO, 2, receivedOptions, todoList,
        requiredSubCommandNames);
    assertTrue(addTodoCommand.equals(sameTodo));

    Option addText2 = ConfigurationUtils.makeOption(ConfigurationUtils.ADD_TODO_TEXT);
    addText2.setArgs(Arrays.asList("Clean", "house"));
    List<Option> receivedOptions2 = new ArrayList<>(Arrays
        .asList(addTodo, addText2, addComplete, addPriority, addDue, addCategory));
    AddTodo addTodoCommand2 = new AddTodo(ConfigurationUtils.ADD_TODO, 2, receivedOptions2, todoList,
        requiredSubCommandNames);
    assertFalse(addTodoCommand.equals(addTodoCommand2));

  }

  @Test
  public void testHashCode() {
    addTodoCommand = new AddTodo(ConfigurationUtils.ADD_TODO, 2, receivedOptions, todoList,
        requiredSubCommandNames);
    AddTodo sameTodo = new AddTodo(ConfigurationUtils.ADD_TODO, 2, receivedOptions, todoList,
        requiredSubCommandNames);
    assertTrue(addTodoCommand.hashCode() == sameTodo.hashCode());
  }

  @Test
  public void testToString() {
    addTodoCommand = new AddTodo(ConfigurationUtils.ADD_TODO, 2, receivedOptions, todoList,
        requiredSubCommandNames);
    System.out.println(addTodoCommand);
    assertEquals("AddTodo{subCommandList=[], "
        + "receivedOptions=[Option{name='--add-todo', baseOptionName='--add-todo', hasArg=false, maxNumArgs=50, minNumArgs=0, maxNumAppearance=1, args=null}, "
        + "Option{name='--todo-text', baseOptionName='--add-todo', hasArg=true, maxNumArgs=50, minNumArgs=1, maxNumAppearance=1, args=[Finish, HW9]}, "
        + "Option{name='--completed', baseOptionName='--add-todo', hasArg=false, maxNumArgs=50, minNumArgs=0, maxNumAppearance=1, args=null}, "
        + "Option{name='--priority', baseOptionName='--add-todo', hasArg=true, maxNumArgs=1, minNumArgs=1, maxNumAppearance=1, args=[1]}, "
        + "Option{name='--due', baseOptionName='--add-todo', hasArg=true, maxNumArgs=1, minNumArgs=1, maxNumAppearance=1, args=[04/18/2021]}, "
        + "Option{name='--category', baseOptionName='--add-todo', hasArg=true, maxNumArgs=1, minNumArgs=1, maxNumAppearance=1, args=[school]}], "
        + "todoList=TodoList{innerList=[Todo{id=1, text='Finish HW9', completed=true, dueDate=2021-04-18, priority=1, category='school'}]}, "
        + "requiredSubCommandNames=[--add-todo, --todo-text], name='--add-todo', text='null', completed=false, due=null, priority=null, category='null'}",
        addTodoCommand.toString());
  }


}