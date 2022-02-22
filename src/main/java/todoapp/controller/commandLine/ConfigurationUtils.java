package todoapp.controller.commandLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A constant class that contains all configuration data.
 */
public class ConfigurationUtils {

  public static final String LOAD_CSV = "--csv-file";// main
  public static final String ADD_TODO = "--add-todo";// main
  public static final String ADD_TODO_TEXT = "--todo-text";  //sub
  public static final String ADD_TODO_COMPLETED = "--completed";
  public static final String ADD_TODO_DUE = "--due";
  public static final String ADD_TODO_PRIORITY = "--priority";
  public static final String ADD_TODO_CATEGORY = "--category";
  public static final String COMPLETE_TODO = "--complete-todo"; // main
  public static final String DISPLAY = "--display"; //main
  public static final String DISPLAY_SHOW_INCOMPLETE = "--show-incomplete";
  public static final String DISPLAY_SHOW_CATEGORY = "--show-category";
  public static final String DISPLAY_SORT_DATE = "--sort-by-date";
  public static final String DISPLAY_SORT_PRIORITY = "--sort-by-priority";
  public static final String UPDATE_CSV = "";     // not included in OPTION_NAMES set. Shouldn't be given from command line

  /**
   * User-defined set of option names that can be entered into command line
   */
  public static final Set<String> OPTION_NAMES = new HashSet<>(Arrays.asList(LOAD_CSV,
      ADD_TODO, ADD_TODO_TEXT, ADD_TODO_COMPLETED, ADD_TODO_DUE, ADD_TODO_PRIORITY,
      ADD_TODO_CATEGORY,
      COMPLETE_TODO, DISPLAY, DISPLAY_SHOW_INCOMPLETE, DISPLAY_SHOW_CATEGORY, DISPLAY_SORT_DATE,
      DISPLAY_SORT_PRIORITY));


  /**
   * Return an Option according to a name specified by command line input
   *
   * @param name - the Option's name
   * @return an Option object
   */
  public static Option makeOption(String name) throws InvalidCommandException {
    switch (name) {
      case ConfigurationUtils.LOAD_CSV:
        return new Option.Builder(ConfigurationUtils.LOAD_CSV, ConfigurationUtils.LOAD_CSV).setHasArg()
            .setMinNumArgs(1).setMaxNumArgs(1).build();
      case ConfigurationUtils.ADD_TODO:
        return new Option.Builder(ConfigurationUtils.ADD_TODO, ConfigurationUtils.ADD_TODO).build();
      case ConfigurationUtils.ADD_TODO_TEXT:
        return new Option.Builder(ConfigurationUtils.ADD_TODO, ConfigurationUtils.ADD_TODO_TEXT).setHasArg()
            .setMinNumArgs(1).build();
      case ConfigurationUtils.ADD_TODO_COMPLETED:
        return new Option.Builder(ConfigurationUtils.ADD_TODO, ConfigurationUtils.ADD_TODO_COMPLETED).build();
      case ConfigurationUtils.ADD_TODO_DUE:
        return new Option.Builder(ConfigurationUtils.ADD_TODO, ConfigurationUtils.ADD_TODO_DUE).setHasArg()
            .setMinNumArgs(1).setMaxNumArgs(1).build();
      case ConfigurationUtils.ADD_TODO_PRIORITY:
        return new Option.Builder(ConfigurationUtils.ADD_TODO, ConfigurationUtils.ADD_TODO_PRIORITY)
            .setHasArg().setMinNumArgs(1).setMaxNumArgs(1).build();
      case ConfigurationUtils.ADD_TODO_CATEGORY:
        return new Option.Builder(ConfigurationUtils.ADD_TODO, ConfigurationUtils.ADD_TODO_CATEGORY)
            .setHasArg().setMinNumArgs(1).build(); // Can have more than 1 word
      // 1 arg -> 1 id at a time. But can have multiple complete Options.
      case ConfigurationUtils.COMPLETE_TODO:
        return new Option.Builder(ConfigurationUtils.COMPLETE_TODO, ConfigurationUtils.COMPLETE_TODO)
            .setHasArg().setMinNumArgs(1).setMaxNumArgs(1).setMaxNumAppearance(10).build();
      case ConfigurationUtils.DISPLAY:
        return new Option.Builder(ConfigurationUtils.DISPLAY, ConfigurationUtils.DISPLAY).build();
      case ConfigurationUtils.DISPLAY_SHOW_INCOMPLETE:
        return new Option.Builder(ConfigurationUtils.DISPLAY, ConfigurationUtils.DISPLAY_SHOW_INCOMPLETE)
            .build();
      case ConfigurationUtils.DISPLAY_SHOW_CATEGORY:
        return new Option.Builder(ConfigurationUtils.DISPLAY, ConfigurationUtils.DISPLAY_SHOW_CATEGORY)
            .setHasArg().setMinNumArgs(1).build(); // Can have more than 1 word
      case ConfigurationUtils.DISPLAY_SORT_DATE:
        return new Option.Builder(ConfigurationUtils.DISPLAY, ConfigurationUtils.DISPLAY_SORT_DATE).build();
      case ConfigurationUtils.DISPLAY_SORT_PRIORITY:
        return new Option.Builder(ConfigurationUtils.DISPLAY, ConfigurationUtils.DISPLAY_SORT_PRIORITY)
            .build();
      default:
        throw new InvalidCommandException("Unknown command: " + name);
    }
  }

