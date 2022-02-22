package todoapp.model.fileIO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import todoapp.controller.commandLine.ConfigurationUtils;

/**
 * This class represents a CSV reader. It reads from a given CSV file and extracts the information.
 */
public class CsvReader implements ICsvReader {
  private static final String DOUBLE_QUOTES_PATTERN = "\"([^\"]*)\"";
  private List<List<String>> entries;

  /** Constructor of CsvReader class. */
  public CsvReader() {
    this.entries = new ArrayList<>();
  }

  /**
   * Read in CSV by lines, parses the contents of CSV file into list of entries. Each entry is a list of Strings
   * representing data from one row in a CSV file.
   *
   * @param path a path to CSV file
   * @throws Exception if file doesn't exist, have wrong extension or something unexpected happened
   *     during parsing
   */
  @Override
  public void readParse(String path) throws Exception {
    if (!path.endsWith(".csv")) {
      throw new IllegalArgumentException("Error: The input file is not in .csv format.");
    }
    try (BufferedReader file = new BufferedReader(new FileReader(path))) {
      String line;
      file.readLine(); // -> Read once to skip the header line. Assume we have valid headers.
      // this.headers = this.convertToListOfStrings(line);
      while ((line = file.readLine()) != null) {
        this.entries.add(convertToListOfStrings(line));
      }
    } catch (FileNotFoundException fnfe) {
      throw new FileNotFoundException("File does not exist");
    } catch (IOException ioe) {
      throw new IOException("Something went wrong!");
    }
  }

  /**
   * Parses each line (except for header line) in CSV to a List.
   *
   * @param csvLineString a String representing contents of a row in CSV file
   * @return list of Strings
   * @throws IllegalArgumentException If a line can't be parsed to correct number of Strings (number
   *     of headers)
   */
  public List<String> convertToListOfStrings(String csvLineString) throws IllegalArgumentException {
    List<String> csvLineList = new ArrayList<>();
    Pattern pattern = Pattern.compile(DOUBLE_QUOTES_PATTERN);
    Matcher matcher = pattern.matcher(csvLineString);
    while (matcher.find()) {
      csvLineList.add(matcher.group(1));
    }

    if (csvLineList.size() != ConfigurationUtils.DEFAULT_HEADER_ORDER.size()) {
      throw new IllegalArgumentException("Wrong CSV formatting on line: " + csvLineString);
    }
    return csvLineList;
  }

  /**
   * Gets the entries from CSV file
   *
   * @return the entries from CSV file
   */
  public List<List<String>> getEntries() {
    return this.entries;
  }

  /**
   * Allows CsvReaders to be compared for equality
   *
   * @param o a different CsvReader to compare to
   * @return true if the objects are equal, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CsvReader)) {
      return false;
    }
    CsvReader csvReader = (CsvReader) o;
    return Objects.equals(getEntries(), csvReader.getEntries());
  }

  /**
   * Calculates a unique integer key
   *
   * @return a unique integer key
   */
  @Override
  public int hashCode() {
    return Objects.hash(getEntries());
  }

  /**
   * Represents the CsvReader as a String
   *
   * @return a String containing information representing a CsvReader
   */
  @Override
  public String toString() {
    return "CsvReader{" + "entries=" + this.entries + '}';
  }
}
