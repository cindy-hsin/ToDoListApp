package todoapp.controller.command;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
//import org.graalvm.compiler.core.common.type.ArithmeticOpTable.UnaryOp.Abs;
import org.junit.Before;
import org.junit.Test;
import todoapp.controller.commandLine.ConfigurationUtils;
import todoapp.controller.commandLine.Option;
import todoapp.model.fileIO.CsvReader;
import todoapp.model.fileIO.CsvWriter;
import todoapp.model.todoData.ITodoList;
import todoapp.model.todoData.Todo;
import todoapp.model.todoData.TodoList;

public class InvokerTest {
  ITodoList todoList;
  Map<String, List<Option>> receivedOptionsInGroup;
  Option loadCsv, addTodo, addTodoText, completeTodo, displayTodo;
  //List<AbstractBaseCommand> baseCommandList;
  String csvPath;
  Invoker invoker;
  Todo newTodo, oldTodo;
  LocalDate date;
  static final Map<String, Set<String>> REQUIRED_SUB_NAMES_FOR_EACH_BASE =
      ConfigurationUtils.REQUIRED_SUBCOMMAND_NAMES_FOR_EACH_BASE;

  @Before
  public void setUp() throws Exception {
    receivedOptionsInGroup = new HashMap<>();
    loadCsv = ConfigurationUtils.makeOption(ConfigurationUtils.LOAD_CSV);
    csvPath = "testTodosReadWrite.csv";

    // Reset csvfile
    List<String> headerLine = new ArrayList<>(
        Arrays.asList("id","text","completed","due","priority","category"));
    List<String> originalLine = new ArrayList<>(
        Arrays.asList("1", "Finish HW9", "false", "03/22/2020", "1", "school"));
    CsvWriter writer = new CsvWriter();
    writer.write(csvPath, Arrays.asList(headerLine, originalLine));

    loadCsv.setArgs(Arrays.asList(csvPath));
    addTodo = ConfigurationUtils.makeOption(ConfigurationUtils.ADD_TODO);
    addTodoText = ConfigurationUtils.makeOption(ConfigurationUtils.ADD_TODO_TEXT);
    addTodoText.setArgs(Arrays.asList("Clean", "House"));
    completeTodo = ConfigurationUtils.makeOption(ConfigurationUtils.COMPLETE_TODO);
    completeTodo.setArgs(Arrays.asList("1"));
    displayTodo = ConfigurationUtils.makeOption(ConfigurationUtils.DISPLAY);
    date = LocalDate.of(2020, 3, 22);
    oldTodo = new Todo(1, "Finish HW9", true, date, 1, "school");
    newTodo = new Todo(2, "Clean House", false, null, null, null);

    List<Option> list = new ArrayList<Option>();
    list.add(loadCsv);
    list.add(addTodo);
    list.add(addTodoText);
    list.add(completeTodo);
    list.add(displayTodo);
    receivedOptionsInGroup.put(ConfigurationUtils.LOAD_CSV, Arrays.asList(loadCsv));
    receivedOptionsInGroup.put(ConfigurationUtils.ADD_TODO, Arrays.asList(addTodoText,addTodo));
    receivedOptionsInGroup.put(ConfigurationUtils.COMPLETE_TODO, Arrays.asList(completeTodo));
    receivedOptionsInGroup.put(ConfigurationUtils.DISPLAY, Arrays.asList(displayTodo));

    todoList = TodoList.createTodoList();
    invoker = new Invoker(receivedOptionsInGroup);
  }

  @Test
  public void execute() throws Exception {
    invoker.execute();
    // baseCommandList is populated and sorted, todoList is also modified.
    // subCommand is also created (testing skipped).

    // Check baseCommand's order.
    assertEquals(ConfigurationUtils.LOAD_CSV, invoker.baseCommandList.get(0).name);
    assertEquals(ConfigurationUtils.UPDATE_CSV, invoker.baseCommandList.get(4).name);
    // Check todoList is updated
    assertEquals(oldTodo, todoList.getTodo(0));
    assertEquals(newTodo, todoList.getTodo(1));

    // Check Csv gets updated
    List<String> line1 = new ArrayList<>(
        Arrays.asList("1", "Finish HW9", "true", "03/22/2020", "1", "school"));
    List<String> line2 = new ArrayList<>(
        Arrays.asList("2", "Clean House", "false", "?", "?", "?"));
    List<List<String>> updatedContent = Arrays.asList(line1, line2);
    CsvReader reader = new CsvReader();
    reader.readParse(csvPath);
    assertEquals(updatedContent, reader.getEntries());
  }

  @Test (expected = IllegalArgumentException.class)
  public void executeLoadCsvFail() throws Exception {
    // e.g. file not found, or not in csv format
    loadCsv.setArgs(Arrays.asList("unknownPath"));
    invoker.execute();
  }


  @Test
  public void testEquals() {
    assertTrue(invoker.equals(invoker));
    assertFalse(invoker.equals(null));
    assertFalse(invoker.equals("invoker"));
    Invoker sameInvoker =  new Invoker(receivedOptionsInGroup);
    assertTrue(invoker.equals(sameInvoker));
  }

  @Test
  public void testHashcode() {
    Invoker sameInvoker =  new Invoker(receivedOptionsInGroup);
    assertTrue(invoker.hashCode() == sameInvoker.hashCode());
  }

  @Test
  public void testToString() {

    String expected = "Invoker{todoList=TodoList{innerList=["
        + "Todo{id=1, text='Finish HW9', completed=true, dueDate=2020-03-22, priority=1, category='school'},"
        + " Todo{id=2, text='Clean House', completed=false, dueDate=null, priority=null, category='null'}]}, "
        + "receivedOptionsInGroup={--display=[Option{name='--display', baseOptionName='--display', hasArg=false, maxNumArgs=50, minNumArgs=0, maxNumAppearance=1, args=null}], "
        + "--csv-file=[Option{name='--csv-file', baseOptionName='--csv-file', hasArg=true, maxNumArgs=1, minNumArgs=1, maxNumAppearance=1, args=[testTodosReadWrite.csv]}], "
        + "--add-todo=[Option{name='--todo-text', baseOptionName='--add-todo', hasArg=true, maxNumArgs=50, minNumArgs=1, maxNumAppearance=1, args=[Clean, House]}, "
        + "Option{name='--add-todo', baseOptionName='--add-todo', hasArg=false, maxNumArgs=50, minNumArgs=0, maxNumAppearance=1, args=null}], "
        + "--complete-todo=[Option{name='--complete-todo', baseOptionName='--complete-todo', hasArg=true, maxNumArgs=1, minNumArgs=1, maxNumAppearance=10, args=[1]}]}, "
        + "baseCommandList=[], "
        + "csvPath='null'}";

    assertEquals(expected, invoker.toString());
  }
}