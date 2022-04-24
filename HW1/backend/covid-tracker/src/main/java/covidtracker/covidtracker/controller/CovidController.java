package covidtracker.covidtracker.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import covidtracker.covidtracker.model.CountryStats;
import covidtracker.covidtracker.service.CovidService;

/**
 * CovidService
 */
@RestController
@RequestMapping("/api/v1")
public class CovidController {

    @Autowired
    private CovidService covidService;

    @GetMapping(value = {"/stats", "/stats/{country}"})
    public ResponseEntity<List<CountryStats>> getStats(@PathVariable("country") Optional<String> country) {

        if(country.isPresent()) {
            // SELECT no codigo, assim pessoas não podem escolher pais errado
            // List<String> existsCountry = covidService.existCountry(country.get());

            //if(existsCountry.isEmpty()) {
            CountryStats result = covidService.getStatsByCountry(country.get());
            return ResponseEntity.ok(Arrays.asList(result));
            //} 

            // return new ResponseEntity<>(Optional.of(existsCountry), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(covidService.getStats());
    }

    @GetMapping("/history/{country}")
    public ResponseEntity<List<CountryStats>> getHistoryByCountry(@PathVariable("country") String country, @RequestParam Optional<String> date) {
        // List<String> existsCountry = covidService.existCountry(country);

        // if((existsCountry.isEmpty() || country.equalsIgnoreCase("all"))) {
        List<CountryStats> result;

        if(date.isEmpty()) {
            result = covidService.getHistory(country);
        } else {
            result = covidService.getHistory(country, date.get());
        }
        return ResponseEntity.ok(result);
        //} 

        // return new ResponseEntity<>(Optional.of(existsCountry), HttpStatus.NOT_FOUND);
    }
}