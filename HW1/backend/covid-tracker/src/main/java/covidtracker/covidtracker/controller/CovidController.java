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

/**
 * CovidSController
 */
@RestController
@RequestMapping("/api/v1")
public class CovidController {

    private static Logger logger = Logger.getLogger(CovidController.class.getName()); 
    
    @Autowired
    private CovidService covidService;

    @GetMapping(value = {"/stats", "/stats/{country}"})
    public ResponseEntity<List<CountryStats>> getStats(@PathVariable("country") Optional<String> country) {

        if(country.isPresent()) {
            // SELECT no codigo, assim pessoas não podem escolher pais errado
            // List<String> existsCountry = covidService.existCountry(country.get());

            //if(existsCountry.isEmpty()) {
            Optional<CountryStats> result = covidService.getStatsByCountry(country.get());
            return result.isPresent() ? ResponseEntity.ok(Arrays.asList(result.get())) : new ResponseEntity<>(Collections.emptyList(),HttpStatus.BAD_REQUEST);
            //} 

            // return new ResponseEntity<>(Optional.of(existsCountry), HttpStatus.NOT_FOUND);
        }

        Optional<List<CountryStats>> result = covidService.getStats();

        return result.isPresent() ? ResponseEntity.ok(result.get()) : new ResponseEntity<>(Collections.emptyList(),HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/history/{country}")
    public ResponseEntity<List<CountryStats>> getHistoryByCountry(@PathVariable("country") String country, @RequestParam Optional<String> date) {
        // List<String> existsCountry = covidService.existCountry(country);

        // if((existsCountry.isEmpty() || country.equalsIgnoreCase("all"))) {
        List<CountryStats> result;

        if(date.isEmpty()) {
            logger.log(Level.INFO, "Controller: get all {0}'s history", country);
            
            Optional<List<CountryStats>> countryStats = covidService.getHistory(country);
            if(countryStats.isPresent()) {
                result = countryStats.get();
            } else {
                return new ResponseEntity<>(Collections.emptyList(),HttpStatus.BAD_REQUEST);
            }
        } else {
            Optional<List<CountryStats>> countryStats = covidService.getHistory(country, date.get());
            if(countryStats.isPresent()) {
                result = countryStats.get();
            } else {
                return new ResponseEntity<>(Collections.emptyList(),HttpStatus.BAD_REQUEST);
            }
        }
        return ResponseEntity.ok(result);
        //} 

        // return new ResponseEntity<>(Optional.of(existsCountry), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/countries")
    public ResponseEntity<List<String>> getAllCountries() {

        Optional<List<String>> result = covidService.getAllCountries();

        return result.isPresent() ? ResponseEntity.ok(result.get()) : new ResponseEntity<>(Collections.emptyList(),HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/cache/details")
    public ResponseEntity<Map<String, Object>> getCacheDetails() {
        logger.log(Level.INFO, "getCacheDetails");
        return ResponseEntity.ok(covidService.getCacheDetails());
    }
}