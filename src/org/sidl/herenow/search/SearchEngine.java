package org.sidl.herenow.search;


import org.sidl.herenow.VenueResult;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

/**
 * Abstracts the search interface using a synchronous interface. Engines s
 */
public interface SearchEngine
{
  /**
   * Register a queue to have multiple, asynchronous results delivered here.
   * Results to this queue may be delivered from many threads.
   * @param queue a thread-safe queue
   */
  public void registerSearchResultQueue(BlockingQueue<VenueResult> queue);

  /**
   * De-register a queue to stop results from being delivered. The last queue to
   * de-register disables the engine.
   */
  public void deregisterSearchResultQueue(BlockingQueue<VenueResult> queue);

  /**
   * Call this method to search for a location, and have it delivered to the result queue
   * as some later point. If no queue is registered at the time of completion, then the result is
   * dropped on the floor and all subsequent processing stops.
   * @param method access to external data source
   * @param location the centroid to search for
   */
  public void searchByCentroid(SearchMethod method, String location);

  /** @return number of total queries made */
  public long searchesMade();

  /** @return number of queries that failed */
  public long searchesFailed();
  
  /** log stats to the given logger */
  public void logStats(Logger logger);

}
