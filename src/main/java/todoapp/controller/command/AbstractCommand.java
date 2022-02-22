package todoapp.controller.command;

import java.util.Objects;

/**
 * Abstract class that represents a Command.
 */
public abstract class AbstractCommand {
  protected String name;

  /**
   * Constructor of an AbstractCommand
   * @param name - the command's name.
   */
  public AbstractCommand(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AbstractCommand that = (AbstractCommand) o;
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
