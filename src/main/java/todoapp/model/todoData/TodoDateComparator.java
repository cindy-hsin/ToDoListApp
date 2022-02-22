package todoapp.model.todoData;

import java.util.Comparator;

/**
 * Class to compare and sort Todo Objects by date
 */
public class TodoDateComparator implements Comparator<Todo> {

  /**
   * Compares its two arguments for order by date. Returns a negative integer, zero, or a positive
   * integer if the first argument is less than, equal to, or greater than the second,
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
    if (o1.getDueDate() == null && o2.getDueDate() == null) {
      return 0;
    }
    if (o1.getDueDate()
        == null)
    {
      return 1;
    }
    if (o2.getDueDate() == null) {
      return -1;
    }
    return o1.getDueDate().compareTo(o2.getDueDate());
  }

  /**
   * Prints the TodoDateComparator as a string
   *
   * @return a string containing information representing the comparator
   */
  @Override
  public String toString() {
    return "TodoDateComparator{}";
  }
}
