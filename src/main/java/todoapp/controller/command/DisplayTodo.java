package todoapp.controller.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import todoapp.controller.commandLine.ConfigurationUtils;
import todoapp.controller.commandLine.InvalidCommandException;
import todoapp.controller.commandLine.Option;
import todoapp.model.todoData.ITodoList;
import todoapp.model.todoData.Todo;
import todoapp.model.todoData.TodoCategoryIterator;
import todoapp.model.todoData.TodoDateComparator;
import todoapp.model.todoData.TodoIncompleteIterator;
import todoapp.model.todoData.TodoPriorityComparator;
import todoapp.view.ViewUtils;

/**
 * Concrete command class, that represents the DisplayTodo BaseCommand.
 */
public class DisplayTodo extends AbstractBaseCommand{
  // Global list to be modified by nested class's execute method.
  protected List<Todo> resultList;

  /**
   * Constructor of a DisplayTodo BaseCommand.
   * @param name - the name of this BaseCommand. "--display"
   * @param order - the order of this BaseCommand. Pre-defined in Configuration class.
   * @param receivedOptions - the List of Options related to this base command/group, received from command line parser.
   * @param todoList - the Singleton TodoList object
   * @param requiredSubCommandNames - the set of required SubCommand's name of this BaseCommand. Defined in Configuration class.
   */
  public DisplayTodo(String name, int order, List<Option> receivedOptions, ITodoList todoList,
      Set<String> requiredSubCommandNames) {
    super(name, order, receivedOptions, todoList, requiredSubCommandNames);
  }

  /**
   * Execute the AddTodo's received SubCommands:
   * Sort/Filter the current original TodoList by executing each Subcommand corresponding to each received Option,
   * then print out the Todolist.
   * If no sorting/filtering Option is received, print out the original Todolist.
   * @throws Exception
   */
  @Override
  public void execute() throws Exception {
    this.populateSubCommandList();
    super.validateRequiredSubCommand();
    this.validateConflictSubCommand();
    // Clone the original list
    this.resultList = new ArrayList<Todo>(todoList.getTodoList());
    // Invoke subcommand to filter/sort the list
    for (AbstractSubCommand sc: this.subCommandList) {
      sc.execute();
    }
    ViewUtils.viewList(this.resultList);
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
   * Validates that --sort-by-date Option and --sort-by-priority Option
   * is not given at the same time.
   * @throws InvalidCommandException
   */
  private void validateConflictSubCommand() throws InvalidCommandException {
    boolean hasSortDate = false;
    boolean hasSortPriority = false;
    for (AbstractSubCommand sc: this.subCommandList) {
      if (sc.name.equals(ConfigurationUtils.DISPLAY_SORT_DATE)) {
        hasSortDate = true;
      } else if (sc.name.equals(ConfigurationUtils.DISPLAY_SORT_PRIORITY)) {
        hasSortPriority = true;
      }
    }
    if (hasSortDate && hasSortPriority) {
      throw new InvalidCommandException("Cannot execute Sort by date and Sort by priority in one run.");
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
    switch(option.getName()){
      case ConfigurationUtils.DISPLAY: return new Display(option.getName(), option.getArgs());
      case ConfigurationUtils.DISPLAY_SHOW_INCOMPLETE:return new ShowIncomplete(option.getName(), option.getArgs());
      case ConfigurationUtils.DISPLAY_SHOW_CATEGORY: return new ShowCategory(option.getName(), option.getArgs());
      case ConfigurationUtils.DISPLAY_SORT_DATE: return new SortByDate(option.getName(), option.getArgs());
      case ConfigurationUtils.DISPLAY_SORT_PRIORITY: return new SortByPriority(option.getName(), option.getArgs());
      default:
        throw new InvalidCommandException("Unknown command: " + option.getName());
    }
  }

  @Override
  public boolean equals(Object o) {
    if (!super.equals(o)) {
      return false;
    }
    DisplayTodo that = (DisplayTodo) o;
    return Objects.equals(resultList, that.resultList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), resultList);
  }


  @Override
  public String toString() {
    return "DisplayTodo{" +
        "subCommandList=" + subCommandList +
        ", receivedOptions=" + receivedOptions +
        ", todoList=" + todoList +
        ", requiredSubCommandNames=" + requiredSubCommandNames +
        ", name='" + name + '\'' +
        ", resultList=" + resultList +
        '}';
  }

  protected class Display extends AbstractSubCommand {
    public Display(String name, List<String> args) {
      super(name, args);
    }
    @Override
    public void execute(){
    }
  }
  /**
   * Represents a ShowIncomplete SubCommand.
   */
  private class ShowIncomplete extends AbstractSubCommand {
    public ShowIncomplete(String name, List<String> args) {
      super(name, args);
    }

    /**
     * Iterate through the TodoList, store incomplete Todos in filteredList and
     * re-assign to the resultList in outer class.
     */
    @Override
    public void execute() {
      List<Todo> filteredList = new ArrayList<>();
      Iterator<Todo> it = new TodoIncompleteIterator(resultList);
      while(it.hasNext()){
        Todo todo = it.next();
        filteredList.add(todo);
      }
      resultList = filteredList;
    }
  }
  /**
   * Represents a ShowCategory SubCommand.
   */
  private class ShowCategory extends AbstractSubCommand {
    public ShowCategory(String name, List<String> args) {
      super(name, args);
    }
    /**
     * Iterate through the TodoList, store Todos of the given category in filteredList and
     * re-assign to the resultList in outer class.
     */
    @Override
    public void execute() {
      List<Todo> filteredList = new ArrayList<>();
      Iterator<Todo> it = new TodoCategoryIterator(resultList, args.get(0));
      while(it.hasNext()){
        Todo todo = it.next();
        filteredList.add(todo);
      }
      resultList = filteredList;
    }
  }

  /**
   * Represents a SortByDate SubCommand.
   */
  private class SortByDate extends AbstractSubCommand {
    public SortByDate(String name, List<String> args) {
      super(name, args);
    }

    /**
     * Sort the outer class's resultList (i.e. the TodoList to be displayed) by date.
     */
    @Override
    public void execute() {
      Collections.sort(resultList, new TodoDateComparator());
    }
  }

  /**
   * Represents a SortByPriority SubCommand.
   */
  private class SortByPriority extends AbstractSubCommand {
    public SortByPriority(String name, List<String> args) {
      super(name, args);
    }

    /**
     * Sort the outer class's resultList (i.e. the TodoList to be displayed) by priority.
     */
    @Override
    public void execute() {
      Collections.sort(resultList, new TodoPriorityComparator());
    }
  }

}
