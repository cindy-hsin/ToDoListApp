package todoapp.controller.command;


import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import todoapp.controller.commandLine.ConfigurationUtils;
import todoapp.controller.commandLine.InvalidCommandException;
import todoapp.controller.commandLine.Option;
import todoapp.model.fileIO.CsvLineToTodoConverterUtils;
import todoapp.model.fileIO.InvalidTodoException;
import todoapp.model.todoData.ITodoList;
import todoapp.model.todoData.Todo;

/**
 * Concrete command class, that represents the AddTodo BaseCommand.
 */
public class AddTodo extends AbstractBaseCommand{

  //  Global Variables to be modified by nested class's execute method.
  private String text;          // might need to change to protected for testing
  private Boolean completed;
  private LocalDate due;
  private Integer priority;
  private String category;

  /**
   * Constructor of an AddTodo BaseCommand.
   * @param name - the name of this BaseCommand. "--add-todo"
   * @param order - the order of this BaseCommand. Pre-defined in Configuration class.
   * @param receivedOptions - the List of Options related to this base command/group, received from command line parser.
   * @param todoList - the Singleton TodoList object
   * @param requiredSubCommandNames - the set of required SubCommand's name of this BaseCommand. Defined in Configuration class.
   */
  public AddTodo(String name, int order, List<Option> receivedOptions, ITodoList todoList, Set<String> requiredSubCommandNames) {
    super(name, order, receivedOptions, todoList, requiredSubCommandNames);
    this.text = Todo.DEFAULT_TEXT;      // null
    this.completed = Todo.DEFAULT_COMPLETED; // false
    this.due = Todo.DEFAULT_DUE_DATE; // null
    this.priority = Todo.DEFAULT_PRIORITY; // null
    this.category = Todo.DEFAULT_CATEGORY; // null
  }

  /**
   * Execute the AddTodo's received SubCommands:
   * Set each field of the new Todo by executing each Subcommand corresponding to each received Option,
   * instantiate a new Todo with these fields, and add to TodoList.
   * @throws Exception
   */
  @Override
  public void execute() throws Exception {
    this.populateSubCommandList();
    super.validateRequiredSubCommand();
    for (AbstractSubCommand sc: this.subCommandList) {
      sc.execute();
    }
    Todo newTodo = new Todo(this.todoList.size() + 1, this.text,
        this.completed, this.due, this.priority, this.category);
    this.todoList.addTodo(newTodo);     // need an addTodo method in ITodoList & TodoList class
  }

  /**
   * Populate the SubcommandList.
   * @throws InvalidCommandException - if there's Unknown Option received.
   */
  @Override
  public void populateSubCommandList() throws InvalidCommandException {
    for (Option option: this.receivedOptions) {
      AbstractSubCommand subCommand = this.createSubCommand(option);
      this.subCommandList.add(subCommand);
    }
  }

  /**
   * Create a SubCommand for each received Option.
   * @param option - a received Option
   * @return the corresponding SubCommand object.
   * @throws InvalidCommandException - if there's Unknown Option received.
   */
  @Override
  public AbstractSubCommand createSubCommand(Option option) throws InvalidCommandException {
    switch(option.getName()) {
      case ConfigurationUtils.ADD_TODO:
        return new AddNewTodo(option.getName(), option.getArgs());
      case ConfigurationUtils.ADD_TODO_TEXT:
        return new AddTodoText(option.getName(), option.getArgs());
      case ConfigurationUtils.ADD_TODO_COMPLETED:
        return new AddTodoCompleted(option.getName(), option.getArgs());
      case ConfigurationUtils.ADD_TODO_DUE:
        return new AddTodoDue(option.getName(), option.getArgs());
      case ConfigurationUtils.ADD_TODO_PRIORITY:
        return new AddTodoPriority(option.getName(), option.getArgs());
      case ConfigurationUtils.ADD_TODO_CATEGORY:
        return new AddTodoCategory(option.getName(), option.getArgs());
      default:
        throw new InvalidCommandException("Unknown Command: " + option.getName());
    }
  }