  /**
   * Set of required base option's names
   */
  public static final Set<String> REQUIRED_BASE_OPTION_NAME = new HashSet<>(Arrays.asList(LOAD_CSV));

  /**
   * Required sub command for each base command.
   */
  public static final Map<String, Set<String>> REQUIRED_SUBCOMMAND_NAMES_FOR_EACH_BASE = new HashMap<String, Set<String>>() {
    {
      put(LOAD_CSV, new HashSet<>());
      put(ADD_TODO, new HashSet<>(Arrays.asList(ADD_TODO, ADD_TODO_TEXT)));
      put(COMPLETE_TODO, new HashSet<>());
      put(DISPLAY, new HashSet<>(Arrays.asList(DISPLAY)));
      put(UPDATE_CSV, new HashSet<>());
    }
  };

  /**
   * Execution order of each baseCommand (i.e. group of commands)
   */
  public static final Map<String, Integer> BASE_COMMAND_ORDER = new HashMap<String, Integer>() {
    {
      put(LOAD_CSV, 1);
      put(ADD_TODO, 2);
      put(COMPLETE_TODO, 3);
      put(DISPLAY, 4);
      put(UPDATE_CSV, 5);
    }
  };

  /**
   * Default order of CSV headers. Assume that the CSV to be loaded will always have valid headers
   * in this order.
   */
  public static final Map<String, Integer> DEFAULT_HEADER_ORDER = new HashMap<String, Integer>() {
    {
      put("id", 0);
      put("text", 1);
      put("completed", 2);
      put("due", 3);
      put("priority", 4);
      put("category", 5);
    }
  };

  /**
   * Command line input example
   */
  public static final List<String[]> EXAMPLES = Arrays.asList(
      new String[]{LOAD_CSV, "todos.csv",
          ADD_TODO, ADD_TODO_TEXT, "Reply", "Email",
          ADD_TODO_PRIORITY, "1", ADD_TODO_CATEGORY, "work",
          COMPLETE_TODO, "1",
          COMPLETE_TODO, "2",
          DISPLAY, DISPLAY_SORT_DATE, DISPLAY_SHOW_INCOMPLETE, DISPLAY_SHOW_CATEGORY, "work"},
      new String[]{LOAD_CSV, "todos.csv", DISPLAY}
  );

  /**
   * Command line usage, grouped by Command groups.
   */
  public static final Map<String, List<String[]>> USAGE = new LinkedHashMap<String, List<String[]>>() {
    {

      List<String[]> groupList = new ArrayList<>();
      groupList.add(new String[]{LOAD_CSV + " <path/to/file>",
          "The CSV file containing the todos. This option is required."});
      put(LOAD_CSV, groupList);

      groupList = new ArrayList<>();
      groupList.add(new String[]{ADD_TODO,
          "Add a new todo. If this option is provided, then --todo-text must also be provided."});
      groupList.add(
          new String[]{ADD_TODO_TEXT + " <description of todo>", "A description of the todo."});
      groupList.add(new String[]{ADD_TODO_COMPLETED,
          "(Optional) Sets the completed status of a new todo to true."});
      groupList.add(new String[]{ADD_TODO_DUE + " <due date>",
          "(Optional) Sets the due date of a new todo. Due date should be in MM/dd/YYYY format."});
      groupList.add(new String[]{ADD_TODO_PRIORITY + " <1, 2, or 3>",
          "(Optional) Sets the priority of a new todo. The value can be 1, 2, or 3."});
      groupList.add(new String[]{ADD_TODO_CATEGORY + " <a category name>",
          "(Optional) Sets the category of a new todo."});
      put(ADD_TODO, groupList);

      groupList = new ArrayList<>();
      groupList.add(
          new String[]{COMPLETE_TODO + " <id>", "Mark the Todo with the provided ID as complete."});
      put(COMPLETE_TODO, groupList);

      groupList = new ArrayList<>();
      groupList
          .add(new String[]{DISPLAY + " <id>", "Mark the Todo with the provided ID as complete."});
      groupList
          .add(new String[]{DISPLAY + " <id>", "Mark the Todo with the provided ID as complete."});
      groupList.add(new String[]{DISPLAY_SHOW_INCOMPLETE,
          "(Optional) If --display is provided, only incomplete todos should be displayed."});
      groupList.add(new String[]{DISPLAY_SHOW_CATEGORY + " <category>",
          "(Optional) If --display is provided, only todos with the given category should be displayed."});
      groupList.add(new String[]{DISPLAY_SORT_DATE,
          "(Optional) If --display is provided, sort the list of by date order (ascending). Cannot be combined with --sort-by-priority."});
      groupList.add(new String[]{DISPLAY_SORT_PRIORITY,
          "(Optional) If --display is provided, sort the list of todos by priority (ascending). Cannot be combined with --sort-by-date."});
      put(DISPLAY, groupList);
    }
  };
}