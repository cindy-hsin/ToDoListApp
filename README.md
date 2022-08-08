# TaskManagementApp
## Design Pattern
1. Pattern: Command Pattern
- Where: The inheritance hierarchy rooted at AbstractCommand, and the Invoker class.
- Why: Easy to manage the execution order of different commands, by taking advantage of subtype polymorphysm.
- How: Invoker's execute() method will create AbstractBaseCommand for each group of Options received from CommandLineParser, and triggers their execute() method
by predefined execution order. Each AbstractBaseCommand object's execute() method will then create an AbstractSubCommand object for each of the Options in the group, and triggers
their execute() method. AbstractSubCommand's execute() method will perform actual operation on TodoList and CsvFile.

2. Pattern: Singleton Pattern
- Where: TodoList class, and where the list is instantiated: Invoker's constructor.
- Why: To guarantee there is only one TodoList in the program.

3. Pattern: Factory Pattern
- Where: Invoker class's createBaseCommand method, and concrete base command classes(AddTodo, CompleteTodo, DisplayTodo) createSubCommand method.
- Why: To hide the logic of instantiating subtype objects from the caller method.

4. Pattern: Builder Pattern
- Where: Option class
- Why: Provide flexibility when instantiating object with many optional fields.


## Note about csv files
Uploaded csv files are used in our unit tests.
