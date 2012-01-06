package org.sidl.herenow.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a set of client IDs and secret keys from a list of of comma-separated values.
 */
public class ClientKeysFile
{
  public ClientKeysFile(Iterable<String> input)
  {
    this.keyMap = new HashMap<String, String>();
    for (String s : input)
    {
      String[] clientKeyPair = s.split(",");
      if (clientKeyPair.length > 2)
      {
        throw new IllegalArgumentException("Bad values: " + s);
      }
      this.keyMap.put(clientKeyPair[0], clientKeyPair[1]);
    }
  }
  
  /** @return a new copy of the backing key store. Each entry is a client ID and its secret key. */
  public Map<String,String> getClientKeys()
  {
    return new HashMap<String, String>(keyMap);
  }
  
  /** @return the number of keys */
  public int size()
  {
    return keyMap.size();
  }
  
  /** @return true if the backing store is empty */
  public boolean isEmpty()
  {
    return keyMap.isEmpty();
  }
  
  private Map<String,String> keyMap;
}
