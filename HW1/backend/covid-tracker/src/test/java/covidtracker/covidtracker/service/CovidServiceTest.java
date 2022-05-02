package covidtracker.covidtracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import covidtracker.covidtracker.cache.Cache;
import covidtracker.covidtracker.model.Cases;
import covidtracker.covidtracker.model.CountryStats;
import covidtracker.covidtracker.model.Deaths;
import covidtracker.covidtracker.model.Tests;
import covidtracker.covidtracker.utils.HttpRequests;

@ExtendWith(MockitoExtension.class)
class CovidServiceTest {

    @Mock( lenient = true)
    private HttpRequests requests;

    @Mock( lenient = true)
    private Cache<String, Object> cache;

    @InjectMocks
    private CovidService covidService;

    private CountryStats stats;

    private JSONArray response;

    private JSONArray responseArray;

    private JSONArray countriesNameResponse;

    @BeforeEach
    private void setUp() throws JSONException {
        stats = new CountryStats("Europe", "Portugal", 10142964, new Cases(-1, -1, 61, -1, 373830, 3791744), 
                    new Deaths(-1, 2185, 3791744), new Tests(4046001, 3791744), "2022-04-25", "2022-04-25T18:15:04+00:00");
        
        JSONObject cases = new JSONObject();
        cases.put("oneMPop", 373830);
        cases.put("total", 3791744);
        cases.put("newCases", -1);
        cases.put("activeCases", -1);
        cases.put("criticalCases", 61);
        cases.put("recovered", -1);

        JSONObject deaths = new JSONObject();
        deaths.put("oneMPop", 2185);
        deaths.put("total", 3791744);
        deaths.put("newDeaths", -1);

        JSONObject tests = new JSONObject();
        tests.put("oneMPop", 4046001);
        tests.put("total", 3791744);

        JSONObject country = new JSONObject();
        country.put("continent", "Europe");
        country.put("name", "Portugal");
        country.put("population", 10142964);
        country.put("day", "2022-04-25");
        country.put("time", "2022-04-25T18:15:04+00:00");
        country.put("cases", cases);
        country.put("deaths", deaths);
        country.put("tests", tests);

        response = new JSONArray();
        response.put(country);

        responseArray = new JSONArray();
        responseArray.put(country);
        responseArray.put(country);

        countriesNameResponse = new JSONArray();
        countriesNameResponse.put("Afghanistan");
        countriesNameResponse.put("Albania");
        countriesNameResponse.put("Algeria");
        countriesNameResponse.put("Andorra");
    }

    @Test
    void whenGetStatsByCountry_InCache() throws IOException, InterruptedException {

        when(cache.get("/stats/portugal")).thenReturn(stats);

        assertEquals(covidService.getStatsByCountry("portugal"), Optional.of(stats));

        verify(cache, times(1)).get("/stats/portugal");
    }

    @Test
    void whenGetStatsByCountry_NotInCache() throws IOException, InterruptedException {

        when(cache.get("/stats/portugal")).thenReturn(null);
        when(requests.doRequest("/statistics?country=portugal")).thenReturn(response);
        when(requests.getCountryStats(response, 0)).thenReturn(stats);

        assertEquals(covidService.getStatsByCountry("portugal"), Optional.of(stats));

        verify(cache, times(1)).get("/stats/portugal");
        verify(requests, times(1)).doRequest("/statistics?country=portugal");
        verify(requests, times(1)).getCountryStats(response, 0);
        verify(cache, times(1)).put("/stats/portugal", stats);
    }

    @Test
    void whenGetStatsByCountry_SomethingWrong() throws IOException, InterruptedException {

        when(cache.get("/stats/portugal")).thenReturn(null);
        when(requests.doRequest("/statistics?country=portugal")).thenThrow(IOException.class);

        assertEquals(covidService.getStatsByCountry("portugal"), Optional.empty());

        verify(cache, times(1)).get("/stats/portugal");
        verify(requests, times(1)).doRequest("/statistics?country=portugal");
    }

