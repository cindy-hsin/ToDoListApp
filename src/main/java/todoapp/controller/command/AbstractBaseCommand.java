package todoapp.controller.command;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import todoapp.controller.commandLine.InvalidCommandException;
import todoapp.controller.commandLine.Option;
import todoapp.model.todoData.ITodoList;


/**
 * Abstract class that represents a BaseCommand.
 */
public abstract class AbstractBaseCommand extends AbstractCommand implements IBaseCommand,Comparable<AbstractBaseCommand>  {
    private Integer order;
    protected List<AbstractSubCommand> subCommandList;
    protected List<Option> receivedOptions;
    protected ITodoList todoList;
    protected Set<String> requiredSubCommandNames;

  /**
   * Constructor of an AbstractBaseCommand
   * @param name - the base command's name/ the command group's name
   * @param order - the execution order of the base command/group
   * @param receivedOptions the List of Options related to this base command/group, received from command line parser.
   * @param todoList - the Singleton TodoList object
   * @param requiredSubCommandNames - the set of required SubCommand's name of this BaseCommand. Defined in Configuration class.
   */
  public AbstractBaseCommand(String name, int order, List<Option> receivedOptions, ITodoList todoList,
      Set<String> requiredSubCommandNames) {
    super(name);
    this.order = order;
    this.receivedOptions = receivedOptions;
    this.todoList = todoList;
    this.subCommandList = new ArrayList<>();    // to be populated
    this.requiredSubCommandNames = requiredSubCommandNames;
  }

  /**
   * Validate if required sub option is given from command line.
   * @throws InvalidCommandException - if required sub option is not given
   */
  @Override
  public void validateRequiredSubCommand() throws InvalidCommandException {
    // Convert subCommandList to a set of command's name
    Set<String> receivedSubCommandNames = new HashSet<>();
    for(AbstractSubCommand sc : this.subCommandList){
      receivedSubCommandNames.add(sc.name);
    }
    Set<String> tempRequiredSubCommandNames = new HashSet<>(this.requiredSubCommandNames);
    tempRequiredSubCommandNames.removeAll(receivedSubCommandNames);
    for(String name: tempRequiredSubCommandNames){
      throw new InvalidCommandException("To execute related options, please add required option: " + name);
    }
  }

  /**
   * Gets the receivedOptions
   * @return receivedOptions
   */
  public List<Option> getReceivedOptions() {
    return this.receivedOptions;
  }

  /**
   * Compare two BaseCommand by their order.
   * @param o - the other BaseCommand
   * @return - 0 if they have same order, -1 if this order is less than that order, otherwise return 1.
   */
  @Override
  public int compareTo(AbstractBaseCommand o) {
    if (this.equals(o) || this.order == o.order)
      return 0;
    if (this.order < o.order)
      return -1;
    return 1;
  }

  @Override
  public boolean equals(Object o) {
    if (!super.equals(o)) {
      return false;
    }
    AbstractBaseCommand that = (AbstractBaseCommand) o;
    return Objects.equals(order, that.order) && Objects
        .equals(subCommandList, that.subCommandList) && Objects
        .equals(getReceivedOptions(), that.getReceivedOptions()) && Objects
        .equals(todoList, that.todoList) && Objects
        .equals(requiredSubCommandNames, that.requiredSubCommandNames);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), order, subCommandList, getReceivedOptions(), todoList,
        requiredSubCommandNames);
  }
}
