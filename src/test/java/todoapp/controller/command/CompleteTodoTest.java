package todoapp.controller.command;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import todoapp.controller.commandLine.ConfigurationUtils;
import todoapp.controller.commandLine.InvalidCommandException;
import todoapp.controller.commandLine.Option;
import todoapp.model.todoData.ITodoList;
import todoapp.model.todoData.Todo;
import todoapp.model.todoData.TodoList;

public class CompleteTodoTest {

  CompleteTodo completeTodoCommand;

  List<Option> receivedOptions;
  Set<String> requiredSubCommandNames;
  Option complete1, complete2;
  Todo todo1, todo2;
  ITodoList todoList;

  @Before
  public void setUp() throws Exception {
    complete1 =  ConfigurationUtils.makeOption(ConfigurationUtils.COMPLETE_TODO);
    complete2 = ConfigurationUtils.makeOption(ConfigurationUtils.COMPLETE_TODO);
    complete1.setArgs(Arrays.asList("1"));
    complete2.setArgs(Arrays.asList("2"));
    receivedOptions = new ArrayList<>(Arrays.asList(complete1, complete2));
    requiredSubCommandNames = ConfigurationUtils.REQUIRED_SUBCOMMAND_NAMES_FOR_EACH_BASE.get(
        ConfigurationUtils.COMPLETE_TODO);
    todo1 = new Todo(1,"Finish HW9",false, null, 1,"School");
    todo2 = new Todo(2,"Clean",false, null, 2,"House");

    todoList = TodoList.createTodoList();
    todoList.addTodo(todo1);
    todoList.addTodo(todo2);
  }

  @Test
  public void createSubCommand() throws InvalidCommandException {
    completeTodoCommand = new CompleteTodo(ConfigurationUtils.COMPLETE_TODO, 3, receivedOptions, todoList, requiredSubCommandNames);
    AbstractSubCommand completeOneTodoCommand = completeTodoCommand.createSubCommand(complete1);
    assertEquals(ConfigurationUtils.COMPLETE_TODO, completeOneTodoCommand.name);
    assertEquals(Arrays.asList("1"), completeOneTodoCommand.args);

  }

  @Test
  public void populateSubCommandList() throws InvalidCommandException {
    completeTodoCommand = new CompleteTodo(ConfigurationUtils.COMPLETE_TODO, 3, receivedOptions, todoList, requiredSubCommandNames);
    List<AbstractSubCommand> list = Arrays.asList
        (completeTodoCommand.createSubCommand(complete1),
            completeTodoCommand.createSubCommand(complete2));
    completeTodoCommand.populateSubCommandList();
    assertEquals(list, completeTodoCommand.subCommandList);
  }

  @Test
  public void execute() throws Exception {
    // System.out.println(todoList);
    ((TodoList) todoList).setTodoList(new ArrayList<>());
    todoList.addTodo(todo1);
    todoList.addTodo(todo2);
    completeTodoCommand = new CompleteTodo(ConfigurationUtils.COMPLETE_TODO, 3, receivedOptions, todoList, requiredSubCommandNames);
    completeTodoCommand.execute();
    Todo expectedTodo1 = new Todo(1,"Finish HW9",true, null, 1,"School");
    Todo expectedTodo2 = new Todo(2,"Clean",true, null, 2,"House");
    assertEquals(expectedTodo1, todoList.getTodo(0));
    assertEquals(expectedTodo2, todoList.getTodo(1));
    // System.out.println(todoList);
  }

  @Test // Won't throw Exception to stop the program. Program will print out error message for the invalid --complete,
  // but still allow other valid "--complete" commands to execute.
  public void executeInvalidIndex() throws InvalidCommandException {
    ((TodoList) todoList).setTodoList(new ArrayList<>());
    todoList.addTodo(todo1);
    todoList.addTodo(todo2);
    // System.out.println(todoList);
    Option invalidComplete = ConfigurationUtils.makeOption(ConfigurationUtils.COMPLETE_TODO);
    invalidComplete.setArgs(Arrays.asList("3"));
    receivedOptions.add(invalidComplete);
    completeTodoCommand = new CompleteTodo(ConfigurationUtils.COMPLETE_TODO, 3, receivedOptions, todoList, requiredSubCommandNames);
    // System.out.println(todoList);
    completeTodoCommand.execute();
  }



  @Test
  public void testToString() {
    completeTodoCommand = new CompleteTodo(ConfigurationUtils.COMPLETE_TODO, 3, receivedOptions, todoList, requiredSubCommandNames);
    String expected = "CompleteTodo{subCommandList=[], receivedOptions=[Option{name='--complete-todo', baseOptionName='--complete-todo', hasArg=true, maxNumArgs=1, minNumArgs=1, maxNumAppearance=10, args=[1]}, Option{name='--complete-todo', baseOptionName='--complete-todo', hasArg=true, maxNumArgs=1, minNumArgs=1, maxNumAppearance=10, args=[2]}], todoList=TodoList{innerList=[Todo{id=1, text='Finish HW9', completed=true, dueDate=null, priority=1, category='School'}, Todo{id=2, text='Clean', completed=true, dueDate=null, priority=2, category='House'}, Todo{id=1, text='Finish HW9', completed=false, dueDate=null, priority=1, category='School'}, Todo{id=2, text='Clean', completed=false, dueDate=null, priority=2, category='House'}]}, requiredSubCommandNames=[], name='--complete-todo'}";
    assertEquals(expected, completeTodoCommand.toString());

  }




}