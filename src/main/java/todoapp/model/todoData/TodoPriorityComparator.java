package todoapp.model.todoData;

import java.util.Comparator;

/**
 * Class to compare and sort Todo Objects by priority
 */
public class TodoPriorityComparator implements Comparator<Todo> {

  /**
   * Compares its two arguments for order by priority. Returns a negative integer, zero, or a
   * positive integer if the first argument is less than, equal to, or greater than the second,
   * respectively.
   *
   * @param o1 the first object to be compared
   * @param o2 the second object to be compared
   * @return a negative integer, zero, or a positive integer if the first argument is less than,
   * equal to, or greater than the second, respectively.
   * @throws ClassCastException if the arguments' types prevent them from being compared by this
   *                            comparator
   */
  @Override
  public int compare(Todo o1, Todo o2) {
    if (o1.getPriority() == null && o2.getPriority() == null) {
      return 0;
    }
    if (o1.getPriority() == null)
    {
      return 1;
    }
    if (o2.getPriority() == null) {
      return -1;
    }
    return o1.getPriority().compareTo(o2.getPriority());
  }

  /**
   * Prints the TodoDateComparator as a string
   *
   * @return a string containing information representing the comparator
   */
  @Override
  public String toString() {
    return "TodoPriorityComparator{}";
  }
}
