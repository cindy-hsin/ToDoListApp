package todoapp.controller.command;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import todoapp.controller.commandLine.ConfigurationUtils;
import todoapp.controller.commandLine.InvalidCommandException;
import todoapp.controller.commandLine.Option;
import todoapp.model.todoData.ITodoList;
import todoapp.model.todoData.Todo;
import todoapp.model.todoData.TodoList;

public class LoadCsvTest {
  ITodoList todoList;
  String path;
  String name = ConfigurationUtils.LOAD_CSV;
  List<Option> receivedOptions;
  Option option;
  LoadCsv loadCsv;

  @Before
  public void setUp() throws Exception {
    path = "testTodosRead.csv";
    option = ConfigurationUtils.makeOption(name);
    option.setArgs(Arrays.asList(path));
    receivedOptions = Arrays.asList(option);
    todoList = TodoList.createTodoList();
    loadCsv = new LoadCsv(name, 1, receivedOptions, todoList,
        ConfigurationUtils.REQUIRED_SUBCOMMAND_NAMES_FOR_EACH_BASE.get(name));
  }


  @Test
  public void getTodoList() {
    assertEquals(todoList, loadCsv.getTodoList());
  }

  @Test
  public void execute() throws Exception {
    ((TodoList) todoList).setTodoList(new ArrayList<>());
    loadCsv.execute();
    Todo todo = new Todo(1,"Finish HW9",false, LocalDate.of(2020,3,22),1,"school");
    assertEquals(todo, loadCsv.getTodoList().getTodo(0));
  }

  @Test (expected=NullPointerException.class)
  public void executeNullTodoList() throws Exception {
    loadCsv = new LoadCsv(name, 1, receivedOptions, null,
        ConfigurationUtils.REQUIRED_SUBCOMMAND_NAMES_FOR_EACH_BASE.get(name));
    loadCsv.execute();
  }

  @Test (expected=IllegalArgumentException.class)
  public void executeNonEmptyTodoList() throws Exception {
    Todo todo = new Todo(1,"Finish HW9",false, LocalDate.of(2020,3,22),1,"school");
    todoList.addTodo(todo);
    loadCsv = new LoadCsv(name, 1, receivedOptions, todoList,
        ConfigurationUtils.REQUIRED_SUBCOMMAND_NAMES_FOR_EACH_BASE.get(name));
    loadCsv.execute();
  }


  @Test
  public void populateSubCommandList() throws InvalidCommandException {
    loadCsv.populateSubCommandList();
  }

  @Test
  public void createSubCommand() throws InvalidCommandException {
    assertNull(loadCsv.createSubCommand(option));
  }

  @Test
  public void testToString() {
    ((TodoList) todoList).setTodoList(new ArrayList<>());
    loadCsv = new LoadCsv(name, 1, receivedOptions, todoList,
        ConfigurationUtils.REQUIRED_SUBCOMMAND_NAMES_FOR_EACH_BASE.get(name));
    assertEquals("LoadCsv{subCommandList=[], receivedOptions=[Option{name='--csv-file', baseOptionName='--csv-file', hasArg=true, maxNumArgs=1, minNumArgs=1, maxNumAppearance=1, args=[testTodosRead.csv]}], todoList=TodoList{innerList=[]}, requiredSubCommandNames=[], name='--csv-file'}",
        loadCsv.toString());
  }


}