package todoapp;

import java.util.List;
import java.util.Map;
import todoapp.controller.command.Invoker;
import todoapp.controller.commandLine.CommandLineParser;
import todoapp.controller.commandLine.Option;
import todoapp.view.ViewUtils;

public class Main {
  public static void main(String[] args) {
    try {
      CommandLineParser parser = new CommandLineParser(args);
      Map<String, List<Option>> parsedOptions = parser.parse();
      Invoker invoker = new Invoker(parsedOptions);
      invoker.execute();
    } catch (Exception e){
      System.out.println("Error: " + e.getMessage());
      ViewUtils.displayAllUsage();
      ViewUtils.displayExample();
    }
  }
}
