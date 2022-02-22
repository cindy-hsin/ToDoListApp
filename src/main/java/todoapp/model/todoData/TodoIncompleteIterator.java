package todoapp.model.todoData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Class representing an iterator over a List of Todos that returns incomplete Todos
 */
public class TodoIncompleteIterator implements Iterator<Todo> {

  /**
   * A list of Todo Objects
   */
  private List<Todo> todos;
  /**
   * Represents the position in the list
   */
  private int index;

  /**
   * Constructor for TodoIncompleteIterator
   *
   * @param todos a list of Todo objects to iterate through
   */
  public TodoIncompleteIterator(List<Todo> todos) {
    this.todos = todos;
    this.index = 0;
  }

  /**
   * Checks if there are incomplete Todos in the rest of the list ( including current Todo)
   *
   * @return true if there are incomplete Todos in the rest of the list, otherwise false
   */
  @Override
  public boolean hasNext() {
    while (this.index < this.todos.size()) {
      Todo curTodo = this.todos.get(this.index);
      if (!curTodo.getCompleted()) {
        return true;
      } else {
        this.index++;
      }
    }

    return false;
  }

  /**
   * Gets the next Todo object in the iteration
   *
   * @return the next Todo object in the iteration
   * @throws NoSuchElementException if the iteration has no more Todo objects
   */
  @Override
  public Todo next() {
    if (this.hasNext()) {
      Todo curTodo = this.todos.get(this.index);
      this.index++;
      return curTodo;
    }
    throw new NoSuchElementException("No next incomplete Todo");
  }

  /**
   * Prints the TodoIncompleteIterator as a string
   *
   * @return a string containing information representing the TodoIncompleteIterator
   */
  @Override
  public String toString() {
    return "TodoIncompleteIterator{" +
        "todos=" + this.todos +
        ", index=" + this.index +
        '}';
  }

  /**
   * Allows TodoCategoryIterators to be compared for equality
   *
   * @param o a different TodoCategoryIterator to compare to
   * @return true if the objects are equal, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    TodoIncompleteIterator that = (TodoIncompleteIterator) o;
    return this.index == that.index && Objects.equals(this.todos, that.todos);
  }

  /**
   * Calculates a unique integer key
   *
   * @return a unique integer key
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.todos, this.index);
  }
}
