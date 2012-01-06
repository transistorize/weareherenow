package org.sidl.herenow.db;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Builds DBConfigs from property files.
 */
public class DBConfigFactory
{
  public static final String SEPARATOR = "=";


  public static DBConfig loadConfig(File path) throws IOException
  {
    if (path == null)
      throw new NullPointerException("Null file path");

    BufferedReader reader = new BufferedReader(new FileReader(path));
    return loadConfig(reader);
  }


  public static DBConfig loadConfig(String config) throws IOException
  {
    if (config == null)
      throw new NullPointerException("Null file path");

    BufferedReader reader = new BufferedReader(new StringReader(config));
    return loadConfig(reader);
  }


  static DBConfig loadConfig(BufferedReader reader) throws IOException
  {
    String s;

    int count = 1;
    Map<String,String> map = new HashMap<String,String>();

    while ((s = reader.readLine()) != null)
    {
      String[] pair = s.split(SEPARATOR);

      if (pair.length < 1 || pair[0] == null)
        throw new IOException("Could not parse DB config file (line:"+ count + "=" + s + ")");

      if (pair.length == 1)
      {
        map.put(pair[0].trim(), null);
      }
      else
      {
        map.put(pair[0].trim(), pair[1].trim());
      }

      count++;
    }
    return new MapDBConfig(map);
  }



}
