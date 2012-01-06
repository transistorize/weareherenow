package org.sidl.herenow.search;

import org.json.JSONException;
import org.json.JSONObject;
import org.sidl.herenow.Venue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides local file access, mostly for testing.
 */
public class FileSearchMethod extends SearchMethod
{
  public FileSearchMethod(SearchProvider provider)
  {
    super(provider);
  }

  @Override
  public SearchResponse invoke(URI uri)
  {
    FileInputStream stream;
    InputStreamReader reader;
    JSONObject rawJSON;
    int available = 1000;
    int errorCode;
    String message;
    try
    {
      // make the connection and wait for the headers to come back
      LOG.info(uri.toString());
      stream = new FileInputStream(new File(uri));
      reader = new InputStreamReader(stream, "UTF-8");

      // check how many queries are left in our quota
      available = 1000;

      //read and parse the entire result
      rawJSON = readJSONResponse(reader);
      stream.close();
      List<Venue> venues = getProvider().getLocations(rawJSON);
      return new SearchResponse(venues, available);
    }
    catch (JSONException jsone)
    {
      message = jsone.getMessage();
      errorCode = IO_ERROR_CODE;
      LOG.log(Level.WARNING, "Error parsing JSON response" , jsone);
    }
    catch (IOException e)
    {
      message = e.getMessage();
      errorCode = IO_ERROR_CODE;

      String err = String.format("URL failed with %d/%s to %s",
          errorCode, message, getProvider().getId());
      LOG.log(Level.WARNING, err, e);
    }

    return new SearchResponse(errorCode, message, available);
  }



  private static final Logger LOG = Logger.getLogger(FileSearchMethod.class.getName());
}
