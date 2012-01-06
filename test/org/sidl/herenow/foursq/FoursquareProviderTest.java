package org.sidl.herenow.foursq;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import static junit.framework.Assert.assertNull;

/**
 * 
 */
public class FoursquareProviderTest
{
  
  public static void main(String... args) throws IOException, JSONException
  {
    //object.getJSONObject("response");
    FileReader reader = new FileReader("JSON_temp.json");
    JSONTokener tokener = new JSONTokener(reader);
    JSONObject object = new JSONObject(tokener);
    reader.close();
    
    //System.out.println(object.toString(2));
    JSONArray venues = object.getJSONObject("response").getJSONArray("venues");
    //for (int i = 0; i < venues.length(); i++)
    //  System.out.print(venues.getJSONObject(i).getJSONObject("hereNow").getInt("count") + ", ");
    //'{"response":{"venues":[{"id":"4bd9c1072e6f0f47bfd20b08","location":{"distance":72,"postalCode":"10461","address":"1320 Hutchinson River Pkwy","state":"NY","lng":-73.83803844451904,"lat":40.83710949725479,"city":"Bronx","country":"USA"},"verified":true,"stats":{"checkinsCount":523,"usersCount":287,"tipCount":10},"name":"Dunkin' Donuts","categories":[{"id":"4bf58dd8d48988d148941735","icon":{"sizes":[32,44,64,88,256],"prefix":"https://foursquare.com/img/categories/food/bagels_","name":".png"},"name":"Donut Shop","primary":true,"shortName":"Donuts","pluralName":"Donut Shops"}],"hereNow":{"count":0},"contact":{"twitter":"dunkindonuts","phone":"7188635225","formattedPhone":"(718) 863-5225"},"url":"http://www.dunkindonuts.com/"},'
    Map m = new TreeMap();
    m.put("id", "4bd9c1072e6f0f47bfd20b08");
    JSONObject mobj = new JSONObject(m);
    assertNull(mobj.getString("id"), mobj.getString("id"));
    //m.put("location", )
  }
}