    @Test
    void whenGetStatsAllCountries_InCache() throws IOException, InterruptedException {

        when(cache.get("/stats")).thenReturn(Arrays.asList(stats, stats));

        assertEquals(covidService.getStats(), Optional.of(Arrays.asList(stats, stats)));

        verify(cache, times(1)).get("/stats");
    }

    @Test
    void whenGetStatsAllCountries_NotInCache() throws IOException, InterruptedException {

        when(cache.get("/stats")).thenReturn(null);
        when(requests.doRequest("/statistics")).thenReturn(responseArray);
        when(requests.getCountryStats(eq(responseArray), Mockito.anyInt())).thenReturn(stats);

        assertEquals(covidService.getStats(), Optional.of(Arrays.asList(stats, stats)));

        verify(cache, times(1)).get("/stats");
        verify(requests, times(1)).doRequest("/statistics");
        verify(requests, times(responseArray.length())).getCountryStats(eq(responseArray), Mockito.anyInt());
        verify(cache, times(1)).put("/stats", Arrays.asList(stats, stats));
    }

    @Test
    void whenGetStatsAllCountries_SomethingWrong() throws IOException, InterruptedException {

        when(cache.get("/stats")).thenReturn(null);
        when(requests.doRequest("/statistics")).thenThrow(IOException.class);

        assertEquals(covidService.getStats(), Optional.empty());

        verify(cache, times(1)).get("/stats");
        verify(requests, times(1)).doRequest("/statistics");
    }

    @Test
    void whenGetCountryHistory_InCache() throws IOException, InterruptedException {
        
        CountryStats newStats = stats;
        newStats.setTime("2022-04-25T18:30:04+00:00");

        when(cache.get("/history/" + stats.getName())).thenReturn(Arrays.asList(stats, newStats));

        assertEquals(covidService.getHistory(stats.getName()), Optional.of(Arrays.asList(stats, newStats)));

        verify(cache, times(1)).get("/history/" + stats.getName());
    }

    @Test
    void whenGetCountryHistory_NotInCache() throws IOException, InterruptedException {
        
        CountryStats newStats = stats;
        newStats.setTime("2022-04-25T18:30:04+00:00");

        when(cache.get("/history/" + stats.getName())).thenReturn(null);
        when(requests.doRequest("/history?country=" + stats.getName())).thenReturn(responseArray);
        when(requests.getCountryStats(eq(responseArray), Mockito.anyInt())).thenReturn(stats);

        assertEquals(covidService.getHistory(stats.getName()), Optional.of(Arrays.asList(stats, newStats)));

        verify(cache, times(1)).get("/history/" + stats.getName());
        verify(requests, times(1)).doRequest("/history?country=" + stats.getName());
        verify(requests, times(responseArray.length())).getCountryStats(eq(responseArray), Mockito.anyInt());
        verify(cache, times(1)).put("/history/" + stats.getName(), Arrays.asList(stats, stats));
    }

    @Test
    void whenGetCountryHistory_SomethingWrong() throws IOException, InterruptedException {
        
        when(cache.get("/history/" + stats.getName())).thenReturn(null);
        when(requests.doRequest("/history?country=" + stats.getName())).thenThrow(IOException.class);

        assertEquals(covidService.getHistory(stats.getName()), Optional.empty());

        verify(cache, times(1)).get("/history/" + stats.getName());
        verify(requests, times(1)).doRequest("/history?country=" + stats.getName());
    }

    @Test
    void whenGetCountryHistoryByDay_InCache() throws IOException, InterruptedException {
        CountryStats newStats = stats;
        newStats.setTime("2022-04-25T18:30:04+00:00");

        when(cache.get("/history/" + stats.getName() + "/" + stats.getDay())).thenReturn(Arrays.asList(stats, newStats));

        assertEquals(covidService.getHistory(stats.getName(), stats.getDay()), Optional.of(Arrays.asList(stats, newStats)));

        verify(cache, times(1)).get("/history/" + stats.getName() + "/" + stats.getDay());
    }

