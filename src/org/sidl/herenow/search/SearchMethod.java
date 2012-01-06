package org.sidl.herenow.search;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

/**
 * Represents one search request to a JSON endpoint.
 * An responseCode of 200 is considered OK, regardless of source.
 *
 * Must call from a single thread.
 */
public abstract class SearchMethod
{
  protected final static int IO_ERROR_CODE = 0;


  public SearchMethod(SearchProvider provider)
  {
    if (provider == null)
    {
      throw new NullPointerException("Null provider");
    }
    this.provider = provider;
  }
  
  public SearchResponse searchByCentroid(String location)
  {
    if (location == null)
    {
      throw new IllegalArgumentException("Null location");
    }
    URI uri = getProvider().getURIForLocation(location);
    return invoke(uri);
  }

  protected abstract SearchResponse invoke(URI uri);

  protected SearchProvider getProvider()
  {
    return provider;
  }
  
  protected JSONObject readJSONResponse(InputStreamReader reader)
      throws IOException, JSONException
  {
    JSONTokener tokenizer = new JSONTokener(reader);
    return new JSONObject(tokenizer);
  }

  private final SearchProvider provider;
   
}