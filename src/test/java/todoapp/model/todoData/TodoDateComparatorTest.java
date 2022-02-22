package todoapp.model.todoData;

import static org.junit.Assert.*;

import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class TodoDateComparatorTest {

  private TodoDateComparator todoDateComparator;
  private LocalDate smallerDate;
  private LocalDate largerDate;
  private LocalDate date;
  private LocalDate nullDate;
  private String todoDateComparatorDescription;

  private Todo todo1, todo2, todo3;

  @Before
  public void setUp() throws Exception {
    todoDateComparator = new TodoDateComparator();
    date = LocalDate.of(2021, 4, 17);
    todo1 = new Todo(1, "Finish HW", true, date, 3, "Work");
    todo2 = new Todo(3, "Finish HW", true, nullDate, 3, "Work");
    todo3 = new Todo(3, "Finish HW", true, nullDate, 3, "Work");
    todoDateComparatorDescription = "TodoDateComparator{}";
  }

  @Test
  public void compare() {
    assertEquals(0, todoDateComparator.compare(todo2, todo3));
    assertEquals(1, todoDateComparator.compare(todo2, todo1));
    assertEquals(-1, todoDateComparator.compare(todo1, todo2));
    assertEquals(0, todoDateComparator.compare(todo1, todo1));
  }

  @Test
  public void testToString() {
    assertEquals(todoDateComparatorDescription, todoDateComparator.toString());
  }
}