package org.sidl.herenow.db;

/**
 * Simple SQLite config that does not support authorization.
 */
public class SQLiteDBConfig implements DBConfig
{
  @Override
  public String getDBDriver()
  {
    return "org.sqlite.JDBC";
  }

  @Override
  public String getURL()
  {
    return "jdbc:sqlite:test_jdbc.db";
  }

  @Override
  public String getFileName()
  {
     return "test_jdbc.db";
  }

  @Override
  public String getTableName()
  {
    return "test_table";
  }

  @Override
  public String getUser()
  {
    return null;
  }

  @Override
  public String getPassword()
  {
    return null;
  }

}
