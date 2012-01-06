package org.sidl.herenow.db;

/**
 * Property-bag for database connection configuration.
 */
public interface DBConfig
{
  public String getDBDriver();

  public String getURL();

  public String getFileName();
  
  public String getTableName();
  
  public String getUser();

  public String getPassword();

}
