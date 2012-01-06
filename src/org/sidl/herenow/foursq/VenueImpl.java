package org.sidl.herenow.foursq;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sidl.herenow.Venue;

/**
 * Foursquare Venue
 */
public class VenueImpl implements Venue
{
  public VenueImpl(JSONObject location) throws JSONException
  {
    int count = 0;
    String cat1 = null;
    String cat2 = null;

    if (!location.has(FSQ_ID_ATTR) || !location.has(FSQ_NAME_ATTR) || !location.has(FSQ_LOC_ATTR))
    {
      throw new JSONException("Insufficient data");
    }

    JSONObject loc = location.getJSONObject(FSQ_LOC_ATTR);
    float lat = (float) loc.getDouble(FSQ_LAT_ATTR);
    float lng = (float)loc.getDouble(FSQ_LNG_ATTR);
    float[] convertXY = StatePlaneConverter.convertTo(lat, lng);

    if (location.has(FSQ_HERE_NOW_ATTR))
    {
      count = location.getJSONObject(FSQ_HERE_NOW_ATTR).getInt(FSQ_COUNT_ATTR);
    }

    if (location.has(FSQ_CATEGORIES_ATTR))
    {
      JSONArray categories = location.getJSONArray(FSQ_CATEGORIES_ATTR);
      if (categories != null && categories.length() > 0)
      {
        JSONObject subgroup3 = categories.getJSONObject(0);
        cat1 = subgroup3.getString(FSQ_NAME_ATTR);

        if (subgroup3.has(FSQ_PARENTS_ATTR))
        {
          JSONArray parents = subgroup3.getJSONArray(FSQ_PARENTS_ATTR);
          if (parents.length() > 0)
            cat2 = parents.getString(0);
        }
      }
    }

    this.vid = location.getString(FSQ_ID_ATTR);
    this.name = location.getString(FSQ_NAME_ATTR);
    this.x = convertXY[0];
    this.y = convertXY[1];
    this.count = count;
    this.cat1 = cat1;
    this.cat2 = cat2;
  }

  @Override
  public String getId()
  {
    return vid;
  }

  @Override
  public String getName()
  {
    return name;
  }

  @Override
  public int getHereNowCount()
  {
    return count;
  }

  @Override
  public float getX()
  {
    return x;
  }

  @Override
  public float getY()
  {
    return y;
  }

  @Override
  public String getCategory1()
  {
    return cat1;
  }

  @Override
  public String getCategory2()
  {
    return cat2;
  }


  private final String vid;
  private final String name;
  private final float x;
  private final float y;
  private final int count;
  private final String cat1;
  private final String cat2;

  private static String FSQ_LOC_ATTR = "location";
  private static String FSQ_LAT_ATTR = "lat";
  private static String FSQ_LNG_ATTR = "lng";
  private static String FSQ_HERE_NOW_ATTR = "hereNow";
  private static String FSQ_COUNT_ATTR = "count";
  private static String FSQ_ID_ATTR = "id";
  private static String FSQ_NAME_ATTR = "name";
  private static String FSQ_CATEGORIES_ATTR = "categories";
  private static String FSQ_PARENTS_ATTR = "parents";
}
