package todoapp.controller.command;

public interface ISubCommand {

  /**
   * Execute the subcommand
   */
  void execute() throws Exception;
}
