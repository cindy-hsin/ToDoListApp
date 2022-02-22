package todoapp.controller.commandLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a CommandLineParser
 */
public class CommandLineParser {
  private String[] args;
  private int index = 0;

  public CommandLineParser(String[] args) {
    this.args = args;
  }

  /**
   * Parse the command line
   * @return the parsed result in a map. Each entry's key is a command group's name (baseOptionName),
   * and value is a list of the group's related Options.
   *
   * @throws InvalidCommandException - if there's unknown command
   */
  public Map<String, List<Option>> parse() throws InvalidCommandException {
    Map <String, List<Option>> parsedOptions = new HashMap<>();

    while (this.index < this.args.length) {
      if (!ConfigurationUtils.OPTION_NAMES.contains(this.args[this.index])) {
        throw new InvalidCommandException("Unknown Command: " + this.args[this.index]);
      }
      Option option = ConfigurationUtils.makeOption(this.args[this.index++]);
      if (option.isHasArg()) {
        List<String> arguments = this.collectArguments();
        // Store the List to option's args field  (option needs to validate number of args)
        option.setArgs(arguments);
      }
      // Put option into map;
      if (!parsedOptions.containsKey(option.getBaseOptionName())) {
        parsedOptions.put(option.getBaseOptionName(), new ArrayList<>());
      }
      parsedOptions.get(option.getBaseOptionName()).add(option);
    }
    this.checkRequiredBaseOptionExist(parsedOptions);
    this.checkNumAppearance(parsedOptions);

    return parsedOptions;
  }


  /**
   * Helper method to collect arguments into a List,
   * until reaching a recognized option name, or end of args.
   * @return the collected arguments
   */
  private List<String> collectArguments() {
    List<String> arguments = new ArrayList<>();
    while (this.index < this.args.length && !ConfigurationUtils.OPTION_NAMES.contains(this.args[this.index])) {
      arguments.add(this.args[this.index++]);
    }
    return arguments;
  }


  /**
   * Check if required base option is given.
   * @param parsedOptions - the parsed result in a map
   * @throws InvalidCommandException
   */
  private void checkRequiredBaseOptionExist(Map<String, List<Option>> parsedOptions)
      throws InvalidCommandException {
    for (String required: ConfigurationUtils.REQUIRED_BASE_OPTION_NAME) {
      if (!parsedOptions.containsKey(required)) {
        throw new InvalidCommandException("Required option: " + required + " is not given.");
      }
    }
  }

  /**
   * Check if any option's number of appearance in command line exceeds its upperbound.
   * @param parsedOptions - the parsed result in a map
   * @throws InvalidCommandException
   */
  private void checkNumAppearance(Map<String, List<Option>> parsedOptions)
      throws InvalidCommandException {
    for (String keyword: parsedOptions.keySet()) {
      List<Option> groupOptions = parsedOptions.get(keyword);     // [addToDoOption, addTextOption, addDueOption]
      Map<String, List<Option>> optionsByName = groupOptions.stream().collect(Collectors.groupingBy(Option::getName));  // grouped by option name
      for (String name: optionsByName.keySet()) {
        Integer maxNumAppearance = optionsByName.get(name).get(0).getMaxNumAppearance();
        if (maxNumAppearance != null && optionsByName.get(name).size() > maxNumAppearance) {
          throw new InvalidCommandException("Option: " + name + " should not be entered more than " + maxNumAppearance + " times.");
        }
      }
    }
  }

}




