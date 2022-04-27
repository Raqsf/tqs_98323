package covidtracker.covidtracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import covidtracker.covidtracker.cache.Cache;
import covidtracker.covidtracker.model.Cases;
import covidtracker.covidtracker.model.CountryStats;
import covidtracker.covidtracker.model.Deaths;
import covidtracker.covidtracker.model.Tests;
import covidtracker.covidtracker.utils.HttpRequests;

@ExtendWith(MockitoExtension.class)
public class CovidServiceTest {

    @Mock( lenient = true)
    private HttpRequests requests;

    @Mock( lenient = true)
    private Cache<String, Object> cache;

    @InjectMocks
    private CovidService covidService;

    private CountryStats stats;

    @BeforeEach
    private void setUp() {
        stats = new CountryStats("Europe", "Portugal", 10142964, new Cases(-1, -1, 61, -1, 373830, 3791744), 
                    new Deaths(-1, 2185, 3791744), new Tests(4046001, 3791744), "2022-04-25", "2022-04-25T18:15:04+00:00");
        /* {
            "continent": "Europe",
            "name": "Portugal",
            "population": 10142964,
            "cases": {
              "oneMPop": 373830,
              "total": 3791744,
              "newCases": -1,
              "activeCases": -1,
              "criticalCases": 61,
              "recovered": -1
            },
            "deaths": {
              "oneMPop": 2185,
              "total": 3791744,
              "newDeaths": -1
            },
            "tests": {
              "oneMPop": 4046001,
              "total": 3791744
            },
            "day": "2022-04-25",
            "time": "2022-04-25T18:15:04+00:00"
          } */
    }

    @Test
    public void whenGetStatsByCountry_InCache() throws IOException, InterruptedException {

        when(cache.get("/stats/portugal")).thenReturn(stats);
        //when(requests.getCountryStats("portugal")).thenReturn(stats);

        assertEquals(covidService.getStatsByCountry("portugal"), Optional.of(stats));

        verify(cache, times(1)).get("/stats/portugal");
        // verify(requests, times(1)).doRequest(Mockito.any());
    }

    @Test
    public void whenGetStatsByCountry_NotInCache() throws IOException, InterruptedException {

        when(cache.get("/stats/portugal")).thenReturn(null);
        when(requests.getCountryStats("portugal")).thenReturn(stats);

        assertEquals(covidService.getStatsByCountry("portugal"), Optional.of(stats));

        verify(cache, times(1)).get("/stats/portugal");
        verify(requests, times(1)).getCountryStats("portugal");
        verify(cache, times(1)).put("/stats/portugal", stats);
        // verify(requests, times(1)).doRequest(Mockito.any());
    }

    @Test
    public void whenGetStatsByCountry_SomethingWrong() throws IOException, InterruptedException {

        when(requests.getCountryStats("portugal")).thenThrow(IOException.class);

        //assertThrows(IOException.class, () -> covidService.getStatsByCountry("portugal"));
        assertEquals(covidService.getStatsByCountry("portugal"), Optional.empty());

        verify(requests, times(1)).getCountryStats("portugal");
        // verify(requests, times(1)).doRequest(Mockito.any());
    }

    @Test
    public void whenGetStatsAllCountries_InCache() throws IOException, InterruptedException {

        when(cache.get("/stats")).thenReturn(Arrays.asList(stats, stats));

        assertEquals(covidService.getStats(), Optional.of(Arrays.asList(stats, stats)));

        verify(cache, times(1)).get("/stats");
    }

    @Test
    public void whenGetStatsAllCountries_NotInCache() throws IOException, InterruptedException {

        when(cache.get("/stats")).thenReturn(null);
        when(requests.getAllCountryStats()).thenReturn(Arrays.asList(stats, stats));

        assertEquals(covidService.getStats(), Optional.of(Arrays.asList(stats, stats)));

        verify(cache, times(1)).get("/stats");
        verify(requests, times(1)).getAllCountryStats();
        verify(cache, times(1)).put("/stats", Arrays.asList(stats, stats));
    }

    @Test
    public void whenGetStatsAllCountries_SomethingWrong() throws IOException, InterruptedException {

        when(requests.getAllCountryStats()).thenThrow(IOException.class);

        assertEquals(covidService.getStats(), Optional.empty());

        verify(requests, times(1)).getAllCountryStats();
    }

    @Test
    public void whenGetCountryHistory_InCache() throws IOException, InterruptedException {
        
        CountryStats newStats = stats;
        newStats.setTime("2022-04-25T18:30:04+00:00");

        when(cache.get("/history/" + stats.getName())).thenReturn(Arrays.asList(stats, newStats));

        assertEquals(covidService.getHistory(stats.getName()), Optional.of(Arrays.asList(stats, newStats)));

        verify(cache, times(1)).get("/history/" + stats.getName());
    }

