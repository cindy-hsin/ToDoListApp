package todoapp.model.fileIO;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import todoapp.model.todoData.Todo;

public class TodoToCsvLineConverterUtilsTest {

  @Test
  public void processTodo() {
    LocalDate date = LocalDate.of(2021, 4, 18);
    Todo todo = new Todo(1, "Finish HW9", true, date, null, "school");
    List<String> expectedEntry = new ArrayList<>();
    expectedEntry.add("1");
    expectedEntry.add("Finish HW9");
    expectedEntry.add("true");
    expectedEntry.add("04/18/2021");
    expectedEntry.add("?");
    expectedEntry.add("school");
    List<String> actual = TodoToCsvLineConverterUtils.processTodo(todo);
    assertEquals(expectedEntry, actual);
  }
}