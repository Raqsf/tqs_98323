package covidtracker.covidtracker.cache;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.awaitility.Awaitility.await;

import java.time.Duration;
import java.util.concurrent.Callable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import covidtracker.covidtracker.model.Cases;
import covidtracker.covidtracker.model.CountryStats;
import covidtracker.covidtracker.model.Deaths;
import covidtracker.covidtracker.model.Tests;

class CacheTest{

    private Cache<String, CountryStats> cache;
    private long timeToLive = 1;
    private CountryStats stats;

    @BeforeEach
    void setUp() {
        cache = new Cache<>(timeToLive);
        stats = new CountryStats("Europe", "Portugal", 10143368, new Cases(-1, -1, 61, -1, 366691, 3719485), new Deaths(-1, 2168, 21993), new Tests(4017243, 40748372), "2022-04-20", "2022-04-20T10:15:03+00:00");
    }

    @Test
    void onConstruction() {
        assertEquals(0, cache.size());
        assertEquals(0, cache.getHitCount());
        assertEquals(0, cache.getMissCount());
        assertEquals(0, cache.getRequestCount());
        assertEquals(0, cache.getHitRatio());
    }

    @Test
    void whenPutItem() {
        cache.put("/stats/portugal", stats);
        assertEquals(1, cache.size());
        assertEquals(0, cache.getHitCount());
        assertEquals(0, cache.getMissCount());
        assertEquals(0, cache.getRequestCount());
        assertEquals(0, cache.getHitRatio());
    } 

    @Test
    void whenGetExistingItem() {
        cache.put("/stats/portugal", stats);
        CountryStats result = cache.get("/stats/portugal");
        assertEquals(stats, result);
        assertEquals(1, cache.size());
        assertEquals(1, cache.getHitCount());
        assertEquals(0, cache.getMissCount());
        assertEquals(1, cache.getRequestCount());
        assertEquals(1, cache.getHitRatio());
    }

    @Test
    void whenGetNonExistingItem() {
        cache.put("/stats/portugal", stats);
        CountryStats result = cache.get("/stats");
        assertEquals(null, result);
        assertEquals(1, cache.size());
        assertEquals(0, cache.getHitCount());
        assertEquals(1, cache.getMissCount());
        assertEquals(1, cache.getRequestCount());
        assertEquals(0, cache.getHitRatio());
    }

    @Test
    void whenDeletingExistingItem() {
        cache.put("/stats/portugal", stats);
        int size = cache.size();
        cache.delete("/stats/portugal");
        assertEquals(size, cache.size() + 1);
    }

    @Test
    void whenDeletingNonExistingItem() {
        cache.put("/stats/portugal", stats);
        int size = cache.size();
        cache.delete("/stats");
        assertEquals(size, cache.size());
    }

    @Test
    void whenExpired() throws InterruptedException {
        cache.put("/stats/portugal", stats);

        await().atMost(Duration.ofSeconds(3)).until(isExpired());

        assertEquals(0, cache.size());
    }

    private Callable<Boolean> isExpired() {
        return () -> cache.size() == 0;
    }

    @Test
    void whenTimeToLiveLesserThanZero() {
        assertThrows(IllegalArgumentException.class, () -> new Cache<>(0L));
    }
    
}
