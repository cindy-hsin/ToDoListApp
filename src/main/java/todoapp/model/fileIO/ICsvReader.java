package todoapp.model.fileIO;

/** Represents ICsvReader Interface */
public interface ICsvReader {
  /**
   * Read in CSV by lines, parses the contents of CSV file into list of entries. Each entry is a
   * list of Strings representing data from one row in a CSV file.
   *
   * @param path a path to CSV file
   * @throws Exception if file doesn't exist, have wrong extension or something unexpected happened
   *     during parsing
   */
  void readParse(String path) throws Exception; // return parsed lines
}
