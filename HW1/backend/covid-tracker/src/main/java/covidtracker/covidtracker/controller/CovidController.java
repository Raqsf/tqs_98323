package covidtracker.covidtracker.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
 * CovidService
 */
@RestController
@RequestMapping("/api/v1")
public class CovidController {

    @Autowired
    private CovidService covidService;

    @GetMapping(value = {"/stats", "/stats/{country}"})
    public ResponseEntity<List<CountryStats>> getStats(@PathVariable("country") Optional<String> country) throws InterruptedException {

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
            result = covidService.getHistory(country);
        } else {
            result = covidService.getHistory(country, date.get());
        }
        return ResponseEntity.ok(result);
        //} 

        // return new ResponseEntity<>(Optional.of(existsCountry), HttpStatus.NOT_FOUND);
    }

    // TODO: metodo que vai buscar todos os paises
}