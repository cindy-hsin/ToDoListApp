package todoapp.model.todoData;

import static org.junit.Assert.*;

import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class TodoPriorityComparatorTest {
  private TodoPriorityComparator todoPriorityComparator;
  private LocalDate date;
  private Todo todo1, todo2, todo3, todo4;
  private String todoPriorityComparatorDescription;

  @Before
  public void setUp() throws Exception {
    todoPriorityComparator = new TodoPriorityComparator();
    date = LocalDate.of(2021, 4, 17);
    todo1 = new Todo(1, "Finish HW", true, date, 3, "Work");
    todo2 = new Todo(3, "Finish HW", true, date, null, "Work");
    todo3 = new Todo(3, "Finish HW", true, date, null, "Work");
    todo4 = new Todo(3, "Finish HW", true, date, 2, "Work");
    todoPriorityComparatorDescription = "TodoPriorityComparator{}";
  }

  @Test
  public void compare() {
    assertEquals(0, todoPriorityComparator.compare(todo2, todo2));
    assertEquals(1, todoPriorityComparator.compare(todo2, todo1));
    assertEquals(-1, todoPriorityComparator.compare(todo1, todo2));
    assertEquals(1, todoPriorityComparator.compare(todo1, todo4));
    assertEquals(-1, todoPriorityComparator.compare(todo4, todo1));
    assertEquals(0, todoPriorityComparator.compare(todo4, todo4));
  }

  @Test
  public void testToString() {
    assertEquals(todoPriorityComparatorDescription, todoPriorityComparator.toString());
  }
}