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
import todoapp.model.fileIO.CsvReader;
import todoapp.model.fileIO.CsvWriter;
import todoapp.model.todoData.ITodoList;
import todoapp.model.todoData.Todo;
import todoapp.model.todoData.TodoList;

public class UpdateCsvTest {
  ITodoList todoList;
  String path;
  String name = ConfigurationUtils.UPDATE_CSV;
  List<Option> receivedOptions;
  UpdateCsv updateCsv;

  @Before
  public void setUp() throws Exception {
    path = "testTodosWrite.csv";
    receivedOptions = null;
    todoList = TodoList.createTodoList();
    updateCsv = new UpdateCsv(name, 1, receivedOptions, todoList,
        ConfigurationUtils.REQUIRED_SUBCOMMAND_NAMES_FOR_EACH_BASE.get(name), path);
  }

  @Test (expected = InvalidCommandException.class)
  public void createSubCommand() throws InvalidCommandException {
    updateCsv.createSubCommand(ConfigurationUtils.makeOption(name));
    // There's no "updatecsv Option"! User won't enter any option related to updatecsv.
    // The command execution is added in Invoker class.
  }

  @Test
  public void populateSubCommandList() {
    updateCsv.populateSubCommandList();
  }

  @Test
  public void execute() throws Exception {

    Todo todo = new Todo(1,"Finish HW9",false, LocalDate.of(2020,3,22),1,"school");
    todoList.addTodo(todo);
    updateCsv.execute();
    CsvReader reader = new CsvReader();
    reader.readParse(path);
    List<String> line = new ArrayList<>(
        Arrays.asList("1", "Finish HW9", "false", "03/22/2020", "1", "school"));
    assertEquals(line, reader.getEntries().get(reader.getEntries().size()-1));
  }

  @Test
  public void testEquals() {
    UpdateCsv updateCsv2 = new UpdateCsv(name, 1, receivedOptions, todoList,
        ConfigurationUtils.REQUIRED_SUBCOMMAND_NAMES_FOR_EACH_BASE.get(name), "somePath");
    assertFalse(updateCsv2.equals(updateCsv));
  }

  @Test
  public void testHashCode() {
    UpdateCsv sameUpdateCsv = new UpdateCsv(name, 1, receivedOptions, todoList,
        ConfigurationUtils.REQUIRED_SUBCOMMAND_NAMES_FOR_EACH_BASE.get(name), path);
    assertTrue(sameUpdateCsv.hashCode() == updateCsv.hashCode());
  }

  @Test
  public void testToString() {
    // System.out.println(updateCsv.toString());
    ((TodoList)todoList).setTodoList(new ArrayList<>());
    assertEquals("UpdateCsv{subCommandList=[], receivedOptions=null, "
        + "todoList=TodoList{innerList=[]}, requiredSubCommandNames=[], name='', csvPath='testTodosWrite.csv'}",
        updateCsv.toString());
  }
}