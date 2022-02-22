package todoapp.model.todoData;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TodoListTest {

  private ITodoList list;
  private LocalDate date;
  private Todo todo1;
  private Todo todo2;
  private Todo todo3;
  private String listDescription;

  @Before
  public void setUp() throws Exception {
    list = TodoList.createTodoList();
    date = LocalDate.of(2021, 4, 17);
    todo1 = new Todo(1, "Finish HW", false, date, 3, "Work");
    todo2 = new Todo(2, "Finish HW", true, date, 3, "School");
    todo3 = new Todo(3, "Finish HW", false, date, 3, "Work");
    list.addTodo(todo1);
    list.addTodo(todo2);
    listDescription = "TodoList{innerList=[Todo{id=1, text='Finish HW', completed=false, dueDate=2021-04-17, priority=3, category='Work'}, Todo{id=2, text='Finish HW', completed=true, dueDate=2021-04-17, priority=3, category='School'}]}";
  }


  @Test
  public void getTodoList() {
    ((TodoList) list).setTodoList(new ArrayList<>());
    list.addTodo(todo1);
    list.addTodo(todo2);
    List<Todo> expectedList = new ArrayList<Todo>();
    expectedList.add(todo1);
    expectedList.add(todo2);
    assertEquals(expectedList, list.getTodoList());
  }

  @Test
  public void getTodo() throws Exception {
    assertEquals(todo2, list.getTodo(1));
  }

  @Test
  public void addTodo() {
    ((TodoList) list).setTodoList(new ArrayList<>());
    list.addTodo(todo1);
    list.addTodo(todo2);
    List<Todo> expectedList = new ArrayList<Todo>();
    expectedList.add(todo1);
    expectedList.add(todo2);
    assertEquals(expectedList, list.getTodoList());
    expectedList.add(todo3);
    list.addTodo(todo3);
    assertEquals(expectedList, list.getTodoList());
  }

  @Test
  public void size() {
    ((TodoList) list).setTodoList(new ArrayList<>());
    list.addTodo(todo1);
    list.addTodo(todo2);
    assertEquals(2, list.size());
  }


  @Test
  public void testToString() {
    ((TodoList) list).setTodoList(new ArrayList<>());
    list.addTodo(todo1);
    list.addTodo(todo2);
    assertEquals(listDescription, list.toString());
  }
}