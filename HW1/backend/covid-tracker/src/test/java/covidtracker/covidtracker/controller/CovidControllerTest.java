package covidtracker.covidtracker.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import covidtracker.covidtracker.model.Cases;
import covidtracker.covidtracker.model.CountryStats;
import covidtracker.covidtracker.model.Deaths;
import covidtracker.covidtracker.model.Tests;
import covidtracker.covidtracker.service.CovidService;

@WebMvcTest(CovidController.class)
public class CovidControllerTest {

    private CountryStats stats;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CovidService service;

    @BeforeEach
    public void setUp() throws Exception {
        /* 
        {
            "continent":"Europe"
            "country":"Portugal"
            "population":10143368
            "cases":{
                "new":NULL
                "active":NULL
                "critical":61
                "recovered":NULL
                "1M_pop":"366691"
                "total":3719485
            }
            "deaths":{
                "new":NULL
                "1M_pop":"2168"
                "total":21993
            }
            "tests":{
                "1M_pop":"4017243"
                "total":40748372
            }
            "day":"2022-04-20"
            "time":"2022-04-20T10:15:03+00:00"
        }
        */
        stats = new CountryStats("Europe", "Portugal", 10143368, new Cases(-1, -1, 61, -1, 366691, 3719485), new Deaths(-1, 2168, 21993), new Tests(4017243, 40748372), "2022-04-20", "2022-04-20T10:15:03+00:00");
    }

    @Test
    public void whenGetStatsByCountry() throws Exception {

        // when(service.existCountry(country)).thenReturn(Collections.emptyList());
        when(service.getStatsByCountry(stats.getName())).thenReturn(Optional.of(stats));

        mvc.perform(
            get("/api/v1/stats/{country}", stats.getName()).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            // Isto era se estivessemos a testar o serviço
            //.andExpect(jsonPath("$response", hasSize(1)))
            //.andExpect(jsonPath("$results", is(1)))
            //.andExpect(jsonPath("$parameters.country", is(country)))
            //.andExpect(jsonPath("$response[0].continent", is(stats.getContinent())))
            .andExpect(jsonPath("$[0].continent", is(stats.getContinent())));
        
        // verify(service, times(1)).existCountry(country);
        verify(service, times(1)).getStatsByCountry(stats.getName());
    }

   /*  @Test
    public void whenGetStatsByCountryWrongName() throws Exception {
        String country = "port";
        List<String> res = new ArrayList<String>();
        res.add("Country " + country +  " doesn't exist");

        when(service.existCountry(country)).thenReturn(res);

        mvc.perform(
            get("/api/v1/stats/{country}", country).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
        
        verify(service, times(1)).existCountry(country);
    } */

    @Test
    public void whenGetAllStats() throws Exception {
        List<CountryStats> allStats = Arrays.asList(stats);

        when(service.getStats()).thenReturn(Optional.of(allStats));

        mvc.perform(
            get("/api/v1/stats").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(service, times(1)).getStats();
    }

    @Test
    public void whenGetHistory() throws Exception {

        List<CountryStats> history = Arrays.asList(stats, stats);
        when(service.getHistory(stats.getName())).thenReturn(history);

        mvc.perform(
            get("/api/v1/history/{country}", stats.getName()).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name", is(stats.getName())));

        verify(service, times(1)).getHistory(stats.getName());
    }

    @Test
    public void whenGetHistoryWithDate() throws Exception {

        List<CountryStats> history = Arrays.asList(stats, stats);

        when(service.getHistory(stats.getName(), stats.getDay())).thenReturn(history);

        mvc.perform(
            get("/api/v1/history/{country}", stats.getName()).contentType(MediaType.APPLICATION_JSON).param("date", stats.getDay()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].day", is(stats.getDay())))
            .andExpect(jsonPath("$[0].name", is(stats.getName())));

        verify(service, times(1)).getHistory(stats.getName(), stats.getDay());
    }
}
