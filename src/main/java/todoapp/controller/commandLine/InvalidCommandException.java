package todoapp.controller.commandLine;


/**
 * Exceptions related to incorrect input from user.
 */
public class InvalidCommandException extends Exception{

  public InvalidCommandException(String message) {
    super(message);
  }
}
