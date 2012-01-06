package org.sidl.herenow.db;

import java.util.HashMap;
import java.util.Map;

/**
 * Property Map
 */
class MapDBConfig implements DBConfig
{

  public static final String DRIVER = "driver";
  public static final String URL = "url";
  public static final String DB_NAME = "file_name";
  public static final String TABLE_NAME = "table_name";
  public static final String USER_NAME = "user_name";
  public static final String PASSWORD = "password";

  public MapDBConfig(Map<String,String> map)
  {
    this.properties = new HashMap<String,String>(map);
  }

  @Override
  public String getDBDriver()
  {
    return properties.get(DRIVER);
  }

  @Override
  public String getURL()
  {
    return properties.get(URL);
  }

  @Override
  public String getFileName()
  {
    return properties.get(DB_NAME);
  }

  @Override
  public String getTableName()
  {
    return properties.get(TABLE_NAME);
  }

  @Override
  public String getUser()
  {
    return properties.get(USER_NAME);
  }

  @Override
  public String getPassword()
  {
    return properties.get(PASSWORD);
  }

  private final Map<String,String> properties;

}
