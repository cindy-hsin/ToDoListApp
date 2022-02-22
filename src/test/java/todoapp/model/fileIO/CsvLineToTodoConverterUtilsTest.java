package todoapp.model.fileIO;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import todoapp.model.todoData.Todo;

public class CsvLineToTodoConverterUtilsTest {

  @Test
  public void processTodo() throws Exception {
    LocalDate date = LocalDate.of(2021, 4, 18);
    Todo expectedTodo = new Todo(1, "Finish HW9", true, date, 1, "school");
    List<String> entry = new ArrayList<>();
    entry.add("1");
    entry.add("Finish HW9");
    entry.add("true");
    entry.add("04/18/2021");
    entry.add("1");
    entry.add("school");
    Todo actualTodo = CsvLineToTodoConverterUtils.processTodo(entry);
    assertEquals(expectedTodo, actualTodo);
  }

  @Test(expected = Exception.class)
  public void processTodoWithException() throws Exception {
    List<String> entry = new ArrayList<>();
    entry.add("1");
    entry.add("Finish HW9");
    entry.add("true");
    entry.add("04/18/2021");
    entry.add("1");
    Todo actualTodo = CsvLineToTodoConverterUtils.processTodo(entry);
  }

  @Test
  public void convertId() throws InvalidTodoException {
    Integer expected = 5;
    Integer actual = CsvLineToTodoConverterUtils.convertId("5");
    assertEquals(expected, actual);
  }

  @Test(expected = Exception.class)
  public void convertIdWithException() throws InvalidTodoException {
    Integer result = CsvLineToTodoConverterUtils.convertId("five");
  }

  @Test
  public void convertIdNull() throws InvalidTodoException {
    Integer result = CsvLineToTodoConverterUtils.convertId("?");
    assertEquals(null, result);
  }

  @Test
  public void convertText() throws InvalidTodoException {
    assertEquals("text", CsvLineToTodoConverterUtils.convertText("text"));
  }

  @Test(expected = Exception.class)
  public void convertTextNull() throws InvalidTodoException {
    String result =  CsvLineToTodoConverterUtils.convertText("?");
  }

  @Test(expected = Exception.class)
  public void convertTextWithException() throws InvalidTodoException {
    String result = CsvLineToTodoConverterUtils.convertText("");
  }

  @Test
  public void convertCompletion() throws InvalidTodoException {
    boolean actual = CsvLineToTodoConverterUtils.convertCompletion("true");
    assertEquals(true, actual);
  }

  @Test(expected = Exception.class)
  public void convertCompletionWithException() throws InvalidTodoException {
    boolean result = CsvLineToTodoConverterUtils.convertCompletion("idk");
  }

  @Test
  public void convertDueDate() throws Exception {
    LocalDate expectedDate = LocalDate.of(2021, 4, 18);
    LocalDate actualDate = CsvLineToTodoConverterUtils.convertDueDate("04/18/2021");
    assertEquals(expectedDate, actualDate);
  }

  @Test(expected = Exception.class)
  public void convertDueDateWithException() throws Exception {
    LocalDate result = CsvLineToTodoConverterUtils.convertDueDate("4/18/2021");
  }

  @Test
  public void convertPriority() throws Exception {
    Integer expected = 3;
    Integer actual = CsvLineToTodoConverterUtils.convertPriority("3");
    assertEquals(expected, actual);
  }
  @Test(expected = Exception.class)
  public void convertPriorityWithException() throws Exception {
    Integer result = CsvLineToTodoConverterUtils.convertPriority("three");
  }

  @Test(expected = Exception.class)
  public void convertPriorityWithExceptionOutOfRange() throws Exception {
    Integer result = CsvLineToTodoConverterUtils.convertPriority("8");
  }

  @Test(expected = Exception.class)
  public void convertPriorityWithExceptionOutOfRange2() throws Exception {
    Integer result = CsvLineToTodoConverterUtils.convertPriority("-1");
  }
  @Test
  public void convertCategory() throws InvalidTodoException {
    assertEquals("text", CsvLineToTodoConverterUtils.convertCategory("text"));
  }

  @Test(expected = Exception.class)
  public void convertCategoryWithException() throws InvalidTodoException {
    String result = CsvLineToTodoConverterUtils.convertCategory("");
  }
}
