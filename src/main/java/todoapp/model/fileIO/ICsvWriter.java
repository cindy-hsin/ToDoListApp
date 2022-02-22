package todoapp.model.fileIO;

import java.io.IOException;
import java.util.List;

/** Represents ICsvWriter Interface */
public interface ICsvWriter {

  /**
   * Writes data to a CSV file
   *
   * @param path a path to CSV file
   * @param entries - a List of all lines (including header line) as Lists of String
   * @throws IOException if something went wrong while writing to a file.
   */
  void write(String path, List<List<String>> entries) throws IOException;
}