    @Test
    void whenGetCountryHistoryByDay_NotInCache() throws IOException, InterruptedException {
        CountryStats newStats = stats;
        newStats.setTime("2022-04-25T18:30:04+00:00");

        when(cache.get("/history/" + stats.getName() + "/" + stats.getDay())).thenReturn(null);
        when(requests.doRequest("/history?country=" + stats.getName() +"&day=" + stats.getDay())).thenReturn(responseArray);
        when(requests.getCountryStats(eq(responseArray), Mockito.anyInt())).thenReturn(stats);

        assertEquals(covidService.getHistory(stats.getName(), stats.getDay()), Optional.of(Arrays.asList(stats, stats)));

        verify(cache, times(1)).get("/history/" + stats.getName() + "/" + stats.getDay());
        verify(requests, times(1)).doRequest("/history?country=" + stats.getName() +"&day=" + stats.getDay());
        verify(requests, times(responseArray.length())).getCountryStats(eq(responseArray), Mockito.anyInt());
        verify(cache, times(1)).put("/history/" + stats.getName() + "/" + stats.getDay(), Arrays.asList(stats, stats));
    }


    @Test
    void whenGetCountryHistoryByDay_SomethingWrong() throws IOException, InterruptedException {
        
        when(cache.get("/history/" + stats.getName() + "/" + stats.getDay())).thenReturn(null);
        when(requests.doRequest("/history?country=" + stats.getName() +"&day=" + stats.getDay())).thenThrow(IOException.class);

        assertEquals(covidService.getHistory(stats.getName(), stats.getDay()), Optional.empty());

        verify(cache, times(1)).get("/history/" + stats.getName() + "/" + stats.getDay());
        verify(requests, times(1)).doRequest("/history?country=" + stats.getName() +"&day=" + stats.getDay());
    }

    @Test
    void whenGetAllCountries_InCache() throws IOException, InterruptedException {

        when(cache.get("/countries")).thenReturn(Arrays.asList("Afghanistan", "Albania", "Algeria", "Andorra"));

        assertEquals(covidService.getAllCountries(), Optional.of(Arrays.asList("Afghanistan", "Albania", "Algeria", "Andorra")));

        verify(cache, times(1)).get("/countries");

    }

    @Test
    void whenGetAllCountries_NotInCache() throws IOException, InterruptedException, JSONException {

        when(cache.get("/countries")).thenReturn(null);
        when(requests.doRequest("/countries")).thenReturn(countriesNameResponse);
        for(int i = 0; i < countriesNameResponse.length(); i++) {
            when(requests.getCountryName(countriesNameResponse, i)).thenReturn(countriesNameResponse.getString(i));
        }
        
        assertEquals(covidService.getAllCountries(), Optional.of(Arrays.asList("Afghanistan", "Albania", "Algeria", "Andorra")));

        verify(cache, times(1)).get("/countries");
        verify(requests, times(1)).doRequest("/countries");
        verify(requests, times(countriesNameResponse.length())).getCountryName(eq(countriesNameResponse), Mockito.anyInt());
        verify(cache, times(1)).put("/countries", Arrays.asList("Afghanistan", "Albania", "Algeria", "Andorra"));
    }

    @Test
    void whenGetAllCountries_SomethingWrong() throws IOException, InterruptedException {

        when(cache.get("/countries")).thenReturn(null);
        when(requests.doRequest("/countries")).thenThrow(IOException.class);

        assertEquals(covidService.getAllCountries(), Optional.empty());

        verify(cache, times(1)).get("/countries");
        verify(requests, times(1)).doRequest("/countries");

    }

    @Test
    void whenGetCacheDetails() {

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
