package todoapp.model.todoData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Class representing an iterator over a List of Todos that returns Todos matching a given category
 */
public class TodoCategoryIterator implements Iterator<Todo> {

  /**
   * A list of Todo Objects
   */
  private List<Todo> todos;
  /**
   * Represents the position in the list
   */
  private int index;
  /**
   * The category the list will be filtered on
   */
  private String targetCategory;

  /**
   * Constructor for TodoCategoryIterator
   *
   * @param todos    a List of Todo objects to iterate through
   * @param category The category to filter the list on
   */
  public TodoCategoryIterator(List<Todo> todos, String category) {
    this.todos = todos;
    this.index = 0;
    this.targetCategory = category;
    // this.todos = this.filterTodosByCategory(category);
  }

  /**
   * Checks if there are qualified elements in the rest of the list ( including current element)
   *
   * @return true if there are qualified elements in the rest of the list, otherwise false
   */
  @Override
  public boolean hasNext() {
    while (this.index < this.todos.size()) {
      Todo curTodo = this.todos.get(this.index);
      if (curTodo.getCategory() != null && curTodo.getCategory()
          .equals(this.targetCategory)) {
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
    throw new NoSuchElementException("No next Todo with target category: " + this.targetCategory);
  }

  /**
   * Prints the TodoCategoryIterator as a string
   *
   * @return a string containing information representing the TodoCategoryIterator
   */
  @Override
  public String toString() {
    return "TodoCategoryIterator{" +
        "todos=" + this.todos +
        ", index=" + this.index +
        ", targetCategory='" + this.targetCategory + '\'' +
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
    TodoCategoryIterator that = (TodoCategoryIterator) o;
    return index == that.index && Objects.equals(this.todos, that.todos) && Objects
        .equals(this.targetCategory, that.targetCategory);
  }

  /**
   * Calculates a unique integer key
   *
   * @return a unique integer key
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.todos, this.index, this.targetCategory);
  }
}









