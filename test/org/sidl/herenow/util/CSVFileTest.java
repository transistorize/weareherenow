package org.sidl.herenow.util;

import java.io.IOException;
import java.util.Iterator;

import static junit.framework.Assert.*;
import org.junit.Test;

/**
 * Tests Centroid File
 */
public class CSVFileTest
{
  public final static String RESOURCE = "org/sidl/herenow/util/Grid_Centroids_115_test.csv";

  @Test public void testFromFile() throws IOException
  {
    CSVFile cfile = new CSVFile(RESOURCE);

    assertEquals("Should load all points", 115, cfile.size());

    cfile = new CSVFile(RESOURCE, 50);

    assertEquals("Should load 50 points", 50, cfile.size());
  }


  @Test public void testIterator() throws IOException
  {
    CSVFile cfile = new CSVFile(RESOURCE);

    assertEquals("Should load all points", 115, cfile.size());

    Iterator<String> iter = cfile.iterator();

    assertTrue("Can read first", iter.hasNext());
    assertEquals("Check first line", "40.8371,-73.8389", iter.next());
  }
}
