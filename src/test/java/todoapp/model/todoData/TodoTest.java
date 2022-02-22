package todoapp.model.todoData;

import static org.junit.Assert.*;

import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class TodoTest {
  private LocalDate date;
  private Todo todo1, todo2, sameTodoTodo1, duplicateTodo1;
  private String todoDescription;

  @Before
  public void setUp() throws Exception {
    todo1 = new Todo(1, "Finish HW", false, date, 3, "Work");
    todo2 = new Todo(2, "Finish HW", true, date, 3, "School");
    duplicateTodo1 = new Todo(1, "Finish HW", false, date, 3, "Work");
    todoDescription = "Todo{id=1, text='Finish HW', completed=false, dueDate=null, priority=3, category='Work'}";
  }

  @Test
  public void setCompleted() {
    assertFalse(todo1.getCompleted());
    todo1.setCompleted();
    assertTrue(todo1.getCompleted());
  }

  @Test
  public void getId() {
    Integer expectedId = 1;
    assertEquals(expectedId, todo1.getId());
  }

  @Test
  public void getText() {
    assertEquals("Finish HW", todo1.getText());
  }

  @Test
  public void getDueDate() {
    assertEquals(date, todo1.getDueDate());
  }

  @Test
  public void getPriority() {
    Integer expectedPriority = 3;
    assertEquals(expectedPriority, todo1.getPriority());
  }

  @Test
  public void getCategory() {
    assertEquals("Work", todo1.getCategory());
  }

  @Test
  public void getCompleted() {
    assertFalse(todo1.getCompleted());
    assertTrue(todo2.getCompleted());
  }

  @Test
  public void testEquals() {
    Todo anotherDuplicateTodo1 = new Todo(1, "Finish HW", false, date, 3, "Work");
    assertTrue(todo1.equals(todo1));
    assertFalse(todo1.equals(todoDescription));
    assertFalse(todo1.equals(null));
    assertTrue(todo1.equals(duplicateTodo1));
    assertTrue(duplicateTodo1.equals(todo1));
    assertTrue(duplicateTodo1.equals(anotherDuplicateTodo1));
    assertTrue(todo1.equals(anotherDuplicateTodo1));
    assertFalse(todo1.equals(todo2));
  }

  @Test
  public void testHashCode() {
    assertTrue(todo1.hashCode() == todo1.hashCode());
  }

  @Test
  public void testToString() {
    assertEquals(todoDescription, todo1.toString());
  }
}