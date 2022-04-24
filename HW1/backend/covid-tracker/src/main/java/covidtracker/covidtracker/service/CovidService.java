package covidtracker.covidtracker.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import covidtracker.covidtracker.model.CountryStats;

/**
 * CovidService
 */
@Service
public class CovidService {
    // https://covid-193.p.rapidapi.com

    public CountryStats getStatsByCountry(String string) {
        return null;
    }

    public List<CountryStats> getStats() {
        // colocar todas as stats num array e não em 3
        return Collections.emptyList();
    }

    public List<String> existCountry(String country) {
        // se o pais existir, retornar lista vazia
        // se não existir (results: 0), retornar paises parecidos ou mensagem de erro
        return country.equals("") ? Collections.emptyList() : new ArrayList<>();
    }

    public List<CountryStats> getHistory(String country) {
        return Collections.emptyList();
    }

    public List<CountryStats> getHistory(String country, String date) {
        return Collections.emptyList();
    }

}