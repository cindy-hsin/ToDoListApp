package todoapp.model.fileIO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/** This class represents a CSV writer. It writes todos to CSV file. */
public class CsvWriter implements ICsvWriter {
  private static final String QUOTATION = "\"";
  private static final String COMMA = ",";

  /** Constructor of CsvWriter class. */
  public CsvWriter() {
  }

  /**
   * Writes data to a CSV file
   *
   * @param path a path to CSV file
   * @param entries - a List of all lines (including header line) as Lists of String
   * @throws IOException if something went wrong while writing to a file.
   */
  @Override
  public void write(String path, List<List<String>> entries) throws IOException {
    try (BufferedWriter outputFile = new BufferedWriter(new FileWriter(path))) {
      for (int i = 0; i < entries.size() - 1; i++) {
        outputFile.write(convert(entries.get(i)) + System.lineSeparator());
      }
      outputFile.write(convert(entries.get(entries.size() - 1)));
    } catch (IOException ioe) {
      throw new IOException(
          "Something went wrong when writing to file: " + path + ioe.getMessage());
    }
  }

  /**
   * Convert a List of String to one String joint with quotation marks and comma
   *
   * @param stringList a list of Strings to be joined with quotation marks and comma
   * @return one String consisting of provided List of String joint with quotation marks and comma
   */
  private String convert(List<String> stringList) {
    String res = "";
    for (int i = 0; i < stringList.size() - 1; i++) {
      res += QUOTATION + stringList.get(i) + QUOTATION + COMMA;
    }
    res += QUOTATION + stringList.get(stringList.size() - 1) + QUOTATION;
    return res;
  }
}
