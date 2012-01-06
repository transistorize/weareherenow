package org.sidl.herenow.db;

import java.io.File;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Test;

/**
 * Simple test of DB.
 */
public class DBConnectionTest extends TestCase
{
  @After public void tearDown()
  {
    File f = new File(config.getFileName());
    if (f.exists() && !f.delete())
      System.out.println("Can't delete " + f);
  }


  @Test public void testSetup()
  {
    DBConnection c = new DBConnectionImpl(config);
    assertTrue(c.connect());
    assertNotNull(c.pipe());
    assertTrue(c.close());
  }


  final DBConfig config = new SQLiteDBConfig();
}
