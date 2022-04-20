package covidtracker.covidtracker.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
            CountryStats result = covidService.getStatsByCountry(country.get());
            return ResponseEntity.ok(Arrays.asList(result));
        }

        return ResponseEntity.ok(covidService.getStats());
    }

    @GetMapping("/history/{country}")
    public ResponseEntity<CountryStats> getHistoryByCountry(@PathVariable("country") String country, @RequestBody Optional<Date> date) {
        return null;
    }
}