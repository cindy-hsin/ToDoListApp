package todoapp.controller.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import todoapp.controller.commandLine.ConfigurationUtils;
import todoapp.controller.commandLine.InvalidCommandException;
import todoapp.controller.commandLine.Option;
import todoapp.model.fileIO.CsvWriter;
import todoapp.model.fileIO.TodoToCsvLineConverterUtils;
import todoapp.model.todoData.ITodoList;
import todoapp.model.todoData.Todo;

/**
 * Concrete command class, that represents the UpdateTodo BaseCommand.
 */
public class UpdateCsv extends AbstractBaseCommand{

  private String csvPath;

  /**
   * Constructor of a UpdateCsv BaseCommand.
   * @param name - the name of this BaseCommand.
   * @param order - the order of this BaseCommand. Pre-defined in Configuration class.
   * @param receivedOptions - the List of Options related to this base command/group, received from command line parser.
   * @param todoList - the Singleton TodoList object
   * @param requiredSubCommandNames - the set of required SubCommand's name of this BaseCommand. Defined in Configuration class.
   * @param csvPath - the csv file's path, given from command line (i.e. the argument of "--csv-file" Option).
   */
  public UpdateCsv(String name, int order, List<Option> receivedOptions, ITodoList todoList,
      Set<String> requiredSubCommandNames, String csvPath) {
    super(name, order, receivedOptions, todoList, requiredSubCommandNames);
    this.csvPath = csvPath;
  }

  /**
   * Execute the UploadCsv command: Convert default headers and each Todo in the TodoList to a List of String,
   * then write to the Csv file.
   * @throws Exception
   */
  @Override
  public void execute() throws Exception {
    CsvWriter writer = new CsvWriter();
    List<List<String>> entries = new ArrayList<>();
    // Add the header as first line
    entries.add(this.processHeaders());
    Todo todo;
    for (int i = 0; i < todoList.size(); i ++) {
      todo = todoList.getTodo(i);
      List<String> todoLine = TodoToCsvLineConverterUtils.processTodo(todo);
      entries.add(todoLine);
    }
    writer.write(this.csvPath, entries);

  }

  /**
   * A helper method to convert the default headers from a map to a List of String,
   * that could be written to CSV.
   * @return
   */
  private List<String> processHeaders() {
    String[] line = new String[ConfigurationUtils.DEFAULT_HEADER_ORDER.size()];
    for (String key : ConfigurationUtils.DEFAULT_HEADER_ORDER.keySet()) {
      line[ConfigurationUtils.DEFAULT_HEADER_ORDER.get(key)] = key;
    }
    return Arrays.asList(line);
  }


  /**
   * Populate the SubcommandList. Since UpdateCsv doesn't have SubCommand,
   * the method is never called and therefore is a void method.
   * @throws InvalidCommandException
   */
  @Override
  public void populateSubCommandList() {return;}

  /**
   * Create a SubCommand for each received Option. Since UpdateCsv doesn't have SubCommand,
   * the method is never called and therefore returns null.
   * @param option - the received Option
   * @return null
   * @throws InvalidCommandException
   */
  @Override
  public AbstractSubCommand createSubCommand(Option option) throws InvalidCommandException {
    return null;
  }


  @Override
  public boolean equals(Object o) {
    if (!super.equals(o)) {
      return false;
    }
    UpdateCsv updateCsv = (UpdateCsv) o;
    return Objects.equals(csvPath, updateCsv.csvPath);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), csvPath);
  }

  @Override
  public String toString() {
    return "UpdateCsv{" +
        "subCommandList=" + subCommandList +
        ", receivedOptions=" + receivedOptions +
        ", todoList=" + todoList +
        ", requiredSubCommandNames=" + requiredSubCommandNames +
        ", name='" + name + '\'' +
        ", csvPath='" + csvPath + '\'' +
        '}';
  }
}
