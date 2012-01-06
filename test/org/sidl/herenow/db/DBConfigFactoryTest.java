package org.sidl.herenow.db;

import java.io.IOException;

import static org.sidl.herenow.db.MapDBConfig.*;
import junit.framework.TestCase;

import org.junit.Test;

/**
 * Test reading capability.
 */
public class DBConfigFactoryTest extends TestCase
{
  @Test public void testLoadFromString()
    throws IOException
  {
    String s = DBConfigFactory.SEPARATOR;
    String n = "\n";
    String user = "Bob";
    String password = null;
    String db_driver = "mysql";
    String db_name  = "test.db";
    String url = "http://weareherenow.org";

    String[] keys = {DRIVER,USER_NAME,URL,DB_NAME};
    String[] values = {db_driver,user,url,db_name};
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < keys.length; i++)
      buffer.append(keys[i]).append(s).append(values[i]).append(n);

    buffer.append(PASSWORD).append(s).append(n);

    DBConfig cf = DBConfigFactory.loadConfig(buffer.toString());
    assertEquals(user, user, cf.getUser());
    assertEquals(db_driver, db_driver, cf.getDBDriver());
    assertEquals(db_name, db_name, cf.getFileName());
    assertEquals(url, url, cf.getURL());
    assertEquals(password, null, cf.getPassword());
  }

}
