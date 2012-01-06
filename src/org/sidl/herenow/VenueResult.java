package org.sidl.herenow;

import org.json.JSONArray;
import org.sidl.herenow.search.SearchProvider;

import java.util.List;

/**
 * A the result of search for venues. Can be a set of venues or an error.
 */
public interface VenueResult
{
  /** @return the resulting array of venues, in JSON format, or null on error */
  public List<Venue> getVenues();

  /** @return the ID string of the endpoint that provided the result */
  public String getSourceId();

  /** @return number of queries available to make */
  public int getQueriesAvailable();
  
  /** @return true if an error searching or on general IO Exceptions */
  public boolean isError();

  /** @return the HTTP error code or 0 for general IO exceptions */
  public int getResponseCode();

  /** @return the response or exception message */
  public String getResponseMessage();

  /** @return the response/parse time for query in nano-seconds */
  public long getTimeInNanos();
}
