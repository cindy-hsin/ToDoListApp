package todoapp.controller.commandLine;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class CommandLineParserTest {
  String[] args;
  Option opt;
  CommandLineParser clp;
  @Before
  public void setUp() throws Exception {
    args = new String[]{"--csv-file", "todos.csv"};
    opt = new Option.Builder(ConfigurationUtils.LOAD_CSV, ConfigurationUtils.LOAD_CSV).setHasArg().setMinNumArgs(1).setMaxNumArgs(1).build();
    clp = new CommandLineParser(args);
  }

  @Test
  public void parse() throws InvalidCommandException {
    Map<String, List<Option>> result = new HashMap<>();
    opt.setArgs(Arrays.asList("todos.csv"));
    List<Option> list = new ArrayList<Option>();
    list.add(opt);
    result.put(ConfigurationUtils.LOAD_CSV, list);

    assertEquals(result, clp.parse());

  }

  @Test
  public void parseAddToSameKeyMultipleTimes() throws InvalidCommandException {
  String[] args2 = new String[]{"--csv-file", "todos.csv", ConfigurationUtils.COMPLETE_TODO, "1", ConfigurationUtils.COMPLETE_TODO, "2"};
  CommandLineParser clp2 = new CommandLineParser(args2);
  Option opt1 = new Option.Builder(ConfigurationUtils.COMPLETE_TODO, ConfigurationUtils.COMPLETE_TODO).setHasArg().setMinNumArgs(1).setMaxNumArgs(1).setMaxNumAppearance(10).build();
  Option opt2 = new Option.Builder(ConfigurationUtils.COMPLETE_TODO, ConfigurationUtils.COMPLETE_TODO).setHasArg().setMinNumArgs(1).setMaxNumArgs(1).setMaxNumAppearance(10).build();

  Map<String, List<Option>> result = new HashMap<>();
  opt.setArgs(Arrays.asList("todos.csv"));
  opt1.setArgs(Arrays.asList("1"));
  opt2.setArgs(Arrays.asList("2"));
  result.put(ConfigurationUtils.LOAD_CSV,  Arrays.asList(opt));
  result.put(ConfigurationUtils.COMPLETE_TODO, Arrays.asList(opt1, opt2));
  assertEquals(result, clp2.parse());

  }

  @Test (expected = InvalidCommandException.class)
  public void parseMissRequiredBaseOption() throws InvalidCommandException {
    String[] args2 = new String[]{ConfigurationUtils.COMPLETE_TODO, "1",};
    CommandLineParser clp2 = new CommandLineParser(args2);

    clp2.parse();
  }
  @Test (expected = InvalidCommandException.class)
  public void invalidNumAppearance() throws InvalidCommandException {
    String[] args2 = new String[]{"--csv-file", "todos.csv", "--display", "--display"};
    CommandLineParser clp2 = new CommandLineParser(args2);
    clp2.parse();
  }

  @Test (expected = InvalidCommandException.class)
  public void unknownCommand() throws InvalidCommandException {
    String[] args2 = new String[]{"--csv", "todos.csv"};
    CommandLineParser clp2 = new CommandLineParser(args2);
    clp2.parse();
  }



}