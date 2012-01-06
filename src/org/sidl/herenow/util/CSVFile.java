package org.sidl.herenow.util;

import java.io.*;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.List;

/**
 * Loads a list of strings, one string per line. These strings represent comma-separated values
 * a file (1 per line), and acts as the iterator over the entire set. This is a greedy reader, in
 * that it loads the entire file-set into memory.
 */
public class CSVFile implements Iterable<String>
{

  /**
   * Main constructor - build from file system path.
   * @param path the path to open on load
   * @throws IOException Failure to load path
   */
  public CSVFile(String path)
    throws IOException
  {
    this.path = path;
    this.list = new LinkedList<String>();
    load();
  }

  /**
   * Main constructor - build from file system path.
   * @param path the path to open on load
   * @param capactiy the number of lines to load
   * @throws IOException Failure to load from path
   */
  public CSVFile(String path, int capactiy)
    throws IOException
  {
    this.path = path;
    this.list = new LinkedList<String>();
    load(capactiy);
  }


  /**
   * @return the iterator over each line of the file
   */
  public Iterator<String> iterator()
  {
    return (list != null) ? list.iterator() : null;
  }

  /**
   * @return the number of lines loaded from file
   */
  public int size()
  {
    return (list != null) ? list.size() : 0;
  }

  /**
   * @return if no lines have been loaded
   */
  public boolean isEmpty()
  {
    return (list == null) || (list.size() == 0);
  }

  /**
   * Calls split on the given string.
   * @return the result of splitting on a comma
   */
  public static String[] split(String s)
  {
    return s.split(",");
  }

  /**
   * Loads all available comma-separated values from a file.
   * The maximum is what can be pushed onto a list.
   * fit in a list.
   * @return the number of centroids loaded
   * @throws IOException Failure to load from path
   */
  private int load()
    throws IOException
  {
    return load(-1);
  }

  /**
   * Loads centroids from file until the entire file is loaded
   * or the capacity limit is reached.
   * @param capacity number of lines to load, or a negative number for all
   *        A zero is ignored.
   * @return the number of lines loaded.
   * @throws IOException Failure to load from path
   */
  private int load(int capacity)
    throws IOException
  {
    BufferedReader reader;

    if (capacity == 0)
      return 0;

    InputStream is = new FileInputStream(path);
    reader = new BufferedReader(new InputStreamReader(is));
    parseFile(reader, capacity);
    reader.close();
    return size();

  }


  private void parseFile(BufferedReader reader, int capacity)
    throws IOException
  {
    int count = 0;
    String readFromFile;

    if (reader == null)
      throw new IllegalArgumentException("No file to read from");

    while ((readFromFile = reader.readLine()) != null)
    {
      if (capacity > 0 && count >= capacity)
        break;
      if (!readFromFile.contains(","))
        continue;
      list.add(readFromFile);
      count++;
    }
  }


  private final String path;
  private final List<String> list;
}
