package covidtracker.covidtracker.cache;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.awaitility.Awaitility.await;

import java.time.Duration;
import java.util.concurrent.Callable;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import covidtracker.covidtracker.model.Cases;
import covidtracker.covidtracker.model.CountryStats;
import covidtracker.covidtracker.model.Deaths;
import covidtracker.covidtracker.model.Tests;

public class CacheTest{

    private Cache<String, CountryStats> cache;
    private long timeToLive = 1;
    private long timeInterval = 1;
    private CountryStats stats;

    @BeforeEach
    public void setUp() {
        cache = new Cache<>(timeToLive, timeInterval);
        stats = new CountryStats("Europe", "Portugal", 10143368, new Cases(-1, -1, 61, -1, 366691, 3719485), new Deaths(-1, 2168, 21993), new Tests(4017243, 40748372), "2022-04-20", "2022-04-20T10:15:03+00:00");
    }

    @Test
    public void onConstruction() {
        assertEquals(0, cache.size());
        assertEquals(0, cache.getHitCount());
        assertEquals(0, cache.getMissCount());
        assertEquals(0, cache.getHitRatio());
    }

    @Test
    public void whenPutItem() {
        cache.put("/stats/portugal", stats);
        assertEquals(1, cache.size());
        assertEquals(0, cache.getHitCount());
        assertEquals(0, cache.getMissCount());
        assertEquals(0, cache.getHitRatio());
    } 

    @Test
    public void whenGetExistingItem() {
        cache.put("/stats/portugal", stats);
        CountryStats result = cache.get("/stats/portugal");
        assertEquals(stats, result);
        assertEquals(1, cache.size());
        assertEquals(1, cache.getHitCount());
        assertEquals(0, cache.getMissCount());
        assertEquals(1, cache.getHitRatio());
    }

    @Test
    public void whenGetNonExistingItem() {
        cache.put("/stats/portugal", stats);
        CountryStats result = cache.get("/stats");
        assertEquals(null, result);
        assertEquals(1, cache.size());
        assertEquals(0, cache.getHitCount());
        assertEquals(1, cache.getMissCount());
        assertEquals(0, cache.getHitRatio());
    }

    @Test
    public void whenDeletingExistingItem() {
        cache.put("/stats/portugal", stats);
        int size = cache.size();
        cache.delete("/stats/portugal");
        assertEquals(size, cache.size() + 1);
    }

    @Test
    public void whenDeletingNonExistingItem() {
        cache.put("/stats/portugal", stats);
        int size = cache.size();
        cache.delete("/stats");
        assertEquals(size, cache.size());
    }

    @Test
    public void whenExpired() throws InterruptedException {
        cache.put("/stats/portugal", stats);

        await().atMost(Duration.ofSeconds(3)).until(isExpired());

        assertEquals(0, cache.size());
    }

    private Callable<Boolean> isExpired() {
        return () -> cache.size() == 0;
    }
    
}
