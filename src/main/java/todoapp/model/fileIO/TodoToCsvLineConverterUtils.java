package todoapp.model.fileIO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import todoapp.model.todoData.Todo;

/** Utility class to convert a Todo to a List of String. */
public class TodoToCsvLineConverterUtils {

  /**
   * Converts fields of a Todo object into a List of Strings
   *
   * @param todo Todo object to be converted into collection of Strings
   * @return List of Strings representing fields of provided Todo object
   */
  public static List<String> processTodo(Todo todo) {
    List<String> line = new ArrayList<>();
    line.add(processID(todo.getId()));
    line.add(processText(todo.getText()));
    line.add(processCompleted(todo.getCompleted()));
    line.add(processDue(todo.getDueDate()));
    line.add(processPriority(todo.getPriority()));
    line.add(processCategory(todo.getCategory()));
    return line;
  }

  /**
   * Processes Integer id into String
   *
   * @param id Integer id of Todo to be processed
   * @return String id of a provided Todo object
   */
  private static String processID(Integer id) {
    return id.toString();
  }

  /**
   * Processes text of a Todo to A string
   *
   * @param text String text
   * @return String text of a provided Todo object
   */
  private static String processText(String text) {
    return text;
  }

  /**
   * Processes boolean completed into String
   *
   * @param completed boolean completed of Todo object to be processed
   * @return String completion status of a provided Todo object
   */
  private static String processCompleted(Boolean completed) {
    return completed.toString();
  }

  /**
   * Processes LocalDate due date into String date
   *
   * @param due LocalDate due date of a Todo object
   * @return String due date of a provided Todo object. If provided due is null, returns a no value
   *     sign(i.e. "?").
   */
  private static String processDue(LocalDate due) {
    return due == null
        ? Todo.NO_VALUE_SIGN
        : due.format(DateTimeFormatter.ofPattern(Todo.DATE_FORMAT));
  }

  /**
   * Processes Integer priority into String
   *
   * @param priority Integer priority of a Todo object
   * @return String priority of a provided Todo object. If provided priority is null, returns a no
   *     value sign(i.e. "?").
   */
  private static String processPriority(Integer priority) {
    return priority == null ? Todo.NO_VALUE_SIGN : priority.toString();
  }

  /**
   * Processes a String category of Todo object to String.
   *
   * @param category String category of a Todo object
   * @return String category of a provided Todo object. If provided category is null, returns a no
   *     value sign(i.e. "?").
   */
  private static String processCategory(String category) {
    return category == null ? Todo.NO_VALUE_SIGN : category;
  }
}
