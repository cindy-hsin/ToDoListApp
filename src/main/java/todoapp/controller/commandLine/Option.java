package todoapp.controller.commandLine;

import java.util.List;
import java.util.Objects;

/**
 * Represents a command line "--" Option
 */
public class Option {
  private final static boolean DEFAULT_HASARG = false;
  private final static int DEFAULT_MAX_NUM_ARGS = 50;   // maximum number of words in the argument (e.g. description text , category name)
  private final static int DEFAULT_MIN_NUM_ARGS = 0;
  // maximum number of appearance in command line.
  private final static int DEFAULT_MAX_NUM_APPEARANCE = 1;


  /** Predefined fields **/
  private String name;        // the Option name: ADD_TODO_TEXT
  private String baseOptionName;    // the group that this Option belongs to: ADD_TODO
  private boolean hasArg;
  private int maxNumArgs;  // e.g. --todo-text finish hw9  --> numArgs = 2
  private int minNumArgs;
  private int maxNumAppearance;
  /** To be populated when parsing command line**/
  private List<String> args;

  /**
   * Private Constructor for Option
   * @param builder - the builder
   */
  private Option(Builder builder) {
    this.name = builder.name;
    this.baseOptionName = builder.baseOptionName;
    this.hasArg = builder.hasArg;
    this.maxNumArgs = builder.maxNumArgs;
    this.minNumArgs = builder.minNumArgs;
    this.maxNumAppearance = builder.maxNumAppearance;
  }

  /**
   * Gets hasArg
   * @return hasArg
   */
  public boolean isHasArg() {
    return this.hasArg;
  }

  /**
   * Gets args
   * @return args
   */
  public List<String> getArgs() {
    return this.args;
  }


  /**
   * Gets name
   * @return name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Gets baseOptionName
   * @return baseOptionName
   */
  public String getBaseOptionName() {
    return this.baseOptionName;
  }

  /**
   * Gets maxNumAppearance.
   * @return
   */
  public int getMaxNumAppearance() {
    return this.maxNumAppearance;
  }

  /**
   * Sets the Option's argument given from command line
   * @param args - argument given from command line
   * @throws InvalidCommandException - if number of given arguments is invalid
   */
  public void setArgs(List<String> args) throws InvalidCommandException {
    this.validateNumArgs(args);
    this.args = args;
  }


  /**
   * Helper method to validate the number of given arguments
   * @param args - arguments given from command line
   * @throws InvalidCommandException - if number of given arguments is invalid
   */
  private void validateNumArgs(List<String> args) throws InvalidCommandException {
    if (!this.isHasArg() && args.size() > 0) {
      throw new InvalidCommandException("Invalid number of arguments: " + this.name + " should not have any arguments");
    }
    if (args.size() < this.minNumArgs) {
      throw new InvalidCommandException("Invalid number of arguments: " +
          this.name + " should have at least " + this.minNumArgs + " argument");
    }
    else if (args.size() > this.maxNumArgs) {
      throw new InvalidCommandException("Invalid number of arguments: " +
          this.name + "'s argument cannot contain more than " + this.maxNumArgs + "words.");

    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Option option = (Option) o;
    return isHasArg() == option.isHasArg() && maxNumArgs == option.maxNumArgs
        && minNumArgs == option.minNumArgs && getMaxNumAppearance() == option.getMaxNumAppearance()
        && Objects.equals(getName(), option.getName()) && Objects
        .equals(getBaseOptionName(), option.getBaseOptionName()) && Objects
        .equals(getArgs(), option.getArgs());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getBaseOptionName(), isHasArg(), maxNumArgs, minNumArgs,
        getMaxNumAppearance(), getArgs());
  }

  @Override
  public String toString() {
    return "Option{" +
        "name='" + name + '\'' +
        ", baseOptionName='" + baseOptionName + '\'' +
        ", hasArg=" + hasArg +
        ", maxNumArgs=" + maxNumArgs +
        ", minNumArgs=" + minNumArgs +
        ", maxNumAppearance=" + maxNumAppearance +
        ", args=" + args +
        '}';
  }

  public static class Builder {
    private String name;
    private String baseOptionName;
    private boolean hasArg;
    private int maxNumArgs;
    private int minNumArgs;
    private int maxNumAppearance;

    /**
     * Builder constructor
     * @param baseOptionName the Option's baseOptionName
     * @param name the Option's name
     */
    public Builder(String baseOptionName, String name) {
      // Only set required fields and default value
      this.baseOptionName = baseOptionName;
      this.name = name;
      this.hasArg = DEFAULT_HASARG;
      this.minNumArgs = DEFAULT_MIN_NUM_ARGS;
      this.maxNumArgs = DEFAULT_MAX_NUM_ARGS;
      this.maxNumAppearance = DEFAULT_MAX_NUM_APPEARANCE;
    }

    /**
     * Sets hasArg
     * @return the Option
     */
    public Builder setHasArg(){
      this.hasArg = true;
      return this;
    }

    /**
     * Sets minNumArgs
     * @return the Option
     */
    public Builder setMinNumArgs(int minNumArgs) {
      this.minNumArgs = minNumArgs;
      return this;
    }

    /**
     * Sets maxNumArgs
     * @return the Option
     */
    public Builder setMaxNumArgs(int maxNumArgs) {
      this.maxNumArgs = maxNumArgs;
      return this;
    }

    /**
     * Sets  maxNumAppearance
     * @return the Option
     */
    public Builder setMaxNumAppearance(int maxNumAppearance) {
      this.maxNumAppearance = maxNumAppearance;
      return this;
    }

    /**
     * Build method
     * @return the Option object
     */
    public Option build() {
      return new Option(this);
    }


  }



}
