package org.sidl.herenow.foursq;

import static org.sidl.herenow.foursq.StatePlaneConverter.convertTo;

import org.junit.Test;


import static org.junit.Assert.*;

/**
 * Test some conversions around the designated geographic area. The expected values were
 * externally generated by another GIS product.
 */
public class StatePlaneConverterTest
{
  /** Fudge factor */
  public static final float EPS = (float)10;


  @Test
  public void testConversion()
  {
    float[] expected1 = {967654.538119f, 253330.113638f};
    assertEqualsEps(expected1, convertTo(40.862f, -74.060f ), EPS);

    float[] expected2 = {1082874.62919f,	147867.95748f};
    assertEqualsEps(expected2, convertTo(40.572f, -73.645f), EPS);

    float[] expected3 = {973971.403506f,	149856.233696f};
    assertEqualsEps(expected3, convertTo(40.578f, -74.037f), EPS);

    float[] expected4 = {1068600.13832f,	257843.706957f};
    assertEqualsEps(expected4, convertTo(40.874f, -73.695f), EPS);

    float[] expected5 = {1042739.69176f,	315698.523247f};
    assertEqualsEps(expected5, convertTo(41.033f, -73.788f), EPS);

    float[] expected6 = {985899.814999f,	390685.033437f};
    assertEqualsEps(expected6, convertTo(41.239f, -73.994f), EPS);
  }

  
  /** Assert equals within an epsilon value */
  private static void assertEqualsEps(float[] expected, float[] actual, float eps)
  {
    assertEquals(expected.length, actual.length);
    for (int i = 0; i < expected.length; i++)
    {
      boolean within = actual[i] >= (expected[i] - eps) && actual[i] <= (expected[i] + eps);
      if (!within)
      {
        float error = Math.abs(expected[i] - actual[i])/ expected[i] * 100;
        throw new AssertionError(
            "index: " + i
            + " expected: " + expected[i]
            + " actual: " + actual[i]
            + " error: " + error);
      }
    }
  }


}
