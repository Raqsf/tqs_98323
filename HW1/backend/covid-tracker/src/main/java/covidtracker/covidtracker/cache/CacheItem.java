package covidtracker.covidtracker.cache;

public class CacheItem<V> {
    private V value;
    private long lastAccessed;

    public CacheItem(V value) {
        this.value = value;
        this.lastAccessed = System.currentTimeMillis();
    }

    public V getValue() {
        return this.value;
    }

    public long getLastAccessed() {
        return this.lastAccessed;
    }

    public void setLastAccessed(long lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

}
