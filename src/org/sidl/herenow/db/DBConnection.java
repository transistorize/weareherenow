package org.sidl.herenow.db;

import java.sql.Connection;

/**
 * Reads the DB configuration and creates a connection to the DB.
 */
public interface DBConnection
{

  public boolean connect();

  public Connection pipe();

  public Exception getLastException();

  public boolean close();

}
