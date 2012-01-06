package org.sidl.herenow.search;

import org.sidl.herenow.Venue;
import org.sidl.herenow.VenueResult;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Executes the actual search
 */
public class SearchTask implements Runnable
{
  public SearchTask(DefaultSearchEngine engine, SearchMethod method, String location)
  {
    this.engine = engine;
    this.method = method;
    this.location = location;
  }

  @Override
  public void run()
  {
    long start, end;
    SearchResponse response;
    VenueResult result;
    SearchProvider sp;

    engine.incrementSearchesAndGet();

    start = System.nanoTime();
    synchronized (method)
    {
      response = method.searchByCentroid(location);
      sp = method.getProvider();
    }
    end = System.nanoTime();

    result = parseResponse(sp, response, start, end);

    logResult(method, start, end, result);

    engine.postResult(result);
  }

  private static VenueResult
    parseResponse(SearchProvider provider, SearchResponse response, long start, long end)
  {
    VenueResult result;
    long time = getTime(start, end);

    if (!response.isError())
    {
      List<Venue> locations = response.getLocations();
      result =  new VenueResultImpl(provider.getId(), locations, response.getAvailable(), time);
    }
    else
    {
      result = new VenueResultImpl(provider.getId(), response.getResponseCode(),
          response.getMessage(), response.getAvailable(), time);
    }

    return result;
  }

  private static void logResult(SearchMethod method, long start, long end, VenueResult result)
  {
    String status;
    status = String.format(
        "Request returned with %d/%s [%d requests available, %s seconds total] to %s",
        result.getResponseCode(),
        result.getResponseMessage(),
        result.getQueriesAvailable(),
        (end - start)/1.0e9,
        method.toString());

    if (result.isError())
      LOG.log(Level.INFO, status);
    else
      LOG.log(Level.FINE, status);
  }

  private static long getTime(long start, long end)
  {
    return (start <= end) ? end - start : 0;
  }


  private final SearchMethod method;
  private final String location;
  private final DefaultSearchEngine engine;

  private static Logger LOG = Logger.getLogger(SearchTask.class.getName());
}

