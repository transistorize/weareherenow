package org.sidl.herenow.foursq;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sidl.herenow.search.SearchProvider;
import org.sidl.herenow.Venue;
import org.sidl.herenow.util.URLBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstracts the foursquare service APIs.
 */
public class FoursquareProvider implements SearchProvider
{
  public FoursquareProvider()
  {
    this("no_id", "secret");
  }
  
  public FoursquareProvider(String id, String key)
  {
    if (id == null || key == null)
    {
      throw new IllegalArgumentException("Null ID or Key");
    }
    this.id = id;
    this.key = key;
  }

  @Override
  public String getId()
  {
    return id;
  }

  @Override
  public URI getURIForLocation(String location)
  {
    try
    {
      URLBuilder builder = new URLBuilder(FSQ_BASIC_QUERY_URL);
      return builder.build(id, key, location).toURI();
    }
    catch (URISyntaxException e)
    {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public List<Venue> getLocations(JSONObject response) throws JSONException
  {
    if (LOG.isLoggable(Level.FINER))
    {
      LOG.finer(response.toString());
    }
    List<Venue> venues = new ArrayList<Venue>();
    
    JSONObject location = null;
    JSONObject responseSubgroup = response.getJSONObject(FSQ_RESPONSE_ATTR);
    JSONArray array = null;
    
    if (responseSubgroup.has(FSQ_VENUES_ATTR))
    {
      array = responseSubgroup.getJSONArray(FSQ_VENUES_ATTR);
    }
    else if (responseSubgroup.has("groups"))
    {
      array = responseSubgroup.getJSONArray("groups").getJSONObject(0).getJSONArray("items");
    }

    if (array != null)
    {
      for(int v = 0; v < array.length(); v++)
      {
        try
        {
          location = array.getJSONObject(v);
          venues.add(getLocation(location));
        }
        catch (JSONException e)
        {
          LOG.log(Level.FINER, "Skipped venue " + location, e);
        }
      }
    }
    return venues;  
  }

  @Override
  public Venue getLocation(JSONObject location) throws JSONException
  {
    return new VenueImpl(location);
  }


  @Override
  public int getAvailableRequests(URLConnection connection)
  {
    //the default is 1 to allow subsequent searches
    return connection.getHeaderFieldInt(FSQ_RATE_LIMIT, 1);
  }

  @Override
  public String toString()
  {
    return "FSQ_" + getId();
  }

 
  private final String id;
  private final String key;

  // todo DSL for this stuff? another config file?

  private static final String FSQ_BASIC_QUERY_URL =
      "https://api.foursquare.com/v2/venues/search?client_id=%s&client_secret=%s&ll=%s";

  private static final String FSQ_QUERY_URL = FSQ_BASIC_QUERY_URL +
      "&v=20120103&radius=170&intent=browse";


  private static final String FSQ_RATE_LIMIT = "X-RateLimit-Remaining";
  private static final String FSQ_RESPONSE_ATTR = "response";
  private static final String FSQ_VENUES_ATTR = "venues";
  
  private static final Logger LOG = Logger.getLogger(FoursquareProvider.class.getName());
}
