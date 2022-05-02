package covidtracker.covidtracker.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import covidtracker.covidtracker.model.CountryStats;
import covidtracker.covidtracker.service.CovidService;
import io.swagger.v3.oas.annotations.Operation;

/**
 * CovidSController
 */
@RestController
@RequestMapping("/api/v1")
public class CovidController {

    private static Logger logger = Logger.getLogger(CovidController.class.getName()); 
    
    @Autowired
    private CovidService covidService;

    @Operation(summary = "Get current COVID-19 statistics from all countries available or from just one country")
    @GetMapping(value = {"/stats", "/stats/{country}"})
    public ResponseEntity<List<CountryStats>> getStats(@PathVariable("country") Optional<String> country) {
        logger.log(Level.INFO, "Statistics");

        if(country.isPresent()) {
            Optional<CountryStats> result = covidService.getStatsByCountry(country.get());
            return result.isPresent() ? ResponseEntity.ok(Arrays.asList(result.get())) : new ResponseEntity<>(Collections.emptyList(),HttpStatus.BAD_REQUEST);
        }

        Optional<List<CountryStats>> result = covidService.getStats();

        return result.isPresent() ? ResponseEntity.ok(result.get()) : new ResponseEntity<>(Collections.emptyList(),HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Get history of COVID-19 statistics from a country in a specific day or since there are data")
    @GetMapping("/history/{country}")
    public ResponseEntity<List<CountryStats>> getHistoryByCountry(@PathVariable("country") String country, @RequestParam Optional<String> date) {
        logger.log(Level.INFO, "History");

        List<CountryStats> result;

        if(date.isEmpty()) {
            logger.log(Level.INFO, "Controller: get all {0}`s history", country);
            
            Optional<List<CountryStats>> countryStats = covidService.getHistory(country);
            if(countryStats.isPresent()) {
                result = countryStats.get();
            } else {
                logger.log(Level.WARNING, "Endpoint needs a country name");
                return new ResponseEntity<>(Collections.emptyList(),HttpStatus.BAD_REQUEST);
            }
        } else {
            Optional<List<CountryStats>> countryStats = covidService.getHistory(country, date.get());
            if(countryStats.isPresent()) {
                result = countryStats.get();
            } else {
                logger.log(Level.WARNING, "Endpoint needs a country name");
                return new ResponseEntity<>(Collections.emptyList(),HttpStatus.BAD_REQUEST);
            }
        }
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Get all countries with COVID-19 information available")
    @GetMapping("/countries")
    public ResponseEntity<List<String>> getAllCountries() {
        logger.log(Level.INFO, "Countries");

        Optional<List<String>> result = covidService.getAllCountries();

        return result.isPresent() ? ResponseEntity.ok(result.get()) : new ResponseEntity<>(Collections.emptyList(),HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Get cache details")
    @GetMapping("/cache/details")
    public ResponseEntity<Map<String, Object>> getCacheDetails() {
        logger.log(Level.INFO, "Cache Details");
        return ResponseEntity.ok(covidService.getCacheDetails());
    }
}