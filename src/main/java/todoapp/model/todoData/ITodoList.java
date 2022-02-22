package todoapp.model.todoData;

import java.util.List;

/**
 * Interface representing methods of a  TodoList, an immutable collection of todos.
 */
public interface ITodoList {

  /**
   * Adds a new Todo object to the TodoList
   *
   * @param newTodo the new Todo object to be added to the TodoList
   */
  void addTodo(Todo newTodo);

  /**
   * Gets the number of elements in the TodoList
   *
   * @return the number of elements in the TodoList
   */
  int size();

  /**
   * Gets a todo stored in TodoList with the given index
   *
   * @param index the position in the TodoList from which to obtain the todo
   * @return the todo in the TodoList at the given index
   * @throws Exception if the index exceeds the size of the TodoList
   */
  Todo getTodo(int index) throws Exception;

  /**
   * Gets all todos as a list, stored in TodoList
   *
   * @return all the todos as a list, stored in TodoList
   */
  List<Todo> getTodoList();
}
