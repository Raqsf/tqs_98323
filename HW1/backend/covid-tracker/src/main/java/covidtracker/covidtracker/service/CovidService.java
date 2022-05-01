package covidtracker.covidtracker.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import covidtracker.covidtracker.cache.Cache;
import covidtracker.covidtracker.model.CountryStats;
import covidtracker.covidtracker.utils.HttpRequests;

/**
 * CovidService
 */
@Service
public class CovidService {

    private static Logger logger = Logger.getLogger(CovidService.class.getName());

    public static final String BASE_URL = "https://covid-193.p.rapidapi.com";

    private String charset = "UTF-8";

    @Autowired
    private HttpRequests request;

    private Cache<String, Object> cache = new Cache<>(300L);

    public Optional<CountryStats> getStatsByCountry(String country) {
        CountryStats cacheResult = (CountryStats) cache.get("/stats/" + country);

        if(cacheResult != null) {
            return Optional.of(cacheResult);
        }

        try {
            CountryStats result = getCountryStats(country);
            cache.put("/stats/" + country, result);
            return Optional.of(result);
        } catch (IOException | InterruptedException e) {
            logger.log(Level.WARNING, "SERVICE: {}", e.getMessage());

            // Restore interrupted state...
            Thread.currentThread().interrupt();

            return Optional.empty();
        }
    }

    public Optional<List<CountryStats>> getStats() {
        List<CountryStats> cacheResult = (List<CountryStats>) cache.get("/stats");

        if(cacheResult != null) {
            return Optional.of(cacheResult);
        }

        try {
            List<CountryStats> result = getAllCountryStats();
            cache.put("/stats", result);
            return Optional.of(result);
        } catch (IOException | InterruptedException e) {
            logger.log(Level.WARNING, "SERVICE: {0}", e.getMessage());
            
            // Restore interrupted state...
            Thread.currentThread().interrupt();

            return Optional.empty();
        }
    }
    
    public Optional<List<CountryStats>> getHistory(String country) {
        List<CountryStats> cacheResult = (List<CountryStats>) cache.get("/history/" + country);

        if(cacheResult != null) {
            return Optional.of(cacheResult);
        }
        
        try {
            List<CountryStats> result = getCountryHistory(country);
            cache.put("/history/" + country, result);
            return Optional.of(result);
        } catch (IOException | InterruptedException e) {
            logger.log(Level.WARNING, "SERVICE: {0}", e.getMessage());
            
            // Restore interrupted state...
            Thread.currentThread().interrupt();

            return Optional.empty();
        }
    }

    public Optional<List<CountryStats>> getHistory(String country, String date) {
        List<CountryStats> cacheResult = (List<CountryStats>) cache.get("/history/" + country + "/" + date);

        if(cacheResult != null) {
            return Optional.of(cacheResult);
        }

        try {
            List<CountryStats> result = getCountryHistoryByDay(country, date);
            cache.put("/history/" + country + "/" + date, result);
            return Optional.of(result);
        } catch (IOException | InterruptedException e) {
            logger.log(Level.WARNING, "SERVICE: {0}", e.getMessage());
            
            // Restore interrupted state...
            Thread.currentThread().interrupt();

            return Optional.empty();
        }
    }

    public Optional<List<String>> getAllCountries() {
        List<String> cacheResult = (List<String>) cache.get("/countries");

        if(cacheResult != null) {
            return Optional.of(cacheResult);
        }

        try {
            List<String> result = getCountries();
            cache.put("/countries", result);
            return Optional.of(result);
        } catch (IOException | InterruptedException e) {
            logger.log(Level.WARNING, "SERVICE: {0}", e.getMessage());
            
            // Restore interrupted state...
            Thread.currentThread().interrupt();

            return Optional.empty();
        }
    }

    public Map<String, Object> getCacheDetails() {
        logger.log(Level.INFO, "CACHE DETAILS");
        
        Map<String, Object> details = new HashMap<>();
        details.put("hits", cache.getHitCount());
        details.put("misses", cache.getMissCount());
        details.put("size", cache.size());
        details.put("requests", cache.getRequestCount());
        details.put("hitRatio", cache.getHitRatio());

        return details;
    }


    CountryStats getCountryStats(String country) throws IOException, InterruptedException {
        logger.log(Level.INFO, "Get {0}`s statistics", country);

        String query = String.format("/statistics?country=%s", URLEncoder.encode(country, charset));

        JSONArray responseArray = request.doRequest(query);

        return request.getCountryStats(responseArray, 0);
    }

    List<CountryStats> getAllCountryStats() throws IOException, InterruptedException {
        logger.log(Level.INFO, "Get all statistics");

        String query = "/statistics";

        JSONArray responseArray = request.doRequest(query);

        List<CountryStats> result = new ArrayList<>();

        for(int i = 0; i < responseArray.length(); i++) {
            result.add(request.getCountryStats(responseArray, i));
        }

        return result;
    }

    List<CountryStats> getCountryHistory(String country) throws IOException, InterruptedException {
        logger.log(Level.INFO, "Get {0}`s history", country);

        String query = String.format("/history?country=%s", URLEncoder.encode(country, charset));

        JSONArray responseArray = request.doRequest(query);

        List<CountryStats> result = new ArrayList<>();

        for(int i = 0; i < responseArray.length(); i++) {
            result.add(request.getCountryStats(responseArray, i));
        }

        return result;
        
    }

    List<CountryStats> getCountryHistoryByDay(String country, String date) throws IOException, InterruptedException {
        logger.log(Level.INFO, String.format("Get %s`s history on %s", country, date));
        
        String query = String.format("/history?country=%s&day=%s", URLEncoder.encode(country, charset), URLEncoder.encode(date, charset));

        JSONArray responseArray = request.doRequest(query);

        List<CountryStats> result = new ArrayList<>();

        for(int i = 0; i < responseArray.length(); i++) {
            result.add(request.getCountryStats(responseArray, i));
        }

        return result;
    }

    List<String> getCountries() throws IOException, InterruptedException {
        logger.log(Level.INFO, "Get countries");

        String query = "/countries";

        JSONArray responseArray = request.doRequest(query);

        List<String> result = new ArrayList<>();

        for(int i = 0; i < responseArray.length(); i++) {
            result.add(request.getCountryName(responseArray, i));
        }

        return result;
    }

}