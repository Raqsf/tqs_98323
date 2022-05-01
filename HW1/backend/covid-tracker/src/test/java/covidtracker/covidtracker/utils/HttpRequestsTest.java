package covidtracker.covidtracker.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import covidtracker.covidtracker.model.Cases;
import covidtracker.covidtracker.model.CountryStats;
import covidtracker.covidtracker.model.Deaths;
import covidtracker.covidtracker.model.Tests;

class HttpRequestsTest {

    private HttpRequests httpRequests;

    @BeforeEach
    public void setUp() throws Exception {
        
        httpRequests = new HttpRequests();
        
    }

    @Test
    void whenDoGoodRequest() throws InterruptedException, IOException {
        assertEquals(JSONArray.class, httpRequests.doRequest("/statistics").getClass());
        
    }

    @Test
    void whenDoBadRequest() {
        // api doesn't have /stats endpoint
        assertThrows(JSONException.class, () -> httpRequests.doRequest("/stats"));
    }

    @Test
    void whenGetCountryName() {
        JSONArray array = new JSONArray();
        array.put("country1");
        array.put("country2");

        assertEquals("country1", httpRequests.getCountryName(array, 0));
        assertEquals(String.class, httpRequests.getCountryName(array, 1).getClass());
        
    }
    
    @Test
    void whenGetCountryStats() throws JSONException {
        JSONObject cases = new JSONObject();
        cases.put("1M_pop", 373830);
        cases.put("total", 3791744);
        cases.put("new", -1);
        cases.put("active", -1);
        cases.put("critical", 61);
        cases.put("recovered", -1);

        JSONObject deaths = new JSONObject();
        deaths.put("1M_pop", 2185);
        deaths.put("total", 3791744);
        deaths.put("new", -1);

        JSONObject tests = new JSONObject();
        tests.put("1M_pop", 4046001);
        tests.put("total", 3791744);

        JSONObject country = new JSONObject();
        country.put("continent", "Europe");
        country.put("country", "Portugal");
        country.put("population", 10142964);
        country.put("day", "2022-04-25");
        country.put("time", "2022-04-25T18:15:04+00:00");
        country.put("cases", cases);
        country.put("deaths", deaths);
        country.put("tests", tests);

        JSONArray response = new JSONArray();
        response.put(country);

        assertEquals(CountryStats.class, httpRequests.getCountryStats(response, 0).getClass());
        
        CountryStats result = httpRequests.getCountryStats(response, 0);
        Cases resultCases = result.getCases();
        Deaths resultDeaths = result.getDeaths();
        Tests resultTests = result.getTests();
        
        assertEquals("Portugal", result.getName());
        assertEquals("Europe", result.getContinent());
        assertEquals(10142964, result.getPopulation());
        assertEquals(-1, resultCases.getNewCases());
        assertEquals(-1, resultCases.getActiveCases());
        assertEquals(61, resultCases.getCriticalCases());
        assertEquals(-1, resultCases.getRecovered());
        assertEquals(373830, resultCases.getOneMPop());
        assertEquals(3791744, resultCases.getTotal());
        assertEquals(-1, resultDeaths.getNewDeaths());
        assertEquals(2185, resultDeaths.getOneMPop());
        assertEquals(3791744, resultDeaths.getTotal());
        assertEquals(4046001, resultTests.getOneMPop());
        assertEquals(3791744, resultTests.getTotal());
    }
}
