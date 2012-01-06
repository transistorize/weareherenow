package org.sidl.herenow.util;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Utility object to help piece together URLs of the format:
 * <p> <code>
 *     http://example.com/search?key1=parameter1&key2=parameter2
 * </code> </p>
 *
 * In this case, parameter1 and parameter2 are filled on call to {@link #build}.
 *
 */
public class URLBuilder
{
  /**
   * @param base the skeleton of the URL
   */
  public URLBuilder(String base)
  {
    url = base;
  }

  /**
   * 
   * @param parameters the values to use in formatting the final URL
   * @return a fully formed URL
   */
  public URL build(Object... parameters)
  {
    try
    {
      return new URL(String.format(url, parameters));
    }
    catch (MalformedURLException mue)
    {
      mue.printStackTrace();
      return null;
    }
  }

  private final String url;
}
