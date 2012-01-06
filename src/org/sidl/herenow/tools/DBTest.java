package org.sidl.herenow.tools;

import org.sidl.herenow.db.DBConfig;
import org.sidl.herenow.db.DBConfigFactory;
import org.sidl.herenow.db.DBConnection;
import org.sidl.herenow.db.DBConnectionImpl;

import java.io.File;
import java.io.IOException;
import java.sql.*;

/**
 * Simple script to test the DB connection on disk using SQLite.
 */
public class DBTest
{
  public static void main(String... s)
  {
    String dbConfigFile = s[0];
    DBConfig config;
    DBConnection connection = null;

    try
    {
      config = DBConfigFactory.loadConfig(new File(dbConfigFile));
      connection = new DBConnectionImpl(config);
      if (!connection.connect())
      {
        System.err.println("Failed to open DB Connection to: " + config.getURL());
        System.err.println(connection.getLastException());
        System.exit(1);
      }
      testDBConnection(connection);
    }
    catch (IOException ioe)
    {
      System.err.println("Error loading config file: " + dbConfigFile);
      System.err.println(ioe);
    }
    finally
    {
      if (connection != null)
      {
        connection.close();
      }
    }
  }


  public static void testDBConnection(DBConnection c)
  {
    try
    {
      Connection conn = c.pipe();
      Statement stmt = conn.createStatement();
      stmt.executeUpdate("drop table if exists people;");
      stmt.executeUpdate("create table people (name, occupation);");
      PreparedStatement prep = conn.prepareStatement("insert into people values (?, ?);");
      prep.setString(1, "Gandhi");
      prep.setString(2, "politics");
      prep.addBatch();
      prep.setString(1, "Turing");
      prep.setString(2, "computers");
      prep.addBatch();
      prep.setString(1, "Wittgenstein");
      prep.setString(2, "philosophy");
      prep.addBatch();

      conn.setAutoCommit(false);
      prep.executeBatch();
      conn.setAutoCommit(true);

      ResultSet rs = stmt.executeQuery("select * from people;");
      while (rs.next())
      {
        System.out.println("name = " + rs.getString("name"));
        System.out.println("occupation = " + rs.getString("occupation"));
      }
    }
    catch (SQLException sqle)
    {
      sqle.printStackTrace();
    }
  }
}
