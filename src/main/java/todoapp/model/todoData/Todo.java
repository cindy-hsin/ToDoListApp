package todoapp.model.todoData;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Todo {

  /**
   * Default value of fields in a Todo
   */
  public static final Integer HIGHEST_PRIORITY = 3;
  public static final Integer LOWEST_PRIORITY = 1;
  public static final String DATE_FORMAT = "MM/dd/yyyy";
  public static final String NO_VALUE_SIGN = "?";
  public static final String DEFAULT_TEXT = null;
  public static final Boolean DEFAULT_COMPLETED = false;
  public static final LocalDate DEFAULT_DUE_DATE = null;
  public static final Integer DEFAULT_PRIORITY = null;
  public static final String DEFAULT_CATEGORY = null;

  private Integer id;
  private String text;
  private Boolean completed;
  private LocalDate dueDate;
  private Integer priority;
  private String category;


  /**
   * Constructor for Todo
   *
   * @param id        identification number of a todo, an Integer
   * @param text      a description of the task to be done, a String
   * @param completed indicates whether the task is complete or incomplete, a Boolean
   * @param dueDate   indicates the due date of the task, a Local Date
   * @param priority  indicates the priority of the todo, an Integer
   * @param category  a user-specified String to group related todos
   */
  public Todo(Integer id, String text, Boolean completed, LocalDate dueDate, Integer priority,
      String category) {
    this.id = id;
    this.text = text;
    this.completed = completed;
    this.dueDate = dueDate;
    this.priority = priority;
    this.category = category;
  }


  /**
   * Sets the Todo's status to completed
   */
  public void setCompleted() {
    this.completed = true;
  }

  /**
   * Gets the identification number of a Todo
   *
   * @return the identification number of a Todo
   */
  public Integer getId() {
    return this.id;
  }

  /**
   * Gets the description of a Todo
   *
   * @return the description of a Todo
   */
  public String getText() {
    return this.text;
  }

  /**
   * Gets the due date of a Todo
   *
   * @return the due date of a Todo
   */
  public LocalDate getDueDate() {
    return this.dueDate;
  }

  /**
   * Gets the priority of a Todo
   *
   * @return the priority of a Todo
   */
  public Integer getPriority() {
    return this.priority;
  }

  /**
   * Gets the category of a Todo
   *
   * @return the category of a Todo
   */
  public String getCategory() {
    return this.category;
  }

  /**
   * Gets information about whether a Todo is oomplete or incomplete
   *
   * @return true if complete, false otherwise
   */
  public Boolean getCompleted() {
    return this.completed;
  }


  /**
   * Allows Todos to be compared for equality
   *
   * @param o a different Todo to compare to
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
    Todo todo = (Todo) o;
    return Objects.equals(this.getId(), todo.getId()) && Objects
        .equals(this.getText(), todo.getText()) && Objects
        .equals(this.getCompleted(), todo.getCompleted()) && Objects
        .equals(this.getDueDate(), todo.getDueDate()) && Objects
        .equals(this.getPriority(), todo.getPriority()) && Objects
        .equals(this.getCategory(), todo.getCategory());
  }

  /**
   * Calculates a unique integer key
   *
   * @return a unique integer key
   */
  @Override
  public int hashCode() {
    return Objects
        .hash(this.getId(), this.getText(), this.getCompleted(), this.getDueDate(),
            this.getPriority(), this.getCategory());
  }

  /**
   * Prints the Todo as a string
   *
   * @return a string containing information representing the Todo
   */
  @Override
  public String toString() {
    return "Todo{" +
        "id=" + this.id +
        ", text='" + this.text + '\'' +
        ", completed=" + this.completed +
        ", dueDate=" + this.dueDate +
        ", priority=" + this.priority +
        ", category='" + this.category + '\'' +
        '}';
  }
}
