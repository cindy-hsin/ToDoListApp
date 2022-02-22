package todoapp.model.fileIO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import todoapp.controller.commandLine.ConfigurationUtils;
import todoapp.model.todoData.Todo;

/** Converts a line from CSV file to a Todo object. */
public class CsvLineToTodoConverterUtils {

  /**
   * Creates a Todo object after converting and validating fields.
   *
   * @param entry - a list of String, i.e. one line in CSV file
   * @return a Todo object
   * @throws Exception when meets a piece of data in CSV file that has unacceptable data or
   *     formatting.
   */
  public static Todo processTodo(List<String> entry) throws Exception {
    Integer id = convertId(entry.get(ConfigurationUtils.DEFAULT_HEADER_ORDER.get("id")));
    String text = convertText(entry.get(ConfigurationUtils.DEFAULT_HEADER_ORDER.get("text")));
    Boolean completed =
        convertCompletion(entry.get(ConfigurationUtils.DEFAULT_HEADER_ORDER.get("completed")));
    LocalDate due = convertDueDate(entry.get(ConfigurationUtils.DEFAULT_HEADER_ORDER.get("due")));
    Integer priority =
        convertPriority(entry.get(ConfigurationUtils.DEFAULT_HEADER_ORDER.get("priority")));
    String category =
        convertCategory(entry.get(ConfigurationUtils.DEFAULT_HEADER_ORDER.get("category")));
    try {
      return new Todo(id, text, completed, due, priority, category);
    } catch (Exception e) {
      throw new InvalidTodoException(
          "Failed when converting" + entry + "to fields due to " + e.getMessage());
    }
  }

  /**
   * Checks that string provided is not empty at all
   *
   * @param value a string to be checked
   * @throws InvalidTodoException if string is empty
   */
  private static void checkEmpty(String value) throws InvalidTodoException {
    if (value == null || value.length() == 0)
      throw new InvalidTodoException("Field cannot be empty");
  }

  /**
   * Checks if the string provided represents a sign for no value (i.e. "?")
   *
   * @param value value to be checked
   * @return true if the string provided represents a sign for no value(i.e. "?"), otherwise false.
   */
  private static boolean checkForNoValueSign(String value) {
    return value.equals(Todo.NO_VALUE_SIGN);
  }

  /**
   * Converts id from String to Integer
   *
   * @param id String id to be converted
   * @return Integer id
   * @throws InvalidTodoException if id value in provided String format is invalid
   */
  public static Integer convertId(String id) throws InvalidTodoException {
    checkEmpty(id);
    if (checkForNoValueSign(id)) {
      return null;
    }
    try {
      return Integer.parseInt(id);
    } catch (NumberFormatException e) {
      throw new InvalidTodoException("Id:" + id + " is not an integer");
    }
  }

  /**
   * Validates and processes the String text of Todo
   *
   * @param text String text that should be validated and processed
   * @return String text
   * @throws InvalidTodoException if String text contains only a no value sign(i.e. "?")
   */
  public static String convertText(String text) throws InvalidTodoException {
    checkEmpty(text);
    if (checkForNoValueSign(text)) {
      throw new InvalidTodoException("No todo text was provided");
    }
    return text;
  }

  /**
   * Converts completion from String to boolean
   *
   * @param completed String completed that should be converted
   * @return completed boolean
   * @throws InvalidTodoException if fail to convert String completion to boolean value
   */
  public static boolean convertCompletion(String completed) throws InvalidTodoException {
    checkEmpty(completed);
    // completed = completed.toLowerCase();
    if (completed.equalsIgnoreCase("true")) {
      return true;
    } else if (completed.equalsIgnoreCase("false")) {
      return false;
    } else {
      throw new InvalidTodoException("Completed field can contain only 'true' or 'false'");
    }
  }

  /**
   * Converts a due date from String to LocalDate date
   *
   * @param date a String due date to be converted
   * @return LocalDate due date
   * @throws Exception if a due date cannot be parsed from String to LocalDate
   */
  public static LocalDate convertDueDate(String date) throws Exception {
    checkEmpty(date);
    if (checkForNoValueSign(date)) {
      return null;
    }
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Todo.DATE_FORMAT);
    try {
      return LocalDate.parse(date, dateTimeFormatter);
    } catch (DateTimeParseException e) {
      throw new DateTimeParseException(
          "Invalid due date format: " + e.getParsedString() + ". " + e.getMessage(),
          date,
          e.getErrorIndex());
    }
  }

  /**
   * Converts a priority from String to Integer
   *
   * @param priority a String priority to be converted
   * @return Integer priority or null if String priority is no value sign (i.e. "?")
   * @throws Exception if cannot be parsed from String to Integer or the priority is out of valid
   *     range
   */
  public static Integer convertPriority(String priority) throws Exception {
    checkEmpty(priority);
    if (checkForNoValueSign(priority)) {
      return null;
    }
    try {
      int res = Integer.parseInt(priority);
      if (res < Todo.LOWEST_PRIORITY || res > Todo.HIGHEST_PRIORITY) {
        throw new InvalidTodoException(
            "Priority should be between " + Todo.LOWEST_PRIORITY + " and " + Todo.HIGHEST_PRIORITY);
      }
      return res;
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Priority should be an integer");
    } catch (InvalidTodoException e) {
      throw e;
    }
  }

  /**
   * Processes a category represented as a String
   *
   * @param category String category to be converted
   * @return the same string or null if String provided is a no value sign(i.e. "?").
   * @throws InvalidTodoException if String provided is invalid
   */
  public static String convertCategory(String category) throws InvalidTodoException {
    checkEmpty(category);
    if (checkForNoValueSign(category)) {
      return null;
    }
    return category;
  }
}
