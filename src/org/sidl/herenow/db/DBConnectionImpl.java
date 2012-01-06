package org.sidl.herenow.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Reads the DB configuration and creates a connection to the DB.
 * Only one connection per process.
 */
public class DBConnectionImpl implements DBConnection
{

  public DBConnectionImpl(DBConfig config)
  {
    this.config = config;
  }

  @Override
  public boolean connect()
  {
    try
    {
      Class.forName(config.getDBDriver());
      if (config.getUser() == null)
        conn = DriverManager.getConnection(config.getURL());
      else
        conn = DriverManager.getConnection(config.getURL(), config.getUser(), config.getPassword());
    }
    catch (Exception e)
    {
      e.printStackTrace();
      exception = e;
    }
    return conn != null;
  }

  @Override
  public Connection pipe()
  {
    return conn;
  }

  @Override
  public Exception getLastException()
  {
    return exception;
  }

  @Override
  public boolean close()
  {
    try
    {
      if (conn != null)
      {
        conn.close();
      }
      return true;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      exception = e;
      return false;
    }
  }


  private Connection conn;
  private Exception exception;
  private final DBConfig config;
}

