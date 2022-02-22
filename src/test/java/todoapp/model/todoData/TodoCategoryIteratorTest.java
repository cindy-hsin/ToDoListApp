
package todoapp.model.todoData;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;

public class TodoCategoryIteratorTest {

  private TodoCategoryIterator workCategoryIterator;
  private TodoCategoryIterator sameWorkCategoryIterator;
  private TodoCategoryIterator schoolCategoryIterator;
  private String workCategoryIteratorDescription;
  private Todo todo1, todo2, todo3;
  private LocalDate date;
  private List<Todo> todos;

  @Before
  public void setUp() throws Exception {
    date = LocalDate.of(2021, 4, 17);
    todo1 = new Todo(1, "Finish HW", true, date, 3, "Work");
    todo2 = new Todo(2, "Finish HW", true, date, 3, "School");
    todo3 = new Todo(3, "Finish HW", true, date, 3, "Work");
    todos = new ArrayList<>(Arrays.asList(todo1, todo2, todo3));
    workCategoryIterator = new TodoCategoryIterator(todos, "Work");
    sameWorkCategoryIterator = new TodoCategoryIterator(todos, "Work");
    schoolCategoryIterator = new TodoCategoryIterator(todos, "School");
    workCategoryIteratorDescription = "TodoCategoryIterator{todos=[Todo{id=1, text='Finish HW', completed=true, dueDate=2021-04-17, priority=3, category='Work'}, Todo{id=2, text='Finish HW', completed=true, dueDate=2021-04-17, priority=3, category='School'}, Todo{id=3, text='Finish HW', completed=true, dueDate=2021-04-17, priority=3, category='Work'}], index=0, targetCategory='Work'}";
  }

  @Test
  public void testIterator() {
    assertTrue(workCategoryIterator.hasNext());
    assertEquals(todo1, workCategoryIterator.next());
    assertTrue(workCategoryIterator.hasNext());
    assertEquals(todo3, workCategoryIterator.next());
    assertFalse(workCategoryIterator.hasNext());
  }

  @Test
  public void noMatchCategory() {
    TodoCategoryIterator personalIterator = new TodoCategoryIterator(todos, "personal");
    assertFalse(personalIterator.hasNext());
  }

  @Test(expected = NoSuchElementException.class)
  public void testNoSuchElementException() {
    for (int i = 0; i < 4; i++) {
      workCategoryIterator.next();
    }
  }

  @Test
  public void testEquals() {
    TodoCategoryIterator duplicateWorkCategoryIterator = new TodoCategoryIterator(todos, "Work");
    assertTrue(workCategoryIterator.equals(workCategoryIterator));
    assertFalse(workCategoryIterator.equals(workCategoryIteratorDescription));
    assertFalse(workCategoryIterator.equals(null));
    assertTrue(workCategoryIterator.equals(sameWorkCategoryIterator));
    assertTrue(sameWorkCategoryIterator.equals(duplicateWorkCategoryIterator));
    assertTrue(workCategoryIterator.equals(duplicateWorkCategoryIterator));
    assertTrue(sameWorkCategoryIterator.equals(workCategoryIterator));
    assertFalse(workCategoryIterator.equals(schoolCategoryIterator));
  }

  @Test
  public void testHashCode() {
    assertTrue(workCategoryIterator.hashCode() == sameWorkCategoryIterator.hashCode());
  }

  @Test
  public void testToString() {
    assertEquals(workCategoryIteratorDescription, workCategoryIterator.toString());
  }
}