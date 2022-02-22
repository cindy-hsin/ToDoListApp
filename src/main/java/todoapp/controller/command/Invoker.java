package todoapp.controller.command;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import todoapp.controller.commandLine.ConfigurationUtils;
import todoapp.controller.commandLine.InvalidCommandException;
import todoapp.controller.commandLine.Option;
import todoapp.model.todoData.ITodoList;
import todoapp.model.todoData.TodoList;
import todoapp.view.ViewUtils;


/**
 * Instantiate BaseCommands and execute them in pre-defined order.
 */
public class Invoker {
  private static final Map<String, Integer> BASE_COMMAND_ORDER = ConfigurationUtils.BASE_COMMAND_ORDER;
  private static final Map<String, Set<String>> REQUIRED_SUB_NAMES_FOR_EACH_BASE =
      ConfigurationUtils.REQUIRED_SUBCOMMAND_NAMES_FOR_EACH_BASE;

  private ITodoList todoList;
  private Map<String, List<Option>> receivedOptionsInGroup;
  protected List<AbstractBaseCommand> baseCommandList;  // protected, for testing
  private String csvPath;

  /**
   * Constructor of an Invoker
   * @param receivedOptionsInGroup - the received Options grouped by baseOption's name. Received from CommandLineParser's parse() method.
   */
  public Invoker(Map<String, List<Option>> receivedOptionsInGroup) {
    this.todoList = TodoList.createTodoList();   // Instantiate TodoList with Singleton Pattern
    this.receivedOptionsInGroup = receivedOptionsInGroup;
    this.baseCommandList = new ArrayList<>();
    this.csvPath = null;
  }


  /**
   * Execute BaseCommands by pre-defined order.
   * @throws Exception - if LoadCsv BaseCommand's execution failed
   */
  public void execute() throws Exception {
    this.populateBaseCommandList();            // Instantiate Base Commands & put into a list
    Collections.sort(this.baseCommandList);   // Sort BaseCommands by their order
    for (AbstractBaseCommand command: this.baseCommandList) {
      try{
        command.execute();
      }
      catch(Exception e){   // When LOAD_CSV failed-> Stop the program
        if (command.name.equals(ConfigurationUtils.LOAD_CSV)){
          throw new IllegalArgumentException("Loading Csv failed: " + e.getMessage());
        }
        else {  // When other base command failed -> Keep the program run (execute next base command)
          System.out.println("Command " + command.name + " failed: " + e.getMessage());
          ViewUtils.displayUsage(command.name);
        }
      }
    }
  }



  /**
   * Add newly created BaseCommand to baseCommandList, to wait for execution.
   * @throws InvalidCommandException - if there's unknown received Option
   */
  private void populateBaseCommandList() throws InvalidCommandException {
    for (String group: receivedOptionsInGroup.keySet()) {
      AbstractBaseCommand command = this.createBaseCommand(group);
      baseCommandList.add(command);

      // UpdateCsv command needs to be added manually.
      if (group.equals(ConfigurationUtils.LOAD_CSV)) {
        this.csvPath = command.getReceivedOptions().get(0).getArgs().get(0);
        AbstractBaseCommand update = this.createBaseCommand(ConfigurationUtils.UPDATE_CSV);
        baseCommandList.add(update);
      }
    }

  }

  /**
   * Create a BaseCommand for one group of options. (Factory Pattern)
   * @param group - the baseOption name/the group name
   * @return - the corresponding BaseCommand object
   * @throws InvalidCommandException
   */
  private AbstractBaseCommand createBaseCommand(String group) throws InvalidCommandException {
    switch(group) {
      case ConfigurationUtils.LOAD_CSV:
        return new LoadCsv(group, BASE_COMMAND_ORDER.get(group), receivedOptionsInGroup.get(group), this.todoList, REQUIRED_SUB_NAMES_FOR_EACH_BASE.get(group));
      case ConfigurationUtils.ADD_TODO:
        return new AddTodo(group, BASE_COMMAND_ORDER.get(group), receivedOptionsInGroup.get(group), this.todoList, REQUIRED_SUB_NAMES_FOR_EACH_BASE.get(group));

      case ConfigurationUtils.COMPLETE_TODO:
        return new CompleteTodo(group, BASE_COMMAND_ORDER.get(group), receivedOptionsInGroup.get(group), this.todoList, REQUIRED_SUB_NAMES_FOR_EACH_BASE.get(group));

      case ConfigurationUtils.DISPLAY:
        return new DisplayTodo(group, BASE_COMMAND_ORDER.get(group), receivedOptionsInGroup.get(group), this.todoList, REQUIRED_SUB_NAMES_FOR_EACH_BASE.get(group));

      case ConfigurationUtils.UPDATE_CSV:
        return new UpdateCsv(group, BASE_COMMAND_ORDER.get(group), receivedOptionsInGroup.get(group), this.todoList, REQUIRED_SUB_NAMES_FOR_EACH_BASE.get(group), this.csvPath);
      default:
        throw new InvalidCommandException("Unknown command: " + group);


    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Invoker invoker = (Invoker) o;
    return Objects.equals(todoList, invoker.todoList) && Objects
        .equals(receivedOptionsInGroup, invoker.receivedOptionsInGroup) && Objects
        .equals(baseCommandList, invoker.baseCommandList) && Objects
        .equals(csvPath, invoker.csvPath);
  }

  @Override
  public int hashCode() {
    return Objects.hash(todoList, receivedOptionsInGroup, baseCommandList, csvPath);
  }

  @Override
  public String toString() {
    return "Invoker{" +
        "todoList=" + todoList +
        ", receivedOptionsInGroup=" + receivedOptionsInGroup +
        ", baseCommandList=" + baseCommandList +
        ", csvPath='" + csvPath + '\'' +
        '}';
  }
}
