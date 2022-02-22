package todoapp.model.fileIO;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class CsvReaderTest {
  CsvReader reader;
  CsvReader reader2;

  @Before
  public void setUp() throws Exception {
    reader = new CsvReader();
    reader2 = new CsvReader();
  }

  @Test
  public void readParse() throws Exception {
    reader.readParse("testTodosRead.csv");
    List<String> line =
        new ArrayList<>(Arrays.asList("1", "Finish HW9", "false", "03/22/2020", "1", "school"));
    List<List<String>> entries = new ArrayList<>();
    entries.add(line);
    assertEquals(entries, reader.getEntries());
    reader2.readParse("testTodosRead.csv");
  }

  @Test
  public void readParseEmptyFile() throws Exception {
    reader.readParse("empty.csv");
    assertEquals(new ArrayList<>(), reader.getEntries());
  }

  @Test
  public void testEquals() {
    assertTrue(reader.equals(reader));
    assertFalse(reader.equals(null));
    assertTrue(reader.equals(reader2));
  }

  @Test
  public void testHashCode() {
    assertTrue(reader2.hashCode() == reader.hashCode());
  }

  @Test
  public void testToString() {
    assertEquals(
        "CsvReader{entries=[]}",
        reader.toString());
  }
}
