package org.sidl.herenow.search;

import org.sidl.herenow.VenueResult;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implements access searching using a synchronous interface.
 *
 * This object is thread-safe.
 */
public class DefaultSearchEngine implements SearchEngine
{
  private ThreadPoolExecutor executor;

  public DefaultSearchEngine()
  {
    this.executor = new ThreadPoolExecutor(1, 10, 60, TimeUnit.SECONDS, 
        new LinkedBlockingQueue<Runnable>());
  }

  @Override
  public synchronized void registerSearchResultQueue(BlockingQueue<VenueResult> queue)
  {
    responseQueues.add(queue);
  }

  @Override
  public synchronized void deregisterSearchResultQueue(BlockingQueue<VenueResult> queue)
  {
    if (responseQueues.remove(queue))
    {
       if (responseQueues.isEmpty())
       {
         executor.shutdownNow();
       }
    }
  }

  @Override
  public synchronized void searchByCentroid(SearchMethod method, String location)
  {    
    executor.execute(new SearchTask(this, method, location));
  }

  @Override
  public long searchesMade()
  {
    return queryCount.get();
  }

  @Override
  public long searchesFailed()
  {
    return queryFailed.get();
  }

  @Override
  public void logStats(Logger logger)
  {
    logger.log(Level.INFO, String.format("Engine stats: %d searches made (%d failed, %d dropped)",
        searchesMade(), searchesFailed(), droppedResults.get()));
  }

  //package-protected

  long incrementSearchesAndGet()
  {
    return queryCount.incrementAndGet();
  }
  
  long incrementFailedSearchesAndGet()
  {
    return queryFailed.incrementAndGet();
  }
  
  void postResult(VenueResult result)
  {
    if (result.isError())
      incrementFailedSearchesAndGet();

    Object[] queues = responseQueues.toArray();
    if (queues.length != 0)
    {
      for (Object o : queues)
      {
        try
        {
          BlockingQueue<VenueResult> q = ((BlockingQueue<VenueResult>)o);
          if (!q.offer(result, 5, TimeUnit.MINUTES))
          {
            long dropped = droppedResults.incrementAndGet();
            LOG.log(Level.WARNING, "Dropping result on the floor: " + result + " count: " + dropped);
          }
        }
        catch (InterruptedException ie)
        {
          LOG.log(Level.WARNING, "Queue offer interrupted" , ie);
        }
      }
    }
  }


  private AtomicLong queryCount = new AtomicLong();

  private AtomicLong queryFailed = new AtomicLong();
  
  private AtomicLong droppedResults = new AtomicLong();

  private Set<BlockingQueue<VenueResult>> responseQueues =
      new CopyOnWriteArraySet<BlockingQueue<VenueResult>>();

  private static final Logger LOG = Logger.getLogger(DefaultSearchEngine.class.getName());
}