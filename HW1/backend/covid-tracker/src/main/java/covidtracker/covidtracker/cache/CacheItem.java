package covidtracker.covidtracker.cache;

public class CacheItem<K, V> {
    private K key;
    private V value;
    private int hitCount = 0;
    private long lastAccessed;

    public CacheItem(K key, V value) {
        this.value = value;
        this.key = key;
        this.lastAccessed = System.currentTimeMillis();
    }
    
    public void incrementHitCount() {
        this.hitCount++;
    }


    public K getKey() {
        return this.key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return this.value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public int getHitCount() {
        return this.hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }


    public long getLastAccessed() {
        return this.lastAccessed;
    }

    public void setLastAccessed(long lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

}
