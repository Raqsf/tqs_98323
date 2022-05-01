package covidtracker.covidtracker.utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import covidtracker.covidtracker.model.Cases;
import covidtracker.covidtracker.model.CountryStats;
import covidtracker.covidtracker.model.Deaths;
import covidtracker.covidtracker.model.Tests;

@Component
public class HttpRequests {

    private static Logger logger = Logger.getLogger(HttpRequests.class.getName());

    private static final String BASE_URL = "https://covid-193.p.rapidapi.com";

    private static final String API_KEY = "f90020ba9dmsh31ec284026a13edp109b7djsn4ff85c39b60d";
    

    public JSONArray doRequest(String uri) throws InterruptedException, IOException {

        logger.log(Level.INFO, "Requesting to API ...");

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + uri))
            .header("X-RapidAPI-Host", "covid-193.p.rapidapi.com")
            .header("X-RapidAPI-Key", API_KEY)
            .method("GET", HttpRequest.BodyPublishers.noBody())
            .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return new JSONObject(response.body()).getJSONArray("response");

        } catch (InterruptedException | IOException e) {
            logger.log(Level.WARNING, e.getMessage());

            throw e;

        }

    }

    public String getCountryName(JSONArray responseArray, int index) {
        return responseArray.getString(index);
    }

    public CountryStats getCountryStats(JSONArray responseArray, int index) {
        
        JSONObject responseJSON = responseArray.getJSONObject(index);

        String continent = responseJSON.isNull("continent") ? "" : responseJSON.getString("continent");
        String countryName = responseJSON.getString("country");
        int population = responseJSON.isNull("population") ? -1 : responseJSON.getInt("population");
        String day = responseJSON.getString("day");
        String time = responseJSON.getString("time");

        JSONObject cases = responseJSON.getJSONObject("cases");
        int newCases = cases.isNull("new") ? -1 : Integer.parseInt(cases.getString("new").replace("+", ""));
        int activeCases = cases.isNull("active") ? -1 : cases.getInt("active");
        int criticalCases = cases.isNull("critical") ? -1 : cases.getInt("critical");
        int recoveredCases = cases.isNull("recovered") ? -1 : cases.getInt("recovered");
        double oneMPopCases = cases.isNull("1M_pop") ? -1 : Double.parseDouble(cases.getString("1M_pop"));
        int totalCases = cases.isNull("total") ? -1 : cases.getInt("total");
        JSONObject deaths = responseJSON.getJSONObject("deaths");
        int newDeaths = deaths.isNull("new") ? -1 : Integer.parseInt(deaths.getString("new").replace("+", ""));
        double oneMPopDeaths = deaths.isNull("1M_pop") ? -1 : Double.parseDouble(deaths.getString("1M_pop"));
        int totalDeaths = deaths.isNull("total") ? -1 : cases.getInt("total");
        JSONObject tests = responseJSON.getJSONObject("tests");
        double oneMPopTests = tests.isNull("1M_pop") ? -1 : Double.parseDouble(tests.getString("1M_pop"));
        int totalTests = tests.isNull("total") ? -1 : cases.getInt("total"); 

        Cases casesRes = new Cases(newCases, activeCases, criticalCases, recoveredCases, oneMPopCases, totalCases);
        Deaths deathsRes = new Deaths(newDeaths, oneMPopDeaths, totalDeaths);
        Tests testsRes = new Tests(oneMPopTests,totalTests);

        return new CountryStats(continent, countryName, population, casesRes, deathsRes, testsRes, day, time);
    }
    
}
