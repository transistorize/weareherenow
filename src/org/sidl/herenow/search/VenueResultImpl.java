package org.sidl.herenow.search;

import org.sidl.herenow.Venue;
import org.sidl.herenow.VenueResult;

import java.util.List;

/**
 * Implements the result set delivered from Foursquare.
 */
class VenueResultImpl implements VenueResult
{
  public VenueResultImpl(String sourceId, List<Venue> venues, int available, long time)
  {
    this.id=sourceId;
    this.array = venues;
    this.errorCode = NO_ERROR;
    this.errorMessage = "OK";
    this.available = available;
    this.time = time;
  }


  public VenueResultImpl(String sourceId, int errorCode, String message, int available, long time)
  {
    this.id = sourceId;
    this.array = null;
    this.errorCode = errorCode;
    this.errorMessage = message;
    this.available = available;
    this.time = time;
  }

  @Override
  public String getSourceId()
  {
    return id;
  }

  @Override
  public List<Venue> getVenues()
  {
    return array;
  }

  @Override
  public int getQueriesAvailable()
  {
    return available;
  }

  @Override
  public boolean isError()
  {
    return errorCode != NO_ERROR;
  }

  @Override
  public int getResponseCode()
  {
    return errorCode;
  }

  @Override
  public String getResponseMessage()
  {
    return errorMessage;
  }

  @Override
  public long getTimeInNanos()
  {
    return time;
  }

  private final String id;
  private final List<Venue> array;
  private final long time;
  private final int errorCode;
  private final String errorMessage;
  private final int available;

  private static int NO_ERROR = 200;
}
