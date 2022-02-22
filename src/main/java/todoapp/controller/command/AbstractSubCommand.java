package todoapp.controller.command;

import java.util.List;
import java.util.Objects;

/**
 * Abstract class that represents a SubCommand.
 */
public abstract class AbstractSubCommand extends AbstractCommand implements ISubCommand{
  protected List<String> args;

  /**
   * Constructor of an AbstractSubCommand
   * @param name- the SubCommand's name.
   * @param args - the SubCommand's argument, received from command line
   */
  public AbstractSubCommand(String name, List<String> args) {
    super(name);
    this.args = args;
  }

  @Override
  public boolean equals(Object o) {
    if (!super.equals(o)) {
      return false;
    }
    AbstractSubCommand that = (AbstractSubCommand) o;
    return Objects.equals(args, that.args);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), args);
  }
}
