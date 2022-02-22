package todoapp.controller.command;

import java.util.List;
import java.util.Set;

import todoapp.controller.commandLine.InvalidCommandException;
import todoapp.controller.commandLine.Option;
import todoapp.model.fileIO.CsvLineToTodoConverterUtils;
import todoapp.model.fileIO.CsvReader;
import todoapp.model.todoData.ITodoList;

/**
 * Concrete command class, that represents the LoadCsv BaseCommand.
 * (LoadCsv BaseCommand's operation: Read in Csv file, convert contents into Todos and store in TodoList.)
 */
public class LoadCsv extends AbstractBaseCommand{

  public LoadCsv(String name, int order, List<Option> receivedOptions, ITodoList todoList, Set<String> requiredSubCommandNames) {
    super(name, order, receivedOptions, todoList, requiredSubCommandNames);
  }

  /**
   * Execute the LoadCsv command.
   * @throws Exception - if the current TodoList is null or not empty.
   */
  @Override
  public void execute() throws Exception {
    if (this.todoList == null) {
      throw new NullPointerException("Null TodoList");
    }
    if (this.todoList.size() != 0) {
      throw new IllegalArgumentException("System should begin with an empty TodoList");
    }
    String path = this.receivedOptions.get(0).getArgs().get(0);

     // Read, convert each line to Todos, store in todoList.
     CsvReader csvReader = new CsvReader();
     csvReader.readParse(path);
     List<List<String>> entries = csvReader.getEntries();  // List<List<String>>

    for (List<String> entry: entries) {
      todoList.addTodo(CsvLineToTodoConverterUtils.processTodo(entry));
      // entry: List<String>: ["1", "Finish HW9" , "false"...]
    }
  }

  /**
   * Populate the SubcommandList. Since LoadCsv doesn't have SubCommand,
   * the method is never called and therefore is a void method.
   * @throws InvalidCommandException
   */
  @Override
  public void populateSubCommandList() throws InvalidCommandException {
    return;
  }

  /**
   * Create a SubCommand for each received Option. Since LoadCsv doesn't have SubCommand,
   * the method is never called and therefore returns null.
   * @param option - the received Option
   * @return null
   * @throws InvalidCommandException
   */
  @Override
  public AbstractSubCommand createSubCommand(Option option) throws InvalidCommandException {
    return null;
  }

  /**
   * Gets the TodoList.
   * @return the TodoList.
   */
  public ITodoList getTodoList() {
    return this.todoList;
  }

  @Override
  public String toString() {
    return "LoadCsv{" +
        "subCommandList=" + subCommandList +
        ", receivedOptions=" + receivedOptions +
        ", todoList=" + todoList +
        ", requiredSubCommandNames=" + requiredSubCommandNames +
        ", name='" + name + '\'' +
        '}';
  }
}
