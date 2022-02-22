package todoapp.controller.command;

import java.util.List;
import java.util.Set;
import todoapp.controller.commandLine.ConfigurationUtils;
import todoapp.controller.commandLine.InvalidCommandException;
import todoapp.controller.commandLine.Option;
import todoapp.model.fileIO.InvalidTodoException;
import todoapp.model.todoData.ITodoList;

/**
 * Concrete command class, that represents the CompleteTodo BaseCommand.
 */
public class CompleteTodo extends AbstractBaseCommand{

  /**
   * Constructor of a CompleteTodo BaseCommand.
   * @param name - the name of this BaseCommand. "--complete-todo"
   * @param order - the order of this BaseCommand. Pre-defined in Configuration class.
   * @param receivedOptions - the List of Options related to this base command/group, received from command line parser.
   * @param todoList - the Singleton TodoList object
   * @param requiredSubCommandNames - the set of required SubCommand's name of this BaseCommand. Defined in Configuration class.
   */
  public CompleteTodo(String name, int order, List<Option> receivedOptions, ITodoList todoList,
      Set<String> requiredSubCommandNames) {
    super(name, order, receivedOptions, todoList, requiredSubCommandNames);
  }

  /**
   * Execute the CompleteTodo's received SubCommands: execute each CompleteOneTodo subcommand corresponding
   * to each received Option from command line. (CompleteTodo group can receive multiple
   * "--complete-todo" Options at on run.)
   * @throws InvalidCommandException
   */
  @Override
  public void execute() throws InvalidCommandException {
    this.populateSubCommandList();
    super.validateRequiredSubCommand();
    for (AbstractSubCommand sc: this.subCommandList) {
      try {
        sc.execute();
      } catch (Exception e){
        System.out.println("Can't complete todo of id: " + sc.args.get(0) +
            " " + e.getMessage());
        // Keep program run, allow other "--complete" commands execute.
      }
    }
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
   * Create a SubCommand for each received Option. Each Option can have different argument(i.e. Todo's id).
   * @param option - a received Option
   * @return the corresponding SubCommand object.
   * @throws InvalidCommandException - if there's Unknown Option received.
   */
  @Override
  public AbstractSubCommand createSubCommand(Option option) throws InvalidCommandException {
    switch(option.getName()) {
      case ConfigurationUtils.COMPLETE_TODO:
        return new CompleteOneTodo(option.getName(), option.getArgs());
      default:
        throw new InvalidCommandException("Unknown command: " + option.getName());
    }
  }

  @Override
  public String toString() {
    return "CompleteTodo{" +
        "subCommandList=" + subCommandList +
        ", receivedOptions=" + receivedOptions +
        ", todoList=" + todoList +
        ", requiredSubCommandNames=" + requiredSubCommandNames +
        ", name='" + name + '\'' +
        '}';
  }

  /**
   * Represents a SubCommand that completes one Todo of the given id.
   */
  protected class CompleteOneTodo extends AbstractSubCommand {
    public CompleteOneTodo(String name, List<String> args) {
      super(name, args);
    }

    /**
     * Convert String arg to Integer id, and set the Todo with that id as completed
     */
    @Override
    public void execute() throws Exception {
      try {
        todoList.getTodo(convertIndex(args.get(0))).setCompleted();
      }catch (IndexOutOfBoundsException e){
        throw new InvalidTodoException("Cannot find todo of id=" + args.get(0));
      }
    }


    /**
     * A helper method to convert string to integer for id
     * @param index the index given as a String
     * @return the index as an Integer
     * @throws Exception if the string could not be process to the integer
     */
    private Integer convertIndex(String index) throws Exception {
      try{
        return Integer.parseInt(index) - 1;
      }catch(NumberFormatException e){
        throw new NumberFormatException("Index of todo should be an integer.");
      }
    }
  }





}