  @Override
  public boolean equals(Object o) {
    if (!super.equals(o)) {
      return false;
    }
    AddTodo addTodo = (AddTodo) o;
    return Objects.equals(text, addTodo.text) && Objects
        .equals(completed, addTodo.completed) && Objects.equals(due, addTodo.due)
        && Objects.equals(priority, addTodo.priority) && Objects
        .equals(category, addTodo.category);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), text, completed, due, priority, category);
  }

  @Override
  public String toString() {
    return "AddTodo{" +
        "subCommandList=" + subCommandList +
        ", receivedOptions=" + receivedOptions +
        ", todoList=" + todoList +
        ", requiredSubCommandNames=" + requiredSubCommandNames +
        ", name='" + name + '\'' +
        ", text='" + text + '\'' +
        ", completed=" + completed +
        ", due=" + due +
        ", priority=" + priority +
        ", category='" + category + '\'' +
        '}';
  }

  /**
   * Represents an AddNewTodo SubCommand.
   */
  protected class AddNewTodo extends AbstractSubCommand {
    public AddNewTodo(String name, List<String> args) {
      super(name, args);
    }

    @Override
    public void execute() {}

  }
  /**
   * Represents an AddTodoText SubCommand.
   */
  protected class AddTodoText extends AbstractSubCommand {
    public AddTodoText(String name, List<String> args) {
      super(name, args);
    }

    /**
     * Execute the AddTodoText SubCommand. (Convert the received text argument from a
     * list of Strings to one String, to be assigned to the new Todo's text field)
     * @throws InvalidTodoException
     */
    @Override
    public void execute() throws InvalidTodoException {
      String joinedText = String.join(" ", this.args);
      text = CsvLineToTodoConverterUtils.convertText(joinedText);   // validate if string is null, 0-length or "?"
    }

  }
  /**
   * Represents an AddTodoCompleted SubCommand.
   */
  protected class AddTodoCompleted extends AbstractSubCommand {

    public AddTodoCompleted(String name, List<String> args) {
      super(name, args);
    }

    /**
     * Execute the AddTodoCompleted SubCommand. (Set the
     * outer class's field "completed" as true, to be assigned to the new Todo's completed field)
     * @throws InvalidTodoException
     */
    @Override
    public void execute() {completed = true;}

  }

  /**
   * Represents an AddTodoDue SubCommand.
   */
  protected class AddTodoDue extends AbstractSubCommand {
    public AddTodoDue(String name, List<String> args) {
      super(name, args);
    }
    /**
     * Execute the AddTodoDue SubCommand. (Convert the received date argument from a
     * raw String to the correct date format, to be assigned to the new Todo's due field)
     * @throws InvalidTodoException
     */
    @Override
    public void execute() throws Exception {
      due = CsvLineToTodoConverterUtils.convertDueDate(this.args.get(0));
    }

  }

  /**
   * Represents an AddTodoPriority SubCommand.
   */
  protected class AddTodoPriority extends AbstractSubCommand {

    public AddTodoPriority(String name, List<String> args) {
      super(name, args);
    }

    /**
     * Execute the AddTodoPriority SubCommand. (Convert the received priority argument from a
     * raw String to Integer, to be assigned to the new Todo's priority field)
     * @throws InvalidTodoException
     */
    @Override
    public void execute() throws Exception {
      priority = CsvLineToTodoConverterUtils.convertPriority(this.args.get(0));
    }
  }

  /**
   * Represents an AddTodoCategory SubCommand.
   */
  protected class AddTodoCategory extends AbstractSubCommand {

    public AddTodoCategory(String name, List<String> args) {
      super(name, args);
    }

    /**
     * Execute the AddTodoPriority SubCommand. (Convert the received category argument from a
     * list of Strings to one String, to be assigned to the new Todo's category field)
     * @throws InvalidTodoException
     */
    @Override
    public void execute() throws InvalidTodoException {
      String joinedCategory = String.join(" ", this.args);
      category = CsvLineToTodoConverterUtils.convertCategory(joinedCategory);
      // validate if category is null or 0-length
    }
  }














}
