package covidtracker.covidtracker.service;

import java.io.IOException;
import java.util.List;
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

    @Autowired
    private Cache cache;

    public Optional<CountryStats> getStatsByCountry(String country) {
        try {
            return Optional.of(request.getCountryStats(country));
        } catch (IOException | InterruptedException e) {
            logger.log(Level.WARNING, "SERVICE: {0}", e.getMessage());

            // Restore interrupted state...
            Thread.currentThread().interrupt();

            return Optional.empty();
        }
    }

    public Optional<List<CountryStats>> getStats() {
        try {
            return Optional.of(request.getAllCountryStats());
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
        try {
            return Optional.of(request.getCountryHistory(country));
        } catch (IOException | InterruptedException e) {
            logger.log(Level.WARNING, "SERVICE: {0}", e.getMessage());
            
            // Restore interrupted state...
            Thread.currentThread().interrupt();

            return Optional.empty();
        }
    }

    public Optional<List<CountryStats>> getHistory(String country, String date) {
        try {
            return Optional.of(request.getCountryHistoryByDay(country, date));
        } catch (IOException | InterruptedException e) {
            logger.log(Level.WARNING, "SERVICE: {0}", e.getMessage());
            
            // Restore interrupted state...
            Thread.currentThread().interrupt();

            return Optional.empty();
        }
    }

    public Optional<List<String>> getAllCountries() {
        try {
            return Optional.of(request.getCountries());
        } catch (IOException | InterruptedException e) {
            logger.log(Level.WARNING, "SERVICE: {0}", e.getMessage());
            
            // Restore interrupted state...
            Thread.currentThread().interrupt();

            return Optional.empty();
        }
    }

}