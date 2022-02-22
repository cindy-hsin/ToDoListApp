package todoapp.model.todoData;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;

public class TodoIncompleteIteratorTest {

  private TodoIncompleteIterator incompleteIterator;
  private TodoIncompleteIterator sameIncompleteIterator;
  private TodoIncompleteIterator differentIncompleteIterator;
  private String incompleteIteratorDescription;
  private Todo todo1, todo2, todo3;
  private LocalDate date;
  private List<Todo> todos1;
  private List<Todo> todos2;

  @Before
  public void setUp() throws Exception {
    date = LocalDate.of(2021, 4, 17);
    todo1 = new Todo(1, "Finish HW", false, date, 3, "Work");
    todo2 = new Todo(2, "Finish HW", true, date, 3, "School");
    todo3 = new Todo(3, "Finish HW", false, date, 3, "Work");
    todos1 = new ArrayList<>(Arrays.asList(todo1, todo2, todo3));
    todos2 = new ArrayList<>(Arrays.asList(todo1, todo2));
    incompleteIterator = new TodoIncompleteIterator(todos1);
    sameIncompleteIterator = new TodoIncompleteIterator(todos1);
    differentIncompleteIterator = new TodoIncompleteIterator(todos2);
    incompleteIteratorDescription = "TodoIncompleteIterator{todos=[Todo{id=1, text='Finish HW', completed=false, dueDate=2021-04-17, priority=3, category='Work'}, Todo{id=2, text='Finish HW', completed=true, dueDate=2021-04-17, priority=3, category='School'}, Todo{id=3, text='Finish HW', completed=false, dueDate=2021-04-17, priority=3, category='Work'}], index=0}";
  }

  @Test
  public void testIterator() {
    assertTrue(incompleteIterator.hasNext());
    assertEquals(todo1, incompleteIterator.next());
    assertTrue(incompleteIterator.hasNext());
    assertEquals(todo3, incompleteIterator.next());
    assertFalse(incompleteIterator.hasNext());
  }

  @Test
  public void noIncomplete() {
    List<Todo> completeTodos = new ArrayList<>(Arrays.asList(todo2));
    incompleteIterator = new TodoIncompleteIterator(completeTodos);
    assertFalse(incompleteIterator.hasNext());
  }

  @Test(expected = NoSuchElementException.class)
  public void testNoSuchElementException() {
    for (int i = 0; i < 4; i++) {
      incompleteIterator.next();
    }
  }

  @Test
  public void testEquals() {
    TodoIncompleteIterator duplicateIncompleteIterator = new TodoIncompleteIterator(todos1);
    assertTrue(incompleteIterator.equals(incompleteIterator));
    assertFalse(incompleteIterator.equals(incompleteIteratorDescription));
    assertFalse(incompleteIterator.equals(null));
    assertTrue(incompleteIterator.equals(sameIncompleteIterator));
    assertTrue(sameIncompleteIterator.equals(duplicateIncompleteIterator));
    assertTrue(incompleteIterator.equals(duplicateIncompleteIterator));
    assertTrue(sameIncompleteIterator.equals(incompleteIterator));
    assertFalse(incompleteIterator.equals(differentIncompleteIterator));
  }

  @Test
  public void testHashCode() {
    assertTrue(incompleteIterator.hashCode() == sameIncompleteIterator.hashCode());
  }

  @Test
  public void testToString() {
    assertEquals(incompleteIteratorDescription, incompleteIterator.toString());
  }
}
