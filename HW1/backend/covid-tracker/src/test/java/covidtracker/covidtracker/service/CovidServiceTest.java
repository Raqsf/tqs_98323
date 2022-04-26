package covidtracker.covidtracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import covidtracker.covidtracker.model.Cases;
import covidtracker.covidtracker.model.CountryStats;
import covidtracker.covidtracker.model.Deaths;
import covidtracker.covidtracker.model.Tests;
import covidtracker.covidtracker.utils.HttpRequests;

@ExtendWith(MockitoExtension.class)
public class CovidServiceTest {

    @Mock( lenient = true)
    private HttpRequests requests;

    @InjectMocks
    private CovidService covidService;

    private CountryStats stats;

    @BeforeEach
    private void setUp() {
        stats = new CountryStats("Europe", "Portugal", 10142964, new Cases(-1, -1, 61, -1, 373830, 3791744), 
                    new Deaths(-1, 2185, 3791744), new Tests(4046001, 3791744), "2022-04-25", "2022-04-25T18:15:04+00:00");
        /* {
            "continent": "Europe",
            "name": "Portugal",
            "population": 10142964,
            "cases": {
              "oneMPop": 373830,
              "total": 3791744,
              "newCases": -1,
              "activeCases": -1,
              "criticalCases": 61,
              "recovered": -1
            },
            "deaths": {
              "oneMPop": 2185,
              "total": 3791744,
              "newDeaths": -1
            },
            "tests": {
              "oneMPop": 4046001,
              "total": 3791744
            },
            "day": "2022-04-25",
            "time": "2022-04-25T18:15:04+00:00"
          } */
    }

    @Test
    public void whenGetStatsByCountry() throws IOException, InterruptedException {

        when(requests.getCountryStats("portugal")).thenReturn(stats);

        assertEquals(covidService.getStatsByCountry("portugal"), Optional.of(stats));

        verify(requests, times(1)).getCountryStats("portugal");
        // verify(requests, times(1)).doRequest(Mockito.any());
    }

    @Test
    public void whenGetStatsByCountry_SomethingWrong() throws IOException, InterruptedException {

        when(requests.getCountryStats("portugal")).thenThrow(IOException.class);

        //assertThrows(IOException.class, () -> covidService.getStatsByCountry("portugal"));
        assertEquals(covidService.getStatsByCountry("portugal"), Optional.empty());

        verify(requests, times(1)).getCountryStats("portugal");
        // verify(requests, times(1)).doRequest(Mockito.any());
    }

    @Test
    public void whenGetStatsAllCountries() throws IOException, InterruptedException {

        when(requests.getAllCountryStats()).thenReturn(Arrays.asList(stats, stats));

        assertEquals(covidService.getStats(), Optional.of(Arrays.asList(stats, stats)));

        verify(requests, times(1)).getAllCountryStats();
    }

    @Test
    public void whenGetStatsAllCountries_SomethingWrong() throws IOException, InterruptedException {

        when(requests.getAllCountryStats()).thenThrow(IOException.class);

        assertEquals(covidService.getStats(), Optional.empty());

        verify(requests, times(1)).getAllCountryStats();
    }

    @Test
    public void whenGetCountryHistory() throws IOException, InterruptedException {
        
        CountryStats newStats = stats;
        newStats.setTime("2022-04-25T18:30:04+00:00");

        when(requests.getCountryHistory(stats.getName())).thenReturn(Arrays.asList(stats, newStats));

        assertEquals(covidService.getHistory(stats.getName()), Optional.of(Arrays.asList(stats, newStats)));

        verify(requests, times(1)).getCountryHistory(stats.getName());
    }

    @Test
    public void whenGetCountryHistory_SomethingWrong() throws IOException, InterruptedException {
        
        when(requests.getCountryHistory(stats.getName())).thenThrow(IOException.class);

        assertEquals(covidService.getHistory(stats.getName()), Optional.empty());

        verify(requests, times(1)).getCountryHistory(stats.getName());
    }

    @Test
    public void whenGetCountryHistoryByDay() throws IOException, InterruptedException {
        CountryStats newStats = stats;
        newStats.setTime("2022-04-25T18:30:04+00:00");

        when(requests.getCountryHistoryByDay(stats.getName(), stats.getDay())).thenReturn(Arrays.asList(stats, newStats));

        assertEquals(covidService.getHistory(stats.getName(), stats.getDay()), Optional.of(Arrays.asList(stats, newStats)));

        verify(requests, times(1)).getCountryHistoryByDay(stats.getName(), stats.getDay());
    }

    @Test
    public void whenGetCountryHistoryByDay_SomethingWrong() throws IOException, InterruptedException {
        
        when(requests.getCountryHistoryByDay(stats.getName(), stats.getDay())).thenThrow(IOException.class);

        assertEquals(covidService.getHistory(stats.getName(), stats.getDay()), Optional.empty());

        verify(requests, times(1)).getCountryHistoryByDay(stats.getName(), stats.getDay());
    }

    @Test
    public void whenGetAllCountries() throws IOException, InterruptedException {

        when(requests.getCountries()).thenReturn(Arrays.asList("Afghanistan", "Albania", "Algeria", "Andorra"));

        assertEquals(covidService.getAllCountries(), Optional.of(Arrays.asList("Afghanistan", "Albania", "Algeria", "Andorra")));

        verify(requests, times(1)).getCountries();

    }

    @Test
    public void whenGetAllCountries_SomethingWrong() throws IOException, InterruptedException {

        when(requests.getCountries()).thenThrow(IOException.class);

        assertEquals(covidService.getAllCountries(), Optional.empty());

        verify(requests, times(1)).getCountries();

    }

}
