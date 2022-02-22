package todoapp.model.todoData;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a list of Todo objects
 */
public class TodoList implements ITodoList {

  /**
   * Eager-allocation implementation of a single TodoList
   */
  protected static final ITodoList INSTANCE = new TodoList();
  /**
   * Represents a list of Todo objects
   */
  private List<Todo> innerList;


  /**
   * Private Constructor for TodoList
   */
  private TodoList(){
    this.innerList = new ArrayList<>();
  }

  /**
   * Singleton pattern Constructor.
   * @return the single INSTANCE of a TodoList
   */
  public static ITodoList createTodoList(){
    return INSTANCE;
  }

  /**
   * Gets all todos as a list, stored in TodoList
   * @return all the todos as a list, stored in TodoList
   */
  public List<Todo> getTodoList() {
    return this.innerList;
  }

  /**
   * Gets a todo stored in TodoList with the given index
   * @param index the position in the TodoList from which to obtain the todo
   * @return the todo in the TodoList at the given index
   * @throws Exception if the index exceeds the size of the TodoList
   */
  @Override
  public Todo getTodo(int index) throws Exception {
    return this.innerList.get(index);
  }

  /**
   * Adds a new Todo object to the TodoList
   * @param newTodo the new Todo object to be added to the TodoList
   */
  @Override
  public void addTodo(Todo newTodo) {
    this.innerList.add(newTodo);
  }

  /**
   * Gets the number of elements in the TodoList
   * @return the number of elements in the TodoList
   */
  @Override
  public int size() {
    return this.innerList.size();
  }

  // For testing purpose. Can be deleted after testing
  public void setTodoList(List<Todo> list) {
    this.innerList = list;
  }


  /**
   * Prints the TodoList as a string
   * @return a string containing information representing the TodoList
   */
  @Override
  public String toString() {
    return "TodoList{" +
        "innerList=" + this.innerList +
        '}';
  }
}
