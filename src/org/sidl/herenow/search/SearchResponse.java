package org.sidl.herenow.search;

import org.sidl.herenow.Venue;

import java.util.List;

/**
 * Immutable search response.
 */
public class SearchResponse
{

  protected final static int OK_ERROR_CODE = 200;
  
  protected final static String OK_MESSAGE = "OK";

 
  public SearchResponse(List<Venue> locations, int available)
  {
     this(locations, OK_ERROR_CODE, OK_MESSAGE, available);
  }

  public SearchResponse(int responseCode, String message, int available)
  {
    this(null, responseCode, message, available);
  }
  
  public boolean isError()
  {
    return responseCode != OK_ERROR_CODE;
  }

  public int getResponseCode()
  {
    return responseCode;
  }

  public int getAvailable()
  {
    return available;
  }

  public String getMessage()
  {
    return message;
  }

  public List<Venue> getLocations()
  {
    return response;
  }


  private SearchResponse(List<Venue> locations, int responseCode, String message, int available)
  {
    this.response = locations;
    this.responseCode = responseCode;
    this.message = message;
    this.available = available;
  }


  private final int responseCode;
  private final int available;
  private final String message;
  private final List<Venue> response;

 
}