    @Test
    public void whenGetCountryHistory_NotInCache() throws IOException, InterruptedException {
        
        CountryStats newStats = stats;
        newStats.setTime("2022-04-25T18:30:04+00:00");

        when(cache.get("/history/" + stats.getName())).thenReturn(null);
        when(requests.getCountryHistory(stats.getName())).thenReturn(Arrays.asList(stats, newStats));

        assertEquals(covidService.getHistory(stats.getName()), Optional.of(Arrays.asList(stats, newStats)));

        verify(cache, times(1)).get("/history/" + stats.getName());
        verify(requests, times(1)).getCountryHistory(stats.getName());
        verify(cache, times(1)).put("/history/" + stats.getName(), Arrays.asList(stats, newStats));
    }

    @Test
    public void whenGetCountryHistory_SomethingWrong() throws IOException, InterruptedException {
        
        when(requests.getCountryHistory(stats.getName())).thenThrow(IOException.class);

        assertEquals(covidService.getHistory(stats.getName()), Optional.empty());

        verify(requests, times(1)).getCountryHistory(stats.getName());
    }

    @Test
    public void whenGetCountryHistoryByDay_InCache() throws IOException, InterruptedException {
        CountryStats newStats = stats;
        newStats.setTime("2022-04-25T18:30:04+00:00");

        when(cache.get("/history/" + stats.getName() + "/" + stats.getDay())).thenReturn(Arrays.asList(stats, newStats));

        assertEquals(covidService.getHistory(stats.getName(), stats.getDay()), Optional.of(Arrays.asList(stats, newStats)));

        verify(cache, times(1)).get("/history/" + stats.getName() + "/" + stats.getDay());
    }

    @Test
    public void whenGetCountryHistoryByDay_NotInCache() throws IOException, InterruptedException {
        CountryStats newStats = stats;
        newStats.setTime("2022-04-25T18:30:04+00:00");

        when(cache.get("/history/" + stats.getName() + "/" + stats.getDay())).thenReturn(null);
        when(requests.getCountryHistoryByDay(stats.getName(), stats.getDay())).thenReturn(Arrays.asList(stats, newStats));

        assertEquals(covidService.getHistory(stats.getName(), stats.getDay()), Optional.of(Arrays.asList(stats, newStats)));

        verify(cache, times(1)).get("/history/" + stats.getName() + "/" + stats.getDay());
        verify(requests, times(1)).getCountryHistoryByDay(stats.getName(), stats.getDay());
        verify(cache, times(1)).put("/history/" + stats.getName() + "/" + stats.getDay(), Arrays.asList(stats, newStats));
    }


    @Test
    public void whenGetCountryHistoryByDay_SomethingWrong() throws IOException, InterruptedException {
        
        when(requests.getCountryHistoryByDay(stats.getName(), stats.getDay())).thenThrow(IOException.class);

        assertEquals(covidService.getHistory(stats.getName(), stats.getDay()), Optional.empty());

        verify(requests, times(1)).getCountryHistoryByDay(stats.getName(), stats.getDay());
    }

    @Test
    public void whenGetAllCountries_InCache() throws IOException, InterruptedException {

        when(cache.get("/countries")).thenReturn(Arrays.asList("Afghanistan", "Albania", "Algeria", "Andorra"));

        assertEquals(covidService.getAllCountries(), Optional.of(Arrays.asList("Afghanistan", "Albania", "Algeria", "Andorra")));

        verify(cache, times(1)).get("/countries");

    }

    @Test
    public void whenGetAllCountries_NotInCache() throws IOException, InterruptedException {

        when(cache.get("/countires")).thenReturn(null);
        when(requests.getCountries()).thenReturn(Arrays.asList("Afghanistan", "Albania", "Algeria", "Andorra"));

        assertEquals(covidService.getAllCountries(), Optional.of(Arrays.asList("Afghanistan", "Albania", "Algeria", "Andorra")));

        verify(cache, times(1)).get("/countries");
        verify(requests, times(1)).getCountries();
        verify(cache, times(1)).put("/countries", Arrays.asList("Afghanistan", "Albania", "Algeria", "Andorra"));
    }

    @Test
    public void whenGetAllCountries_SomethingWrong() throws IOException, InterruptedException {

        when(requests.getCountries()).thenThrow(IOException.class);

        assertEquals(covidService.getAllCountries(), Optional.empty());

        verify(requests, times(1)).getCountries();

    }

    @Test
    public void whenGetCacheDetails() {

        when(cache.getHitCount()).thenReturn(1);
        when(cache.getMissCount()).thenReturn(1);
        when(cache.getRequestCount()).thenReturn(2);
        when(cache.size()).thenReturn(1);
        when(cache.getHitRatio()).thenReturn(0.5);

        Map<String, Object> res = new HashMap<>();
        res.put("hits", 1);
        res.put("misses", 1);
        res.put("requests", 2);
        res.put("size", 1);
        res.put("hitRatio", 0.5);

        assertEquals(covidService.getCacheDetails(), res);

        verify(cache, times(1)).getHitCount();
        verify(cache, times(1)).getMissCount();
        verify(cache, times(1)).getRequestCount();
        verify(cache, times(1)).size();
        verify(cache, times(1)).getHitRatio();

    }

}
