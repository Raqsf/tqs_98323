package covidtracker.covidtracker.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    @Autowired
    private HttpRequests request;

    private Cache<String, Object> cache = new Cache<>(300L);

    public Optional<CountryStats> getStatsByCountry(String country) {
        CountryStats cacheResult = (CountryStats) cache.get("/stats/" + country);

        if(cacheResult != null) {
            return Optional.of(cacheResult);
        }

        try {
            CountryStats result = request.getCountryStats(country);
            cache.put("/stats/" + country, result);
            return Optional.of(result);
        } catch (IOException | InterruptedException e) {
            logger.log(Level.WARNING, "SERVICE: {0}", e.getMessage());

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
            List<CountryStats> result = request.getAllCountryStats();
            cache.put("/stats", result);
            return Optional.of(result);
        } catch (IOException | InterruptedException e) {
            logger.log(Level.WARNING, "SERVICE: {0}", e.getMessage());
            
            // Restore interrupted state...
            Thread.currentThread().interrupt();

            return Optional.empty();
        }
    }

    /* public List<String> existCountry(String country) {
        // se o pais existir, retornar lista vazia
        // se não existir (results: 0), retornar paises parecidos ou mensagem de erro
        return country.equals("") ? Collections.emptyList() : new ArrayList<>();
    } */

    public Optional<List<CountryStats>> getHistory(String country) {
        List<CountryStats> cacheResult = (List<CountryStats>) cache.get("/history/" + country);

        if(cacheResult != null) {
            return Optional.of(cacheResult);
        }
        
        try {
            List<CountryStats> result = request.getCountryHistory(country);
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
            List<CountryStats> result = request.getCountryHistoryByDay(country, date);
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
            List<String> result = request.getCountries();
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

}