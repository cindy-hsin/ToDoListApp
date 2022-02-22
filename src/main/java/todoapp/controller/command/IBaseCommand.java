package todoapp.controller.command;

import todoapp.controller.commandLine.InvalidCommandException;
import todoapp.controller.commandLine.Option;

public interface IBaseCommand{

  /**
   * Execute the base command.
   */
  void execute() throws Exception;

  /**
   * Create a Subcommand for each received Option from command line.
   */
  AbstractSubCommand createSubCommand(Option option) throws InvalidCommandException;

  /**
   * Create Subcommands
   * and put them into a subCommandList to wait for execution.
   */
  void populateSubCommandList() throws InvalidCommandException;

  /**
   * Check if required SubCommands are contained in
   * subCommandList.
   */
  void validateRequiredSubCommand() throws InvalidCommandException;



}
