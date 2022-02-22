package todoapp.model.fileIO;

/** Exception thrown when a field is invalid when creating a new Todo,
 * and when setting a Todo with invalid id as completed */
public class InvalidTodoException extends Exception {
  /**
   * Constructs a new InvalidTodoException
   *
   * @param message message to be printed
   */
  public InvalidTodoException(String message) {
    super(message);
  }
}
