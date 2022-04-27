package covidtracker.covidtracker.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

@Component
public class Cache<K, V> {
    private Map<K, CacheItem<V>> map;
    private int hitCount;
    private int missCount;
    private int requestCount;
    private final long timeToLive;

    private static Logger logger = Logger.getLogger(Cache.class.getName());

    public Cache(long timeToLive, long timerInterval) {
        this.timeToLive = timeToLive * 1000;
        this.hitCount = 0;
        this.missCount = 0;
        map = new HashMap<>();

        if (timeToLive > 0 && timerInterval > 0) {
            
            Runnable runnable = () -> {
                while (true) {
                    try {

                        Thread.sleep(timerInterval * 1000);
                    } catch (InterruptedException ex) {
                        logger.log(Level.WARNING, "Interrupted!", ex);
                        // Restore interrupted state...
                        Thread.currentThread().interrupt();
                    }
                    update();
                }
            };

			Thread t = new Thread(runnable);

			t.setDaemon(true);
			t.start();
        }
    }

    public void put(K key, V value) {
        synchronized (map) {
            logger.log(Level.INFO, "Putting new item in cache");
            map.put(key, new CacheItem<>(value));
        }
    }

    public int size() {
        synchronized (map) {
            return map.size();
        }
    }

    public V get(K key) {
        synchronized (map) {
            requestCount++;
            if(!map.containsKey(key)) {
                missCount++;
                return null;
            }

            CacheItem<V> node = map.get(key);
            hitCount++;
            node.setLastAccessed(System.currentTimeMillis());
            return node.getValue();
        }
    }

    public void delete(K key) {
        synchronized (map) {
            map.remove(key);
        }
    }

    public int getHitCount() {
        synchronized (map) {
            return hitCount;
        }
    }

    public int getMissCount() {
        synchronized (map) {
            return missCount;
        }
    }

    public int getRequestCount() {
        synchronized (map) {
            return requestCount;
        }
    }

    public double getHitRatio() {
        synchronized (map) {
            return hitCount + missCount != 0 ? (double) hitCount/(hitCount + missCount) : 0;
        }
    }

    public void update() {
        long now = System.currentTimeMillis();
		ArrayList<K> deleteKey = null;
 
		synchronized (map) {
			deleteKey = new ArrayList<>();
			K key = null;
			CacheItem<V> cacheItem = null;
 
			for(Map.Entry<K,CacheItem<V>> entry : map.entrySet()) {
				key = entry.getKey();
				cacheItem= map.get(key);
 
				if (cacheItem != null && (now > (timeToLive + cacheItem.getLastAccessed()))) {
					deleteKey.add(key);
				}
			}
		}
 
		for (K key : deleteKey) {
			synchronized (map) {
				map.remove(key);
			}
 
			// yield(): A hint to the scheduler that the current thread is willing to
			// yield its current use of a processor.
			// The scheduler is free to ignore this hint.
			Thread.yield();
		}
    }
}
