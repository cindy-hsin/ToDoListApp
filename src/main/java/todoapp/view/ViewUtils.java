package todoapp.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import todoapp.model.fileIO.TodoToCsvLineConverterUtils;
import todoapp.model.todoData.Todo;
import todoapp.controller.commandLine.ConfigurationUtils;

/**
 * Class responsible for printing out messages to terminal
 */
public class ViewUtils {

  /**
   * Displays a formatted Todo list to the terminal
   * @param list the list to be displayed on the terminal
   */
  public static void viewList(List<Todo> list){
    if(list.size() > 0){
     List<String[]> formattedTodos = formatTodos(list);
     for(String[] row: formattedTodos){
       System.out.format("%-10s%-50s%-15s%-15s%-10s%-30s\n",
           row[ConfigurationUtils.DEFAULT_HEADER_ORDER.get("id")],
           row[ConfigurationUtils.DEFAULT_HEADER_ORDER.get("text")],
           row[ConfigurationUtils.DEFAULT_HEADER_ORDER.get("completed")],
           row[ConfigurationUtils.DEFAULT_HEADER_ORDER.get("due")],
           row[ConfigurationUtils.DEFAULT_HEADER_ORDER.get("priority")],
           row[ConfigurationUtils.DEFAULT_HEADER_ORDER.get("category")]);
     }
    }else{
      System.out.println("There are no todos to be displayed.");
    }
  }

  /**
   * Helper method to return a list of Todos organized as a dataset
   * @param list the list of Todos to be converted into a dataset
   * @return a list of Strings where each field in a Todo is represented as a string
   */
  private static List<String[]> formatTodos(List<Todo> list){
    Map<String, Integer> headers = ConfigurationUtils.DEFAULT_HEADER_ORDER;
    List<String[]> result = new ArrayList<>();
    String[] fields = new String[headers.size()];
    for(String header: headers.keySet()){
      fields[headers.get(header)] = header;
    }
    result.add(fields);
    for(Todo todo: list){
      List<String> todoLine = TodoToCsvLineConverterUtils.processTodo(todo);
      String[] todoArray = new String[todoLine.size()];
      todoArray = todoLine.toArray(todoArray);
      result.add(todoArray);
    }
    return result;
  }

  /**
   * Displays an example of correct command line input
   */
  public static void displayExample(){
    List<String[]>examples = ConfigurationUtils.EXAMPLES;
    System.out.println("Examples:");
    for (String[] line: examples){
      System.out.println(String.join(" ", line));
    }
    System.out.println(System.lineSeparator());
  }

  /**
   * Displays explanation of command line usage for a specific group of base commands
   * @param baseCommandName the base command for which command line usage is to be displayed
   */
  public static void displayUsage(String baseCommandName){
    System.out.println("  Group Usage of " + baseCommandName + ":");
    List<String[]> usage  = ConfigurationUtils.USAGE.get(baseCommandName);
    for (String[] line: usage){
      System.out.println("    " + String.join(" ", line));
    }
    // System.out.println(System.lineSeparator());
  }


  /**
   * Displays explanation of command line usage for all commands
   */
  public static void displayAllUsage(){
    System.out.println("Usage of All Commands:");
    Map<String, List<String[]>> allUsage  = ConfigurationUtils.USAGE;
    for (String group: allUsage.keySet()) {
      displayUsage(group);
    }
  }

}
