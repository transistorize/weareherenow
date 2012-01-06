package org.sidl.herenow.search;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sidl.herenow.Venue;

import java.net.URI;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Abstracts the base details for a URI-driven search platform, for a single client endpoint.
 *
 * <h3>Threading Notes</h3>
 * This class is always called from a single-thread.
 */
public interface SearchProvider
{
  /** @return an immutable client identifier */
  public String getId();

  /**
   * @param location a string describing the centroid location, usually "lat,lng"
   * @return the URI need to open an IO stream for the desired search location
   */
  public URI getURIForLocation(String location);

  /**
   * @param connection the connection that has the header information
   * @return the number of available requests available to the search engine
   */
  public int getAvailableRequests(URLConnection connection);

  /**
   * @param response the full HTTP response from the remote web service
   * @return the array of location objects from a full HTTP response
   * @throws JSONException if the locations cannot be parsed due to missing data
   */
  public List<Venue> getLocations(JSONObject response) throws JSONException;

  /**
   * @param location the parsed single-location JSON structure
   * @return a map of the values pulled from the location based on the leaf keys
   * @throws JSONException if the location object cannot be parsed
   */
  public Venue getLocation(JSONObject location) throws JSONException;
  
}
